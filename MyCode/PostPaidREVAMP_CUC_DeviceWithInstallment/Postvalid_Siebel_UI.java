package PostPaidREVAMP_CUC_DeviceWithInstallment;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;




public class Postvalid_Siebel_UI 
	{
	
	static int row=2;
	//static int cellId=ConsumerActivation.cellId;
	static Statement stmt;
	static String MSISDN;
	static String SIM;
	static String username;
	static String password;
	static String File;
	static String Fil;
	/*static String post_path="";*/
	static String Billing_account;
	/*static String account=Postvalid_billing.account_no;
	static String orderid=ConsumerActivation.orderNumber;
	static String url;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;*/
	static int conditionCounter=0;
	static String runlog;
	//static String runlog;
	static String reportfile;
	static CommonUtilitiesWriter report;
	static String Postpre;
	static String url;
	static String orderNumber;
	static String Actipre;
	static String account;
	
	public Postvalid_Siebel_UI(String runlog,String reportfile,String Postpre,String Actipre,CommonUtilitiesWriter report)
	{
		
	//	Postvalid_billing.MSISDN=MSISDN;
	}
	
	public int post_siebel(String MSISDN)throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		conditionCounter=0;
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");data_read();
		siebel(MSISDN);
		return conditionCounter;

	}

	public static void siebel(String MSISDN) throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		//post_path=ConsumerActivationMain.Postpre;
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
		report.writeTorun(runlog,"URL invoked");
		driver.manage().window().maximize();

		//Login

		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(username);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(password);
		//Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();
		Thread.sleep(20000);
		System.out.println("Logged in succefully to Siebel");

		//Validation
		//Clicking on Services
		try{
			Alert alert0 = driver.switchTo().alert();
			alert0.accept();

			report.writeToFile(runlog, "Password Expire Alert Accepted");
		}catch(NoAlertPresentException e){

			report.writeToFile(runlog, "Password Expire Alert not present");
		}

		Thread.sleep(15000);

		// Go to Services tab
		report.clickWE(driver, "//a[text()='Services']");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_MSISDN_Label']")).sendKeys(MSISDN);
		report.clickWE(driver, ".//*[@id='s_1_1_22_0_Ctrl']");
		Thread.sleep(5000);

		// Check for service
		try{
			if(driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]"))).getText().equals("Active")){
				WebElement element = driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]")));
				//report.takeScreenShotMethodWE(driver, post_path, "Siebel_Services_Tab", element);
				report.takeScreenShotMethodWE(driver, Postpre, "Siebel_Services_Tab.jpg",element);
				report.writeTorun(runlog, "Service is Active in Siebel");
				report.writeToFile(reportfile, "Service is Active in Siebel");
			} else {
				WebElement element = driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]")));
				report.takeScreenShotMethodWE(driver, Postpre, "Siebel_Services_Tab", element);
				report.writeTorun(runlog, "Service is Not Active in Siebel");
				report.writeToFile(reportfile, "Service is Not Active in Siebel");
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			report.takeScreenShotMethod(driver, Postpre, "Siebel_Services_Tab.jpg");
			report.writeTorun(runlog, "Service Not Found in Siebel for Account "+account);
			report.writeToFile(reportfile, "Service Not Found in Siebel for Account "+account);
			conditionCounter++;
		}

		//order check

		report.writeTorun(runlog,"Order check");
		System.out.println("Order check");
		driver.findElement(By.xpath("//a[text()='Orders']")).click();;
		Thread.sleep(6000);
		report.writeTorun(runlog,"Clicked Order ");

		WebElement we=driver.findElement(By.xpath(".//*[@id='s_S_A2_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td/input"));
		we.click();
		we.sendKeys(orderNumber);

		driver.findElement(By.xpath(".//*[@id='s_2_1_4_0_Ctrl']")).click();
		Thread.sleep(5000);

		report.writeTorun(runlog,"Cllicked on go");

		try{
			if(driver.findElement(By.xpath("//a[text()='".concat(orderNumber).concat("']/../../td[4]"))).getText().equals("Applied")){
				WebElement element = driver.findElement(By.xpath("//a[text()='".concat(orderNumber).concat("']/../../td[4]")));
				report.takeScreenShotMethodWE(driver, Postpre, "Siebel_Orders_Tab.jpg",element);
				report.writeTorun(runlog, "Order is Applied in Siebel");
				report.writeToFile(reportfile, "Order is Applied in Siebel");
			}
			else
			{
				WebElement element = driver.findElement(By.xpath("//a[text()='".concat(orderNumber).concat("']/../../td[4]")));
				report.takeScreenShotMethodWE(driver, Postpre, "Siebel_Orders_Tab.jpg",element);
				report.writeTorun(runlog, "Order is Not Applied in Siebel");
				report.writeToFile(reportfile, "Order is Not Applied in Siebel");
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			report.takeScreenShotMethod(driver, Postpre, "Siebel_Orders_Tab.jpg");
			report.writeTorun(runlog, "Order Not found in Siebel");
			report.writeToFile(reportfile, "Order Not found in Siebel");
			conditionCounter++;
		}
		driver.quit();
		report.writeToFile(runlog, "Browser Closed");
	}


	public static void data_read() throws IOException
	{
		//row=2;
		File file1=new File ("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevamp_DeviceWithInstallment.xls");
		FileInputStream inputstrm= new FileInputStream(file1);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	/*	HSSFSheet testSheet1 = testdata.getSheet("Activation");
		MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
*/
		HSSFSheet testSheet = testdata.getSheet("Application_Information");
		HSSFRow ro2 = testSheet.getRow(2);
		username=ro2.getCell(2).getStringCellValue();
		HSSFRow ro3 = testSheet.getRow(3);
		password=ro3.getCell(2).getStringCellValue();
		HSSFRow ro4 = testSheet.getRow(1);
		url=ro4.getCell(2).getStringCellValue();

		inputstrm.close();
		//Billing_account=ro4.getCell(1).getStringCellValue();
		report.writeTorun(runlog,"Data read successfully");
		report.writeTorun(runlog,"Status query started");

		File file2=new File (Actipre.concat("\\Oreder.xls"));
		//File file_data=new File ("C:\\Automated_Execution\\Input\\DB_VALIDATION.xls");
		FileInputStream inputstrm1= new FileInputStream(file2);
		HSSFWorkbook testdata1=new HSSFWorkbook(inputstrm1); 
		HSSFSheet worksheet = testdata1.getSheet("Order");
		HSSFRow ro = worksheet.getRow(1);
		orderNumber = ro.getCell(0).getStringCellValue();
		//System.out.println(oreder_id);
		
		//testdata.close();
		
		inputstrm1.close();


		File file3=new File (Postpre.concat("\\Billing.xls"));
		//File file_data=new File ("C:\\Automated_Execution\\Input\\DB_VALIDATION.xls");
		FileInputStream inputstrm2= new FileInputStream(file3);
		HSSFWorkbook testdata2=new HSSFWorkbook(inputstrm2); 
		HSSFSheet worksheet1 = testdata2.getSheet("BILL");
		HSSFRow ro1 = worksheet1.getRow(1);
		account = ro1.getCell(0).getStringCellValue();
	}
}
