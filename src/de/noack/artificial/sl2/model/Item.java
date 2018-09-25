package de.noack.artificial.sl2.model;

public class Item extends DomainElement {

    private String name;
    private double demand;
    private String recommendation;

    public Item(String name) {
        super();
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