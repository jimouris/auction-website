package javauction.service;

import javauction.model.CategoryEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by gpelelis on 10/7/2016.
 */
public class CategoryService {
    public List getAllCategories(){
        Session session = HibernateUtil.getSession();
        try {
            Query query = session.createQuery("from CategoryEntity");
            List results = query.list();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public CategoryEntity getCategory(int cid){
        Session session = HibernateUtil.getSession();
        try {
            CategoryEntity category = null;
                Query query = session.createQuery("from CategoryEntity where categoryId = :cid");
                List results = query.setParameter("cid", cid).list();

                if (results.size() > 0) {
                    category = (CategoryEntity) results.get(0);
                }
            return category;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}
