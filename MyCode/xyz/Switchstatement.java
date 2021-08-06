package xyz;

public class Switchstatement {
	
	public static void main(String [] args)
	
	{
		
		char grade = 'C';//Based on this value,the result will come
		switch(grade){
		
		case 'A' : 
			System.out.println("Excellent");
			break;
			
		case 'B' : 
			System.out.println("Wll done");
			break;
			
		case 'C' : 
			System.out.println("better");
			break;
			
			default :
				
				System.out.println("Invalid grade");
			
		}
		
	}

}
