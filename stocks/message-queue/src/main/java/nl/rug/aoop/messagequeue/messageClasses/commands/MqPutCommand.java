package nl.rug.aoop.messagequeue.messageClasses.commands;

import lombok.Getter;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

import java.util.Map;

/**
 * This class contains the attributes and behaviour of a MqPut command.
 */
public class MqPutCommand implements Command {

    @Getter
    private final MessageQueue queue;

    /**
     * Constructor used to initialize a MqPut command.
     *
     * @param queue is the queue that messages are enqueued to
     */
    public MqPutCommand(MessageQueue queue) {
        this.queue = queue;
    }

    /**
     * This method is used to transform a Json string into a message object and add it to the queue.
     *
     * @param parms is the map where the body of the message is extracted from
     */
    @Override
    public void execute(Map<String, Object> parms) {
        String message = (String) parms.get("body");
        Message m = Message.fromJson(message);
        queue.enqueue(m);
    }
}
