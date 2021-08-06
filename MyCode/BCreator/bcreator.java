package BCreator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class bcreator {
	
	static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String Actipre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	static String device;
	static String  orderNumber;
	
		
	public bcreator(String runlog,String reportfile,String Actipre,CommonUtilitiesWriter report)
	{
		bcreator.runlog=runlog;
		bcreator.reportfile=reportfile;
		bcreator.Actipre=Actipre;
		bcreator.report=report;
	}
	
	public int bcreatoractivation (int cellId,String device) throws IOException, FileNotFoundException, InterruptedException 
	
	{
		
		if(device.equalsIgnoreCase("Skip"))
		{
			report.writeToFile(reportfile, System.lineSeparator()+"B-Creator Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else
		{
			report.writeToFile(reportfile, System.lineSeparator()+"Consmer Activation with device Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation with device Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		}

		
		WebDriver driver = null;
		String browser = "Chrome";
		
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		
		//int cellId = iterations;
		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Consumer.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
				
		HSSFWorkbook testWorkBook = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWorkBook.getSheet("Application_Information");
		
		// Login Page Data
		HSSFRow row1 = testSheet.getRow(1);
		String siebelUrl = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(2);
		String xUserName = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(3);
		String xPassword = row3.getCell(2).getStringCellValue();
		
		HSSFRow row4=testSheet.getRow(3);
		String xRuname = row4.getCell(2).getStringCellValue();
		HSSFRow row5=testSheet.getRow(4);
		String xRpassword = row5.getCell(2).getStringCellValue();
		
		
		//System.out.println("Application info read");	

		// Activation Page Data
		
		testSheet = testWorkBook.getSheet("Activation");

		String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMsisdn = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDeliveryPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPaymentType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSegment = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMonthlyfee = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPackagesetupfee = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xArabicinvoice = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xEnglishinvoice = testSheet.getRow(++row).getCell(cellId).getStringCellValue();

		report.writeTorun(runlog,"Read Data from Excel Successful");
		inputStream.close();
		//Maximize
		driver.manage().window().maximize();

		driver.get(siebelUrl);
		report.writeTorun(runlog,"URL invoked");

		// Login as creater
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();

		report.writeTorun(runlog,"Clicked on Login");
		
		
		//Creation part
		
		driver.findElement(By.xpath("//img[@id='tb_2']")).click();//CLick on site map
		
		Thread.sleep(7000);
		driver.findElement(By.xpath("//a[@id='s_sma38']")).click();//click on Admin Product
		
		driver.findElement(By.xpath("//a[text()='My Packages - Author View']")); //Click on My packages view
		
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@id='s_7_1_8_0_Ctrl']")).click();//Click on new
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@id='1_Package_Name']")).sendKeys(xPackage);//Eneter package
		
		//Package type
		driver.findElement(By.xpath("//input[@name='s_3_1_43_0']")).sendKeys(xType);;
		
		
		//Customer segment type(CUC,Consumer,IUC, etc.)
	
		driver.findElement(By.xpath("//input[@name='s_3_1_2_0']")).sendKeys(xSegment);
		
		//Packahe type
		
		driver.findElement(By.xpath("//input[@name='s_3_1_0_0']")).sendKeys(xType);
		
		//Monthly fee
		
		driver.findElement(By.xpath("//input[@name='s_3_1_4_0']")).sendKeys(xMonthlyfee);
		
		driver.findElement(By.xpath("//input[@name='s_3_1_7_0']")).sendKeys(xPackagesetupfee);
		
		driver.findElement(By.xpath("//input[@name='s_3_1_7_0']")).sendKeys(xArabicinvoice);
		
		driver.findElement(By.xpath("//input[@name='s_3_1_7_0']")).sendKeys(xEnglishinvoice);
		
		
		//Add on
		
			driver.findElement(By.xpath("//button[@id='s_6_1_9_0_Ctrl']")).click();
			
			Thread.sleep(3000);
			try{
				Alert alert1 = driver.switchTo().alert();
				alert1.accept();
				report.writeTorun(runlog,"Alert1 Accepted");
			}catch(NoAlertPresentException e){
				report.writeTorun(runlog,"Alert1 not present");
			}
		
			try{
				Alert alert2 = driver.switchTo().alert();
				alert2.accept();
				report.writeTorun(runlog,"Alert2 Accepted");
			}catch(NoAlertPresentException e){
				report.writeTorun(runlog,"Alert2 not present");
			}
		
			//Have to add the add on
		
			
			
			
			
			
			
			//Submit for review
			driver.findElement(By.xpath("//button[@id='s_7_1_9_0_Ctrl']")).click();
			Thread.sleep(7000);
			
			
			//Close the browser
			
			driver.quit();
			
			//Log inn reviewer
			
			
			driver.manage().window().maximize();

			driver.get(siebelUrl);
			report.writeTorun(runlog,"URL invoked");

			// Login as creater
			driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
			driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
			driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();

			report.writeTorun(runlog,"Clicked on Login");
			

			//Reviewer
			
			driver.findElement(By.xpath("//img[@id='tb_2']")).click();//CLick on site map
			
			Thread.sleep(7000);
			driver.findElement(By.xpath("//a[@id='s_sma38']")).click();//click on Admin Product
			
			//Reviewer
			
			driver.findElement(By.xpath("//a[text()='Review Packages - Reviewer View"));
			
			//Query
			
			driver.findElement(By.xpath("//button[@id='s_1_1_8_0_Ctrl']")).click();
			
			
			driver.findElement(By.xpath("//input[@id='1_Package_Name']")).click();
			
			driver.findElement(By.xpath("//input[@id='1_Package_Name']")).sendKeys(xPackage);
			
			driver.findElement(By.xpath("//button[@id='s_1_1_5_0_Ctrl']")).click();
			
			// Have to add package drill down
			
		return 0;

	}
}
