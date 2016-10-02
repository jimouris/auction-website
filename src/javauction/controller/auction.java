package javauction.controller;

import com.sun.tools.javac.util.Pair;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentCollectionConverter;
import com.thoughtworks.xstream.hibernate.converter.HibernatePersistentMapConverter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.mapper.Mapper;
import javauction.model.*;
import javauction.service.AuctionService;
import javauction.service.CategoryService;
import javauction.service.RatingService;
import javauction.service.UserService;
import javauction.util.CategoryXmlUtil;
import javauction.util.UserXmlUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;


@WebServlet(name = "auction")
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB
        maxFileSize=1024*1024*50,       // 50 MB
        maxRequestSize=1024*1024*100)   // 100 MB
public class auction extends HttpServlet {

    /**
     * Directory where uploaded files will be saved, its relative to
     * the web application directory.
     */
    private static final String DIR_TO_UPLOAD = "image_auction";
    private static final String DIR_FOR_XML = "xml";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuctionService auctionService = new AuctionService();
        CategoryService categoryService = new CategoryService();
        String next_page = "/user/homepage.jsp";
        HttpSession session = request.getSession();


        if (request.getParameter("action") == null) {
            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }
        switch (request.getParameter("action")) {
            case "addNew":
                String name = request.getParameter("name"); /* required */
                String description = request.getParameter("description"); /* required */
                Double lowestBid = Double.parseDouble(request.getParameter("lowestBid")); /* required */
                String startToday = request.getParameter("startToday"); /* always sent by default */
                int activeDays = Integer.parseInt(request.getParameter("activeDays")); /* optional */
                String location = request.getParameter("location");  /* required */
                String country = request.getParameter("country");  /* required */
                String instantBuy = request.getParameter("instantBuy"); /* always sent by default */
                /* get userid from session. userid will be sellerid for this specific auction! */
                session = request.getSession();
                long sellerId = ((UserEntity) session.getAttribute("user")).getUserId();

                /* the auction will start now, so we have to find the current date */
                Timestamp startDate = null;
                Timestamp endDate = null;
                byte isActive = 0;
                if (startToday.equals("true")) {
                    startDate = new Timestamp(System.currentTimeMillis());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DAY_OF_WEEK, activeDays);
                    endDate = new Timestamp(cal.getTime().getTime());
                    isActive = 1;
                }

                /* create auction entity with the required value */
                AuctionEntity auction = new AuctionEntity(name, sellerId, description, lowestBid, location, country, startDate, isActive, endDate);

