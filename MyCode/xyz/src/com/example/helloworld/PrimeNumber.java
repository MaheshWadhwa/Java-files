package com.example.helloworld;

import java.util.Scanner;

public class PrimeNumber {
	
	@SuppressWarnings("resource")
	public static void main(String [] args)
	
	{
		Scanner reader = new Scanner(System.in);    // Reading from System.in
		
		System.out.println("Enter a number: \n");
		
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		
		System.out.println("The entered valus is "+n);
		
		int i =2;
		
		do
		
		{
			if(n%i!= 0)
			{
				i++;
				
			}
			
			else if(n%i==0)
			{
				System.out.println(n + " is not a prime number");
			    break;
			    
			   
			}
		
			
			
		} 
		
		while(i<n);
		
		if(i<n)
		{
		
		System.out.println("\n It is not a Prime number as It can be divided by " +i);
		
		}
		
		if(i==n)
			
		{
	
			System.out.println(n+ " is a prime number");
		}
		
}}
