package nl.rug.aoop.stock;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.stock.factories.StockCommandHandlerFactory;
import nl.rug.aoop.stock.handlers.OrderHandler;
import nl.rug.aoop.stock.order.OrderListManager;

/**
 * This class contains the attributes and behaviour of Main.
 */
public class Main {

    /**
     * The main.
     *
     * @param args argument.
     */
    public static void main(String[] args) {
        OrderListManager orderListManager = new OrderListManager();
        StockCommandHandlerFactory stockCommandHandlerFactory = new StockCommandHandlerFactory(orderListManager);
        CommandHandler stockCommandHandler = stockCommandHandlerFactory.create();
        OrderHandler orderHandler = new OrderHandler(stockCommandHandler);
        StockApplication stockApplication = new StockApplication(orderHandler, orderListManager);
        new Thread(stockApplication).start();
    }

}
