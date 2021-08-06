package xyz;

public class overLoading_using_static_methods {
	
	
	static void area(int a , int b)
	
	{
		
		int z = a*b;
		System.out.println("Area of square is = " +z);
		
	}
	
	static int   area(int c)
	
	{
		int l = 22*(c*c)/7;
		System.out.println("Area of circle is = " +l);
		
		return l;
		
	}
	
	public static void main(String [] args)
	
	{
		
		//overLoading data = new overLoading();
		
		int x = 300,y = 100;
		int r = 200;
		
		area(r);
		area(x,y);
		
		
		
	}

}
