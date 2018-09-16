package de.noack.artificial.sl2.model;

import java.util.HashMap;
import java.util.HashSet;

public class Market extends DomainElement {

	private HashSet <Cashpoint> cashpoints = new HashSet <>();

	private HashMap <Item, Integer> inventory = new HashMap <Item, Integer>();

	public Market() {
		super();
	}

	public void createCashpoint() {
		cashpoints.add(new Cashpoint(this));
	}

	public void buyForInventory(Item item, int amount) {
		if(inventory.containsKey(item)) {
			Integer newAmount = inventory.get(item)+ amount;
			inventory.put(item, newAmount);
		}
	}
}