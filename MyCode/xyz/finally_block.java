package xyz;

public class finally_block {

	public static void main(String [] args)
	
	{
		
		try
		
		{
			
			int a = 10,b= 0;
			
			int c = a/b;
			
			System.out.println(c);
			
		}
			
		
		
		catch(NullPointerException e)
		{
			
			System.out.println(e);			
		}
		
		catch(ArithmeticException e)
		{
			
			System.out.println(e);			
		}
		
		
		finally{
			
			System.out.println("Exception is handled in finally block");
			
		}
		
		
	}
	
}
