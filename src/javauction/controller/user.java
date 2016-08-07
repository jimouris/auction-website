package javauction.controller;

import javauction.model.UserEntity;
import javauction.service.RegisterService;
import javauction.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gpelelis on 19/4/2016.
 */
@WebServlet(name = "user")
public class user extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/public";
        // the endpoint that admin can use to approve a user
        if (request.getParameter("action").equals("approveUser")){
            response.setContentType("text/html");

            UserEntity user;
            next_page = "/admin/userInfo.jsp";
            long uid = Long.parseLong(request.getParameter("uid"));

            // retrieve user's info
            try {
                UserService userService = new UserService();
                Boolean status = userService.approveUser(uid);
                user = userService.getUser(uid);
                request.setAttribute("user", user);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (request.getParameter("action").equals("register")){
            response.setContentType("text/html");

            // prepare variables
            next_page = "/public/register.jsp";

            String password = request.getParameter("password");
            String repeat_password = request.getParameter("repeat_password");
            if (password.equals(repeat_password)) { /* if passwords match*/

                // get the user input
                String username = request.getParameter("username");
                String email = request.getParameter("email");
                String name = request.getParameter("name");
                String lastname = request.getParameter("lastname");
                String vat = request.getParameter("vat");
                String phonenumber = request.getParameter("phone");
                String homeaddress = request.getParameter("address");
                String latitude = request.getParameter("latitude");
                String longitude = request.getParameter("longitude");
                String city = request.getParameter("city");
                String country = request.getParameter("country");

                UserEntity user = new UserEntity(username, password, name, lastname, email, phonenumber, vat, homeaddress, latitude, longitude, city, country);

                // tell the customer to register a new user
                try {
                    RegisterService registerService = new RegisterService();
                    RegisterService.RegisterStatus result = registerService.register(user);

                    switch (result) {
                        case REG_SUCCESS:
                            request.setAttribute("regStatus", "Successfully registered");
                            next_page = "/user/welcome.jsp"; break;
                        case REG_UNAME_EXISTS:
                            System.out.println("Username exists"); break;
                        case REG_EMAIL_EXISTS:
                            System.out.println("Email exists"); break;
                        case REG_FAIL: default:
                            request.setAttribute("regStatus", "Registration Failed :/");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Passwords must match");
                request.setAttribute("regStatus", "Passwords must match");
            }
        }
        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/public";
        // pass a userEntity object that matches the uid
        if (request.getParameter("action").equals("getAUser")) {
            UserEntity user;
            next_page = "/admin/userInfo.jsp";
            long uid = Long.parseLong(request.getParameter("uid"));

            // retrieve user's info
            try {
                UserService userService = new UserService();
                user = userService.getUser(uid);
                request.setAttribute("user", user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("getAllUsers")){
            List userLst;
            next_page = "/admin/listUsers.jsp";
            UserService userService = new UserService();
            userLst = userService.getAllUsers();

            request.setAttribute("userLst", userLst);
        }
        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

}
