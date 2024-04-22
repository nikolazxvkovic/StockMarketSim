package nl.rug.aoop.messagequeue.messageClasses.consumers;

import lombok.Getter;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.Consumer;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

/**
 * The ReceiveMessage class is used to act as a receiver which delivers messages to the consumer.
 */
public class ReceiveMessage implements Consumer {

    @Getter
    private final MessageQueue messageQueue;

    /**
     * This constructor is used to initialize a message receiver.
     *
     * @param messageQueue is the queue from which messages are delivered.
     */
    public ReceiveMessage(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @Override
    public Message poll() {
        if (messageQueue.getSize() == 0) {
            return null;
        }
        return messageQueue.dequeue();
    }

}
