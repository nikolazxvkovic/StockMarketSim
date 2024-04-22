package nl.rug.aoop.stock;

import nl.rug.aoop.stock.stockData.Loader;
import nl.rug.aoop.stock.stockData.StockCollection;
import nl.rug.aoop.stock.stockData.TraderCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LoaderTest {

    @Test
    public void testStuff() {
        StockCollection stockCollection1 = Loader.initializeStockData();
        stockCollection1.getStocks().forEach((key, value ) -> assertNotNull(value.getSymbol()));

        TraderCollection traderCollection = Loader.initializeTraderData();
        traderCollection.getTraders().forEach((key, value) -> assertNotNull(value.getName()));
    }
}
