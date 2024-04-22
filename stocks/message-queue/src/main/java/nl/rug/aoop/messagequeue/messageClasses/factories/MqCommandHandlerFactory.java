package nl.rug.aoop.messagequeue.messageClasses.factories;

import lombok.Getter;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messageClasses.commands.MqPutCommand;
import nl.rug.aoop.messagequeue.messageClasses.commands.MqStockCommand;
import nl.rug.aoop.messagequeue.messageClasses.commands.MqTraderCommand;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;

/**
 * This class contains the attributes and behaviour of a MqCommandHandlerFactory.
 */
public class MqCommandHandlerFactory implements AbstractMqCommandHandlerFactory {

    @Getter
    private final MessageQueue queue;

    /**
     * Constructor used to initialize a MqCommandHandlerFactory.
     *
     * @param queue is the queue that is passed to commands so they can alter it
     */
    public MqCommandHandlerFactory(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public CommandHandler create() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MqPutCommand(queue));
        commandHandler.registerCommand("MqStock", new MqStockCommand(queue));
        commandHandler.registerCommand("MqTrader", new MqTraderCommand(queue));
        return commandHandler;
    }
}
