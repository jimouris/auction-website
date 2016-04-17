package javauction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * Created by gpelelis on 17/4/2016.
 */
@WebServlet(name = "test-db")
public class testdb extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        User mpla = new User("mitsos", "ds", "ds", "dsad", "dsad", "dsad", "ds", "dsad", "dsds", "dsada");

        ConntectionDB dbc = new ConntectionDB();

        try {
            Connection con = dbc.initializeConnection();
            dbc.insertUser(mpla);
            dbc.printUser(mpla);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
