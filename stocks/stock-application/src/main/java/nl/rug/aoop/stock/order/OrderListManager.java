package nl.rug.aoop.stock.order;

import lombok.Getter;
import lombok.Setter;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.stock.stockData.Loader;
import nl.rug.aoop.stock.stockData.StockCollection;
import nl.rug.aoop.stock.stockData.Trader;
import nl.rug.aoop.stock.stockData.TraderCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the attributes and behaviour of a OrderListManager.
 */
public class OrderListManager {
    @Getter
    private final List<Message> buyOrders, sellOrders;
    @Getter
    @Setter
    private StockCollection stockCollection;
    @Getter
    @Setter
    private TraderCollection traderCollection;

    /**
     * The constructor used to initialize an OrderListManager.
     */
    public OrderListManager() {
        this.buyOrders = new ArrayList<>();
        this.sellOrders = new ArrayList<>();
        stockCollection = Loader.initializeStockData();
        traderCollection = Loader.initializeTraderData();
    }

    /**
     * A method used to update the stock price.
     *
     * @param stock the stock being updated
     * @param price the new price
     */
    public void updateStockPrice(String stock, Double price) {
        stockCollection.getStocks().get(stock).setInitialPrice(price);
    }

    /**
     * A method to update the trader buy order.
     *
     * @param buyOrder the message buy order.
     */
    public void updateTraderBuyOrder(Message buyOrder) {
        String traderId = buyOrder.getTraderId();
        Trader trader = traderCollection.getTraders().get(traderId);
        double price = stockCollection.getStocks().get(buyOrder.getBody()).getInitialPrice();
        trader.setFunds(trader.getFunds() - (int) price * buyOrder.getNumber());
        if (trader.getOwnedShares().containsKey(buyOrder.getBody())) {
            int numberOfSharesOwned = trader.getOwnedShares().get(buyOrder.getBody());
            trader.getOwnedShares().put(buyOrder.getBody(), numberOfSharesOwned + buyOrder.getNumber());
        } else {
            trader.getOwnedShares().put(buyOrder.getBody(), buyOrder.getNumber());
        }
    }

    /**
     * A method to update the trader sell order.
     *
     * @param sellOrder the message sell order.
     */
    public void updateTraderSellOrder(Message sellOrder) {
        String traderId = sellOrder.getTraderId();
        Trader trader = traderCollection.getTraders().get(traderId);
        double price = stockCollection.getStocks().get(sellOrder.getBody()).getInitialPrice();
        trader.setFunds(trader.getFunds() + (int) price * sellOrder.getNumber());
        if (sellOrder.getNumber() == trader.getOwnedShares().get(sellOrder.getBody())) {
            trader.getOwnedShares().remove(sellOrder.getBody());
        } else {
            int numberOfSharesOwned = trader.getOwnedShares().get(sellOrder.getBody());
            trader.getOwnedShares().put(sellOrder.getBody(), numberOfSharesOwned - sellOrder.getNumber());
        }
    }
}
