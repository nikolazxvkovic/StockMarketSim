package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.queues.PriorityBlockingMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class TestPriorityBlockingMessageQueue {

    private PriorityBlockingQueue<Message> list;
    private PriorityBlockingMessageQueue priorityBlockingMessageQueue;
    private String header1, header2, header3, body1, body2, body3;
    private Message message1, message2, message3;

    /**
     * Method used to initialize values used for each test.
     */
    @BeforeEach
    public void setUp() {
        priorityBlockingMessageQueue = new PriorityBlockingMessageQueue();
        list = new PriorityBlockingQueue<>();
        header1 = "123";
        body1 = "Hello!";
        message1 = new Message(header1, body1);
        list.add(message1);
        header2 = "#52";
        body2 = "This is a test message.";
        message2 = new Message(header2, body2);
        list.add(message2);
        header3 = "S4308491";
        body3 = "Please ignore it";
        message3 = new Message(header3, body3);
        list.add(message3);
    }

    /**
     * Test for the constructor of the priority blocking message queue which asserts that the size of the queue is
     * equal to zero when initialized.
     */
    @Test
    public void testOrderedQueueConstructor() {
        assertEquals(0, priorityBlockingMessageQueue.getMessages().
                size());
    }

    /**
     * Test for the enqueue method which enqueues three messages and asserts that two priority blocking queues
     * containing the same three messages are the same and that the size of the ordered queue is equal to 3 afterwards.
     */
    @Test
    public void testEnqueue() {
        priorityBlockingMessageQueue.enqueue(message1);
        priorityBlockingMessageQueue.enqueue(message2);
        priorityBlockingMessageQueue.enqueue(message3);
        assertArrayEquals(priorityBlockingMessageQueue.getMessages().toArray(), list.toArray());
        assertEquals(3, priorityBlockingMessageQueue.getMessages().size());
    }

    /**
     * Test for the enqueue method when null is passed as an argument.
     */
    @Test
    public void testEnqueueInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> priorityBlockingMessageQueue.enqueue(null));
    }

    /**
     * Test for the dequeue method which enqueues three messages and asserts that two priority blocking queues
     * containing the same three messages are the same after dequeue-ing one of the messages and that the size of the
     * queue is equal to 2 afterwards.
     */
    @Test
    public void testDequeue() {
        priorityBlockingMessageQueue.enqueue(message1);
        priorityBlockingMessageQueue.enqueue(message2);
        priorityBlockingMessageQueue.enqueue(message3);
        list.remove();
        priorityBlockingMessageQueue.dequeue();
        assertArrayEquals(priorityBlockingMessageQueue.getMessages().toArray(), list.toArray());
        assertEquals(2, priorityBlockingMessageQueue.getSize());
    }

    /**
     * Test for the dequeue method for when the priority blocking queue contains no messages.
     */
    @Test
    public void testDequeueEmptyQueue() {
        assertNull(priorityBlockingMessageQueue.dequeue());
    }

    /**
     * Test for the getSize method.
     */
    @Test
    public void testGetSize() {
        assertEquals(0, priorityBlockingMessageQueue.getSize());
        priorityBlockingMessageQueue.enqueue(message1);
        priorityBlockingMessageQueue.enqueue(message2);
        priorityBlockingMessageQueue.enqueue(message3);
        assertEquals(3, priorityBlockingMessageQueue.getSize());
        assertEquals(list.size(), priorityBlockingMessageQueue.getSize());
        priorityBlockingMessageQueue.dequeue();
        priorityBlockingMessageQueue.dequeue();
        priorityBlockingMessageQueue.dequeue();
        assertEquals(0, priorityBlockingMessageQueue.getSize());
    }
}
