package ConsumerRaqiActivation;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
public class PostActivationCheckInMCR 
{
	static String dataCheckFolderPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;
	static int checkCounter = 0;
	static WebDriver driver = null;
	

	public PostActivationCheckInMCR(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		PostActivationCheckInMCR.dataCheckFolderPath = dataCheckFolderPath;
		PostActivationCheckInMCR.reportFile = reportFile;
		PostActivationCheckInMCR.datasetLogFile = datasetLogFile;
		PostActivationCheckInMCR.utilities = utilities;
	} // end of constructor method

	public int execute(String Msisdn, String Sim1, String Sim2, String Sim3) throws InterruptedException, IOException,ClassNotFoundException {

		checkCounter = 0;
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MCR"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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
			utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Post_Activation_Check_In_MCR_MSISDN");
			if(MSISDNResp.equals(""))
			{
				utilities.writeToFile(datasetLogFile, "Entry not seen in MDM");
				checkCounter++;
				conditionCounter++;
			}
			else if(driver.findElement(By.xpath("//a[text()='"+Msisdn+"']/../../td[1]/a")).getText().equals(PostActivationCheckInBilling.account_no)){
				utilities.writeToFile(datasetLogFile, "MSISDN "+Msisdn+" is associated with Account Number "+PostActivationCheckInBilling.account_no+" in MDM");
			} else {
				utilities.writeToFile(datasetLogFile, "MSISDN associated with other Account Number");
				checkCounter++;
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			utilities.writeToFile(datasetLogFile, "MSISDN Available for use [NoSuchElementException - For reference]");
		}

		//***************************************************************************

		//TO CHECK SIM AVAILABILITY:

		simCheckInMcr(Sim1,"SIM1");
		simCheckInMcr(Sim2,"SIM2");
		simCheckInMcr(Sim3,"SIM3");
		driver.close();

		return conditionCounter;

	}
	public void simCheckInMcr(String Sim, String type) throws InterruptedException{
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
		utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Post_Activation_Check_In_MCR_"+type);
		
	}
}
		
		

