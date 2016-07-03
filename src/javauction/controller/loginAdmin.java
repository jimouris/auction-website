package javauction.controller;

import javauction.service.LoginService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gpelelis on 17/4/2016.
 * this is called as loginAdmin.do
 */
public class loginAdmin extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        boolean result = false;
        String next_page = "/backoffice.jsp";

        // get the user input & create the object
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // check the credentials
        // tell the customer to register a new user
        LoginService loginService = new LoginService();
        try {
            result = loginService.authenticateAdmin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List userLst;
        if (result) {
            next_page = "/listUsers.jsp";
            userLst = loginService.getAllUsers();

            request.setAttribute("userLst", userLst);
        }

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
