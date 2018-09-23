package de.noack.artificial.sl2.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Market extends DomainElement {

	private Stock stock;

	private HashSet <Cashpoint> cashpoints = new HashSet <>();

	public Market(int maxStockSize) {
		super();
		createStock(maxStockSize);
	}

	public void createCashpoint() {
		cashpoints.add(new Cashpoint(this));
	}

	public void createStock(int maxSize) {
		stock = new Stock(maxSize);
	}

	public Stock getStock() {
		return stock;
	}

	public void recalculateDemandForAllItems() {
		int amountOfAllSoldItems = 0;
		HashMap <Item, Integer> amountsPerItem = new HashMap <>();
		for (Cashpoint singleCashpoint : cashpoints) {
			HashSet <SoldItems> allSoldItemsOfThisCashpoint = singleCashpoint.getSoldItems();
			for (SoldItems soldItems : allSoldItemsOfThisCashpoint) {
				amountOfAllSoldItems += soldItems.getCount();
				amountsPerItem.put(soldItems.getItem(), amountsPerItem.get(soldItems.getItem()) + soldItems.getCount());
			}
		}
		for (Map.Entry <Item, Integer> itemWithSellingCount : amountsPerItem.entrySet()) {
			itemWithSellingCount.getKey().setDemand(itemWithSellingCount.getValue().intValue() / amountOfAllSoldItems);
		}
	}
}