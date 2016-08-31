package javauction.model;

import com.thoughtworks.xstream.annotations.*;
import javauction.util.CategoryXmlUtil;
import javauction.util.MoneyXmlUtil;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "auction", schema = "auctionwebsite")
@XStreamAlias("Item")
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AuctionID")
    @XStreamAlias("ItemID")
    @XStreamAsAttribute
    private long auctionId;
    @XStreamOmitField
    private Long sellerId;
    @XStreamOmitField
    private Long buyerId;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("First_Bid")
    @XStreamConverter(MoneyXmlUtil.class)
    private double lowestBid;
    @XStreamOmitField
    private double finalPrice;
    private Date startingDate;
    private Date endingDate;
    @XStreamAlias("Location")
    private String location;
    @XStreamAlias("Country")
    private String country;
    private double longitude;
    private double latitude;
    private Integer numOfBids;
    @XStreamOmitField
    private Byte isStarted;
    private double buyPrice;

    @ManyToMany(targetEntity = CategoryEntity.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "auction_has_category",
            joinColumns = { @JoinColumn(name = "auction_AuctionID") },
            inverseJoinColumns = { @JoinColumn(name = "category_CategoryID") })
    @XStreamImplicit(itemFieldName = "Category")
    @XStreamConverter(CategoryXmlUtil.class)
    private Set<CategoryEntity> categories;

    @OneToMany(targetEntity = BidEntity.class, mappedBy = "auction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("amount DESC")
    @XStreamAlias("Bids")
    private Set<BidEntity> bids;

    @OneToMany(targetEntity = ItemImageEntity.class, mappedBy = "auction",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @XStreamOmitField
    private Set<ItemImageEntity> images;

    @XStreamAlias("Description")
    private String description;


    public AuctionEntity() {
    }

    public AuctionEntity(String name, long sellerId, String description, double lowestBid, String location, String country, double buyPrice, Date startingDate, Byte isStarted, Date endDate) {
        this.name = name;
        this.sellerId = sellerId;
        this.description = description;
        this.lowestBid = lowestBid;
        this.location = location;
        this.country = country;
        this.startingDate = startingDate;
        this.endingDate = endDate;
        this.isStarted = isStarted;
        this.buyPrice = buyPrice;
    }

    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "SellerID")
    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "BuyerID")
    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "LowestBid")
    public double getLowestBid() {
        return lowestBid;
    }

    public void setLowestBid(double lowestBid) {
        this.lowestBid = lowestBid;
    }

    @Basic
    @Column(name = "FinalPrice")
    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Basic
    @Column(name = "StartingDate")
    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    @Basic
    @Column(name = "EndingDate")
    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    @Basic
    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Basic
    @Column(name = "NumOfBids")
    public Integer getNumOfBids() {
        return numOfBids;
    }

    public void setNumOfBids(Integer numOfBids) {
        this.numOfBids = numOfBids;
    }

    @Basic
    @Column(name = "Longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "Latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "IsStarted")
    public Byte getIsStarted() {
        return isStarted;
    }

    public void setIsStarted(Byte isStarted) {
        this.isStarted = isStarted;
    }

    @Basic
    @Column(name = "BuyPrice")
    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) { this.buyPrice = buyPrice; }

    public Set<CategoryEntity> getCategories(){
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public Set<BidEntity> getBids() {
        return bids;
    }

    public void setBids(Set<BidEntity> bids) {
        this.bids = bids;
    }

    public Set<ItemImageEntity> getImages() {
        return images;
    }

    public void setImages(Set<ItemImageEntity> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionEntity that = (AuctionEntity) o;

        if (auctionId != that.auctionId) return false;
        if (Double.compare(that.lowestBid, lowestBid) != 0) return false;
        if (Double.compare(that.finalPrice, finalPrice) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.buyPrice, buyPrice) != 0) return false;
        if (sellerId != null ? !sellerId.equals(that.sellerId) : that.sellerId != null) return false;
        if (buyerId != null ? !buyerId.equals(that.buyerId) : that.buyerId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (startingDate != null ? !startingDate.equals(that.startingDate) : that.startingDate != null) return false;
        if (endingDate != null ? !endingDate.equals(that.endingDate) : that.endingDate != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (numOfBids != null ? !numOfBids.equals(that.numOfBids) : that.numOfBids != null) return false;
        if (isStarted != null ? !isStarted.equals(that.isStarted) : that.isStarted != null) return false;
        if (categories != null ? !categories.equals(that.categories) : that.categories != null) return false;
        return bids != null ? bids.equals(that.bids) : that.bids == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (sellerId != null ? sellerId.hashCode() : 0);
        result = 31 * result + (buyerId != null ? buyerId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(lowestBid);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startingDate != null ? startingDate.hashCode() : 0);
        result = 31 * result + (endingDate != null ? endingDate.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (numOfBids != null ? numOfBids.hashCode() : 0);
        result = 31 * result + (isStarted != null ? isStarted.hashCode() : 0);
        temp = Double.doubleToLongBits(buyPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (bids != null ? bids.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuctionEntity{" +
                "auctionId=" + auctionId +
                ", sellerId=" + sellerId +
                ", buyerId=" + buyerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", lowestBid=" + lowestBid +
                ", finalPrice=" + finalPrice +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", numOfBids=" + numOfBids +
                ", isStarted=" + isStarted +
                ", buyPrice=" + buyPrice +
                ", categories=" + categories +
                ", bids=" + bids +
                '}';
    }
}
