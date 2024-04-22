package nl.rug.aoop.stock.view;

import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.stock.stockData.Stock;

/**
 * This class contains the attributes and behaviour of a StockModel.
 */
public class StockModel implements StockDataModel {
    private final Stock stock;

    /**
     * A constructor.
     *
     * @param stock the stock.
     */
    public StockModel(Stock stock) {
        this.stock = stock;
    }

    /**
     * A getter.
     *
     * @return gets the symbol.
     */
    @Override
    public String getSymbol() {
        return stock.getSymbol();
    }

    /**
     * A getter.
     *
     * @return gets the name.
     */
    @Override
    public String getName() {
        return stock.getName();
    }

    /**
     * A getter.
     *
     * @return gets the shares.
     */
    @Override
    public long getSharesOutstanding() {
        return stock.getSharesOutstanding();
    }

    /**
     * A getter.
     *
     * @return gets the market cap.
     */
    @Override
    public double getMarketCap() {
        return stock.getMarketCap();
    }

    /**
     * A getter.
     *
     * @return gets the price of the stock.
     */
    @Override
    public double getPrice() {
        return stock.getInitialPrice();
    }
}
