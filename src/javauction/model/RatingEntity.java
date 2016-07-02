package javauction.model;

import javax.persistence.*;

/**
 * Created by jimouris on 7/2/16.
 */
@Entity
@Table(name = "rating", schema = "auction-website", catalog = "")
@IdClass(RatingEntityPK.class)
public class RatingEntity {
    private long fromId;
    private long toId;
    private int rate;

    @Id
    @Column(name = "FromID")
    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    @Id
    @Column(name = "ToID")
    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    @Basic
    @Column(name = "Rate")
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEntity that = (RatingEntity) o;

        if (fromId != that.fromId) return false;
        if (toId != that.toId) return false;
        if (rate != that.rate) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (fromId ^ (fromId >>> 32));
        result = 31 * result + (int) (toId ^ (toId >>> 32));
        result = 31 * result + rate;
        return result;
    }
}
