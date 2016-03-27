

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;


/**
 *
 * @author hostAdmin
 */
@WebServlet(urlPatterns = {"/executeAnswer"})
public class executeAnswer extends HttpServlet {

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
            out.println("<h1>Servlet executeNewAuction at " + request.getContextPath() + "</h1>");
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
    @SuppressWarnings("null")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        

        // create a session object if it is already not created
        HttpSession session = request.getSession(true);
        String userId = String.valueOf(session.getAttribute("isUser"));

        // session:: check if it is a regular user
        if (userId == null || "".equals(userId) ) {
            response.sendRedirect("./index.jsp");
         } 

        response.setContentType("text/html");
       
        // assign post data to vars
        String sender_id = request.getParameter("sender_id");
        String receiver_id = request.getParameter("receiver_id");
        String title = request.getParameter("title");
        String message_text = request.getParameter("message_text");


        // convert sender_id and receiver_id to integers
        int senderid = 0;
        int receiverid = 0;
        if(sender_id !=null && !sender_id.isEmpty())
            senderid = Integer.parseInt(sender_id.trim());
        if(receiver_id !=null && !receiver_id.isEmpty())
            receiverid = Integer.parseInt(receiver_id.trim());

        Connection conn = null;
        PreparedStatement psmtm = null;
        try {
            
            // Open a connection
            conn = DBConnection.getDBConnection();

            // execute a query to add a new message
            psmtm = conn.prepareStatement(
                    "insert into message (messagetext, title) values(?,?)", Statement.RETURN_GENERATED_KEYS);

            psmtm.setString(1, message_text);
            psmtm.setString(2, title);
            int success = psmtm.executeUpdate();
            
            int messageid = -1;
            // get the id of the newly created auction and assign it resutl
            try (ResultSet result = psmtm.getGeneratedKeys()) {
                if (result.next()) {
                    messageid = result.getInt(1);
                    psmtm = conn.prepareStatement("insert into conversations (messageid, senderid, receiverid) values(?,?,?)");
                    psmtm.setInt(1, messageid);
                    psmtm.setInt(2, senderid);
                    psmtm.setInt(3, receiverid);
                    success = psmtm.executeUpdate();
                }
                else {
                    throw new SQLException("Creating messing failed, no ID obtained.");
                }
            }
              
            System.out.println("The id of message is: " + messageid);

            

            // insert auction was succesfull
            if (success > 0) {
                response.sendRedirect("./conversation.jsp?receiver=" + receiver_id);
            } 
        } catch (Exception e2){
            System.out.println(e2);
        } finally {
            try {
                // clean up environment
                psmtm.close(); // stmt: statement for conn connection
                conn.close(); // conn: connection on tedprojectdatabase;
            } catch (SQLException ex) {
                Logger.getLogger(executeAnswer.class.getName()).log(Level.SEVERE, null, ex);
            }
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
