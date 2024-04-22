package nl.rug.aoop.messagequeue.messageClasses.queues;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The OrderedMessageQueue class contains the attributes and the behaviour of an unordered message queue.
 */
public class OrderedMessageQueue implements MessageQueue {

    private final SortedMap<LocalDateTime, Message> list;

    /**
     * The constructor is used to initialize an unordered message queue by creating an empty TreeMap.
     */
    public OrderedMessageQueue() {
        this.list = new TreeMap<>();
    }

    /**
     * The method enqueues (adds) messages to the ordered queue.
     *
     * @param message is the message that is enqueued
     */
    @Override
    public void enqueue(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Cannot insert null into the queue");
        }
        if (list.containsKey(message.getTimestamp())) {
            throw new IllegalArgumentException("Another message with the same key already exists");
        }
        list.put(message.getTimestamp(), message);
    }

    /**
     * The method dequeues and returns messages from the ordered queue.
     *
     * @return .
     */
    @Override
    public Message dequeue() {
        if (list.size() == 0) {
            return null;
        }
        return list.remove(list.firstKey());
    }

    @Override
    public int getSize() {
        return list.size();
    }

    public SortedMap<LocalDateTime, Message> getMessages() {
        return list;
    }

}
