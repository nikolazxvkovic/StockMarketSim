package nl.rug.aoop.messagequeue;

import com.google.gson.Gson;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMessage {

    /**
     * Test for the constructor of the message.
     */
    @Test
    public void testConstructor() {
        String header = "Abc65d";
        String body = "_/0{pq";
        Message message = new Message(header, body);
        assertEquals(header, message.getHeader());
        assertEquals(body, message.getBody());
    }

    /**
     * Test for when null is passed as argument to the constuctor of the message.
     */
//    @Test
//    public void testConstructorInvalidString() {
//        String key = "abc";
//        String value = "_/0{pq";
//        assertThrows(IllegalArgumentException.class, () -> new Message(null, null));
//        assertThrows(IllegalArgumentException.class, () -> new Message(key, null));
//        assertThrows(IllegalArgumentException.class, () -> new Message(null, value));
//    }
//
//    @Test
//    public void testToJson() {
//        Gson gson = new Gson();
//        String header = "Abc65d";
//        String body = "_/0{pq";
//        Message message = new Message(header, body);
//        String string1 = message.toJson();
//        String string2 = gson.toJson(message);
//        assertEquals(string1, string2);
//    }
//
//    @Test
//    public void testFromJson() {
//        String header = "Abc65d";
//        String body = "_/0{pq";
//        Message message = new Message(header, body);
//        String string = message.toJson();
//        assertEquals(Message.fromJson(string).getBody(), message.getBody());
//        assertEquals(Message.fromJson(string).getHeader(), message.getHeader());
//        assertEquals(Message.fromJson(string).getTimestamp(), message.getTimestamp());
//    }
}


