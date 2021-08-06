package xyz;

public class Sort_real {
	
	public static void main(String [] args)
	
	{
		
		int [] arr = {2,3,2,7,9,6,9,8,1,0};
		
		
		for(int i =0;i<arr.length;i++)
		{
			if((i+1)!=(arr.length))
			{
			  if(arr[i]<arr[i+1])
				
			   {
				int x = arr[i];
				
				int b = 0;
				
				do {
					
					
					if(x<=arr[b])
					{
						
					}
					
					
				}while(b<arr.length);
				
				System.out.println(x);
				
			    }
			
			
			
			}
			
			else System.out.println(arr[i]);
		}
		
		
	}

}
