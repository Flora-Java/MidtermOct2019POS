// The Validator class is used to validate the user input and to set the
//  parameter of the validated inputted data.

package com.groupproject;

import java.util.Scanner;

	// This is a concrete class that can be used over and over again
	// You can also add your own validation methods here
	public class Validator {
		public static String getString(Scanner sc, String prompt) {
			System.out.print(prompt);
			String s = sc.next(); // read user entry
			sc.nextLine(); // discard any other data entered on the line
			return s;
		}

		// This method tests if the input is a valid string
		public static int getInt(Scanner sc, String prompt) {
			int i = 0;
			boolean isValid = false;
			while (isValid == false) {
				System.out.print(prompt);
				if (sc.hasNextInt()) {
					i = sc.nextInt();
					isValid = true;
				} else {
					System.out.println("Error! Invalid integer value. Try again.");
				}
				sc.nextLine(); // Discard any other data entered on the line
			}
			return i;
		}

		// This method tests if the input is a valid integer, based on the range set by the user
		public static int getInt(Scanner sc, String prompt, int min, int max) {
			int i = 0;
			boolean isValid = false;
			while (isValid == false) {
				i = getInt(sc, prompt);
				if (i < min)
					System.out.println("Error! Number must be " + min + " or greater.");
				else if (i > max)
					System.out.println("Error! Number must be " + max + " or less.");
				else
					isValid = true;
			}
			return i;
		}

		// This method tests if the input is a valid double
		public static double getDouble(Scanner sc, String prompt) {
			double d = 0;
			boolean isValid = false;
			while (isValid == false) {
				System.out.print(prompt);
				if (sc.hasNextDouble()) {
					d = sc.nextDouble();
					isValid = true;
				} else {
					System.out.println("Error! Invalid decimal value. Try again.");
				}
				sc.nextLine(); // discard any other data entered on the line
			}
			return d;
		}

		// This method tests if the input is a valid double, based on the range set by the user
		public static double getDouble(Scanner sc, String prompt, double min, double max) {
			double d = 0;
			boolean isValid = false;
			while (isValid == false) {
				d = getDouble(sc, prompt);
				if (d < min)
					System.out.println("Error! Number must be " + min + " or greater.");
				else if (d > max)
					System.out.println("Error! Number must be " + max + " or less.");
				else
					isValid = true;
			}
			return d;
		}
		// This method tests if the input is a valid string, based on the Regex set by the user
		public static String getStringMatchingRegex(Scanner sc, String prompt, String regex) {
			boolean isValid = false;
			String input;
			
			do {
				input = getString(sc, prompt);
				if (input.matches(regex)) {
					isValid = true;
				} else {
					System.out.println("Input must match the right format: ");
				}
				
			} while (!isValid);
			
			return input;
		}

	}
	
	
	


	
	
	

