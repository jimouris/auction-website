package javauction.controller;

import javauction.model.AuctionEntity;
import javauction.model.UserEntity;
import javauction.service.CategoryService;
import javauction.service.SearchService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
            if (action.equals("searchAuctions")) {
                // PostRedirectGet pattern
                String url = constructURL(request);
                response.sendRedirect(url + "&reallyActive=true&page=0");
                return;
            }
        }

        // if user has provided another action, then reroute him
        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";
        HttpSession session = request.getSession();

        if (request.getParameter("action") != null) {
            int page = 0;

            if (request.getParameterMap().containsKey("page")) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            if (request.getParameter("action").equals("advancedSearch")) {
                /* gather all categories to display on jsp */
                CategoryService categoryService = new CategoryService();
                List categoryLst = categoryService.getAllCategories();
                request.setAttribute("categoriesLst", categoryLst);

                next_page = "/public/customSearch.jsp";
            }  else if (request.getParameter("action").equals("searchAuctions")){
                SearchService searchService = new SearchService();
                List<AuctionEntity> auctionsLst;

                // auctions that are activated by user and are on time frame
                if (request.getParameterMap().containsKey("isActive"))
                    searchService.setIsActive(Byte.parseByte(request.getParameter("isActive")));
                // get your auctions
                if (request.getParameterMap().containsKey("boughtBy"))
                    if( request.getParameter("boughtBy").equals("you") )
                        searchService.setBuyerID( ((UserEntity) session.getAttribute("user")).getUserId());
                // auctions that are outside of time frame
                if (request.getParameterMap().containsKey("isEnded"))
                    if( request.getParameter("isEnded").equals("true") )
                        searchService.setIsEnded(true);
                if (request.getParameterMap().containsKey("seller"))
                    if( request.getParameter("seller").equals("you") )
                        searchService.setSellerID( ((UserEntity) session.getAttribute("user")).getUserId());

                /* simple or advanced search */
                if (request.getParameterMap().containsKey("reallyActive"))
                    if (request.getParameter("reallyActive").equals("true"))
                        searchService.setReallyActive(true);
                if (request.getParameterMap().containsKey("name"))
                    searchService.setAuctionName(request.getParameter("name"));
                if (request.getParameterMap().containsKey("categories"))
                    searchService.setCategories(request.getParameterValues("categories"));
                if (request.getParameterMap().containsKey("description"))
                    searchService.setDescription(request.getParameter("description"));
                if (request.getParameterMap().containsKey("location"))
                    searchService.setLocation(request.getParameter("location"));
                searchService.setMinPrice(0d);
                if (request.getParameterMap().containsKey("minPrice"))
                    if (!request.getParameter("minPrice").equals(""))
                    searchService.setMinPrice(Double.parseDouble(request.getParameter("minPrice")));
                searchService.setMaxPrice(Double.MAX_VALUE);
                if (request.getParameterMap().containsKey("maxPrice"))
                    if (!request.getParameter("maxPrice").equals(""))
                    searchService.setMaxPrice(Double.parseDouble(request.getParameter("maxPrice")));

                auctionsLst = searchService.searchAuctions(page);

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
