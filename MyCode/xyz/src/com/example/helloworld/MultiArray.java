package com.example.helloworld;

public class MultiArray {
	
	public static void main(String [] args)
	
	{
		
		int[][] array2 = {{1,3,4,6},{5,7,9,10}}; //Multi dimension array
		int k = array2.length;
		
		System.out.println("The length of array is " +k);
		
		for(int i=0;i<=1;i++)
		{
			for(int j=0;j<=3;j++)
				
			{
				
				System.out.print(" "+array2[i][j]);
				
			}
			
			System.out.print("\n");
			
		}
		
		
		
	}

}
