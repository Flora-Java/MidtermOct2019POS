// The Product parent class catagorizes the list of products in the Products.txt to
//  allow the PosApp application to extract the information and catagorize
//  the sections that make up each product. The sections are: name, category, description,
//  price and taxable.

package com.groupproject;

public class Product {
	
	// Instance variables;
	private String name;
	private String category;
	private String description;
	private double price;
	private boolean taxable;

	// Blank constructor for for Product class, needed for the Order child subclass
	public Product() {
	}
	
	// Constructor for Product class
	public Product(String name, String category, String description, double price, boolean taxable) {
		this.name = name;
		this.category = category;
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

	// Getter for category
	public String getCatagory() {
		return category;
	}

	// Setter for category
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
		return name + "\t" + category + "\t" + description + "\t" + price;
	}

}
