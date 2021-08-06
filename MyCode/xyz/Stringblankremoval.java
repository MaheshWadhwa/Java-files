package xyz;

public class Stringblankremoval {
	
	public static void main(String [] args)
	
	{
		
		String name = "    i want to say hi to you";
		int i;
		 //char result = name.charAt(9)
		//System.out.println(name.length());
		for(i=0;i<name.length();i++)
			{
			
			char result = name.charAt(i);
			
			if( result == ' ' )
				
			{
				
				
			}
			
			else 
				
			System.out.print(result);
		}
		
		
	}
}
