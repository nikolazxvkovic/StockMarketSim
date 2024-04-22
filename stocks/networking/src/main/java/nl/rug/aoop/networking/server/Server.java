package nl.rug.aoop.networking.server;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.networking.messageHandlers.MessageHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server class contains the attributes and behaviour of a server.
 */
@Slf4j
public class Server implements Runnable {
    @Getter
    private boolean running = false;
    private int threadNr;
    private final ServerSocket serverSocket;
    private final ExecutorService executorService;
    private final MessageHandler messageHandler;
    @Getter
    private Socket socket;
    @Getter
    private final List<ClientHandler> clientHandlers;

    /**
     * Constructor used to initialize a server.
     *
     * @param port           is a port number given by the user
     * @param messageHandler is a message handler
     * @throws IOException .
     */
    public Server(int port, MessageHandler messageHandler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.executorService = Executors.newCachedThreadPool();
        this.threadNr = 0;
        this.messageHandler = messageHandler;
        this.clientHandlers = new ArrayList<>();
    }

    /**
     * This method is used to wait for incoming connections from clients and put them in separate threads.
     */
    public void run() {
        running = true;
        log.info("Server starting on port: " + serverSocket.getLocalPort());
        while (running) {
            try {
                socket = serverSocket.accept();
                log.info("Spawning thread: " + threadNr);
                ClientHandler clientHandler = new ClientHandler(socket, threadNr, messageHandler);
                clientHandlers.add(clientHandler);
                executorService.submit(clientHandler);
                threadNr++;
            } catch (IOException e) {
                log.error("Something went wrong with spawning the client handler." + e.getMessage());
            }
        }
    }

    /**
     * This method is used to stop the run method.
     */
    public void terminate() {
        running = false;
        executorService.shutdownNow();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public int getNumberOfClients() {
        return threadNr;
    }
}
