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

		Scanner scan = new Scanner(System.in);
		ArrayList<Double> price = new ArrayList<Double>();
		ArrayList<Integer> quant = new ArrayList<Integer>();
		ArrayList<String> item = new ArrayList<String>();
		ArrayList<Boolean> tax = new ArrayList<Boolean>();
		ArrayList<Order> order=new ArrayList<Order>();

		int option = 0;
		int cashSelection;
		int quantity;
		double priceD;
		double sum = 0.0;
		double taxes=0.0;
		double totalTaxes=0.0;
		double total = 0.0;
		double grandTotal=0.0;
		double cashT = 0.0;
		double change = 0.0;
		boolean taxable=false;
		
		String optionFlag = "y";
		String items = null;
		String message, cVVCreditCard, expDate;
		String checkNumber = " ";
		String credCardNum1 = " ";
		String creditCardNum = " ";
		
		DecimalFormat df2 = new DecimalFormat("#,##0.00");

		while (!(option == 13)) {
			System.out.println("=================================================\n");
			System.out.println("		Welcome to FFC'Store \n");
			System.out.println("=================================================\n");

			do {
				System.out.println("List of products: \n");
				readFromFile();
				option = Validator.getInt(scan, "\n what would you like to order?: ", 1, 13);
				if (option < 13) {
				quantity = Validator.getInt(scan, "\n How many would you like to order?:", 1, 15);
				priceD = showPrice("src/com/groupproject/products.txt", option, option);
				items = showItem("src/com/groupproject/products.txt", option, option);
				taxable = showTaxable("src/com/groupproject/products.txt", option, option);
				price.add(priceD);
				quant.add(quantity);
				item.add(items);
				tax.add(taxable);
				order.add(new Order(items,"","",priceD,taxable,quantity));
				
				message = "\n Would you like to order something else? (y/n): ";
				optionFlag = Validator.getStringMatchingRegex(scan, message, "[nyNY]{1}");
				} else {
					break;
				}

			} while (optionFlag.equals("y") || optionFlag.equals("Y"));
			if (option == 13) {
				System.out.println("Good Bye!");
				break;
			}
			System.out.println("\n This your Order :");
			System.out.println("\n Item             Qty             Price           Tax");
			System.out.println("=========================================================");
			for (int i = 0; i < quant.size(); i++) {
				if(tax.get(i)) {
					taxes =  price.get(i)*.06;
					totalTaxes=taxes*quant.get(i) ;
					}
				else {
					taxes=0.0;
				}
				
				sum=price.get(i)+taxes;
				
				total= sum*quant.get(i) ;
				System.out.printf("%-18s %-13s $%-15s $%-10s \n", item.get(i), quant.get(i), df2.format(price.get(i)), df2.format(totalTaxes));
				grandTotal = grandTotal+total;
			}
			System.out.println(" ");
			System.out.println("\n                        Grand Total(With Taxes) :$" + df2.format(grandTotal));

			// asking for a payment type
			cashSelection = Validator.getInt(scan,
					"\n    Please Select Your Payment Type " + "(Enter 1.Cash  2.Credit 3.Check ):", 1, 3);
			System.out.println(" ");
			if (cashSelection == 1) {
				System.out.println("Enter the tendered Amount:");
				cashT = scan.nextDouble();
				change = cashT - grandTotal;
				System.out.println("Your change :" + df2.format(change));
			} 
			else if (cashSelection == 2) {
				creditCardNum = Validator.getStringMatchingRegex(scan,
						"Please provide Credit card Number: (xxxx-xxxx-xxxx-xxxx) ", "\\d{4}-\\d{4}-\\d{4}-\\d{4}");
				cVVCreditCard = Validator.getStringMatchingRegex(scan, "Please provide Expiration date: (mm/yy) ",
						"(0[1-9]|1[012])\\/(19|20|21|22|23|24)");

				cVVCreditCard = Validator.getStringMatchingRegex(scan, "Please provide CVV (3 digit on the back :",
						"[0-9]{3}");
			} else if (cashSelection == 3) {
				checkNumber = Validator.getStringMatchingRegex(scan, "Please provide check number :", "[0-9]{3}");
			}
			
			System.out.println("\n This is your receipt. Thank you !\n");
			System.out.println("\n Item             Qty            Total Price");
			System.out.println("=================================================");
			for (int i = 0; i < quant.size(); i++) {
				sum = quant.get(i) * price.get(i);
				System.out.printf("%-18s %-13s $%-15s \n", item.get(i), quant.get(i), df2.format(price.get(i)));
				total = total + sum;
			}
			System.out.println("");
			System.out.println("\n                       Sub Total : $" + (df2.format(grandTotal-totalTaxes)));
			System.out.println("\n          Total (taxes included) : $"+df2.format(grandTotal));

			if (cashSelection == 1) {
				System.out.println("\nYou paid with cash, tendered amount: $" + df2.format(cashT));
				System.out.println("your change: 			     $" + df2.format(change));
			} else if (cashSelection == 2) {
				credCardNum1 = creditCardNum.substring(12);
				System.out.println("\nYou paid with Credit Card number: xxxx-xxxx-xxxx-" + credCardNum1);

			} else {
				System.out.println("\nYou paid with check number : " + checkNumber);

			}
			System.out.println("\n\n");
		}
	}

	public static void readFromFile() {
		String file = "src/com/groupproject/products.txt";
		Path path = Paths.get(file);

		File f = path.toFile();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = br.readLine();

			System.out.printf("%12s %20s %10s \n", "Name", "Description", "Price");
			System.out.printf("=================================================\n");
			String[] lineword = new String[5];
			int counter = 1;
			String name;
			String desc;
			String price;
			String format;
			while (line != null) {
				// this is just printing the line to the console
				lineword = line.split(",");
				name = lineword[0];
				desc = lineword[1];
				price = "$" + lineword[3];
				format = "%-5s %-15s %-15s %-10s\n";
				System.out.printf(format, counter, name, desc, price);
				// this is grabbing the next line of data
				line = br.readLine();
				counter++;
			}
			System.out.printf("%-5s %-15s\n", counter, "EXIT");

			br.close();

		} catch (FileNotFoundException e1) {
			createFile(file);
			System.out.println("The file was created successfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void createFile(String fileName) {

		Path path = Paths.get(fileName);
		if (Files.notExists(path)) {
			try {
				Files.createFile(path);
				System.out.println("The file was created successfully!");
			} catch (IOException e) {

				System.out.println("OOPS! Something went terribly wrong!");
			}
		} else {
			System.out.println("The file already exists");
		}

	}

	public static double showPrice(String fileName, int option1, int option2) {
		String line = null;
		double price = 0;
		int currentLineNo = 1;
		BufferedReader in = null;
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

	public static String showItem(String fileName, int option1, int option2) {
		String line = null;
		String item = null;
		int currentLineNo = 1;
		BufferedReader in = null;
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
	
	public static boolean showTaxable(String fileName, int option1, int option2) {
		String line = null;
		boolean tax=false;
		int currentLineNo = 1;
		BufferedReader in = null;
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
