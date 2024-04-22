package nl.rug.aoop.messagequeue.messageInterfaces;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;

/**
 * The MessageQueue interface contains the methods used in the queues to enqueue and dequeue messages and get their
 * size.
 */
public interface MessageQueue {

    /**
     * The method enqueues (adds) messages to the queue in the classes that implement this interface.
     *
     * @param message is the message that is enqueued
     */
    void enqueue(Message message);

    /**
     * The method dequeues and returns messages from the queue in the classes that implement this interface.
     *
     * @return .
     */
    Message dequeue();

    /**
     * The method returns the size of the message queue in the classes that implement this interface.
     *
     * @return .
     */
    int getSize();
}
