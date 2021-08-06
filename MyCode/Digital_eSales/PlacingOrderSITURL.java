package Digital_eSales;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class PlacingOrderSITURL {

	
	//REPORT GENERATE
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String RunLog_path =  "C:\\Automated_Execution\\eSales\\Output\\RunLog_Report\\Report_RunLog_BrowseandShop";
	static String Report_Filepath = "C:\\Automated_Execution\\eSales\\Output\\File_Report\\Report_File_BrowseandShop";


	public static void main(String[] args) throws InterruptedException, IOException {
		
		//Launching Internet Explorer 	
			WebDriver driver = null;
	    	String eSalesUrl = "https://10.73.146.196:9002/telcostorefront"; 
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
								
			
			 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\PlacingOrderSITURL.properties");
			 Properties prop = new Properties();
			 prop.load(objfile);
			 
			//LAUNCH eSALES APPLICATION	
			driver.get(prop.getProperty("URL"));
			Thread.sleep(4000);
			driver.manage().window().maximize();
			 			
			ImageAttachmentInDocument capt = new ImageAttachmentInDocument(); 
			Utilities_eSales HE = new Utilities_eSales();
			String path = "C:\\Automated_Execution\\eSales\\Output";
			String fileName = "SS";
							
			//CLICKED ON ESHOP LINK
			WebElement element = driver.findElement(By.xpath(prop.getProperty("eShop")));
			HE.elementHighlight(element, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			capt.CaptureScreenShot();
			driver.findElement(By.xpath(prop.getProperty("eShop"))).click();
			System.out.println("eShop link clicked Successfully");	
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "eShop link cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "eShop link cliked Successfully");
			Thread.sleep(3000);
			
			//CLICKED ON Devices LINK
			WebElement element1 = driver.findElement(By.xpath(prop.getProperty("Devices")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element1, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("Devices"))).click();
			System.out.println("Devices link clicked Successfully");			
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Devices link cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Devices link cliked Successfully");
			Thread.sleep(3000);				
						
			//CLICKED ON ProductName
			System.out.println("Product Name :"+ driver.findElement(By.xpath(prop.getProperty("ProductName"))).getText());
			WebElement element2 = driver.findElement(By.xpath(prop.getProperty("ProductName")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element2, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("ProductName"))).click();
			System.out.println("ProductName clicked Successfully");			
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Product Name cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "ProductName cliked Successfully");
			Thread.sleep(3000);
/*			
			//Clicking on Buy in Product page
			System.out.println("Element Name in Product page :"+ driver.findElement(By.xpath(prop.getProperty("Buy"))).getText());
			WebElement element3 = driver.findElement(By.xpath(prop.getProperty("Buy")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element3, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("Buy"))).click();
			System.out.println("Buy Button clicked Successfully");		
			Thread.sleep(4000);*/
			
			//Clicking on Add to Cart in the Buy pop-up
			System.out.println("Add to Cart in Product Button is :"+ driver.findElement(By.xpath(prop.getProperty("AddtoCart"))).getText());
			WebElement element4 = driver.findElement(By.xpath(prop.getProperty("AddtoCart")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element4, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("AddtoCart"))).click();
			System.out.println("Added the product into cart Successfully");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Added the product into cart Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Added the product into cart Successfully");
			Thread.sleep(3000);	
			
			//Clicking on Add to Cart Window in the Buy pop-up
			System.out.println("Add to Cart in Product Button is :"+ driver.findElement(By.xpath(prop.getProperty("AddtoCartWindow"))).getText());
			WebElement element41 = driver.findElement(By.xpath(prop.getProperty("AddtoCartWindow")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element41, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("AddtoCartWindow"))).submit();
			System.out.println("Added the product into cart Successfully");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Added the product into cart Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Added the product into cart Successfully");
			Thread.sleep(10000);				
			
			//Clicking on Next step the Product Page
			WebElement element5 = driver.findElement(By.xpath(prop.getProperty("NextStepButton")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element5, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("NextStepButton"))).click();
			System.out.println("Proceeded for Login");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Proceeded for Login" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Proceeded for Login");
			Thread.sleep(3000);	
			
			//Entering UserName in Login pop-up
			WebElement element6 = driver.findElement(By.xpath(prop.getProperty("LoginUsername")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element6, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("LoginUsername"))).sendKeys("rannapraveen");
			System.out.println("Username entered successfully");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Username entered successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Username entered successfully");
			Thread.sleep(3000);	
			
			//Entering Password in Login pop-up
			WebElement element7 = driver.findElement(By.xpath(prop.getProperty("LoginPassword")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element7, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("LoginPassword"))).sendKeys("Pravi@12345");
			System.out.println("Password entered successfully");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Password entered successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Password entered successfully");
			Thread.sleep(3000);					
				
			//Clicking on Login Button
			WebElement element8 = driver.findElement(By.xpath(prop.getProperty("LoginButton")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element8, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("LoginButton"))).click();
			System.out.println("Logged Successfully");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Logged Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Logged Successfully");
			Thread.sleep(3000);	
			
			//Clicking on Next step in the Order page
			WebElement element9 = driver.findElement(By.xpath(prop.getProperty("NextStepOrder")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element9, driver);
		//	HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("NextStepOrder"))).click();
			System.out.println("Navigate to Delivery Details Page");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Navigate to Delivery Details Page" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Navigate to Delivery Details Page");
			Thread.sleep(3000);
			
			//Clicking on Next step in the Order page
			WebElement element10 = driver.findElement(By.xpath(prop.getProperty("NextStepOrder1")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element10, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("NextStepOrder1"))).click();
			System.out.println("Navigate to Payment Details Page");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Navigate to Payment Details Page" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Navigate to Payment Details Page");
			Thread.sleep(3000);		
						
			//Clicking on Check Box 
			WebElement element11 = driver.findElement(By.xpath(prop.getProperty("Checkbox")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element11, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("Checkbox"))).click();
			System.out.println("Check Box Clicked Successfully");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Check Box Clicked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Check Box Clicked Successfully");
			Thread.sleep(3000);	
			
			//Clicking on Place Order Button
			WebElement element12 = driver.findElement(By.xpath(prop.getProperty("PalceOrderButton")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element12, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("PalceOrderButton"))).click();
			System.out.println("Place Order Button Clicked Successfully");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Place Order Button Clicked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Check Box Clicked Successfully");
			Thread.sleep(3000);			
			
			//Enter Credit Card Details 
			//Card Number
			driver.findElement(By.xpath(prop.getProperty("CardNumber"))).sendKeys("1234569778942356");
			//Name
			driver.findElement(By.xpath(prop.getProperty("CardName"))).sendKeys("MIT");
			//Exp. Month
			driver.findElement(By.xpath(prop.getProperty("ExpMonth"))).sendKeys("Feb");
			//Exp. Year
			driver.findElement(By.xpath(prop.getProperty("ExpYear"))).sendKeys("2017");
			//CVV
			driver.findElement(By.xpath(prop.getProperty("CCV"))).sendKeys("696");
			System.out.println("Card Details entered Successfully");				
			Thread.sleep(2000);				
					
			//Clicking on Pay Button
			WebElement element13 = driver.findElement(By.xpath(prop.getProperty("PayButton")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element13, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("PayButton"))).click();
			System.out.println("Pay Button Clicked Successfully and Navigate to Order Confirmation Page");		
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Pay Button Clicked Successfully and Navigate to Order Confirmation Page" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Pay Button Clicked Successfully and Navigate to Order Confirmation Page");
			Thread.sleep(3000);					
							
			//Order Details in OrderConfirmation page
			System.out.println("Order Confirmation Message :"+ driver.findElement(By.xpath(prop.getProperty("Thnakyou"))).getText());
			System.out.println("Order Number is :"+ driver.findElement(By.xpath(prop.getProperty("OrderNumber"))).getText());	
			System.out.println("Order Details Message :"+ driver.findElement(By.xpath(prop.getProperty("OrderMesg"))).getText());
			Thread.sleep(4000);
			
			System.out.println("Test Scenario Completed!!");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Test Scenario Completed - Pass" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Test Scenario Completed - Pass");
			 //Destroying the driver object
			  driver.close();
	}

}
