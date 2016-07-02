package javauction.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by jimouris on 7/2/16.
 */
public class BidEntityPK implements Serializable {
    private long bidderId;
    private long auctionId;

    @Column(name = "BidderID")
    @Id
    public long getBidderId() {
        return bidderId;
    }

    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    @Column(name = "AuctionID")
    @Id
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidEntityPK that = (BidEntityPK) o;

        if (bidderId != that.bidderId) return false;
        if (auctionId != that.auctionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (bidderId ^ (bidderId >>> 32));
        result = 31 * result + (int) (auctionId ^ (auctionId >>> 32));
        return result;
    }
}
