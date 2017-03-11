package pl.dorobie.rating.domain.model;

public class UpdateRating {
    private String id;
    private String type;
    private int rate;
    private String userId;
    private String customerId;
    private String voterNick;
    private String announceId;
    private List<String> tags;
    private String comment;
    private Date date;

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

    Date getDate() {
        return date
    }

    void setDate(Date date) {
        this.date = date
    }

    String getVoterNick() {
        return voterNick
    }

    void setVoterNick(String voterNick) {
        this.voterNick = voterNick
    }


    @Override
    public String toString() {
        return "UpdateRating{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", rate=" + rate +
                ", userId='" + userId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", voterNick='" + voterNick + '\'' +
                ", announceId='" + announceId + '\'' +
                ", tags=" + tags +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                "} " + super.toString();
    }
}
