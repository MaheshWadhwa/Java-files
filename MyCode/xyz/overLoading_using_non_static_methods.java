package xyz;

public class overLoading_using_non_static_methods {
	
	
	void area(int a , int b)
	
	{
		
		int z = a*b;
		System.out.println("Area of square is = " +z);
		
	}
	
   int  area(int c)
	
	{
		int l = 22*(c*c)/7;
		System.out.println("Area of circle is = " +l);
		
		return l;
		
	}
	
	public static void main(String [] args)
	
	{
		
		overLoading_using_non_static_methods data = new overLoading_using_non_static_methods();
		
		int x = 50,y = 100;
		int r = 7;
		
		data.area(r);
		data.area(x,y);
		
		
		
	}

}
