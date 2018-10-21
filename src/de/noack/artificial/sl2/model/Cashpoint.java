package de.noack.artificial.sl2.model;

import java.util.HashSet;

public class Cashpoint {

	private Market parent;
	private HashSet <SoldItems> soldItems;

	public Cashpoint(Market parent) {
		this.parent = parent;
		this.soldItems = new HashSet <>();
	}

	public void sellItem(String itemName) {
		Item itemToSell = parent.getStock().retrieveSellableItem(itemName);
		for (SoldItems alreadySoldItems : soldItems) {
			if (alreadySoldItems.isItemEqualTo(itemToSell)) {
				alreadySoldItems.increaseCount();
				return;
			}
		}
		if (itemToSell != null) {
			soldItems.add(new SoldItems(itemToSell));
		}
	}

	public HashSet <SoldItems> getSoldItems() {
		return soldItems;
	}
}