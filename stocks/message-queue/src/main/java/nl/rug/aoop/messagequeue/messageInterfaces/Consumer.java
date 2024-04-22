package nl.rug.aoop.messagequeue.messageInterfaces;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;

/**
 * An interface that a class can implement so that the consumer can receive messages.
 */
public interface Consumer {
    /**
     * Allows the consumer to receive messages.
     *
     * @return .
     */
    Message poll();
}
