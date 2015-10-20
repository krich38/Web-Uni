package model;

import java.util.Date;


/**
 * @author Kyle Richards
 * @version 1.0
 *
 * A single user of the system
 */
public final class User {
    private final String username;
    private final String email;
    private final String password;
    private boolean loggedIn;
    private Date lastPosted;
    private String IP;
    public static final int SECONDS_BETWEEN_TWEETS = 5;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public final String getPassword() {
        return password;
    }

    public final String getEmail() {
        return email;
    }

    public final String getUsername() {
        return username;
    }

    public final void updatePosted() {
        lastPosted = new Date();
    }

    public final boolean canPost() {
        if (lastPosted == null) {
            return true;
        }
        Date now = new Date();
        long diffMs = now.getTime() - lastPosted.getTime();
        long diffSec = diffMs / 1000;
        long sec = diffSec % 60;
        return sec > SECONDS_BETWEEN_TWEETS;
    }

    public final void setIP(String IP) {
        this.IP = IP;
    }

    public final String getIP() {
        return IP;
    }

    public final boolean equals(Object o) {
        if (o instanceof User) {
            User user = (User) o;
            return user.getUsername().equalsIgnoreCase(username);
        }
        return false;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
