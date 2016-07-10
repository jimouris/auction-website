package javauction.service;

import javauction.model.UserEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by jimouris on 7/2/16.
 */
public class RegisterService {

    public enum RegisterStatus {
        REG_FAIL,
        REG_SUCCESS,
        REG_UNAME_EXISTS,
        REG_EMAIL_EXISTS
    }

    public RegisterStatus register(UserEntity user){
        Session session = HibernateUtil.getSession();
        /*  if username exists */
        Query query = session.createQuery("from UserEntity where username='"+user.getUsername()+"'");
        List results = query.list();
        if (results.size() > 0) {
            return RegisterStatus.REG_UNAME_EXISTS;
        }
        /*  if email exists */
        query = session.createQuery("from UserEntity where email='"+user.getEmail()+"'");
        results = query.list();
        if (results.size() > 0) {
            return RegisterStatus.REG_EMAIL_EXISTS;
        }
        /* register the new user*/
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return RegisterStatus.REG_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 7/3/16 add here a rollback
        } finally {
            session.close();
        }
        return RegisterStatus.REG_FAIL;
    }

}