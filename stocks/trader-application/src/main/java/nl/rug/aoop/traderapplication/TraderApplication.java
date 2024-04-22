package nl.rug.aoop.traderapplication;

import nl.rug.aoop.messagequeue.messageClasses.consumers.ReceiveMessage;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.producers.NetworkProducerClientSide;
import nl.rug.aoop.messagequeue.messageInterfaces.Consumer;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.Producer;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.stock.stockData.Loader;
import nl.rug.aoop.stock.stockData.StockCollection;
import nl.rug.aoop.stock.stockData.Trader;
import nl.rug.aoop.stock.stockData.TraderCollection;
import nl.rug.aoop.traderapplication.strategy.RandomStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains the attributes and behaviour of a Trader Application.
 */
public class TraderApplication implements Runnable {
    private List<Client> clientCollection;
    private MessageQueue messageQueue;
    private boolean isRunning;
    private Consumer receiveInfo;

    /**
     * A constructor.
     */
    public TraderApplication() {
        this.isRunning = true;
    }

    /**
     * Runs the trader app.
     */
    @Override
    public void run() {
        try {
            StockCollection stockCollection = Loader.initializeStockData();
            TraderCollection traderCollection = Loader.initializeTraderData();
            retrieveQueueAndClients();
            startNetworkedClients();
            List<Producer> producerCollection = initializeProducers();
            while (isRunning) {
                while (messageQueue.getSize() != 0) {
                    Message message = receiveInfo.poll();
                    if (message.getHeader().equals("Stock")) {
                        updateStockInfo(message, stockCollection);
                    } else {
                        updateTraderInfo(message, traderCollection);
                    }
                }
                sendOrder(traderCollection, producerCollection, stockCollection);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method that retrieves the message queue and the clients.
     *
     * @throws IOException exception.
     */
    public void retrieveQueueAndClients() throws IOException {
        Map<String, Object> queueAndClients = Loader.initializeTraders();
        clientCollection = (List<Client>) queueAndClients.get("clients");
        messageQueue = (MessageQueue) queueAndClients.get("queue");
        receiveInfo = new ReceiveMessage(messageQueue);
    }

    /**
     * Starts the threads for the network.
     */
    public void startNetworkedClients() {
        for (Client client : clientCollection) {
            new Thread(client).start();
        }
    }

    /**
     * Initializes the network producers.
     *
     * @return returns the producer list.
     */
    public List<Producer> initializeProducers() {
        List<Producer> producerCollection = new ArrayList<>();
        for (Client client : clientCollection) {
            Producer producer = new NetworkProducerClientSide(client);
            producerCollection.add(producer);
        }
        return producerCollection;
    }

    /**
     * Updates the stock info.
     *
     * @param message         message.
     * @param stockCollection stock info.
     */
    public void updateStockInfo(Message message, StockCollection stockCollection) {
        double updatedStockPrice = message.getPrice();
        String stockSymbol = message.getSymbol();
        stockCollection.getStocks().get(stockSymbol).setInitialPrice(updatedStockPrice);
    }

    /**
     * Updates the trader info.
     *
     * @param message          message.
     * @param traderCollection trader info.
     */
    public void updateTraderInfo(Message message, TraderCollection traderCollection) {
        Trader trader = traderCollection.getTraders().get(message.getTraderId());
        trader.setFunds(message.getTraderFunds());
        trader.setOwnedShares(message.getTraderOwnedShares());
    }

    /**
     * Sends a order.
     *
     * @param traderCollection   trader info.
     * @param producerCollection producer list.
     * @param stockCollection    stock info.
     */
    public void sendOrder(TraderCollection traderCollection, List<Producer> producerCollection,
                          StockCollection stockCollection) {
        for (int i = 0; i < traderCollection.getTraders().size(); i++) {
            RandomStrategy randomStrategy =
                    new RandomStrategy(traderCollection.getTraders().get("bot" + (i + 1)), stockCollection);
            randomStrategy.doStrategy();
            Message message = randomStrategy.generateOrder();
            Message sendMessage = new Message("MqPut", message.toJson());
            producerCollection.get(i).put(sendMessage);
        }
    }

    /**
     * terminates the trader app.
     */
    public void terminate() {
        isRunning = false;
    }
}
