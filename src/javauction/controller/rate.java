package javauction.controller;

import javauction.model.RatingEntity;
import javauction.model.UserEntity;
import javauction.service.RatingService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jimouris on 8/12/16.
 */
public class rate extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RatingService ratingService = new RatingService();
        String next_page = "/user/homepage.jsp";
        HttpSession session = request.getSession();

        long to_id, from_id, aid;
        if (request.getParameter("action").equals("addRating")) {
            from_id = ((UserEntity) session.getAttribute("user")).getUserId();
            to_id = Long.parseLong(request.getParameter("to_id"));
            aid = Long.parseLong(request.getParameter("aid"));
            int rating = Integer.parseInt(request.getParameter("rating"));

            RatingEntity ratingEntity = new RatingEntity(from_id, to_id, aid, rating);
            ratingService.addEntity(ratingEntity);

            request.setAttribute("aid", aid);
            request.setAttribute("to_id", to_id);
            request.setAttribute("from_id", from_id);
            next_page = "/user/rating.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = null;
        String param = request.getParameter("action");
        HttpSession session = request.getSession();

        long to_id, from_id, aid;
        switch (param) {
            case "getRating": /* get all actions with sellerId = uid (from session) */
                to_id = Long.parseLong(request.getParameter("to_id"));
                from_id = ((UserEntity) session.getAttribute("user")).getUserId();
                aid = Long.parseLong(request.getParameter("aid"));

                RatingService ratingService = new RatingService();
                Integer rating = ratingService.getRating(from_id, to_id, aid);

                request.setAttribute("aid", aid);
                request.setAttribute("to_id", to_id);
                request.setAttribute("from_id", from_id);
                request.setAttribute("rating", rating);
                next_page = "/user/rating.jsp";
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

}
