package xyz;

import java.util.Scanner;

public class Reverse_atruntime {
	
	public static void main(String[] args)
	
	{
		
		String s;
		System.out.println("Please provide the number to be reversed \n ");
		
		Scanner in = new Scanner(System.in);
				
				 s = in.nextLine();
		
				 in.close();
		int l = s.length();
		
		System.out.println("The length of string is "+l);
		
	for(int i = l-1;i>=0;i--)
			
		{    
			
					
			
			System.out.print(s.charAt(i));
		}
		
		
		
		
	}

}
