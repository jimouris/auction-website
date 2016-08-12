package javauction.service;

import javauction.model.AuctionEntity;
import javauction.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.metamodel.domain.Entity;
import org.hibernate.metamodel.relational.ObjectName;
import org.hibernate.metamodel.source.annotations.entity.EntityClass;

/**
 * Created by root on 8/12/16.
 */
abstract class Service {

    public void addEntity(Object entity) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.save(entity);
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

}
