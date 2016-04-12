

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
import javax.servlet.http.HttpSession;


/**
 *
 * @author hostAdmin
 */
@WebServlet(urlPatterns = {"/executeLogin"})
public class executeLogin extends HttpServlet {

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
        out.println("<title>Login status</title>");
        out.println("</head>");
        out.println("<body>");
       
       

        // assign post data to vars
        String username = request.getParameter("loginUsername");
        String pass = request.getParameter("loginPassword");
    
        // get caller's url
        String ref = request.getHeader("Referer"); 

        // set things if coming as admin
        boolean admin = false;
        String table = "user"; // the table to be used
        if (ref.indexOf("backoffice") > 0){
            admin = true;
            table = "admin";
        }


        try {
            Connection conn = null;
            Statement stmt =  null;

            // Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DBConnection.getDBConnection();
            System.out.println("Connected database successfully...");

            // execute sql query
            stmt = conn.createStatement();
            String sql;
            if(admin){
                sql = "select username, password, id from admin" ;
            } else{
                sql = "select username, password, account from user" ;
            }
            ResultSet rs = stmt.executeQuery(sql);

            
            int id = -1;
            boolean exist = false;
            // check if Username exists
            while(rs.next()){
                String dbUser = rs.getString("username");
                String dbPass = rs.getString("password");
                // out.println("<h4>dbUser: " + dbUser + " ,postMail: " + mail + "</h4>");
                if (dbUser.equals(username) && dbPass.equals(pass)){
                    if(admin)
                        id = rs.getInt("id");
                    else
                        id = rs.getInt("account");
                    exist = true;
                    break;
                }
            }
            
            if (exist && admin){ // admin
                // Set response content type
                response.setContentType("text/html");

                // start a session and send to admin's panel
                HttpSession session = request.getSession(true);
                session.setAttribute("isAdmin","true");
                response.sendRedirect("./admin.jsp");
            } else if (exist){ // regular user
                // Set response content type
                response.setContentType("text/html");

                // start a session and set isUser = user's id
                HttpSession session = request.getSession(true);
                session.setAttribute("isUser",id);
                session.setAttribute("userId",id);
                response.sendRedirect("./welcome.jsp");
            }
            else{
                out.print("<h1>User " + username + " is not registered</h1>");
            }
            

            out.println("<a href='./index.jsp'>To homepage</a>");
            out.println("</body>");
            out.println("</html>");

            // clean up environment
            rs.close(); // rs: results of query
            stmt.close(); // stmt: statement for con connection
            conn.close(); // con: connection on tedprojectdatabase;
        } catch (Exception e){
           out.print("<h1>Login failed::" + e + "</h1>");
                System.out.println(e);
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
