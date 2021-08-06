package com.example.helloworld;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

public class Hrms extends Credentials  {

public static void main(String[] args) throws IOException, InterruptedException  {
		
	System.setProperty("webdriver.chrome.driver","D://java life/chromedriver.exe");
	ChromeDriver driver = new ChromeDriver();
	
		Hrms objexcelfile = new Hrms();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The HRMS application is being opened for you ");
    
 
    
   String System = "HRMS";
   String url = "http://prodapps01.prod.mobily.lan:8001/OA_HTML/RF.jsp?function_id=28716&resp_id=-1&resp_appl_id=-1&security_group_id=0&lang_code=US&params=35MIKu-zA-tTnpE-7iReGQ&oas=Z0C5vIBGVaqg6UqeE6GtSQ";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
   driver.get(url);
   
   driver.manage().window().maximize();
   
   WebElement user = driver.findElement(By.id("usernameField"));
	WebElement password = driver.findElement(By.id("passwordField"));
	WebElement login = driver.findElement(By.id("SubmitButton"));
	
	
	
	
	
	
	
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);
     
     driver.manage().window().maximize();
     
     run();
     
}

static void run()
{
System.out.println("\n The HRMS application is opened for you now ");

}
}