package com.example.helloworld;

public class StringComparison {
	
	public static void main(String [] args)
	
	{
		String str1 = "selenium";
		String str2 = "SELENIUM";
		
		String str3 = "SELENIUM";
		
		String str4 = "zselenium";
		
		//String Comparison using == operator
		
		
		System.out.println(str1 == str2);//false
		
		System.out.println(str3 == str2);//true
		
		//String comparison using equals() method
		
		System.out.println(str1.equals(str2));//false
		
		System.out.println(str2.equals(str3));//true
		
		//String comparison using compareTo() method
		
		System.out.println(str1.compareTo(str2));  //comparing the ascii values ,Greater than zero.
		
		
		System.out.println(str2.compareTo(str3));
		
		System.out.println(str1.compareTo(str4));//less than zero
		
		
		
		
	}

}
