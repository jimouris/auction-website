package javauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Auto-generated class
 */
@Entity
@Table(name = "auction_has_category", schema = "auctionwebsite")
public class AuctionCategoryEntity {
    private long auctionId;

    @Id
    @Column(name = "AuctionId")
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

        AuctionCategoryEntity that = (AuctionCategoryEntity) o;

        if (auctionId != that.auctionId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (auctionId ^ (auctionId >>> 32));
    }
}
