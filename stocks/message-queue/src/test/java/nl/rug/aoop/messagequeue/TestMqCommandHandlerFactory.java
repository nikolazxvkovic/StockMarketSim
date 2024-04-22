package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.factories.MqCommandHandlerFactory;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMqCommandHandlerFactory {

    private MessageQueue messageQueue;
    private MqCommandHandlerFactory mqCommandHandlerFactory;

    @BeforeEach
    public void setUp() {
        messageQueue = new PriorityBlockingMessageQueue();
        mqCommandHandlerFactory = new MqCommandHandlerFactory(messageQueue);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, mqCommandHandlerFactory.getQueue().getSize());
    }

    @Test
    public void testCreate() {
        assertTrue(mqCommandHandlerFactory.create().getCommandMap().containsKey("MqPut"));
    }
}
