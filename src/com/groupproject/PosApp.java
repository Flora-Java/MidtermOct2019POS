package com.groupproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class PosApp {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		ArrayList<Double> price = new ArrayList<Double>();
		ArrayList<Integer> quant = new ArrayList<Integer>();
		ArrayList<String> item = new ArrayList<String>();
		ArrayList<Product> catalog = new ArrayList<Product>();

		int option = 0;
		int cashSelection;
		int quantity;
		double priceD;
		double sum = 0.0;
		double total = 0.0;
		double cashT = 0.0;
		double change = 0.0;
		String optionFlag = "y";
		String items = null;
		String message, cVVCreditCard, expDate;
		String checkNumber = " ";
		String credCardNum1 = " ";
		String creditCardNum = " ";

		catalog = createArrayWithFileContent();
		while (!(option == 13)) {
			clearScreen();
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
				price.add(priceD);
				quant.add(quantity);
				item.add(items);
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
			// here is where you display array list with order
			clearScreen();
			System.out.println("\n This your Order :");
			System.out.println("\n Item             Qty            Total Price");
			System.out.println("=================================================");
			for (int i = 0; i < quant.size(); i++) {
				sum = quant.get(i) * price.get(i);
				System.out.printf("%-18s %-13s $%-15s \n", item.get(i), quant.get(i), price.get(i));
				total = total + sum;
			}
			System.out.println(" ");
			System.out.println("\n                          Sub Total :$" + total);

			// asking for a payment type
			cashSelection = Validator.getInt(scan,
					"\n    Please Select Your Payment Type " + "(Enter 1.Cash : 2.Credit: 3.Check :):", 1, 3);
			System.out.println(" ");
			if (cashSelection == 1) {
				System.out.println("Enter the tendered Amount:");
				cashT = scan.nextDouble();
				change = cashT - total;
				System.out.println("Change :" + change);
			} 
			else if (cashSelection == 2) {
				creditCardNum = Validator.getStringMatchingRegex(scan,
						"Please provide Credit card Number : (16 digits) ", "^[4]{1}[0-9]{15}");
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
				System.out.printf("%-18s %-13s $%-15s \n", item.get(i), quant.get(i), price.get(i));
				total = total + sum;
			}
			System.out.println("");
			System.out.println("\n                          Sub Total :$" + total);
			// here you need to include the total with taxes, I couldnt find it.
			System.out.println("\n               Total (taxes included) : $");

			if (cashSelection == 1) {
				System.out.println("\nYou paid with cash, tendered amount: " + cashT);
				System.out.println("your change: 					     " + change);
			} else if (cashSelection == 2) {
				credCardNum1 = creditCardNum.substring(12);
				System.out.println("\nYou paid with Credit Card number: xxxx-xxxx-xxxx-" + credCardNum1);

			} else {
				System.out.println("\nYou paid with check number : " + checkNumber);

			}
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
				// System.out.println(line);
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
				// System.out.println(line);
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

	public final static void clearScreen() {
		try {
			final String os = System.getProperty("os.name");

			if (os.contains("Windows")) {
				Runtime.getRuntime().exec("cls");
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (final Exception e) {
			// Handle any exceptions.
		}
	}

	public static ArrayList<Product> createArrayWithFileContent() {
		// move the content of file products.txt to an ArrayList to access price

		ArrayList<Product> catalog = new ArrayList<Product>();
		String file = "src/com/groupproject/products.txt";
		Path path = Paths.get(file);
		File f = path.toFile();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = br.readLine();

			String[] lineword = new String[5];
			String name, desc;
			double price;
			while (line != null) {
				// this is just printing the line to the console
				lineword = line.split(",");
				name = lineword[0];
				desc = lineword[1];
				price = Double.parseDouble(lineword[3]);
				// this is grabbing the next line of data
				line = br.readLine();
				catalog.add(new Product(name, desc, " ", price, false));
			}
			br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catalog;

	}

}
