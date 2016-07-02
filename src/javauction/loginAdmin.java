package javauction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import javauction.model.customer;
import javauction.model.OpStatus;

/**
 * Created by gpelelis on 17/4/2016.
 * this is called as loginAdmin.do
 */
public class loginAdmin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // prepare variables
        OpStatus status;
        String next_page = "/backoffice.jsp";
        Connection db = (Connection) getServletContext().getAttribute("db");

        // get the user input & create the object
        customer user = new customer();
        user.username = request.getParameter("username");
        user.password = request.getParameter("password");

        // check the credentials
        status = user.loginAdmin(db);

        // generate the message that we want to send to the user
        if (status == OpStatus.Success) {
            next_page = "/adminPanel.jsp";
        } else if (status == OpStatus.Error) {
            request.setAttribute("regStatus", "There was an error");
        } else if (status == OpStatus.WrongCredentials) {
            request.setAttribute("regStatus", "Username or password is wrong");
        }

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
