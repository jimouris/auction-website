package javauction;

import java.sql.SQLException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import javauction.model.Database;
import javauction.model.OpStatus;

public class listUsers extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        // prepare variables
        OpStatus status = OpStatus.Error;
        String next_page = "/listUsers.jsp";
        Connection db = (Connection) getServletContext().getAttribute("db");

        // This arraylist should have a list of customer objects.
        ArrayList users = new ArrayList();
        Database database = new Database();
        try {
            status = database.getEverybody(users, db);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // get the user to list on admin
        request.setAttribute("userList", users);

        // generate the message that we want to send to the user
        if (status == OpStatus.Success) {
            request.setAttribute("retrieveData", true);
        } else if (status == OpStatus.Error) {
            request.setAttribute("retrieveData", false);
            request.setAttribute("msg", "There was an error retrieving the list");
        } 

        // then forward the request to welcome.jsp with the information of status
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }
}
