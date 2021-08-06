package IUC_MNP;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;






public class ConsumerProfileCreation {
	
	//static int cellId = 2;
	static int row;
/*	static int cellId=ConsumerActivationMain.cellId;
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;*/
	
	//static String Actipre;
	static String runlog;
	static String reportfile;
	static CommonUtilitiesWriter report;
	static int conditionCounter=0;
	static String Postpre;
	
	public ConsumerProfileCreation(String runlog,String reportfile,String Postpre,CommonUtilitiesWriter report)
	{
		ConsumerProfileCreation.runlog=runlog;
		ConsumerProfileCreation.reportfile=reportfile;
		ConsumerProfileCreation.Postpre=Postpre;
		ConsumerProfileCreation.report=report;	
	}
	
	public int acti(int cellId) throws IOException, InterruptedException
	
	{
		
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
		
		report.writeToFile(reportfile, System.lineSeparator()+"Profile Creation in Siebel"+System.lineSeparator()+"--------------------------");
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tProfile Creation in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_IUC_MNP.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
				
		HSSFWorkbook testWorkBook = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWorkBook.getSheet("Application_Information");
		
		HSSFRow row1 = testSheet.getRow(1);
		String siebelUrl = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(2);
		String xUserName = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(3);
		String xPassword = row3.getCell(2).getStringCellValue();
		System.out.println("Application info read");	
		
		// Customer Profile Creation Page
		
		row=0;
		testSheet = testWorkBook.getSheet("Profile_Creation");
		String xIdType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCategory = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xOccupation = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDateFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIdExpiry = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIssueDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIssuePlace = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xTitle = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xFirstName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xLastName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBirthDateFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBirthDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xLanguagePreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xContactPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMobile = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xEmail = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xNationality = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xGender = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAddress = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xZipCode = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPoBox = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		report.writeTorun(runlog,"\nRead Data from Excel Successful");
		//System.out.println("\nRead Data from Excel Successful");
		inputStream.close();

		//Maximize
		driver.manage().window().maximize();
		driver.get(siebelUrl);
		
		Thread.sleep(4000);
		report.writeTorun(runlog,"URL invoked");
			
		// Login Page
		
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();
		
		report.writeTorun(runlog,"Clicked on Login");
		//System.out.println("Clicked on Login");
		
		//Click Customer Tab
		Thread.sleep(20000);
		driver.findElement(By.linkText("Customer")).click();
		report.writeTorun(runlog,"Clicked on Customer Tab");
		
		//Click Express Activation
		Thread.sleep(5000);
		WebElement select = driver.findElement(By.id("j_s_sctrl_tabView"));
	    List<WebElement> options = select.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	        if("Express Activation".equals(option.getText())) {
	        	option.click();
	        	report.writeTorun(runlog,"Clicked on Express Activation");
	        	break;
	        }
	    } 
		
	    
	    //Click New
	    Thread.sleep(10000);
		driver.findElement(By.xpath("//button[text()='New']")).click();
	    report.writeTorun(runlog,"Clicked on New");
		
	    //Fill Details
	    Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[7]/div/input")).sendKeys("Mobily ");
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[6]/td[7]/div/input")).sendKeys(xIdNo);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[7]/td[7]/div/input")).sendKeys(xCategory);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[8]/td[7]/div/input")).sendKeys(xOccupation);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[10]/div/input")).sendKeys(xDateFormat);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[7]/td[9]/div/input")).sendKeys(xIdExpiry);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[9]/td[9]/div/input")).sendKeys(xIssueDate);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[10]/td[7]/div/input")).sendKeys(xIssuePlace);
	    Thread.sleep(2000);
	    
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[3]/div/input")).sendKeys(xTitle);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[7]/td[3]/div/input")).sendKeys(xFirstName);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[9]/td[3]/div/input")).sendKeys(xLastName);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[11]/td[3]/div/input")).sendKeys(xBirthDateFormat);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[13]/td[3]/div/input")).sendKeys(xBirthDate);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[16]/td[3]/div/input")).clear();
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[16]/td[3]/div/input")).sendKeys(xLanguagePreference);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[5]/div/input")).sendKeys(xContactPreference);
	    driver.findElement(By.xpath("//input[@aria-labelledby='EECCMobileNumber_Label']")).sendKeys(xMobile);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[9]/td[5]/div/input")).sendKeys(xEmail);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[14]/td[5]/div/input")).sendKeys(xNationality);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[16]/td[5]/div/input")).sendKeys(xGender);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[13]/td[7]/div/input")).sendKeys(xAddress);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[18]/td[5]/div/input")).clear();
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[18]/td[5]/div/input")).sendKeys(xZipCode);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[16]/td[7]/div/input")).sendKeys(xPoBox);
		driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[7]/div/input")).clear();
		driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[7]/div/input")).sendKeys(xIdType);
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[3]/div/input")).clear();
	    driver.findElement(By.xpath(".//*[@id='a_3']/div/table/tbody/tr[5]/td[3]/div/input")).sendKeys(xTitle);
	    driver.findElement(By.xpath("//button[text()='Save']")).click();
	    
	    report.writeTorun(runlog,"Clicked on Save");
	    
	    Thread.sleep(15000);
	    driver.findElement(By.linkText("Express Activation")).click();
	    
	    report.writeTorun(runlog,"Clicked on Express Activation");
	    
	    Thread.sleep(4000); 
	    driver.findElement(By.linkText("Customer")).click();
	    report.writeTorun(runlog,"Clicked on Customer");
	    
	    Thread.sleep(5000);
	    driver.findElement(By.xpath(".//*[@id='s_S_A2_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[3]/td[2]/table/tbody/tr/td/input")).sendKeys(xIdNo);
	    driver.findElement(By.xpath(".//*[@id='s_2_1_13_0_Ctrl']")).click(); // Click on Go
	    report.writeTorun(runlog,"Clicked on Go");
	    
	    Thread.sleep(10000);	
	    
	    driver.quit();
	    report.writeTorun(runlog,"Browser Closed");
	    report.writeTorun(runlog,"Customer Successfully Created from Siebel!\n\n");
	    //report.writeToFile(reportfile,"Customer profile Successfully Created from Siebel!\n\n");
	    //System.out.println("Customer Successfully Created from Siebel!\n\n");
	    
	    PostProfileValidation_MDM profileCheckInMdmObj = new PostProfileValidation_MDM(runlog,reportfile,Postpre,report);
		int check = profileCheckInMdmObj.ProfileValidation_MDM(cellId);

		return check;
	    
	} // method
}