package javauction;

import javauction.model.customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by gpelelis on 4/5/2016.
 */
public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // if the viewer has a session then he must go to homepage
        // todo: check if this forward to index.jsp is the right one.
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            request.setAttribute("loginFailed", true);
            request.setAttribute("msg", "You are already logged in");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
            return;
        }


        // get the password and login.
        // If they are empty, then send the viewer back to homepage
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        if (username == null || username.length() == 0 || pass == null || pass.length() == 0) {
            request.setAttribute("loginFailed", true);
            request.setAttribute("msg", "Username or password can't be empty");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
            return;
        }

        // create a new user to be used
        customer user = new customer(username, pass); // instansiate a user to be registered


        // get the db connection
        Connection database = (Connection) getServletContext().getAttribute("db");

        // because referer Header attribute can be manipulated
        // we set a custom parameter from the page that the admin can login
        // this parameter must be (referrer, backoffice)
        String from = request.getParameter("referrer");
        Boolean comes_from_backoffice = false;
        if (from != null && from.length() != 0)
            comes_from_backoffice = from.contentEquals("backoffice") ? true : false;

        // test the credential. If the credentials are valid, the user.isValid returns true
        user.login(database);

        /* The message wrong email or password is presented to the viewer in the following possibilities
         * 1) the viewer uses wrong credential
         * 2) the viewer tries to login as a root from homepage
         */
        if (comes_from_backoffice) {
            if (user.isAdmin() && user.isValid()) {
                // now  create a new session
                session = request.getSession();
                session.setAttribute("user-type", "admin");

                // send him to new page as logged in admin
                request.setAttribute("loginFailed", false);
                request.setAttribute("msg", "Successful login.");
                RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
                view.forward(request, response);
                return;
            } else {
                // send him to new page with an error
                request.setAttribute("loginFailed", true);
                request.setAttribute("msg", "Wrong email or password");
                RequestDispatcher view = request.getRequestDispatcher("backoffice.jsp");
                view.forward(request, response);
                return;
            } // /End user.isAdmin
        } else {
            if (user.isValid() && !user.isAdmin()) {
                // now  create a new session
                session = request.getSession();
                session.setAttribute("user-type", "simple");

                // send him to new page as a logged in customer
                request.setAttribute("loginFailed", false);
                request.setAttribute("msg", "Successful login.");
                RequestDispatcher view = request.getRequestDispatcher("store.jsp");
                view.forward(request, response);
                return;
            } else if (!user.isValid() || user.isAdmin()) {
                // send him to new page
                request.setAttribute("loginFailed", true);
                request.setAttribute("msg", "Wrong email or password");
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                return;
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
