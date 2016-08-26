package javauction.service;

import javauction.model.NotificationEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by gpelelis on 24/8/2016.
 */
public class NotificationService extends Service {
    public List<NotificationEntity> getNotificationsOf(long rid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        List notifcations = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(NotificationEntity.class);
            criteria.add(Restrictions.eq("receiverId", rid));
            /* stuff for pagination */
            criteria.addOrder(Order.desc("dateAdded"));

            notifcations = criteria.list();
            tx.commit();
        } catch (HibernateException e){
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
        return notifcations;
    }

    public NotificationEntity getNotification(Long nid) {
        Session session = HibernateUtil.getSession();
        try {
            NotificationEntity notification = null;
            notification = (NotificationEntity) session.get(NotificationEntity.class, nid);
            return notification;
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


    public void setSeen(long nid) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            NotificationEntity auction = (NotificationEntity) session.get(NotificationEntity.class, nid);
            auction.setIsSeen((byte) 1);
            session.update(auction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
            }
        }
    }

    public void deleteNotificaton(long mid) {
        Session session = HibernateUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(NotificationEntity.class);
            NotificationEntity notif = (NotificationEntity) criteria.add(Restrictions.eq("messageId", mid)).uniqueResult();
            session.delete(notif);
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
}
