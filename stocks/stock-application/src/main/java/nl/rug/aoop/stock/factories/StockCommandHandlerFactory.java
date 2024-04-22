package nl.rug.aoop.stock.factories;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.stock.commands.StockBuyCommand;
import nl.rug.aoop.stock.commands.StockSellCommand;
import nl.rug.aoop.stock.order.OrderListManager;

/**
 * This class contains the attributes and behaviour of a StockCommandHandlerFactory.
 */
public class StockCommandHandlerFactory implements AbstractStockCommandHandlerFactory {

    private final OrderListManager orderListManager;

    /**
     * The constructor of a StockCommandHandlerFactory.
     *
     * @param orderListManager is used to store the sell/buy order lists
     */
    public StockCommandHandlerFactory(OrderListManager orderListManager) {
        this.orderListManager = orderListManager;
    }

    @Override
    public CommandHandler create() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("buy", new StockBuyCommand(orderListManager));
        commandHandler.registerCommand("sell", new StockSellCommand(orderListManager));
        return commandHandler;
    }
}
