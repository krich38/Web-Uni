package model;

import com.mongodb.*;
import org.bson.BSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class DatabaseAdapter {

    private static final MongoClient CLIENT;


    private static final com.mongodb.DB DB;

    public static void saveUser(User user) {
        DBObject object = buildObjectUser(user);
        //USERS.update(object, );
        USERS.insert(object);

    }

    public static User loadUser(String username, String password) {
        DBObject object = new BasicDBObject("_name", username);
        DBObject found = USERS.findOne(object);
        if (found == null) {
            System.out.println("Unable to find user... " + username);
            return null;
        }
        //System.out.println(password + " : " + found.get("pass"));
        // todo: password comparison
        return new User(username, ((String) found.get("email")), password);

    }

    private static DBObject buildObjectUser(User user) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("_name", user.getUsername());
        docBuilder.append("pass", user.getPassword());
        docBuilder.append("email", user.getEmail());
        return docBuilder.get();
    }


    private static final DBCollection USERS;

    private static final DBCollection MESSAGES;

    static {
        CLIENT = new MongoClient("127.0.0.1", 27017);
        DB = CLIENT.getDB("comp391_kricha35");
        USERS = DB.getCollection("users");
        MESSAGES = DB.getCollection("messages");

//
//        /** clear our databases while testing */
//        while(MESSAGES.find().size() > 0) {
//            DBObject doc = MESSAGES.findOne();
//            MESSAGES.remove(doc);
//        }
//
//        while(USERS.find().size() > 0) {
//            DBObject doc = USERS.findOne();
//            USERS.remove(doc);
//        }

    }

    public static User getUserByName(String user) {
        DBObject object = new BasicDBObject("_name", user);

        DBObject found = USERS.findOne(object);
        if (found == null) {

            return null;
        }
        return new User(user, ((String) found.get("email")), ((String) found.get("pass")));
    }

    public static List<Message> newestMessages(int amount) {
        DBCursor messages = MESSAGES.find().limit(amount);
        List<Message> messageList = new ArrayList<>(amount);
        while(messages.hasNext()) {
            DBObject mes = messages.next();
            Message m = new Message((String) mes.get("_id"), (String) mes.get("text"), getUserByName((String) mes.get("user")));
            messageList.add(m);

        }

        return messageList;
    }

    public static void newTweet(Message tweet) {
        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();

        docBuilder.append("_id", tweet.getID());
        docBuilder.append("text", tweet.tweet);
        docBuilder.append("user", tweet.getPoster().getUsername());
        //docBuilder.append("email", user.getEmail());
        MESSAGES.insert(docBuilder.get());

    }
}
