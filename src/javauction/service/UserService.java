package javauction.service;

import javauction.model.UserEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by jimouris on 7/3/16.
 */
public class UserService {

    public UserEntity getUser(Object obj) {
        Session session = HibernateUtil.getSession();
        try {
            UserEntity user = null;
            if (obj instanceof String) {
                String username = obj.toString();
                Query query = session.createQuery("from UserEntity where username = :username and isAdmin = 0");
                List results = query.setParameter("username", username).list();
                if (results.size() > 0) {
                    user = (UserEntity) results.get(0);
                }
            } else if (obj instanceof Long) {
                long uid = (long) obj;
                user = (UserEntity) session.get(UserEntity.class, uid);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List getAllUsers(){
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from UserEntity where isAdmin = 0");
            List results = query.list();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Boolean approveUser(long uid) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            UserEntity user = (UserEntity) session.get(UserEntity.class, uid);
            user.setIsApproved((byte) 1);
            session.update(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }
}

