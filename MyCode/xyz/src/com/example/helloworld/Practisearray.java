package com.example.helloworld;

public class Practisearray {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int a[]  = {1,2,3,4,5};
		int b[][] = {{1,2,3,4,5},{6,7,8,9,10}};
		
		System.out.println("Length of array a is = " +a.length);
		System.out.println("Length of array b is = " +b.length);
		
		for(int i = 0;i<=4;i++)
		{
		System.out.println(a[i]);

	}
		
		for( int i = 0;i<=1;i++)
		{      System.out.println();
			for(int j= 0;j<=4;j++)
				
			{
				
				System.out.print(" " + b[i][j]);
				
			}
			
		}
			
	}

}
