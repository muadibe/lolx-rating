package pl.dorobie.rating.rest.model

import org.hibernate.validator.constraints.NotEmpty

public class UpdateRatingMessage {
    private String id
    @NotEmpty
    private String type
    private int rate
    @NotEmpty
    private String userId
    @NotEmpty
    private String customerId
    @NotEmpty
    private String announceId
    private List<String> tags
    private String comment
    private String voterNick

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(String announceId) {
        this.announceId = announceId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    String getVoterNick() {
        return voterNick
    }

    void setVoterNick(String voterNick) {
        this.voterNick = voterNick
    }
}
