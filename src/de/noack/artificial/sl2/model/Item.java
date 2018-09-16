package de.noack.artificial.sl2.model;

public class Item extends DomainElement {

	private String name;

	public Item(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
