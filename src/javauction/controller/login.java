package javauction.controller;

import javauction.service.UserService;
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
 * this is called as login.do
 */
public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // get the user input & create the object
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String usr_type = request.getParameter("action");


        boolean result = false;
        String next_page = "/";

        // check the credentials
        // and forward to appropriate page if admin or user
        try {
            LoginService loginService = new LoginService();

            if (usr_type.equals("admin")) {
                result = loginService.authenticateAdmin(username, password);
                if (result){
                    next_page = "admin.jsp";
                }
            } else if (usr_type.equals("user")){
                result = loginService.authenticateUser(username, password);
                if (result) {
                    next_page = "homepage.jsp";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
