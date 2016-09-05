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
import java.util.Map;

/**
 * Created by jimouris on 7/11/16.
 */
public class search extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");
            if (action.equals("doAdvancedSearch") || action.equals("doSimpleSearch")) {
                // PostRedirectGet pattern
                String url = constructURL(request);
                response.sendRedirect(url + "&page=0");
                return;
            }
        }

        // if user has provided another action, then reroute him
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";
        if (request.getParameter("action") != null) {

            if (request.getParameter("action").equals("advancedSearch")) {
            /* gather all categories to display on jsp */
                CategoryService categoryService = new CategoryService();
                List categoryLst = categoryService.getAllCategories();
                request.setAttribute("categoriesLst", categoryLst);

                next_page = "/public/customSearch.jsp";
            } else if (request.getParameter("action").equals("doAdvancedSearch")) {
                int page = 0;
                if (request.getParameterMap().containsKey("page")) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
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
                List<AuctionEntity> auctionsLst = auctionService.searchAuction(categories, description, minPrice, maxPrice, location, page);

                // construct url for next and previous page
                constructPrevNext(page, request);

                request.setAttribute("auctionsLst", auctionsLst);
                next_page = "/public/searchResults.jsp";
            } else if (request.getParameter("action").equals("doSimpleSearch")) {
                String name = request.getParameter("name");
                int page = 0;
                if (request.getParameterMap().containsKey("page")) {
                    page = Integer.parseInt(request.getParameter("page"));
                }

                // get the data to display
                AuctionService auctionService = new AuctionService();
                List<AuctionEntity> auctionsLst = auctionService.searchAuction(name, page);

                // construct url for next and previous page
                constructPrevNext(page, request);

                request.setAttribute("auctionsLst", auctionsLst);
                next_page = "/public/searchResults.jsp";
            }
        }
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    // sets attributes thta will be passed to searchResults jsp
    // in order to show previous and next page links on search result
    private void constructPrevNext(int page, HttpServletRequest request) {
        String next, previous;
        next = constructURL(request) + "&page=";
        if (page > 0){
            previous = next + (page - 1);
            request.setAttribute("previousPage", previous);
        }
        next = next + (page + 1); // don't change the order of this. previous will haave wrong initial string
        request.setAttribute("nextPage", next);
    }

    // i want to return something like auction.do?action=simpleSearch&name=&page=
    private String constructURL(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        String url = "search.do?";
        for (Map.Entry<String, String[]> entry : params.entrySet())
        {
            // page param will be self assigned, so we have to skip it
            if (!entry.getKey().equals("page")) {
                String[] values = entry.getValue();
                for (String value : values) {
                    url = url + entry.getKey() + "=";
                    url = url + value + "&";
                }
            }
        }
        // cut the last & that iteration adds
        url = url.substring(0, url.length()-1);
        return url;
    }
}
