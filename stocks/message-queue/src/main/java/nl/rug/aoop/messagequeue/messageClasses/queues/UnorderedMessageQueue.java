package nl.rug.aoop.messagequeue.messageClasses.queues;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The UnorderedMessageQueue class contains the attributes and the behaviour of an unordered message queue.
 */
public class UnorderedMessageQueue implements MessageQueue {

    private final Queue<Message> list;

    /**
     * The constructor is used to initialize an unordered message queue by creating an empty LinkedList.
     */
    public UnorderedMessageQueue() {
        this.list = new LinkedList<>();
    }

    /**
     * The method enqueues (adds) messages to the unordered queue.
     *
     * @param message is the message that is enqueued
     */
    @Override
    public void enqueue(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Cannot insert null into the queue");
        }
        list.add(message);
    }

    /**
     * The method dequeues and returns messages from the unordered queue.
     *
     * @return .
     */
    @Override
    public Message dequeue() {
        if (list.size() == 0) {
            return null;
        }
        return list.remove();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    public Queue<Message> getMessages() {
        return this.list;
    }
}
