package xyz;

public class flowcontrol {

	public static void main(String [] args){
		
		int a,b,c,d;
		a=500;b=200;c =3000;
		
		if(a>b)//Single condition
		{
			System.out.println("A is a big number");
		}
	
		if((a>b)||(a>c))// compound or multi condition.
{
	System.out.println("A is a big number than b & c");
}
		
		if(a>c)
		{
			System.out.println("A is a big number");
		
		}
		
		else
		{ 
			System.out.println("B is a big number");
		}
	}
	
	
}
