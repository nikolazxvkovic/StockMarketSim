package nl.rug.aoop.stock.handlers;

import lombok.Getter;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the attributes and behaviour of a OrderHandler.
 */
public class OrderHandler {

    @Getter
    private final Map<String, Object> map;
    private final CommandHandler commandHandler;

    /**
     * The constructor used to create an OrderHandler.
     *
     * @param commandHandler is the command handler that executes the commands
     */
    public OrderHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.map = new HashMap<>();
    }

    /**
     * Method used to handle the order.
     *
     * @param mess is the message order
     */
    public void handleOrder(Message mess) {
        map.put("order", mess);
        commandHandler.execute(mess.getHeader(), map);
    }
}
