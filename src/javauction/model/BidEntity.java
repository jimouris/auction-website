package javauction.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by jimouris on 7/2/16.
 */

@Entity
@Table(name = "bid", schema = "auctionwebsite")
public class BidEntity {
    private long bidid;
    private long bidderId;
    private Timestamp bidTime;
    private float amount;
    private long auctionId;
    private AuctionEntity auction;

    public BidEntity(long bidderId, long auctionId, float amount) {
        this.bidderId = bidderId;
        this.auctionId = auctionId;
        this.amount = amount;
        Calendar cal = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        this.bidTime = timestamp;
    }

    public BidEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Bid")
    public long getBidid() {
        return bidid;
    }

    public void setBidid(long bidid) {
        this.bidid = bidid;
    }

    @Basic
    @Column(name = "BidderID")
    public long getBidderId() {
        return bidderId;
    }

    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    @Basic
    @Column(name = "AuctionID")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "BidTime")
    public Timestamp getBidTime() {
        return bidTime;
    }

    public void setBidTime(Timestamp bidTime) {
        this.bidTime = bidTime;
    }

    @Basic
    @Column(name = "Amount")
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @ManyToOne
    @JoinColumn(name="AuctionID", nullable = false, insertable = false, updatable = false)
    public AuctionEntity getAuction() {
        return auction;
    }

    public void setAuction(AuctionEntity auction) {
        this.auction = auction;
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
        result = 31 * result + (int) amount;
        return result;
    }
}
