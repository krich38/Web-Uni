package io;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class WebSocketActor extends UntypedActor {

    public static Props props(ActorRef out) {

        return Props.create(WebSocketActor.class, out);
    }

    /** The Actor for the client (browser) */
    private final ActorRef out;


    private final SocketListener listener;
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            out.tell("I received your message: " + message, self());
        }
    }

    public WebSocketActor(ActorRef out) {

        this.out = out;

        /*
          Our GibberishListener, written as a Java 8 Lambda.
          Whenever we receive a gibberish, if it matches our topic, convert it to a JSON string, and send it to the client.
         */
        this.listener = (g) -> {
            if (true) {
                // Convert the Gibberish to a JSON string
                //String message = JsonExample.toJson(g).toString();

                /*
                 This asynchronously sends the message to the WebSocket client.
                 Self is a reference to this actor (the sender)
                 */
                //out.tell(message, self());

                System.out.println(g.tweet);
            }
        };

        MessageHub.getInstance().addListener(listener);
        System.out.println("messageeee");
    }

    public void postStop() throws Exception {
        // De-register our listener

        MessageHub.getInstance().removeListener(this.listener);
    }
}
