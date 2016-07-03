package javauction.controller;

import javauction.model.UserEntity;
import javauction.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jimouris on 7/3/16.
 */
@WebServlet(name = "updateUser")
public class updateUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        UserEntity user;
        String next_page = "/userInfo.jsp";
        int uid = Integer.parseInt(request.getParameter("uid"));

        // retrieve user's info
        try {
            UserService userService = new UserService();
            Boolean status = userService.approveUser(uid);
            user = userService.getUser(uid);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // then forward the request to userInfo.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
