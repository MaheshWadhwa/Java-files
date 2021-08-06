package xyz;

public class ArrayCopy {
	
	public static void main(String [] args) 
	
	{  
		int [] array1 = {10,20,30};
		
		int array2 [] = array1;
		
		System.out.println(array2[0] + "\n" + array2[2]);
		
		for(int i=0;i<array2.length;i++ )
			
		{
			System.out.println(array2[i]);
			
		}
		
	}

}
