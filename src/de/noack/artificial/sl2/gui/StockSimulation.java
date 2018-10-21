package de.noack.artificial.sl2.gui;

import de.noack.artificial.sl2.logic.LogicHandler;
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

/**
 * StockSimulation stellt eine Anwendung zur Simulation von Nachfrage dar. Es handelt sich um eine
 * JavaFX Anwendung, welche die Eingabe von bis zu 10 verkaufbaren Gegenständen, den Items, erlaubt
 * und diese dann verkauft und nachgekauft werden können.
 */
public class StockSimulation extends Application {

	// Das zu befüllende Fenster der Anwendung (es gibt immer nur eins)
	static Stage window;

	// Alle anzuzeigenden Item-Erstellungs-Fenster
	static List <Scene> createItemPrompts = new ArrayList <>();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// Referenz auf das Fenster
		window = primaryStage;
		window.setTitle("Stock simulation");

		// Erstellung des ersten Input-Fensters (Eingabe der Anzahl der zu nutzenden Items)
		createItemCountPromptWindow();
		window.setResizable(false);
		window.setFullScreen(false);
		window.show();
	}

	// Hier wird das Fenster erstellt, welches als Eingabe der Menge der zu benutzenden Items dient.
	private void createItemCountPromptWindow() {

		Label label = new Label("Enter item count (max. 10):");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		// Lediglich ganzzahlige Nummern (weniger als 10) sind erlaubt.
		TextField textField = new TextField();
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				textField.setText(newValue.replaceAll("[^\\d]", ""));
			}
			if (Integer.valueOf(textField.getText()) > 10) {
				textField.setText("10");
			}
		});

		// Bestätigung der Anzahl und Erstellung von n Item-Erstellungsfenstern
		Button button = new Button("Ok");
		button.setOnAction(e -> commitCount(Integer.valueOf(textField.getText())));

		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, textField, button);

		window.setScene(new Scene(layout, 300, 100));
	}

	// Für die eingegebene Menge werden Fenster erstellt, die einen Item-Namen und eine initial vorhandene Menge
	// als Input benötigen.
	private void commitCount(int count) {
		for (int i = 0; i < count; i++) {
			TextField nameTextField = new TextField();

			// Für die Menge sind wieder nur ganze Zahlen erlaubt.
			TextField stockTextField = new TextField();
			stockTextField.textProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue.matches("\\d*")) {
					stockTextField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			});

			Button button = new Button("Submit");

			Scene scene = createItemPromptWindow(nameTextField, stockTextField, button);

			// Bei Submit wird ein Item erstellt und das nächste Fenster, falls vorhanden angezeigt.
			button.setOnAction(e -> addItem(nameTextField.getText(), Integer.valueOf(stockTextField.getText()), scene));

			createItemPrompts.add(scene);
		}
		// Am Ende wird der erste Item-Prompt geöffnet.
		window.setScene(createItemPrompts.get(0));
	}

	// Fügt dem Modell ein Item hinzu und wählt das nächste Fenster
	private void addItem(String name, Integer stock, Scene scene) {
		LogicHandler.getInstance().addItem(name, stock);
		nextWindow(scene);
	}

	// Erstellt ein Fenster zur Erstellung eines Items
	private Scene createItemPromptWindow(TextField nameTextField, TextField stockTextField,
	                                     Button button) {

		Label nameLabel = new Label("Enter item name:");
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		Label stockLabel = new Label("Enter initial stock count:");
		stockLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		VBox layout = new VBox(20);
		layout.getChildren().addAll(nameLabel, nameTextField, stockLabel, stockTextField, button);

		Scene scene = new Scene(layout, 300, 200);

		return scene;
	}

	// Gibt das nächste Fenster zur Erstellung von Items zurück oder erstellt das Hauptfenster
	// falls nicht vorhanden
	private void nextWindow(Scene scene) {
		if (createItemPrompts.indexOf(scene) == createItemPrompts.size() - 1) {
			initMainWindow();
		} else window.setScene(createItemPrompts.get(createItemPrompts.indexOf(scene) + 1));
	}

	public static void initMainWindow() {
		Scene mainScene = new Scene(loadMainWindow(), 500, 320);
		window.setScene(mainScene);
	}

	// "Füttert" das Hauptfenster mit den ermittelten Daten
	private static GridPane loadMainWindow() {
		GridPane gridPane = new GridPane();

		LogicHandler.getInstance().addStockSizeToDisplay(gridPane);
		int columnIndex = 0;
		gridPane.add(new Label("Items:"), columnIndex++, 1);
		gridPane.add(new Label("|"), columnIndex++, 1);
		gridPane.add(new Label("Demand:"), columnIndex++, 1);
		gridPane.add(new Label("|"), columnIndex++, 1);
		gridPane.add(new Label("Stock:"), columnIndex++, 1);
		gridPane.add(new Label("|"), columnIndex++, 1);
		gridPane.add(new Label("Recommendation:"), columnIndex, 1);

		LogicHandler.getInstance().displayItemData(gridPane, 0, 2);
		return gridPane;
	}
}