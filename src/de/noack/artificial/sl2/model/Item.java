package de.noack.artificial.sl2.model;

public class Item {

	private static int ID_COUNTER = 0;

	private int id;
	private String name;

	public Item(String name) {
		this.id = ID_COUNTER;
		ID_COUNTER++;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
