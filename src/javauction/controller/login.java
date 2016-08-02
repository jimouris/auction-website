package javauction.controller;

import javauction.model.UserEntity;
import javauction.service.LoginService;
import javauction.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        LoginService.LoginStatus result;
        String next_page = "/";
        HttpSession session = request.getSession();

        // check the credentials
        // and forward to appropriate page if admin or user
        try {
            LoginService loginService = new LoginService();

            if (usr_type.equals("admin")) {
                result = loginService.authenticateAdmin(username, password);
                if (result == LoginService.LoginStatus.LOGIN_SUCCESS) {

                    session.setAttribute("isAdmin", true);

                    next_page = "admin.jsp";
                } else if (result == LoginService.LoginStatus.LOGIN_NOT_ADMIN) {
                    next_page = "index.jsp";
                    System.out.println("You are not admin!");
                } else if (result == LoginService.LoginStatus.LOGIN_WRONG_UNAME_PASSWD) {
                    System.out.println("Wrong username and password");
                } else {
                    System.out.println("Login failed for some reason");
                }
            } else if (usr_type.equals("user")){
                result = loginService.authenticateUser(username, password);
                if (result == LoginService.LoginStatus.LOGIN_SUCCESS) {

                    UserService userservice = new UserService();
                    UserEntity user = userservice.getUser(username);
                    session.setAttribute("uid", user.getUserId());
                    session.setAttribute("isAdmin", false);

                    next_page = "homepage.jsp";
                } else if (result == LoginService.LoginStatus.LOGIN_NOT_APPROVED) {
//                    next_page = "approvalerror.jsp";
                    System.out.println("U r not approved my man:/");
                } else if (result == LoginService.LoginStatus.LOGIN_WRONG_UNAME_PASSWD) {
                    System.out.println("Wrong username and password");
                } else {
                    System.out.println("Login failed for some reason");
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
