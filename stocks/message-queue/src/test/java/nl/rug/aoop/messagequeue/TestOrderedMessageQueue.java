package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.queues.OrderedMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderedMessageQueue {

    private SortedMap<LocalDateTime, Message> list;
    private OrderedMessageQueue orderedQue;
    private String header1, header2, header3, body1, body2, body3;
    private Message message1, message2, message3;

    /**
     * Method used to initialize values used for each test.
     */
    @BeforeEach
    public void setUp() {
        orderedQue = new OrderedMessageQueue();
        list = new TreeMap<>();
        header1 = "123";
        body1 = "Hello!";
        message1 = new Message(header1, body1);
        list.put(message1.getTimestamp(), message1);
        header2 = "#52";
        body2 = "This is a test message.";
        message2 = new Message(header2, body2);
        list.put(message2.getTimestamp(), message2);
        header3 = "S4308491";
        body3 = "Please ignore it";
        message3 = new Message(header3, body3);
        list.put(message3.getTimestamp(), message3);
    }

    /**
     * Test for the constructor of the ordered message queue which asserts that the size of the queue is equal to zero
     * when initialized.
     */
    @Test
    public void testOrderedQueueConstructor() {
        assertEquals(0, orderedQue.getMessages().size());
    }

    /**
     * Test for the enqueue method which enqueues three messages and asserts that two treemaps containing the same
     * three messages are the same and that the size of the ordered queue is equal to 3 afterwards.
     */
    @Test
    public void testEnqueue() {
        orderedQue.enqueue(message1);
        orderedQue.enqueue(message2);
        orderedQue.enqueue(message3);
        assertEquals(orderedQue.getMessages(), list);
        assertEquals(3, orderedQue.getMessages().size());
    }

    /**
     * Test for the enqueue method when null is passed as an argument.
     */
    @Test
    public void testEnqueueInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> orderedQue.enqueue(null));
    }

    /**
     * Test for the enqueue method when a message is enqueued to an ordered queue that already contains a message with
     * the same key.
     */
    @Test
    public void testEnqueueSameKey() {
        String headerTest = "1";
        String bodyTest = "Same key";
        Message messageTest = new Message(headerTest, bodyTest);
        orderedQue.enqueue(messageTest);
        assertThrows(IllegalArgumentException.class, () -> orderedQue.enqueue(messageTest));
    }

    /**
     * Test for the dequeue method which enqueues three messages and asserts that two treemaps containing the same three
     * messages are the same after dequeue-ing one of the messages and that the size of the ordered queue is equal to 2
     * afterwards.
     */
    @Test
    public void testDequeue() {
        orderedQue.enqueue(message1);
        orderedQue.enqueue(message2);
        orderedQue.enqueue(message3);
        list.remove(list.firstKey());
        orderedQue.dequeue();
        assertEquals(orderedQue.getMessages(), list);
        assertEquals(2, orderedQue.getSize());
    }

    /**
     * Test for the dequeue method for when the ordered queue contains no messages.
     */
    @Test
    public void testDequeueEmptyQueue() {
        assertNull(orderedQue.dequeue());
    }

    /**
     * Test for the getSize method.
     */
    @Test
    public void testGetSize() {
        assertEquals(0, orderedQue.getSize());
        orderedQue.enqueue(message1);
        orderedQue.enqueue(message2);
        orderedQue.enqueue(message3);
        assertEquals(3, orderedQue.getSize());
        assertEquals(list.size(), orderedQue.getSize());
        orderedQue.dequeue();
        orderedQue.dequeue();
        orderedQue.dequeue();
        assertEquals(0, orderedQue.getSize());
    }
}
