package javauction.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "bid", schema = "auction-website", catalog = "")
@IdClass(BidEntityPK.class)
public class BidEntity {
    private long bidderId;
    private long auctionId;
    private Date bidTime;
    private int amount;

    @Id
    @Column(name = "BidderID")
    public long getBidderId() {
        return bidderId;
    }

    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    @Id
    @Column(name = "AuctionID")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "BidTime")
    public Date getBidTime() {
        return bidTime;
    }

    public void setBidTime(Date bidTime) {
        this.bidTime = bidTime;
    }

    @Basic
    @Column(name = "Amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidEntity bidEntity = (BidEntity) o;

        if (bidderId != bidEntity.bidderId) return false;
        if (auctionId != bidEntity.auctionId) return false;
        if (amount != bidEntity.amount) return false;
        if (bidTime != null ? !bidTime.equals(bidEntity.bidTime) : bidEntity.bidTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (bidderId ^ (bidderId >>> 32));
        result = 31 * result + (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (bidTime != null ? bidTime.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }
}
