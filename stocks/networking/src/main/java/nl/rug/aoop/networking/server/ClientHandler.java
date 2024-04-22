package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The ClientHandler class handles messages coming from the client.
 */
@Slf4j
public class ClientHandler implements Runnable {

    private final Socket socket;
    @Getter
    private boolean running = false;
    private final int threadNr;
    private final PrintWriter out;
    @Getter
    private final BufferedReader in;
    private final MessageHandler messageHandler;

    /**
     * Constructor used to initialize a client handler.
     *
     * @param socket         is the socket that connects the client to the server
     * @param threadNr       is the thread number on which the client is running
     * @param messageHandler is a message handler
     * @throws IOException .
     */
    public ClientHandler(Socket socket, int threadNr, MessageHandler messageHandler) throws IOException {
        this.messageHandler = messageHandler;
        this.socket = socket;
        this.threadNr = threadNr;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * This method is used to read messages from the client and sends them to the handler, and it terminates when the
     * message that has been received is null.
     */
    @Override
    public void run() {
        running = true;
        out.println("Hello!");
        while (running) {
            try {
                String input = in.readLine();
                if (input == null) {
                    terminate();
                    break;
                }
                messageHandler.handleMessage(input);
            } catch (IOException e) {
                log.error("Could not read input from the client.");
            }
        }
    }

    /**
     * This method is used to send a string message to the connected client.
     * @param message is the message we want to send
     */
    public void sendMessage(String message) {
        if (message == null || message.equals("")) {
            throw new IllegalArgumentException("Attempting to send an invalid message.");
        }
        out.println(message);
    }

    /**
     * This method is used to stop the run method.
     */
    public void terminate() {
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            log.error("Cannot close the socket.", e);
        }
    }
}
