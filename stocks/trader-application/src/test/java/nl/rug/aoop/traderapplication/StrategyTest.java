package nl.rug.aoop.traderapplication;

import nl.rug.aoop.stock.stockData.Loader;
import nl.rug.aoop.stock.stockData.Trader;
import nl.rug.aoop.traderapplication.strategy.RandomStrategy;
import nl.rug.aoop.traderapplication.strategy.TraderStrategy;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StrategyTest {

    @Test
    public void testStuff() throws IOException {
        Map<String, Integer> ownedShares = new HashMap<>();
        ownedShares.put("NVDA", 3);
        ownedShares.put("AMD", 23);
        ownedShares.put("AAPL", 15);
        ownedShares.put("ADBE", 1);
        ownedShares.put("FB", 3);
        Trader trader = new Trader("bot1", "Just Bob", 10450, ownedShares);
        TraderStrategy traderStrategy = new RandomStrategy(trader, Loader.initializeStockData());
        traderStrategy.doStrategy();
    }
}
