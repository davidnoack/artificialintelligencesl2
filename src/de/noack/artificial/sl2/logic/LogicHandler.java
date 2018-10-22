package de.noack.artificial.sl2.logic;

import de.noack.artificial.sl2.gui.StockSimulation;
import de.noack.artificial.sl2.model.Item;
import de.noack.artificial.sl2.model.Market;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Map;

/**
 * Kapselt sämtliche Zugriffe von außen auf das Modell. Ein Markt wird simuliert
 * anhand der über die Oberfläche eingegebenen Daten.
 */
public class LogicHandler {

	private Market market;
	private Integer stockSize = 0;

	private static LogicHandler ourInstance = new LogicHandler();

	public static LogicHandler getInstance() {
		return ourInstance;
	}

	private LogicHandler() {
		market = new Market(0);
	}

	/**
	 * Fügt eine neue Ware hinzu. Die Lagergröße wird um den initialen
	 * Bestand der Ware erhöht.
	 *
	 * @param name
	 * @param stock
	 */
	public void addItem(String name, Integer stock) {
		stockSize += stock;
		market.getStock().setMaxSize(stockSize);
		market.getStock().buyForInventory(new Item(name), stock);
	}

	/**
	 * Fügt die aktuelle maximale Lagergröße der Oberfläche hinzu
	 *
	 * @param gridPane
	 */
	public void addStockSizeToDisplay(GridPane gridPane) {
		gridPane.add(new Label("Stock Size: " +
				market.getStock().getMaxSize()), 0, 0);
	}

	/**
	 * Baut eine tabellarische Übersicht der Itemdaten auf
	 *
	 * @param gridPane
	 * @param xPos
	 * @param yPos
	 */
	public void displayItemData(GridPane gridPane, int xPos, int yPos) {

		market.createCashpoint();

		for (Map.Entry <Item, Integer> inventoryEntry : market.getStock().getInventory().entrySet()) {
			gridPane.add(new Label(inventoryEntry.getKey().getName()), xPos++, yPos);
			gridPane.add(new Label("|"), xPos++, yPos);
			// Bedürfnis wird auf zwei Nachkommastellen gerundet.
			String demand = String.valueOf(inventoryEntry.getKey().getDemand());
			gridPane.add(new Label(demand.substring(0, Math.min(demand.length(), 4))), xPos++, yPos);
			gridPane.add(new Label("|"), xPos++, yPos);
			gridPane.add(new Label(String.valueOf(inventoryEntry.getValue())), xPos++, yPos);
			gridPane.add(new Label("|"), xPos++, yPos);
			gridPane.add(new Label(inventoryEntry.getKey().getRecommendation()), xPos++, yPos);

			Button sellItem = new Button("Sell to Customer!");
			sellItem.setOnAction(e -> sellItemAndRefreshDisplay(inventoryEntry.getKey().getName()));
			gridPane.add(sellItem, xPos++, yPos);

			Button buyItem = new Button("Buy for Inventory!");
			buyItem.setOnAction(e -> buyForInventoryAndRefreshDisplay(inventoryEntry.getKey(), 1));
			gridPane.add(buyItem, xPos, yPos++);

			xPos = 0;
		}
	}

	/**
	 * Ruft die Methode zum Kauf einer Ware auf und aktualisiert die Anzeige
	 *
	 * @param itemToBuy
	 * @param amount
	 */
	private void buyForInventoryAndRefreshDisplay(Item itemToBuy, int amount) {
		market.getStock().buyForInventory(itemToBuy, amount);
		refreshDisplay();
	}

	/**
	 * Ruft die Methode zum Verkauf einer Ware auf und aktualisiert die Anzeige
	 *
	 * @param itemName
	 */
	private void sellItemAndRefreshDisplay(String itemName) {
		market.getRandomCashpoint().sellItem(itemName);
		refreshDisplay();
	}

	/**
	 * Hier wird die Nachfrage neu berechnet, die Empfehlungen angepasst und
	 * das Hauptfenster neu gerendert.
	 */
	private void refreshDisplay() {
		market.recalculateDemandForAllItems();
		market.refreshRecommendations();
		StockSimulation.initMainWindow();
	}
}