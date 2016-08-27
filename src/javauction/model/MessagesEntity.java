package javauction.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "messages", schema = "auctionwebsite")
public class MessagesEntity {
    private long messageId;
    private long senderId;
    private long receiverId;
    private long auctionId;
    private String message;
    private Timestamp sendDate;
    private byte isRead;

    public MessagesEntity(long sid, long rid, long aid, String msg) {
        this.senderId = sid;
        this.receiverId = rid;
        this.auctionId = aid;
        this.message = msg;
        Calendar cal = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        this.sendDate = timestamp;
    }

    public MessagesEntity() {
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) /* use this in order to get the right id after session.save() */
    @Column(name = "MessageID")
    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name="SenderID")
    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    @Basic
    @Column(name="ReceiverID")
    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    @Basic
    @Column(name="AuctionID")
    public long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(long auctionId) {
        this.auctionId = auctionId;
    }

    @Basic
    @Column(name = "Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "SendDate")
    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    @Basic
    @Column(name = "IsRead")
    public byte getIsRead() {
        return isRead;
    }

    public void setIsRead(byte isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagesEntity that = (MessagesEntity) o;

        if (messageId != that.messageId) return false;
        if (senderId != that.senderId) return false;
        if (receiverId != that.receiverId) return false;
        if (auctionId != that.auctionId) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (sendDate != null ? !sendDate.equals(that.sendDate) : that.sendDate != null) return false;
        if (isRead != that.isRead) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (messageId ^ (messageId >>> 32));
        result = 31 * result + (int) (senderId ^ (senderId >>> 32));
        result = 31 * result + (int) (receiverId ^ (receiverId >>> 32));
        result = 31 * result + (int) (auctionId ^ (auctionId >>> 32));
        result = 31 * result + message.hashCode();
        result = 31 * result + sendDate.hashCode();
        result = 31 * result + (int) isRead;
        return result;
    }

    @Override
    public String toString() {
        return "MessagesEntity{" +
                "messageId=" + messageId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", auctionId=" + auctionId +
                ", message='" + message + '\'' +
                ", sendDate=" + sendDate +
                ", isRead=" + isRead +
                '}';
    }
}
