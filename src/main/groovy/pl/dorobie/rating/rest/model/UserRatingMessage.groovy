package pl.dorobie.rating.rest.model

import pl.dorobie.rating.domain.support.Comment

public class UserRatingMessage {
    private String id
    private int likeCount
    private float starRate
    private int starRateCount
    private List<Comment> lastComments

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

    int getStarRateCount() {
        return starRateCount
    }

    void setStarRateCount(int starRateCount) {
        this.starRateCount = starRateCount
    }

    List<Comment> getLastComments() {
        return lastComments
    }

    void setLastComments(List<Comment> lastComments) {
        this.lastComments = lastComments
    }
}
