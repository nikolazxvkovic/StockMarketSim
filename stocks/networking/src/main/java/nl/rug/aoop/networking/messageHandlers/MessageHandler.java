package nl.rug.aoop.networking.messageHandlers;

/**
 * Interface for handling messages.
 */
public interface MessageHandler {
    /**
     * Method that needs to be overridden to implement functionality for handling a message.
     *
     * @param message is the message that needs to be handled.
     */
    void handleMessage(String message);
}
