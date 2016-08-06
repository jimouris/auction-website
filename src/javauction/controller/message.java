package javauction.controller;

import javauction.model.MessagesEntity;
import javauction.service.MessagesService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jimouris on 8/5/16.
 */
@WebServlet(name = "message")
public class message extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessagesService messagesService = new MessagesService();
        String next_page = "index.jsp";

        if (request.getParameter("action").equals("addNewMessage")) {
            HttpSession session = request.getSession();
            long sid = (long) session.getAttribute("uid");
            long rid = Long.parseLong(request.getParameter("rid"));
            long aid = Long.parseLong(request.getParameter("aid"));
            String msg = request.getParameter("message_text");

            MessagesEntity messagesEntity = new MessagesEntity(sid, rid, aid, msg);
            messagesService.addMessage(messagesEntity);

            getConversation(request, aid);
            request.setAttribute("aid", aid);
            request.setAttribute("rid", rid);

            next_page = "messages.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = null;
        String param = request.getParameter("action");

        switch (param) {
            case "getConversation": /* get all actions with sellerId = uid (from session) */
                long rid = Long.parseLong(request.getParameter("rid"));
                HttpSession session = request.getSession();
                long aid = Long.parseLong(request.getParameter("aid"));

                getConversation(request, aid);
                request.setAttribute("aid", aid);
                request.setAttribute("rid", rid);

                next_page = "/messages.jsp";
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    private void getConversation(HttpServletRequest request, long aid){
        MessagesService messagesService = new MessagesService();

        java.util.List messagesLst = messagesService.getAuctionConversation(aid);
        request.setAttribute("messagesLst", messagesLst);

    }

}
