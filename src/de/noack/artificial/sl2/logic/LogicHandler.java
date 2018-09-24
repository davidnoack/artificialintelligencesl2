package de.noack.artificial.sl2.logic;

import de.noack.artificial.sl2.model.Item;
import de.noack.artificial.sl2.model.Market;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class LogicHandler {

    Market market;
    Integer stockSize;

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
        for (Map.Entry<Item, Integer> inventoryEntry : market.getStock().getInventory().entrySet()) {
            gridPane.add(new Label(inventoryEntry.getKey().getName()), xPos++, yPos);
            gridPane.add(new Label(String.valueOf(inventoryEntry.getKey().getDemand())), xPos++, yPos);
            gridPane.add(new Label(String.valueOf(inventoryEntry.getValue())), xPos++, yPos);
            gridPane.add(new Label("Recommendation"), xPos++, yPos);
            yPos++;
            xPos = 0;
        }
    }
}