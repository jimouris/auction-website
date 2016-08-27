package javauction.service;

import javauction.model.MessagesEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import java.util.List;


/**
 * Created by jimouris on 8/6/16.
 */
public class MessagesService extends Service {

    public enum Message_t {
        Inbox_t,
        Sent_t
    }

    public Long addNewMessage(MessagesEntity messagesEntity) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        Long messageID = null;
        try{
            tx = session.beginTransaction();
            messageID = (Long) session.save(messagesEntity);
            session.flush();
            tx.commit();
        } catch (HibernateException e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e){
                // ignore
            }
        }
        return messageID;
    }

    public void deleteMessage(long mid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            MessagesEntity message = (MessagesEntity) session.get(MessagesEntity.class, mid);
            session.delete(message);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
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

    /* return messages where (receiverId = rid case inbox, or senderId = rid case sent) grouped by senderId*/
    public List getInboxOrSent(long rid, Message_t msg_t) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = null;
            switch (msg_t) {
                case Inbox_t:
                    query = session.createQuery("select m from MessagesEntity m where m.sendDate = (select max(b.sendDate) from MessagesEntity b where b.auctionId = m.auctionId and b.receiverId = :rid)");
                    break;
                case Sent_t:
                    query = session.createQuery("select m from MessagesEntity m where m.sendDate = (select max(b.sendDate) from MessagesEntity b where b.auctionId = m.auctionId and b.senderId = :rid)");
                    break;
            }

            return query.setParameter("rid", rid).list();
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
