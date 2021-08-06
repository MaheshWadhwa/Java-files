package com.example.helloworld;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BMS extends Credentials  {

public static void main(String[] args) throws IOException, InterruptedException  {
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
		
		//Create an array with the data in the same order in which you expect to be filled in excel file
		
		
	
	////Create a object of Timesheet_data_driven class
	
		BMS objexcelfile = new BMS();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The BMS application is being opened for you ");
    
 
    FirefoxDriver driver 	= new FirefoxDriver();
   String System = "BMS";
   String url = "http://10.14.11.71:9080/bms/login123.jsp";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
   driver.get(url);
   

   
   WebElement user = driver.findElement(By.name("loginOperator"));
	WebElement password = driver.findElement(By.name("loginPassword"));
	WebElement login = driver.findElement(By.xpath("html/body/form/table/tbody/tr/td/table/tbody/tr[4]/td[4]/input"));
	
	
	
	
	
	
	
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);
     
     run();
     
}

static void run()
{
System.out.println("\n The BMS application is opened for you now ");

}
}