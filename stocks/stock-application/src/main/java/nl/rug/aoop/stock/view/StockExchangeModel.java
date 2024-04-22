package nl.rug.aoop.stock.view;

import nl.rug.aoop.model.StockDataModel;
import nl.rug.aoop.model.StockExchangeDataModel;
import nl.rug.aoop.model.TraderDataModel;
import nl.rug.aoop.stock.stockData.StockCollection;
import nl.rug.aoop.stock.stockData.TraderCollection;

/**
 * This class contains the attributes and behaviour of a StockExchangeModel.
 */
public class StockExchangeModel implements StockExchangeDataModel {
    private final StockCollection stockCollection;
    private final TraderCollection traderCollection;
    private TraderModel traderModel;

    /**
     * A constructor.
     *
     * @param stockCollection  map of stocks.
     * @param traderCollection map of traders.
     */
    public StockExchangeModel(StockCollection stockCollection, TraderCollection traderCollection) {
        this.stockCollection = stockCollection;
        this.traderCollection = traderCollection;
    }

    /**
     * A getter.
     *
     * @param index The index of the stock that should be accessed.
     * @return the symbol.
     */
    @Override
    public StockDataModel getStockByIndex(int index) {
        int i = 0;
        String goodSymbol = null;
        for (String symbol : stockCollection.getStocks().keySet()) {
            if (index == i) {
                goodSymbol = symbol;
            }
            i++;
        }
        return (StockDataModel) stockCollection.getStocks().get(goodSymbol);
    }

    /**
     * A getter.
     *
     * @return returns the number of stocks.
     */
    @Override
    public int getNumberOfStocks() {
        return stockCollection.getStocks().size();
    }

    /**
     * A getter.
     *
     * @param index The index of the trader that should be accessed.
     * @return returns the name of trader.
     */
    @Override
    public TraderDataModel getTraderByIndex(int index) {
        int i = 0;
        String goodSymbol = null;
        for (String name : traderCollection.getTraders().keySet()) {
            if (index == i) {
                goodSymbol = name;
            }
            i++;
        }
        return (TraderDataModel) traderCollection.getTraders().get(goodSymbol);
    }

    /**
     * A getter.
     *
     * @return returns the size of the traders.
     */
    @Override
    public int getNumberOfTraders() {
        return traderCollection.getTraders().size();
    }
}
