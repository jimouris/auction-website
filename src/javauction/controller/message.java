package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.model.MessagesEntity;
import javauction.model.NotificationEntity;
import javauction.model.UserEntity;
import javauction.service.AuctionService;
import javauction.service.MessagesService;
import javauction.service.NotificationService;
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

@WebServlet(name = "message")
public class message extends HttpServlet {


    // all actions of post follows a post/redirect/get pattern in order to avoid form resubmission
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessagesService messagesService = new MessagesService();
        NotificationService notificationService = new NotificationService();
        String next_page = "/user/homepage.jsp";
        HttpSession session = request.getSession();

        long rid = Long.parseLong(request.getParameter("rid"));                 // the receiver of conversation
        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if (action.equals("addNewMessage")) {
                long sid = ((UserEntity) session.getAttribute("user")).getUserId(); // the sender
                long aid = Long.parseLong(request.getParameter("aid"));             // the auction id
                String msg = request.getParameter("message_text");

                MessagesEntity messagesEntity = new MessagesEntity(sid, rid, aid, msg);
                Long mid = messagesService.addNewMessage(messagesEntity);
                NotificationEntity notificationEntity = new NotificationEntity(rid, "message", aid, sid, mid);
                notificationService.addEntity(notificationEntity);

                String url = "/message.do?action=getConversation&rid=" + rid + "&aid=" + aid;
                response.sendRedirect(url);
                return;
            } else if (action.equals("deleteMessage")){
                long uid = ((UserEntity) session.getAttribute("user")).getUserId(); // just for safety check
                long mid = Long.parseLong(request.getParameter("mid"));             // message id to delete
                long sender_id = Long.parseLong(request.getParameter("sid"));
                long aid = Long.parseLong(request.getParameter("aid"));

                // check if someone else tries to delete a message that he/she does not own
                if (sender_id == uid){
                    messagesService.deleteMessage(mid);
    //                notificationService.deleteNotificaton(mid);
                }

                String url = "/message.do?action=getConversation&rid=" + rid + "&aid=" + aid;
                response.sendRedirect(url);
                return;
            }
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = null;
        MessagesService messagesService = new MessagesService();
        HttpSession session = request.getSession();
        UserService userService = new UserService();
        AuctionService auctionService = new AuctionService();

        long rid;
        if (request.getParameter("action") != null) {
            String param = request.getParameter("action");
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

    // i want to return something like auction.do?action=simpleSearch&name=&page=
//    private String constructMessageURL(HttpServletRequest request){
//        Map<String, String[]> params = request.getParameterMap();
//        String url = "message.do?";
//        for (Map.Entry<String, String[]> entry : params.entrySet()) {
//            String[] values = entry.getValue();
//            for (String value : values) {
//                url = url + entry.getKey() + "=";
//                url = url + value + "&";
//            }
//        }
//        // cut the last & that iteration adds
//        url = url.substring(0, url.length()-1);
//        return url;
//    }
}
