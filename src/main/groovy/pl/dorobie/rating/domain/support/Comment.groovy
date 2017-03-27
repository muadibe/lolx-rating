package pl.dorobie.rating.domain.support;

public class Comment {
    private String nick
    private String userId
    private String msg
    private int stars
    private String date

    String getNick() {
        return nick
    }

    void setNick(String nick) {
        this.nick = nick
    }

    String getUserId() {
        return userId
    }

    void setUserId(String userId) {
        this.userId = userId
    }

    String getMsg() {
        return msg
    }

    void setMsg(String msg) {
        this.msg = msg
    }

    int getStars() {
        return stars
    }

    void setStars(int stars) {
        this.stars = stars
    }

    String getDate() {
        return date
    }

    void setDate(String date) {
        this.date = date
    }
}
