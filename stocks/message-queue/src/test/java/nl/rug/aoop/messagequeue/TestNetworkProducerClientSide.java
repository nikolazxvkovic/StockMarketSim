package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.producers.NetworkProducerClientSide;
import nl.rug.aoop.networking.client.Client;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestNetworkProducerClientSide {

    @Test
    public void testPut() {
        Client mockClient = Mockito.mock(Client.class);
        NetworkProducerClientSide networkProducerClientSide = new NetworkProducerClientSide(mockClient);
        Message message = new Message("Test", "Message");
        networkProducerClientSide.put(message);
        Mockito.verify(mockClient).sendMessage(message.toJson());
    }
}
