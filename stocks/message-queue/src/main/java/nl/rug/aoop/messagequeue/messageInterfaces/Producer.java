package nl.rug.aoop.messagequeue.messageInterfaces;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;

/**
 * An interface that a class can implement so that the producer can send messages.
 */
public interface Producer {
    /**
     * Allows the producer to send messages.
     *
     * @param message is the message sent by the producer
     */
    void put(Message message);
}
