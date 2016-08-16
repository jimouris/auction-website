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
            String name = request.getParameter("name"); /* required */
            String description = request.getParameter("description"); /* required */
            float lowestBid = Float.parseFloat(request.getParameter("lowestBid")); /* required */
            String startToday = request.getParameter("startToday"); /* always sent by default */
            int activeDays = Integer.parseInt(request.getParameter("activeDays")); /* optional */
            String location = request.getParameter("location");  /* required */
            String country = request.getParameter("country");  /* required */
            String instantBuy = request.getParameter("instantBuy"); /* always sent by default */
            /* find out if we can sell this auction instantly */
            float buyPrice = -1;
            if (instantBuy.equals("true")){
                buyPrice = Float.parseFloat(request.getParameter("buyPrice"));
            }
            /* get userid from session. userid will be sellerid for this specific auction! */
            HttpSession session = request.getSession();
            long sellerId = ((UserEntity) session.getAttribute("user")).getUserId();

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

            // create auction entity with the required value
            AuctionEntity auction = new AuctionEntity(name, sellerId, description, lowestBid, location, country, buyPrice, startDate, isStarted, endDate);

            /* if google maps returned precise location */
            if (request.getParameterMap().containsKey("longitude") && request.getParameterMap().containsKey("latitude")) {
                Double longitude = Double.valueOf(request.getParameter("longitude"));  /* optional */
                Double latitude = Double.valueOf(request.getParameter("latitude")); /* optional */
                auction.setLongitude(longitude);
                auction.setLatitude(latitude);
            }
            /* if seller selected categories */
            if (request.getParameterMap().containsKey("categories")) {
                String[] categoriesParam = request.getParameterValues("categories"); /* required */
                // map the auction with the specified categories
                CategoryEntity category;
                Set<CategoryEntity> categories = new HashSet<>();
                for (String cid : categoriesParam) {
                    category = categoryService.getCategory(Integer.parseInt(cid));
                    categories.add(category);
                }
                auction.setCategories(categories);
            }

            /* by now all the data should be ok */
            try {
                auctionService.addEntity(auction);
                request.setAttribute("aid", auction.getAuctionId());
                next_page = "/user/auctionSubmit.jsp";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (request.getParameter("action").equals("activateAuction")){
            AuctionEntity auction;
            long aid = Long.parseLong(request.getParameter("aid"));
            Date endingDate = Date.valueOf(request.getParameter("endingDate"));
            Boolean status;
            try {
                auctionService.activateAuction(aid, endingDate, true);
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
                status = false; // @jimouris edw tha trexei ama skasei, h' vlakeia egraya?
            }

            response.sendRedirect("/auction.do?action=getAnAuction&aid="+aid+"&from=activate&status="+status);
            return;
        } else if (request.getParameter("action").equals("updateAuction")) {
            Boolean status = false;
            String name = request.getParameter("name");
            String desc = request.getParameter("description");
            Double lowestBid = null;
            if (request.getParameterMap().containsKey("lowestBid"))
                lowestBid = Double.parseDouble(request.getParameter("lowestBid"));
            Double buyPrice = null;
            if (request.getParameterMap().containsKey("buyPrice")) {
                Boolean set_buyPrice = request.getParameter("buyPrice").isEmpty();
                System.out.println(set_buyPrice + request.getParameter("buyPrice"));
                buyPrice = set_buyPrice ? -1 : Double.parseDouble(request.getParameter("buyPrice"));
            }
            Double latitude = null;
            if (request.getParameterMap().containsKey("latitude"))
                latitude = Double.valueOf(request.getParameter("latitude")); /* optional */
            Double longitude = null;
            if (request.getParameterMap().containsKey("longitude"))
                longitude = Double.valueOf(request.getParameter("longitude"));  /* optional */
            String location = request.getParameter("location");
            String country = request.getParameter("country");
            Date startingDate = null;
            if (request.getParameterMap().containsKey("startingDate"))
                startingDate = Date.valueOf(request.getParameter("startingDate"));
            Date endingDate = null;
            if (request.getParameterMap().containsKey("endingDate"))
                endingDate = Date.valueOf(request.getParameter("endingDate"));
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
                auctionService.updateAuction(categories, aid, name, desc, lowestBid,  buyPrice, location, country, startingDate, endingDate, null, latitude, longitude);
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("/auction.do?action=getAnAuction&aid="+aid+"&from=update&status="+status);
            return;
        } else if (request.getParameter("action").equals("deleteAuction")) {
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

            Boolean status = false;
            try {
                BidEntity bid = new BidEntity(uid, aid, amount);
                auctionService.addEntity(bid);
                status = true;
            } catch (Exception e) {
                e.printStackTrace();
                status = false; // @jimouris edw tha trexei ama skasei, h' vlakeia egraya?
            }

            response.sendRedirect("/auction.do?action=getAnAuction&aid="+aid+"&from=bid&status="+status);
            return;
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

        /* assign successMsg or errorMsg to request*/
        createMsg(request, response);

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

                /* Auctions selected categories*/
                Set <CategoryEntity> cats = auction.getCategories();
                List<CategoryEntity> usedCategories = new ArrayList<>();
                for (CategoryEntity c : cats){
                    usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                }
                /* get the highest bid */
                Set <BidEntity> allBids = auction.getBids();
                List<BidEntity> bidLst = new ArrayList<>();
                List<UserEntity> biddersLst = new ArrayList<>();
                UserService userService = new UserService();
                for (BidEntity b : allBids){
                    bidLst.add(b);
                    biddersLst.add(userService.getUser(b.getBidderId()));
                }

                /* if auction has ended */
                Long buyerid = null;
                if (biddersLst.size() > 0) {
                    buyerid = (Long) biddersLst.get(0).getUserId();
                }
                auction = checkDateAndSetBuyer(request, auction, aid, buyerid, auctionService);
                UserEntity seller = userService.getUser(sid);
                RatingService ratingService = new RatingService();
                Double avg_rating = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);

                request.setAttribute("usedCategories", usedCategories);
                request.setAttribute("auction", auction);
                request.setAttribute("seller", seller);
                request.setAttribute("biddersLst", biddersLst);
                request.setAttribute("bidLst", bidLst);
                request.setAttribute("avg_rating", avg_rating);
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
            case "editAuction":
                aid = Long.parseLong(request.getParameter("aid"));
                auction = auctionService.getAuction(aid);
                uid = (Long) ((UserEntity) session.getAttribute("user")).getUserId();
                if (auction.getSellerId() != uid) {
                    System.out.println("someone tried to edit an auction without the right credentials");
                    next_page = "/user/homepage.jsp";
                } else {
                    /* Auctions selected categories*/
                    cats = auction.getCategories();
                    usedCategories = new ArrayList<>();
                    for (CategoryEntity c : cats){
                        usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                    }
                    /* all categories */
                    request.setAttribute("usedCategories", usedCategories);
                    request.setAttribute("categoryLst", categoryLst);
                    request.setAttribute("auction", auction);
                    next_page = "/user/auctionEdit.jsp";
                }
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    /* Assigns successMsg or errorMsg on request object
     * if we know where the request came from and we have a status(true||false)
     * then we can assign proper messages that can be passed to JSPs
     * 1: BE AWARE OF THE HACK: It seems that chrome doesn't send referer header on refresh or when user is manually
     * writing the url. So by using this we will set msg attr only when we come from a redirect or a link
     **/
    private void createMsg(HttpServletRequest request, HttpServletResponse response) {
        // i want to check if the request is coming from a sendRedirect
        // if the user sends those paramaterers manually from a browser, then strip them out
        String from = null;
        if (request.getParameterMap().containsKey("from")) {
            from = request.getParameter("from");
        }
        String status = null;
        if (request.getParameterMap().containsKey("status") && request.getHeader("referer") != null){ /* 1 */
            status = request.getParameter("status");
            switch (from) {
                case "activate":
                    if (status.equals("true")) request.setAttribute("successMsg", "auction activated successfully");
                    else request.setAttribute("errorMsg", "auction couldn't be activated");
                    break;
                case "delete":
                    if (status.equals("true")) request.setAttribute("successMsg", "auction deleted successfully");
                    else request.setAttribute("errorMsg", "auction couldn't be deleted");
                    break;
                case "bid":
                    if (status.equals("true")) request.setAttribute("successMsg", "You successfully bid the auction.");
                    else request.setAttribute("errorMsg", "Bid couldn't be placed.");
                    break;
                case "update":
                    if (status.equals("true")) request.setAttribute("successMsg", "Updated successfully.");
                    else request.setAttribute("errorMsg", "Couldn't update the auction.");
                    break;
                default:
                    break;
            }
        }
        return;
    }

    /* check if auction has ended and set the related fields */
    private AuctionEntity checkDateAndSetBuyer(HttpServletRequest request, AuctionEntity auction, long aid, Long buyerId, AuctionService auctionService) {
        Date currentDate = new Date(System.currentTimeMillis());
        if(auction.getEndingDate() != null) {
            if (auction.getEndingDate().before(currentDate)) {
                request.setAttribute("isEnded", true);
                auctionService.activateAuction(aid, null, false);
                auction.setIsStarted((byte) 0);
                if (buyerId != null) {
                    auctionService.updateAuction(null, aid, null, null, null, null, null, null, null, null, buyerId, null, null);
                    auction.setBuyerId(buyerId);
                }
            } else {
                request.setAttribute("isEnded", false);
            }
        } else {
                request.setAttribute("isEnded", false);
        }
        return auction;
    }

}
