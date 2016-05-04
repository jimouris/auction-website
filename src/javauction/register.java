package javauction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import javauction.model.*;

/**
 * Created by gpelelis on 17/4/2016.
 * this is called as register.do
 */
public class register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // this would be sucess of failure
        boolean status = false;

        // create a new customer to be inserted
        customer human = new customer(); // instansiate a customer to be registered

        // get the passwords from the request
        String pass = request.getParameter("password");
        String pas2 = request.getParameter("password_repeat");

        // if the passwords are ok, then assign the data
        // and try to register the user
        if(human.validCustomerPass(pass, pas2)) {
            human.email= request.getParameter("email");
            human.name = request.getParameter("name");
            human.lastname = request.getParameter("lastname");
            human.password = request.getParameter("password");
            human.vat = request.getParameter("vat");
            human.phone = request.getParameter("phone");
            human.address = request.getParameter("address");
            human.city = request.getParameter("city");
            human.country = request.getParameter("country");
            human.postcode = request.getParameter("postcode");
            human.latitude = request.getParameter("latitude");
            human.longitude = request.getParameter("longitude");

            // get the db connection
            Connection database = (Connection) getServletContext().getAttribute("db");

            // try to add the customer to database
            status = human.registerCustomer(database);
        }

        // so the next page know if the insertion was successful
        request.setAttribute("status", status);

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher("/register.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
