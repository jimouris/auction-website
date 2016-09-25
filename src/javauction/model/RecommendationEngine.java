package javauction.model;

import javauction.service.AuctionService;
import javauction.service.BidService;
import java.util.*;

public class RecommendationEngine {

    public static int K_Neighbours = 5;
    private int Items_to_recommend = 5;

    public List<AuctionEntity> getRecommendations(long uid) {
        List<Long> knn = getKnn(uid);
        return getItems(uid, knn);
    }

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
        SortedSet<Map.Entry<Long, Integer>> descSimilarity = entriesSortedByValues(k_nearestSet);
        System.out.println(descSimilarity);
        /* Keep the K-Nearest */
        int i = 0;
        List<Long> knn = new ArrayList<>();
        for (Map.Entry<Long, Integer> it : descSimilarity) {
            knn.add(it.getKey());
            if (++i > K_Neighbours) {
                break;
            }
        }
        return knn;
    }

    private List<AuctionEntity> getItems(long uid, List<Long> knn) {
        HashSet<Long> recommendationSet = new HashSet<>();
        BidService bidService = new BidService();
        AuctionService auctionService = new AuctionService();
        /* get all bids that user-uid has placed */
        List<Long> excludeBids = bidService.getAllUserBids(uid);
        int items = 0;
        for (Long n : knn) {
            List<Long> biddedItems = bidService.getAllUserBids(n);
            biddedItems.removeAll(excludeBids);
            for (Long it : biddedItems) {
                if (++items > Items_to_recommend) {
                    break;
                }
                recommendationSet.add(it);
            }
        }

        List<AuctionEntity> recommendations = new ArrayList<>();
        for (Long ae: recommendationSet) {
            recommendations.add(auctionService.getAuction(ae));
        }

        return recommendations;
    }

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

