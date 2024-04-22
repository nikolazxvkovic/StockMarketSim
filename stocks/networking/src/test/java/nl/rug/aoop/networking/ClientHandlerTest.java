package nl.rug.aoop.networking;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;
import nl.rug.aoop.networking.server.ClientHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
public class ClientHandlerTest {

    private int serverPort;
    private boolean serverStarted;
    private Socket serverSideSocket = null;

    private void startServer() {
        new Thread(() -> {
            try {
                ServerSocket s = new ServerSocket(0);
                serverPort = s.getLocalPort();
                serverStarted = true;
                serverSideSocket = s.accept();
            } catch (IOException e) {
                log.error("Could not start server.", e);
            }
        }).start();
        await().atMost(2, TimeUnit.SECONDS).until(() -> serverStarted);
    }

    @Test
    public void testRunReadSingleMessage() throws IOException {
        startServer();
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", serverPort));
        await().atMost(1, TimeUnit.SECONDS).until(() -> serverSideSocket != null);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        ClientHandler clientHandler = new ClientHandler(serverSideSocket, 0, mockHandler);
        new Thread(clientHandler).start();
        await().atMost(1, TimeUnit.SECONDS).until(clientHandler::isRunning);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String testMessage = "This is a test message";
        out.println(testMessage);
        out.flush();
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(testMessage));
    }

    @Test
    public void testRunReadMultipleMessages() throws IOException {
        startServer();
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", serverPort));
        await().atMost(1, TimeUnit.SECONDS).until(() -> serverSideSocket != null);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        ClientHandler clientHandler = new ClientHandler(serverSideSocket, 0, mockHandler);
        new Thread(clientHandler).start();
        await().atMost(1, TimeUnit.SECONDS).until(clientHandler::isRunning);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String testMessage1 = "This is a test message";
        out.println(testMessage1);
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(testMessage1));
        String testMessage2 = "This is a test";
        out.println(testMessage2);
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(testMessage2));
        String testMessage3 = "Test";
        out.println(testMessage3);
        await().untilAsserted(() -> Mockito.verify(mockHandler).handleMessage(testMessage3));
    }

    @Test
    public void testTerminate() throws IOException {
        startServer();
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", serverPort));
        await().atMost(1, TimeUnit.SECONDS).until(() -> serverSideSocket != null);
        MessageHandler mockHandler = Mockito.mock(MessageHandler.class);
        ClientHandler clientHandler = new ClientHandler(serverSideSocket, 0, mockHandler);
        new Thread(clientHandler).start();
        await().atMost(1, TimeUnit.SECONDS).until(clientHandler::isRunning);
        clientHandler.terminate();
        assertFalse(clientHandler.isRunning());
    }
}
