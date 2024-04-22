package nl.rug.aoop.messagequeue.messageClasses.commands;

import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

import java.util.Map;

/**
 * This class contains the attributes and behaviour of a MqTraderCommand.
 */
public class MqTraderCommand implements Command {
    private final MessageQueue queue;

    /**
     * The constructor for the MqTrader command.
     *
     * @param queue is the queue that messages are enqueued to
     */
    public MqTraderCommand(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void execute(Map<String, Object> parms) {
        String traderId = (String) parms.get("traderid");
        int traderFunds = (Integer) parms.get("traderfunds");
        Map<String, Integer> ownedShares = (Map<String, Integer>) parms.get("tradershares");
        Message m = new Message("Trader", "info");
        m.setTraderId(traderId);
        m.setTraderFunds(traderFunds);
        m.setTraderOwnedShares(ownedShares);
        queue.enqueue(m);
    }
}
