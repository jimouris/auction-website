package javauction.service;

import javauction.model.UserEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by jimouris on 7/2/16.
 */
public class RegisterService {

    public boolean register(UserEntity user){
        Session session = HibernateUtil.getSession();
//        if(isUserExists(user)) return false;

        try {
            session.beginTransaction();
            System.out.println(user.toString());
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 7/3/16 add here a rollback
        } finally {
            session.close();
        }
        return true;
    }

//    public boolean isUserExists(UserEntity user){
//        Session session = HibernateUtil.getSession();
//        boolean result = false;
//        Transaction tx = null;
//        try{
//            tx = session.getTransaction();
//            tx.begin();
//            Query query = session.createQuery("from user where UserId='"+user.getUserId()+"'");
//            UserEntity u = (UserEntity) query.uniqueResult();
//            tx.commit();
//            if(u!=null) result = true;
//        }catch(Exception ex){
//            if(tx!=null){
//                tx.rollback();
//            }
//        }finally{
//            session.close();
//        }
//        return result;
//    }
}