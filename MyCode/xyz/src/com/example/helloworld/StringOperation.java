package com.example.helloworld;

public class StringOperation {
	
	public static void main(String [] args)
	
	{
		
		String str1 = "Selenium";
		
		String str2= " Testing";
		
		System.out.println(str1+str2);
		
		System.out.println("Selenium"+1+1);//Selenium11 (priority is concatenation)
		
		System.out.println(1+1+"Selenium");//evaluation process from left to right-  2Selenium
		
		System.out.println("1"+1+"Selenium");
		
		System.out.println("1"+1+1+1+"Selenium");
		
				
				}

}
