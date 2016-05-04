package javauction;

import javauction.model.customer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gpelelis on 4/5/2016.
 */
public class login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // create a new user to be inserted
        customer user = new customer(); // instansiate a user to be registered

        // get the passwords and login
        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        user.email= username;
        user.password = pass;

        if (user.login()) {
            // if the user can login then let him go to store
            request.setAttribute("status", true);
            RequestDispatcher view = request.getRequestDispatcher("/store.jsp");
            view.forward(request, response);
        } else{
            // the login will fail, only if the viewer provided wrong user/pass
            request.setAttribute("status", false);
            request.setAttribute("status-msg", "The email or password was wrong.");

            // todo: also start a customer's session

            // then forward the request to welcome.jsp with the information of status
            RequestDispatcher view = request.getRequestDispatcher("/welcome.jsp");
            view.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
