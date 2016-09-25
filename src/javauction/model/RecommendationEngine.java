package javauction.model;

import javauction.service.AuctionService;
import javauction.service.BidService;

import java.util.HashSet;
import java.util.List;

public class RecommendationEngine {

    public void createNeighborhood(long uid) {
        BidService bidService = new BidService();
        AuctionService auctionService = new AuctionService();

        /* get all bids that user-uid has placed */
        List<Long> userBids = bidService.getAllUserBids(uid);
        /* for every auction */
        for (Long aid : userBids) {
            System.out.println("Aid: "+aid);
            HashSet<Long> bidderIds = auctionService.getUniqueBidders(aid);
            System.out.println("\tBidders:");
            for (Long bidderId : bidderIds) {
                System.out.println("\t\t"+ bidderId);
            }
        }

    }


}
