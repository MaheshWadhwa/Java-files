package RAAQI_MNP_PORT_IN;
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




public class PostValidation_In_MDM {
	
	static int row=2;
	static int cellId=RaaqiMNPPortINActivationMain.cellId;
	static Statement stmt;
	static String MSISDN;
	static String SIM;
	static String username;
	static String password;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=RaaqiMNPPortINActivationMain.runlog;
	static String reportfile=RaaqiMNPPortINActivationMain.reportfile;
	static int conditionCounter=0;
	static int checkCounter=0;
	static String post_path="";

	public int PostValidMDM() throws InterruptedException, IOException 
	{

		//Launching Firefox		
		post_path=RaaqiMNPPortINActivationMain.Postpre;
		conditionCounter = 0;
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in MDM"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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


		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_RaqiMNP.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(7);
		String URL = row1.getCell(2).getStringCellValue();
		driver.get(URL);
		Thread.sleep(10000);	

		//Read data from Excel 
		HSSFSheet testSheet1 = testWork.getSheet("Activation");
		String Msisdn = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		String Sim = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
		inputStream.close();
		report.writeTorun(runlog,"Read Data from Excel Successful");



		driver.findElement(By.xpath(".//*[@id='form2:input-msisdn']")).sendKeys(Msisdn);		
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='form2:j_id_2x']")).click();
		Thread.sleep(5000);		



		try{
			if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Active")) { 
				report.writeTorun(runlog, "MSISDN is Active in MDM");
			}
			else{
				report.writeTorun(runlog, "MSISDN Not Active in MDM");
				checkCounter++;
				conditionCounter++;
			}
			if(driver.findElement(By.xpath("//label[text()='SIM : ']/../../td[4]/label")).getText().equals(Sim)){
				report.writeTorun(runlog, "Associated SIM in MDM - "+Sim);
			}else{
				report.writeTorun(runlog, "Another SIM seen in MDM");
				checkCounter++;
				conditionCounter++;
			}
			if(driver.findElement(By.xpath("//label[text()='Account Number : ']/../../td[2]/label")).getText().equals(Postvalid_billing.account_no)){
				//report.writeTorun(runlog, "Account Number in MDM - "+PostActivationCheckInBilling.account_no);
			}else{
				report.writeTorun(runlog, "Another Account Number seen in MDM");
				checkCounter++;
				conditionCounter++;
			}

		} catch(NoSuchElementException e1){
			report.writeTorun(runlog, "Exception occured while checking MSISDN in MDM.");
		}
		driver.findElement(By.xpath("//label[text()='Executed Date : ']")).click();
		report.takeScreenShotMethod(driver, post_path.concat("\\Post_Activation_Validation"), "Post_Activation_Check_In_MDM_MSISDN");

		driver.close();
		return conditionCounter;
	}

		}


