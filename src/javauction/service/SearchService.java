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
 * Created by gpelelis on 25/9/2016.
 */
public class SearchService {
    Byte isActive = null;
    Long buyerID = null;
    Long sellerID = null;
    Boolean isEnded = null;
    String auctionName = null;
    Double minPrice = null;
    Double maxPrice = null;
    String location = null;
    String description = null;
    String[] categories = null;
    Boolean reallyActive = null;

    private void setAuctionPagination(Criteria crit, Integer numOfItems, int page){
        int start = numOfItems*page;

        /* stuff for pagination */
        crit.setFirstResult(start); // 0, pagesize*1 + 1, pagesize*2 + 1, ...
        crit.setMaxResults(numOfItems);
        crit.setFetchMode("categories", FetchMode.SELECT);  // disabling those "FetchMode.SELECT"
        crit.setFetchMode("bids", FetchMode.SELECT);        // will screw up everything.
        crit.setFetchMode("seller", FetchMode.SELECT);
        crit.setFetchMode("images", FetchMode.SELECT);
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

    }

    public List<AuctionEntity> searchAuctions(int page) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List auctions = null;
        int pagesize = 6;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            Timestamp currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());


            if (isActive != null)
                criteria.add(Restrictions.eq("isStarted", isActive));

            if (buyerID != null)
                criteria.add(Restrictions.eq("buyerId", buyerID));

            if (sellerID != null)
                criteria.add(Restrictions.eq("sellerId", sellerID));

            if (isEnded != null) {
                criteria.add(Restrictions.lt("endingDate", currentDate));
            }

            if (auctionName != null) {
                criteria.add(Restrictions.like("name", auctionName, MatchMode.ANYWHERE));
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

            if (description != null)
                criteria.add(Restrictions.like("description", description, MatchMode.ANYWHERE));

            if (location != null)
                criteria.add(Restrictions.like("location", location, MatchMode.ANYWHERE));

            /* return only activated and in time auctions */
            if (reallyActive != null){
                criteria.add(Restrictions.eq("isStarted", (byte) 1));
                criteria.add(Restrictions.gt("endingDate", currentDate));
            }


            /* minPrice < price < maxPrice */
            Criterion buyNow = Restrictions.between("buyPrice", minPrice, maxPrice);
            Criterion bid = Restrictions.between("lowestBid", minPrice, maxPrice);
            LogicalExpression bidOrBuy = Restrictions.or(buyNow, bid);
            criteria.add(bidOrBuy);

            setAuctionPagination(criteria, pagesize, page);

            auctions = criteria.list();

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            }
        }
        return auctions;
    }


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

    public void setBuyerID(Long buyerID) {
        this.buyerID = buyerID;
    }

    public void setIsEnded(Boolean ended) {
        isEnded = ended;
    }

}
