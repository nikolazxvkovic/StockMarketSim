package nl.rug.aoop.stock.stockData;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messageClasses.factories.MqCommandHandlerFactory;
import nl.rug.aoop.messagequeue.messageClasses.messageHandlers.ClientMessageHandler;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains the attributes and behaviour of a Loader.
 */
@Slf4j
public class Loader {

    /**
     * The method to intialize the yaml files.
     *
     * @return map.
     */
    public static StockCollection initializeStockData() {
        StockCollection stockCollection = new StockCollection(new HashMap<>());
        URL resource = (StockCollection.class).getResource("/stocks.yaml");
        Path path;
        try {
            path = Paths.get(resource.toURI());
            YamlLoader yamlLoader = new YamlLoader(path);
            stockCollection = yamlLoader.load(StockCollection.class);
            return stockCollection;
        } catch (IOException e) {
            log.error("Failed to load stocks from YAML! (IOException)", e);
        } catch (URISyntaxException e) {
            log.error("Failed to load stocks from YAML! (URISyntaxException)", e);
        }
        return stockCollection;
    }

    /**
     * The method to initialize the yaml files.
     *
     * @return map.
     */
    public static TraderCollection initializeTraderData() {
        TraderCollection traderCollection = new TraderCollection(new HashMap<>());
        URL resource = (StockCollection.class).getResource("/traders.yaml");
        Path path;
        try {
            path = Paths.get(resource.toURI());
            YamlLoader yamlLoader = new YamlLoader(path);
            traderCollection = yamlLoader.load(TraderCollection.class);
            return traderCollection;
        } catch (IOException e) {
            log.error("Failed to load traders from YAML! (IOException)", e);
        } catch (URISyntaxException e) {
            log.error("Failed to load traders from YAML! (URISyntaxException)", e);
        }

        return traderCollection;
    }

    /**
     * Initializes the traders.
     *
     * @return map.
     * @throws IOException throws exception.
     */
    public static Map<String, Object> initializeTraders() throws IOException {
        Map<String, Object> queueAndClients = new HashMap<>();
        TraderCollection traderCollection = initializeTraderData();
        List<Client> clientCollection = new ArrayList<>();
        MessageQueue messageQueue = new PriorityBlockingMessageQueue();
        MqCommandHandlerFactory commandHandlerFactory = new MqCommandHandlerFactory(messageQueue);
        CommandHandler commandHandler = commandHandlerFactory.create();
        MessageHandler messageHandler = new ClientMessageHandler(commandHandler);
        for (int i = 0; i < traderCollection.getTraders().size(); i++) {
            Client client = new Client(new InetSocketAddress("localhost", 63000), messageHandler);
            clientCollection.add(client);
        }
        queueAndClients.put("queue", messageQueue);
        queueAndClients.put("clients", clientCollection);
        return queueAndClients;
    }
}
