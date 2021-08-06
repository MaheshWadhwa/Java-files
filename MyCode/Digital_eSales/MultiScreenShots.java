package Digital_eSales;

import java.awt.AWTException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.portable.OutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.apache.commons.io.FileUtils;

import multiScreenShot.MultiScreenShot;

public class  MultiScreenShots 
{


    public static void main(String[] args) throws IOException, AWTException, InterruptedException 
    {

      MultiScreenShot mShot=new MultiScreenShot("C:\\Automated_Execution\\eSales\\Output\\","MultiScreenShots");
      
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

        //maximze the window
        driver.manage().window().maximize();

        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
        
    	Utilities_eSales HE = new Utilities_eSales();
    	
      //CLICKED ON ESHOP LINK
		System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).getText());
		WebElement element = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a"));
		HE.elementHighlight(element, driver);
		//HE.takeScreenShotMethod(driver,path,fileName);		
		driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/a")).click();
		System.out.println("eShop Link cliked Successfully");
		Thread.sleep(4000);
		
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
        
		//CLICKED ON Devices LINK
		System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).getText());
		WebElement element1 = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a"));
		HE.elementHighlight(element1, driver);
		driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).click();
		System.out.println("Devices Link cliked Successfully");
		Thread.sleep(4000);  
		
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);

		//Clicking on Product
		System.out.println("Product Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).getText());
		WebElement element2 = driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a"));
		HE.elementHighlight(element2, driver);
		driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).click();
		System.out.println("Product Selected Suucessfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Clicking on Buy in Product page
		System.out.println("Element Name in Product page :"+ driver.findElement(By.xpath("//a[@name='add-to-cart']")).getText());
		WebElement element3 = driver.findElement(By.xpath("//a[@name='add-to-cart']"));
		HE.elementHighlight(element3, driver);
		driver.findElement(By.xpath("//a[@name='add-to-cart']")).click();
		System.out.println("Proceeded Successfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Clicking on Add to Cart in the Buy pop-up
		System.out.println("Element Name in pop-up page :"+ driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a")).getText());
		WebElement element4 = driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a"));
		HE.elementHighlight(element4, driver);
		driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a")).click();
		System.out.println("Added the product into cart Successfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
        
		//Clicking on Next step the Product Page
		WebElement element5 = driver.findElement(By.id("next-step"));
		HE.elementHighlight(element5, driver);
		driver.findElement(By.id("next-step")).click();
		System.out.println("Proceeded for Login");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
        
		//Entering UserName in Login pop-up
		WebElement element6 = driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[1]/input"));
		HE.elementHighlight(element6, driver);
		driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[1]/input")).sendKeys("rannapraveen");
		System.out.println("Username entered successfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Entering Password in Login pop-up
		WebElement element7 = driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[2]/input"));
		HE.elementHighlight(element7, driver);
		driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[2]/input")).sendKeys("Pravi@12345");
		System.out.println("Password entered successfully");
		Thread.sleep(4000);	
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		   
		//Clicking on Login Button
		WebElement element8 = driver.findElement(By.xpath("//a[@name='add-to-cart']"));
		HE.elementHighlight(element8, driver);
		driver.findElement(By.xpath("//a[@name='add-to-cart']")).click();
		System.out.println("Logged Successfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Clicking on Next step in the Order page
		WebElement element9 = driver.findElement(By.xpath("//div/div[4]/div/div[3]/div/span[2]/form/input"));
		HE.elementHighlight(element9, driver);
		driver.findElement(By.xpath("//div/div[4]/div/div[3]/div/span[2]/form/input")).click();
		System.out.println("Navigate to Delivery Details Page");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
/*		//Clicking on Cash on Delivery Button
		driver.findElement(By.xpath("//div/div[4]/div/div/div[1]/div/div/div/div[1]/div/label[2]/div[1]/div")).click();
		System.out.println("Navigete to Payment method Page");
		Thread.sleep(4000);*/
		
		//Clicking on Next step in the Order page
		WebElement element10 = driver.findElement(By.xpath("//div/div[4]/div/div/div[3]/div/span[2]/form/input"));
		HE.elementHighlight(element10, driver);
		driver.findElement(By.xpath("//div/div[4]/div/div/div[3]/div/span[2]/form/input")).click();
		System.out.println("Navigate to Payment Details Page");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Clicking on Check Box 
		WebElement element11 = driver.findElement(By.xpath("//a[@name='add-to-cart']"));
		HE.elementHighlight(element11, driver);
		driver.findElement(By.xpath("//div/div[4]/div[3]/div/label")).click();
		System.out.println("Check Box Clicked Successfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Clicking on Place Order Button 
		WebElement element12 = driver.findElement(By.xpath("//input[@id='placeOrderButton']"));
		HE.elementHighlight(element12, driver);
		driver.findElement(By.xpath("//input[@id='placeOrderButton']")).click();
		System.out.println("Place Order Button Clicked Successfully");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		
		//Enter Credit Card Details 
		//Card Number
		driver.findElement(By.xpath("//input[@id='new-card-number']")).sendKeys("1234569778942356");
		//Name
		driver.findElement(By.xpath("//input[@id='new-card-name']")).sendKeys("MIT");
		//Exp. Month
		driver.findElement(By.xpath("//input[@id='new-card-exp-month']")).sendKeys("Feb");				
		//Exp. Year
		driver.findElement(By.xpath("//input[@id='new-card-exp-year']")).sendKeys("2017");
		//CVV
		driver.findElement(By.xpath("//input[@id='new-card-ccv']")).sendKeys("696");
		System.out.println("Card Details entered Successfully");				
		Thread.sleep(2000);
		
		//Clicking on Pay Button
		WebElement element13 = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/form/div[3]/div[2]/input[2]"));
		HE.elementHighlight(element13, driver);
		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/form/div[3]/div[2]/input[2]")).click();							
		System.out.println("Pay Button Clicked Successfully and Navigate to Order Confirmation Page ");
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
						
		//Order Details in OrderConfirmation page
		System.out.println("Order Confirmation Message :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]")).getText());
		System.out.println("Order Number is :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]/div/a[1]")).getText());	
		System.out.println("Order Details Message :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]/div")).getText());
		Thread.sleep(4000);
        //take full screenshot using MultiScreenShot class
        mShot.multiScreenShot(driver);
		

        //minimize the window using MultiScreenShot class
         mShot.minimize();

        driver.quit();

    }

}