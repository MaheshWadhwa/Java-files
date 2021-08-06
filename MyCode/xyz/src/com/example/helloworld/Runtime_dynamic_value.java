package com.example.helloworld;

import java.util.Scanner;

public class Runtime_dynamic_value {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a;
		int b;
		float c;
		String s;
		
		Scanner in = new Scanner(System.in);
		 
	      System.out.println("Enter a string");
	      s = in.nextLine();
	      System.out.println("You entered string "+s);
	 
	      System.out.println("Enter a float");
	      c = in.nextFloat();
	      System.out.println("You entered float "+c);   
	      
	      System.out.println("Enter an integer a");
	      a = in.nextInt();
	      System.out.println("You entered integer a =  "+a);
	      
	      System.out.println("Enter an integer b");
	      b = in.nextInt();
	      System.out.println("You entered integer b =  "+b);
	 
	     a = a+b;
	     b = a-b;
	     a = a-b;
	      
	        System.out.println("After swapping a ="  +a);
			System.out.println("After swapping b ="  +b);
	      
	      
		
		in.close();
		
		

	}

}
