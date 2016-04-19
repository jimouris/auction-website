package javauction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javauction.model.*;

/**
 * Created by gpelelis on 17/4/2016.
 * this is called as register.do
 */
public class register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // get the data from the request
        String c = request.getParameter("name");
        String d = request.getParameter("password");

        // get data from user API
        user Registered = new user(); // instantiate a register user
        String username = Registered.getUsername(c);
        String password = Registered.getPassword(d);

        request.setAttribute("addedUser", username);
        request.setAttribute("addedPass", password);

        RequestDispatcher view = request.getRequestDispatcher("register.jsp");

        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
