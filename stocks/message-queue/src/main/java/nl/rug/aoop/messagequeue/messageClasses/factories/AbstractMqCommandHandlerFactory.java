package nl.rug.aoop.messagequeue.messageClasses.factories;

import nl.rug.aoop.command.CommandHandler;

/**
 * This class contains the attributes and behaviour of an AbstractMqCommandHandlerFactory.
 */
public interface AbstractMqCommandHandlerFactory {
    /**
     * This method needs to be overridden and implemented to create a command handler.
     *
     * @return .
     */
    CommandHandler create();
}
