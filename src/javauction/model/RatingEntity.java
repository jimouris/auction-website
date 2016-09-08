package javauction.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "rating", schema = "auctionwebsite")
public class RatingEntity implements Serializable {
    private long fromId;
    private long toId;
    private long auctionId;
    private int rating;
    private Byte isSeller; // the toID attribute is the id of seller
    private UserEntity receiver;

    public RatingEntity(long from_id, long to_id, long aid, int rating, Byte forSeller) {
        this.fromId = from_id;
        this.toId = to_id;
        this.auctionId = aid;
        this.rating = rating;
        this.isSeller = forSeller;
    }

    public RatingEntity() {
    }

    @Id
    @Column(name = "FromID")
    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    @Id
    @Column(name = "ToID")
    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    @Id
    @Column(name = "AuctionId")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "Rating")
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @ManyToOne
    @JoinColumn(name="ToID", insertable = false, updatable = false)
    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity user) {
        this.receiver = user;
    }

    @Basic
    @Column(name = "isSeller")
    public Byte getIsSeller() {
        return isSeller;
    }

    public void setIsSeller(Byte isSeller) {
        this.isSeller = isSeller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEntity that = (RatingEntity) o;

        if (fromId != that.fromId) return false;
        if (toId != that.toId) return false;
        if (auctionId != that.auctionId) return false;
        return rating == that.rating;

    }

    @Override
    public int hashCode() {
        int result = (int) (fromId ^ (fromId >>> 32));
        result = 31 * result + (int) (toId ^ (toId >>> 32));
        result = 31 * result + (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + rating;
        return result;
    }

    @Override
    public String toString() {
        return "RatingEntity{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", auctionId=" + auctionId +
                ", rating=" + rating +
                ", isSeller=" + isSeller +
                '}';
    }
}
