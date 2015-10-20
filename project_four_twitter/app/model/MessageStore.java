package model;

import controllers.SpaController;
import play.mvc.WebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class MessageStore {
    private static final List<Message> messages = new CopyOnWriteArrayList<>();

//    static {
//        // just here whilst testing
//        for (int i = 0; i < 4; i++) {
//            messages.add(new Message("This is exmaple tweet #number " + i, new User("Tweeter_" + (i + 1), "emailhere", "foo")));
//        }
//        messages.add(new Message("This is example to #search for.", new User("Searchme", "emailhere", "foo")));
//    }

    public static List<Message> getMessages(boolean order) {
        if (order) {
            Collections.sort(messages, (m1, m2) -> m2.getDate().compareTo(m1.getDate()));
        }

        List<Message> messages = DatabaseAdapter.newestMessages(30);
if(messages == null) {
    messages = new ArrayList<>();
}
        return messages;
    }

    public static void prependTweet(Message tweet) {

        DatabaseAdapter.newTweet(tweet);
        for (WebSocket.Out<String> out : SpaController.ACTIVE_OUT) {
            out.write(tweet.toString());
        }
    }

    public static List<Message> getMostRecent() {
        List<Message> messages = getMessages(true);
        return messages.size() >= 9 ? messages.subList(0, 9) : messages.subList(0, messages.size());
    }

    public static List<Message> searchByUsername(String username) {
        username = username.replaceAll("[^A-Za-z0-9]@", "");
        List<Message> messagesFound = null;
        for (Message msg : messages) {
            if (msg.getPoster().getUsername().equalsIgnoreCase(username)) {
                // we have actually found a message, open a list
                if (messagesFound == null) {
                    messagesFound = new ArrayList<>(messages.size());
                }
                messagesFound.add(msg);
            }
        }
        return messagesFound;
    }

    public static List<Message> searchByHashtag(String tag) {
        tag = tag.replaceAll("[^A-Za-z0-9]", "");
        List<Message> messagesFound = null;
        for (Message m : messages) {
            for (String s : m.getTags()) {
                if (tag.equalsIgnoreCase(s)) {
                    // we have actually found a message, open a list
                    if (messagesFound == null) {
                        messagesFound = new ArrayList<>(messages.size());
                    }
                    messagesFound.add(m);
                }
            }
        }
        return messagesFound;
    }

    public static List<Message> getByID(String ID) {
        List<Message> tweetFound = null;
        for (Message m : messages) {
            if (m.getID().equals(ID)) {
                tweetFound = new ArrayList<>();
                tweetFound.add(m);
                break; // no point continuing the iteration
            }
        }
        return tweetFound;
    }

    /*public Message[] searchByTopic(String message) {
        Message foundMessage = null;
        for(Message msg : messages) {
            if(msg.getTweet().equalsIgnoreCase(message)) {
                foundMessage = msg;
            }
        }
        return foundMessage;
    }*/
}
