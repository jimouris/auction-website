package javauction.service;

import javauction.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Created by root on 8/12/16.
 */
abstract class Service {

    public long addEntity(Object entity) {
        long id;
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            id = (long) session.save(entity);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            id = -1;
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception e) {
                // ignore
            }
        }
        return id;
    }

}
