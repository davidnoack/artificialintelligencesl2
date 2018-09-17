package de.noack.artificial.sl2.model;

import java.util.HashSet;

public class Cashpoint extends DomainElement {

    private Market parent;
    private HashSet<SoldItems> soldItems;

    public Cashpoint(Market parent) {
        super();
        this.parent = parent;
        this.soldItems = new HashSet<>();
    }

    public void sellItem(String itemName) {
        Item itemToSell = parent.getStock().retrieveSellableItem(itemName);
        for (SoldItems aldreadySoldItems : soldItems) {
            if (aldreadySoldItems.isItemEqualTo(itemToSell)) {
                aldreadySoldItems.increaseCount();
                return;
            }
        }
        soldItems.add(new SoldItems(itemToSell));
    }

    public HashSet<SoldItems> getSoldItems() {
        return soldItems;
    }

    public Market getParent() {
        return parent;
    }
}
