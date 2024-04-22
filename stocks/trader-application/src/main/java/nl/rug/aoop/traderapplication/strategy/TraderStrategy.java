package nl.rug.aoop.traderapplication.strategy;

import java.io.IOException;

/**
 * Implements Strategy Pattern.
 */
public interface TraderStrategy {

    /**
     * Does the strategy.
     *
     * @throws IOException exception.
     */
    void doStrategy() throws IOException;

}
