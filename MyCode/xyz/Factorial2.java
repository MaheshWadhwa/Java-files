package xyz;

import java.util.Scanner;

public class Factorial2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
@SuppressWarnings("resource")
Scanner reader = new Scanner(System.in);    // Reading from System.in
		
		System.out.println("Enter a number: \n");
		
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		
		System.out.println("The entered valus is "+n);
		
		int i =n;
		
		do{
			
			
			n= n*(i-1);
		
			i--;
		}
		
		while(i>1);
		
		System.out.println("The Factorial of n is "+ n);

	}

}
