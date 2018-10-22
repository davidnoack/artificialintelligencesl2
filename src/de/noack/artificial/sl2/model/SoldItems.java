package de.noack.artificial.sl2.model;

/**
 * SoldItems stellt eine Übersicht der insgesamt verkauften Waren einer Sorte dar
 */
public class SoldItems {
	private Item item;
	private int count;

	public SoldItems(Item item) {
		this.item = item;
		count = 1;
	}

	public Item getItem() {
		return item;
	}

	public int getCount() {
		return count;
	}

	public void increaseCount() {
		count++;
	}

	public boolean isItemEqualTo(Item otherItem) {
		return item.getName() != null && otherItem != null && item.getName().equals(otherItem.getName());
	}
}