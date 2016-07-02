package javauction.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by jimouris on 7/2/16.
 */
public class ItemImageEntityPK implements Serializable {
    private long auctionId;
    private String imageFileName;

    @Column(name = "AuctionId")
    @Id
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Column(name = "ImageFileName")
    @Id
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

        ItemImageEntityPK that = (ItemImageEntityPK) o;

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
