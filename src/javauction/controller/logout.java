package javauction.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jimouris on 8/2/16.
 */
public class logout extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession new_session = request.getSession(false);
        if (new_session != null) {
            new_session.invalidate();
            response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
            response.setHeader("Cache-Control","no-store"); // Directs caches not to store the page under any circumstance
            response.setHeader("Cache-Control", "must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0);
        }
        request.setAttribute("successMsg", "You successfully logged out");
        RequestDispatcher view = request.getRequestDispatcher("/public/");
        view.forward(request, response);
    }
}
