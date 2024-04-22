package nl.rug.aoop.messagequeue.messageClasses.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

import java.util.Map;

/**
 * This class contains the attributes and behaviour of a MqStockCommand.
 */
public class MqStockCommand implements Command {

    private final MessageQueue queue;

    /**
     * The constructor for the MqStock command.
     *
     * @param queue is the queue that messages are enqueued to
     */
    public MqStockCommand(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void execute(Map<String, Object> parms) {
        Double stockPrice = (Double) parms.get("stockPrice");
        String stockSymbol = (String) parms.get("stockSymbol");
        Message m = new Message("Stock", "info");
        m.setPrice(stockPrice);
        m.setSymbol(stockSymbol);
        queue.enqueue(m);
    }
}
