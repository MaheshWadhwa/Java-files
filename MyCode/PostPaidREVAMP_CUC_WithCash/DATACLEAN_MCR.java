package PostPaidREVAMP_CUC_WithCash;
import java.sql.Statement;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.By;
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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("unused")
public class DATACLEAN_MCR {

	static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String datapre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	
	
	/*static int row=2;
	static int cellId=ConsumerActivationMain.cellId;
	static int conditionCounter = 0;
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;
	static String post_path=ConsumerActivationMain.Postpre;*/
	
	public DATACLEAN_MCR(String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{
		DATACLEAN_MCR.runlog=runlog;
		DATACLEAN_MCR.reportfile=reportfile;
		DATACLEAN_MCR.datapre=datapre;
		DATACLEAN_MCR.report=report;
	
	}
	
	
	public int mcrClean(String MSISDN,String SIM) throws InterruptedException, IOException,ClassNotFoundException,NullPointerException

	{

		//Launch URL

		int checkCounter = 0;
		conditionCounter = 0;
		WebDriver driver = null;
		String browser = "Chrome";

		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MCR"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");	
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

		//Data File

		//Open MDM	
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevampWithCash.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(4);
		String URL = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(5);
		String username = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(6);
		String password = row3.getCell(2).getStringCellValue();

	/*	HSSFSheet testSheet1 = testWork.getSheet("Activation");
		String SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
		String MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
*/
		// TODO Auto-generated method stub

		//Launching URL
		driver.get(URL);
		driver.manage().window().maximize();
		Thread.sleep(5000);
		//driver.manage().window().maximize();
		report.writeTorun(runlog, "MCR URL Invoked");
		//Login Page
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_UserName']")).sendKeys(username);
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_Password']")).sendKeys(password);
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_LoginImageButton']")).click();
		report.writeTorun(runlog, "Logged in successfully");
		Thread.sleep(4000);

		//MSISDN Search

		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/a")).click();
		Thread.sleep(3000);
		WebElement w=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiMSISDN']"));
		//w.click();
		w.sendKeys(MSISDN);
		System.out.print(MSISDN);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegByMSISDN']")).click();
		report.writeTorun(runlog,"MSISDN submitted");
		Thread.sleep(3000);
		report.takeScreenShotMethod(driver, datapre, "Data_Check_In_MCR_MSISDN");

		//RESULT OF MSISDN		
		try{
			WebElement wb=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[2]/td"));
			String MSISDNResp =wb.getText();
			report.takeScreenShotMethod(driver, datapre, "Data_Check_In_MCR_MSISDN");
			//report.writeTorun(runlog,"Data_Check_In_MCR_MSISDN");

			if(MSISDNResp.equals(""))
			{
				//report.writeToFile(reportfile, "MSISDN Pre check - Pass");
				report.writeTorun(runlog, "MSISDN Pre check - Pass");
			}
			else 
			{

				report.writeTorun(runlog, "MSISDN Pre check - Fail");
				//report.writeToFile(runlog, "MSISDN Pre check - Fail");
				checkCounter++;
				conditionCounter++;	
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println( "MSISDN Available for use [NoSuchElementException - For reference]");
			report.writeTorun(runlog, "MSISDN Available for use [NoSuchElementException - For reference]");
		}

		//***************************************************************************

		//TO CHECK SIM AVAILABILITY:

		// CLICK ON HOME BUTTON
		driver.findElement(By.xpath(".//*[@id='ctl00_uiHome']")).click();
		Thread.sleep(10000);

		//SIM SEARCH
		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[6]/td/a")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiSIM']")).sendKeys(SIM);
		System.out.print(SIM);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegBySIM']")).click();
		report.writeTorun(runlog, "SIM Sumbitted");
		Thread.sleep(3000);

		//RESULT OF SIM		
		WebElement wbsim=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[3]/td"));
		String SIMResp =wbsim.getText();
		report.takeScreenShotMethod(driver, datapre, "Data_Check_In_MCR_SIM");
		if(SIMResp.equals(""))
		{
			report.writeTorun(runlog, "SIM pre check - Pass");
			//report.writeToFile(reportfile, "SIM pre check - Pass");

			
		}
		else 
		{
			report.writeTorun(runlog, "SIM pre check - Fail");
			//report.writeToFile(reportfile, "SIM pre check - Fail");
			checkCounter++;
			conditionCounter++;
		}

		driver.close();
		return conditionCounter;

	}
}