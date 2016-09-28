package javauction.service;

import javauction.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Implements a simple add operation to database using the hibernate API.
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
            } catch (Exception ignored) {}
        }
    }

}
