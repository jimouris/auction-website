package javauction.service;

import javauction.model.AuctionEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Session;

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }


}
