package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.model.MessagesEntity;
import javauction.model.UserEntity;
import javauction.service.AuctionService;
import javauction.service.MessagesService;
import javauction.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jimouris on 8/5/16.
 */
@WebServlet(name = "message")
public class message extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessagesService messagesService = new MessagesService();
        String next_page = "/user/homepage.jsp";

        if (request.getParameter("action").equals("addNewMessage")) {
            HttpSession session = request.getSession();
//            long sid = (long) session.getAttribute("uid");
            long sid = ((UserEntity) session.getAttribute("user")).getUserId();

            long rid = Long.parseLong(request.getParameter("rid"));
            long aid = Long.parseLong(request.getParameter("aid"));
            String msg = request.getParameter("message_text");

            MessagesEntity messagesEntity = new MessagesEntity(sid, rid, aid, msg);
            messagesService.addEntity(messagesEntity);

            getConversation(messagesService, request, aid);
            request.setAttribute("aid", aid);
            request.setAttribute("rid", rid);

            next_page = "/user/messages.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = null;
        String param = request.getParameter("action");
        MessagesService messagesService = new MessagesService();
        HttpSession session = request.getSession();
        UserService userService = new UserService();
        AuctionService auctionService = new AuctionService();

        long rid;
        switch (param) {
            case "getConversation": /* get all actions with sellerId = uid (from session) */
                rid = Long.parseLong(request.getParameter("rid"));
                long aid = Long.parseLong(request.getParameter("aid"));

                getConversation(messagesService, request, aid);
                request.setAttribute("aid", aid);
                request.setAttribute("rid", rid);
                next_page = "/user/messages.jsp";

                break;
            case "listInbox": /* get all messages from inbox */
//                rid = (long) session.getAttribute("uid");
                rid = ((UserEntity) session.getAttribute("user")).getUserId();

                List<MessagesEntity> msgsLst = messagesService.getInboxOrSent(rid, MessagesService.Message_t.Inbox_t);

                List<UserEntity> sendersLst = new ArrayList<>();
                List<AuctionEntity> auctionsLst = new ArrayList<>();
                for (MessagesEntity m : msgsLst) {
                    sendersLst.add(userService.getUser(m.getSenderId()));
                    auctionsLst.add(auctionService.getAuction(m.getAuctionId()));
                }

                request.setAttribute("messagesLst", msgsLst);
                request.setAttribute("sendersLst", sendersLst);
                request.setAttribute("auctionsLst", auctionsLst);

                next_page = "/user/listInbox.jsp";
                break;
            case "listSent": /* get all messages from inbox */
//                rid = (long) session.getAttribute("uid");
                rid = ((UserEntity) session.getAttribute("user")).getUserId();

                msgsLst = messagesService.getInboxOrSent(rid, MessagesService.Message_t.Sent_t);

                List<UserEntity> receiversLst = new ArrayList<>();
                auctionsLst = new ArrayList<>();
                for (MessagesEntity m : msgsLst) {
                    receiversLst.add(userService.getUser(m.getReceiverId()));
                    auctionsLst.add(auctionService.getAuction(m.getAuctionId()));
                }

                request.setAttribute("messagesLst", msgsLst);
                request.setAttribute("receiversLst", receiversLst);
                request.setAttribute("auctionsLst", auctionsLst);

                next_page = "/user/listSent.jsp";
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    private void getConversation(MessagesService messagesService, HttpServletRequest request, long aid){
        UserService userService = new UserService();

        List<MessagesEntity> messagesLst = messagesService.getAuctionConversation(aid);
        List<MessagesEntity> revMessagesLst = new ArrayList<>(messagesLst.subList(0, messagesLst.size()));
        Collections.reverse(revMessagesLst);

        List<UserEntity> sendersLst = new ArrayList<>();
        for (MessagesEntity m : revMessagesLst) {
            sendersLst.add(userService.getUser(m.getSenderId()));
        }

        request.setAttribute("messagesLst", revMessagesLst);
        request.setAttribute("sendersLst", sendersLst);
    }

}
