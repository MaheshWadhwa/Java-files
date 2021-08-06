package com.example.helloworld;

import java.util.Scanner;

public class Replace_string {
	
	public static void main(String [] args)
	
	{
		
		String s;
		
		String domain = "abcd";	

		System.out.println("Please enter your number to be replaced");
		
		Scanner in = new Scanner(System.in);
		
		
		s= in.nextLine();
		
		domain = domain+s;
		
		System.out.println("The value of domain is =" +domain);
		
		in.close();
		
		
		
		
	}
	

}
