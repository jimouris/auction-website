package javauction.service;

import javauction.model.BidEntity;
import javauction.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.util.List;


public class BidService {

    /**
     * @param uid userId
     * @return a list of auction-ids that user with uid has bided
     */
    public List getAllUserBids(long uid) {
        Session session = HibernateUtil.getSession();
        List bids = null;
        try {
            Criteria criteria = session.createCriteria( BidEntity.class );
            criteria.add(Restrictions.eq("bidderId", uid));
            criteria.setProjection( Projections.distinct( Projections.property( "auctionId" ) ));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            bids = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            try { if (session != null) session.close(); } catch (Exception ignored) {}
        }
        return bids;
    }

}
