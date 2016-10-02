package javauction.service;

import javauction.model.AuctionEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Implements simple search operations from database using the hibernate API.
 */
public class SearchService {

    private Byte isActive = null;
    private Long bidderID = null;
    private Long sellerID = null;
    private Boolean isEnded = null;
    private String auctionName = null;
    private Double minPrice = null;
    private Double maxPrice = null;
    private String location = null;
    private String description = null;
    private String[] categories = null;
    private Boolean reallyActive = null;

    private int pagesize = 12;

    /**
     * @param crit criteria
     * @param page current page
     */
    private void setAuctionPagination(Criteria crit,  int page){
        int start = pagesize*page;
        /* stuff for pagination */
        crit.setFirstResult(start); // 0, pagesize*1 + 1, pagesize*2 + 1, ...
        crit.setMaxResults(pagesize);
        crit.setFetchMode("categories", FetchMode.SELECT);  // disabling those "FetchMode.SELECT"
        crit.setFetchMode("bids", FetchMode.SELECT);        // will screw up everything.
        crit.setFetchMode("images", FetchMode.SELECT);
        crit.setFetchMode("seller", FetchMode.SELECT);
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    /**
     * @param page current page
     * @return a list of auction entities for current page
     */
    public List<AuctionEntity> searchAuctions(int page) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<AuctionEntity> auctions = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            Timestamp currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());

            if (isActive != null) { criteria.add(Restrictions.eq("isActive", isActive)); }
            if (sellerID != null) { criteria.add(Restrictions.eq("sellerId", sellerID)); }
            if (isEnded != null) { criteria.add(Restrictions.lt("endingDate", currentDate)); }
            if (auctionName != null) { criteria.add(Restrictions.like("name", auctionName, MatchMode.ANYWHERE)); }

            if (bidderID != null) {
                List <Long> biddersID = new ArrayList<>();
                biddersID.add(bidderID);
                criteria.createAlias("bids", "auctionBids");
                criteria.add(Restrictions.in("auctionBids.bidderId", biddersID));
            }

            if (categories != null) {
                /* convert list of strings to list of integers */
                List <Integer> intCategories = new ArrayList<>();
                for (String c : categories) {
                    intCategories.add(Integer.parseInt(c));
                }
                criteria.createAlias("categories", "auctionCategory");
                criteria.add(Restrictions.in("auctionCategory.categoryId", intCategories));
            }
            if (description != null) { criteria.add(Restrictions.like("description", description, MatchMode.ANYWHERE)); }
            if (location != null) { criteria.add(Restrictions.like("location", location, MatchMode.ANYWHERE)); }
            /* return only activated and in time auctions */
            if (reallyActive != null) {
                criteria.add(Restrictions.eq("isActive", (byte) 1));
                criteria.add(Restrictions.gt("endingDate", currentDate));
            }

            /* minPrice < price < maxPrice */
            Criterion buyNow = Restrictions.between("buyPrice", minPrice, maxPrice);
            Criterion bid = Restrictions.between("lowestBid", minPrice, maxPrice);
            LogicalExpression bidOrBuy = Restrictions.or(buyNow, bid);
            criteria.add(bidOrBuy);
            setAuctionPagination(criteria, page);
            auctions = criteria.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) { tx.rollback(); }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return auctions;
    }

    /** finds how many pages exist as resutl
     */

    public void setReallyActive(Boolean reallyActive) {
        this.reallyActive = reallyActive;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public void setAuctionName(String name) {
        this.auctionName = name;
    }

    public void setSellerID(Long sellerID) {
        this.sellerID = sellerID;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public void setBidderID(Long bidderID) {
        this.bidderID = bidderID;
    }

    public void setIsEnded(Boolean ended) {
        isEnded = ended;
    }

}
