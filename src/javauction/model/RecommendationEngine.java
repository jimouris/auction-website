package javauction.model;

import javauction.service.AuctionService;
import javauction.service.BidService;

import java.util.*;

/**
 * Class that is responsible for the recommendation system.
 * When a user loges-in this class provides him Items_to_recommend (5) items in his homepage.
 */
public class RecommendationEngine {

    private int K_Neighbours = 15;
    private int Items_to_recommend = 5;

    /**
     * returns the list of recommendations (AuctionEntities)
     * @param uid the logged-in userId
     * @return the list of recommendations
     */
    public List<AuctionEntity> getRecommendations(long uid) {
        List<Long> knn = getKnn(uid);
        return getItems(uid, knn);
    }

    /**
     * returns the list of K-Nearest-Neighbours (UserIds)
     * @param uid the logged-in userId
     * @return the list of K-Nearest userIds.
     */
    private List<Long> getKnn(long uid) {
        BidService bidService = new BidService();
        AuctionService auctionService = new AuctionService();
        /* Similarity Map for uid: <userId, similarity> */
        Map<Long, Integer> k_nearestSet = new TreeMap<>();

        /* get all bids that user-uid has placed */
        List<Long> userBids = bidService.getAllUserBids(uid);
        /* for every auction that uid has placed bid */
        for (Long aid : userBids) {
            HashSet<Long> bidderIds = auctionService.getUniqueBidders(aid);
            bidderIds.remove((Long) uid);
            for (Long bidderId : bidderIds) {
                int count = k_nearestSet.containsKey(bidderId) ? k_nearestSet.get(bidderId) : 0;
                k_nearestSet.put(bidderId, count + 1);
            }
        }
        /* Sort the set */
        SortedSet<Map.Entry<Long, Integer>> desc_k_nearest = entriesSortedByValues(k_nearestSet);
        /* Keep the K-Nearest */
        int i = 0;
        List<Long> knn = new ArrayList<>();
        for (Map.Entry<Long, Integer> it : desc_k_nearest) {
            knn.add(it.getKey());
            if (++i > K_Neighbours) {
                break;
            }
        }
        return knn;
    }

    /**
     * Given the KNN and the UserId returns Items_to_recommend (5) auctions.
     * @param uid the logged-in userId
     * @param knn the list of K-Nearest-Neighbours (userIds).
     * @return the list of recommendations
     */
    private List<AuctionEntity> getItems(long uid, List<Long> knn) {
        List<AuctionEntity> recommendationsLst = new ArrayList<>();
        BidService bidService = new BidService();
        AuctionService auctionService = new AuctionService();
        /* get all bids that user-uid has placed */
        List<Long> excludeBids = bidService.getAllUserBids(uid);
        /* exclude all the auctions you are the seller */
        List<AuctionEntity> excludeAuctions = auctionService.getAllAuctions(uid, false);
        int items = 0;
        for (Long n : knn) {
            if (items > Items_to_recommend) {
                break;
            }
            List<Long> biddedItems = bidService.getAllUserBids(n);
            biddedItems.removeAll(excludeBids);
            for (Long it : biddedItems) {
                if (items > Items_to_recommend) {
                    break;
                }
                AuctionEntity auction = auctionService.getAuction(it);
                if (auction.getIsActive() == (byte) 0 || containsId(excludeAuctions, it)) {
                    continue;
                }
                recommendationsLst.add(auction);
                items++;
            }
        }
        return recommendationsLst;
    }

    private boolean containsId(List<AuctionEntity> list, long id) {
        for (AuctionEntity object : list) {
            if (object.getAuctionId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sorts the SortedSet by value descending order
     * @param map All neighbours
     * @param <K> User Ids
     * @param <V> Similarity with the logged-in user
     * @return the sorted by value (descending order) set
     */
    private static <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

}

