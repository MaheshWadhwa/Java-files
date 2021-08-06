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

public class PlacingOrder {

	//public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	//REPORT GENERATE
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String RunLog_path =  "C:\\Automated_Execution\\eSales\\Output\\RunLog_Report\\Report_RunLog";
	static String Report_Filepath = "C:\\Automated_Execution\\eSales\\Output\\File_Report\\Report_File";
	
	
		public static void main(String[] args) throws InterruptedException, IOException {
			
			//Launching Internet Explorer 	
				WebDriver driver = null;
		    	String eSalesUrl = "http://sda072.prod.mobily.lan/portalu/eSaleDemo/layouts/_homepage/index.html"; 
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
									
				
				 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\PlacingOrder.properties");
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
				System.out.println("eShop link cliked Successfully");
				report.writeToFile(Report_Filepath, "eShop link clicked Successfully");
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "eShop link cliked Successfully" +System.lineSeparator()+"--------------------------------------");
				Thread.sleep(3000);
				
				
				//CLICKED ON Devices LINK
				WebElement element1 = driver.findElement(By.xpath(prop.getProperty("Devices")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element1, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("Devices"))).click();
				System.out.println("Devices link clicked Successfully");
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Devices link cliked Successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Devices link clicked Successfully");
				Thread.sleep(3000);				
				
