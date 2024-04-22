package nl.rug.aoop.messagequeue.messageClasses.queues;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * This class contains the attributes and the behaviour of a priority blocking message queue.
 */
public class PriorityBlockingMessageQueue implements MessageQueue {

    private final PriorityBlockingQueue<Message> list;

    /**
     * The constructor is used to initialize a priority blocking message queue by creating an empty priority blocking
     * queue.
     */
    public PriorityBlockingMessageQueue() {
        this.list = new PriorityBlockingQueue<>();
    }

    /**
     * The method enqueues (adds) messages to the queue.
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
     * The method dequeues and returns messages from the queue.
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

    public PriorityBlockingQueue<Message> getMessages() {
        return this.list;
    }
}
