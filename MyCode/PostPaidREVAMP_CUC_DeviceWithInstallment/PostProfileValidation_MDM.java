package PostPaidREVAMP_CUC_DeviceWithInstallment;
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


public class PostProfileValidation_MDM {
	
	static int row=0;
/*	static int cellId=ConsumerActivationMain.cellId;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;
	static int conditionCounter=0;
	static String post_path=ConsumerActivationMain.Postpre;*/
	static int checkCounter;
	static String runlog;
	static String reportfile;
	static CommonUtilitiesWriter report;
	static int conditionCounter=0;
	static String Postpre;

	public PostProfileValidation_MDM(String runlog,String reportfile,String Postpre,CommonUtilitiesWriter report)
	{
		PostProfileValidation_MDM.runlog=runlog;
		PostProfileValidation_MDM.reportfile=reportfile;
		PostProfileValidation_MDM.Postpre=Postpre;
		PostProfileValidation_MDM.report=report;	
	}
	
	public  int ProfileValidation_MDM(int cellId) throws InterruptedException, IOException {

		report.writeToFile(reportfile, System.lineSeparator()+"Validate Profile in MDM"+System.lineSeparator()+"--------------------------");
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tProfile validation in MDM"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");


		WebDriver driver = null;
		String browser = "Chrome";

		if (browser=="Firefox") 
		{
			driver=new FirefoxDriver();
		}
		else if (browser=="IE") 
		{
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}
		else if (browser=="Chrome")
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		//Read data from Excel 

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevamp_DeviceWithInstallment.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWork = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWork.getSheet("Application_information");
		HSSFRow row1 = testSheet.getRow(7);
		String URL = row1.getCell(2).getStringCellValue();


		driver.get(URL);
		Thread.sleep(10000);
		int row=0;
		testSheet = testWork.getSheet("Profile_Creation");
		String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		//System.out.println("Read Data from Excel Successful");
		//report.writeTorun(runlog, "Read Data from Excel Successful");
		//MDM URl launch

		driver.manage().window().maximize();

		driver.get(URL);
		Thread.sleep(3000);
		//System.out.println("URL invoked");
		report.writeTorun(runlog,"URL invoked");

		//CLICK ON GET ALL PARTY LINK

		driver.findElement(By.xpath(".//*[@id='form1:j_id_e']/span[2]")).click();
		Thread.sleep(5000);

		//SELECT IDENTIFICATION TYPE
		driver.findElement(By.xpath(".//*[@id='form2:input-identificationType']/div[2]/span")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='form2:input-identificationType_panel']/ul/li[23]")).click();		
		Thread.sleep(2000);
		report.writeTorun(runlog,"Select Identification type as GCC ID :");

		//GIVE Identification Number 
		driver.findElement(By.xpath(".//*[@id='form2:input-identificationNumber']")).sendKeys(xIdNo);		
		Thread.sleep(2000);
		report.writeTorun(runlog,"To Give Identification Number as ProfileID :" +xIdNo);

		//CLICK ON SEARCH BUTTON
		driver.findElement(By.xpath(".//*[@id='form2:j_id_32']")).click();
		Thread.sleep(4000);		
		report.writeTorun(runlog,"Suceessfuly search with ProfileID :"+xIdNo);		

		//TO VALIDATE PARTY ID		
		try{
			if((driver.findElement(By.xpath("//label[text()='Result Code : ']/../../td[4]/label")).getText().equalsIgnoreCase("No Identifiers found for that party")) && (driver.findElement(By.xpath("//label[text()='Result Code : ']/../../td[2]/label")).getText().equalsIgnoreCase("FATAL"))){
				report.writeTorun(runlog, "Profile not seen in MDM...");
				checkCounter++;
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			try{
				if(driver.findElement(By.xpath("//label[text()='Party Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Active")){
					driver.findElement(By.xpath("//label[text()='Party Status Value : ']/../../td[4]/label")).click();
					report.writeTorun(runlog, "Profile seen in MDM..");
				}else{
					report.writeToFile(runlog, "Profile not Active in MDM...");
					checkCounter++;
					conditionCounter++;
				}
			} catch(NoSuchElementException e1){
				report.writeTorun(runlog, "Exception occured while checking Profile in MDM.");
			}
		}
		Thread.sleep(2000);
		report.takeScreenShotMethod(driver, Postpre.concat("\\Profile_Creation"), "Profile Validation in MDM");
		driver.close();
		return conditionCounter;
}
}


