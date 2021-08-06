package com.example.helloworld;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Mantra extends Credentials {

	public static void main(String[] args) throws IOException, InterruptedException  {
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
		
		//Create an array with the data in the same order in which you expect to be filled in excel file
		
		
	
	////Create a object of Timesheet_data_driven class
	
		Mantra objexcelfile = new Mantra();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The Mantra is being opened for you ");
    
 
    FirefoxDriver driver 	= new FirefoxDriver();
   String System = "Mantra";
   String url = "http://10.12.236.50:8080/mantra/Login";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
   driver.get(url);
   
   WebElement user = driver.findElement(By.id("j_username"));
	WebElement password = driver.findElement(By.id("j_password"));
	WebElement login = driver.findElement(By.id("btnLogin"));
   
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);
     
     driver.close();
     
     run();
     
}
	static void run()
	{
	System.out.println("\n The Mantra is opened for you ");
	}
	
}