				//CLICKED ON ProductName
				System.out.println("Product Name :"+ driver.findElement(By.xpath(prop.getProperty("ProductName"))).getText());
				WebElement element2 = driver.findElement(By.xpath(prop.getProperty("ProductName")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element2, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("ProductName"))).click();
				System.out.println("ProductName clicked Successfully");	
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Product Name cliked Successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "ProductName clicked Successfully");
				Thread.sleep(3000);
				
				//Clicking on Buy in Product page
				System.out.println("Element Name in Product page :"+ driver.findElement(By.xpath(prop.getProperty("Buy"))).getText());
				WebElement element3 = driver.findElement(By.xpath(prop.getProperty("Buy")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element3, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("Buy"))).click();
				System.out.println("Buy Button clicked Successfully");
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Buy Button cliked Successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Buy Button clicked Successfully");
				Thread.sleep(3000);
				
				//Clicking on Add to Cart in the Buy pop-up
				System.out.println("Add to Cart in Product Button is :"+ driver.findElement(By.xpath(prop.getProperty("AddtoCart"))).getText());
				WebElement element4 = driver.findElement(By.xpath(prop.getProperty("AddtoCart")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element4, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("AddtoCart"))).click();
				System.out.println("Added the product into cart Successfully");	
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Added the product into cart Successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Added the product into cart Successfully");
				Thread.sleep(3000);				
				
				//Clicking on Next step the Product Page
				WebElement element5 = driver.findElement(By.xpath(prop.getProperty("NextStepButton")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element5, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("NextStepButton"))).click();
				System.out.println("Proceeded for Login");	
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Proceeded for Login" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Proceeded for Login");
				Thread.sleep(3000);	
				
				//Entering UserName in Login pop-up
				WebElement element6 = driver.findElement(By.xpath(prop.getProperty("LoginUsername")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element6, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("LoginUsername"))).sendKeys("rannapraveen");
				System.out.println("Username entered successfully");	
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Username entered successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Username entered successfully");
				Thread.sleep(3000);	
				
				//Entering Password in Login pop-up
				WebElement element7 = driver.findElement(By.xpath(prop.getProperty("LoginPassword")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element7, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("LoginPassword"))).sendKeys("Pravi@12345");
				System.out.println("Password entered successfully");
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Password entered successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Password entered successfully");
				Thread.sleep(3000);					
					
				//Clicking on Login Button
				WebElement element8 = driver.findElement(By.xpath(prop.getProperty("LoginButton")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element8, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("LoginButton"))).click();
				System.out.println("Logged Successfully");	
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Logged Successfully" +System.lineSeparator()+"--------------------------------------");
				report.writeToFile(Report_Filepath, "Logged Successfully");
				Thread.sleep(3000);	
				
				//Clicking on Next step in the Order page
				WebElement element9 = driver.findElement(By.xpath(prop.getProperty("NextStepOrder")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element9, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("NextStepOrder"))).click();
				System.out.println("Navigate to Delivery Details Page");
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Navigate to Payment Details Page" +System.lineSeparator()+"--------------------------------------");
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
				report.writeToFile(Report_Filepath, "Navigate to Delivery Details Page");
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
				report.writeToFile(Report_Filepath, "Place Order Button Clicked Successfully");
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
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+ "Card Details entered Successfully" +System.lineSeparator()+"--------------------------------------");
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
				report.writeTorun(RunLog_path, "Test Scenario Completed!!");
				report.writeToFile(Report_Filepath, "Pass");
				report.writeTorun(RunLog_path, "--------------------------------------"+System.lineSeparator()+"Test Scenario Completed - Pass"+System.lineSeparator()+"--------------------------------------");

				
				 //Destroying the driver object
				  driver.close();
		}

	}

				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				  
				
				
				
							
				
				
			
				
				
				
				
				
				
				
				
				/*
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
				driver.findElement(By.xpath("//div[@id='main-menu']/ul/li[3]/div/div/ul/li[5]/div/a")).click();
				System.out.println("Devices Link cliked Successfully");
				Thread.sleep(4000);
				
				//Clicking on Product
				System.out.println("Product Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).getText());
				WebElement element2 = driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a"));
				HE.elementHighlight(element2, driver);
				driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/div[1]/p/a")).click();
				System.out.println("Product Selected Suucessfully");
				Thread.sleep(4000);
				
				//Clicking on Buy in Product page
				System.out.println("Element Name in Product page :"+ driver.findElement(By.xpath("//a[@name='add-to-cart']")).getText());
				WebElement element3 = driver.findElement(By.xpath("//a[@name='add-to-cart']"));
				HE.elementHighlight(element3, driver);
				driver.findElement(By.xpath("//a[@name='add-to-cart']")).click();
				System.out.println("Proceeded Successfully");
				Thread.sleep(4000);
				
				//Clicking on Add to Cart in the Buy pop-up
				System.out.println("Element Name in pop-up page :"+ driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a")).getText());
				WebElement element4 = driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a"));
				HE.elementHighlight(element4, driver);
				driver.findElement(By.xpath("/html/body/div[8]/div[2]/div/div[3]/div/a")).click();
				System.out.println("Added the product into cart Successfully");
				Thread.sleep(4000);
				
				//Clicking on Next step the Product Page
				WebElement element5 = driver.findElement(By.id("next-step"));
				HE.elementHighlight(element5, driver);
				driver.findElement(By.id("next-step")).click();
				System.out.println("Proceeded for Login");
				Thread.sleep(4000);
				
				//Entering UserName in Login pop-up
				WebElement element6 = driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[1]/input"));
				HE.elementHighlight(element6, driver);
				driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[1]/input")).sendKeys("rannapraveen");
				System.out.println("Username entered successfully");
				Thread.sleep(4000);
				
				//Entering Password in Login pop-up
				WebElement element7 = driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[2]/input"));
				HE.elementHighlight(element7, driver);
				driver.findElement(By.xpath("/html/body/div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[2]/input")).sendKeys("Pravi@12345");
				System.out.println("Password entered successfully");
				Thread.sleep(4000);
				
				//Clicking on Login Button
				WebElement element8 = driver.findElement(By.xpath("//a[@name='add-to-cart']"));
				HE.elementHighlight(element8, driver);
				driver.findElement(By.xpath("//div/header/div/nav/div/div[2]/div[4]/div/div/div[2]/form/div[4]/input")).click();
				System.out.println("Logged Successfully");
				Thread.sleep(4000);
				
				//Clicking on Next step in the Order page
				WebElement element9 = driver.findElement(By.xpath("//div/div[4]/div/div[3]/div/span[2]/form/input"));
				HE.elementHighlight(element9, driver);
				driver.findElement(By.xpath("//div/div[4]/div/div[3]/div/span[2]/form/input")).click();
				System.out.println("Navigate to Delivery Details Page");
				Thread.sleep(4000);
				
				//Clicking on Cash on Delivery Button
				driver.findElement(By.xpath("//div/div[4]/div/div/div[1]/div/div/div/div[1]/div/label[2]/div[1]/div")).click();
				System.out.println("Navigete to Payment method Page");
				Thread.sleep(4000);
				
				//Clicking on Next step in the Order page
				WebElement element10 = driver.findElement(By.xpath("//div/div[4]/div/div/div[3]/div/span[2]/form/input"));
				HE.elementHighlight(element10, driver);
				driver.findElement(By.xpath("//div/div[4]/div/div/div[3]/div/span[2]/form/input")).click();
				System.out.println("Navigate to Payment Details Page");
				Thread.sleep(4000);
				
				//Clicking on Check Box 
				WebElement element11 = driver.findElement(By.xpath("//a[@name='add-to-cart']"));
				HE.elementHighlight(element11, driver);
				driver.findElement(By.xpath("//div/div[4]/div[3]/div/label")).click();
				System.out.println("Check Box Clicked Successfully");
				Thread.sleep(4000);
				
				//Clicking on Place Order Button 
				WebElement element12 = driver.findElement(By.xpath("//input[@id='placeOrderButton']"));
				HE.elementHighlight(element12, driver);
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
				WebElement element13 = driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/form/div[3]/div[2]/input[2]"));
				HE.elementHighlight(element13, driver);
				driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div/div[2]/form/div[3]/div[2]/input[2]")).click();							
				System.out.println("Pay Button Clicked Successfully and Navigate to Order Confirmation Page ");
				Thread.sleep(4000);
								
				//Order Details in OrderConfirmation page
				System.out.println("Order Confirmation Message :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]")).getText());
				System.out.println("Order Number is :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]/div/a[1]")).getText());	
				System.out.println("Order Details Message :"+ driver.findElement(By.xpath("//div/div[4]/div[1]/div[1]/div[1]/div")).getText());
				Thread.sleep(4000);
				
				//Select Filter Value as All from Drop down List		
						WebElement element=driver.findElement(By.id("form2:j_id_k_label"));
						element.click();
						element.getText();
						driver.findElement(By.xpath("//span[text()='Option Two']")).click();
						Thread.sleep(2000);	
						System.out.println("Drop Down values is " + element.getText());	
						Thread.sleep(2000);
						
						Select Se = new Select(element);
		    			Select Se = new Select(driver.findElement(By.id("form2:j_id_k_label")));
						
						*/
					
						

