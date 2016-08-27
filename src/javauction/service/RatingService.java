package javauction.service;

import javauction.model.RatingEntity;
import javauction.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by jimouris on 8/12/16.
 */
public class RatingService extends Service {

    public enum Rating_t {
        To_t,
        From_t;
    }

    public RatingEntity getRating(long from_id, long to_id, long aid) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from RatingEntity where fromId = :fromId and toId = :toId and auctionId = :auctionId");
            query.setParameter("fromId", from_id);
            query.setParameter("toId", to_id);
            List results = query.setParameter("auctionId", aid).list();
            RatingEntity rating = null;
            if (results.size() > 0) {
                rating = (RatingEntity) results.get(0);
            }
            return rating;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<RatingEntity> getFromOrToRatings(long uid, Rating_t rating_t) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = null;
            switch (rating_t) {
                case From_t:
                    query = session.createQuery("from RatingEntity where fromId = :uid");
                    break;
                case To_t:
                    query = session.createQuery("from RatingEntity where toId = :uid");
                    break;
            }
            return query.setParameter("uid", uid).list();
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

    public void updateRating(long from_id, long to_id, long aid, int rating) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            RatingEntity ratingEntity = getRating(from_id, to_id, aid);
            ratingEntity.setRating(rating);
            session.update(ratingEntity);
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

    public Double calcAvgRating(long from_id, Rating_t rating_t) {
        RatingService ratingService = new RatingService();
        List<RatingEntity> ratingsLst = ratingService.getFromOrToRatings(from_id, rating_t);
        double avg_rating = 0;
        for (RatingEntity r : ratingsLst) {
            avg_rating += r.getRating();
        }
        if (ratingsLst.size() <= 0) {
            return null;
        }
        avg_rating /= ratingsLst.size();
        return avg_rating;
    }

}
