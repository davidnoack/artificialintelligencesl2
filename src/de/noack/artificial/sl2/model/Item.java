package de.noack.artificial.sl2.model;

public class Item extends DomainElement {

    private String name;
    private float demand;

    public Item(String name) {
        super();
        this.name = name;
        demand = 0;
    }

    public String getName() {
        return name;
    }

    public float getDemand() {
        return demand;
    }

    public void setDemand(float demand) {
        this.demand = demand;
    }
}
