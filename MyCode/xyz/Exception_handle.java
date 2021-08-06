package xyz;

public class Exception_handle

{
	
	
	public static void main(String [] args)
	{
	try
	
	{
		
		int i,sum = 1;
		
		for(i= -1;i<10;i++)
		{
			
			sum = sum/i;
		
		System.out.print(i);
		
		}
		
		
	
	}
	
catch(ArithmeticException e)
	
	{
		System.out.print("0");
		
	}
	}
}


