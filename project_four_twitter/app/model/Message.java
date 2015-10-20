package model;

import play.data.validation.Constraints;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kyle Richards
 * @version 1.0
 *
 * Encapsulate a single tweet
 */
public final class Message {
    private String ID; // UUID
    private final Date date;
    private final Set<String> tags;
    private final String[] parts;
    @Constraints.Required
    @Constraints.MaxLength(140)
    public final String tweet;
    private final User poster;

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("[K:ma dd/mm/yyyy]");

    public Message(String tweet, User poster) {
        ID = UUID.randomUUID().toString();
        this.tweet = tweet;
        this.poster = poster;
        date = new Date();
        tags = extractTags(tweet);
        parts = tweet.split(" ");
    }

    public Message(String id, String text, User user) {
        this(text, user);
        this.ID = id;

    }



    @Override
    public final String toString() {
        return "(" + DATE_FORMAT.format(date) + ") [" + tweet + "] {" + poster.getUsername() + "}";
    }

    public final Date getDate() {
        return date;
    }


    public final User getPoster() {
        return poster;
    }

    public static Set<String> extractTags(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text);
        Set<String> hashtags = new HashSet<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.startsWith("#")) {
                hashtags.add(token.substring(1).replace(".", ""));
            }
        }
        return hashtags;
    }

    public final Set<String> getTags() {
        return tags;
    }

    public final String[] getParts() {
        return parts;
    }

    public final String getID() {
        return ID;
    }
}
