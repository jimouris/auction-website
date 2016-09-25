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


public class AuctionService extends Service {

    public AuctionEntity getAuction(Object obj) {
        Session session = HibernateUtil.getSession();
        try {
            AuctionEntity auction = null;
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
            return auction;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return null;
    }

    @Deprecated
    public List getAllEndedAuctions(Long uid, boolean isSeller) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List auctions = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            /* get all inactive */
            criteria.add(Restrictions.eq("isStarted", (byte) 0));
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
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return auctions;
    }

    @Deprecated
    public List getAllAuctions(long sid, boolean getAllActive) {
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Query query;
            if (getAllActive) {
                query = session.createQuery("from AuctionEntity where isStarted = 1");
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

    public void activateAuction(long aid, Timestamp endingDate, boolean activate) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            AuctionEntity auction = (AuctionEntity) session.get(AuctionEntity.class, aid);
            if (activate) {
                auction.setIsStarted((byte) 1);
                /* when we activate the auction, we may pass the endingdate at this time */
                if (endingDate != null) auction.setEndingDate(endingDate);
            } else {
                auction.setIsStarted((byte) 0);
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

    // deletes an auction and all associated records of auction_has_category from db
    // todo: when we add the images, check if it also delete those
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

    public void updateAuction(Set<CategoryEntity> categories, Long aid, String name, String desc, Double lowestBid,
                              Double buyPrice, String location, String country, Timestamp startingDate, Timestamp endingDate, Long buyerid, Double latitude, Double longitude) {

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

    public List getNAuctions(int N) {
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Criteria criteria = session.createCriteria(AuctionEntity.class);

            criteria.setMaxResults(N);

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
        HashSet<Long> uniqueBidders = new HashSet<>(bidders);
        return uniqueBidders;
    }

    public long addAuction(AuctionEntity auction){
        Session session = HibernateUtil.getSession();

        try {
            session.beginTransaction();
            session.save(auction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            } finally {
                return auction.getAuctionId();
            }
        }
    }

    public long getLastImageId() {
        Session session = HibernateUtil.getSession();
        ItemImageEntity result = null;
        try{
            result = (ItemImageEntity) session.createQuery("from ItemImageEntity ORDER BY itemImageId DESC")
                    .setMaxResults(1).uniqueResult();

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
