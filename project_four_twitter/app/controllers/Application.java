package controllers;

import model.*;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Kyle Richards
 * @version 1.0
 */
public class Application extends Controller {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("\\w{1,15}");
    public static final Pattern USERNAME_PATTERN = Pattern.compile("\\w{1,15}");
    private static PageModel renderModelFlag;

    /*
     * Handle our register request
     */
    public static Result register() {
        // is user already logged in?
        if (UserStore.gotIP(request().remoteAddress())) {
            return redirectToIndex("You are logged in! Please logout first.");
        }
        return ok((Content) views.html.application.register.render(new PageModel(MessageStore.getMostRecent(), null, "Please register!")));
    }

    public static Result registerRequest(String username, String email, String pw) {
        Matcher userMatch = USERNAME_PATTERN.matcher(username);
        if (userMatch.matches()) {
            Matcher emailMatch = EMAIL_PATTERN.matcher(email);
            if (emailMatch.matches()) {
                Matcher passMatch = PASSWORD_PATTERN.matcher(pw);
                if (passMatch.matches()) {
                    if (UserStore.isUsernameAvail(username)) {
                        User newUser = new User(username, email, UserStore.encrypt(pw));
                        newUser.setIP(request().remoteAddress());
                        UserStore.storeNewUser(newUser); // new user registered, store them in db
                        return loginRequest(newUser, "You have successfully registered! ");
                    } else {
                        return register("That username is already taken.");
                    }
                } else {
                    return register("Please enter a valid password.");
                }
            } else {
                return register("Please enter a valid email.");
            }
        } else {
            return register("Please enter a valid username.");
        }
    }

    private static Result register(String msg) {
        return ok((Content) views.html.application.register.render(new PageModel(MessageStore.getMostRecent(), null, msg)));
    }

    private static Result loginRequest(User user, String preMessage) {
        System.out.println("Login request " + user.getUsername());
        if (UserStore.lookupUser(user)) { // is this correct authentication?
            String ip = request().remoteAddress();
            if (UserStore.loggedin(user)) { // is the user already logged in elsewhere?
                return redirectToIndex("Your account is already logged in.");
            }

            user.setIP(ip);
            UserStore.addUser(user);
            return redirectToIndex(preMessage + "You are now logged in as " + user.getUsername());
        } else {
            return redirectToIndex("Login failed!");
        }
    }

    public static Result revoke(String user) {
        User u = UserStore.getUserByName(user);
        if (u != null) {
            UserStore.loggedOut(u); // log out this remote user
            return redirectToIndex("You have revoked their login. You may now log in.");
        } else {
            return redirectToIndex("That person isn't logged in.");
        }
    }

    public static Result postTweet() {
        User user = UserStore.getUserByIP(request().remoteAddress());
        if (user == null) {
            return redirectToIndex("You must be logged in to post a tweet.");
        }
        if (user.canPost()) { // has enough time elapsed since the last tweet?
            DynamicForm form = Form.form().bindFromRequest();
            Message tweet = new Message(form.get("tweet"), user);
            MessageStore.prependTweet(tweet);
            user.updatePosted();

            return redirectToIndex();
        } else {
            return redirectToIndex("Please wait 5 seconds before posting your next tweet.");
        }
    }

    public static Result index() {
        PageModel model;

        String ip = request().remoteAddress();
        User user = UserStore.getUserByIP(ip);
        if (renderModelFlag != null) { // already flagged to prerender?
            model = renderModelFlag;
            renderModelFlag = null;
        } else {
            model = new PageModel(MessageStore.getMostRecent(), user, user == null ? "You have to be logged in!" : "Logged in as " + user.getUsername());
        }

        return ok((Content) views.html.application.index.render(model));
    }

    public static Result logout() {
        User user = UserStore.getUserByIP(request().remoteAddress());
        if (user == null) {
            return redirectToIndex("You weren't logged in!");
        }


        UserStore.loggedOut(user);

        PageModel model = new PageModel(MessageStore.getMostRecent(), null, "You have logged out!");
        return ok((Content) views.html.application.index.render(model));
    }

    public static Result loginRequest() {
        String ip = request().remoteAddress();
        if (UserStore.getUserByIP(ip) != null) { // already got a connection from this IP?
            return redirectToIndex("Max 1 account per IP");
        }
        DynamicForm form = Form.form().bindFromRequest();
        User u = new User(form.get("user"), null, form.get("password"));
        if (UserStore.loggedin(u)) { // is user already logged in?
            PageModel model = new PageModel(MessageStore.getMostRecent(), u, "You are already logged in.");
            return ok((Content) views.html.application.revoke.render(model));
        }
        return loginRequest(u, "");
    }

    public static Result redirectToIndex() {
        User user = UserStore.getUserByIP(request().remoteAddress());
        if (user == null) {
            return redirectToIndex("You need to be logged in!");
        } else {
            return redirectToIndex("Logged in as " + user.getUsername());
        }
    }

    public static Result redirectToIndex(String message) {
        return redirectToIndex(message, MessageStore.getMostRecent());
    }

    public static Result postsByUser(String user) {
        if (user.length() < 2) {
            return redirectToIndex("Please enter a valid username.");
        }
        List<Message> tweets = MessageStore.searchByUsername(user.replace("@", ""));
        PageModel page;
        User u = UserStore.getUserByIP(request().remoteAddress());
        if (tweets != null) {
            //page = new PageModel(tweets, u, "Showing all posts by the user " + user);
            return ok(Json.toJson(tweets));
        } else {
            page = new PageModel(MessageStore.getMostRecent(), u, "No tweets by the user " + user + " were found.");
        }
        return ok((Content) views.html.application.searchuser.render(page));
    }

    public static Result postsByTag(String tag) {
        if (tag.length() < 2) {
            return redirectToIndex("Please enter a valid hashtag.");
        }
        List<Message> tweets = MessageStore.searchByHashtag(tag);
        User user = UserStore.getUserByIP(request().remoteAddress());
        if (tag.charAt(0) == '#') {
            tag = tag.substring(1);
        }
        PageModel page;
        if (tweets != null) {
            //page = new PageModel(tweets, user, "Showing all posts the tag #" + tag);
            System.out.println(tweets);
            return ok(Json.toJson(tweets));
        } else {
            page = new PageModel(MessageStore.getMostRecent(), user, ("No tweets by the tag #" + tag + " were found."));
        }
        return ok((Content) views.html.application.searchtag.render(page));
    }

    private static Result redirectToIndex(String message, List<Message> tweets) {
        renderModelFlag = new PageModel(tweets, UserStore.getUserByIP(request().remoteAddress()), message);
        return ok();
    }

//    static {
//        User newUser = new User("test123", "foo@foo.com", UserStore.encrypt("test123"));
//        UserStore.storeNewUser(newUser);
//    }

    public static Result postByID(String ID) {
        List<Message> tweet = MessageStore.getByID(ID);
        PageModel page;
        User user = UserStore.getUserByIP(request().remoteAddress());
        if (tweet == null) {
            page = new PageModel(MessageStore.getMostRecent(), user, "Unable to find tweet by ID.");
        } else {
            page = new PageModel(tweet, user, "Showing post by ID.");
        }
        return ok((Content) views.html.application.searchid.render(page));
    }


    public static Result test() {
        return ok(new File("./lol.jpg"));
    }


}
