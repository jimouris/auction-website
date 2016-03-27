

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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;


/**
 *
 * @author hostAdmin
 */
@WebServlet(urlPatterns = {"/executeApprove"})
public class executeApprove extends HttpServlet {

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
        String id = request.getParameter("id");

        // create a session object if it is already not created
        HttpSession session = request.getSession(true);
        
        if ((session.getAttribute("isAdmin") == null) || (session.getAttribute("isAdmin") == "")) {
            response.sendRedirect("./index.jsp");
          }
        else{ // the user is admin
            try {
                // setting connection & statement vars
                Connection conn = null;
                Statement stmt = null;

               

                // Open a connection
                System.out.println("Connecting to a selected database...");
                 conn = DBConnection.getDBConnection();
                System.out.println("Connected database successfully...");

               
                // updating user's approve state
                System.out.println("approving user with id: " + id);
                stmt = conn.createStatement();
                String sql = "update account set approved=1 where id=" + id;
                System.out.println("executing query:: " + sql);
                stmt.executeUpdate(sql);

                // clean up environment
                stmt.close(); // ps: statement for conn connection
                conn.close(); // conn: connection on tedprojectdatabase;
            } catch (Exception e2) {
                out.print("<h1>Approvement failed::" + e2 + "</h1>");
                System.out.println(e2);
           } finally{
                response.sendRedirect("./admin.jsp");
           }
        } // the user was admin
        
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
