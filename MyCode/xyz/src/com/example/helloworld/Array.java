package com.example.helloworld;

public class Array {
	
	public static void main(String [] args)
	
	{
		
		double []  mathOperations= new double[3];
		
		double a=70,b = 30;
		
		mathOperations[0] = a+b;
		
		mathOperations[1] = a-b;
		
		//mathOperations[2] = a*b;
		
		//mathOperations[3] = a/b;  
		
		for(double Operation: mathOperations)
		{
			
			System.out.println(Operation);
		}
		
	}

}
