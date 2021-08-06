//My first program

package com.example.helloworld;

import java.io.Console;
import java.lang.*;


public class Sample1 {
     //Method declaration or create a method
	 public int multiply(int a,int b,int c){
	 int result = a*b*c;
	 return result;
	 }
	 
	public static void main(String [] args) {
	//This is a sampleprogram
		
	int a = 10,b,c; //Variables declaration
	b=20;c=30; //Initialization
	
	final int money =100;//Constant declaration
	
	System.out.println("Addition of a,b is "+(a+b));//30
	System.out.println(money);//100
	System.out.println(c);//30
	
	if(a>b ){
	System.out.println("A is a big number")	;}
	else{
		System.out.println("B is a big number");
	}
	for(int d=1;d<=10;d++){
		System.out.println(d);
	}
	//Create object and access Methods
	Sample1 obj = new Sample1();
	int x=obj.multiply(10,25,50);
	System.out.println(x);
	}
}
