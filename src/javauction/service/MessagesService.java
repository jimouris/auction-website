package javauction.service;

import javauction.model.MessagesEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created by jimouris on 8/6/16.
 */
public class MessagesService {

    public void addMessage(MessagesEntity message) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.save(message);
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


    /* simple search: search for auctions whose names contain string name */
    public java.util.List getAuctionConversation(long aid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        java.util.List messages = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(MessagesEntity.class);
            criteria.add(Restrictions.eq("auctionId", aid));

            messages = criteria.list();
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
        return messages;
    }

}
