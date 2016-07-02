package javauction.model;

import javax.persistence.*;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "itemImage", schema = "auction-website", catalog = "")
@IdClass(ItemImageEntityPK.class)
public class ItemImageEntity {
    private long auctionId;
    private String imageFileName;

    @Id
    @Column(name = "AuctionId")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Id
    @Column(name = "ImageFileName")
    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemImageEntity that = (ItemImageEntity) o;

        if (auctionId != that.auctionId) return false;
        if (imageFileName != null ? !imageFileName.equals(that.imageFileName) : that.imageFileName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (imageFileName != null ? imageFileName.hashCode() : 0);
        return result;
    }
}
