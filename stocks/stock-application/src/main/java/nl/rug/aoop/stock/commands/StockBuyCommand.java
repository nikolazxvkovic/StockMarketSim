package nl.rug.aoop.stock.commands;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.messageClasses.message.Message;
import nl.rug.aoop.stock.order.OrderListManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class contains the attributes and behaviour of a StockBuyCommand.
 */
@Slf4j
public class StockBuyCommand implements Command {
    private final List<Message> buyOrders, sellOrders;
    private final OrderListManager orderListManager;

    /**
     * The constructor used to initialize a StockBuy command.
     *
     * @param orderListManager is used to store the sell/buy order lists
     */
    public StockBuyCommand(OrderListManager orderListManager) {
        this.orderListManager = orderListManager;
        this.buyOrders = orderListManager.getBuyOrders();
        this.sellOrders = orderListManager.getSellOrders();
    }

    /**
     * Method that is overridden from the Command interface and is used to add the buyOrder to the buyOrders list and
     * then start the matching procedure.
     *
     * @param options is a map containing messages whose headers are taken as commands
     */
    @Override
    public void execute(Map<String, Object> options) {
        Message currentBuyOrder = (Message) options.get("order");
        buyOrders.add(currentBuyOrder);
        matchOrderDetails(currentBuyOrder, sellOrders);
    }

    /**
     * Matches the current buy order with the corresponding sell orders.
     *
     * @param currentBuyOrder the current buy order
     * @param sellOrders      List of sell orders that satisfy the conditions
     */
    public void matchOrderDetails(Message currentBuyOrder, List<Message> sellOrders) {
        List<Message> qualifyingSellOrders = new ArrayList<>();
        for (Message sellOrder : sellOrders) {
            String stockNameCurrentOrder = currentBuyOrder.getBody();
            String stockNameSellOrder = sellOrder.getBody();
            if (stockNameCurrentOrder.equals(stockNameSellOrder) && currentBuyOrder.getPrice() >= sellOrder.getPrice()
                    && currentBuyOrder.getNumber() <= sellOrder.getNumber()) {
                qualifyingSellOrders.add(sellOrder);
            }
        }
        if (qualifyingSellOrders.size() != 0) {
            Message lowestPriceSellOrder = findLowestPrice(qualifyingSellOrders);
            matchOrder(currentBuyOrder, lowestPriceSellOrder);
        }
    }

    /**
     * Method to find the lowest price from the list of selling orders.
     *
     * @param qualifyingSellOrders is the list of qualifying sell orders
     * @return returns the lowest possible price sell order for a stock
     */
    public Message findLowestPrice(List<Message> qualifyingSellOrders) {
        double minimumPrice = Double.MAX_VALUE;
        Message lowestPriceSellOrder = qualifyingSellOrders.get(0);
        for (Message qualifyingSellOrder : qualifyingSellOrders) {
            if (qualifyingSellOrder.getPrice() <= minimumPrice) {
                minimumPrice = qualifyingSellOrder.getPrice();
                lowestPriceSellOrder = qualifyingSellOrder;
            }
        }
        return lowestPriceSellOrder;
    }

    /**
     * Matches the current buy order with the lowest price sell order of the same stock and updates the stock price and
     * the traders.
     *
     * @param currentBuyOrder      the current order being compared
     * @param lowestPriceSellOrder the lowest price sell order
     */
    public void matchOrder(Message currentBuyOrder, Message lowestPriceSellOrder) {
        if (currentBuyOrder.getNumber() == lowestPriceSellOrder.getNumber()) {
            buyOrders.remove(currentBuyOrder);
            sellOrders.remove(lowestPriceSellOrder);
            orderListManager.updateStockPrice(lowestPriceSellOrder.getBody(), lowestPriceSellOrder.getPrice());
            orderListManager.updateTraderBuyOrder(currentBuyOrder);
            orderListManager.updateTraderSellOrder(lowestPriceSellOrder);
        }
    }
}
