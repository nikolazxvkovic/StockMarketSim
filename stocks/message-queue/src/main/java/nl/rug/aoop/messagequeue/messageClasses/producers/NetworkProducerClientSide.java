package nl.rug.aoop.messagequeue.messageClasses.producers;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageInterfaces.Producer;
import nl.rug.aoop.networking.client.Client;

/**
 * This class contains the attributes and behaviour of a NetworkProducerClientSide.
 */
public class NetworkProducerClientSide implements Producer {

    private final Client client;

    /**
     * Constructor used to initialize a Network Producer.
     *
     * @param client is the client that sends messages to the server
     */
    public NetworkProducerClientSide(Client client) {
        this.client = client;
    }

    @Override
    public void put(Message message) {
        client.sendMessage(message.toJson());
    }
}
