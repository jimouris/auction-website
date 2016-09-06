package javauction.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by gpelelis on 24/8/2016.
 */
@Entity
@Table(name = "notification", schema = "auctionwebsite")
public class NotificationEntity {
    private long notificationId;
    private String type;
    private long auctionId;
    private Long messageId;
    private long receiverId;
    private long actorId;
    private Byte isSeen;
    private Timestamp DateAdded;

    private UserEntity actor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ActorID", nullable = false, insertable = false, updatable = false)
    public UserEntity getActor() {
        return actor;
    }
    public void setActor(UserEntity actor) {
        this.actor = actor;
    }

    public NotificationEntity(long rid, String message, long aid, long sid, Long mid) {
        this.receiverId = rid;
        this.type = message;
        this.auctionId = aid;
        this.actorId = sid;
        this.isSeen = 0;
        Calendar cal = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        this.DateAdded = timestamp;
        this.messageId = mid;
    }

    public NotificationEntity(long rid, String message, long aid, long sid) {
        this.receiverId = rid;
        this.type = message;
        this.auctionId = aid;
        this.actorId = sid;
        this.isSeen = 0;
        Calendar cal = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        this.DateAdded = timestamp;
    }

    public NotificationEntity() {
    }

    @Id
    @Column(name = "NotificationID", nullable = false)
    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    @Basic
    @Column(name = "Type", nullable = false, length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "AuctionID", nullable = false)
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "MessageID", nullable = true)
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "ReceiverID", nullable = false)
    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    @Basic
    @Column(name = "ActorID", nullable = false)
    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    @Basic
    @Column(name = "isSeen", nullable = true)
    public Byte getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Byte isSeen) {
        this.isSeen = isSeen;
    }

    @Basic
    @Column(name = "DateAdded")
    public Timestamp getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        DateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "notificationId=" + notificationId +
                ", type='" + type + '\'' +
                ", auctionId=" + auctionId +
                ", messageId=" + messageId +
                ", receiverId=" + receiverId +
                ", actorId=" + actorId +
                ", isSeen=" + isSeen +
                ", DateAdded=" + DateAdded +
                ", actor=" + actor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationEntity that = (NotificationEntity) o;

        if (notificationId != that.notificationId) return false;
        if (auctionId != that.auctionId) return false;
        if (receiverId != that.receiverId) return false;
        if (actorId != that.actorId) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (isSeen != null ? !isSeen.equals(that.isSeen) : that.isSeen != null) return false;
        if (DateAdded != null ? !DateAdded.equals(that.DateAdded) : that.DateAdded != null) return false;
        return actor != null ? actor.equals(that.actor) : that.actor == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (notificationId ^ (notificationId >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + (messageId != null ? messageId.hashCode() : 0);
        result = 31 * result + (int) (receiverId ^ (receiverId >>> 32));
        result = 31 * result + (int) (actorId ^ (actorId >>> 32));
        result = 31 * result + (isSeen != null ? isSeen.hashCode() : 0);
        result = 31 * result + (DateAdded != null ? DateAdded.hashCode() : 0);
        result = 31 * result + (actor != null ? actor.hashCode() : 0);
        return result;
    }
}
