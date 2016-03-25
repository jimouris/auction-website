

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import myPackage.DBConnection;

/**
 *
 * @author hostAdmin
 */
@WebServlet(urlPatterns = {"/editAuction"})
public class editAuction extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet executeEditAuction</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editAuction at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // create a session object if it is already not created
        HttpSession session = request.getSession(true);

        // session:: check if it is a regular user
        if ((session.getAttribute("isUser") == null) || (session.getAttribute("isUser") == "")) {
            response.sendRedirect("./index.jsp");
          }

        response.setContentType("text/html"); 
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>edit an auction</title>");
        out.println("<link href='./css/skeleton.css' rel='stylesheet'");
        out.println("</head>");
        out.println("<body>");
        
        // assign post data to vars
        String auctionid = request.getParameter("auctionid");
        String mode = request.getParameter("mode");
        String startDate = request.getParameter("date");

        try {
            // setting connection & statement vars
            Connection conn = null;
            Statement stmt = null;
            PreparedStatement psmtm = null;
            String query = "";
            ResultSet resultset = null;
            int i = 0;
            boolean hasDate = false;
            Date date = null;


            // Open a connection
            conn = DBConnection.getDBConnection();


            // security check: compare session's id with the seler
            Boolean isValid = false;
            stmt = conn.createStatement();
            query = "select count(*) as exist from auction where seller=" + session.getAttribute("isUser") + " and id=" + auctionid;
            resultset = stmt.executeQuery(query);

            // check if seller is the same with user id
            // so the user can edit the auction
            resultset.first();
            if(resultset.getInt("exist") == 1)
                isValid = true;
            
            // check if there is a valid date
            // and get a valid date represantation
            // to-do:: check if end date is after start date
            if(startDate != null && startDate != ""){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = (Date) sdf.parse(startDate);
                hasDate = true;
            }


            if (mode.equals("start") && isValid && hasDate){
                query = "UPDATE auction SET startdate=?, isActive=true, enddate=? where id='" + auctionid + "'";
                psmtm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                psmtm.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                psmtm.setTimestamp(2, new java.sql.Timestamp(date.getTime()));

                i = psmtm.executeUpdate();

                if (i > 0) {
                   out.println("<div class=\"container\"><div class=\"five columns row u-center u-center-text\"><h1 >Auction is currently running</h1><a href=./auctions.jsp class=\"button-primary button\"> get back to auctions</a> <br />or get back to <a href=./welcome.jsp>home</a></div></div>");
                    System.out.println("edit of a new auction was succesfull.");
                } 

                // clean up environment
                psmtm.close(); // ps: statement for conn connection
                conn.close(); // conn: connection on tedprojectdatabase;
            } else if( mode.equals("delete") && isValid){
                // to-do : na mpei elegxos gia id xrhsth me getparameter kai session
                query = "delete from auction where id='" + auctionid + "'";
                psmtm = conn.prepareStatement(query);
                // set complete the query and execute it
                i = psmtm.executeUpdate();

                if (i > 0) {
                    out.println("<div class=\"container\"><div class=\"five columns row u-center u-center-text\"><h1 >Auction deleted</h1><a href=./auctions.jsp class=\"button-primary button\"> get back to auctions</a> <br />or go to <a href=./welcome.jsp>home</a></div></div>");
                    System.out.println("succesfull delete on auction.");
                } else{
                    System.out.println("An error occured on deletion.");
                } 

                // clean up environment
                psmtm.close();
                conn.close(); 
            } else if(!hasDate)
                out.println("You didn't provide an end date");
            else{
                out.println("What kind of sorcery did you use?");
            }
        } catch (Exception e2){
            
            out.print("<h1>edit auction failed::" + e2.getMessage() + "</h1>");
            System.out.println(e2);
        } 
        
                
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
