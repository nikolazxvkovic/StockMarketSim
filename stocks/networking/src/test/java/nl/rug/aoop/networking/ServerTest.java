package nl.rug.aoop.networking;

import nl.rug.aoop.networking.messageHandlers.MessageHandler;
import nl.rug.aoop.networking.server.Server;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

public class ServerTest {

    @Test
    public void testConstructor() throws IOException {
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Server server = new Server(0, mockHandler);
        assertEquals(server.getNumberOfClients(), 0);
    }

    @Test
    public void testRunWithSingleConnection() throws IOException {
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Server server = new Server(0, mockHandler);
        new Thread(server).start();
        await().atMost(1, TimeUnit.SECONDS).until(server::isRunning);
        assertTrue(server.isRunning());
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", server.getPort()));
            await().atMost(1, TimeUnit.SECONDS).until(() -> server.getNumberOfClients() == 1);
            assertEquals(1, server.getNumberOfClients());
        }
    }

    @Test
    public void testTerminate() throws IOException {
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Server server = new Server(0, mockHandler);
        new Thread(server).start();
        await().atMost(1, TimeUnit.SECONDS).until(server::isRunning);
        assertTrue(server.isRunning());
        server.terminate();
        assertFalse(server.isRunning());
    }
}
