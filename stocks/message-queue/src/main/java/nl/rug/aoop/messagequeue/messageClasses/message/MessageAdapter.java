package nl.rug.aoop.messagequeue.messageClasses.message;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Custom type adapter for Message class.
 * Determines how the Message class is converted to/from Json.
 */
public class MessageAdapter extends TypeAdapter<Message> {
    /**
     * Header field.
     */
    public static final String HEADER_FIELD = "Header";
    /**
     * Body field.
     */
    public static final String BODY_FIELD = "Body";
    /**
     * Timestamp field.
     */
    public static final String TIME_FIELD = "Time";
    /**
     * Symbol field.
     */
    public static final String SYMBOL_FIELD = "Symbol";
    /**
     * Number field.
     */
    public static final String NUMBER_FIELD = "Number";
    /**
     * Price field.
     */
    public static final String PRICE_FIELD = "Price";
    /**
     * TraderId field.
     */
    public static final String TRADERID_FIELD = "TraderId";
    /**
     * TraderFunds field.
     */
    public static final String TRADERFUNDS_FIELD = "TraderFunds";
    private String header = null, body = null, traderId = null, symbol = null;
    private int traderFunds = 0, number = 0;
    private double price = 0;
    private LocalDateTime time = null;

    /**
     * Reads a Message object and returns it in its JSON string form.
     *
     * @param reader JSON reader
     * @return message in JSON string form
     * @throws IOException exception to be thrown
     */
    @Override
    public Message read(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            String fieldName = null;
            if (token.equals(JsonToken.NAME)) {
                fieldName = reader.nextName();
            }
            if (fieldName == null) {
                continue;
            }
            switchMethod(fieldName, reader);
        }
        reader.endObject();
        Message message = new Message(header,body);
        message.setTimestamp(time);
        message.setTraderId(traderId);
        message.setSymbol(symbol);
        message.setTraderFunds(traderFunds);
        message.setPrice(price);
        message.setNumber(number);
        return message;
    }

    /**
     * Takes a string and converts it to a Message object.
     *
     * @param writer JSON writer
     * @param message message the result should be stored in
     * @throws IOException exception to be thrown
     */
    @Override
    public void write(JsonWriter writer, Message message) throws IOException {
        writer.beginObject();
        writer.name(HEADER_FIELD);
        writer.value(message.getHeader());
        writer.name(BODY_FIELD);
        writer.value(message.getBody());
        writer.name(TRADERID_FIELD);
        writer.value(message.getTraderId());
        writer.name(SYMBOL_FIELD);
        writer.value(message.getSymbol());
        writer.name(TIME_FIELD);
        writer.value(message.getTimestamp().toString());
        writer.name(TRADERFUNDS_FIELD);
        writer.value(message.getTraderFunds());
        writer.name(NUMBER_FIELD);
        writer.value(message.getNumber());
        writer.name(PRICE_FIELD);
        writer.value(message.getPrice());
        writer.endObject();
    }

    /**
     * Method used for adapting JSon messages.
     *
     * @param fieldName fieldName
     * @param reader JSon reader
     * @throws IOException exception to be thrown
     */
    public void switchMethod(String fieldName, JsonReader reader) throws IOException {
        switch (fieldName) {
            case HEADER_FIELD:
                header = reader.nextString();
                break;
            case BODY_FIELD:
                body = reader.nextString();
                break;
            case TRADERID_FIELD:
                traderId = reader.nextString();
                break;
            case SYMBOL_FIELD:
                symbol = reader.nextString();
                break;
            case TIME_FIELD:
                time = LocalDateTime.parse(reader.nextString());
                break;
            case TRADERFUNDS_FIELD:
                traderFunds = reader.nextInt();
                break;
            case NUMBER_FIELD:
                number = reader.nextInt();
                break;
            case PRICE_FIELD:
                price = reader.nextDouble();
                break;
        }
    }
}
