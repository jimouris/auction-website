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
        if(session.getAttribute("username") != null){
            request.setAttribute("loginFailed", true);
            request.setAttribute("msg",  "You are already logged in");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
            return;
        }

        // create a new user to be used
        customer user = new customer(); // instansiate a user to be registered

        // get the password and login.
        // If they are empty, then send the viewer back to homepage
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        if(username == null || username.length() == 0 || pass == null || pass.length() == 0){
            request.setAttribute("loginFailed", true);
            request.setAttribute("msg", "Username or password can't be empty");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
            return;
        }

        user.email= username;
        user.password = pass;

        // get the db connection
        Connection database = (Connection) getServletContext().getAttribute("db");

        // pass the db connection and try to login
        if (user.login(database)) {
            // this is a valid user
            request.setAttribute("loginFailed", false);
            request.setAttribute("msg", "Congratulation.");

            // get any running sessions and destroy it
            session = request.getSession();
            session.invalidate();
            // now  create a new session
            session = request.getSession();

            /* checks the type of user that is logged in.
             * 1) the user is coming from backoffice and is admin
             * 2) the user is not admin and has valid credential, so sent him to store as logged in
             * 3) the user probably did a malicious action (like trying admin passwords from homepage).
             */
            if(user.isAdmin() && request.getRequestURI().contentEquals("/backoffice.jsp")){ /* 1 */
                session.setAttribute("user-type", "admin");
                RequestDispatcher view = request.getRequestDispatcher("admin.jsp");
                view.forward(request, response);
                return;
            } else if(!user.isAdmin()){ /* 2 */
                session.setAttribute("user-type", "simple");
                RequestDispatcher view = request.getRequestDispatcher("store.jsp");
                view.forward(request, response);
                return;
            } else{ /* 3 */
                session.invalidate();
                request.setAttribute("loginFailed", true);
                request.setAttribute("msg", "The email or password was wrong. Try again");
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                view.forward(request, response);
                return;
            }
        } else{
            // if the credentials were wrong, set the appropriate message to be displayed
            // and send to homepage in order to login again
            request.setAttribute("loginFailed", true);
            request.setAttribute("msg", "The email or password was wrong. Try again");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request, response);
            return;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
