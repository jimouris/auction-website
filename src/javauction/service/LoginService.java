package javauction.service;

import javauction.controller.PasswordAuthentication;
import javauction.model.UserEntity;
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
            Query query = session.createQuery("from UserEntity where username = '" + username + "'");
            List results = query.list();
            if (results.size() == 0) {
                /* Actually is wrong username but we dont want to give much information*/
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
            UserEntity user = (UserEntity) results.get(0);
            if (user.getIsAdmin() == 0) {
                return LoginStatus.LOGIN_NOT_ADMIN;
            }
            if (user.getIsApproved() == 0) {
                return LoginStatus.LOGIN_NOT_APPROVED;
            }
            byte[] hash = user.getHash();
            byte[] salt = user.getSalt();
            boolean loginStat = PasswordAuthentication.isExpectedPassword(password.toCharArray(), salt, hash);
            if (loginStat) {
                return LoginStatus.LOGIN_SUCCESS;
            } else {
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
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
            Query query = session.createQuery("from UserEntity where username = '" + username + "' and isAdmin = 0");
            List results = query.list();
            if (results.size() == 0) {
                /* Actually is wrong username but we dont want to give much information*/
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
            UserEntity user = (UserEntity) results.get(0);
            if (user.getIsApproved() == 0) {
                return LoginStatus.LOGIN_NOT_APPROVED;
            }
            byte[] hash = user.getHash();
            byte[] salt = user.getSalt();
            boolean loginStat = PasswordAuthentication.isExpectedPassword(password.toCharArray(), salt, hash);
            if (loginStat) {
                return LoginStatus.LOGIN_SUCCESS;
            } else {
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return LoginStatus.LOGIN_FAIL;
    }
}
