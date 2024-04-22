package nl.rug.aoop.stock.view;

import nl.rug.aoop.model.TraderDataModel;
import nl.rug.aoop.stock.stockData.Trader;

import java.util.List;

/**
 * This class contains the attributes and behaviour of a TraderModel.
 */
public class TraderModel implements TraderDataModel {
    private final Trader trader;

    /**
     * A constructor.
     *
     * @param trader the trader.
     */
    public TraderModel(Trader trader) {
        this.trader = trader;
    }

    /**
     * A getter.
     *
     * @return returns the id.
     */
    @Override
    public String getId() {
        return trader.getId();
    }

    /**
     * A getter.
     *
     * @return returns the name.
     */
    @Override
    public String getName() {
        return trader.getName();
    }

    /**
     * A getter.
     *
     * @return returns the funds.
     */
    @Override
    public double getFunds() {
        return trader.getFunds();
    }

    /**
     * A getter.
     *
     * @return returns the stocks.
     */
    @Override
    public List<String> getOwnedStocks() {
        return (List<String>) trader.getOwnedShares();
    }
}
