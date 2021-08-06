package com.example.helloworld;

public class Reverse_String {
	
	public static void main(String[] args)
	
	{
				String s = "hi my name is mahesh wadhwa";
		
		int l = s.length();
		
		System.out.println("The length of string is "+l);
		
		for(int i = l-1;i>=0;i--)
			
		{    
			
					
			
			System.out.print(s.charAt(i));
		}
		
		
	}

}
