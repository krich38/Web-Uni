package model;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class UserStore {
    private static final Map<String, User> USER_MAP = new ConcurrentHashMap<>();

    public static String encrypt(String pw) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(pw, salt);
    }

    public static void storeNewUser(User newUser) {

        DatabaseAdapter.saveUser(newUser);
        System.out.println(newUser);
        //USER_MAP.put(newUser.getIP(), newUser);


    }

    public static boolean lookupUser(User user) {
        User loadedUser = DatabaseAdapter.loadUser(user.getUsername(), user.getPassword());
        if (loadedUser == null) {
            return false;
        }
        return true;
    }

    /**
     * Do we already have a user registered with this name
     * @param user The username
     */
    public static boolean isUsernameAvail(String user) {
        return DatabaseAdapter.getUserByName(user) == null;
    }

    public static boolean gotIP(String ip) {
        return USER_MAP.containsKey(ip);
    }

    public static boolean loggedin(User user) {
        return USER_MAP.containsValue(user);
    }

    public static void addUser(User user) {
        USER_MAP.put(user.getIP(), user);
    }

    public static User getUserByIP(String ip) {
        return USER_MAP.get(ip);
    }

    public static void loggedOut(User user) {
        USER_MAP.remove(user.getIP());
        user.setLoggedIn(false);
        DatabaseAdapter.saveUser(user);
    }

    public static User getUserByName(String user) {

        return DatabaseAdapter.getUserByName(user);
    }
}
