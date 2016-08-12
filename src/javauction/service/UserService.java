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
public class UserService {

    public enum RegisterStatus {
        REG_FAIL,
        REG_SUCCESS,
        REG_UNAME_EXISTS,
        REG_EMAIL_EXISTS
    }

    public enum LoginStatus {
        LOGIN_FAIL,
        LOGIN_SUCCESS,
        LOGIN_WRONG_UNAME_PASSWD,
        LOGIN_NOT_APPROVED,
        LOGIN_NOT_ADMIN
    }

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

    public Boolean unameExist(String uname){
        Session session = HibernateUtil.getSession();
        try{
            Query query = session.createQuery("from UserEntity where username = :uname");
            query.setParameter("uname", uname);
            List results = query.list();
            return results.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    public boolean emailExist(String email) {
        Session session = HibernateUtil.getSession();
        try{
            Query query = session.createQuery("from UserEntity where email = :email");
            query.setParameter("email", email);
            List results = query.list();
            return results.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
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

    public RegisterStatus register(UserEntity user){
        Session session = HibernateUtil.getSession();
        /*  if username exists */
        Query query = session.createQuery("from UserEntity where username = :username");
        List results = query.setParameter("username", user.getUsername()).list();
        if (results.size() > 0) {
            return RegisterStatus.REG_UNAME_EXISTS;
        }
        /*  if email exists */
        query = session.createQuery("from UserEntity where email = :email");
        results =query.setParameter("email", user.getEmail()).list();
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

    // authenticate the admin
    public LoginStatus authenticateAdmin(String username, String password) {
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from UserEntity where username = :username");
            List results = query.setParameter("username", username).list();
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
            Query query = session.createQuery("from UserEntity where username = :username and isAdmin = 0");
            List results = query.setParameter("username", username).list();
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

