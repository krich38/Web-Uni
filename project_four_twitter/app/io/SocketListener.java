package io;

import model.Message;

/**
 * @author Kyle Richards
 * @version 1.0
 */
public interface SocketListener {

    public void receiveMessage(Message msg);
}
