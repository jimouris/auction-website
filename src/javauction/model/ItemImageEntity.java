package javauction.model;

import javax.persistence.*;

/**
 * Class that describes an image of an item for sale.
 */
@Entity
@Table(name = "itemimage", schema = "auctionwebsite")
public class ItemImageEntity {
    private long itemImageId;
    private long auctionId;
    private String imageFileName;
    private AuctionEntity auction;

    public ItemImageEntity(String fileName, long auctionId) {
        this.imageFileName = fileName;
        this.auctionId = auctionId;
    }

    public ItemImageEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ItemImageId")
    public long getItemImageId() {
        return itemImageId;
    }

    public void setItemImageId(long itemImageId) {
        this.itemImageId = itemImageId;
    }

    @Basic
    @Column(name = "AuctionId")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "ImageFileName")
    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
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

        ItemImageEntity that = (ItemImageEntity) o;

        if (itemImageId != that.itemImageId) return false;
        if (auctionId != that.auctionId) return false;
        return imageFileName != null ? imageFileName.equals(that.imageFileName) : that.imageFileName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (itemImageId ^ (itemImageId >>> 32));
        result = 31 * result + (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (imageFileName != null ? imageFileName.hashCode() : 0);
        return result;
    }
}
