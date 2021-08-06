package Digital_eSales;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Payment_Mode_Options_For_GuestUser {

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
			
			//LAUNCH eSALES APPLICATION	
			driver.get(eSalesUrl);	
			Thread.sleep(10000);			
						
			//CLICKED ON ESHOP LINK
			System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).getText());
			driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).click();
			System.out.println(" eShop Link cliked Successfully");
			Thread.sleep(4000);
			
			//CLICKED ON Devices LINK
			System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).getText());
			driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).click();
			System.out.println(" Devices Link cliked Successfully");
			Thread.sleep(4000);
			
			//CLICKED ON Added Button
//			driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/button[2]")).click();
//			System.out.println(" Added Button cliked Successfully");
//			Thread.sleep(4000);
			
			
			//CLICKED ON Buy Button
			System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[3]/div[3]/a[2]")).getText());
			driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[3]/div[3]/a[2]")).click();
			System.out.println(" Buy Button cliked Successfully");
			Thread.sleep(4000);
			
	/*		//CLICKED ON Add to cart Button
			driver.findElement(By.xpath("//div[@id='popover346978']/div[2]/div/div[3]/div/a")).click();
			System.out.println(" Add to cart Button cliked Successfully");
			Thread.sleep(4000);*/
			
			
			
	}

}
