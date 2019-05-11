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
		int option,cashSelection;
		int quantity;
		double priceD;
		String optionFlag = "y";
		String items = null;
		String message;

		System.out.println("=================================================\n");
		System.out.println("		Welcome to FFC'Store \n");
		System.out.println("=================================================\n");

		do {
			System.out.println("List of products: \n");
			readFromFile();
			option = Validator.getInt(scan, "\n what would you like to order?: ", 1, 13);

			quantity = Validator.getInt(scan, "\n How many would you like to order?:", 1, 15);
			priceD = showPrice("src/com/groupproject/products.txt", option, option);
			items = showItem("src/com/groupproject/products.txt", option, option);
			price.add(priceD);
			quant.add(quantity);
			item.add(items);
			message = "\n Would you like to order something else? (y/n): ";
			optionFlag = Validator.getStringMatchingRegex(scan, message, "[nyNY]{1}");

		} while (optionFlag.equals("y") || optionFlag.equals("Y"));
		// here is where you display array list with order
		double sum, total = 0.0,cashT,change;
		System.out.println("\n This your final Order :");
		System.out.println("\n Item             Qty            Total Price");
		System.out.println("=================================================");
			for (int i = 0; i < quant.size(); i++) {
			sum = quant.get(i) * price.get(i);
			System.out.println(String.format("%5s %15s %15s \n", item.get(i),quant.get(i),price.get(i)));
			total = total + sum;
		}
		System.out.println("");
		System.out.println("\n                          Sub Total :$" + total);
		
		System.out.println( "\n    Please Select Your Payment Type (Enter 1.Cash :/2.Credit:/3.Check :):");
		
		cashSelection=scan.nextInt();
		if(cashSelection==1) {
			System.out.println("Enter the tendered Amount:");
			cashT=scan.nextDouble();
			change=cashT-total;
			System.out.println("Change :"+change);
			
		}//credit card ,check and printing receipt is remaining
		else if(cashSelection==2){}
		else if (cashSelection==3){}
		
		
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

}
