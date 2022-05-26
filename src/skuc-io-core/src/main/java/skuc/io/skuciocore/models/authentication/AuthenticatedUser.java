package skuc.io.skuciocore.models.authentication;

import skuc.io.skuciocore.models.csm.User;

public class AuthenticatedUser {
    private User user;
    private Token token;

    public AuthenticatedUser() {}

    public AuthenticatedUser(User user, Token token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
