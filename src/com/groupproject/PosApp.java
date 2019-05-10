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

		System.out.println("Welcome to FFC'store \n");
		System.out.println("These are our products \n");
		readFromFile();
		System.out.println(("\n what would you like to order?: "));
		

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

}
