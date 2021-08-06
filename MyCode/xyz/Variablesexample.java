package xyz;

public class Variablesexample {

	static int a = 100; //static variable- Class level variable has to before main method.can be accessed anywhere
	public int salary() {
		int mysalary = 10000 + 2000+1500; //local variable ,accessible within method 
		mysalary= mysalary+a;
		return mysalary;
	}
	
	public static void main(String [] args){
		int b = 200; //instance variable- only in conditional & loop blocks
		System.out.println(a);
		System.out.println(b);
		//System.out.println(mysalary); will not work as my salary is local variable
		
		Variablesexample obj = new Variablesexample();
				System.out.println(obj.salary());
				//i is localvariable
				for (int i = 1;i<=10;i++){
					System.out.println(i);
					System.out.println(a);
					System.out.println(b);
				}
	}
}
