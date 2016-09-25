package javauction.controller;

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
import javauction.util.SellerXmlUtil;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by gpelelis on 4/7/2016.
 */
@WebServlet(name = "auction")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB
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

        if (request.getParameter("action") != null) {
            if (request.getParameter("action").equals("addNew")) {
                // get the user input
                String name = request.getParameter("name"); /* required */
                String description = request.getParameter("description"); /* required */
                float lowestBid = Float.parseFloat(request.getParameter("lowestBid")); /* required */
                String startToday = request.getParameter("startToday"); /* always sent by default */
                int activeDays = Integer.parseInt(request.getParameter("activeDays")); /* optional */
                String location = request.getParameter("location");  /* required */
                String country = request.getParameter("country");  /* required */
                String instantBuy = request.getParameter("instantBuy"); /* always sent by default */
                /* get userid from session. userid will be sellerid for this specific auction! */
                HttpSession session = request.getSession();
                long sellerId = ((UserEntity) session.getAttribute("user")).getUserId();

                // the auction will start now, so we have to find the current date
                Timestamp startDate = null;
                Timestamp endDate = null;
                byte isStarted = 0;
                if (startToday.equals("true")) {
                    startDate = new Timestamp(System.currentTimeMillis());

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DAY_OF_WEEK, activeDays);
                    endDate = new Timestamp(cal.getTime().getTime());
                    isStarted = 1;
                }

                // create auction entity with the required value
                AuctionEntity auction = new AuctionEntity(name, sellerId, description, lowestBid, location, country, startDate, isStarted, endDate);

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
                    // upload the images
                    uploadFiles(request, auction.getAuctionId());
                    request.setAttribute("aid", auction.getAuctionId());
                    next_page = "/user/auctionSubmit.jsp";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (request.getParameter("action").equals("activateAuction")) {
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
                Timestamp startingDate = null;
                if (request.getParameterMap().containsKey("startingDate"))
                    startingDate = Timestamp.valueOf(request.getParameter("startingDate"));
                Timestamp endingDate = null;
                if (request.getParameterMap().containsKey("endingDate"))
                    endingDate = Timestamp.valueOf(request.getParameter("endingDate"));
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
                    auctionService.updateAuction(categories, aid, name, desc, lowestBid, buyPrice, location, country, startingDate, endingDate, null, latitude, longitude);
                    status = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response.sendRedirect("/auction.do?action=getAnAuction&aid=" + aid + "&from=update&status=" + status);
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
                double amount = Float.parseFloat(request.getParameter("bid"));
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
                }

                response.sendRedirect("/auction.do?action=getAnAuction&aid=" + aid + "&from=bid&status=" + status);
                return;
            }
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
        createMsg(request, response);
        if (request.getParameter("action") != null) {
            String param = request.getParameter("action");

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
                    /* if auction has ended */
                    Long buyerid = null;
                    if (biddersLst.size() > 0) {
                        buyerid = (Long) biddersLst.get(0).getUserId();
                    }
                    auction = checkDateAndSetBuyer(request, auction, aid, buyerid, auctionService);
                    UserEntity seller = userService.getUser(sid);
                    RatingService ratingService = new RatingService();
                    Double avg_rating = ratingService.calcAvgRating(sid, RatingService.Rating_t.To_t);

                    request.setAttribute("auction", auction);
                    request.setAttribute("usedCategories", usedCategories);
                    request.setAttribute("imageLst", imageLst);
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
                case "getAuctionsAsXML":
                    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
                    next_page = "homepage.jsp";

                    if ( isAdmin ) {
                        List<AuctionEntity> auctions = auctionService.getNAuctions(10);
                        for (AuctionEntity a : auctions)
                            a.setBidStuff();

                        // use xstream to convert entities to xml
                        XStream stream = new XStream((new StaxDriver(new NoNameCoder())));
                        stream.setMode(XStream.NO_REFERENCES);

                        // http://constc.blogspot.gr/2008/03/xstream-with-hibernate.html
                        stream.addDefaultImplementation(org.hibernate.collection.internal.PersistentList.class, java.util.List.class);
                        stream.addDefaultImplementation(org.hibernate.collection.internal.PersistentMap.class, java.util.Map.class);
                        stream.addDefaultImplementation(org.hibernate.collection.internal.PersistentSet.class, java.util.Set.class);

                        Mapper mapper = stream.getMapper();
                        stream.registerConverter(new HibernatePersistentCollectionConverter(mapper));
                        stream.registerConverter(new HibernatePersistentMapConverter(mapper));

                        // use annotaations instead of stream calls
                        stream.processAnnotations(AuctionEntity.class);
                        stream.alias("Items", List.class);

                        // create the file to export
                        String applicationPath = request.getServletContext().getRealPath("");
                        String uploadFilePath = applicationPath + File.separator + DIR_FOR_XML;
                        String fileName = uploadFilePath + "/auctions.xml";
                        File fileSaveDir = new File(uploadFilePath);
                        if (!fileSaveDir.exists()) {
                            fileSaveDir.mkdirs();
                        }

                        // generate the xml and write it
                        Writer out;
                        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
                        try {
                            String xml = stream.toXML(auctions);
                            out.write(xml);
                            out.write(" ");
                            out.close();
                            next_page = DIR_FOR_XML + "/auctions.xml";
                        } catch (FileNotFoundException e) {
                            out.close();

                        }
                    }
                    break;
                case "setFromXML":
                    XStream xstream = new XStream(new DomDriver());

                    xstream.processAnnotations(AuctionEntity.class);
                    xstream.registerConverter(new SellerXmlUtil());
                    xstream.registerConverter(new CategoryXmlUtil());
                    xstream.alias("Items", List.class);

                    UserService userservice = new UserService();
                    AuctionService auctionservice = new AuctionService();
                    categoryService = new CategoryService();

                    for (int index =0 ; index < 40; index++ ) {

                        InputStream input = getServletContext().getResourceAsStream("items-" + index + ".xml");
                        System.out.println("\nstart on items-" + index + ".xml");
                        List<AuctionEntity> items = (List<AuctionEntity>) xstream.fromXML(input);
                        int itemindex = 0;
                        for (AuctionEntity item : items) {
                            System.out.println("\non item " + item.getName());
                            System.out.println(itemindex++);
                            item.setIsStarted((byte) 0);

                            // copy bids to savee after auction creation
                            Set<BidEntity> dummyBids = item.getBids();
                            Set<BidEntity> bids = new LinkedHashSet<>();
                            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                            Boolean hasEnded = item.getEndingDate().before(currentDate);

                            int j = 0;
                            for (Iterator<BidEntity> i = dummyBids.iterator(); i.hasNext(); ) {
                                BidEntity b = i.next();
                                BidEntity bid = new BidEntity(b);
                                sid = userservice.addOrUpdate(bid.getBidder());
                                bid.setBidderId(sid);
                                bids.add(bid);

                                // do stuff for auction
                                i.remove();
                                if (hasEnded && j == bids.size() - 1)
                                    item.setBuyerId(bid.getBidderId());
                                j++;
                            }

                            // try to save or get the user for the auction and each bid
                            sid = userservice.addOrUpdate(item.getSeller());
                            item.setSellerId(sid);

                            // try to save or get the current categories
                            categoryService.addOrUpdate(item.getCategories());

                            // add the auction and commit every bid
                            aid = auctionservice.addAuction(item);
                            for (BidEntity b : bids) {
                                b.setAuctionId(aid);
                                auctionservice.addEntity(b);
                            }
                        }
                    }

                    break;
                }
            }
            RequestDispatcher view = request.getRequestDispatcher(next_page);
            view.forward(request, response);
        }



    private Boolean uploadFiles(HttpServletRequest request, long auctionId) throws IOException, ServletException {
        // gets absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + DIR_TO_UPLOAD;

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String fileName = null;
        ItemImageEntity image;
        AuctionService auctionService = new AuctionService();
        // map the auction with the specified categories
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
        return true;
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
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
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
