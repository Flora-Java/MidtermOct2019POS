package com.groupproject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TotalCalculation {

	
public static void creditCardCheck(String cardNumber,String expDate,String CVV) {
	
	String [] regExArr= {"\\d{4}-\\d{4}-\\d{4}-\\d{4}",",\\d{2}-\\d{2}","\\d{3}"};
	String [] inputArr= {cardNumber,expDate,CVV};
		
	for(int i=0;i<3;i++) {
	
	String nameValue=Validator(inputArr[i],regExArr[i]);
	System.out.println(inputArr[i]+" is "+nameValue);
	}
	
} 
	public static String Validator(String word,String RegEx) {
	   String msg="";
	   Pattern p=Pattern.compile(RegEx);
	   Matcher m=p.matcher(word);
	   boolean test=m.matches();
	   	   
	   if(test==true) 
		   msg="valid";
	   else 
		   msg="not valid";
	   return msg;
    }
	
	
	public static void checkNumber() {
		
	}
	
	public static void calcTax() {
		
	}
	
	public static void displayReceipt(ArrayList<Order> arrOrder) {
		System.out.println("RECEIPT");
		System.out.printf("%3s%10s%5s%15s", "No.", "Product Name", "Quantity", "Price");
		System.out.println();
		System.out
				.println("=======================================================================");

		for (int i = 0; i <arrOrder.size(); i++) {
			//System.out.println((i+1)+".",arrOrder.getName(),arrOrder.getQuantity,arrOrder.getPrice());
			System.out.println();
		}
		System.out.println("   Sub Total:");
		System.out.println("         Tax:");
		System.out.println(" Grand Total:");
		System.out.println("Payment Type:");
		System.out.println("          Thank You           ");
				
	}
	
public static double taxCalc(double amount,boolean taxable) {
		double tax;
      if(taxable) {
    	  
    	  
      }
		return 0.0;

	}
 
		
	
}





