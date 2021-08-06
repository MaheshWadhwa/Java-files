package Digital_eSales;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import Utilities.CommonUtilitiesWriter;

public class BrowseandShop {

	//public static void main(String[] args) {
	
	//REPORT GENERATE
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String RunLog_path =  "C:\\Automated_Execution\\eSales\\Output\\RunLog_Report\\Report_RunLog_BrowseandShop";
	static String Report_Filepath = "C:\\Automated_Execution\\eSales\\Output\\File_Report\\Report_File_BrowseandShop";
		
		public static void main(String[] args) throws InterruptedException, IOException {		

		  
		//Launching Internet Explorer 	
			WebDriver driver = null;
	    	String eSalesUrl = "http://10.73.146.196:9001/telcostorefront"; 
			String browser = "IE";
					

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
			
			driver.manage().window().maximize();			

			ImageAttachmentInDocument capt = new ImageAttachmentInDocument(); 
			Utilities_eSales HE = new Utilities_eSales();
			String path = "C:\\Automated_Execution\\eSales\\Output";
			String fileName = "SS";
			 
			 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\BrowseandShop.properties");
			 Properties prop = new Properties();
			 prop.load(objfile);

			// Launching the eSales Application
			 driver.get(prop.getProperty("URL"));
			 Thread.sleep(4000);
			 
			WebElement element = driver.findElement(By.xpath(prop.getProperty("eShop")));
			HE.elementHighlight(element, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			capt.CaptureScreenShot();
			driver.findElement(By.xpath(prop.getProperty("eShop"))).click();
			System.out.println("eShop link cliked Successfully");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "eShop link cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "eShop link clicked Successfully");
			Thread.sleep(4000);
			
			WebElement element1 = driver.findElement(By.xpath(prop.getProperty("Devices")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element1, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("Devices"))).click();
			System.out.println("Devices link cliked Successfully");	
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Devices link cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Devices link clicked Successfully");
			Thread.sleep(4000);
			 
			WebElement element2 = driver.findElement(By.xpath(prop.getProperty("Buy")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element2, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("Buy"))).click();
			System.out.println("Buy Button cliked Successfully");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Buy Button cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Buy Button clicked Successfully");
			Thread.sleep(4000);
			 
			System.out.println("Test Scenario Completed!!");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Test Scenario Completed - Pass" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Test Scenario Completed - Pass");
			
			 
			 //Destroying the driver object
			  driver.close();
			 
				
		}
	}






/*	  //Creating the File Object
File file = new File("C:\\Users\\j.dasari.mit\\workspace\\MITAutomation\\src\\Digital_eSales\\BrowseandShop.properties");
//Creating properties object
	Properties prop = new Properties();
	//Creating InputStream object to read data
	 FileInputStream objInput = null;
	  try {
	  objInput = new FileInputStream(file);
	  //Reading properties key/values in file
	   prop.load(objInput);
	  //Closing the InputStream
	  objInput.close();
	   } catch (FileNotFoundException e) {
	    System.out.println(e.getMessage());   
	  
	    } catch (IOException e) {
	   System.out.println(e.getMessage());				 
	    }*/





/*		
//LAUNCH eSALES APPLICATION	
driver.get(eSalesUrl);	
Thread.sleep(10000);	

Utilities_eSales HE = new Utilities_eSales();
String path = "C:\\Automated_Execution\\eSales\\Output";
String fileName = "ScreenShots";
			
//CLICKED ON ESHOP LINK
System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).getText());
WebElement element = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a"));
HE.elementHighlight(element, driver);
HE.takeScreenShotMethod(driver,path,fileName);				
driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).click();	
System.out.println("eShop Link cliked Successfully");
Thread.sleep(4000);

//CLICKED ON Devices LINK
System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).getText());
WebElement element1 = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a"));
HE.elementHighlight(element1, driver);
HE.takeScreenShotMethod(driver,path,fileName);					
driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).click();
System.out.println("Devices Link cliked Successfully");
Thread.sleep(4000);


//CLICKED ON Buy Button
System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[3]/div[3]/a[2]")).getText());
WebElement element2 = driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[3]/div[3]/a[2]"));
HE.elementHighlight(element2, driver);
HE.takeScreenShotMethod(driver,path,fileName);	
driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[3]/div[3]/a[2]")).click();
System.out.println("Buy Button cliked Successfully");
Thread.sleep(4000);	*/

//Adding wait
 //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);





//Launching the eSales Application
 //driver.get(prop.getProperty("URL"));
			  
			  
			/*WebElement element = driver.findElement(By.id(prop.getProperty("eSales.HomePage.linkeShop")));
			HE.elementHighlight(element, driver);
			HE.takeScreenShotMethod(driver,path,fileName);
			element.click();
			 
			 element = driver.findElement(By.id(prop.getProperty("eSales.HomePage.linkDevice")));
			 element.click();
			
			 element = driver.findElement(By.id(prop.getProperty("eSales.HomePage.btnBuy")));
			  element.click();
			  System.out.println("Test Scenario Completed!!");
			  */
			   //Destroying the driver object
			   //driver.close();


