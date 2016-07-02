package javauction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "auctionCategory", schema = "auction-website", catalog = "")
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
