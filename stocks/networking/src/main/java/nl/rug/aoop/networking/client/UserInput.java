package nl.rug.aoop.networking.client;

import java.util.Scanner;

/**
 * This class contains methods used to send user-written messages to the server.
 */
public class UserInput implements Runnable {
    private final Scanner scanner;
    private boolean running = false;
    private final Client client;

    /**
     * Constructor for UserInput.
     *
     * @param client is the client that sends the messages
     */
    public UserInput(Client client) {
        this.client = client;
        this.scanner = new Scanner(System.in);
    }

    /**
     * This method is used to take user input and send it to the server as a string.
     */
    @Override
    public void run() {
        running = true;
        while (running) {
            String input = scanner.nextLine();
            client.sendMessage(input);
            if (input.trim().equalsIgnoreCase("BYE")) {
                client.terminate();
                terminate();
            }
        }
    }

    /**
     * This method is used to stop the run method.
     */
    public void terminate() {
        running = false;
    }
}

