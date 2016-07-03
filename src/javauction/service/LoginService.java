package javauction.service;

import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


/**
 * Created by jimouris on 7/3/16.
 */
public class LoginService {

    public boolean authenticateAdmin(String userName, String password) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from UserEntity where username='"+userName+"' and password='"+password+"' and isAdmin=1");
            List results = query.list();
            return results.size() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    public List getAllUsers(){
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from UserEntity where isAdmin=0");
            List results = query.list();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
