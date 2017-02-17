package pl.dorobie.rating.rest.model

public class VoteMessage {
    private int like
    private float starRate
    private String comment

    int getLike() {
        return like
    }

    void setLike(int like) {
        this.like = like
    }

    String getComment() {
        return comment
    }

    void setComment(String comment) {
        this.comment = comment
    }

    float getStarRate() {
        return starRate
    }

    void setStarRate(float starRate) {
        this.starRate = starRate
    }


    @Override
    public String toString() {
        return "Vote{" +
                "like=" + like +
                ", starRate=" + starRate +
                ", comment='" + comment + '\'' +
                "} " + super.toString();
    }
}
