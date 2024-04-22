package nl.rug.aoop.stock.commands;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.stock.order.OrderListManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains the attributes and behaviour of a StockSellCommand.
 */
@Slf4j
public class StockSellCommand implements Command {
    private final List<Message> buyOrders, sellOrders;
    private final OrderListManager orderListManager;

    /**
     * The constructor used to initialize a StockBuy command.
     *
     * @param orderListManager is used to store the sell/buy order lists
     */
    public StockSellCommand(OrderListManager orderListManager) {
        this.orderListManager = orderListManager;
        this.buyOrders = orderListManager.getBuyOrders();
        this.sellOrders = orderListManager.getSellOrders();
    }

    /**
     * Method that is overridden from the Command interface and is used to add the sellOrder to the sellOrders list and
     * then start the matching procedure.
     *
     * @param options is a map containing messages whose headers are taken as commands
     */
    @Override
    public void execute(Map<String, Object> options) {
        Message currentSellOrder = (Message) options.get("order");
        sellOrders.add(currentSellOrder);
        matchOrderDetails(currentSellOrder, buyOrders);
    }

    /**
     * Matches the current sell order with the corresponding buy orders.
     *
     * @param currentSellOrder the current sell order
     * @param buyOrders        List of buy orders that satisfy the conditions
     */
    public void matchOrderDetails(Message currentSellOrder, List<Message> buyOrders) {
        List<Message> qualifyingBuyOrders = new ArrayList<>();
        for (Message buyOrder : buyOrders) {
            String stockNameCurrentOrder = currentSellOrder.getBody();
            String stockNameBuyOrder = buyOrder.getBody();
            if (stockNameCurrentOrder.equals(stockNameBuyOrder) && currentSellOrder.getPrice() <= buyOrder.getPrice()
                    && currentSellOrder.getNumber() >= buyOrder.getNumber()) {
                qualifyingBuyOrders.add(buyOrder);
            }
        }
        if (qualifyingBuyOrders.size() != 0) {
            Message highestPriceBuyOrder = findHighestPrice(qualifyingBuyOrders);
            matchOrder(currentSellOrder, highestPriceBuyOrder);
        }
    }

    /**
     * Method to find the highest price from the list of buying orders.
     *
     * @param qualifyingBuyOrders is the list of qualifying buy orders
     * @return returns the highest possible price buy order for a chosen stock.
     */
    public Message findHighestPrice(List<Message> qualifyingBuyOrders) {
        double maximumPrice = 0;
        Message highestPriceBuyOrder = qualifyingBuyOrders.get(0);
        for (Message qualifyingBuyOrder : qualifyingBuyOrders) {
            if (qualifyingBuyOrder.getPrice() >= maximumPrice) {
                maximumPrice = qualifyingBuyOrder.getPrice();
                highestPriceBuyOrder = qualifyingBuyOrder;
            }
        }
        return highestPriceBuyOrder;
    }

    /**
     * Matches the current sell order with the highest price buy order of the same stock and updates the stock price and
     * the traders.
     *
     * @param currentSellOrder     the current order being compared
     * @param highestPriceBuyOrder the highest price buy order
     */
    public void matchOrder(Message currentSellOrder, Message highestPriceBuyOrder) {
        if (currentSellOrder.getNumber() == highestPriceBuyOrder.getNumber()) {
            buyOrders.remove(highestPriceBuyOrder);
            sellOrders.remove(currentSellOrder);
            orderListManager.updateStockPrice(highestPriceBuyOrder.getBody(), highestPriceBuyOrder.getPrice());
            orderListManager.updateTraderBuyOrder(highestPriceBuyOrder);
            orderListManager.updateTraderSellOrder(currentSellOrder);
        }
    }
}
