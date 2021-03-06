package de.noack.artificial.sl2.model;

import java.util.*;

/**
 * Ein Markt enthält ein Lager und mehrere Kassen. Für den Markt kann die Nachfrage aller Waren berechnet,
 * sowie die Empfehlung für jede Ware erstellt werden.
 */
public class Market {

	private Stock stock;
	private List <Cashpoint> cashpoints = new ArrayList <>();

	public Market(int maxStockSize) {
		stock = new Stock(maxStockSize);
	}

	public void createCashpoint() {
		cashpoints.add(new Cashpoint(this));
	}

	/**
	 * Iteriert über alle Warenverkäufe aller Kassen und berechnet anhand dessen relativ zur
	 * Anzahl aller verkauften Waren die Nachfrage.
	 */
	public void recalculateDemandForAllItems() {
		double amountOfAllSoldItems = 0;
		Map <Item, Double> amountsPerItem = new HashMap <>();
		for (Cashpoint singleCashpoint : cashpoints) {
			Set <SoldItems> allSoldItemsOfThisCashpoint = singleCashpoint.getSoldItems();
			for (SoldItems soldItems : allSoldItemsOfThisCashpoint) {
				amountOfAllSoldItems = amountOfAllSoldItems + soldItems.getCount();
				double oldAmount = 0;
				if (amountsPerItem.containsKey(soldItems.getItem())) oldAmount = amountsPerItem.get(soldItems.getItem());
				amountsPerItem.put(soldItems.getItem(), oldAmount + soldItems.getCount());
			}
		}
		for (Map.Entry <Item, Double> itemWithSellingCount : amountsPerItem.entrySet())
			itemWithSellingCount.getKey().setDemand(itemWithSellingCount.getValue() / amountOfAllSoldItems);
	}

	/**
	 * Erstellt eine Empfehlung auf Basis der Nachfrage
	 */
	public void refreshRecommendations() {
		for (Map.Entry <Item, Integer> itemsInStock : stock.getInventory().entrySet()) {
			Item item = itemsInStock.getKey();
			double count = itemsInStock.getValue().doubleValue();
			String buy = "Buy for Inventory!";
			if (stock.isEnoughSpaceFor(1) && (item.getDemand() > 0.5D ||
					(item.getDemand() > (1 / stock.getInventory().size()) && count < 0.1 * stock.getMaxSize())))
				item.setRecommendation(buy);
			else if (count == 0) item.setRecommendation(buy);
			else item.setRecommendation("");
		}
	}

	/**
	 * Gibt eine zufällige der verfügbaren Kassen zurück
	 *
	 * @return Kasse
	 */
	public Cashpoint getRandomCashpoint() {
		return cashpoints.get((new Random()).nextInt(cashpoints.size()));
	}

	public Stock getStock() {
		return stock;
	}
}