

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import myPackage.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author hostAdmin
 */
@WebServlet(urlPatterns = {"/executeNewAuction"})
public class executeNewAuction extends HttpServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        out.println("<title>New Auction Status</title>");
        out.println("<link href='./css/skeleton.css' rel='stylesheet'");
        out.println("</head>");
        out.println("<body>");
       
        // assign post data to vars
        String name = request.getParameter("aucName");
        String instantBuy = request.getParameter("aucInstantBuy");
        String buy_price = request.getParameter("aucBuyPrice");
        String first_bid = request.getParameter("aucFirstBid");
        String city = request.getParameter("aucCity");
        String country = request.getParameter("aucCountry");
        String description = request.getParameter("aucDescription");
        String[] categories = request.getParameterValues("aucCategory");

        // get buyPrice and firstBid as integers
        int buyPrice = 0;
        int firstBid = 0;
        int category1 = 0;
        if(buy_price !=null && !buy_price.isEmpty())
            buyPrice=Integer.parseInt(buy_price.trim());
        if(first_bid !=null && !first_bid.isEmpty())
            firstBid = Integer.parseInt(first_bid.trim());
       
        // converting category id string into id
        int[] category = new int[categories.length];
        for(int i=0; i < categories.length; i++)
            category[i] = Integer.parseInt(categories[i]);

        try {
            // setting connection & statement vars
            Connection conn = null;
            PreparedStatement stmt = null;

            // Open a connection
            conn = DBConnection.getDBConnection();
            

            // execute a query to simply insert an item (no linking with user etc)
            System.out.println("Inserting a new item for auction ...");
            PreparedStatement psmtm = conn.prepareStatement(
                    "insert into auction (title, city, country, description, buyPrice, isInstantBuy, startingPrice,startdate,enddate,seller,isActive) values(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);


            // the user has set a price for instant buy.
            if(instantBuy.equals("1")){
                psmtm.setDouble(5, buyPrice); // set the buy price > 0
                psmtm.setBoolean(6, true); // set instantBuy true
            }
            else{
                psmtm.setInt(5, -1); // buyPrice is < 0
                psmtm.setBoolean(6, false); // set instantBuy false
            }
                
            psmtm.setString(1, name);            
            psmtm.setString(2, city); 
            psmtm.setString(3,  country); 
            psmtm.setString(4, description);
            psmtm.setDouble(7, firstBid);
            psmtm.setDate(8, null);
            psmtm.setDate(9, null);
            psmtm.setInt(10, (int)session.getAttribute("userId"));
            psmtm.setBoolean(11, false);
          
           

            int i = psmtm.executeUpdate();
            
            int auction_id = -1;
            // get the id of the newly created auction
            // to be used on a following link and map categories
            try (ResultSet result = psmtm.getGeneratedKeys()) {
                if (result.next()) {
                    auction_id = result.getInt(1);
                    for(i = 0; i < category.length; i++){
                        psmtm = conn.prepareStatement("insert into categories (auction, category) values(?,?)");

                        psmtm.setInt(1, auction_id);
                        psmtm.setInt(2, category[i]);
                        psmtm.executeUpdate();
                    }
                }
                else {
                    throw new SQLException("Creating auction failed, no ID obtained.");
                }
            }

            // insert auction was succesfull
            if (i > 0) {
                out.println("<div class=\"container\">"
                        + "<div class=\"five columns row u-center u-center-text\">"
                        + "<h1 >Auction created successfully</h1>"
                        + "<a href=./auctionInfo.jsp?id=" + auction_id + " class=\"button button-primary\">View your auction</a> "
                        + "<br /></h1> Get back to <a href=./welcome.jsp>home</a>");
            } 

            // clean up environment
            psmtm.close(); // ps: statement for conn connection
            conn.close(); // conn: connection on tedprojectdatabase;
        } catch (Exception e2){
            out.print("<h1>New auction failed::" + e2.getMessage() + "</h1>");
            System.out.println(e2);
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
