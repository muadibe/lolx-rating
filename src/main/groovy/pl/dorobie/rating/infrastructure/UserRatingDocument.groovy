package pl.dorobie.rating.infrastructure

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
public class UserRatingDocument {
    @Id
    private String id
    private int likeCount
    private float starRate
    private long starRateSum
    private long starRateCount

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getStarRate() {
        return starRate;
    }

    public void setStarRate(float starRate) {
        this.starRate = starRate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    long getStarRateSum() {
        return starRateSum
    }

    void setStarRateSum(long starRateSum) {
        this.starRateSum = starRateSum
    }

    long getStarRateCount() {
        return starRateCount
    }

    void setStarRateCount(long starRateCount) {
        this.starRateCount = starRateCount
    }
}
