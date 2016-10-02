package javauction.service;

import javauction.controller.PasswordAuthentication;
import javauction.model.UserEntity;
import javauction.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Implements simple operations in order to add, update or remove entries from database using the hibernate API.
 * Mostly used by user.do and auction.do servlets-controllers.
 */
public class UserService extends Service {

    private int pagesize = 20;

    /**
     * if user exists update him, else add him.
     * @param user UserEntity to add or update
     * @return userId
     */
    public long addOrUpdate(UserEntity user) {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("from UserEntity where username = :username");
        List results = query.setParameter("username", user.getUsername()).list();
        if (results.size() > 0) {
            UserEntity persUser = (UserEntity) results.get(0);
            user.setUserId(persUser.getUserId());
            try {
                session.close();
            } catch (Exception ignored) {}
            return persUser.getUserId();
        } else {
            addEntity(user);
            try {
                session.close();
            } catch (Exception ignored) {}
            return user.getUserId();
        }
    }

    /**
     * @param obj String (user username) or Long (user Id)
     * @return if (obj instance of String) then returns user that username = (String) obj
     *         else if (obj instanceof Long) then returns user that id = (Long) obj.
     */
    public UserEntity getUser(Object obj) {
        Session session = HibernateUtil.getSession();
        UserEntity user = null;
        try {
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
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception ignored) {}
        }
        return user;
    }

    /**
     * @param crit criteria
     * @param page current page
     */
    private void setUserPagination(Criteria crit, int page) {
        int start = pagesize*page;
        /* stuff for pagination */
        crit.setFirstResult(start); // 0, pagesize*1 + 1, pagesize*2 + 1, ...
        crit.setMaxResults(pagesize);
        crit.setFetchMode("notifications", FetchMode.SELECT);  // disabling those "FetchMode.SELECT"
        crit.setFetchMode("auctions", FetchMode.SELECT);        // will screw up everything.
        crit.setFetchMode("rating", FetchMode.SELECT);
        crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    }

    /**
     * @param page current page
     * @return Users list for current page.
     */
    public List getAllUsers(Integer page){
        Session session = HibernateUtil.getSession();
        Transaction tx;
        List users = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(UserEntity.class);
            criteria.add(Restrictions.ne("isAdmin", (byte) 1));
            criteria.addOrder(Order.asc("isApproved"));
            setUserPagination(criteria, page);
            users = criteria.list();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception ignored) {}
        }
        return users;
    }

    /**
     * @param uname username
     * @return true if uname exists, otherwise false.
     */
    public Boolean unameExist(String uname){
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Query query = session.createQuery("from UserEntity where username = :uname");
            query.setParameter("uname", uname);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception ignored) {}
        }
        return (results != null) && results.size() > 0;
    }

    /**
     * @param email email
     * @return true if email exists, otherwise false.
     */
    public boolean emailExist(String email) {
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Query query = session.createQuery("from UserEntity where email = :email");
            query.setParameter("email", email);
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception ignored) {}
        }
        return (results != null) && results.size() > 0;
    }

    /**
     * Approve user with userId == uid
     * @param uid userId
     */
    public void approveUser(long uid) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            UserEntity user = (UserEntity) session.get(UserEntity.class, uid);
            user.setIsApproved((byte) 1);
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (Exception ignored) {}
        }
    }

    /**
     * Registers a user
     * @param user user
     * @return RegisterStatus enumeration
     */
    public RegisterStatus register(UserEntity user){
        Session session = HibernateUtil.getSession();
        /*  if username exists */
        Query query = session.createQuery("from UserEntity where username = :username");
        List results = query.setParameter("username", user.getUsername()).list();
        if (results.size() > 0) {
            try { session.close(); } catch (Exception ignored) {}
            return RegisterStatus.REG_UNAME_EXISTS;
        }
        /*  if email exists */
        query = session.createQuery("from UserEntity where email = :email");
        results =query.setParameter("email", user.getEmail()).list();
        if (results.size() > 0) {
            try { session.close(); } catch (Exception ignored) {}
            return RegisterStatus.REG_EMAIL_EXISTS;
        }
        /* register the new user*/
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            try { session.close(); } catch (Exception ignored) {}
            return RegisterStatus.REG_FAIL;
        } finally {
            try {
                session.close();
            } catch (Exception ignored) {}
        }
        return RegisterStatus.REG_SUCCESS;
    }

    public enum RegisterStatus {
        REG_FAIL,
        REG_SUCCESS,
        REG_UNAME_EXISTS,
        REG_EMAIL_EXISTS
    }

    /**
     * Login
     * @param username username for login
     * @param password password for login
     * @param userAuth true for user, false for admin
     * @return LoginStatus
     */
    public LoginStatus authenticateUserOrAdmin(String username, String password, boolean userAuth) {
        Session session = HibernateUtil.getSession();
        boolean loginStat;
        try {
            Query query;
            if (!userAuth) {
                query = session.createQuery("from UserEntity where username = :username");
            } else {
                query = session.createQuery("from UserEntity where username = :username and isAdmin = 0");
            }
            List results = query.setParameter("username", username).list();
            if (results.size() == 0) {
                try { session.close(); } catch (Exception ignored) {}
                /* Actually is wrong username but we dont want to give much information*/
                return LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
            }
            UserEntity user = (UserEntity) results.get(0);
            if (!userAuth) { /* if admin authentication */
                if (user.getIsAdmin() == 0) {
                    try { session.close(); } catch (Exception ignored) {}
                    return LoginStatus.LOGIN_NOT_ADMIN;
                }
            }
            if (user.getIsApproved() == 0) {
                try { session.close(); } catch (Exception ignored) {}
                return LoginStatus.LOGIN_NOT_APPROVED;
            }
            byte[] hash = user.getHash();
            byte[] salt = user.getSalt();
            loginStat = PasswordAuthentication.isExpectedPassword(password.toCharArray(), salt, hash);
        } catch (HibernateException e) {
            e.printStackTrace();
            try { session.close(); } catch (Exception ignored) {}
            return LoginStatus.LOGIN_FAIL;
        } finally {
            try { session.close(); } catch (Exception ignored) {}
        }
        return (loginStat) ? LoginStatus.LOGIN_SUCCESS : LoginStatus.LOGIN_WRONG_UNAME_PASSWD;
    }

    public enum LoginStatus {
        LOGIN_FAIL,
        LOGIN_SUCCESS,
        LOGIN_WRONG_UNAME_PASSWD,
        LOGIN_NOT_APPROVED,
        LOGIN_NOT_ADMIN
    }

}
