package nl.rug.aoop.networking.client;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * The Client class contains the attributes and behaviour of a client.
 */
@Slf4j
public class Client implements Runnable {
    private final int TIMEOUT = 10000;
    @Getter
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    @Getter
    private boolean running = false;
    @Getter
    private final boolean connected;
    private final MessageHandler messageHandler;

    /**
     * Constructor used to initialize a client.
     *
     * @param address        is the address used to connect to a server
     * @param messageHandler is a message handler
     * @throws IOException .
     */
    public Client(InetSocketAddress address, MessageHandler messageHandler) throws IOException {
        this.messageHandler = messageHandler;
        initSocket(address);
        connected = true;
    }

    /**
     * This method is used to initialize the socket.
     *
     * @param address is the address used to connect to a server
     * @throws IOException .
     */
    public void initSocket(InetSocketAddress address) throws IOException {
        socket = new Socket();
        socket.connect(address, TIMEOUT);

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if (!socket.isConnected()) {
            throw new IOException("Socket is not connected to the server.");
        }
    }

    /**
     * This method is used to (attempt to) send messages to the connected server.
     *
     * @param message is the message that we want to send to the connected server
     * @throws IllegalArgumentException .
     */
    public void sendMessage(String message) throws IllegalArgumentException {
        if (message == null || message.equals("")) {
            throw new IllegalArgumentException("Attempting to send an invalid message.");
        }
        out.println(message);
    }

    /**
     * This method is used to receive and handle response messages from the server.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                String serverResponse = in.readLine();
                if (serverResponse == null) {
                    terminate();
                    break;
                }
                messageHandler.handleMessage(serverResponse);
            } catch (IOException e) {
                log.error("Could not receive message. " + e.getMessage());
            }
        }
    }

    /**
     * This method is used to stop the run method.
     */
    public void terminate() {
        log.info("Attempting to terminate client.");
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Cannot close the socket", e);
        }
    }
}
