package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ClientTest {

    private int serverPort;
    private boolean serverStarted;
    private PrintWriter serverSideOut;
    private BufferedReader serverSideIn;
    private InetSocketAddress address;
    private MessageHandler mockHandler;

    private void startServer() {
        new Thread(() -> {
            try {
                ServerSocket s = new ServerSocket(0);
                serverPort = s.getLocalPort();
                serverStarted = true;
                Socket serverSideSocket = s.accept();
                serverSideOut = new PrintWriter(serverSideSocket.getOutputStream());
                serverSideIn = new BufferedReader(new InputStreamReader(serverSideSocket.getInputStream()));
                log.info("Client accepted");
            } catch (IOException e) {
                log.error("Could not start server.", e);
            }
        }).start();
        await().atMost(2, TimeUnit.SECONDS).until(() -> serverStarted);
    }

    @Test
    public void testConstructorWithRunningServer() throws IOException {
        startServer();
        InetSocketAddress address = new InetSocketAddress("localhost", serverPort);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        Client client = new Client(address, mockHandler);
        assertTrue(client.isConnected());
    }

    @Test
    public void testConstructorInvalidPort() {
        startServer();
        InetSocketAddress address = new InetSocketAddress("localhost", 30000);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        assertThrows(IOException.class, () -> new Client(address, mockHandler));
    }

    @Test
    public void testConstructorInvalidHost() {
        startServer();
        InetSocketAddress address = new InetSocketAddress("testhost", serverPort);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        assertThrows(IOException.class, () -> new Client(address, mockHandler));
    }

    @BeforeEach
    public void setUp() {
        startServer();
        address = new InetSocketAddress("localhost", serverPort);
        mockHandler = Mockito.mock(MessageHandler.class);
    }

    @Test
    public void testInitSocket() throws IOException {
        Client client = new Client(address, mockHandler);
        client.initSocket(address);
        assertTrue(client.getSocket().isConnected());
    }

    @Test
    public void testRunReadSingleMessage() throws IOException {
        Client client = new Client(address, mockHandler);
        new Thread(client).start();
        await().atMost(3, TimeUnit.SECONDS).until(client::isRunning);
        assertTrue(client.isRunning());
        String message = "Server is responding to client";
        serverSideOut.println(message);
        serverSideOut.flush();
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(message));
    }

    @Test
    public void testRunReadMultipleMessages() throws IOException {
        Client client = new Client(address, mockHandler);
        new Thread(client).start();
        await().atMost(3, TimeUnit.SECONDS).until(client::isRunning);
        assertTrue(client.isRunning());
        String message1 = "Server is responding to client";
        serverSideOut.println(message1);
        serverSideOut.flush();
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(message1));
        String message2 = "Server is responding";
        serverSideOut.println(message2);
        serverSideOut.flush();
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(message2));
        String message3 = "Server";
        serverSideOut.println(message3);
        serverSideOut.flush();
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(message3));
    }

    @Test
    public void testSendMessageOne() throws IOException {
        Client client = new Client(address, mockHandler);
        String message = "Test message";
        client.sendMessage(message);
        assertEquals(serverSideIn.readLine(), message);
    }

//    @Test
//    public void testSendMessageMultiple() throws IOException {
//        Client client = new Client(address, mockHandler);
//        String message1 = "Test message 1";
//        client.sendMessage(message1);
//        assertEquals(serverSideIn.readLine(), message1);
//        String message2 = "Test message 2";
//        client.sendMessage(message2);
//        assertEquals(serverSideIn.readLine(), message2);
//        String message3 = "Test message 3";
//        client.sendMessage(message3);
//        assertEquals(serverSideIn.readLine(), message3);
//    }

    @Test
    public void testSendMessageInvalidArgument() throws IOException {
        Client client = new Client(address, mockHandler);
        assertThrows(IllegalArgumentException.class, () -> client.sendMessage(null));
        assertThrows(IllegalArgumentException.class, () -> client.sendMessage(""));
    }

    @Test
    public void testTerminate() throws IOException {
        Client client = new Client(address, mockHandler);
        new Thread(client).start();
        await().atMost(3, TimeUnit.SECONDS).until(client::isRunning);
        assertTrue(client.isRunning());
        client.terminate();
        assertFalse(client.isRunning());
        client.sendMessage("Hey");
        assertNull(serverSideIn.readLine());
    }
}
