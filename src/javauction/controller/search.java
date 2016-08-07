package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.service.AuctionService;
import javauction.service.CategoryService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by jimouris on 7/11/16.
 */
public class search extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/public";
        if (request.getParameter("action").equals("doAdvancedSearch")) {
            String[] categories = request.getParameterValues("categories");
            double minPrice = 0;
            if (!request.getParameter("minPrice").equals("")) {
                minPrice = Double.parseDouble(request.getParameter("minPrice"));
            }
            double maxPrice = Double.MAX_VALUE;
            if (!request.getParameter("maxPrice").equals("")) {
                maxPrice = Double.parseDouble(request.getParameter("maxPrice"));
            }
            String location = request.getParameter("location");
            String description = request.getParameter("description");
            AuctionService auctionService = new AuctionService();
            List <AuctionEntity> auctionsLst = auctionService.searchAuction(categories, description, minPrice, maxPrice, location);

            request.setAttribute("auctionsLst", auctionsLst);
            next_page = "/public/searchResults.jsp";
        } else if (request.getParameter("action").equals("doSimpleSearch")) {
            String name = request.getParameter("name");

            AuctionService auctionService = new AuctionService();
            List <AuctionEntity> auctionsLst = auctionService.searchAuction(name);

            request.setAttribute("auctionsLst", auctionsLst);
            next_page = "/public/searchResults.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/public";
        if (request.getParameter("action").equals("advancedSearch")) {
            /* gather all categories to display on jsp */
            CategoryService categoryService = new CategoryService();
            List categoryLst = categoryService.getAllCategories();
            request.setAttribute("categoriesLst", categoryLst);

            next_page = "/public/customSearch.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

}
