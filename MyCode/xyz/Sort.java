package xyz;

public class Sort {
	
	public static void main(String [] args)
	
	{
		
		int [] arr = {2,3,2,7,9,10000};
		
		
		for(int i =0;i<arr.length;i++)
		{
			if((i+1)!=(arr.length))
			{
			  if(arr[i]<arr[i+1])
				
			   {
				
				
				System.out.println(arr[i]);
				
			    }
			
			
			
			}
			
			else System.out.println(arr[i]);
		}
		
		
	}

}
