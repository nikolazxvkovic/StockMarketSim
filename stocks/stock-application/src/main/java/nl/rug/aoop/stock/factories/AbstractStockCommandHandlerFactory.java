package nl.rug.aoop.stock.factories;

import nl.rug.aoop.command.CommandHandler;

/**
 * This interface contains the attributes and behaviour of an AbstractMqCommandHandlerFactory.
 */
public interface AbstractStockCommandHandlerFactory {
    /**
     * Creates the command.
     *
     * @return the command
     */
    CommandHandler create();
}
