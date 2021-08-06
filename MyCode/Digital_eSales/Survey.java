package Digital_eSales;

import java.awt.List;
import java.io.IOException;

import org.apache.bcel.generic.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Survey {

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
			
			//Clicking on Product
			System.out.println("Product Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).getText());
			driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).click();
			System.out.println("Product Selected Suucessfully");
			Thread.sleep(4000);
			
			//Clicking on Buy in Product page
			System.out.println("Element Name in Product page :"+ driver.findElement(By.xpath("//a[@name='add-to-cart']")).getText());
			driver.findElement(By.xpath("//a[@name='add-to-cart']")).click();
			System.out.println("Proceeded Successfully");
			Thread.sleep(4000);
			
			//Clicking on Add to Cart in the Buy pop-up
			System.out.println("Element Name in pop-up page :"+ driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a")).getText());
			driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a")).click();
			System.out.println("Added the product into cart Successfully");
			Thread.sleep(4000);
			
			//Clicking on Next step the Product Page
			driver.findElement(By.id("next-step")).click();
			System.out.println("Proceeded for Login");
			Thread.sleep(4000);
			
			//Entering UserName in Login pop-up
			driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[1]/input")).sendKeys("rannapraveen");
			System.out.println("Username entered successfully");
			Thread.sleep(4000);
			
			//Entering Password in Login pop-up
			driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[2]/input")).sendKeys("Pravi@12345");
			System.out.println("Password entered successfully");
			Thread.sleep(4000);
			
			//Clicking on Login Button
			driver.findElement(By.xpath("//div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[4]/input")).click();
			System.out.println("Logged Successfully");
			Thread.sleep(4000);
			
			//Clicking on Next step in the Order page
			driver.findElement(By.xpath("//div/div[4]/div/div[3]/div/span[2]/form/input")).click();
			System.out.println("Navigate to Delivery Details Page");
			Thread.sleep(4000);
			
/*				//Clicking on Cash on Delivery Button
			driver.findElement(By.xpath("//div/div[4]/div/div/div[1]/div/div/div/div[1]/div/label[2]/div[1]/div")).click();
			System.out.println("Navigete to Payment method Page");
			Thread.sleep(4000);*/
			
			//Clicking on Next step in the Order page
			driver.findElement(By.xpath("//div/div[4]/div/div/div[3]/div/span[2]/form/input")).click();
			System.out.println("Navigate to Payment Details Page");
			Thread.sleep(4000);
			
			//Clicking on Check Box 
			driver.findElement(By.xpath("//div/div[4]/div[3]/div/label")).click();
			System.out.println("Check Box Clicked Successfully");
			Thread.sleep(4000);
			
			//Clicking on Place Order Button 
			driver.findElement(By.xpath("//input[@id='placeOrderButton']")).click();
			System.out.println("Place Order Button Clicked Successfully");
			Thread.sleep(4000);
			
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
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/form/div[3]/div[2]/input[2]")).click();							
			System.out.println("Pay Button Clicked Successfully and Navigate to Order Confirmation Page ");
			Thread.sleep(4000);
							
			//Order Details in OrderConfirmation page
			System.out.println("Order Confirmation Message :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]")).getText());
			System.out.println("Order Number is :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]/div/a[1]")).getText());	
			System.out.println("Order Details Message :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]/div")).getText());
			Thread.sleep(4000);
	
			//Proceeding for Survey by Checking YES box
			driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/div/div[1]/label")).click();							
			Thread.sleep(4000);
	
			//Entering the Answers for the Survey
			driver.findElement(By.id("input1")).sendKeys("Answer1");							
			Thread.sleep(4000);
			
			driver.findElement(By.id("input2")).sendKeys("Answer2");							
			Thread.sleep(4000);
			
			
/*			WebElement element=driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/div[3]/div/div/span/span[1]/span/span[2]/b"));

            element.click();

            element.getText();

            driver.findElement(By.xpath("//li[@id='select2-3afg-result-bjsk-Option Two']")).click();

            Thread.sleep(2000);     

            System.out.println("Drop Down values is " + element.getText()); 

            Thread.sleep(2000);*/
            
            
/*            Select select = new Select(driver.findElement(By.xpath(".//*[@id='form2:j_id_k_panel']/ul/All"))); 
            dropdown.selectByIndex(7);

            dropdown.selectByVisibleText("All");

            dropdown.selectByValue("All");

*/
		

/*			 driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/div[3]/div/div/span/span[1]/span/span[2]/b")).click();
		     Thread.sleep(2000);
		     driver.findElement(By.xpath("//li[@id='select2-3afg-result-bjsk-Option Two']")).click();       
		     Thread.sleep(2000);*/
			
			driver.findElement(By.id("textarea1")).sendKeys("Answer4");							
			Thread.sleep(4000);
			
/*			driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/div[5]/div/label[2]")).click();							
			Thread.sleep(4000);
			*/
			driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/div[5]/div/label[3]")).click();							
			Thread.sleep(4000);
			
			driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/div[6]/div[2]/label[1]")).click();							
			Thread.sleep(4000);
			
/*			driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/div[6]/div[2]/label[2]")).click();							
			Thread.sleep(4000);*/
			
			//Clicking on Send Button For Survey
			driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[2]/form/input")).click();							
			System.out.println("Send Button Clicked and Survey Submitted Successfully");
			Thread.sleep(4000);
	
	
	
	}

}

