package nl.rug.aoop.messagequeue;

import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.messagequeue.messageClasses.queues.UnorderedMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnorderedMessageQueue {

    private UnorderedMessageQueue que;
    private Queue<Message> list;
    private String header1, header2, header3, body1, body2, body3;
    private Message message1, message2, message3;

    /**
     * Method used to initialize values used for each test.
     */
    @BeforeEach
    public void setUp() {
        que = new UnorderedMessageQueue();
        list = new LinkedList<>();
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
     * Test for the constructor of the unordered message queue which asserts that the size of the queue is equal to zero
     * when initialized.
     */
    @Test
    public void testQueueConstructor() {
        assertEquals(0, que.getMessages().size());
    }

    /**
     * Test for the enqueue method which enqueues three messages and asserts that two linkedlists containing the same
     * three messages are the same and that the size of the unordered queue is equal to 3 afterwards.
     */
    @Test
    public void testEnqueue() {
        que.enqueue(message1);
        que.enqueue(message2);
        que.enqueue(message3);
        assertArrayEquals(que.getMessages().toArray(), list.toArray());
        assertEquals(3, que.getSize());
    }

    /**
     * Test for the enqueue method when null is passed as an argument.
     */
    @Test
    public void testEnqueueInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> que.enqueue(null));
    }

    /**
     * Test for the dequeue method which enqueues three messages and asserts that two linkedlists containing the same
     * three messages are the same after dequeue-ing two of the messages and that the size of the unordered queue is
     * equal to 1 afterwards.
     */
    @Test
    public void testDequeue() {
        que.enqueue(message1);
        que.enqueue(message2);
        que.enqueue(message3);
        assertEquals(message1, que.dequeue());
        list.remove();
        assertArrayEquals(que.getMessages().toArray(), list.toArray());
        assertEquals(message2, que.dequeue());
        assertEquals(1, que.getSize());
    }

    /**
     * Test for the dequeue method for when the unordered queue contains no messages.
     */
    @Test
    public void testDequeueEmptyQueue() {
        assertNull(que.dequeue());
    }

    /**
     * Test for the getSize method.
     */
    @Test
    public void testGetSize() {
        assertEquals(0, que.getSize());
        que.enqueue(message1);
        que.enqueue(message2);
        que.enqueue(message3);
        assertEquals(3, que.getSize());
        assertEquals(list.size(), que.getSize());
        que.dequeue();
        que.dequeue();
        que.dequeue();
        assertEquals(0, que.getSize());
    }

}
