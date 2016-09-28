package javauction.service;

import javauction.model.CategoryEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements simple operations in order to add, update or remove entries from database using the hibernate API.
 */
public class CategoryService  extends Service {

    /**
     * @return a list of all categories
     */
    public List getAllCategories() {
        Session session = HibernateUtil.getSession();
        List results = null;
        try {
            Query query = session.createQuery("from CategoryEntity");
            results = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return results;
    }

    /**
     * @param cid categoryId
     * @return a category with Id == cid
     */
    public CategoryEntity getCategory(int cid) {
        Session session = HibernateUtil.getSession();
        CategoryEntity category = null;
        try {
            Query query = session.createQuery("from CategoryEntity where categoryId = :cid");
            List results = query.setParameter("cid", cid).list();
            if (results.size() > 0) {
                category = (CategoryEntity) results.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return category;
    }

    /**
     * @param categories a set of category entities
     * @return the updated set of categories
     */
    public HashSet<CategoryEntity> addOrUpdate(Set<CategoryEntity> categories) {
        Session session = HibernateUtil.getSession();
        try {
            for( CategoryEntity cat : categories ) {
                Query query = session.createQuery("from CategoryEntity where categoryName = :cname");
                List results = query.setParameter("cname", cat.getCategoryName()).list();
                if (results.size() == 0) {
                    addEntity(cat);
                } else {
                    CategoryEntity ac = (CategoryEntity) results.get(0);
                    cat.setCategoryId(ac.getCategoryId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if (session != null) session.close();
            } catch (Exception ignored) {}
        }
        return (HashSet<CategoryEntity>) categories;
    }

}
