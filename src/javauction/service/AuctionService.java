package javauction.service;

import javauction.model.AuctionEntity;
import javauction.model.BidEntity;
import javauction.model.CategoryEntity;
import javauction.model.ItemImageEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import java.sql.Timestamp;
import java.util.*;

/**
 * Implements simple operations in order to add, update or remove entries from database using the hibernate API.
 * Mostly used by auction.do servlet-controller.
 */
public class AuctionService extends Service {

    /**
     * @param obj String (auction name) or Long (auction Id)
     * @return if (obj instance of String) then returns Auction that name = (String) obj
     *         else if (obj instanceof Long) then returns Auction that id = (Long) obj.
     */
    public AuctionEntity getAuction(Object obj) {
        Session session = HibernateUtil.getSession();
        AuctionEntity auction = null;
        try {
            if (obj instanceof String) {
                String auction_name = obj.toString();
                Query query = session.createQuery("from AuctionEntity where name = :auction_name");
                List results = query.setParameter("auction_name", auction_name).list();
                if (results.size() > 0) {
                    auction = (AuctionEntity) results.get(0);
                }
            } else if (obj instanceof Long) {
                long aid = (long) obj;
                auction = (AuctionEntity) session.get(AuctionEntity.class, aid);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return auction;
    }

    /**
     * @param uid userId
     * @param isSeller is seller?
     * @return all ended auctions for user uid (as seller or buyer)
     */
    public List getAllEndedAuctions(Long uid, boolean isSeller) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List auctions = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            /* get all inactive */
            criteria.add(Restrictions.eq("isActive", (byte) 0));
            /* get all those that are really ended */
            Timestamp currentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
            criteria.add(Restrictions.lt("endingDate", currentDate));
            /* where seller id == sid */
            if (uid != null) {
                if (isSeller) {
                    criteria.add(Restrictions.eq("sellerId", uid));
                } else {
                    criteria.add(Restrictions.eq("buyerId", uid));
                }
            }
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
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

    /**
     * @param sid sellerId
     * @param getAllActive get active or all?
     * @return A list of auctions
     */
    public List getAllAuctions(long sid, boolean getAllActive) {
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Query query;
            if (getAllActive) {
                if (sid < 0) {
                    query = session.createQuery("from AuctionEntity where isActive = 1");
                } else {
                    query = session.createQuery("from AuctionEntity where sellerId = :sid and isActive = 1");
                    query.setParameter("sid", sid);
                }
            } else {
                query = session.createQuery("from AuctionEntity where sellerId = :sid");
                query.setParameter("sid", sid);
            }
            results = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return results;
    }

    /**
     * If activate == true then activate auction with Id == aid and set its ending date to endingDate.
     * else set isActive = 0.
     * @param aid AuctionId
     * @param endingDate The new ending date
     * @param activate To set active or inactive.
     */
    public void activateAuction(long aid, Timestamp endingDate, boolean activate) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            AuctionEntity auction = (AuctionEntity) session.get(AuctionEntity.class, aid);
            if (activate) {
                auction.setIsActive((byte) 1);
                if (endingDate != null) { auction.setEndingDate(endingDate); }
            } else {
                auction.setIsActive((byte) 0);
            }
            Timestamp timeNow = new Timestamp(Calendar.getInstance().getTimeInMillis());
            auction.setStartingDate(timeNow);
            session.update(auction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * Deletes an auction with id == aid and all associated records of auction_has_category and also itemimage from db
     * @param aid the auctionId to delete
     */
    public void deleteAuction(long aid) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            AuctionEntity auction = (AuctionEntity) session.get(AuctionEntity.class, aid);
            session.delete(auction);
            transaction.commit();
        } catch (HibernateException e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * Updates only the fields that are not null of auction with id aid.
     * @param categories set of categories
     * @param aid auctionId
     * @param name name
     * @param desc description
     * @param lowestBid lowest bid
     * @param buyPrice buy price (instant buy)
     * @param location location
     * @param country country
     * @param startingDate starting date
     * @param endingDate ending date
     * @param buyerid buyerId
     * @param latitude latitude
     * @param longitude longtitude
     */
    public void updateAuction(Set<CategoryEntity> categories, Long aid, String name, String desc, Double lowestBid,
                              Double buyPrice, String location, String country, Timestamp startingDate,
                              Timestamp endingDate, Long buyerid, Double latitude, Double longitude)
    {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        AuctionEntity auction = getAuction(aid);
        try {
            tx = session.beginTransaction();
            if (categories != null) { auction.setCategories(categories); }
            if (name != null) { auction.setName(name); }
            if (desc != null) { auction.setDescription(desc); }
            if (lowestBid != null) { auction.setLowestBid(lowestBid); }
            if (buyPrice != null) { auction.setBuyPrice(buyPrice); }
            if (country != null) { auction.setCountry(country); }
            if (location != null) { auction.setLocation(location); }
            if (latitude != null) { auction.setLatitude(latitude); }
            if (longitude != null) { auction.setLongitude(longitude); }
            if (startingDate != null) { auction.setStartingDate(startingDate); }
            if (endingDate != null) { auction.setEndingDate(endingDate);}
            if (buyerid != null) { auction.setBuyerId(buyerid); }
            session.update(auction);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * @return A list of all auctions.
     */
    public List<AuctionEntity> getAuctions() {
        Session session = HibernateUtil.getSession();
        List<AuctionEntity> results = null;
        try {
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            results = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return results;
    }

    /**
     * @param ids A list of auction IDs
     * @return A list of auctions whose ids are in the ids list.
     */
    public List<AuctionEntity> getAuctionsFromIds(List<Long> ids){
        Session session = HibernateUtil.getSession();
        List<AuctionEntity> auctions = null;
        try {
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            criteria.add(Restrictions.in("auctionId", ids));
            criteria.setFetchMode("categories", FetchMode.SELECT);  // don't disable those fetch modes
            criteria.setFetchMode("bids", FetchMode.SELECT);
            criteria.setFetchMode("images", FetchMode.SELECT);
            criteria.setFetchMode("seller", FetchMode.SELECT);
            auctions = criteria.list();
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return auctions;
    }

    /**
     * @param aid Auction ID
     * @return A set of unique bidder ids for the auction with Id == aid.
     */
    public HashSet<Long> getUniqueBidders(Long aid) {
        AuctionEntity auction = getAuction(aid);
        if (auction == null) {
            return null;
        }
        Set<BidEntity> bidEntities = auction.getBids();
        List<Long> bidders = new ArrayList<>();
        for (BidEntity b : bidEntities) {
            bidders.add(b.getBidderId());
        }
        return new HashSet<>(bidders);
    }

    /**
     * @param auction Auction Entity to add.
     * @return Auction Id
     */
    public long addAuction(AuctionEntity auction){
        addEntity(auction);
        return auction.getAuctionId();
    }

    /**
     * @return the last image Id from database, in order to have unique image names. If there is no image yet, returns 0.
     */
    public long getLastImageId() {
        Session session = HibernateUtil.getSession();
        ItemImageEntity result = null;
        try {
            result = (ItemImageEntity) session.createQuery("from ItemImageEntity ORDER BY itemImageId DESC").setMaxResults(1).uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return result != null ? result.getItemImageId() : 0;
    }

}
