package nl.rug.aoop.messagequeue;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.messageHandlers.MqMessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMqMessageHandler {

    private CommandHandler mockCommandHandler;
    private MqMessageHandler mqMessageHandler;

    @BeforeEach
    public void setUp() {
        mockCommandHandler = Mockito.mock(CommandHandler.class);
        mqMessageHandler = new MqMessageHandler(mockCommandHandler);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, mqMessageHandler.getMap().size());
    }

    @Test
    public void testHandleMessage() {
        Message message = new Message("test", "message");
        String jsonMessage = message.toJson();
        mqMessageHandler.handleMessage(jsonMessage);
        Mockito.verify(mockCommandHandler).execute(message.getHeader(), mqMessageHandler.getMap());
    }
}
