package xyz;

public class Operatorexample {

	public static void main(String [] args){
		int a= 10,b=5;
		String c ="Selenium",d ="Testing";
		System.out.println("Addition of a , b is"+ (a+b));//+ is for concatenating one line message with variable value
		System.out.println("Subtraction of a , b is"+ (a-b));
		System.out.println("Multiplication of a,b is"+ (a*b));
		System.out.println("Divide of a,b is"+ (a/b));
		System.out.println("Modulus of a,b is"+ (a%b));
		
		b=10;
		a= ++b;
		System.out.println(a);//11,variables take latest values
		a = --b;
		System.out.println(a);
		
		//a++;
		System.out.println((a>b));
		System.out.println((a>=b));
		System.out.println((a==b));
		System.out.println((a<b));
		System.out.println((a!=b));
		System.out.println((a));
		boolean e=true,f = false;
		System.out.println(!(e&&f));//true
		System.out.println((e&&f));//false
		System.out.println((e||f));//true
		
		int g = 1000,h= 500,i= 700;
		
		
		
		
		if((g>h)&&(g>i)){
			
			System.out.println("G is a big number");
		}
			else
			{
				System.out.println("G is not a big number");
			}
			
		
		
		
		
		}
			
		
		
	}

