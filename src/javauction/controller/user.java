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
import java.util.Map;


@WebServlet(name = "user")
public class user extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        if (request.getParameter("action") == null) {
            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }
        switch (request.getParameter("action")) {
            case "approveUser":
                response.setContentType("text/html");

                UserEntity user;
                next_page = "/admin/userInfo.jsp";
                long uid = Long.parseLong(request.getParameter("uid"));

                // retrieve user's info
                try {
                    UserService userService = new UserService();
                    userService.approveUser(uid);
                    user = userService.getUser(uid);
                    request.setAttribute("user", user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "register":
                response.setContentType("text/html");
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

                    user = new UserEntity(username, hash, salt, name, lastname, email, phonenumber, vat, homeaddress, latitude, longitude, city, country);

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
        /* then forward the request to welcome.jsp with the information of status */
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        if (request.getParameter("action") == null) {
            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }
        UserService userService = new UserService();
        switch (request.getParameter("action")) {
            case "getAUser":
                UserEntity user;
                next_page = "/admin/userInfo.jsp";
                long uid = Long.parseLong(request.getParameter("uid"));

                // retrieve user's info
                try {
                    user = userService.getUser(uid);
                    request.setAttribute("user", user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "getAllUsers":
                List userLst, nextPageUserLst;
                    next_page = "/admin/listUsers.jsp";
                Integer page = 0;
                Boolean isLastPage;
                if (request.getParameterMap().containsKey("page")) {
                    page = Integer.parseInt(request.getParameter("page"));
                }

                userLst = userService.getAllUsers(page);
                nextPageUserLst = userService.getAllUsers(page + 1) ;
                isLastPage = nextPageUserLst.size() == 0;

                constructPrevNext(page, isLastPage, request);
                request.setAttribute("userLst", userLst);
                break;
            case "unameExists":
                String uname = request.getParameter("uname");
                response.setContentType("text/html");

                PrintWriter out = response.getWriter();
                if (userService.unameExist( uname)) {
                    out.println("exists");
                }
                return;
            case "emailExists":
                String email = request.getParameter("email");
                response.setContentType("text/html");

                out = response.getWriter();
                if (userService.emailExist(email)) {
                    out.println("exists");
                }
                return;
            case "home":
                HttpSession session = request.getSession();
                uid = ((UserEntity) session.getAttribute("user")).getUserId();
                List<AuctionEntity> recommendationLst = (List<AuctionEntity>) session.getAttribute("recommendationLst");
                if (recommendationLst == null) {
                    RecommendationEngine recommender = new RecommendationEngine();
                    recommendationLst = recommender.getRecommendations(uid);
                    session.setAttribute("recommendationLst", recommendationLst);
                }
                next_page = "/user/homepage.jsp";
                break;
        }
        /* then forward the request to welcome.jsp with the information of status */
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }



    // i want to return something like auction.do?action=simpleSearch&name=&page=
    private String constructURL(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        String url = "/user.do?";
        for (Map.Entry<String, String[]> entry : params.entrySet())
        {
            // page param will be self assigned, so we have to skip it
            if (!entry.getKey().equals("page")) {
                String[] values = entry.getValue();
                for (String value : values) {
                    url = url + entry.getKey() + "=";
                    url = url + value + "&";
                }
            }
        }
        // cut the last & that iteration adds
        url = url.substring(0, url.length()-1);
        return url;
    }

    // sets attributes thta will be passed to searchResults jsp
    // in order to show previous and next page links on search result
    private void constructPrevNext(int page, boolean isLastPage, HttpServletRequest request) {
        String next, previous;
        next = constructURL(request) + "&page=";

        if (page > 0){
            previous = next + (page - 1);
            request.setAttribute("previousPage", previous);
        }
        if (!isLastPage){
            next = next + (page + 1); // don't change the order of this. previous will haave wrong initial string
            request.setAttribute("nextPage", next);
        }
    }

}
