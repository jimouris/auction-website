package javauction.service;

import com.sun.tools.javac.util.Pair;
import javauction.model.RatingEntity;
import javauction.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Implements simple operations in order to add, update or remove entries from database using the hibernate API.
 */
public class RatingService extends Service {

    public enum Rating_t {
        To_t,
        From_t;
    }

    /**
     * @param from_id from id
     * @param to_id to id
     * @param aid auction Id
     * @return rating that has been placed from user with id from_id for user with id to_id
     */
    public RatingEntity getRating(long from_id, long to_id, long aid) {
        Session session = HibernateUtil.getSession();
        RatingEntity rating = null;
        try {
            Query query = session.createQuery("from RatingEntity where fromId = :fromId and toId = :toId and auctionId = :auctionId");
            query.setParameter("fromId", from_id);
            query.setParameter("toId", to_id);
            List results = query.setParameter("auctionId", aid).list();
            if (results.size() > 0) {
                rating = (RatingEntity) results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return rating;
    }

    /**
     * @param uid userId
     * @param rating_t ratings From or To
     * @return all ratings that a user has placed, or have been placed for him
     */
    public List<RatingEntity> getFromOrToRatings(long uid, Rating_t rating_t) {
        Session session = HibernateUtil.getSession();
        Query query = null;
        List<RatingEntity> ratingEntities = null;
        try {
            switch (rating_t) {
                case From_t:
                    query = session.createQuery("from RatingEntity where fromId = :uid");
                    break;
                case To_t:
                    query = session.createQuery("from RatingEntity where toId = :uid");
                    break;
            }
            ratingEntities = (query != null) ? query.setParameter("uid", uid).list() : null;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return ratingEntities;
    }

    /**
     * @param from_id from userId
     * @param to_id to userId
     * @param aid auctionId
     * @param rating rating
     */
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
            } catch (Exception ignored) {}
        }
    }

    /**
     * @param from_id from userId
     * @param rating_t ratings From or To
     * @return average rating
     */
    public Pair<Double, Integer> calcAvgRating(long from_id, Rating_t rating_t) {
        RatingService ratingService = new RatingService();
        List<RatingEntity> ratingsLst = ratingService.getFromOrToRatings(from_id, rating_t);
        double avg_rating = 0;
        int total_reputation = 0;
        for (RatingEntity r : ratingsLst) {
            total_reputation = updateReputation(total_reputation, r.getRating());
            avg_rating += r.getRating();
        }
        if (ratingsLst.size() <= 0) {
            return null;
        }
        avg_rating /= ratingsLst.size();
        return new Pair<>(avg_rating, total_reputation);
    }

    private int updateReputation(int reputation, int rating) {
        switch (rating) {
            case 1: return reputation + 1;
            case 2: return reputation + 3;
            case 3: return reputation + 5;
            case 4: return reputation + 10;
            case 5: return reputation + 20;
            default: return reputation;
        }
    }

}
