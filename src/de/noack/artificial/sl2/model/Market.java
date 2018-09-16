package de.noack.artificial.sl2.model;

import java.util.HashMap;
import java.util.HashSet;

public class Market {

	private HashSet <Cashpoint> cashpoints = new HashSet <>();

	private HashMap <Item, Integer> inventory = new HashMap <Item, Integer>();

	public Market() {
		// do smth
	}

	public HashSet <Cashpoint> getCashpoints() {
		return cashpoints;
	}
}