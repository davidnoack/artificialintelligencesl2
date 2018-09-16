package de.noack.artificial.sl2.model;

import java.util.ArrayList;
import java.util.List;

public class Cashpoint extends DomainElement {

	private Market parent;
	private List<Item> scannedItems;

	public Cashpoint(Market parent) {
		super();
		this.parent = parent;
		this.scannedItems = new ArrayList <>();
	}

	public void scanItem(Item item) {
		scannedItems.add(item);
	}
}
