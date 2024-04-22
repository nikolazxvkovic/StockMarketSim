package nl.rug.aoop.traderapplication.strategy;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.stock.stockData.StockCollection;
import nl.rug.aoop.stock.stockData.Trader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class contains the attributes and behaviour of a Random Strategy.
 */
@Slf4j
public class RandomStrategy implements TraderStrategy {
    private final List<String> order;
    private final Trader trader;
    private final StockCollection stockCollection;
    private String randomOrder, randomStock;
    private Integer randomShares;
    private double calculatedPrice;

    /**
     * A method that chooses 'buy' or 'sell'.
     *
     * @param trader          the trader.
     * @param stockCollection the stock info.
     */
    public RandomStrategy(Trader trader, StockCollection stockCollection) {
        this.trader = trader;
        this.stockCollection = stockCollection;
        this.order = new ArrayList<>();
        order.add("buy");
        order.add("sell");
    }

    /**
     * Runs the strategy.
     */
    @Override
    public void doStrategy() {
        randomShares = randomShares();
        //Delete after we can confirm that it runs properly
        log.info("Random order: " + randomOrder);
        log.info("Random stock: " + randomStock);
        log.info("Random shares: " + randomShares);
        log.info("Random price : " + calculatedPrice);
        //
    }

    /**
     * Chooses random buy or sell.
     *
     * @return returns random order.
     */
    public String randomOrder() {
        Random random = new Random();
        return order.get(random.nextInt(order.size()));
    }

    /**
     * Chooses a random stock.
     *
     * @return returns a random stock
     */
    public String randomStock() {
        randomOrder = randomOrder();
        Random random = new Random();
        if (randomOrder.equals("buy")) {
            List<String> keyList = new ArrayList<>(stockCollection.getStocks().keySet());
            int randomPosition = random.nextInt(keyList.size());
            return keyList.get(randomPosition);
        } else {
            List<String> keyList = new ArrayList<>(trader.getOwnedShares().keySet());
            int randomPosition = random.nextInt(keyList.size());
            return keyList.get(randomPosition);
        }
    }

    /**
     * Calculates the random share.
     *
     * @return returns the random amount of shares
     */
    public Integer randomShares() {
        randomStock = randomStock();
        Random random = new Random();
        long noOfSharesAvailableStock = stockCollection.getStocks().get(randomStock).getSharesOutstanding();
        calculatedPrice = calculatePrice(randomStock);
        if (randomOrder.equals("buy")) {
            if ((trader.getFunds() / calculatedPrice) <= noOfSharesAvailableStock) {
                return (int) random.nextDouble(0, trader.getFunds() / calculatedPrice);
            } else {
                return (int) random.nextLong(0, noOfSharesAvailableStock);
            }
        } else {
            Integer noOfSharesAvailableTrader = trader.getOwnedShares().get(randomStock);
            return random.nextInt(0, noOfSharesAvailableTrader);
        }
    }

    /**
     * Calculates the random price of the shares.
     *
     * @param randomStock random stock price
     * @return returns the random stock price
     */
    public double calculatePrice(String randomStock) {
        double stockPrice = stockCollection.getStocks().get(randomStock).getInitialPrice();
        Random random = new Random();
        if (randomOrder.equals("sell")) {
            return random.nextDouble(stockPrice - 10, stockPrice);
        } else {
            return random.nextDouble(stockPrice, stockPrice + 10);
        }
    }

    /**
     * Generates the order.
     *
     * @return returns the order.
     */
    public Message generateOrder() {
        Message generatedOrder = new Message(randomOrder, randomStock);
        generatedOrder.setNumber(randomShares);
        generatedOrder.setPrice(calculatedPrice);
        generatedOrder.setTraderId(trader.getId());
        return generatedOrder;
    }
}
