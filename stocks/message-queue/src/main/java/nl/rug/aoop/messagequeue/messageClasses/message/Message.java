package nl.rug.aoop.messagequeue.messageClasses.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * The Message class contains the attributes of a message.
 */
public final class Message implements Comparable<Message> {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Message.class, new MessageAdapter().nullSafe())
            .create();
    private final String header, body;
    @Setter
    private LocalDateTime timestamp;
    @Getter
    @Setter
    private int number;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private String traderId, symbol;
    @Getter
    @Setter
    private Integer traderFunds;
    @Getter
    @Setter
    private Map<String, Integer> traderOwnedShares;

    /**
     * This constructor is used to initialize a message.
     *
     * @param header is the header of the message
     * @param body   is the body of the message, the string containing the actual message
     */
    public Message(String header, String body) {
        if (header == null || body == null) {
            throw new IllegalArgumentException("Cannot create message with null contents.");
        }
        this.header = header;
        this.body = body;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * This method is used to convert a Json string to a message object.
     *
     * @param string is the Json string that needs to be converted
     * @return .
     */
    public static Message fromJson(String string) {
        return GSON.fromJson(string, Message.class);
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * This method is used to convert a message to a Json string.
     *
     * @return .
     */
    public String toJson() {
        return GSON.toJson(this);
    }

    @Override
    public int compareTo(Message message) {
        if (timestamp.isEqual(message.getTimestamp())) {
            return 0;
        } else if (timestamp.isAfter(message.getTimestamp())) {
            return 1;
        } else {
            return -1;
        }
    }
}
