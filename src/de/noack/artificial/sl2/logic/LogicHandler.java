package de.noack.artificial.sl2.logic;

import de.noack.artificial.sl2.gui.StockSimulation;
import de.noack.artificial.sl2.model.Item;
import de.noack.artificial.sl2.model.Market;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Map;

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

	public void addItem(String name, Integer stock) {
		stockSize += stock;
		market.getStock().setMaxSize(stockSize);
		market.getStock().buyForInventory(new Item(name), stock);
	}

	public void addStockSizeToDisplay(GridPane gridPane) {
		gridPane.add(new Label("Stock Size: " + market.getStock().getMaxSize()), 0, 0);
	}

	public void displayItemData(GridPane gridPane, int xPos, int yPos) {

		market.createCashpoint();

		for (Map.Entry <Item, Integer> inventoryEntry : market.getStock().getInventory().entrySet()) {
			gridPane.add(new Label(inventoryEntry.getKey().getName()), xPos++, yPos);
			gridPane.add(new Label("|"), xPos++, yPos);
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

	private void buyForInventoryAndRefreshDisplay(Item itemToBuy, int amount) {
		market.getStock().buyForInventory(itemToBuy, amount);
		market.recalculateDemandForAllItems();
		market.refreshRecommendations();
		StockSimulation.initMainWindow();
	}

	private void sellItemAndRefreshDisplay(String itemName) {
		market.getRandomCashpoint().sellItem(itemName);
		market.recalculateDemandForAllItems();
		market.refreshRecommendations();
		StockSimulation.initMainWindow();
	}
}