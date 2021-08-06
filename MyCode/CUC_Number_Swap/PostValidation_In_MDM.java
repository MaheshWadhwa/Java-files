package CUC_Number_Swap;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import CUC_Number_Swap.PostActivationCheckInBilling;
//import report.CommonUtilities;


public class PostValidation_In_MDM {

	static int row=6;
	static int cellId=NumSwapMain.cellId;
	static Statement stmt;
	static String newMSISDN;
	static String SIM;
	static String username;
	static String password;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=NumSwapMain.runlog;
	static String reportfile=NumSwapMain.reportfile;
	static int conditionCounter;
	static int checkCounter;

	public int PostValidMDM() throws InterruptedException, IOException 
	{

		//Launching Firefox 	

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

		driver.manage().window().maximize();


		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC_NumSwap.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(7);
		String URL = row1.getCell(2).getStringCellValue();
		driver.get(URL);
		Thread.sleep(10000);	

		//Read data from Excel 
		HSSFSheet testSheet1 = testWork.getSheet("Activation");
		String Sim = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
		String oldMSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		String newMSISDN = testSheet1.getRow(4+row).getCell(cellId).getStringCellValue();
		inputStream.close();
		report.writeTorun(runlog,"Read Data from Excel Successful");



		driver.findElement(By.xpath(".//*[@id='form2:input-msisdn']")).sendKeys(newMSISDN);		
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='form2:j_id_2x']")).click();
		Thread.sleep(5000);		

		//To validate new MSISDN
		try{
			if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Active")) { 
				report.writeToFile(runlog, "MSISDN is Active in MDM");
			}
			else{
				report.writeToFile(runlog, "MSISDN Not Active in MDM");
				checkCounter++;
				conditionCounter++;
			}
			if(driver.findElement(By.xpath("//label[text()='SIM : ']/../../td[4]/label")).getText().equals(Sim)){
				report.writeToFile(runlog, "Associated SIM in MDM - "+Sim);
			}else{
				report.writeToFile(runlog, "Another SIM seen in MDM");
				checkCounter++;
				conditionCounter++;
			}
			if(driver.findElement(By.xpath("//label[text()='Account Number : ']/../../td[2]/label")).getText().equals(Postvalid_billing.new_account_no)){
				report.writeToFile(runlog, "Account Number in MDM - "+Postvalid_billing.new_account_no);
			}else{
				report.writeToFile(runlog, "Another Account Number seen in MDM");
				checkCounter++;
				conditionCounter++;
			}

		} catch(NoSuchElementException e1){
			report.writeToFile(runlog, "Exception occured while checking MSISDN in MDM.");
		}
		driver.findElement(By.xpath("//label[text()='Executed Date : ']")).click();
		//report.takeScreenShotMethod(driver, dataCheckFolderPath, "Post_Activation_Check_In_MDM_MSISDN");

		WebElement w=driver.findElement(By.xpath(".//*[@id='form2:input-msisdn']"));
		w.clear();
		w.sendKeys(oldMSISDN);
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='form2:j_id_2x']")).click();
		Thread.sleep(5000);		

		//To validate old MSISDN
		try{
			if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Active")) { 
				report.writeToFile(runlog, "Old MSISDN is Active in MDM");
				checkCounter++;
				conditionCounter++;
			}
			else{
				report.writeToFile(runlog, "Old MSISDN Not Active in MDM");
				
			}
			if(driver.findElement(By.xpath("//label[text()='SIM : ']/../../td[4]/label")).getText().equals(Sim)){
				report.writeToFile(runlog, "Associated SIM in MDM - "+Sim);
				checkCounter++;
				conditionCounter++;
			}else{
				report.writeToFile(runlog, "Another SIM seen in MDM");
				
			}
			if(driver.findElement(By.xpath("//label[text()='Account Number : ']/../../td[2]/label")).getText().equals(Postvalid_billing.account_no)){
				report.writeToFile(runlog, "Account Number in MDM - "+Postvalid_billing.account_no);
				checkCounter++;
				conditionCounter++;
			}else{
				report.writeToFile(runlog, "Another Account Number seen in MDM");
				
			}

		} catch(NoSuchElementException e1){
			report.writeToFile(runlog, "Exception occured while checking MSISDN in MDM.");
		}
		driver.findElement(By.xpath("//label[text()='Executed Date : ']")).click();
		
		
		
		
		driver.close();
		return conditionCounter;
	}
}


/*//TO VALIDATE new MSISDN STATUS		

		String newMSISDNStatus = driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText();
		report.writeToFile(reportfile,"new MSISDN Nubmer Status in MDM is :" + newMSISDNStatus);
		report.writeTorun(runlog,"New MSISDN Nubmer Status in MDM is :" + newMSISDNStatus);
		//TO VALIDATE SIM NUMBER 		
		String SimNo = driver.findElement(By.xpath("//label[text()='SIM : ']/../../td[4]/label")).getText();
		report.writeToFile(reportfile,"newMSISDN Associated with SIM Number in MDM is :" + SimNo);
		report.writeTorun(runlog,"New MSISDN Associated with SIM Number in MDM is :" + SimNo);

		//TO VALIDATE ACCOUNT NUMBER 
		String AcctNo = driver.findElement(By.xpath("//label[text()='Account Number : ']/../../td[2]/label")).getText();
		report.writeToFile(reportfile,"New MSISDN Associated with Account Number in MDM is :" +AcctNo);
		report.writeTorun(runlog,"New MSISDN Associated with Account Number in MDM is :" +AcctNo);
 */



