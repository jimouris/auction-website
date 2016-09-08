package javauction.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import javax.persistence.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "user", schema = "auctionwebsite")
public class UserEntity {

    @XStreamOmitField
    private long userId;

    @XStreamAlias("UserID")
    @XStreamAsAttribute
    private String username;
    @XStreamOmitField
    private byte[] hash;
    @XStreamOmitField
    private byte[] salt;
    @XStreamOmitField
    private String firstname;
    @XStreamOmitField
    private String lastname;
    @XStreamOmitField
    private String email;
    @XStreamOmitField
    private String phoneNumber;
    @XStreamOmitField
    private String vat;
    @XStreamAlias("Location")
    private String homeAddress;
    @XStreamOmitField
    private String latitude;
    @XStreamOmitField
    private String longitude;
    @XStreamOmitField
    private String city;
    @XStreamAlias("Country")
    private String country;
    @XStreamOmitField
    private Date signUpDate;
    @XStreamOmitField
    private byte isAdmin;
    @XStreamOmitField
    private byte isApproved;
    @XStreamOmitField
    private Set<RatingEntity> rating;
    @XStreamOmitField
    private Set<NotificationEntity> notifications;
    @XStreamOmitField
    private Set<AuctionEntity> auctions;
    @XStreamAlias("Rating")
    @XStreamAsAttribute
    private Integer ratingAsBidder = 0;
    @XStreamOmitField
    private Integer ratingAsSeller = 0;

    public UserEntity(String username, byte[] hash, byte[] salt, String firstname, String lastname, String email, String phoneNumber, String vat,
                      String homeAddress, String latitude, String longitude, String city, String country) {
        this.username = username;
        this.hash = hash;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.vat = vat;
        this.homeAddress = homeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.country = country;
        this.isAdmin = 0;
        this.isApproved = 0;
        java.util.Date currentDate = new java.util.Date(System.currentTimeMillis());
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        this.signUpDate = sqlDate;
    }

    public UserEntity(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "UserID")
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "Hash")
    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "Salt")
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "Firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "Lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "Vat")
    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    @Basic
    @Column(name = "HomeAddress")
    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Basic
    @Column(name = "Latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
    @Column(name = "SignUpDate")
    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    @Basic
    @Column(name = "IsAdmin")
    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Basic
    @Column(name = "isApproved")
    public byte getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(byte isApproved) {
        this.isApproved = isApproved;
    }


    @OneToMany(targetEntity = NotificationEntity.class, mappedBy = "actor", fetch = FetchType.EAGER)
    @OrderBy("dateAdded DESC")
    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seller")
    public Set<AuctionEntity> getAuctions() {
        return auctions;
    }

    public void setAuctions(Set<AuctionEntity> auctions) {
        this.auctions = auctions;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "receiver")
    public Set<RatingEntity> getRating() {
        return rating;
    }

    public void setRating(Set<RatingEntity> rating) {
        this.rating = rating;
    }

    @Transient
    public Integer getRatingAsBidder() {
        return ratingAsBidder;
    }

    public void setRatingAsBidder(Integer ratingAsBidder) {
        this.ratingAsBidder = ratingAsBidder;
    }

    @Transient
    public Integer getRatingAsSeller() {
        return ratingAsSeller;
    }

    public void setRatingAsSeller(Integer ratingAsSeller) {
        this.ratingAsSeller = ratingAsSeller;
    }

    public void setRatingAs(String usr_type){
        if ( rating.size() > 0 ) {
            if (usr_type.equals("seller")){
                this.ratingAsSeller = 0; // do this because
                for (RatingEntity r : rating)
                    this.ratingAsSeller += r.getIsSeller() == 1 ? r.getRating() : 0;
            }
            else if (usr_type.equals("bidder")){
                this.ratingAsBidder = 0; // do this because
                for (RatingEntity r : rating)
                    this.ratingAsBidder += r.getIsSeller() == 0 ? r.getRating() : 0;
            }



        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (isAdmin != that.isAdmin) return false;
        if (isApproved != that.isApproved) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (hash != null ? !hash.equals(that.hash) : that.hash != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (vat != null ? !vat.equals(that.vat) : that.vat != null) return false;
        if (homeAddress != null ? !homeAddress.equals(that.homeAddress) : that.homeAddress != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (signUpDate != null ? !signUpDate.equals(that.signUpDate) : that.signUpDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (vat != null ? vat.hashCode() : 0);
        result = 31 * result + (homeAddress != null ? homeAddress.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (signUpDate != null ? signUpDate.hashCode() : 0);
        result = 31 * result + (int) isAdmin;
        result = 31 * result + (int) isApproved;
        return result;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", hash=" + Arrays.toString(hash) +
                ", salt=" + Arrays.toString(salt) +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", vat='" + vat + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", signUpDate=" + signUpDate +
                ", isAdmin=" + isAdmin +
                ", isApproved=" + isApproved +
//                ", rating=" + rating +
                ", notifications=" + notifications +
                '}';
    }
}
