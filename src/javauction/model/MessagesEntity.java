package javauction.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "messages", schema = "auctionwebsite", catalog = "")
public class MessagesEntity {
    private long messageId;
    private String message;
    private Date receivedDate;
    private byte isRead;

    @Id
    @Column(name = "MessageID")
    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
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
    @Column(name = "ReceivedDate")
    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
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
        if (isRead != that.isRead) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (receivedDate != null ? !receivedDate.equals(that.receivedDate) : that.receivedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (messageId ^ (messageId >>> 32));
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
        result = 31 * result + (int) isRead;
        return result;
    }
}
