package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.producers.SendMessage;
import nl.rug.aoop.messagequeue.messageClasses.queues.OrderedMessageQueue;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import nl.rug.aoop.messagequeue.messageClasses.queues.UnorderedMessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSendMessage {

    private MessageQueue messageQueue1, messageQueue2, messageQueue3, messageQueueTest1, messageQueueTest2,
            messageQueueTest3;
    private Message message1, message2;
    private SendMessage messageSender1, messageSender2, messageSender3;

    @Test
    public void testConstructor() {
        messageQueue1 = new UnorderedMessageQueue();
        messageQueue2 = new OrderedMessageQueue();
        messageQueue3 = new PriorityBlockingMessageQueue();
        SendMessage sendMessage1 = new SendMessage(messageQueue1);
        SendMessage sendMessage2 = new SendMessage(messageQueue2);
        SendMessage sendMessage3 = new SendMessage(messageQueue3);
        assertEquals(0, sendMessage1.getMessageQueue().getSize());
        assertEquals(0, sendMessage2.getMessageQueue().getSize());
        assertEquals(0, sendMessage3.getMessageQueue().getSize());
    }

    @BeforeEach
    public void setUp() {
        messageQueue1 = new UnorderedMessageQueue();
        messageQueue2 = new OrderedMessageQueue();
        messageQueue3 = new PriorityBlockingMessageQueue();
        messageQueueTest1 = new UnorderedMessageQueue();
        messageQueueTest2 = new OrderedMessageQueue();
        messageQueueTest3 = new PriorityBlockingMessageQueue();
        message1 = new Message("Test", "12353421");
        message2 = new Message("Another test", "987");
        messageSender1 = new SendMessage(messageQueue1);
        messageSender2 = new SendMessage(messageQueue2);
        messageSender3 = new SendMessage(messageQueue3);
    }

    @Test
    public void testPutInvalidInputUnorderedQueue() {
        assertThrows(IllegalArgumentException.class, () -> messageSender1.put(null));
    }

    @Test
    public void testPutInvalidInputOrderedQueue() {
        assertThrows(IllegalArgumentException.class, () -> messageSender2.put(null));
    }

    @Test
    public void testPutInvalidInputPriorityBlockingQueue() {
        assertThrows(IllegalArgumentException.class, () -> messageSender3.put(null));
    }

    @Test
    public void testPutUnorderedQueue() {
        messageSender1.put(message1);
        messageQueueTest1.enqueue(message1);
        assertEquals(messageQueue1.getSize(), messageQueueTest1.getSize());
        assertEquals(messageQueue1.getSize(), 1);
        messageSender1.put(message2);
        messageQueueTest1.enqueue(message2);
        assertEquals(messageQueue1.getSize(), messageQueueTest1.getSize());
        assertEquals(messageQueue1.getSize(), 2);
    }

    @Test
    public void testPutOrderedQueue() {
        messageSender2.put(message1);
        messageQueueTest2.enqueue(message1);
        assertEquals(messageQueue2.getSize(), messageQueueTest2.getSize());
        assertEquals(messageQueue2.getSize(), 1);
        messageSender2.put(message2);
        messageQueueTest2.enqueue(message2);
        assertEquals(messageQueue2.getSize(), messageQueueTest2.getSize());
        assertEquals(messageQueue2.getSize(), 2);
    }

    @Test
    public void testPutPriorityBlockingQueue() {
        messageSender3.put(message1);
        messageQueueTest3.enqueue(message1);
        assertEquals(messageQueue3.getSize(), messageQueueTest3.getSize());
        assertEquals(messageQueue3.getSize(), 1);
        messageSender3.put(message2);
        messageQueueTest3.enqueue(message2);
        assertEquals(messageQueue3.getSize(), messageQueueTest3.getSize());
        assertEquals(messageQueue3.getSize(), 2);
    }
}
