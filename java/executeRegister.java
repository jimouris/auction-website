

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import myPackage.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletException;


/**
 *
 * @author hostAdmin
 */
@WebServlet(urlPatterns = {"/executeRegister"})
public class executeRegister extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet executeRegister</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet executeRegister at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); // if is a simple get, then out a simple message
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        response.setContentType("text/html"); 
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Registration status</title>");
        out.println("<link href='./css/skeleton.css' rel='stylesheet'");
        out.println("</head>");
        out.println("<body>");
       
       

        // assign post data to vars
        String fname = request.getParameter("regName");
        String lname = request.getParameter("regSurname");
        String email = request.getParameter("regEmail");
        String vat = request.getParameter("regVAT");
        String address = request.getParameter("regAddress");
        String city = request.getParameter("regCity");
        String postcode = request.getParameter("regPostCode");
        String country = request.getParameter("regCountry");
        String password = request.getParameter("regPassword");
        String vPassword = request.getParameter("regVerifyPassword");
        
        // verify password and go
        if (password.equals(vPassword)){
            try {
                // setting connection & statement vars
                Connection conn = null;
                Statement stmt = null;

               
                // Open a connection
                System.out.println("Connecting to a selected database...");
                conn = DBConnection.getDBConnection();
                System.out.println("Connected database successfully...");

               

                // execute a query to insert the user
                System.out.println("Inserting someone into the user table (human's general info) ...");
                PreparedStatement psmtm = conn.prepareStatement(
                        "insert into account (firstname, lastname,vat,email,address,city,postcode,approved,country) values(?,?,?,?,?,?,?,?,?)");

                psmtm.setString(1, fname);
                psmtm.setString(2, lname);
                psmtm.setString(3, vat);
                psmtm.setString(4, email);
                psmtm.setString(5, address);
                psmtm.setString(6, city);
                psmtm.setString(7, postcode);
                psmtm.setBoolean(8, false);
                psmtm.setString(9, country);

                int i = psmtm.executeUpdate();


                // link user with a new account
                // find the id of user and insert it on account
                int userId = 0;
                ResultSet resultset = null;

                System.out.println("Getting the id of " + fname + ", " + lname + "...");
                stmt = conn.createStatement();
                String sql = "select id from account where email = \"" + email + "\"";
                System.out.println("The sql query is: " + sql);
                resultset = stmt.executeQuery(sql);
                resultset.last();
                userId = resultset.getInt("id");
                


                // execute a query to create the account
                System.out.println("Creating the account of " + fname + ", " + lname + "...");
                psmtm = conn.prepareStatement(
                    "insert into user (username, password, account) values(?,?,?)");

                psmtm.setString(1, email);
                psmtm.setString(2, password);
                psmtm.setInt(3, userId);

                int passQuery = psmtm.executeUpdate();

                // account insert and user update was succesfull
                if (i > 0 && passQuery > 0) {
                    response.sendRedirect("./approval.jsp");
                }

                // clean up environment
                psmtm.close(); // ps: statement for conn connection
                conn.close(); // conn: connection on tedprojectdatabase;
            } catch (Exception e2){
                if (e2.getMessage().indexOf("email_UNIQUE") > 0){
                    response.sendRedirect("./register.jsp?duplicate_email=1");
                    out.print("<h1>Registestration failed::" + e2.getMessage() + "</h1>");
                    
                }
                System.out.println(e2);
           }
        }
        else{
            out.print("<h1>Password doesn't match</h1>");            
        }
        
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
