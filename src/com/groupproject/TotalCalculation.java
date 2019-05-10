package com.groupproject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TotalCalculation {

	public static double grandTotal(double amount) {
		// taxable--do it in the main app

		return 0.0;

	}

public static void creditCardCheck(String cardNumber,String expDate,String CVV) {
	
	String [] regExArr= {"\\d{4}-\\d{4}-\\d{4}-\\d{4}",",\\d{2}-\\d{2}","\\d{3}"};
	String [] inputArr= {cardNumber,expDate,CVV};
		
	for(int i=0;i<4;i++) {
	
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
	public static double cashCheck(double grandTotal,double amountTendered) {
		
		double change=amountTendered-grandTotal;
		
		return change;
	}
	public static void checkNumber() {
		
	}

	
	
	
}





