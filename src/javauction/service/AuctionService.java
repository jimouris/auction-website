package javauction.service;

import javauction.model.AuctionEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

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
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            }
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
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            }
        }
        return null;
    }

    public List getAllAuctions(long sid, boolean getAllActive) {
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Query query;
            if (getAllActive) {
                query = session.createQuery("from AuctionEntity where isStarted=1");
            } else {
                query = session.createQuery("from AuctionEntity where sellerId =" + sid);
            }
            results = query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            }
        }
        return results;
    }

    /* simple search: search for auctions whose names contain string name */
    public List searchAuction(String name) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List auctions = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
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

    /* advanced search, using custom criteria! */
    public List searchAuction(String[] categories, String desc, double minPrice, double maxPrice, String location) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List auctions = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(AuctionEntity.class);
            /* category search */
            if (categories != null) {
                // TODO category search
            }
            /* description search */
            if (desc != "") criteria.add(Restrictions.like("description", desc, MatchMode.ANYWHERE));
            /* minPrice < price < maxPrice */
            criteria.add(Restrictions.between("buyPrice", minPrice, maxPrice));
            /* location search*/
            if (location != "") {
                Criterion city = Restrictions.like("city", location, MatchMode.EXACT);
                Criterion country = Restrictions.like("country", location, MatchMode.EXACT);
                LogicalExpression cityORcountry = Restrictions.or(city, country);
                criteria.add(cityORcountry);
            }

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

    public void activateAuction(long aid) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            AuctionEntity auction = (AuctionEntity) session.get(AuctionEntity.class, aid);
            auction.setIsStarted((byte) 1);
            session.update(auction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
