package de.noack.artificial.sl2.logic;

import de.noack.artificial.sl2.model.Market;

public class Controller {

    public void simulate(int maxSize) {
        Market market = new Market(maxSize);
        market.createCashpoint();
    }
}
