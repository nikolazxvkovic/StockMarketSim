package nl.rug.aoop.stock.stockData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class contains the attributes and behaviour of a Stock.
 */
@Getter
@Setter
@NoArgsConstructor
public class Stock {
    private String symbol, name;
    private long sharesOutstanding;
    private double initialPrice, marketCap;

    /**
     * A constructor.
     *
     * @param symbol            symbol of the stock
     * @param name              name of the stock.
     * @param sharesOutstanding shares left.
     * @param initialPrice      initial price of the stock.
     */
    public Stock(String symbol, String name, long sharesOutstanding, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.sharesOutstanding = sharesOutstanding;
        this.initialPrice = initialPrice;
        this.marketCap = sharesOutstanding * initialPrice;
    }
}
