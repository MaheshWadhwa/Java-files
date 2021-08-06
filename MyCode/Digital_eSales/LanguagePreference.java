package Digital_eSales;

import java.awt.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.bcel.generic.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class LanguagePreference {

	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	
	public static void main(String[] args) throws InterruptedException, IOException {
		
		//Launching Internet Explorer 	
			WebDriver driver = null;
	    	String eSalesUrl = "http://10.73.146.196:9001/telcostorefront"; 
			String browser = "Firefox";
					

			if (browser=="Firefox") {
				driver=new FirefoxDriver();
				}
			else if (browser=="IE")
			{
				System.setProperty("webdriver.ie.driver","C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
				driver=new InternetExplorerDriver();			
			}
			else if (browser=="Chrome")
			{
				System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
				driver= new ChromeDriver();
			}
			
			
			 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\LanguagePreference.properties");
			 Properties prop = new Properties();
			 prop.load(objfile);
			 
			//LAUNCH eSALES APPLICATION	
			driver.get(prop.getProperty("URL"));
			Thread.sleep(4000);
			driver.manage().window().maximize();
			
			ImageAttachmentInDocument capt = new ImageAttachmentInDocument(); 
			Utilities_eSales HE = new Utilities_eSales();
			String path = "C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\";
			String fileName = "SS";				
			
			//Selecting Langauage 
			WebElement element = driver.findElement(By.xpath(prop.getProperty("LanguageButton")));
			HE.elementHighlight(element, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			capt.CaptureScreenShot();
			driver.findElement(By.xpath(prop.getProperty("LanguageButton"))).click();
			System.out.println("Language link cliked Successfully");			 
			Thread.sleep(4000);	
				
			//Change Language Option
			WebElement element1 = driver.findElement(By.xpath(prop.getProperty("ChangeLanguage")));
			HE.elementHighlight(element1, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			capt.CaptureScreenShot();
			driver.findElement(By.xpath(prop.getProperty("ChangeLanguage"))).click();
			System.out.println("Language preference changed successfully");			 
			Thread.sleep(4000);	
	
			System.out.println("Test Scenario Completed!!");
			
			//Destroying the driver object
			 driver.close();		
			
			
	}
}
