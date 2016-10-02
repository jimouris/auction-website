package javauction.service;

import javauction.model.NotificationEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Implements simple operations in order to add, update or remove entries from database using the hibernate API.
 */
public class NotificationService extends Service {

    /**
     * @param rid receiverId
     * @return a list of notification entities
     */
    public List<NotificationEntity> getNotificationsOf(long rid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List<NotificationEntity> notifications = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(NotificationEntity.class);
            criteria.add(Restrictions.eq("receiverId", rid));
            criteria.setFetchMode("actor", FetchMode.SELECT); /* dont load duplicates
            /* stuff for pagination */
            criteria.addOrder(Order.desc("dateAdded"));
            notifications = criteria.list();
            tx.commit();
        } catch (HibernateException e){
            if (tx != null) { tx.rollback(); }
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return notifications;
    }

    /**
     * @param nid notification Id
     * @return notification entity whom Id == nid
     */
    public NotificationEntity getNotification(Long nid) {
        Session session = HibernateUtil.getSession();
        NotificationEntity notification = null;
        try {
            notification = (NotificationEntity) session.get(NotificationEntity.class, nid);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return notification;
    }

    /**
     * set the notification with Id nid as seen
     * @param nid notification Id
     */
    public void setSeen(long nid) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            NotificationEntity notification = (NotificationEntity) session.get(NotificationEntity.class, nid);

            /* get all notifications for the same auctionid, type and actor
             * then setIsSeen for all of those */
            long actorId = notification.getActorId();
            long auctionId = notification.getAuctionId();
            String type = notification.getType();
            Criteria criteria = session.createCriteria(NotificationEntity.class);
            criteria.add(Restrictions.eq("actorId", actorId));
            criteria.add(Restrictions.eq("auctionId", auctionId));
            criteria.add(Restrictions.eq("type", type));
            List<NotificationEntity> notifications = criteria.list();
            for (NotificationEntity n : notifications){
                n.setIsSeen((byte) 1);
                session.update(n);
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
    }

}
