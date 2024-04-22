package nl.rug.aoop.messagequeue;

import com.google.gson.Gson;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.commands.MqPutCommand;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMqPutCommand {

    private MessageQueue messageQueue;
    private MqPutCommand mqPutCommand;

    @BeforeEach
    public void setUp() {
        messageQueue = new PriorityBlockingMessageQueue();
        mqPutCommand = new MqPutCommand(messageQueue);
    }

//    @Test
//    public void testConstructor() {
//        assertEquals(0, mqPutCommand.getQueue().getSize());
//    }
//
//    @Test
//    public void testExecute() {
//        Map<String, Object> map = new HashMap<>();
//        Message message1 = new Message("Test", "Message");
//        Gson gson = new Gson();
//        map.put("body", gson.toJson(message1));
//        mqPutCommand.execute(map);
//        Message message2 = mqPutCommand.getQueue().dequeue();
//        assertEquals(message2.getHeader(), message1.getHeader());
//        assertEquals(message2.getBody(), message1.getBody());
//        assertEquals(message2.getTimestamp(), message1.getTimestamp());
//    }
}
