package nl.rug.aoop.stock;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.initialization.SimpleViewFactory;
import nl.rug.aoop.messagequeue.messageClasses.consumers.ReceiveMessage;
import nl.rug.aoop.messagequeue.messageClasses.factories.MqCommandHandlerFactory;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.messageHandlers.MqMessageHandler;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.Consumer;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import nl.rug.aoop.stock.handlers.OrderHandler;
import nl.rug.aoop.stock.order.OrderListManager;
import nl.rug.aoop.stock.stockData.Loader;
import nl.rug.aoop.stock.stockData.StockCollection;
import nl.rug.aoop.stock.stockData.Trader;
import nl.rug.aoop.stock.stockData.TraderCollection;
import nl.rug.aoop.stock.view.StockExchangeModel;

import java.io.IOException;

import static org.awaitility.Awaitility.await;

/**
 * This class contains the attributes and behaviour of a StockApplication.
 */
public class StockApplication implements Runnable {

    private final OrderHandler orderHandler;
    private final MessageQueue messageQueue;
    private final Consumer receiveOrder;
    private final OrderListManager orderListManager;
    private boolean isRunning;
    private Server server;

    /**
     * The constructor.
     *
     * @param orderHandler     handles the orders.
     * @param orderListManager the list of orders.
     */
    public StockApplication(OrderHandler orderHandler, OrderListManager orderListManager) {
        this.orderHandler = orderHandler;
        this.orderListManager = orderListManager;
        this.messageQueue = new PriorityBlockingMessageQueue();
        this.receiveOrder = new ReceiveMessage(messageQueue);
        this.isRunning = true;
    }

    /**
     * Runs the stock app.
     */
    @Override
    public void run() {
        try {
            StockCollection stockCollection = Loader.initializeStockData();
            TraderCollection traderCollection = Loader.initializeTraderData();
            startNetworkedServer();
            while (isRunning) {
                if (messageQueue.getSize() != 0) {
                    Message message = receiveOrder.poll();
                    if (message != null) {
                        orderHandler.handleOrder(message);
                        stockCollection = orderListManager.getStockCollection();
                        traderCollection = orderListManager.getTraderCollection();

                    }
                    sendStockUpdates(stockCollection);
                    sendTraderUpdates(traderCollection);
                }
            }
            SimpleViewFactory simpleViewFactory = new SimpleViewFactory();
            simpleViewFactory.createView(new StockExchangeModel(stockCollection, traderCollection));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the networked server.
     *
     * @throws IOException throws exception.
     */
    public void startNetworkedServer() throws IOException {
        MqCommandHandlerFactory commandHandlerFactory = new MqCommandHandlerFactory(messageQueue);
        CommandHandler commandHandler = commandHandlerFactory.create();
        MessageHandler messageHandler = new MqMessageHandler(commandHandler);
        server = new Server(63000, messageHandler);
        new Thread(server).start();
        await().until(() -> server.isRunning());
        await().until(() -> server.getNumberOfClients() != 0);
    }

    /**
     * Sends the updated stock info.
     *
     * @param stockCollection the stock info.
     */
    public void sendStockUpdates(StockCollection stockCollection) {
        for (var entry : stockCollection.getStocks().entrySet()) {
            Message message = new Message("MqStock", "info");
            message.setSymbol(entry.getKey());
            message.setPrice(entry.getValue().getInitialPrice());
            server.getClientHandlers().get(0).sendMessage(message.toJson());
        }
    }

    /**
     * Sends the updated trader info.
     *
     * @param traderCollection the trader info.
     */
    public void sendTraderUpdates(TraderCollection traderCollection) {
        for (int i = 0; i < traderCollection.getTraders().size(); i++) {
            Trader trader = traderCollection.getTraders().get("bot" + (i + 1));
            Message message = new Message("MqTrader", "info");
            message.setTraderId(trader.getId());
            message.setTraderFunds(trader.getFunds());
            message.setTraderOwnedShares(trader.getOwnedShares());
            server.getClientHandlers().get(i).sendMessage(message.toJson());
        }
    }

    /**
     * Terminates the stock app.
     */
    public void terminate() {
        isRunning = false;
    }

}