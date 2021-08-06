package ConsumerRaqiActivation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


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
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public PostActivationCheckInSiebel(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		PostActivationCheckInSiebel.reportFile = reportFile;
		PostActivationCheckInSiebel.datasetLogFile = datasetLogFile;
		PostActivationCheckInSiebel.datasetFolderPath = datasetFolderPath;
		PostActivationCheckInSiebel.utilities = utilities;	
		PostActivationCheckInSiebel.postcheckFolderPath = datasetFolderPath.concat("\\Post_Activation_Validation");
	} // end of constructor method

	public int checkSiebel(String Msisdn) throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		conditionCounter = 0;
		MSISDN = Msisdn;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		data_read();
		siebel();
		return conditionCounter;
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
		try {

			// Fetch Order ID from file
			String file = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_CONSUMER_RAQI.xls");
			File file_data=new File (file);
			FileInputStream inputstrm= new FileInputStream(file_data);

			HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
			HSSFSheet worksheet = testdata.getSheet("Sheet1");
			HSSFRow ro = worksheet.getRow(1);
			orderid = ro.getCell(0).getStringCellValue();
			testdata.close();
			inputstrm.close();
			
			// Fetch Service Account Number from file
			file = datasetFolderPath.concat("\\Post_Activation_Validation\\Billing.xls");
			file_data=new File (file);
			inputstrm= new FileInputStream(file_data);

			testdata=new HSSFWorkbook(inputstrm); 
			worksheet = testdata.getSheet("Billing_Query_1");
			ro = worksheet.getRow(1);
			account = ro.getCell(0).getStringCellValue();
			testdata.close();
			inputstrm.close();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
	}
}