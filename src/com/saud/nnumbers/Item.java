package com.saud.nnumbers;

public class Item {

	String value;
	String number;
	
	public Item() {
		
	}

	public Item(String value, String number) {
		this.value = value;
		this.number = number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	public String getNumber() {
		return number;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
