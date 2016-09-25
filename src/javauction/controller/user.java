package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.model.RecommendationEngine;
import javauction.model.UserEntity;
import javauction.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by gpelelis on 19/4/2016.
 */
@WebServlet(name = "user")
public class user extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        if (request.getParameter("action") != null) {
            // the endpoint that admin can use to approve a user
            if (request.getParameter("action").equals("approveUser")) {
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

            } else if (request.getParameter("action").equals("register")) {
                response.setContentType("text/html");

                // prepare variables
                next_page = "/public/register.jsp";

                String password = request.getParameter("password");
                String repeat_password = request.getParameter("repeat_password");
                String errorMsg;
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

                    byte[] salt = PasswordAuthentication.genSalt();
                    byte[] hash = PasswordAuthentication.hash(password.toCharArray(), salt);

                    UserEntity user = new UserEntity(username, hash, salt, name, lastname, email, phonenumber, vat, homeaddress, latitude, longitude, city, country);

                    // tell the customer to register a new user
                    try {
                        UserService userService = new UserService();
                        UserService.RegisterStatus result = userService.register(user);
                        switch (result) {
                            case REG_SUCCESS:
                                request.setAttribute("errorMsg", "Successfully registered");
                                next_page = "/user/welcome.jsp";
                                break;
                            case REG_UNAME_EXISTS:
                                errorMsg = "Try another username, " + username + " exists";
                                request.setAttribute("errorMsg", errorMsg);
                                break;
                            case REG_EMAIL_EXISTS:
                                errorMsg = "Try another email, " + email + " exists";
                                request.setAttribute("errorMsg", errorMsg);
                                break;
                            case REG_FAIL:
                            default:
                                errorMsg = "Registration failed";
                                request.setAttribute("errorMsg", errorMsg);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMsg = "Passwords must match";
                    request.setAttribute("errorMsg", errorMsg);
                }
            }
        }
        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        if (request.getParameter("action") != null) {
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
            } else if (request.getParameter("action").equals("unameExists")){
                String uname = request.getParameter("uname");
                response.setContentType("text/html");

                UserService userService = new UserService();
                PrintWriter out = response.getWriter();
                if (userService.unameExist( uname))
                    out.println("exists");
                return;
            } else if (request.getParameter("action").equals("emailExists")) {
                String email = request.getParameter("email");
                response.setContentType("text/html");

                UserService userService = new UserService();
                PrintWriter out = response.getWriter();
                if (userService.emailExist(email))
                    out.println("exists");
                return;
            } else if (request.getParameter("action").equals("home")){
                HttpSession session = request.getSession();
                long uid = ((UserEntity) session.getAttribute("user")).getUserId();
                System.out.println("USERID:"+uid);
                RecommendationEngine recommender = new RecommendationEngine();
                List<AuctionEntity> recommendationLst = recommender.getRecommendations(uid);
                for (AuctionEntity ae : recommendationLst) {
                    System.out.println(ae.getAuctionId() + " " + ae.getName());
                }
                request.setAttribute("recommendationLst", recommendationLst);
                next_page = "/user/homepage.jsp";
            }
        }

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

}
