package javauction;

import javauction.model.UserEntity;
import javauction.service.RegisterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gpelelis on 17/4/2016.
 * this is called as register.do
 */
public class register extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // prepare variables
        String next_page = "/register.jsp";

        // get the user input
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String vat = request.getParameter("vat");
        String phonenumber = request.getParameter("phone");
        String homeaddress = request.getParameter("address");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String postcode = request.getParameter("postcode");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        UserEntity user = new UserEntity(username, password, name, lastname, email, phonenumber, vat, homeaddress, latitude, longitude, city, country);


        // tell the customer to register a new user
        try {
            System.out.println("1");
            RegisterService registerService = new RegisterService();
            System.out.println("2");
            boolean result = registerService.register(user);
            System.out.println("3");

            if (result) {
                request.setAttribute("regStatus", "Successfully registered");
                next_page = "/welcome.jsp";
            } else {
                request.setAttribute("regStatus", "Registration Failed :/");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);

        // generate the message that we want to send to the user

        /*
        else if (result == OpStatus.Error) {
            request.setAttribute("regStatus", "There was an error");
        } else if (result == OpStatus.UsernameExist) {
            request.setAttribute("regStatus", "This username already exist");
        } else if (result == OpStatus.DiffPass) {
            request.setAttribute("regStatus", "You didn't provide the same password");
        }*/
//

//        status = user.register(request.getParameter("repeat_password"), db);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
