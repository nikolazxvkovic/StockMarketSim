package nl.rug.aoop.messagequeue.messageClasses.messageHandlers;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the attributes and behaviour of a ClientMessageHandler.
 */
public class ClientMessageHandler implements MessageHandler {

    private final Map<String, Object> map;
    private final CommandHandler commandHandler;

    /**
     * This is a constructor used to initialize a message handler designed for the client side.
     *
     * @param commandHandler handles the commands
     */
    public ClientMessageHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.map = new HashMap<>();
    }

    @Override
    public void handleMessage(String message) {
        Message mess = Message.fromJson(message);
        map.put("stockPrice", mess.getPrice());
        map.put("stockSymbol", mess.getSymbol());
        map.put("traderid", mess.getTraderId());
        map.put("traderfunds", mess.getTraderFunds());
        map.put("tradershares", mess.getTraderOwnedShares());
        commandHandler.execute(mess.getHeader(), map);
    }
}
