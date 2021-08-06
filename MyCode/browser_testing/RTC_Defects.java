package browser_testing;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RTC_Defects extends Credentials  {

public static void main(String[] args) throws IOException, InterruptedException  {
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
		
		//Create an array with the data in the same order in which you expect to be filled in excel file
		
		
	
	////Create a object of Timesheet_data_driven class
	
		RTC_Defects objexcelfile = new RTC_Defects();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The RTC defect management tool is being opened for you ");
    
 
    FirefoxDriver driver 	= new FirefoxDriver();
   String System = "RTC";
   String url = "https://eecsaruh6how077.prod.mobily.lan:9443/ccm/web/projects/";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
   driver.get(url);
   
   WebElement user = driver.findElement(By.id("jazz_app_internal_LoginWidget_0_userId"));
	WebElement password = driver.findElement(By.id("jazz_app_internal_LoginWidget_0_password"));
	WebElement login = driver.findElement(By.xpath(".//*[@id='jazz_app_internal_LoginWidget_0']/div[1]/div[1]/div[3]/form/button"));
   
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);
     
     run();
     
}

static void run()
{
System.out.println("\n The RTC defect management tool is opened for you now ");

}
}