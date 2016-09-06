package javauction.controller;

import javauction.model.UserEntity;
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

        UserService.LoginStatus result;
        String next_page = "/public/";
        HttpSession session = request.getSession();

        // check the credentials
        // and forward to appropriate page if admin or user
        try {
            UserService userService = new UserService();

            String errorMsg;
            if (usr_type.equals("admin")) {
                result = userService.authenticateAdmin(username, password);
                next_page = "/public/backoffice.jsp";
                if (result == UserService.LoginStatus.LOGIN_SUCCESS) {
                    session.setAttribute("isAdmin", true);

                    next_page = "/admin/homepage.jsp";
                } else if (result == UserService.LoginStatus.LOGIN_NOT_ADMIN) {
                    errorMsg = "You are not admin!";
                    request.setAttribute("errorMsg", errorMsg);
                } else if (result == UserService.LoginStatus.LOGIN_WRONG_UNAME_PASSWD) {
                    errorMsg = "Wrong username and password";
                    request.setAttribute("errorMsg", errorMsg);
                } else {
                    errorMsg = "Login failed for some reason";
                    request.setAttribute("errorMsg", errorMsg);
                }
            } else if (usr_type.equals("user")){
                result = userService.authenticateUser(username, password);
                if (result == UserService.LoginStatus.LOGIN_SUCCESS) {
                    UserEntity user = userService.getUser(username);
                    session.setAttribute("user", user);
                    session.setAttribute("isAdmin", false);

                    response.sendRedirect("/user.do?action=home");
                    next_page = "/user/homepage.jsp";
                    return;
                } else if (result == UserService.LoginStatus.LOGIN_NOT_APPROVED) {
                    errorMsg = "Be patient, wait for your approval";
                    request.setAttribute("errorMsg", errorMsg);
                } else if (result == UserService.LoginStatus.LOGIN_WRONG_UNAME_PASSWD) {
                    errorMsg = "Wrong username and password";
                    request.setAttribute("errorMsg", errorMsg);
                } else {
                    errorMsg = "Login failed";
                    request.setAttribute("errorMsg", errorMsg);
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
