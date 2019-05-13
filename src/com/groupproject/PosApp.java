/*
 * Names: Kuruvilla "Finny" Bose, Flora Lopez, Chan Park
 * Title: POS Terminal
 * Description: This project will display the list of products from the products.txt to
 * allow the user to pick the products to purchase, calculates the sales tax, if
 * applicable, and outputs the total. Then it will process the payment from the user
 * and display the receipt of the sale.
 */

package com.groupproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class PosApp {

	public static void main(String[] args) {

		// Creates the new Scanner scan to accept user inputs for the program
		Scanner scan = new Scanner(System.in);
		
		// Creates ArrayLists for the product sections in the product.txt
		ArrayList<Double> price = new ArrayList<Double>();
		ArrayList<Integer> quant = new ArrayList<Integer>();
		ArrayList<String> item = new ArrayList<String>();
		ArrayList<Boolean> tax = new ArrayList<Boolean>();
		ArrayList<Order> order = new ArrayList<Order>();

		// Sets the numerical variables used in the application
		int option = 0;
		int cashSelection;
		int quantity;
		double priceD;
		double sum = 0.0;
		double taxes = 0.0;
		double totalTaxes = 0.0;
		double total = 0.0;
		double grandTotal = 0.0;
		double cashT = 0.0;
		double change = 0.0;
		boolean taxable = false;

		// Sets the string variables used in the application
		String optionFlag = "y";
		String items = null;
		String message, cVVCreditCard, expDate;
		String checkNumber = " ";
		String credCardNum1 = " ";
		String creditCardNum = " ";
		
		// Sets the format for prices to the hundredths place
		DecimalFormat df2 = new DecimalFormat("#,##0.00");

		// While loop to allow the user to end the program when 13 is entered
		while (!(option == 13)) {
			
			// Title of the program
			System.out.println("=================================================\n");
			System.out.println("	       Welcome to FFC Store \n");
			System.out.println("=================================================");

			// Do-while loop to output the list of products for the user to make selections from
			do {
				
				// Title of the list of products
				System.out.println("\n	      List of store products\n");
				
				// Method to read the list of products from the products.txt
				readFromFile();
				
				// Asks user to pick an item from the list of products
				option = Validator.getInt(scan, "\nWhat would you like to order?: ", 1, 13);
				
				// If-else statement to allow user to add additional products to the order
				if (option < 13) {

					// Asks user to enter the quantity to purchase
					quantity = Validator.getInt(scan, "\nHow many would you like to order?: ", 1, 15);
					
					// Pulls product information of the product chosen
					priceD = showPrice("src/com/groupproject/products.txt", option, option);
					items = showItem("src/com/groupproject/products.txt", option, option);
					taxable = showTaxable("src/com/groupproject/products.txt", option, option);

					// Adds the item to the ArrayLists
					price.add(priceD);
					quant.add(quantity);
					item.add(items);
					tax.add(taxable);
					order.add(new Order(items, "", "", priceD, taxable, quantity));

					// Asks user if they want to purchase another product
					message = "\nWould you like to order something else? (y/n): ";
					optionFlag = Validator.getStringMatchingRegex(scan, message, "[nyNY]{1}");
					
				} else {
					
					// Ends the if-else statement if user ends the program
					break;
				}

			// Lets the user order another item by pressing Y
			} while (optionFlag.equals("y") || optionFlag.equals("Y"));
			
			// If statement to end the program, prompted by the user
			if (option == 13) {
				System.out.println("\nThank you, have a nice day!");
				break;
			}
			
			// Outputs the subtotal of the user's order
			
			// Title for the subtotal list
			System.out.println("\nThis is your order");
			System.out.println("\nItem             Qty             Price            Tax");
			System.out.println("=========================================================");
			
			// For-loop to calculate the total order for the user
			for (int i = 0; i < quant.size(); i++) {
				
				// If-else statement outputs sales tax
				if(tax.get(i)) {
					
					taxes =  price.get(i) * .06;
					totalTaxes = taxes * quant.get(i) ;
					
					// Else statement if there is no sales tax for the product
					} else {
						
						taxes = 0.0;
				}
				
				// Calculates the subtotals
				sum = price.get(i) + taxes;
				total = sum * quant.get(i);
				System.out.printf("%-18s %-13s $%-15s $%-10s \n", item.get(i), quant.get(i), df2.format(price.get(i)), df2.format(totalTaxes));
				grandTotal = grandTotal + total;
			}
			
			// Outputs the subtotal list
			System.out.println("");
			System.out.println("\nGrand Total (Including Taxes): $" + df2.format(grandTotal));

			// Asking for a payment type
			cashSelection = Validator.getInt(scan,
					"\nPlease select your payment type " + "(Enter  1. Cash  2. Credit  3. Check): ", 1, 3);
			System.out.println("");
			
			// If-else statements to calculate the payment for the order
			// Calculates cash payment
			if (cashSelection == 1) {
				System.out.print("Enter the tendered amount: $");
				cashT = scan.nextDouble();
				change = cashT - grandTotal;
				System.out.println("Your change: $" + df2.format(change));
			
			// Calculates credit card payment
			} else if (cashSelection == 2) {
				creditCardNum = Validator.getStringMatchingRegex(scan,
						"Please provide credit card number: (xxxx-xxxx-xxxx-xxxx) ", "\\d{4}-\\d{4}-\\d{4}-\\d{4}");
				cVVCreditCard = Validator.getStringMatchingRegex(scan, "Please provide expiration date (MM/YY): ",
						"(0[1-9]|1[012])\\/(19|20|21|22|23|24)");
				cVVCreditCard = Validator.getStringMatchingRegex(scan, "Please provide the CVV (3 digit security code on the back of card): ",
						"[0-9]{3}");
				
			// Calculates check payment
			} else if (cashSelection == 3) {
				checkNumber = Validator.getStringMatchingRegex(scan, "Please provide check number: ", "[0-9]{3}");
			}
			
			// Outputs the receipt for the user
			
			// Title for receipt
			System.out.println("\n This is your receipt. Thank you!\n");
			System.out.println("\n Item             Qty            Total Price");
			System.out.println("=================================================");
			
			// For-loop to output the receipt items
			for (int i = 0; i < quant.size(); i++) {
				
				sum = quant.get(i) * price.get(i);
				System.out.printf("%-18s %-13s $%-23s \n", item.get(i), quant.get(i), df2.format(price.get(i)));
				total = total + sum;
			}
			
			// Outputs the totals
			System.out.println("");
			System.out.println("\n                          Sub Total: $" + (df2.format(grandTotal-totalTaxes)));
			System.out.println("\n             Total (taxes included): $" + (df2.format(grandTotal)));

			// If-else statements to output the payment type that was decided earlier by user
			// Cash output
			if (cashSelection == 1) {
				
				System.out.println("\nYou paid with cash, tendered amount: $" + df2.format(cashT));
				System.out.println("Your change: 			     $" + df2.format(change));
			
			// Credit output
			} else if (cashSelection == 2) {
				credCardNum1 = creditCardNum.substring(12);
				System.out.println("\nYou paid with Credit Card number: xxxx-xxxx-xxxx-" + credCardNum1);

			// Check output
			} else {
				System.out.println("\nYou paid with check number: " + checkNumber);

			}
			
			System.out.println("\n\n");
			
		}
		
	}

	// Method to read from the product.txt
	public static void readFromFile() {
		String file = "src/com/groupproject/products.txt";
		Path path = Paths.get(file);

		// Reads from the product.txt
		File f = path.toFile();
		BufferedReader br = null;
		
		// Try statement
		try {
			br = new BufferedReader(new FileReader(f));
			String line = br.readLine();

			// Outputs the products, reading the strings in the products.txt file
			System.out.printf("%10s %22s %9s \n", "Name", "Description", "Price");
			System.out.printf("=================================================\n");
			
			// Declares the variables
			String[] lineword = new String[5];
			int counter = 1;
			String name;
			String desc;
			String price;
			String format;
			
			//While loop to output the product
			while (line != null) {
				
				// This is just printing the line to the console
				lineword = line.split(",");
				name = lineword[0];
				desc = lineword[1];
				price = "$" + lineword[3];
				format = "%-5s %-15s %-15s %-10s\n";
				System.out.printf(format, counter, name, desc, price);
				
				// This is grabbing the next line of data
				line = br.readLine();
				counter++;
			}
			
			System.out.printf("%-5s %-15s\n", counter, "EXIT");

			br.close();

			// Catch statements for errors
		} catch (FileNotFoundException e1) {
			createFile(file);
			System.out.println("The file was created successfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Method to create the file in case the product.txt file for the first time or if it didnt' exist
	public static void createFile(String fileName) {

		Path path = Paths.get(fileName);
		
		// If-else statement to let user know if there are errors
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
				System.out.println("The file was created successfully!");
			} catch (IOException e) {

				System.out.println("OOPS! Something went terribly wrong!");
			}
			
		// Output if the product.txt file already exists
		} else {
			System.out.println("The file already exists");
		}

	}

	// Method to show prices
	public static double showPrice(String fileName, int option1, int option2) {
		
		// Declares the variables
		String line = null;
		double price = 0;
		int currentLineNo = 1;
		BufferedReader in = null;
		
		// Try-catch statement to catch errors
		try {
			in = new BufferedReader(new FileReader(fileName));
			
			while (currentLineNo < option1) {
				if (in.readLine() == null) {
					throw new IOException("File too small");
				}
				currentLineNo++;
			}
			
			while (currentLineNo <= option2) {
				line = in.readLine();
				String[] lineword = new String[5];
				lineword = line.split(",");
				price = Double.parseDouble(lineword[3]);
				currentLineNo++;
			}
			
		} catch (IOException ex) {
			
			System.out.println("Problem reading file.\n" + ex.getMessage());
			
		} finally {
			
			try {
				
				if (in != null)
					in.close();
				
			} catch (IOException ignore) {
				
			}
			
		}
		
		return price;
		
	}

	// Method to show the items
	public static String showItem(String fileName, int option1, int option2) {
		
		// Declares the variables
		String line = null;
		String item = null;
		int currentLineNo = 1;
		BufferedReader in = null;
		
		// Try-catch statement to catch errors
		try {
			in = new BufferedReader(new FileReader(fileName));
			while (currentLineNo < option1) {
				if (in.readLine() == null) {
					throw new IOException("File too small");
				}
				currentLineNo++;
			}
			
			while (currentLineNo <= option2) {
				line = in.readLine();
				String[] lineword = new String[5];
				lineword = line.split(",");
				item = lineword[0];
				currentLineNo++;
			}
			
		} catch (IOException ex) {
			
			System.out.println("Problem reading file.\n" + ex.getMessage());
			
		} finally {
			
			try {
				if (in != null)
					in.close();
				
			} catch (IOException ignore) {
				
			}
			
		}
		
		return item;
		
	}
	
	// Method to show the taxable items
	public static boolean showTaxable(String fileName, int option1, int option2) {
		
		// Declares the variables
		String line = null;
		boolean tax=false;
		int currentLineNo = 1;
		BufferedReader in = null;
		
		// Try-catch statement to catch errors
		try {
			in = new BufferedReader(new FileReader(fileName));
			while (currentLineNo < option1) {
				if (in.readLine() == null) {
					throw new IOException("File too small");
				}
				currentLineNo++;
			}
			while (currentLineNo <= option2) {
				line = in.readLine();
				String[] lineword = new String[5];
				lineword = line.split(",");
				tax =Boolean.parseBoolean(lineword[4]);
				
				currentLineNo++;
			}
			
		} catch (IOException ex) {
			
			System.out.println("Problem reading file.\n" + ex.getMessage());
			
		} finally {
			
			try {
				
				if (in != null)
					in.close();
				
			} catch (IOException ignore) {
				
			}
			
		}
		
		return tax;
		
	}

}
