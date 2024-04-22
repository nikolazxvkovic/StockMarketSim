module stockapplication {
    requires networking;
    requires messagequeue;
    requires command;
    requires lombok;
    requires util;
    exports nl.rug.aoop.stock.stockData;
    requires org.slf4j;
    requires stock.market.ui;
    requires awaitility;
    opens nl.rug.aoop.stock.stockData to com.fasterxml.jackson.databind;
}