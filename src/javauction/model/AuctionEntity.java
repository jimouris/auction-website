package javauction.model;

import com.thoughtworks.xstream.annotations.*;
import javauction.util.CategoryXmlUtil;
import javauction.util.DateXmlUtil;
import javauction.util.MoneyXmlUtil;
import javauction.util.UserXmlUtil;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Set;

/**
 * Class that describes an auction entity. (An item for sale).
 */
@Entity
@Table(name = "auction", schema = "auctionwebsite")
@XStreamAlias("Item")
public class AuctionEntity {

    @XStreamAlias("ItemID")
    @XStreamAsAttribute
    private long auctionId;
    @XStreamOmitField
    private Long sellerId;
    @XStreamOmitField
    private Long buyerId;
    @XStreamAlias("Name")
    private String name;
    @XStreamImplicit(itemFieldName = "Category")
    @XStreamConverter(CategoryXmlUtil.class)
    private Set<CategoryEntity> categories;
    @Transient
    @XStreamAlias("Currently")
    @XStreamConverter(MoneyXmlUtil.class)
    private Double currently;
    @XStreamAlias("First_Bid")
    @XStreamConverter(MoneyXmlUtil.class)
    private double lowestBid;
    @Transient
    @XStreamAlias("Number_of_Bids")
    private int numOfBids;
    @XStreamAlias("Bids")
    private Set<BidEntity> bids;
    @XStreamOmitField
    private Set<ItemImageEntity> images;
    @XStreamAlias("Location")
    private String location;
    @XStreamAlias("Country")
    private String country;
    @XStreamOmitField
    private double longitude;
    @XStreamOmitField
    private double latitude;
    @XStreamOmitField
    private Byte isActive;
    @XStreamAlias("Buy_Price")
    @XStreamConverter(MoneyXmlUtil.class)
    private Double buyPrice;
    @XStreamAlias("Started")
    @XStreamConverter(DateXmlUtil.class)
    private Timestamp startingDate;
    @XStreamAlias("Ends")
    @XStreamConverter(DateXmlUtil.class)
    private Timestamp endingDate;
    @XStreamAlias("Description")
    private String description;
    @XStreamAlias("Seller")
    @XStreamConverter(UserXmlUtil.class)
    private UserEntity seller;

    public AuctionEntity() {}

    public AuctionEntity(String name, long sellerId, String description, double lowestBid, String location, String country, Timestamp startingDate, Byte isActive, Timestamp endDate) {
        this.name = name;
        this.sellerId = sellerId;
        this.description = description;
        this.lowestBid = lowestBid;
        this.location = location;
        this.country = country;
        this.startingDate = startingDate;
        this.endingDate = endDate;
        this.isActive = isActive;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AuctionID")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "SellerID")
    public Long getSellerId() { return sellerId; }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Basic
    @Column(name = "BuyerID")
    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
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
    @Column(name = "Description", nullable = true)
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
    @Column(name = "StartingDate")
    public Timestamp getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Timestamp startingDate) {
        this.startingDate = startingDate;
    }

    @Basic
    @Column(name = "EndingDate")
    public Timestamp getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Timestamp endingDate) {
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
    @Column(name = "isActive")
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "BuyPrice", nullable = true)
    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) { this.buyPrice = buyPrice; }

    @ManyToMany(targetEntity = CategoryEntity.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "auction_has_category",
            joinColumns = { @JoinColumn(name = "auction_AuctionID") },
            inverseJoinColumns = { @JoinColumn(name = "category_CategoryID") })
    public Set<CategoryEntity> getCategories(){
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    @OneToMany(targetEntity = BidEntity.class, mappedBy = "auction", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("amount DESC")
    public Set<BidEntity> getBids() {
        return bids;
    }

    public void setBids(Set<BidEntity> bids) {
        this.bids = bids;
    }

    @OneToMany(targetEntity = ItemImageEntity.class, mappedBy = "auction",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<ItemImageEntity> getImages() {
        return images;
    }

    public void setImages(Set<ItemImageEntity> images) {
        this.images = images;
    }

    @ManyToOne
    @JoinColumn(name="SellerID", nullable = false, insertable = false, updatable = false)
    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity seller) {
        this.seller = seller;
    }

    public void setBidStuff(){
        if ( bids.size() > 0 ){
            Iterator iter = bids.iterator();
            numOfBids = bids.size();
            BidEntity bid = (BidEntity) iter.next();
            currently = bid.getAmount();
            // this will compute the sum for user when is a bidder
            bid.getBidder().setRatingAs("bidder");
        } else{
            numOfBids = 0;
            currently = lowestBid;
        }
    }

    @Override
    public String toString() {
        return "AuctionEntity{" +
                "auctionId=" + auctionId +
                ", sellerId=" + sellerId +
                ", buyerId=" + buyerId +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                ", lowestBid=" + lowestBid +
                ", bids=" + bids +
                ", images=" + images +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", isActive=" + isActive +
                ", buyPrice=" + buyPrice +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Transient
    public Long getIdOfHighestBidder(){
        Long BidderID = null;
        Double highestBid = 0.0;
        for (BidEntity bid : bids ) {
            if (bid.getAmount() > highestBid){
                BidderID = bid.getBidderId();
                highestBid = bid.getAmount();
            }
        }
        return BidderID;
    }
}
