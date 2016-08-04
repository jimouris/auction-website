package javauction.service;

import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by jimouris on 8/4/16.
 */
public class BidService {

    public List getAllBids(long aid){
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from BidEntity aid=" + aid);
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
