package skuc.io.skuciocore.models.authentication;

import skuc.io.skuciocore.models.csm.User;

public class AuthenticatedUser {
    private User user;
    private String token;

    public AuthenticatedUser() {}

    public AuthenticatedUser(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
