package iucNormalActivation_FrameWork;
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

@SuppressWarnings("unused")
public class DataCheckInMcr {

	static String dataCheckFolderPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	public DataCheckInMcr(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		DataCheckInMcr.dataCheckFolderPath = dataCheckFolderPath;
		DataCheckInMcr.reportFile = reportFile;
		DataCheckInMcr.datasetLogFile = datasetLogFile;
		DataCheckInMcr.utilities = utilities;
	} // end of constructor method

	public int execute(String Msisdn, String Sim) throws InterruptedException, IOException,ClassNotFoundException {

		int checkCounter = 0;
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MCR"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		WebDriver driver = null;
		String browser = "Firefox";
		String MCRurl = "http://10.6.37.119/MCR/Portal/members/Default.aspx";

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}
		else if (browser=="IE")
		{
			System.setProperty("webdriver.ie.driver","C:\\Automated_Execution\\Scripts\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}
		else if (browser=="Chrome")
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Automated_Execution\\Scripts\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}

		//Launching URL
		driver.get(MCRurl);
		driver.manage().window().maximize();

		//Login Page
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_UserName']")).sendKeys("e.khaleel");
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_Password']")).sendKeys("khaleel");
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_LoginImageButton']")).click();
		Thread.sleep(4000);

		//MSISDN Search

		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/a")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiMSISDN']")).sendKeys(Msisdn);
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegByMSISDN']")).click();
		Thread.sleep(3000);

		//RESULT OF MSISDN
		try{
			WebElement wb=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[2]/td"));
			String MSISDNResp =wb.getText();
			utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Data_Check_In_MCR_MSISDN");
			if(MSISDNResp.equals(""))
			{
				utilities.writeToFile(datasetLogFile, "MSISDN Available for use");
			}
			else {
				utilities.writeToFile(datasetLogFile, "MSISDN Not Available. It should be cleaned for use.");
				checkCounter++;
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			utilities.writeToFile(datasetLogFile, "MSISDN Available for use [NoSuchElementException - For reference]");
		}




		//***************************************************************************

		//TO CHECK SIM AVAILABILITY:

		// CLICK ON HOME BUTTON
		driver.findElement(By.xpath(".//*[@id='ctl00_uiHome']")).click();
		Thread.sleep(5000);

		//SIM SEARCH
		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[6]/td/a")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiSIM']")).sendKeys(Sim);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegBySIM']")).click();
		Thread.sleep(3000);

		//RESULT OF SIM		
		WebElement wbsim=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[3]/td"));
		String SIMResp =wbsim.getText();
		utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Data_Check_In_MCR_SIM");
		if(SIMResp.equals(""))
		{
			utilities.writeToFile(datasetLogFile, "SIM Available for use.");
		}
		else {
			utilities.writeToFile(datasetLogFile, "SIM Not Available. It should be cleaned for use.");
			checkCounter++;
			conditionCounter++;
		}
		driver.close();

		return conditionCounter;

	}
}