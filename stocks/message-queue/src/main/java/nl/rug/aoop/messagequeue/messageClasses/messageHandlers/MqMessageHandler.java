package nl.rug.aoop.messagequeue.messageClasses.messageHandlers;

import lombok.Getter;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods used to handle a message.
 */
public class MqMessageHandler implements MessageHandler {

    @Getter
    private final Map<String, Object> map;
    private final CommandHandler commandHandler;

    /**
     * Constructor used to initialize a MqMessageHandler.
     *
     * @param commandHandler is the command handler that we call to execute a command
     */
    public MqMessageHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.map = new HashMap<>();
    }

    /**
     * This method is used to transform a Json string into a message, add its header and body to the map and execute
     * the command that is written in the header of the message.
     *
     * @param message is the message that needs to be handled
     */
    @Override
    public void handleMessage(String message) {
        Message mess = Message.fromJson(message);
        map.put("header", mess.getHeader());
        map.put("body", mess.getBody());
        commandHandler.execute(mess.getHeader(), map);
    }
}
