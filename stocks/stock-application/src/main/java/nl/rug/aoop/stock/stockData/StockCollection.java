package nl.rug.aoop.stock.stockData;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * This class contains the attributes and behaviour of a StockCollection.
 */
@NoArgsConstructor
public class StockCollection {
    @Getter
    private Map<String, Stock> stocks;

    /**
     * A constructor.
     *
     * @param stocks a map for the stocks using the yaml file.
     */
    public StockCollection(Map<String, Stock> stocks) {
        this.stocks = stocks;
    }
}
