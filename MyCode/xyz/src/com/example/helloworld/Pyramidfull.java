package com.example.helloworld;

import java.util.Scanner;

public class Pyramidfull {
	
	public static void main(String[] args) {
		
		
Scanner reader = new Scanner(System.in);    // Reading from System.in
		
		System.out.println("Enter a number: \n");
		
		int n = reader.nextInt(); // Scans the next token of the input as an int.
		
		System.out.println("\nBelow is the Pyramid for the number provided which is  " +n + "\n");
		
		for(int i=1;i<=n;i++)
			
		{
			
			{
				int k=n-i;
				do{
					k--;
					System.out.print(" ");
					
				}while(k>=0);
			
			      
			for(int j= i;j>=1;j--)
				
			
				
				System.out.print("* ");
			}
			
			System.out.println();
			
		}
		
		
	}

}
