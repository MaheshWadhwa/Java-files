package xyz;

public class ArrayExample {
	
	public static void main(String [] args)
	
	{
		
		int a[];
		a = new int[3]; //1st method
		
		double [] b = new double [4]; //2nd method of assigning values
		
		int [] xyz = {70,80,90}; //3rd method of initializing.
		 
		b[0] =7.1;
		
		char [] abc = {'A','B','Z'}; //Array of characters
		
		int [] def = {10,20,30,40}; //Array of Integers
		
		String [] ghi ={ "UFT","Selenium","RFT"}; //Array of Strings
		
		boolean [] jkl = {true,false,false,true}; //Array of boolean or logical values
		
		System.out.println(abc[2]);
		System.out.println(def[0]);
		System.out.println(ghi[1]);
		System.out.println(jkl[3]);
		
		
		
		
		System.out.println(b[0]);
		 
		System.out.println(xyz[1] + "\n" + xyz[2]);
		
		
		
		//a[0]=10;
		a[0] ='A';
		a[1] = 1;//invalid values- syntax error
		a[2] =30;
		//a[3]= 40;//out of range(Run time error)
		
		
		System.out.println(a[0]);
		System.out.println(a[1]);
		System.out.println(a[1]+a[2]);
		
		
	}

}
