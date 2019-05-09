package com.groupproject;

public class Product {
	
	// Instance variables;
	private String name;
	private String category;
	private String description;
	private double price;
	private boolean taxable;
	
	//Constructor for Product class
	public Product(String name, String catagory, String description, double price, boolean taxable) {
		this.name = name;
		this.category = catagory;
		this.description = description;
		this.price = price;
		this.taxable = taxable;
	}

	// Getter for name
	public String getName() {
		return name;
	}

	// Setter for name
	public void setName(String name) {
		this.name = name;
	}

	// Getter for catagory
	public String getCatagory() {
		return category;
	}

	// Setter for catagory
	public void setCatagory(String catagory) {
		this.category = catagory;
	}

	// Getter for description
	public String getDescription() {
		return description;
	}

	// Setter for description
	public void setDescription(String description) {
		this.description = description;
	}

	// Getter for price
	public double getPrice() {
		return price;
	}

	// Setter for price
	public void setPrice(double price) {
		this.price = price;
	}

	// Getter for taxable
	public boolean getTaxable() {
		return taxable;
	}

	// Setter for taxable
	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}
	
	// Override method for toString
	@Override
	public String toString() {
		return name + "\t" + category + "\t" + description + "\t" + price + "\t" + taxable;
	}

}
