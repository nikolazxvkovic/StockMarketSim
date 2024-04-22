package nl.rug.aoop.traderapplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Main class.
 */
@Slf4j
public class Main {

    /**
     * The main.
     *
     * @param args argument.
     */
    public static void main(String[] args) {
        TraderApplication traderApplication = new TraderApplication();
        new Thread(traderApplication).start();
    }
}
