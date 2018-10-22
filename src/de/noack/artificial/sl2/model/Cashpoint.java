package de.noack.artificial.sl2.model;

import java.util.HashSet;

/**
 * Der Cashpoint stellt eine Kasse dar. Sie ist eindeutig einem "Elternmarkt"
 * zugeordnet und besitzt eine Menge an Warenverkäufen.
 */
public class Cashpoint {

	private Market parent;
	private HashSet <SoldItems> soldItems;

	public Cashpoint(Market parent) {
		this.parent = parent;
		this.soldItems = new HashSet <>();
	}

	/**
	 * Ermittelt anhand eines Warennamens eine verkaufbare Ware.
	 * Ist die Ware auf Lager, wird sie in die Warenverkäufe eingetragen,
	 * ansonsten passiert nichts.
	 *
	 * @param itemName
	 */
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