package com.inquiremusic.app.model;

/**
 * ComboItem provides a consistent data model for the front-end for containing a Key Value pair. Similar to HashMap, but is not an array of items.
 * @author Thomas P. Kovalchuk
 *
 */
public class ComboItem {
	private int key;
	private String value;
	
	/**
	 * ComboItem creates an empty ComboItem object for variables to be set later
	 */
	public ComboItem() {
		
	}
	
	/**
	 * ComboItem creates an object of the given key, value pair
	 * @param key
	 * @param value
	 */
	public ComboItem(int key, String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}

	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
