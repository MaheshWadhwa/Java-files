package CUC_Number_Swap;

import java.sql.Statement;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.By;
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

import CUC_Number_Swap.PostActivationCheckInBilling;

@SuppressWarnings("unused")
public class PostValidation_In_MCR 
{
	static int row=6;
	static int cellId=NumSwapMain.cellId;
	static Statement stmt;
	static String MSISDN;
	static String SIM;
	static String username;
	static String password;
	static int checkCounter;
	static int conditionCounter;
		
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=NumSwapMain.runlog;
	static String reportfile=NumSwapMain.reportfile;
	
	public int postValidMCR() throws InterruptedException, IOException,ClassNotFoundException {
		
		checkCounter = 0;
		conditionCounter = 0;
		//Data File
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC_NumSwap.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(4);
		String MCRurl = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(5);
		String username = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(6);
		String password = row3.getCell(2).getStringCellValue();
		
	    
		testSheet = testWork.getSheet("Activation");
		String oldMSISDN = testSheet.getRow(2+row).getCell(cellId).getStringCellValue();
		String newMSISDN = testSheet.getRow(4+row).getCell(cellId).getStringCellValue();

		// TODO Auto-generated method stub
		WebDriver driver = null;
		String browser = "Chrome";
		//String MCRurl = "http://10.6.37.119/MCR/Portal/members/Default.aspx";
//		driver.manage().window().maximize();
				
		//driver=new FirefoxDriver();
		
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
		
    	//Launching URL
		driver.get(MCRurl);
		report.writeTorun(runlog,"URL invoked");

		//Login Page
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_UserName']")).sendKeys(username);
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_Password']")).sendKeys(password);
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_LoginImageButton']")).click();
		report.writeTorun(runlog,"Logged in successfully");
		Thread.sleep(4000);
		
		//MSISDN Search		
		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/a")).click();
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiMSISDN']")).sendKeys(newMSISDN);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegByMSISDN']")).click();
		report.writeTorun(runlog,"New MSISDN submitted");
		
		Thread.sleep(3000);
		
		//RESULT OF New MSISDN
		try{
			WebElement wb=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[2]/td"));
			String MSISDNResp =wb.getText();
			//utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Post_Activation_Check_In_MCR_MSISDN");
			if(MSISDNResp.equals(""))
			{
				report.writeToFile(runlog, "Entry not seen in MCR");
				checkCounter++;
				conditionCounter++;
			}
			else if(driver.findElement(By.xpath("//a[text()='"+newMSISDN+"']/../../td[1]/a")).getText().equals(Postvalid_billing.new_account_no)){
				report.writeToFile(runlog, "MSISDN "+newMSISDN+" is associated with Account Number "+Postvalid_billing.new_account_no+" in MCR");
			} else {
				report.writeToFile(runlog, "MSISDN associated with other Account Number");
				checkCounter++;
				conditionCounter++;
			}
		} 
		catch(NoSuchElementException e)
		{
			report.writeToFile(runlog, "New MSISDN Available for use [NoSuchElementException - For reference]");
		}
		
		
		//Result of old MSISDN
		driver.findElement(By.xpath(".//*[@id='ctl00_uiHome']")).click();
		
		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/a")).click();
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiMSISDN']")).sendKeys(oldMSISDN);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegByMSISDN']")).click();
		report.writeTorun(runlog,"Old MSISDN submitted");
		
		Thread.sleep(3000);
		
		try{
			WebElement wb=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[2]/td"));
			String MSISDNResp =wb.getText();
			//utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Post_Activation_Check_In_MCR_MSISDN");
			if(MSISDNResp.equals(""))
			{
				report.writeToFile(runlog, "Entry not seen in MDM - Pass");
			
			}
			else if(driver.findElement(By.xpath("//a[text()='"+newMSISDN+"']/../../td[1]/a")).getText().equals(Postvalid_billing.account_no)){
				report.writeToFile(runlog, "MSISDN "+newMSISDN+" is associated with Account Number "+Postvalid_billing.account_no+" in MCR - Fail");
				checkCounter++;
				conditionCounter++;
			} else {
				report.writeToFile(runlog, "MSISDN associated with other Account Number - Pass");
				
			}
		} catch(NoSuchElementException e){
			report.writeToFile(runlog, "MSISDN Available for use [NoSuchElementException - For reference]");
		}
		
		
		
		
		/*//TO VALIDATE ACCOUNT NUMBER	
		String AccountNo = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiActiveAccount']/tbody/tr[2]/td[1]/a")).getText();
		report.writeToFile(reportfile,"MSISDN Associated with Account Number in MCR is :" + AccountNo);
		report.writeTorun(runlog,"MSISDN Associated with Account Number in MCR is :" + AccountNo);
		*/
		/*//TO VALIDATE MSISDN NUMBER 		
		String msisdn = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiActiveAccount']/tbody/tr[2]/td[3]/a")).getText();
		report.writeToFile(reportfile,"MSISDN Number in MCR is :" + msisdn);
		report.writeTorun(runlog,"MSISDN Number in MCR is :" + msisdn);
		*/
		/*//TO VALIDATE SIM NUMBER 
		String sim = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiActiveAccount']/tbody/tr[2]/td[4]")).getText();
		report.writeToFile(reportfile,"MSISDN Associated with SIM Number in MCR is :" +sim);
		report.writeTorun(runlog,"MSISDN Associated with SIM Number in MCR is :" +sim);
		*/
		
		
		return conditionCounter;
	}
}
		
		

