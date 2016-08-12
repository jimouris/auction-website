package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.model.MessagesEntity;
import javauction.model.RatingEntity;
import javauction.model.UserEntity;
import javauction.service.AuctionService;
import javauction.service.MessagesService;
import javauction.service.RatingService;
import javauction.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimouris on 8/12/16.
 */
public class rate extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RatingService ratingService = new RatingService();
        String next_page = "/user/homepage.jsp";
        HttpSession session = request.getSession();

        long from_id = ((UserEntity) session.getAttribute("user")).getUserId();
        long to_id = Long.parseLong(request.getParameter("to_id"));
        long aid = Long.parseLong(request.getParameter("aid"));
        if (request.getParameter("action").equals("addRating")) {
            int rating = Integer.parseInt(request.getParameter("rating"));

            RatingEntity ratingEntity = new RatingEntity(from_id, to_id, aid, rating);
            ratingService.addEntity(ratingEntity);

            request.setAttribute("aid", aid);
            request.setAttribute("to_id", to_id);
            request.setAttribute("from_id", from_id);
            request.setAttribute("rating", rating);
            next_page = "/user/rating.jsp";
        } else if (request.getParameter("action").equals("updateRating")) {
            int rating = Integer.parseInt(request.getParameter("rating"));

            ratingService.updateRating(from_id, to_id, aid, rating);

            request.setAttribute("aid", aid);
            request.setAttribute("to_id", to_id);
            request.setAttribute("from_id", from_id);
            request.setAttribute("rating", rating);
            next_page = "/user/rating.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = null;
        String param = request.getParameter("action");
        HttpSession session = request.getSession();
        RatingService ratingService = new RatingService();

        long to_id, from_id, aid;
        from_id = ((UserEntity) session.getAttribute("user")).getUserId();
        switch (param) {
            case "getRating":
                to_id = Long.parseLong(request.getParameter("to_id"));
                aid = Long.parseLong(request.getParameter("aid"));
                Integer rating = ratingService.getRating(from_id, to_id, aid).getRating();

                request.setAttribute("aid", aid);
                request.setAttribute("to_id", to_id);
                request.setAttribute("from_id", from_id);
                request.setAttribute("rating", rating);
                next_page = "/user/rating.jsp";
                break;
            case "listTo":
                List<RatingEntity> ratingsLst = ratingService.getFromOrToRatings(from_id, RatingService.Rating_t.From_t);
                List<UserEntity> receiversLst = new ArrayList<>();
                List<AuctionEntity> auctionsLst = new ArrayList<>();
                UserService userService = new UserService();
                AuctionService auctionService = new AuctionService();

                for (RatingEntity r : ratingsLst) {
                    receiversLst.add(userService.getUser(r.getToId()));
                    auctionsLst.add(auctionService.getAuction(r.getAuctionId()));
                }

                request.setAttribute("ratingsLst", ratingsLst);
                request.setAttribute("receiversLst", receiversLst);
                request.setAttribute("auctionsLst", auctionsLst);
                next_page = "/user/listYourSubmittedRatings.jsp";
                break;
            case "listFrom":
                ratingsLst = ratingService.getFromOrToRatings(from_id, RatingService.Rating_t.To_t);
                List<UserEntity> sendersLst = new ArrayList<>();
                auctionsLst = new ArrayList<>();
                userService = new UserService();
                auctionService = new AuctionService();

                for (RatingEntity r : ratingsLst) {
                    sendersLst.add(userService.getUser(r.getFromId()));
                    auctionsLst.add(auctionService.getAuction(r.getAuctionId()));
                }

                request.setAttribute("ratingsLst", ratingsLst);
                request.setAttribute("sendersLst", sendersLst);
                request.setAttribute("auctionsLst", auctionsLst);
                next_page = "/user/listYourReceivedRatings.jsp";
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

}
