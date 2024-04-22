package nl.rug.aoop.messagequeue.messageClasses.producers;

import lombok.Getter;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.Producer;

/**
 * The SendMessage class is used to act as a sender which takes messages from the producer.
 */
public class SendMessage implements Producer {

    @Getter
    private final MessageQueue messageQueue;

    /**
     * This constructor is used to initialize a message sender.
     *
     * @param messageQueue messageQueue is the queue to which messages are added
     */
    public SendMessage(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public void put(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Cannot put null into the queue");
        }
        messageQueue.enqueue(message);
    }
}
