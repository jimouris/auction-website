package javauction.service;

import javauction.model.CategoryEntity;
import javauction.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gpelelis on 10/7/2016.
 */
public class CategoryService  extends Service{
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

    public HashSet<CategoryEntity> addOrUpdate(Set<CategoryEntity> categories) {
        Session session = HibernateUtil.getSession();
        try {
            for( CategoryEntity cat : categories ) {
                Query query = session.createQuery("from CategoryEntity where categoryName = :cname");
                List results = query.setParameter("cname", cat.getCategoryName()).list();

                if (results.size() == 0) {
                    try {
                        session.beginTransaction();
                        session.save(cat);
                        session.getTransaction().commit();
                    } catch (HibernateException e) {
                        e.printStackTrace();
                    }
                } else {
                    CategoryEntity ac = (CategoryEntity) results.get(0);
                    cat.setCategoryId(ac.getCategoryId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            session.close();
        }
        return (HashSet<CategoryEntity>) categories;
    }


}
