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

public class DeviceCompare {

	//public static void main(String[] args) {
		
		public static void main(String[] args) throws InterruptedException, IOException , NumberFormatException{
			
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
				
				
				 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") +"\\src\\Digital_eSales\\DeviceCompare.properties");
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
				
				//CLICKED ON ESHOP LINK
				WebElement element = driver.findElement(By.xpath(prop.getProperty("eShop")));
				HE.elementHighlight(element, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				capt.CaptureScreenShot();
				driver.findElement(By.xpath(prop.getProperty("eShop"))).click();
				System.out.println("eShop link clicked Successfully");			 
				Thread.sleep(3000);
				
				//CLICKED ON Devices LINK
				WebElement element1 = driver.findElement(By.xpath(prop.getProperty("Devices")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element1, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("Devices"))).click();
				System.out.println("Devices link clicked Successfully");			
				Thread.sleep(3000);		
				
				//CLICKED ON Added Button
				WebElement element2 = driver.findElement(By.xpath(prop.getProperty("AddButton")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element2, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("AddButton"))).click();
				System.out.println("Added Button clicked Successfully");			
				Thread.sleep(3000);	
				
				//CLICKED ON Compare Button
				WebElement element3 = driver.findElement(By.xpath(prop.getProperty("CompareButton")));
				capt.CaptureScreenShot();
				HE.elementHighlight(element3, driver);
				HE.takeScreenShotMethod(driver,path,fileName);	
				driver.findElement(By.xpath(prop.getProperty("CompareButton"))).click();
				System.out.println("Compare Button clicked Successfully");			
				Thread.sleep(3000);	
				
				//PriceName Captured
				System.out.println("Capture Element Name :"+ driver.findElement(By.xpath(prop.getProperty("Price"))).getText());
				
				//Capture price lists
				String Price1 = driver.findElement(By.xpath(prop.getProperty("Price1"))).getText();
				String Price2 = driver.findElement(By.xpath(prop.getProperty("Price2"))).getText();
				String Price3 = driver.findElement(By.xpath(prop.getProperty("Price3"))).getText();
				
				System.out.println("Price of the First Device :"+ driver.findElement(By.xpath(prop.getProperty("Price1"))).getText());
				System.out.println("Price of the Second Device :"+ driver.findElement(By.xpath(prop.getProperty("Price2"))).getText());
				System.out.println("Price of the Third Device :"+ driver.findElement(By.xpath(prop.getProperty("Price3"))).getText());
				
				String StrPrice1 = Price1.substring(0, 3);
				System.out.println("Price1 " + StrPrice1);
				
				String StrPrice2 = Price2.substring(0, 3);
				System.out.println("Price2 " + StrPrice2);
				
				String StrPrice3 = Price3.substring(0, 3);
				System.out.println("Price3 " + StrPrice3);
				
				int a = Integer.parseInt(StrPrice1);
				int b = Integer.parseInt(StrPrice2);
				int c = Integer.parseInt(StrPrice3);
				
				
				if ((a <= b) && (a <= c)) 
				{ 
				    System.out.println ("Preferred Device with Low price is : " + a);
				} else if ((b <= c))
				{    
				    System.out.println ("Preferred Device with Low price is: " + b);
				} 
				else 
				{                                            
				    System.out.println ("Preferred Device with Low price is: " + c);
				}
				
				
			System.out.println("Test Scenario Completed!!");
				
			//Destroying the driver object
			 driver.close();
				
		}

	}


				
				
				
				
				
				
				
			/*	
				
				
							
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
				System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/button[2]")).getText());
				driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[1]/div[1]/div[2]/button[2]")).click();
				System.out.println(" Added Button cliked Successfully");
				Thread.sleep(4000);
				
				//CLICKED ON Compare Button
				System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[3]/div/div[3]/div[2]/a")).getText());
				driver.findElement(By.xpath("//div[4]/div[3]/div/div/div[3]/div/div[3]/div[2]/a")).click();
				System.out.println(" Added Button cliked Successfully");
				Thread.sleep(4000);
				
				System.out.println("Capture Element Name :"+ driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[1]/div/p")).getText());
				
				String Price1 = driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[2]/div/p[1]")).getText();
				String Price2 = driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[2]/div/p[2]")).getText();
				String Price3 = driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[2]/div/p[3]")).getText();
				
				System.out.println("Price of the First Device :"+ driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[2]/div/p[1]")).getText());
				System.out.println("Price of the Second Device :"+ driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[2]/div/p[2]")).getText());
				System.out.println("Price of the Third Device :"+ driver.findElement(By.xpath("//div[3]/div[5]/div[2]/div[2]/div/p[3]")).getText());
				
				String StrPrice1 = Price1.substring(0, 3);
				System.out.println("Price1 " + StrPrice1);
				
				String StrPrice2 = Price2.substring(0, 3);
				System.out.println("Price2 " + StrPrice2);
				
				String StrPrice3 = Price3.substring(0, 3);
				System.out.println("Price3 " + StrPrice3);
				
				int a = Integer.parseInt(StrPrice1);
				int b = Integer.parseInt(StrPrice2);
				int c = Integer.parseInt(StrPrice3);
				
				
				if ((a <= b) && (a <= c)) 
				{ 
				    System.out.println ("Preferred Device with Low price is : " + a);
				} else if ((b <= c))
				{    
				    System.out.println ("Preferred Device with Low price is: " + b);
				} 
				else 
				{                                            
				    System.out.println ("Preferred Device with Low price is: " + c);
				}
				
				System.out.println("Test Scenario Completed!!");
				
			//Destroying the driver object
			 driver.close();
				
		}

	}

		

	
*/