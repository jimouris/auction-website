package javauction.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "auction", schema = "auctionwebsite")
public class AuctionEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long auctionId;

    private long sellerId;
    private String name;
    private String description;
    private double lowestBid;
    private double currentBid;
    private double finalPrice;
    private Date startingDate;
    private Date endingDate;
    private String country;
    private String city;
    private Integer numOfBids;
    private Double longtitude;
    private double latitude;
    private Byte isStarted;
    private double buyPrice;

    // used for the javabean
    public AuctionEntity() {
    }

    public AuctionEntity(String name, String description, double lowestBid, String country, String city, double buyPrice, Date startingDate, Byte isStarted, Date endDate, long sellerId) {
        this.name = name;
        this.description = description;
        this.lowestBid = lowestBid;
        this.country = country;
        this.city = city;
        this.startingDate = startingDate;
        this.endingDate = endDate;
        this.isStarted = isStarted;
        this.buyPrice = buyPrice;

        // todo: get the real id of seller
        this.sellerId = sellerId;
    }

    @Id
    @Column(name = "AuctionID")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "SellerID")
    public long getSelledId() {
        return sellerId;
    }

    public void setSelledId(long selledId) {
        this.sellerId = selledId;
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
    @Column(name = "CurrentBid")
    public double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
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
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String location) {
        this.city = location;
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
    @Column(name = "Longtitude")
    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionEntity that = (AuctionEntity) o;

        if (auctionId != that.auctionId) return false;
        if (Double.compare(that.lowestBid, lowestBid) != 0) return false;
        if (Double.compare(that.currentBid, currentBid) != 0) return false;
        if (Double.compare(that.finalPrice, finalPrice) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.buyPrice, buyPrice) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (startingDate != null ? !startingDate.equals(that.startingDate) : that.startingDate != null) return false;
        if (endingDate != null ? !endingDate.equals(that.endingDate) : that.endingDate != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (numOfBids != null ? !numOfBids.equals(that.numOfBids) : that.numOfBids != null) return false;
        if (longtitude != null ? !longtitude.equals(that.longtitude) : that.longtitude != null) return false;
        return isStarted != null ? isStarted.equals(that.isStarted) : that.isStarted == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(lowestBid);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(currentBid);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startingDate != null ? startingDate.hashCode() : 0);
        result = 31 * result + (endingDate != null ? endingDate.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (numOfBids != null ? numOfBids.hashCode() : 0);
        result = 31 * result + (longtitude != null ? longtitude.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isStarted != null ? isStarted.hashCode() : 0);
        temp = Double.doubleToLongBits(buyPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AuctionEntity{" +
                "auctionId=" + auctionId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", lowestBid=" + lowestBid +
                ", currentBid=" + currentBid +
                ", finalPrice=" + finalPrice +
                ", startingDate=" + startingDate +
                ", endingDate=" + endingDate +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", numOfBids=" + numOfBids +
                ", longtitude=" + longtitude +
                ", latitude=" + latitude +
                ", isStarted=" + isStarted +
                ", buyPrice=" + buyPrice +
                '}';
    }
}
