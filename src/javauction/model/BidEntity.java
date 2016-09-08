package javauction.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import javauction.util.DateXmlUtil;
import javauction.util.MoneyXmlUtil;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by jimouris on 7/2/16.
 */

@Entity
@Table(name = "bid", schema = "auctionwebsite")
@XStreamAlias("Bid")
public class BidEntity {
    @XStreamOmitField
    private long bidid;
    @XStreamOmitField
    private long bidderId;
    @XStreamAlias("Bidder")
    private UserEntity bidder;
    @XStreamAlias("Time")
    @XStreamConverter(DateXmlUtil.class)
    private Timestamp bidTime;
    @XStreamAlias("Amount")
    @XStreamConverter(MoneyXmlUtil.class)
    private Double amount;
    @XStreamOmitField
    private long auctionId;
    @XStreamOmitField
    private AuctionEntity auction;

    public BidEntity(long bidderId, long auctionId, Double amount) {
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
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

    @OneToOne
    @JoinColumn(name="BidderID", referencedColumnName = "userId", insertable = false, updatable = false)
    public UserEntity getBidder() {
        return bidder;
    }

    public void setBidder(UserEntity seller) {
        this.bidder = seller;
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
        result = (int) (31 * result +  amount);
        return result;
    }

    @Override
    public String toString() {
        return "BidEntity{" +
                "bidid=" + bidid +
                ", bidderId=" + bidderId +
                ", bidder=" + bidder +
                ", bidTime=" + bidTime +
                ", amount=" + amount +
                ", auctionId=" + auctionId +
                ", auction=" + auction +
                '}';
    }
}
