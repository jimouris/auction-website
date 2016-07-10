package javauction.service;

import javauction.model.AuctionEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by gpelelis on 5/7/2016.
 */
public class AuctionService {

    public boolean addAuction(AuctionEntity auction) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.save(auction);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public AuctionEntity getAuction(Object obj) {
        Session session = HibernateUtil.getSession();
        try {
            AuctionEntity auction = null;
            if (obj instanceof String) {
                String auction_name = obj.toString();
                Query query = session.createQuery("from AuctionEntity where name='"+auction_name+"'");
                List results = query.list();
                if (results.size() > 0) {
                    auction = (AuctionEntity) results.get(0);
                }
            } else if (obj instanceof Long) {
                long aid = (long) obj;
                auction = (AuctionEntity) session.get(AuctionEntity.class, aid);
            }
            return auction;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List getAllAuctions(long sid){
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from AuctionEntity where sellerId =" + sid);
            List results = query.list();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
