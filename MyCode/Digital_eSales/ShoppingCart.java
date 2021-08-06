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

public class ShoppingCart {

	//public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	//REPORT GENERATE
		static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
		static String RunLog_path =  "C:\\Automated_Execution\\eSales\\Output\\RunLog_Report\\Report_RunLog_ShopingCart";
		static String Report_Filepath = "C:\\Automated_Execution\\eSales\\Output\\File_Report\\Report_File_ShopingCart";

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
			
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\ShoppingCart.properties");
			Properties prop = new Properties();
			prop.load(objfile);
			 
			//LAUNCH eSALES APPLICATION	
			driver.get(prop.getProperty("URL"));
			Thread.sleep(4000);
			driver.manage().window().maximize();
			
			
			ImageAttachmentInDocument capt = new ImageAttachmentInDocument(); 
			Utilities_eSales HE = new Utilities_eSales();
			String path = "C:\\Automated_Execution\\eSales\\Output\\ScreenShots";
			String fileName = "SS";
			
			
			//CLICKED ON Cart Button
			WebElement element = driver.findElement(By.xpath(prop.getProperty("CartButton")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			System.out.println("Cart Icon cliked Successfully");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Cart Icon cliked Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Cart Icon cliked Successfully");
			Thread.sleep(3000);
			
			//CLICKED ON Remove Button in Cart pop-up
			WebElement element1 = driver.findElement(By.xpath(prop.getProperty("RemoveButton")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element1, driver);
			HE.takeScreenShotMethod(driver,path,fileName);
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(prop.getProperty("RemoveButton"))).click();
			System.out.println("First Product in Cart Removed Successfully");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "First Product in Cart Removed Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "First Product in Cart Removed Successfully");
			Thread.sleep(3000);
			
			WebElement element2 = driver.findElement(By.xpath(prop.getProperty("RemoveButton1")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element2, driver);
			HE.takeScreenShotMethod(driver,path,fileName);	
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(prop.getProperty("RemoveButton1"))).click();
			System.out.println("Second Product in Cart Removed Successfully");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Second Product in Cart Removed Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Second Product in Cart Removed Successfully");
			Thread.sleep(3000);
			
/*			WebElement element3 = driver.findElement(By.xpath(prop.getProperty("RemoveButton2")));
			capt.CaptureScreenShot();
			HE.elementHighlight(element3, driver);
			HE.takeScreenShotMethod(driver,path,fileName);*/
			
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(prop.getProperty("CartButton"))).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath(prop.getProperty("RemoveButton2"))).click();
			System.out.println("Third Product in Cart Removed Successfully");	
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Third Product in Cart Removed Successfully" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Third Product in Cart Removed Successfully");
			Thread.sleep(3000);
			
			//Updated Message from Cart Pop-up
			System.out.println("Cart Updated Message from Pop-up : " + driver.findElement(By.xpath(prop.getProperty("CartMsg"))).getText());
			Thread.sleep(4000);
			
			System.out.println("Test Scenario Completed!!");
			report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Test Scenario Completed - Pass" +System.lineSeparator()+"--------------------------------------");
			report.writeToFile(Report_Filepath, "Test Scenario Completed - Pass");

			
			//Destroying the driver object
			driver.close();
			
	}

}

			
			
			
			
			
			
			
			
			
			
	
	/*		
			driver.findElement(By.xpath("//header/div/nav/div/div[2]/div[3]/i")).click();
			System.out.println(" Cart Icon cliked Successfully");
			Thread.sleep(4000);
			
			//CLICKED ON Remove Button in Cart pop-up
			driver.findElement(By.xpath("//header/div/nav/div/div[2]/div[3]/div/ul/li[1]/div[2]/i")).click();
			System.out.println("First Product in Cart Removed Successfully");
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//header/div/nav/div/div[2]/div[3]/div/ul/li[1]/div[2]/i")).click();
			System.out.println("Second Product in Cart Removed Successfully");
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//header/div/nav/div/div[2]/div[3]/div/ul/li[1]/div[2]/i")).click();
			System.out.println("Third Product in Cart Removed Successfully");
			Thread.sleep(4000);
			
			//Updated Message from Cart Pop-up
			System.out.println("Cart Updated Message from Pop-up : " + driver.findElement(By.xpath("//header/div/nav/div/div[2]/div[3]/div/ul/li")).getText());
			Thread.sleep(4000);
			
	}

}
*/