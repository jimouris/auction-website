package javauction.service;

import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


/**
 * Created by jimouris on 7/3/16.
 */
public class LoginService {

    public enum LoginStatus {
        LOGIN_FAIL,
        LOGIN_SUCCESS,
        LOGIN_WRONG_UNAME_PASSWD,
        LOGIN_NOT_APPROVED,
        LOGIN_NOT_ADMIN
    }

    // authenticate the admin
    public LoginStatus authenticateAdmin(String username, String password) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from UserEntity where username='"+username+"' and password='"+password+"'");
            List results = query.list();
            if (results.size() == 0) {
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
            query = session.createQuery("from UserEntity where username='"+username+"' and password='"+password+"' and isAdmin=1");
            results = query.list();
            if (results.size() == 0) {
                return LoginStatus.LOGIN_NOT_ADMIN;
            }
            return LoginStatus.LOGIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return LoginStatus.LOGIN_FAIL;
    }

    // authenticate a regular user
    public LoginStatus authenticateUser(String username, String password) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from UserEntity where username='"+username+"' and password='"+password+"' and isAdmin=0");
            List results = query.list();
            if (results.size() == 0) {
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
            query = session.createQuery("from UserEntity where username='"+username+"' and password='"+password+"' and isApproved=1 and isAdmin=0");
            results = query.list();
            if (results.size() == 0) {
                return LoginStatus.LOGIN_NOT_APPROVED;
            }
            return LoginStatus.LOGIN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return LoginStatus.LOGIN_FAIL;
    }
}
