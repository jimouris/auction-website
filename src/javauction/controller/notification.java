package javauction.controller;

import javauction.model.NotificationEntity;
import javauction.service.NotificationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gpelelis on 26/8/2016.
 */
@WebServlet(name = "notification", urlPatterns = {"/notification.do"})
public class notification extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long nid = Long.valueOf(request.getParameter("nid"));
        String next_page = "/user/homepage.jsp";
        String url = "";
        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if (action.equals("viewNotification")) {
                // get the notification based on the id
                NotificationService notificationService = new NotificationService();
                NotificationEntity notification = notificationService.getNotification(nid);

                // construct the url for get message
                switch (notification.getType()) {
                    case "message":
                        url = "/message.do?action=getConversation&rid=" + notification.getActorId()
                                + "&aid=" + notification.getAuctionId();
                        break;
                    case "rate":
                        url = "/rate.do?action=getRating&to_id=" + notification.getActorId()
                                + "&aid=" + notification.getAuctionId();
                        break;
                }
                // set a isSeen to true
                notificationService.setSeen(nid);

                // redirect
                response.sendRedirect(url);
                return;
            }
        }
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }
}
