package model;

import java.util.List;

/**
 * @author Kyle Richards
 * @version 1.0
 *
 * A single page model, encapsulating tweets and a message for the scala render
 *
 * Passed to the template.
 */
public final class PageModel {
    private final List<Message> messages;
    private final User user;
    private final String message;

    public PageModel(List<Message> messages, User user, String message) {
        this.messages = messages;
        this.user = user;
        this.message = message;
    }

    public final List<Message> getMessages() {
        return messages;
    }

    public final User getUser() {
        return user;
    }

    public final String getMessage() {
        return message;
    }

    public final boolean isLoggedIn() {
        return user != null;
    }

    public final boolean gotTweets() {
        return !message.isEmpty();
    }
}
