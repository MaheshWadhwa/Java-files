package CUC_MNP_PORT_IN;
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



public class Data_CleanIn_MDM {
	
	
	static int row=2;
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
	static int checkCounter = 0;
	public Data_CleanIn_MDM(String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{
		
		Data_CleanIn_MDM.runlog=runlog;
		Data_CleanIn_MDM.report=report;
		Data_CleanIn_MDM.datapre=datapre;
		Data_CleanIn_MDM.report=report;
	}
	
	
	public  int data_CelanMDM(String MSISDN,String SIM) throws InterruptedException, IOException
	{
		
		//path=ConsumerActivationMain.datapre;

		//Launching Firefox 	
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MDM"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		WebDriver driver = null;
		conditionCounter = 0;
		String browser = "Chrome";

		//Browser initialisation and selection

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
		//CommonUtilities cu = new CommonUtilities();
		Thread.sleep(1000);	

		//Open MDM	URL
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Consumer_MNP.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(7);
		String URL = row1.getCell(2).getStringCellValue();
		driver.get(URL);
		Thread.sleep(10000);	

		//Read data from Excel

		/*HSSFSheet testSheet1 = testWork.getSheet("Activation");
		String SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
		String MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		inputStream.close();
		report.writeTorun(runlog, "Data read successfully");
*/
		//Select Filter Value as All from Drop down List		

		driver.findElement(By.xpath(".//*[@id='form2:input-msisdn']")).sendKeys(MSISDN);
		report.writeTorun(runlog, "MSISDN submitted  - " +MSISDN);
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='form2:j_id_2x']")).click();
		Thread.sleep(5000);	
		report.takeScreenShotMethod(driver, datapre, "Data_Check_In_MDM_MSISDN");

		try
		{
			if((driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).getText().equalsIgnoreCase("No contract found matching the search criterion")) && (driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[2]/label")).getText().equalsIgnoreCase("FATAL")))
				

			{
				
				report.writeTorun(runlog, "MSISDN pre check in MDM- Pass - " + MSISDN);

				//report.writeToFile(reportfile, "MSISDN pre check - Pass - " + MSISDN);
			}
			else
			{
				report.writeTorun(runlog, "MSISDN pre check in MDM- Fail - " + MSISDN);
				//report.writeToFile(reportfile, "MSISDN pre check - Fail - " + MSISDN);
				checkCounter++;
				conditionCounter++;
			}
		}
		catch(NoSuchElementException e)
		{
			try{
				if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Terminated")) 
				{ 
					report.writeTorun(runlog, "MSISDN pre check in MDM - Pass - " + MSISDN);
					//report.writeToFile(reportfile, "MSISDN pre check - Pass - " + MSISDN);

				}
				else
				{
					report.writeTorun(runlog, "MSISDN pre check - Fail -  " + MSISDN);

					//report.writeToFile(reportfile, "MSISDN pre check - Fail - " + MSISDN);
					checkCounter++;
					conditionCounter++;
				}
				Thread.sleep(3000);

			}

			catch(NoSuchElementException e1)
			{

				report.writeTorun(runlog, "Exception occured while checking MSISDN in MDM.");
			}

			//report.takeScreenShotMethod(driver, path, "Data_Check_In_MDM_MSISDN");
			System.out.println("Screenshot taken");

		}


		//**************************************************************************			
		//IF IT IS SIM		
		//CLICK ON GET ALL CONTACT WITH PRODUCT LINK
		driver.findElement(By.xpath(".//*[@id='form1:j_id_c']/span[2]")).click();
		Thread.sleep(10000);

		//SELECT SIM OPTION FROM OTHER SEARCH CIRTERIA
		driver.findElement(By.xpath(".//*[@id='form2:j_id_5o_label']")).click();;
		driver.findElement(By.xpath(".//*[@id='form2:j_id_5o_panel']/ul/li[2]")).click();

		//GIVE SIM 
		driver.findElement(By.xpath(".//*[@id='form2:input-paramValue']")).sendKeys(SIM);		
		Thread.sleep(4000);
		//CLICK ON SEARCH BUTTON
		driver.findElement(By.xpath(".//*[@id='form2:j_id_6c']")).click();
		Thread.sleep(4000);		

		Thread.sleep(4000);
		report.takeScreenShotMethod(driver, datapre, "Data_Check_In_MDM_SIM");

		try
		{
			if((driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).getText().equalsIgnoreCase("No contract found matching the search criterion")) && (driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[2]/label")).getText().equalsIgnoreCase("FATAL")))
			{
				driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).click();
				report.writeTorun(runlog, "SIM pre check - Pass -  " + SIM);
				//report.writeToFile(reportfile, "SIM pre check - Pass  - " + SIM);
			}
			else
			{
				driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).click();
				report.writeTorun(runlog, "SIM pre check - Fail -  " + SIM);
				//report.writeToFile(reportfile, "SIM pre check - Fail - " + SIM);

				checkCounter++;
				conditionCounter++;
			}


		}

		catch(NoSuchElementException e)
		{
			try{
				if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Terminated")){
					driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).click();
					report.writeTorun(runlog, "SIM pre check - Pass  - " + SIM);
				}
				else
				{
					driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).click();
					report.writeTorun(runlog, "SIM pre check - Fail  - " + SIM);
					checkCounter++;
					conditionCounter++;
				}
			}
			catch(NoSuchElementException e1){
				report.writeTorun(runlog, "Exception ouucerd");
			}
			//report.takeScreenShotMethod(driver, path, MDM_SIM);
		}
		Thread.sleep(2000);
		driver.close();
		return conditionCounter;

	}
}


