package com.example.helloworld;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

public class Allsec extends Credentials  {

public static void main(String[] args) throws IOException, InterruptedException  {
		
	System.setProperty("webdriver.chrome.driver","D://java life/chromedriver.exe");
	ChromeDriver driver = new ChromeDriver();
	
		Allsec objexcelfile = new Allsec();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The Allsec application is being opened for you ");
    
 
    
   String System = "Allsec";
   String url = "https://www.allsechro.com/smartpaylogin/Common/Web_Signon_smartpay.aspx";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
   driver.get(url);
    WebElement code = driver.findElement(By.name("txt_comp_code"));
    code.sendKeys("MIT");
    WebElement click = driver.findElement(By.id("btn_comp"));
    click.click();
   WebElement user = driver.findElement(By.id("txt_uid"));
	WebElement password = driver.findElement(By.name("txt_pwd"));
	WebElement login = driver.findElement(By.name("btn_submit"));
	
	
	
	
	
	
	
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);
     
     driver.manage().window().maximize();
     
     Thread.sleep(3000);
     
     String url1 = driver.getCurrentUrl();
     
     
     if(url1!= url)
     
     run();
     else 
    	 
    	 run1();
    	 
     
}

static void run()
{
System.out.println("\n The Allsec application is opened for you now ");

}

static void run1()
{
	System.out.println("Either Username or Password is wrong");	
}

}