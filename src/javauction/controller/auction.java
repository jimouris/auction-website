package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.service.AuctionService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gpelelis on 4/7/2016.
 */
@WebServlet(name = "auction")
public class auction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action").equals("addNew")){
            // get the user input
            String Name = request.getParameter("name");
            String Description = request.getParameter("description");
            float LowestBid = Float.parseFloat(request.getParameter("lowestBid"));
            String startToday = request.getParameter("startToday");
            int activeDays = Integer.parseInt(request.getParameter("activeDays"));
            String Country = request.getParameter("country");
            String City = request.getParameter("city");
            String instantBuy = request.getParameter("instantBuy");
            /* get userid from session. userid will be sellerid for this specific auction! */
            HttpSession session = request.getSession();
            long userId = (long) session.getAttribute("uid");

            // find out if we can sell this auction instantly
            float buyPrice = -1;
            if (instantBuy.equals("true")){
                buyPrice = Float.parseFloat(request.getParameter("buyPrice"));
            }

            // the auction will start now, so we have to find the current date
            java.sql.Date startDate = null;
            java.sql.Date endDate = null;
            byte isStarted = 0;
            if (startToday.equals("true")){
                java.util.Date currentDate = new java.util.Date(System.currentTimeMillis());
                startDate = new java.sql.Date(currentDate.getTime());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(String.valueOf(startDate)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.add(Calendar.DATE, activeDays);  // number of days to add
                endDate = Date.valueOf(sdf.format(c.getTime()));  // dt is now the new date

                isStarted = 1;
            }

            AuctionEntity auction = new AuctionEntity(Name, Description, LowestBid, Country, City, buyPrice, startDate, isStarted, endDate, userId);

            // tell the service to add a new auction
            try {
                AuctionService auctionService = new AuctionService();
                boolean result = auctionService.addAuction(auction);

                if (result) {
                    System.out.println("success" + auction);
                } else {
                    System.out.println("failure" + auction);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

//            RequestDispatcher view = request.getRequestDispatcher(next_page);
//            view.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("action").equals("ViewAllAuctions")) {
            List AuctionLst;
            String next_page = "/listAuctions.jsp";
            AuctionService auctionService = new AuctionService();
            AuctionLst = auctionService.getAllAuctions(33);

//            HttpSession session = request.getSession();
//            long userID = (long) session.getAttribute("uid");
//            System.out.println("MPOMOPADASDAS "+userID);
            // TODO: 7/6/16 !!! replace 33 with SellerID !!!

            request.setAttribute("AuctionLst", AuctionLst);

            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }

    }
}
