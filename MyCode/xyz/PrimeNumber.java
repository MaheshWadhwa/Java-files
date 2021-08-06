package xyz;

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
			
			if(n==1)
			{
				
				System.out.println("1 is not a prime number");
				break;
			}
			
			if(n==2)
			{
				
				//System.out.println("2 is always a prime number");
				break;
			}
			
			if(n%i!= 0)
			{
				i++;
			//	System.out.println("value of i inside first condition is "+i);
			}
			
			else if(n%i==0)
			{
			//	System.out.println("value of i inside 2nd condition is "+i);
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
		
		/*if(i>n)  commented out section
		
		{
			
			System.out.println(n+ "is not a prime number" );
			
			}*/
		
		
}}
