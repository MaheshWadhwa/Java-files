package PostPaidREVAMP_CUC_WithCashBack;

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





@SuppressWarnings("unused")
public class PostValidation_In_MCR 
{
	//static int row=2;
	//static int cellId=ConsumerActivationMain.cellId;
	static Statement stmt;
	static String MSISDN;
	static String SIM;
	static String username;
	static String password;
		
	/*static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;*/
	static int conditionCounter = 0;
	static int checkCounter = 0;
	static WebDriver driver = null;
	static String post_path="";
	
	//static int conditionCounter=0;
	static String runlog;
	//static String runlog;
	static String reportfile;
	static CommonUtilitiesWriter report;
	static String Postpre;
	
	
	public PostValidation_In_MCR(String runlog,String reportfile,String Postpre,String Actipre,CommonUtilitiesWriter report)
	
	{
		
		Postvalid_Siebel_UI.runlog=runlog;
		Postvalid_Siebel_UI.reportfile=reportfile;
		Postvalid_Siebel_UI.Postpre=Postpre;
		PostValidation_In_MCR.report=report;
	}
	
	
	
	public int postValidMCR(String MSISDN,String SIM) throws InterruptedException, IOException,ClassNotFoundException {
		
		//Postpre=ConsumerActivationMain.Postpre;
		checkCounter = 0;
		conditionCounter = 0;
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MCR"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		//Data File
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevamp.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(4);
		String MCRurl = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(5);
		String username = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(6);
		String password = row3.getCell(2).getStringCellValue();
		
	    
		/*testSheet = testWork.getSheet("Activation");
		MSISDN = testSheet.getRow(2+row).getCell(cellId).getStringCellValue();
		SIM = testSheet.getRow(row).getCell(cellId).getStringCellValue();*/

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
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiMSISDN']")).sendKeys(MSISDN);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegByMSISDN']")).click();
		report.writeTorun(runlog,"MSISDN submitted");
		
		Thread.sleep(3000);
		
		//RESULT OF MSISDN
				try{
					WebElement wb=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr/td/table/tbody/tr[2]/td"));
					String MSISDNResp =wb.getText();
					report.takeScreenShotMethod(driver, Postpre.concat("\\Post_Activation_Validation"), "Post_Activation_Check_In_MCR_MSISDN");
					if(MSISDNResp.equals(""))
					{
						report.writeToFile(runlog, "Entry not seen in MDM");
						checkCounter++;
						conditionCounter++;
					}
					else if(driver.findElement(By.xpath("//a[text()='"+MSISDN+"']/../../td[1]/a")).getText().equals(Postvalid_billing.account_no)){
						report.writeToFile(runlog, "MSISDN "+MSISDN+" is associated with Account Number "+Postvalid_billing.account_no+" in MCR");
					} else {
						report.writeToFile(runlog, "MSISDN associated with other Account Number");
						checkCounter++;
						conditionCounter++;
					}
				} catch(NoSuchElementException e){
					report.writeToFile(runlog, "MSISDN Available for use [NoSuchElementException - For reference]");
				}

				//***************************************************************************

				//TO CHECK SIM AVAILABILITY:

				simCheckInMcr();
				
				driver.close();

				return conditionCounter;

			}

		
	public void simCheckInMcr() throws InterruptedException, FileNotFoundException{
		// CLICK ON HOME BUTTON
		driver.findElement(By.xpath(".//*[@id='ctl00_uiHome']")).click();
		Thread.sleep(5000);

		//SIM SEARCH
		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[6]/td/a")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiSIM']")).sendKeys(SIM);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegBySIM']")).click();
		Thread.sleep(3000);

		//RESULT OF SIM		
		WebElement wbsim=driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiData']/tbody/tr[1]/td/table/tbody/tr[1]/td/table/tbody/tr[3]/td"));
		String SIMResp =wbsim.getText();
		report.takeScreenShotMethod(driver, post_path.concat("\\Post_Activation_Validation"), "Post_Activation_Check_In_MCR_");
		
	}

}
