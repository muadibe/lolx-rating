package pl.dorobie.rating.domain.support;

public class Comment {
    private String nick
    private String msg
    private int stars

    String getNick() {
        return nick
    }

    void setNick(String nick) {
        this.nick = nick
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
}
