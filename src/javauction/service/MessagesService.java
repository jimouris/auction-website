package javauction.service;

import javauction.model.MessagesEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

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
    public List getAuctionConversation(long aid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List messages = null;
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

    /* return messages where receiverId = rid grouped by senderId*/
    public List getInbox(long rid) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("select m from MessagesEntity m where m.sendDate = " +
                    "(select max(b.sendDate) from MessagesEntity b where b.auctionId = m.auctionId and b.receiverId =" + rid + ")");
            List <MessagesEntity> messagesLst = query.list();

            return messagesLst;
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


}
