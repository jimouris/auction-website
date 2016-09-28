package javauction.service;

import javauction.model.MessagesEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import java.util.List;

/**
 * Implements simple operations in order to add, update or remove entries from database using the hibernate API.
 */
public class MessagesService {

    public enum Message_t {
        Inbox_t,
        Sent_t
    }

    /**
     * @param messagesEntity message entity to add
     * @return messageId
     */
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
            if (tx != null) { tx.rollback(); }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return messageID;
    }

    /**
     * @param mid messageId to delete
     */
    public void deleteMessage(long mid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            MessagesEntity message = (MessagesEntity) session.get(MessagesEntity.class, mid);
            session.delete(message);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) { tx.rollback(); }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * @param aid auction id
     * @return a list of message entities about auction with Id == aid
     */
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
            if (tx != null) { tx.rollback(); }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return messages;
    }

    /**
     * @param rid receiverId
     * @param msg_t message type (Inbox or Sent)
     * @return messages where (receiverId = rid case inbox, or senderId = rid case sent) grouped by senderId
     */
    public List getInboxOrSent(long rid, Message_t msg_t) {
        Session session = HibernateUtil.getSession();
        Query query = null;
        List messages = null;
        try {
            switch (msg_t) {
                case Inbox_t:
                    query = session.createQuery("select m from MessagesEntity m where m.sendDate = (select max(b.sendDate) " +
                            "from MessagesEntity b where b.auctionId = m.auctionId and b.receiverId = :rid)");
                    break;
                case Sent_t:
                    query = session.createQuery("select m from MessagesEntity m where m.sendDate = (select max(b.sendDate) " +
                            "from MessagesEntity b where b.auctionId = m.auctionId and b.senderId = :rid)");
            }
            messages = (query != null) ? query.setParameter("rid", rid).list() : null;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return messages;
    }

}
