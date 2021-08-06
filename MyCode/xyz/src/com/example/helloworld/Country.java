package com.example.helloworld;

public class Country {

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		
		
		String country = "India Bhutan India Pakistan China Bangladesh Srilanka Nepal";
		
		int l = country.length();
		
		
		for(int i=l-1;i>=0;i--) //l = 20 initially,l =  12
			
		{
			if(country.charAt(i)== ' ') // i = 14 initially,i =  5
			{
				
				for(int j=i+1;j<l;j++) //j = 15 initially ,j = 6
					
				{
					
					System.out.print(country.charAt(j));
					
				}
				
				System.out.print(' ');
				
				
			i=l-i;  // now i is 5  ,7
			l =l-i;  // now l is 14, 5
			i=l-1;   //now i is 13, 4
			
			
			
			//System.out.print(i);
			//System.out.print(l);
			
			}
				
		
				System.out.print(i); //very important to know the program execution .We can make it comment it as well
		
			
			
			
			
			
		}
		
		
			for(int x= 0;x<l;x++)
				
			{
				if(country.charAt(x)!=' ')
					
				{
					
					System.out.print(country.charAt(x));
					
				}
				
				
			}
				
		

	}

}
