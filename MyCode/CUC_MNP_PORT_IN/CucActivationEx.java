package CUC_MNP_PORT_IN;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import CUC_MNP_PORT_IN.CommonUtilitiesWriter;



public class CucActivationEx {
	
	static Statement stmt;
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String Actipre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	static String device;
	static String  orderNumber;
	
	public CucActivationEx(String runlog,String reportfile,String Actipre,CommonUtilitiesWriter report)
	{
		CucActivationEx.runlog=runlog;
		CucActivationEx.reportfile=reportfile;
		CucActivationEx.Actipre=Actipre;
		CucActivationEx.report=report;
		
	}
	
	public int CUCAct(int cellId,String device) throws InterruptedException, IOException 
	{
		// TODO Auto-generated method stub
		
		WebDriver driver = null;
		String browser = "Chrome"; 
		conditionCounter=0;
	
		if(device.equalsIgnoreCase("Skip"))
		{
			report.writeToFile(reportfile, System.lineSeparator()+"CUC Activation Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else
		{
			report.writeToFile(reportfile, System.lineSeparator()+"CUC Activation with device Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation with device Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		}

		
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}
		else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		
		/*report.writeToFile(reportfile, System.lineSeparator()+"Initiate CUC Activation in Siebel"+System.lineSeparator()+"---------------------------------");
		//conditionCounter = 0;
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tInitiate CUC Activation in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		

*/
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC_MNP.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
		
		HSSFWorkbook testWorkBook = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWorkBook.getSheet("Application_Information");
		
		// Login Page Data
		HSSFRow row1 = testSheet.getRow(1);
		String siebelUrl = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(2);
		String xUserName = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(3);
		String xPassword = row3.getCell(2).getStringCellValue();
		//System.out.println("Application info read");
		report.writeTorun(runlog, "Application info read");
				
		// Activation Page Data
		int row = 1;
		testSheet = testWorkBook.getSheet("Activation");
		
		String xCommid = testSheet.getRow(row).getCell(cellId).getStringCellValue();
		String xAccountNo = testSheet.getRow(2+row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(3+row).getCell(cellId).getStringCellValue();
		String xType = testSheet.getRow(4+row).getCell(cellId).getStringCellValue();
		String xSIM = testSheet.getRow(5+row).getCell(cellId).getStringCellValue();
		String xIMSI = testSheet.getRow(6+row).getCell(cellId).getStringCellValue();
		String xMSISDN = testSheet.getRow(7+row).getCell(cellId).getStringCellValue();
		String xConDur = testSheet.getRow(8+row).getCell(cellId).getStringCellValue();
		
		
		report.writeTorun(runlog,"Test data read from excel");
		inputStream.close();
		driver.manage().window().maximize();
		driver.get(siebelUrl);
		report.writeTorun(runlog, "URL invoked");

		CommonUtilitiesWriter cu = new CommonUtilitiesWriter();
		
				//Maximize
		
		// Login Page
		
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();
		report.writeTorun(runlog, "Clicked on go");
		Thread.sleep(20000);
		
		//Business Accounts Tab
		
		cu.selectOption(driver, "j_s_sctrl_tabScreen", "Business Accounts /");
		report.writeTorun(runlog,"Clicked on Business Accounts");
		Thread.sleep(5000);
		
		WebElement we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Go']")));
		driver.findElement(By.xpath(".//*[@id='s_S_A1_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[3]/td[2]/table/tbody/tr/td/input")).sendKeys(xCommid);
		we.click();
		we = null;
		report.writeTorun(runlog,"Clicked on Go");
		Thread.sleep(5000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='1Account_Number']/a")));
		String accountNumber = we.getText();
		we.click();
		Thread.sleep(5000);
		report.writeTorun(runlog,"Clicked on "+accountNumber);
		
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@name='Account Number']")));
		String accountNumber1 = we.getText();
		we.click();
		Thread.sleep(5000);
		report.writeTorun(runlog,"Clicked on "+accountNumber1);
		
		
		
		cu.selectOption(driver, "j_s_vctrl_div_tabScreen", "Mobily Number Portability");
		
		Thread.sleep(5000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='New']")));
		we.click();
		report.writeTorun(runlog,"Clicked on New");
		
		Thread.sleep(5000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Add Line - service']/../td[2]/a")));
		String orderNumber = we.getText();
		we.click();
		report.writeTorun(runlog,"Clicked on "+orderNumber);
		
		Thread.sleep(4000);
		cu.clickWE(driver, "//button[@title='Line Items:New']");
		
		Thread.sleep(7000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Save']")));
		driver.findElement(By.xpath("//td[@id='1EEMBL_Product_Selected_By_User']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_Product_Selected_By_User']")).sendKeys(xPackage);
		we.click();
		report.writeTorun(runlog,"Clicked on Save");
		
			Thread.sleep(10000);
		
		try{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert1 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert1 not present");
	    }		
		
		Thread.sleep(2000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Line Items']")));
		we.click();
		report.writeTorun(runlog,"Clicked on Line Items");
		Thread.sleep(5000);
		
		try{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert1 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert1 not present");
	    }		
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='s_2_1_112_0_Ctrl']")));
		Thread.sleep(2000);
			
		
	
		WebElement s=driver.findElement(By.xpath(".//*[@id='1EEMBL_SIM_Card']"));
		s.click();
		Thread.sleep(3000);
		s.click();
		Thread.sleep(3000);
	    WebElement sim=driver.findElement(By.xpath(".//*[@id='1_EEMBL_SIM_Card']"));
	   
	    sim.sendKeys(xSIM);
	    Thread.sleep(3000);
		
		report.writeTorun(runlog,"SIM passed");
		
		Thread.sleep(2000);
		WebElement m=driver.findElement(By.xpath(".//*[@id='1EEMBL_MSISDN']"));
		m.click();
		Thread.sleep(3000);
		m.click();
		Thread.sleep(3000);
		WebElement msisdn=driver.findElement(By.xpath(".//*[@id='1_EEMBL_MSISDN']"));
		
		
		msisdn.sendKeys(xMSISDN);
		
		report.writeTorun(runlog,"MSISDN passed");
		Thread.sleep(2000);
		
		if(device.equalsIgnoreCase("Skip"))
		{
			//TBD
			
		}
		else
		{
		
		WebElement wb=driver.findElement(By.xpath(".//*[@id='1EEMBL_IMEI']"));
		wb.click();
		Thread.sleep(3000);
		wb.click();
		Thread.sleep(3000);
		WebElement imei=driver.findElement(By.xpath(".//*[@id='1EEMBL_IMEI']"));
		imei.sendKeys(device);
		}
				
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Contract_Duration_Label']")).sendKeys(xConDur);
		driver.findElement(	By.xpath("//input[@aria-labelledby='EEMBL_Sales_PF_Number_Label']")).sendKeys(xUserName);
		cu.clickWE(driver, "//input[@aria-labelledby='Available_Date_Label']/../img");
		Thread.sleep(2000);

        cu.clickWE(driver, "//button[text()='Now']");
        Thread.sleep(2000);
        cu.clickWE(driver, "//button[text()='Done']");
        Thread.sleep(2000);


		
		driver.findElement(By.xpath(".//*[@id='s_2_1_121_0_icon']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Now']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[text()='Done']")).click();
		
		//String orderNumber1 = driver.findElement(By.xpath(".//*[@id='a_2']/div/table/tbody/tr[3]/td[3]/div/input")).getText();
		String orderNumber1=driver.findElement(By.className("mceGridField")).getText();
		we.click();
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("CUC_POSP");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow r = sheet.createRow((short) 1);
		r.createCell(0).setCellValue(orderNumber);
		
		//String File = "C:\\Automated_Execution\\Input\\DB_VALIDATION.xls";
		String File =Actipre.concat("\\Oreder.xls");
		FileOutputStream fileOut = new FileOutputStream(File, false);
		workbook.write(fileOut);
		fileOut.close();
		
		
		
		report.writeTorun(runlog,"Clicked on Submit Approval");
		report.writeTorun(runlog,"Order Number is "+ orderNumber);
		report.writeToFile(reportfile,"Order Number is "+ orderNumber);
		cu.clickWE(driver, "//a[text()='Orders']");
		Thread.sleep(3000);
		
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Go']")));
		try
		{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert1 Accepted");
	    }
		catch(NoAlertPresentException e)
		{
	    	report.writeTorun(runlog,"Alert1 not present");
	    }		
		driver.findElement(By.xpath("//input[@aria-labelledby='Order_Number_Label']")).sendKeys(orderNumber);
		we.click();
		Thread.sleep(6000);
		report.writeTorun(runlog,"Clicked on Go");
		
		cu.selectOption(driver, "s_vis_div", "All Sales Order - Supervisor");
		Thread.sleep(3000);
		//Select drop down
		
		/*WebElement select1 = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.id(selectTagID)));
	    List<WebElement> options1 = select1.findElements(By.tagName("option"));
	    for (WebElement option : options1) {
	        if(option.getText().contains(opt)) {
	        	option.click();
	        	System.out.println("Clicked on "+opt);
	        	break;*/
		
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Assign Order']")));
		WebElement w=driver.findElement(By.xpath("//input[@aria-labelledby='Assign_Employee_Label']"));
		w.click();
		Thread.sleep(2000);
		w.sendKeys(xUserName);
		we.click();
		
		Thread.sleep(20000);
		
		try
		{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert1 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert1 not present");
	    }		

		report.writeTorun(runlog,"Clicked on Assign Order");
		
		driver.findElement(By.xpath("//a[text()='Home']")).click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@id='1_Action']")).click();
		WebElement ww=driver.findElement(By.xpath("//input[@id='1_Action']"));
		ww.click();
		Thread.sleep(2000);
		ww.sendKeys("Approved");
		driver.findElement(By.xpath("//button[text()='Save']")).click();
		Thread.sleep(3000);

		try
		{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e)
		{
	    	report.writeTorun(runlog,"Alert2 not present");
	    }	
		driver.findElement(By.xpath("//button[text()='Save']")).click();
		
		try
		{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert3 Accepted");
	    }
		catch(NoAlertPresentException e)
		{
	    	report.writeTorun(runlog,"Alert3 not present");
	    }		
		

		Thread.sleep(2000);
		report.writeToFile(reportfile, "Activation completed successfully");
		return 0;
	
	}

}
