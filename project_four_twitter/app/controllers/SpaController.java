package controllers;

import io.MessageHub;
import io.WebSocketActor;
import model.*;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import play.twirl.api.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class SpaController extends Controller {
    private static PageModel renderModelFlag;

    public static Result spa() {

        PageModel model;
        String ip = request().remoteAddress();
        User user = UserStore.getUserByIP(ip);
        if (renderModelFlag != null) {
            model = renderModelFlag;
            renderModelFlag = null;
        } else {
            model = new PageModel(MessageStore.getMostRecent(), user, user == null ? "You have to be logged in!" : "Logged in as " + user.getUsername());
        }
        return ok((Content) views.html.application.spa.render(model));
    }

    public static Result search(String search) {
        System.out.println(search);
        if (search.startsWith("@")) {
            String user = search.replace("@", "");
//        if (user.length() < 2) {
//            return redirectToIndex("Please enter a valid username.");
//        }
            List<Message> tweets = MessageStore.searchByUsername(user);
            PageModel page;
            User u = UserStore.getUserByIP(request().remoteAddress());
            if (tweets != null) {
                page = new PageModel(tweets, u, "Showing all posts by the user " + user);
                //return ok(Json.toJson(tweets));
            } else {
                page = new PageModel(MessageStore.getMostRecent(), u, "No tweets by the user " + user + " were found.");
            }
            return TODO;
        }
        return TODO;
    }

    public static WebSocket<String> test() {

        WebSocket<String> ws = new WebSocket<String>() {


            public void onReady(WebSocket.In<String> in, WebSocket.Out<String> out) {

                in.onMessage(new F.Callback<String>() {
                    public void invoke(String event) {

                        System.out.println(event);


                    }
                });


                   in.onClose(new F.Callback0() {
                    public void invoke() {

                        System.out.println("Disconnected");

                    }
                });

                ACTIVE_OUT.add(out);

            }

        };



        //return ws;
        return WebSocket.<String>withActor((out) -> WebSocketActor.props(out));
    }

    public static final List<WebSocket.Out<String>> ACTIVE_OUT = new ArrayList<>();

    public static Result postTweetSpa() {
          User user = UserStore.getUserByIP(request().remoteAddress());
        if (user == null) {
            return Application.redirectToIndex("You must be logged in to post a tweet.");
        }
        if (user.canPost()) {
            DynamicForm form = Form.form().bindFromRequest();
            Message tweet = new Message(form.get("tweet"), user);
            MessageStore.prependTweet(tweet);
            MessageHub.getInstance().send(tweet);
            user.updatePosted();
            return ok();
        } else {
            return Application.redirectToIndex("Please wait 5 seconds before posting your next tweet.");
        }
    }
}
