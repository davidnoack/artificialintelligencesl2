package de.noack.artificial.sl2.logic;

import de.noack.artificial.sl2.gui.Main;
import de.noack.artificial.sl2.model.Item;
import de.noack.artificial.sl2.model.Market;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class LogicHandler {

    Market market;
    Integer stockSize = 0;

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

        for (Map.Entry<Item, Integer> inventoryEntry : market.getStock().getInventory().entrySet()) {
            gridPane.add(new Label(inventoryEntry.getKey().getName()), xPos++, yPos);
            gridPane.add(new Label(String.valueOf(inventoryEntry.getKey().getDemand())), xPos++, yPos);
            gridPane.add(new Label(String.valueOf(inventoryEntry.getValue())), xPos++, yPos);
            gridPane.add(new Label("Recommendation"), xPos++, yPos);

            Button buyItem = new Button("Buy!");
            buyItem.setOnAction(e -> sellItemAndRefreshDisplay(inventoryEntry.getKey().getName()));
            gridPane.add(buyItem, xPos, yPos++);

            xPos = 0;
        }
    }

    public void sellItemAndRefreshDisplay(String itemName) {
        market.getRandomCashpoint().sellItem(itemName);
        Main.initMainWindow();
    }
}