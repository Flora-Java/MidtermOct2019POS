package com.groupproject;

public class Order extends Product {
	
	// Instance variable, only needs to initialize quantity since the
	//  parent superclass has the other four variables
	private int quantity = 0;
	
	// Product overloaded constructor
	public Order() {
	}

	// Using data for the parent superclass and data specific to the class
	public Order(String name, String catagory, String description, double price, boolean taxable, int quantity) {
		super(name, catagory, description, price, taxable);
		this.quantity = quantity;
	}

	// Getter to get quantity
	public int getQuantity() {
		return quantity;
	}

	// Setter to set quantity
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Method override for toString to output the elements
	@Override
	public String toString() {
		return super.toString() + "\t" + quantity;
	}
	
}
