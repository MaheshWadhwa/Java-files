package Digital_eSales;

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

public class ProductReview {

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
				
				
				 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\ProductReview.properties");
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
				HE.takeScreenShotMethod(driver,path,fileName);	
				capt.CaptureScreenShot();
				driver.findElement(By.xpath(prop.getProperty("eShop"))).click();
				System.out.println("eShop link cliked Successfully");			 
				Thread.sleep(3000);
				
				//CLICKED ON Devices LINK
				WebElement element1 = driver.findElement(By.xpath(prop.getProperty("Devices")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element1, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("Devices"))).click();
				System.out.println("Devices link cliked Successfully");			
				Thread.sleep(3000);				
				
				//CLICKED ON ProductName
				System.out.println("Product Name :"+ driver.findElement(By.xpath(prop.getProperty("ProductName"))).getText());
				WebElement element2 = driver.findElement(By.xpath(prop.getProperty("ProductName")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element2, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("ProductName"))).click();
				System.out.println("ProductName cliked Successfully");			
				Thread.sleep(3000);
				
				//Clicking on SubmitReview 
				WebElement element3 = driver.findElement(By.xpath(prop.getProperty("SubmitReview")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element3, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("SubmitReview"))).click();
				System.out.println("Ready to Enter your Review");			
				Thread.sleep(3000);	
				
				//Click on Rating1
				WebElement element4 = driver.findElement(By.xpath(prop.getProperty("Rating1")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element4, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("Rating1"))).click();
				System.out.println("Clicked on First Star");			
				Thread.sleep(2000);
				
				//Click on Rating2
				WebElement element5 = driver.findElement(By.xpath(prop.getProperty("Rating2")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element5, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("Rating2"))).click();
				System.out.println("Clicked on Second Star");			
				Thread.sleep(2000);				
				
				//Entering Review Comment
				driver.findElement(By.xpath(prop.getProperty("Comments"))).sendKeys("MIT");
				System.out.println("Review Comment Entered");			
				Thread.sleep(2000);	
						
				//Clicking on POST Button
				WebElement element7 = driver.findElement(By.xpath(prop.getProperty("PostButton")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element7, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("PostButton"))).click();
				System.out.println("Review Comment Entered");			
				Thread.sleep(3000);	
				
				System.out.println("Test Scenario Completed!!");
				
				//Destroying the driver object
				driver.close();
}
				
}			
			
				
				
				
				
				
				
				
				
				
				
				
	/*			
				
				
				
				
				
				driver.manage().window().maximize();			
				
				//LAUNCH eSALES APPLICATION	
				driver.get(eSalesUrl);	
				Thread.sleep(10000);
				
							
				//CLICKED ON ESHOP LINK
				System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).getText());
				driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).click();
				System.out.println("eShop Link cliked Successfully");
				Thread.sleep(4000);
				
				//CLICKED ON Devices LINK
				System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).getText());
				driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).click();
				System.out.println("Devices Link cliked Successfully");
				Thread.sleep(4000);
				
				//Clicking on Product
				System.out.println("Product Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).getText());
				driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).click();
				System.out.println("Product Selected Suucessfully");
				Thread.sleep(4000);
				
				//Clicking on SubmitReview 
				driver.findElement(By.xpath("//div[@id='device-details-reviews']/div/section/div[1]/a[2]")).click();
				System.out.println("Ready to Enter your Review");
				Thread.sleep(4000);
				
				//Rating
				driver.findElement(By.xpath("//div[@id='device-details-reviews']/div/section/div[2]/form/div[1]/div[2]/span[1]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[@id='device-details-reviews']/div/section/div[2]/form/div[1]/div[2]/span[2]")).click();
				Thread.sleep(2000);
				
				//Entering Review
				driver.findElement(By.xpath("//div[@id='device-details-reviews']/div/section/div[2]/form/div[2]/textarea")).sendKeys("");
				System.out.println("Review Comment Entered");
				Thread.sleep(2000);
						
				//Clicking on POST 
				driver.findElement(By.xpath("//div[@id='device-details-reviews']/div/section/div[2]/form/div[3]/input[2]")).click();
				System.out.println("Review and Rating Posted Succesfully");
				Thread.sleep(4000);					
				
	}

}
*/