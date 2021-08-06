package com.example.helloworld;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class FileNet extends Credentials {
	
	 static void   run()
		{
	System.out.println("\n The Filenet is opened for you ");  
	return;
		}
	  void run1()
	 {
		 
	 }
	   
	
	
public static void main(String[] args) throws IOException, InterruptedException  {
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
		
		//Create an array with the data in the same order in which you expect to be filled in excel file
		
		
	
	////Create a object of Timesheet_data_driven class
	
		FileNet objexcelfile = new FileNet();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The filenet is being opened for you ");
    
 
    System.setProperty("webdriver.ie.driver","D://java life/IEDriverServer.exe");
	WebDriver driver = new InternetExplorerDriver();
	
   String System = "filenet";
   String url = "http://volga2:9080/Workplace/Browse.jsp";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
  driver.get(url);
   
   /*WebElement user = driver.findElement(By.id("j_username"));
	WebElement password = driver.findElement(By.id("j_password"));
	WebElement login = driver.findElement(By.id("btnLogin"));
   
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);*/
     
 Thread.sleep(5000);
     
 run();
 
 
 WebElement os = driver.findElement(By.linkText("MobilyOS"));
 os.click();
 WebElement os1 = driver.findElement(By.linkText("MIT SIT Documents"));
 os1.click();
 WebElement os2 = driver.findElement(By.partialLinkText("Consumer Project Artifacts"));
 os2.click();

 
 
 
 
}
	
	
	
	
	
}


