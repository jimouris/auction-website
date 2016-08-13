package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.model.BidEntity;
import javauction.model.CategoryEntity;
import javauction.model.UserEntity;
import javauction.service.AuctionService;
import javauction.service.CategoryService;
import javauction.service.RatingService;
import javauction.service.UserService;

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
import java.util.*;

/**
 * Created by gpelelis on 4/7/2016.
 */
@WebServlet(name = "auction")
public class auction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuctionService auctionService = new AuctionService();
        CategoryService categoryService = new CategoryService();
        String next_page = "/user/homepage.jsp";

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
            String[] categoriesParam = request.getParameterValues("categories");
            /* get userid from session. userid will be sellerid for this specific auction! */
            HttpSession session = request.getSession();
            long userId = ((UserEntity) session.getAttribute("user")).getUserId();

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
                // map the auction with the specified categories
                CategoryEntity category;
                Set<CategoryEntity> categories = new HashSet<>();
                for (String cid : categoriesParam){
                    category = categoryService.getCategory(Integer.parseInt(cid));
                    categories.add(category);
                }
                auction.setCategories(categories);

                /* if auction submitted successfully */
                auctionService.addEntity(auction);
                request.setAttribute("aid", auction.getAuctionId());
                next_page = "/user/auctionSubmit.jsp";

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("activateAuction")){
            response.setContentType("text/html");

            AuctionEntity auction;
            long aid = Long.parseLong(request.getParameter("aid"));

            // retrieve auction's info
            try {
                auctionService.activateAuction(aid, true);
                auction = auctionService.getAuction(aid);

                /* all categories */
                List categoryLst = categoryService.getAllCategories();
                request.setAttribute("categoryLst", categoryLst);
                /* Auctions selected categories*/
                Set <CategoryEntity> cats = auction.getCategories();
                List<CategoryEntity> usedCategories = new ArrayList<>();
                for (CategoryEntity c : cats){
                    usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                }
                request.setAttribute("usedCategories", usedCategories);
                long sid = auction.getSellerId();
                UserService userService = new UserService();
                UserEntity seller = userService.getUser(sid);
                RatingService ratingService = new RatingService();
                Double avg_rating = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);
                request.setAttribute("avg_rating", avg_rating);
                request.setAttribute("seller", seller);
                request.setAttribute("auction", auction);
            } catch (Exception e) {
                e.printStackTrace();
            }
            next_page = "/public/auctionInfo.jsp";
        } else if (request.getParameter("action").equals("updateAuction")) {
            String name = request.getParameter("name");
            String desc = request.getParameter("description");
            Double lowestBid = Double.parseDouble(request.getParameter("lowestBid"));
            Double finalPrice = Double.parseDouble(request.getParameter("finalPrice"));
            Double buyPrice = Double.parseDouble(request.getParameter("buyPrice"));
            String city = request.getParameter("city");
            String country = request.getParameter("country");
            Date startingDate = Date.valueOf(request.getParameter("startingDate"));
            Date endingDate = Date.valueOf(request.getParameter("endingDate"));
            long aid = Long.parseLong(request.getParameter("aid"));
            String[] categoriesParam = request.getParameterValues("categories");

            try {
                // map the auction with the specified categories
                Set<CategoryEntity> categories = null;
                if (categoriesParam != null) {
                    CategoryEntity category;
                    categories = new HashSet<>();
                    for (String cid : categoriesParam) {
                        category = categoryService.getCategory(Integer.parseInt(cid));
                        categories.add(category);
                    }
                }
                auctionService.updateAuction(categories, aid, name, desc, lowestBid, finalPrice, buyPrice, city, country, startingDate, endingDate, null);

                AuctionEntity auction = auctionService.getAuction(aid);
                request.setAttribute("auction", auction);
                /* all categories */
                List categoryLst = categoryService.getAllCategories();
                request.setAttribute("categoryLst", categoryLst);
                /* Auctions selected categories*/
                Set <CategoryEntity> cats = auction.getCategories();
                List<CategoryEntity> usedCategories = new ArrayList<>();
                for (CategoryEntity c : cats){
                    usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                }
                request.setAttribute("usedCategories", usedCategories);
                /* get the highest bid */
                Set <BidEntity> allBids = auction.getBids();
                List<BidEntity> bidLst = new ArrayList<>();
                List<UserEntity> biddersLst = new ArrayList<>();
                UserService userService = new UserService();
                for (BidEntity b : allBids){
                    bidLst.add(b);
                    biddersLst.add(userService.getUser(b.getBidderId()));
                }
                request.setAttribute("bidLst", bidLst);
                request.setAttribute("biddersLst", biddersLst);
                /* if auction has ended */

                Long buyerid = null;
                if (biddersLst.size() > 0) {
                    buyerid = (Long) biddersLst.get(0).getUserId();
                }
                auction = checkDateAndSetBuyer(request, auction, aid, buyerid, auctionService);
                long sid = auction.getSellerId();
                UserEntity seller = userService.getUser(sid);
                request.setAttribute("seller", seller);
                RatingService ratingService = new RatingService();
                Double avg_rating = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);
                request.setAttribute("avg_rating", avg_rating);
                request.setAttribute("auction", auction);
                next_page = "/public/auctionInfo.jsp";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("deleteAuction")) {
            response.setContentType("text/html");

            try {
                long aid = Long.parseLong(request.getParameter("aid"));
                auctionService.deleteAuction(aid);
                next_page = "/user/homepage.jsp";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("bidAuction")) {
            float amount = Float.parseFloat(request.getParameter("bid"));
            long aid = Long.parseLong(request.getParameter("aid"));
            HttpSession session = request.getSession();
            long uid = ((UserEntity) session.getAttribute("user")).getUserId();

            BidEntity bid = new BidEntity(uid, aid, amount);
            auctionService.addEntity(bid);

            AuctionEntity auction = auctionService.getAuction(aid);
            request.setAttribute("auction", auction);
            /* Auctions selected categories*/
            Set <CategoryEntity> cats = auction.getCategories();
            List<CategoryEntity> usedCategories = new ArrayList<>();
            for (CategoryEntity c : cats){
                usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
            }
            request.setAttribute("usedCategories", usedCategories);
            /* get the highest bid */
            Set <BidEntity> allBids = auction.getBids();
            List<BidEntity> bidLst = new ArrayList<>();
            List<UserEntity> biddersLst = new ArrayList<>();
            UserService userService = new UserService();
            for (BidEntity b : allBids){
                bidLst.add(b);
                biddersLst.add(userService.getUser(b.getBidderId()));
            }
            request.setAttribute("bidLst", bidLst);
            request.setAttribute("biddersLst", biddersLst);
            /* if auction has ended */
            Long buyerid = null;
            if (biddersLst.size() > 0) {
                buyerid = (Long) biddersLst.get(0).getUserId();
            }
            auction = checkDateAndSetBuyer(request, auction, aid, buyerid, auctionService);
            long sid = auction.getSellerId();
            UserEntity seller = userService.getUser(sid);
            request.setAttribute("seller", seller);
            RatingService ratingService = new RatingService();
            Double avg_rating = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);
            request.setAttribute("avg_rating", avg_rating);
            request.setAttribute("auction", auction);
            next_page = "/public/auctionInfo.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = null;
        String param = request.getParameter("action");
        AuctionService auctionService = new AuctionService();
        HttpSession session = request.getSession();
        CategoryService categoryService = new CategoryService();
        List categoryLst = categoryService.getAllCategories();
        switch (param) {
            case "getAllAuctions": /* get all actions with sellerId = uid (from session) */
                long uid = ((UserEntity) session.getAttribute("user")).getUserId();
                List auctionLst = auctionService.getAllAuctions(uid, false);
                request.setAttribute("auctionLst", auctionLst);
                next_page = "/public/listAuctions.jsp";
                break;
            case "getAllActiveAuctions": /* get all active auctions, all sellers */
                request.setAttribute("auctionLst", auctionService.getAllAuctions(-1, true));
                next_page = "/public/listAuctions.jsp";
                break;
            case "newAuction": /* gather all categories to display on jsp */
                request.setAttribute("categoryLst", categoryLst);
                next_page = "/user/newAuction.jsp";
                break;
            case "getAnAuction": /* get an auction with auctionId = aid */
                long aid = Long.parseLong(request.getParameter("aid"));
                AuctionEntity auction = auctionService.getAuction(aid);
                UserEntity user = (UserEntity) session.getAttribute("user");

                /* get seller id for the auction */
                long sid = auction.getSellerId();
                /* Guest session */
                if (user == null) {
                    session.setAttribute("isSeller", false);
                } else {
                    session.setAttribute("isSeller", sid == user.getUserId());
                }

                /* all categories */
                request.setAttribute("categoryLst", categoryLst);
                /* Auctions selected categories*/
                Set <CategoryEntity> cats = auction.getCategories();
                List<CategoryEntity> usedCategories = new ArrayList<>();
                for (CategoryEntity c : cats){
                    usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                }
                request.setAttribute("usedCategories", usedCategories);
                /* get the highest bid */
                Set <BidEntity> allBids = auction.getBids();
                List<BidEntity> bidLst = new ArrayList<>();
                List<UserEntity> biddersLst = new ArrayList<>();
                UserService userService = new UserService();
                for (BidEntity b : allBids){
                    bidLst.add(b);
                    biddersLst.add(userService.getUser(b.getBidderId()));
                }
                request.setAttribute("bidLst", bidLst);
                request.setAttribute("biddersLst", biddersLst);
                /* if auction has ended */
                Long buyerid = null;
                if (biddersLst.size() > 0) {
                    buyerid = (Long) biddersLst.get(0).getUserId();
                }
                auction = checkDateAndSetBuyer(request, auction, aid, buyerid, auctionService);
                UserEntity seller = userService.getUser(sid);
                RatingService ratingService = new RatingService();
                Double avg_rating = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);
                request.setAttribute("avg_rating", avg_rating);
                request.setAttribute("seller", seller);
                request.setAttribute("auction", auction);
                next_page = "/public/auctionInfo.jsp";
                break;
            case "getAllYourEndedAuctions":
                uid = (Long) ((UserEntity) session.getAttribute("user")).getUserId();
                auctionLst = auctionService.getAllEndedAuctions(uid, true);
                request.setAttribute("auctionLst", auctionLst);
                next_page = "/public/listAuctions.jsp";
                break;
            case "getAllEndedAuctions":
                auctionLst = auctionService.getAllEndedAuctions(null, false);
                request.setAttribute("auctionLst", auctionLst);
                next_page = "/public/listAuctions.jsp";
                break;
            case "getAuctionsYouHaveBought":
                uid = (Long) ((UserEntity) session.getAttribute("user")).getUserId();
                auctionLst = auctionService.getAllEndedAuctions(uid, false);
                request.setAttribute("auctionLst", auctionLst);
                next_page = "/public/listAuctions.jsp";
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    /* check if auction has ended and set the related fields */
    private AuctionEntity checkDateAndSetBuyer(HttpServletRequest request, AuctionEntity auction, long aid, Long buyerId, AuctionService auctionService) {
        Date currentDate = new Date(System.currentTimeMillis());
        if (auction.getEndingDate().before(currentDate)) {
            request.setAttribute("isEnded", true);
            auctionService.activateAuction(aid, false);
            auction.setIsStarted((byte) 0);
            if (buyerId != null) {
                auctionService.updateAuction(null, aid, null, null, null, null, null, null, null, null, null, buyerId);
                auction.setBuyerId(buyerId);
            }
        } else {
            request.setAttribute("isEnded", false);
        }
        return auction;
    }

}
