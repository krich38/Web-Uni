package io;

import model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public class MessageHub {
    private final List<SocketListener> listeners;

    static final MessageHub instance = new MessageHub();

    public static MessageHub getInstance() {
        return instance;
    }

    protected MessageHub() {
        this.listeners = Collections.synchronizedList(new ArrayList<>());
    }

    public void send(Message g) {
        System.out.println(listeners.size());
        for (SocketListener listener : listeners) {
            listener.receiveMessage(g);
        }
    }

    public void addListener(SocketListener l) {
        this.listeners.add(l);
    }

    public void removeListener(SocketListener l) {
        this.listeners.remove(l);
    }

}
