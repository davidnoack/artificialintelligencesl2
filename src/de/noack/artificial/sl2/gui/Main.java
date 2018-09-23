package de.noack.artificial.sl2.gui;

import de.noack.artificial.sl2.model.Item;
import de.noack.artificial.sl2.model.Market;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

	Stage window;
	Scene noOfItemsPrompt;
	List <Scene> createItemPrompts = new ArrayList <>();

	Market market = new Market(50);
	List <Item> itemList = new ArrayList <>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		window = primaryStage;
		window.setTitle("Stock simulation");

		noOfItemsPrompt = createItemCountPromptWindow();

		window.setScene(noOfItemsPrompt);
		window.setResizable(false);
		window.setFullScreen(false);
		window.show();
	}

	private Scene createItemCountPromptWindow() {

		Label label = new Label("Enter item count:");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		TextField textField = new TextField();
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				textField.setText(newValue.replaceAll("[^\\d]", ""));
			}
			if (Integer.valueOf(textField.getText()) > 26) {
				textField.setText("26");
			}
		});

		Button button = new Button("Ok");
		button.setOnAction(e -> commitCount(Integer.valueOf(textField.getText())));

		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, textField, button);

		return new Scene(layout, 300, 100);
	}

	private void commitCount(int count) {
		for (int i = 0; i < count; i++) {
			TextField nameTextField = new TextField();

			TextField stockTextField = new TextField();
			stockTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("\\d*")) {
					stockTextField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			});

			Button button = new Button("Submit");
			button.setOnAction(e -> itemList.add(new Item(nameTextField.getText())));

			Scene scene = createItemPromptWindow(nameTextField, stockTextField, button);
			createItemPrompts.add(scene);
		}
		window.setScene(createItemPrompts.get(0));
	}

	private Scene createItemPromptWindow(TextField nameTextField, TextField stockTextField,
	                                     Button button) {

		Label nameLabel = new Label("Enter item name:");
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		Label stockLabel = new Label("Enter initial stock count:");
		stockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		VBox layout = new VBox(20);
		layout.getChildren().addAll(nameLabel, nameTextField, stockLabel, stockTextField, button);

		Scene scene = new Scene(layout, 300, 200);
		button.setOnAction(e -> nextWindow(scene));

		return scene;
	}

	private void nextWindow(Scene scene) {
		if (createItemPrompts.indexOf(scene) == createItemPrompts.size() - 1) {
			initMainWindow();
		} else window.setScene(createItemPrompts.get(createItemPrompts.indexOf(scene) + 1));
	}

	private void initMainWindow() {
		initDomainModel();
		Scene mainScene = new Scene(loadMainWindow(), 400, 400);
		window.setScene(mainScene);
	}

	private GridPane loadMainWindow() {
		GridPane gridPane = new GridPane();

		int xPos = 0;
		int yPos = 1;

		gridPane.add(new Label("Stock Size : 50"),0,0);
		gridPane.add(new Label("Items:"), 0, 1);
		gridPane.add(new Label("Demand:"), 1, 1);
		gridPane.add(new Label("Stock:"), 2, 1);
		gridPane.add(new Label("Recommendation:"), 3, 1);

		for (Item item : itemList) {
			yPos++;
			gridPane.add(new Label(item.getName()), xPos++, yPos);
			gridPane.add(new Label(String.valueOf(item.getDemand())), xPos++, yPos);
			gridPane.add(new Label(String.valueOf(market.getStock().getInventory().get(item))), xPos++, yPos);
			gridPane.add(new Label("Recommendation"), xPos++, yPos);
			xPos = 0;
		}
		return gridPane;
	}

	private void initDomainModel() {
		for(Item item : itemList) {
			market.getStock().getInventory().put(item, Integer.valueOf(50 / itemList.size()));
		}
	}
}