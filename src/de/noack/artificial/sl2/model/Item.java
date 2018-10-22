package de.noack.artificial.sl2.model;

/**
 * Das Item stellt eine Ware dar, die einen Namen, eine Nachfrage als Fließkommazahl
 * und einer Empfehlung zusammen.
 */
public class Item {

	private String name;
	private double demand;
	private String recommendation;

	public Item(String name) {
		this.name = name;
		recommendation = "";
		demand = 0;
	}

	public String getName() {
		return name;
	}

	public double getDemand() {
		return demand;
	}

	public void setDemand(double demand) {
		this.demand = demand;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
}