package pl.dorobie.rating.domain.model

class UserDetails {

    private String id
    private String nick
    private String email

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getNick() {
        return nick
    }

    void setNick(String nick) {
        this.nick = nick
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }


    @Override
    public String toString() {
        return "OAuthUserDetails{" +
                "id='" + id + '\'' +
                ", nick='" + nick + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
