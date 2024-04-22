package nl.rug.aoop.stock.stockData;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * This class contains the attributes and behaviour of a Trader.
 */
@Getter
@Setter
@NoArgsConstructor
public class Trader {
    private String id, name;
    private Integer funds;
    private Map<String, Integer> ownedShares;

    /**
     * A constructor.
     *
     * @param id          id of the trader.
     * @param name        name of the trader.
     * @param funds       funds left for the trader.
     * @param ownedShares what stocks they own and how many shares.
     */
    public Trader(String id, String name, Integer funds, Map<String, Integer> ownedShares) {
        this.id = id;
        this.name = name;
        this.funds = funds;
        this.ownedShares = ownedShares;
    }
}
