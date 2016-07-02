package javauction.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by jimouris on 7/2/16.
 */
public class RatingEntityPK implements Serializable {
    private long fromId;
    private long toId;

    @Column(name = "FromID")
    @Id
    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    @Column(name = "ToID")
    @Id
    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEntityPK that = (RatingEntityPK) o;

        if (fromId != that.fromId) return false;
        if (toId != that.toId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (fromId ^ (fromId >>> 32));
        result = 31 * result + (int) (toId ^ (toId >>> 32));
        return result;
    }
}
