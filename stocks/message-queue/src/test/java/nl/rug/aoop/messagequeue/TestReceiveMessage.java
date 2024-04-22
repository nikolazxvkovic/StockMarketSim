package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.consumers.ReceiveMessage;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.queues.OrderedMessageQueue;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import nl.rug.aoop.messagequeue.messageClasses.queues.UnorderedMessageQueue;
import nl.rug.aoop.messagequeue.messageInterfaces.MessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestReceiveMessage {

    private MessageQueue messageQueue1, messageQueue2, messageQueue3, messageQueueTest1, messageQueueTest2,
            messageQueueTest3;
    private Message message;
    private ReceiveMessage messageReceiver1, messageReceiver2, messageReceiver3;

    @Test
    public void testConstructor() {
        messageQueue1 = new UnorderedMessageQueue();
        messageQueue2 = new OrderedMessageQueue();
        messageQueue3 = new PriorityBlockingMessageQueue();
        ReceiveMessage receiveMessage1 = new ReceiveMessage(messageQueue1);
        ReceiveMessage receiveMessage2 = new ReceiveMessage(messageQueue2);
        ReceiveMessage receiveMessage3 = new ReceiveMessage(messageQueue3);
        assertEquals(0, receiveMessage1.getMessageQueue().getSize());
        assertEquals(0, receiveMessage2.getMessageQueue().getSize());
        assertEquals(0, receiveMessage3.getMessageQueue().getSize());
    }

    @BeforeEach
    public void setUp() {
        messageQueue1 = new UnorderedMessageQueue();
        messageQueue2 = new OrderedMessageQueue();
        messageQueue3 = new PriorityBlockingMessageQueue();
        messageQueueTest1 = new UnorderedMessageQueue();
        messageQueueTest2 = new OrderedMessageQueue();
        messageQueueTest3 = new PriorityBlockingMessageQueue();
        message = new Message("Test", "12353421");
        messageQueue1.enqueue(message);
        messageQueue2.enqueue(message);
        messageQueue3.enqueue(message);
        messageQueueTest1.enqueue(message);
        messageQueueTest2.enqueue(message);
        messageQueueTest3.enqueue(message);
        messageReceiver1 = new ReceiveMessage(messageQueue1);
        messageReceiver2 = new ReceiveMessage(messageQueue2);
        messageReceiver3 = new ReceiveMessage(messageQueue3);
    }

    @Test
    public void testPollEmptyUnorderedQueue() {
        messageReceiver1.poll();
        assertNull(messageReceiver1.poll());
    }

    @Test
    public void testPollEmptyOrderedQueue() {
        messageReceiver2.poll();
        assertNull(messageReceiver2.poll());
    }

    @Test
    public void testPollEmptyPriorityBlockingQueue() {
        messageReceiver3.poll();
        assertNull(messageReceiver3.poll());
    }

    @Test
    public void testPollUnorderedQueue() {
        Message messagePoll = new Message("021", "Test message");
        messageQueue1.enqueue(messagePoll);
        messageQueueTest1.enqueue(messagePoll);
        assertEquals(messageReceiver1.poll(), messageQueueTest1.dequeue());
        assertEquals(1, messageQueue1.getSize());
        assertEquals(messageReceiver1.poll(), messageQueueTest1.dequeue());
        assertEquals(0, messageQueue1.getSize());
    }

    @Test
    public void testPollOrderedQueue() {
        Message messagePoll = new Message("021", "Test message");
        messageQueue2.enqueue(messagePoll);
        messageQueueTest2.enqueue(messagePoll);
        assertEquals(messageReceiver2.poll(), messageQueueTest2.dequeue());
        assertEquals(1, messageQueue2.getSize());
        assertEquals(messageReceiver2.poll(), messageQueueTest2.dequeue());
        assertEquals(0, messageQueue2.getSize());
    }

    @Test
    public void testPollPriorityBlockingQueue() {
        Message messagePoll = new Message("021", "Test message");
        messageQueue3.enqueue(messagePoll);
        messageQueueTest3.enqueue(messagePoll);
        assertEquals(messageReceiver3.poll(), messageQueueTest3.dequeue());
        assertEquals(1, messageQueue3.getSize());
        assertEquals(messageReceiver3.poll(), messageQueueTest3.dequeue());
        assertEquals(0, messageQueue3.getSize());
    }
}
