package browser_testing;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TDM extends Credentials  {

public static void main(String[] args) throws IOException, InterruptedException  {
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
		
		//Create an array with the data in the same order in which you expect to be filled in excel file
		
		
	
	////Create a object of Timesheet_data_driven class
	
		TDM objexcelfile = new TDM();
	
		
		
	String filePath = "D://java life";
	
	//Write on  the excel sheet

    System.out.println("\n The TDM tool is being opened for you ");
    
 
    FirefoxDriver driver 	= new FirefoxDriver();
   String System = "TDM";
   String url = "http://10.12.234.139:8084/TestDataManagement/loginform.jsp";
		
	//	System.out.println("\n The Output is written to the Output excel file");
	
	//Call read file method of the class to read data
   driver.get(url);
   

   
   WebElement user = driver.findElement(By.name("username"));
	WebElement password = driver.findElement(By.name("password"));
	WebElement login = driver.findElement(By.xpath(".//*[@id='search']"));
	
	
	
	
	
	
	
     objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office",System,user,password,login);
     
     driver.manage().window().maximize();
     
     run();
     String url1 = driver.getCurrentUrl();
     objexcelfile.run1(url1);
}

static void run()
{
System.out.println("\n The TDM tool is opened for you now ");

}
void run1(String url)
{
	 
	 System.out.println(url); 
	
}


}