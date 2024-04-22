package nl.rug.aoop.stock.stockData;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * This class contains the attributes and behaviour of a TraderCollection.
 */
@Getter
@NoArgsConstructor
public class TraderCollection {
    private Map<String, Trader> traders;

    /**
     * A constructor.
     *
     * @param traders a map for the stocks using the yaml file.
     */
    public TraderCollection(Map<String, Trader> traders) {
        this.traders = traders;
    }
}