                /* find out if we can sell this auction instantly */
                if (instantBuy.equals("true")) {
                    double buyPrice = Double.parseDouble(request.getParameter("buyPrice"));
                    auction.setBuyPrice(buyPrice);
                }
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
                    /* upload the images */
                    uploadFiles(request, auction.getAuctionId());
                    request.setAttribute("aid", auction.getAuctionId());
                    next_page = "/user/auctionSubmit.jsp";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "activateAuction":
                long aid = Long.parseLong(request.getParameter("aid"));
                Timestamp endingDate = Timestamp.valueOf(request.getParameter("endingDate"));
                Boolean status;
                try {
                    auctionService.activateAuction(aid, endingDate, true);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    status = false;
                }
                response.sendRedirect("/auction.do?action=getAnAuction&aid=" + aid + "&from=activate&status=" + status);
                return;
            case "updateAuction":
                status = false;
                name = request.getParameter("name");
                String desc = request.getParameter("description");
                lowestBid = null;
                if (request.getParameterMap().containsKey("lowestBid")) {
                    lowestBid = Double.parseDouble(request.getParameter("lowestBid"));
                }
                Double buyPrice = null;
                if (request.getParameterMap().containsKey("buyPrice")) {
                    Boolean set_buyPrice = request.getParameter("buyPrice").isEmpty();
                    buyPrice = set_buyPrice ? -1 : Double.parseDouble(request.getParameter("buyPrice"));
                }
                Double latitude = null;
                if (request.getParameterMap().containsKey("latitude")) {
                    latitude = Double.valueOf(request.getParameter("latitude")); /* optional */
                }
                Double longitude = null;
                if (request.getParameterMap().containsKey("longitude")) {
                    longitude = Double.valueOf(request.getParameter("longitude"));  /* optional */
                }
                location = request.getParameter("location");
                country = request.getParameter("country");
                Timestamp startingDate = null;
                if (request.getParameterMap().containsKey("startingDate")) {
                    startingDate = Timestamp.valueOf(request.getParameter("startingDate"));
                }
                endingDate = null;
                if (request.getParameterMap().containsKey("endingDate")) {
                    endingDate = Timestamp.valueOf(request.getParameter("endingDate"));
                }
                aid = Long.parseLong(request.getParameter("aid"));
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
                    auctionService.updateAuction(categories, aid, name, desc, lowestBid, buyPrice, location, country, startingDate, endingDate, null, latitude, longitude);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/auction.do?action=getAnAuction&aid=" + aid + "&from=update&status=" + status);
                return;
            case "deleteAuction":
                try {
                    aid = Long.parseLong(request.getParameter("aid"));
                    auctionService.deleteAuction(aid);
                    next_page = "/user/homepage.jsp";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "bidAuction":
                double amount = Float.parseFloat(request.getParameter("bid"));
                aid = Long.parseLong(request.getParameter("aid"));
                session = request.getSession();
                long uid = ((UserEntity) session.getAttribute("user")).getUserId();
                status = false;
                try {
                    BidEntity bid = new BidEntity(uid, aid, amount);
                    auctionService.addEntity(bid);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/auction.do?action=getAnAuction&aid=" + aid + "&from=bid&status=" + status);
                return;
            case "buyAuction":
                aid = Long.parseLong(request.getParameter("aid"));
                session = request.getSession();
                uid = ((UserEntity) session.getAttribute("user")).getUserId();
                auction = auctionService.getAuction(aid);
                status = false;
                try {
                    BidEntity bid = new BidEntity(uid, aid, auction.getBuyPrice());
                    auctionService.addEntity(bid);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                auctionService.activateAuction(aid, null, false);
                auctionService.updateAuction(null, aid, null, null, null, null, null, null, null, auction.getStartingDate(), uid, null, null);
                request.setAttribute("isEnded", true);
                response.sendRedirect("/auction.do?action=getAnAuction&aid=" + aid + "&from=bid&status=" + status);
                return;
            case "getAuctionsAsXML":
                Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
                next_page = "/admin/homepage.jsp";

                if (!isAdmin) { break; }
                List<AuctionEntity> auctions;
                /* get the auctions to generate XML */
                if (request.getParameterMap().containsKey("exportSelected")){
                    List<Long> auctionIds = new ArrayList<>();
                    String[] ids = request.getParameterValues("auctionIds");
                    for (String id : ids){
                        auctionIds.add(Long.valueOf(id));
                    }
                    auctions = auctionService.getAuctionsFromIds(auctionIds);
                } else {
                    auctions = auctionService.getAuctions();
                }

                for (AuctionEntity a : auctions) {
                    a.setBidStuff();
                }
                /* use xstream to convert entities to xml */
                XStream stream = new XStream((new StaxDriver(new NoNameCoder())));
                stream.setMode(XStream.NO_REFERENCES);

                /* http://constc.blogspot.gr/2008/03/xstream-with-hibernate.html */
                stream.addDefaultImplementation(org.hibernate.collection.internal.PersistentList.class, java.util.List.class);
                stream.addDefaultImplementation(org.hibernate.collection.internal.PersistentMap.class, java.util.Map.class);
                stream.addDefaultImplementation(org.hibernate.collection.internal.PersistentSet.class, java.util.Set.class);

                Mapper mapper = stream.getMapper();
                stream.registerConverter(new HibernatePersistentCollectionConverter(mapper));
                stream.registerConverter(new HibernatePersistentMapConverter(mapper));

                /* use annotaations instead of stream calls */
                stream.processAnnotations(AuctionEntity.class);
                stream.alias("Items", List.class);

                /* create the file to export */
                String applicationPath = request.getServletContext().getRealPath("");
                String uploadFilePath = applicationPath + File.separator + DIR_FOR_XML;
                String fileName = uploadFilePath + "/auctions.xml";
                File fileSaveDir = new File(uploadFilePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }

                /* generate the xml and write it */
                Writer out;
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
                try {
                    String xml = stream.toXML(auctions);
                    out.write(xml);
                    out.write(" ");
                    out.close();
                    next_page = "/admin/downloadAuctions.jsp";
                    request.setAttribute("xmlLink", DIR_FOR_XML + "/auctions.xml");
                } catch (FileNotFoundException e) {
                    out.close();
                }
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String next_page = "/user/homepage.jsp";

        AuctionService auctionService = new AuctionService();
        HttpSession session = request.getSession();
        CategoryService categoryService = new CategoryService();
        List categoryLst = categoryService.getAllCategories();

        /* assign successMsg or errorMsg to request*/
        createMsg(request);
        if (request.getParameter("action") == null) {
            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }
        switch (request.getParameter("action")) {
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
                Set<CategoryEntity> cats = auction.getCategories();
                List<CategoryEntity> usedCategories = new ArrayList<>();
                for (CategoryEntity c : cats) {
                    usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                }
                /* get all images */
                Set<ItemImageEntity> allImages = auction.getImages();
                List<ItemImageEntity> imageLst = new ArrayList<>();
                for (ItemImageEntity img : allImages) {
                    imageLst.add(img);
                }
                /* get the highest bid */
                Set<BidEntity> allBids = auction.getBids();
                List<BidEntity> bidLst = new ArrayList<>();
                List<UserEntity> biddersLst = new ArrayList<>();
                UserService userService = new UserService();
                for (BidEntity b : allBids) {
                    bidLst.add(b);
                    biddersLst.add(userService.getUser(b.getBidderId()));
                }
                Long buyerid = auction.getIdOfHighestBidder();
                auction = checkDateAndSetBuyer(request, auction, aid, buyerid, auctionService);
                UserEntity seller = userService.getUser(sid);
                RatingService ratingService = new RatingService();
                Pair<Double, Integer> ratingNreputation = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);
                Double avg_rating = null;
                Integer total_reputation = null;
                if (ratingNreputation != null) {
                    avg_rating = ratingNreputation.fst;
                    total_reputation = ratingNreputation.snd;
                }

                request.setAttribute("auction", auction);
                request.setAttribute("usedCategories", usedCategories);
                request.setAttribute("imageLst", imageLst);
                request.setAttribute("seller", seller);
                request.setAttribute("biddersLst", biddersLst);
                request.setAttribute("bidLst", bidLst);
                request.setAttribute("avg_rating", avg_rating);
                request.setAttribute("total_reputation", total_reputation);
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
            case "getAuctionsYouHaveBid":
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
                    next_page = "/user/homepage.jsp";
                } else {
                    /* Auctions selected categories*/
                    cats = auction.getCategories();
                    usedCategories = new ArrayList<>();
                    for (CategoryEntity c : cats) {
                        usedCategories.add(new CategoryEntity(c.getCategoryId(), c.getCategoryName()));
                    }
                    /* all categories */
                    request.setAttribute("usedCategories", usedCategories);
                    request.setAttribute("categoryLst", categoryLst);
                    request.setAttribute("auction", auction);
                    next_page = "/user/auctionEdit.jsp";
                }
                break;
            case "setFromXML":
                Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
                int firstFile = Integer.parseInt(request.getParameter("firstFile"));
                int lastFile = Integer.parseInt(request.getParameter("lastFile"));
                next_page = "/admin/homepage.jsp";

                if (!isAdmin) { break; }
                XStream xstream = new XStream(new DomDriver());

                xstream.processAnnotations(AuctionEntity.class);
                xstream.registerConverter(new UserXmlUtil());
                xstream.registerConverter(new CategoryXmlUtil());
                xstream.alias("Items", List.class);

                UserService userservice = new UserService();
                AuctionService auctionservice = new AuctionService();
                categoryService = new CategoryService();
                for (int index = firstFile ; index < lastFile; index++) {
                    InputStream input = getServletContext().getResourceAsStream("items-" + index + ".xml");
                    System.out.println("\nstart on items-" + index + ".xml");
                    List<AuctionEntity> items = (List<AuctionEntity>) xstream.fromXML(input);
                    for (AuctionEntity item : items) {
                        item.setIsActive((byte) 0);
                        /* copy bids to save after auction creation */
                        Set<BidEntity> dummyBids = item.getBids();
                        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                        Boolean hasEnded = item.getEndingDate().before(currentDate);

                        List<BidEntity> bids = new ArrayList<>();
                        bids.addAll(dummyBids);
                        dummyBids.clear();

                        /* set the bidder id before adding them to db */
                        Long itemBuyerId = null;
                        Double highestBid = 0.0;
                        for (BidEntity bid : bids ) {
                            sid = userservice.addOrUpdate(bid.getBidder());
                            bid.setBidderId(sid);

                            /* check for buyer, if the auction is ended */
                            if (bid.getAmount() > highestBid && hasEnded){
                                itemBuyerId = bid.getBidderId();
                                highestBid = bid.getAmount();
                            }
                        }

                        /* set the buyeer to auction */
                        if (itemBuyerId != null){
                            item.setBuyerId(itemBuyerId);
                        }

                        /* try to save or get the user for the auction and each bid */
                        sid = userservice.addOrUpdate(item.getSeller());
                        item.setSellerId(sid);

                        /* if no location provided, use UoA's location */
                        if (item.getLatitude() == 0 && item.getLongitude() == 0){
                            item.setLatitude(37.968196);
                            item.setLongitude(23.7764984);
                        }

                        /* try to save or get the current categories */
                        categoryService.addOrUpdate(item.getCategories());
                        /* add the auction and commit every bid */
                        aid = auctionservice.addAuction(item);
                        for (BidEntity b : bids) {
                            b.setAuctionId(aid);
                            auctionservice.addEntity(b);
                        }
                    }
                }
                request.setAttribute("successMsg", "Finished importing auctions");
                break;
        }

        RequestDispatcher view = request.getRequestDispatcher(next_page);
        view.forward(request, response);
    }

