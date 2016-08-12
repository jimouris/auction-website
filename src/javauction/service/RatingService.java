package javauction.service;

import javauction.model.RatingEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by jimouris on 8/12/16.
 */
public class RatingService extends Service {

    public Integer getRating(long from_id, long to_id, long aid) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from RatingEntity where fromId = :fromId and toId = :toId and auctionId = :auctionId");
            query.setParameter("fromId", from_id);
            query.setParameter("toId", to_id);
            List results = query.setParameter("auctionId", aid).list();
            Integer rating = null;
            if (results.size() > 0) {
                rating = ((RatingEntity) results.get(0)).getRating();
            }
            return rating;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
