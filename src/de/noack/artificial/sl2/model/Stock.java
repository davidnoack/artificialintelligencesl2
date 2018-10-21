package de.noack.artificial.sl2.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Stock stellt einen Bestand in Form eines Lagers dar. Dieser besitzt eine Maximalgröße,
 * sowie ein Inventar. Der Bestand kann selbst Waren nachbestellen.
 */
public class Stock {

	// Größe des Lagers
	private int maxSize;

	// Inventar der Waren mit Anzahl der lagernden
	private HashMap <Item, Integer> inventory = new HashMap <>();

	public Stock(int maxSize) {
		this.maxSize = maxSize;
	}

	public HashMap <Item, Integer> getInventory() {
		return inventory;
	}

	// Wenn genug Platz im Inventar ist, wird der Bestand um den "amount" erweitert
	public void buyForInventory(Item item, int amount) {
		if (isEnoughSpaceFor(amount)) {
			Integer newAmount = amount;
			if (inventory.containsKey(item)) {
				newAmount += inventory.get(item);
			}
			inventory.put(item, newAmount);
		}
	}

	// Prüft, ob die Summe der einzel lagernden Waren inkl. der zu kaufenden Menge
	// die Größe des Lagers nicht übersteigen würden
	private boolean isEnoughSpaceFor(int amount) {
		int sumUsedAmount = 0;
		for (Integer usedAmount : inventory.values()) sumUsedAmount += usedAmount;
		return (amount <= (maxSize - sumUsedAmount));
	}

	public Item retrieveSellableItem(String itemName) {
		for (Map.Entry <Item, Integer> inventoryEntry : inventory.entrySet()) {
			if (inventoryEntry.getKey().getName().equals(itemName) && inventoryEntry.getValue().intValue() > 0) {
				inventory.put(inventoryEntry.getKey(), inventoryEntry.getValue() - 1);
				return inventoryEntry.getKey();
			}
		}
		return null;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