    private void uploadFiles(HttpServletRequest request, long auctionId) throws IOException, ServletException {
        /* gets absolute path of the web application */
        String applicationPath = request.getServletContext().getRealPath("");
        /* constructs path of the directory to save uploaded file */
        String uploadFilePath = applicationPath + File.separator + DIR_TO_UPLOAD;

        /* creates the save directory if it does not exists */
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String fileName;
        ItemImageEntity image;
        AuctionService auctionService = new AuctionService();
        /* map the auction with the specified categories */
        /* Get all the parts from request and write it to the file on server */
        for (Part part : request.getParts()) {
            if (part.getName().equals("fileName") && part.getContentType().contains("image")) {
                long iid = auctionService.getLastImageId()+1;
                fileName = getFileName(part);
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).trim();
                part.write(uploadFilePath + File.separator + iid + "." + fileType);
                image = new ItemImageEntity(iid + "." + fileType, auctionId);
                auctionService.addEntity(image);
            }
        }
    }

    /**
     * gets the filename from a part of multiform data
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }

    /* Assigns successMsg or errorMsg on request object
     * if we know where the request came from and we have a status(true||false)
     * then we can assign proper messages that can be passed to JSPs
     * 1: BE AWARE OF THE HACK: It seems that chrome doesn't send referer header on refresh or when user is manually
     * writing the url. So by using this we will set msg attr only when we come from a redirect or a link
     **/
    private void createMsg(HttpServletRequest request) {
        // i want to check if the request is coming from a sendRedirect
        // if the user sends those paramaterers manually from a browser, then strip them out
        String from = null;
        if (request.getParameterMap().containsKey("from")) {
            from = request.getParameter("from");
        }
        String status;
        if (request.getParameterMap().containsKey("status") && request.getHeader("referer") != null && from != null) { /* 1 */
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
    }

    /* check if auction has ended and set the related fields */
    private AuctionEntity checkDateAndSetBuyer(HttpServletRequest request, AuctionEntity auction,
                                               long aid, Long buyerId, AuctionService auctionService)
    {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        if(auction.getEndingDate() != null) {
            if (auction.getEndingDate().before(currentDate)) {
                request.setAttribute("isEnded", true);
                auctionService.activateAuction(aid, null, false);
                auction.setIsActive((byte) 0);
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
