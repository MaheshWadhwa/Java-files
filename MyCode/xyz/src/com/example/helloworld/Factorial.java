package com.example.helloworld;

import java.util.Scanner;

public class Factorial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
@SuppressWarnings("resource")
Scanner reader = new Scanner(System.in);    // Reading from System.in
		
		System.out.println("Enter a number: \n");
		
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		
		System.out.println("The entered valus is "+n);
		
		for(int i=n;i>1;i--)
			
		{
			n= n*(i-1);
			
			
		}
		
		System.out.println("The Factorial of n is "+ n);

	}

}
