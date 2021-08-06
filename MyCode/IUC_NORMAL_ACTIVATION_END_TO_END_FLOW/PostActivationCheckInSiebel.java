package IUC_NORMAL_ACTIVATION_END_TO_END_FLOW;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import Utilities.CommonUtilitiesWriterOLD;

public class PostActivationCheckInSiebel 
{

	static String MSISDN;
	static String username;
	static String password;
	static String url;
//	static String Billing_account;
	static String account;
	static String orderid;
	
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static String postcheckFolderPath = "";
	static CommonUtilitiesWriterOLD utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public PostActivationCheckInSiebel(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriterOLD utilities){
		PostActivationCheckInSiebel.reportFile = reportFile;
		PostActivationCheckInSiebel.datasetLogFile = datasetLogFile;
		PostActivationCheckInSiebel.datasetFolderPath = datasetFolderPath;
		PostActivationCheckInSiebel.utilities = utilities;	
		PostActivationCheckInSiebel.postcheckFolderPath = datasetFolderPath.concat("\\Post_Activation_Validation");
	} // end of constructor method
	
	public void checkSiebel(String Msisdn) throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		data_read();
		siebel();
	}

	public static void siebel() throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		WebDriver driver = null;
		String browser = "Chrome";

		if (browser=="Firefox") 
		{
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

		//Launching URL
		driver.get(url);
		utilities.writeToFile(datasetLogFile, "URL Invoked");
		driver.manage().window().maximize();

		//Login
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(username);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(password);
		utilities.clickWE(driver, "//a[text()='Login']");
		Thread.sleep(15000);
		
		// Go to Services tab
		utilities.clickWE(driver, "//a[text()='Services']");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_MSISDN_Label']")).sendKeys(MSISDN);
		utilities.clickWE(driver, ".//*[@id='s_1_1_22_0_Ctrl']");
		Thread.sleep(5000);
		
		// Check for service
		try{
			if(driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]"))).getText().equals("Active")){
				utilities.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Services_Tab.jpg");
				utilities.writeToFile(datasetLogFile, "Service is Active in Siebel");
				utilities.writeToFile(reportFile, "Service is Active in Siebel");
			}
		} catch(NoSuchElementException e){
			utilities.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Services_Tab.jpg");
			utilities.writeToFile(datasetLogFile, "Service is Not Active in Siebel");
			utilities.writeToFile(reportFile, "Service is Not Active in Siebel");
			conditionCounter++;
		}
			
		// Go to Orders tab
		utilities.clickWE(driver, "//a[text()='Orders']");
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='Order_Number_Label']")).sendKeys(orderid);
		utilities.clickWE(driver, "//button[text()='Go']");
		Thread.sleep(5000);
		
		// Check for Order
		try{
			if(driver.findElement(By.xpath("//a[text()='".concat(account).concat("']/../../td[4]"))).getText().equals("Applied")){
				utilities.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Orders_Tab.jpg");
				utilities.writeToFile(datasetLogFile, "Order is Applied in Siebel");
				utilities.writeToFile(reportFile, "Order is Applied in Siebel");
			}
		} catch(NoSuchElementException e){
			utilities.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Orders_Tab.jpg");
			utilities.writeToFile(datasetLogFile, "Service is Not Applied in Siebel");
			utilities.writeToFile(reportFile, "Service is Not Applied in Siebel");
			conditionCounter++;
		}
	}


	public static void data_read() throws IOException
	{
		
		
	}
}
