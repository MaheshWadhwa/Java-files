package PostPaidREVAMP_CUC_WithCash;
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


//import CUC.CommonUtilities;


public class RevampCUCPostPaidActivation {
	
	/*static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=CucMain.runlog;
	static String reportfile=CucMain.reportfile;
	static String Acti_path=CucMain.Actipre;
	static int row;
	static int cellId=CucMain.cellId;
	static int conditionCounter=0;
	static String device=CucMain.device;*/
	//static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
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
	
	public RevampCUCPostPaidActivation(String runlog,String reportfile,String Actipre,CommonUtilitiesWriter report)
	{
		RevampCUCPostPaidActivation.runlog=runlog;
		RevampCUCPostPaidActivation.reportfile=reportfile;
		RevampCUCPostPaidActivation.Actipre=Actipre;
		RevampCUCPostPaidActivation.report=report;
		
	}
	
	public int CUCAct(int cellId,String device) throws InterruptedException, IOException 
	{
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		
		WebDriver driver = null;
		String browser = "Chrome"; 
		conditionCounter=0;
	
		if(device.equalsIgnoreCase("Skip"))
		{
			report.writeToFile(reportfile, System.lineSeparator()+"PostPaidRevampCUC Activation Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else
		{
			report.writeToFile(reportfile, System.lineSeparator()+"PostPaidRevampCUC Activation with device Started in Siebel"+System.lineSeparator()+"--------------------------");
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
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevampWithCash.xls");
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
		System.out.println(siebelUrl);
				
		// Activation Page Data
		int row = 1;
		testSheet = testWorkBook.getSheet("Activation");
		
		//String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		/*String xSIM = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMSISDN = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDeliveryPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPaymentType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIMEI= testSheet.getRow(++row).getCell(cellId).getStringCellValue();*/
		
		
		
		String xIdNo = testSheet.getRow(row).getCell(cellId).getStringCellValue();
		String xAccountNo = testSheet.getRow(2+row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(3+row).getCell(cellId).getStringCellValue();
		String xType = testSheet.getRow(4+row).getCell(cellId).getStringCellValue();
		String xSIM = testSheet.getRow(5+row).getCell(cellId).getStringCellValue();
		String xIMSI = testSheet.getRow(6+row).getCell(cellId).getStringCellValue();
		String xMSISDN = testSheet.getRow(7+row).getCell(cellId).getStringCellValue();
		String xConDur = testSheet.getRow(8+row).getCell(cellId).getStringCellValue();
		String xIMEI= testSheet.getRow(9+row).getCell(cellId).getStringCellValue();
		String xMode = testSheet.getRow(10+row).getCell(cellId).getStringCellValue();
		String xBundleList = testSheet.getRow(11+row).getCell(cellId).getStringCellValue();
		String xSlab = testSheet.getRow(12+row).getCell(cellId).getStringCellValue();
		String xAttachmentType = testSheet.getRow(13+row).getCell(cellId).getStringCellValue();
		String xBrandValue = testSheet.getRow(14+row).getCell(cellId).getStringCellValue();
		String xInstallment = testSheet.getRow(15+row).getCell(cellId).getStringCellValue();
		String xDeviceModel = testSheet.getRow(16+row).getCell(cellId).getStringCellValue();
		
		//System.out.println("Test data read from excel");
	
		report.writeTorun(runlog,"Test data read from excel");
		
		inputStream.close();
		
		driver.manage().window().maximize();
		driver.get(siebelUrl);
		Thread.sleep(3000);
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
		driver.findElement(By.xpath("//input[@aria-labelledby='Full_Name_Label']")).sendKeys(xIdNo);
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
		
		
		cu.selectOption(driver, "j_s_vctrl_div_tabScreen", "Sales Order - Corporate");
		
		Thread.sleep(5000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='New']")));
		we.click();
		report.writeTorun(runlog,"Clicked on New");
		
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }	
		
		Thread.sleep(5000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='Add Line - service']/../td[2]/a")));
		String orderNumber = we.getText();
		we.click();
		report.writeTorun(runlog,"Clicked on "+orderNumber);
		
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }	
		
		Thread.sleep(4000);
		cu.clickWE(driver, "//button[@title='Line Items:New']");
		
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }	
		
		Thread.sleep(7000);
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Save']")));
		try{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert1 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert1 not present");
	    }
		
		driver.findElement(By.xpath("//td[@id='1EEMBL_Product_Selected_By_User']")).click();
		Thread.sleep(1000);
		try{
	    	Alert alert1 = driver.switchTo().alert();
	    	alert1.accept();
	    	report.writeTorun(runlog,"Alert1 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert1 not present");
	    }
		
		driver.findElement(By.xpath("//input[@id='1_EEMBL_Product_Selected_By_User']")).sendKeys(xPackage);	
		Thread.sleep(3000);
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }	
		
			Thread.sleep(5000);
	
		we.click();
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }	
		
			Thread.sleep(5000);
		report.writeTorun(runlog,"Clicked on Save");

	
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }		
		    /*finally {
		        if (alert1 != true) {
			    	Alert alert1 = driver.switchTo().alert();
		        	alert1.accept();
		          //  driver.findElement(By.xpath("")).click();
		        }
		    }*/
		
		//GOTO ORDERDETAILS
		driver.findElement(By.xpath("//a[text()='Orders Details']")).click();
	    report.writeTorun(runlog,"Clicked on ORDERDETAILS TAB");		
	    Thread.sleep(10000); 
	    
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }
		
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }
		
		Thread.sleep(10000);	
		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Line Items']")));
		we.click();
		report.writeTorun(runlog,"Clicked on Line Items");
		Thread.sleep(2000);
		
		try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	report.writeTorun(runlog,"Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	report.writeTorun(runlog,"Alert2 not present");
	    }			
		Thread.sleep(5000);
		
/*		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='s_2_1_112_0_Ctrl']")));
		//driver.findElement(By.xpath(".//*[@id='1EEMBL_SIM_Card']")).click();
*/		
				//Click Customize button 	    
				driver.findElement(By.xpath("//button[@data-display='Customize']")).click();
			    report.writeTorun(runlog,"Clicked on Customize Button");		
			    Thread.sleep(10000);
			    
			    //CLICK ON MODE LIST
			    String a="//div[text()='";
			    String b="']/../../div/input";
			    String c= xMode;
			    String d=a.concat(c).concat(b);			    
			    driver.findElement(By.xpath(d)).click();			    
			  // driver.findElement(By.xpath("//div[text()='Mode Supreme']/../../div/input")).click();
			    report.writeTorun(runlog,"Clicked on Mode Advanced Button");		
			    Thread.sleep(10000);
			    
			    //Click on Customize Image
			    driver.findElement(By.xpath("//img[@title='Customize']")).click();
			    report.writeTorun(runlog,"Clicked on Customize Image");		
			    Thread.sleep(10000);
			    
			    //Select Bundle Custom - Supreme from Bundle List			    
		  		driver.findElement(By.xpath("//img[@title='Customize']/../../../../div/select")).click();
		  		Thread.sleep(2000);
			    String A="//option[text()='";
			    String B="']";
			    String C= xBundleList;
			    String D=A.concat(C).concat(B);			    
			    driver.findElement(By.xpath(D)).click();  
		  	//	driver.findElement(By.xpath("//option[text()='Bundle Custom - Supreme']")).click();
			    report.writeTorun(runlog,"Select Bundle Custom - Supreme from Bundle List");		
			    Thread.sleep(5000);
			    
/*			    //Slect Slab Value
		  		driver.findElement(By.xpath("//option[text()='Default']/..")).click();
		  		Thread.sleep(2000);
			    String e="//option[text()='";
			    String f="']";
			    String g= xSlab;
			    String h=e.concat(g).concat(f);			    
			    driver.findElement(By.xpath(h)).click();			    
		  	//	driver.findElement(By.xpath("//option[text()='Default']")).click();
			    report.writeTorun(runlog,"Select Slab Value - Slab List");		
			    Thread.sleep(5000);	*/
			    
			    //Slect Slab Value
				// driver.findElement(By.xpath("//option[text()='Default']/..")).click();
			  		Thread.sleep(2000);
				    String e="//option[text()='";
				    String f="']/..";
				    String g= xSlab;
				    String h=e.concat(g).concat(f);			    
				    driver.findElement(By.xpath(h)).click();			    
			  	//	driver.findElement(By.xpath("//option[text()='Default']")).click();
				    String ee="//option[text()='";
				    String ff="']";
				    String gg= xSlab;
				    String hh=e.concat(gg).concat(ff);			    
				    driver.findElement(By.xpath(hh)).click();
				    report.writeTorun(runlog,"Select Slab Value - Slab List");		
				    Thread.sleep(5000);
			    
				    
			    // Select Device with Cash from List Box
		  		driver.findElement(By.xpath("//option[text()='Device with Cash']/..")).click();
		  		Thread.sleep(3000);
		  		driver.findElement(By.xpath("//option[text()='Device with Cash']")).click();
		  		Thread.sleep(5000);
		  		
		  		// Give Quantity value
		  		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("1");
		  		Thread.sleep(3000);
		  		
		  		//Click on Add Item Button
		  		driver.findElement(By.xpath("//button[text()='Add Item']")).click();
		  		Thread.sleep(10000);
		  		
			    //Click on Customize Image
			    driver.findElement(By.xpath("//a[text()='Device with Cash']/../../../div/div/span/a/img[@title='Customize']")).click();
			    report.writeTorun(runlog,"Clicked on Customize Image");		
			    Thread.sleep(10000);
			    
/*			     //Select Brand value from Brand List 
		  		driver.findElement(By.xpath("//option[text()='HTC']/..")).click();
		  		Thread.sleep(2000);
		  		driver.findElement(By.xpath("//option[text()='HTC']")).click();
			    report.writeTorun(runlog,"Select Brand value from Brand List");		
			    Thread.sleep(5000);
			    
			    //Select Contract Value from Contract List 
		  		driver.findElement(By.xpath("//option[text()='12']/..")).click();
		  		Thread.sleep(2000);
		  		driver.findElement(By.xpath("//option[text()='12']")).click();
			    report.writeTorun(runlog,"Select Contract Value from Contract List ");		
			    Thread.sleep(5000);
		  		
			    //Select Device Model Value from Device Model List 
		  		driver.findElement(By.xpath("//option[text()='HTC one M8']/..")).click();
		  		Thread.sleep(2000);
		  		driver.findElement(By.xpath("//option[text()='HTC one M8']")).click();
			    report.writeTorun(runlog,"Select Contract Value from Contract List ");		
			    Thread.sleep(5000);*/
			    
			     //Select Brand value from Brand List 
			  	//	driver.findElement(By.xpath("//option[text()='HTC']/..")).click();
			  		Thread.sleep(2000);
			  		String A1="//option[text()='";
					String B1="']/..";
					String C1= xBrandValue;
					String D1=A1.concat(C1).concat(B1);			    
					driver.findElement(By.xpath(D1)).click();  
			  	//	driver.findElement(By.xpath("//option[text()='HTC']")).click();
			  		String A11="//option[text()='";
					String B11="']";
					String C11= xBrandValue;
					String D11=A11.concat(C11).concat(B11);	
				    report.writeTorun(runlog,"Select Brand value from Brand List");		
				    Thread.sleep(5000);
				    
				    //Select Contract Value from Contract List 
			  //	driver.findElement(By.xpath("//option[text()='12']/..")).click();
			  		Thread.sleep(2000);
			  		String AA1="//option[text()='";
					String BB1="']/..";
					String CC1= xInstallment;
					String DD1=AA1.concat(CC1).concat(BB1);			    
					driver.findElement(By.xpath(DD1)).click(); 
			  	//	driver.findElement(By.xpath("//option[text()='12']")).click();
			  		String AAA1="//option[text()='";
					String BBB1="']";
					String CCC1= xInstallment;
					String DDD1=AAA1.concat(CCC1).concat(BBB1);			    
					driver.findElement(By.xpath(DD1)).click();
				    report.writeTorun(runlog,"Select Contract Value from Contract List ");		
				    Thread.sleep(5000);
			  		
				    //Select Device Model Value from Device Model List 
			  	//	driver.findElement(By.xpath("//option[text()='HTC one M8']/..")).click();
			  		Thread.sleep(2000);
			  		String aa1="//option[text()='";
					String bb1="']/..";
					String cc1= xDeviceModel;
					String dd1=aa1.concat(cc1).concat(bb1);			    
					driver.findElement(By.xpath(dd1)).click();
			  	//	driver.findElement(By.xpath("//option[text()='HTC one M8']")).click();
			  		String aaa1="//option[text()='";
					String bbb1="']";
					String ccc1= xDeviceModel;
					String ddd1=aa1.concat(ccc1).concat(bbb1);			    
					driver.findElement(By.xpath(ddd1)).click();
				    report.writeTorun(runlog,"Select Device Model from DeviceModel List ");		
				    Thread.sleep(5000);
			    
			    // Click on Done Button
		  		driver.findElement(By.xpath("//button[text()='Done']")).click();
			    report.writeTorun(runlog,"Click on Done Button ");		
			    Thread.sleep(40000);
			    
			    //Click On Attachment
				cu.selectOption(driver, "j_s_vctrl_div_tabScreen", "Attachments");				
				Thread.sleep(10000);			
					
			    // Click on NewURL Button
		  		driver.findElement(By.xpath("//button[text()='New URL']")).click();
			    report.writeTorun(runlog,"Click on NewURL Button ");		
			    Thread.sleep(10000);
			    
			    // Click on NewURL Button
		  		driver.findElement(By.xpath("//input[@aria-labelledby='s_SweUrl_Label']")).sendKeys("QATEST");			   
			    Thread.sleep(5000);			    
				
			    // Click on Add Button
		  		driver.findElement(By.xpath("//button[text()='Add']")).click();
			    report.writeTorun(runlog,"Click on Add Button ");		
			    Thread.sleep(10000);
			    
				//Attachment Type
				driver.findElement(By.id("1EEMBL_Attachment_Type")).click();
				driver.findElement(By.xpath("//td[@id='1EEMBL_Attachment_Type']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='EEMBL_Attachment_Type']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='EEMBL_Attachment_Type']")).sendKeys(xAttachmentType);
				Thread.sleep(10000);
				
				//GOTO LineItems Tab
				driver.findElement(By.xpath("//a[text()='Line Items']")).click();
			    report.writeTorun(runlog,"Clicked on LineItems TAB");		
			    Thread.sleep(10000); 
			    
			    
				Thread.sleep(2000);
				we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Line Items']")));
				we.click();
				report.writeTorun(runlog,"Clicked on Line Items");
				Thread.sleep(10000);
			    			    
			    //Give Sales PF Number
		  		driver.findElement(By.xpath("//input[@aria-label='Sales PF Number']")).sendKeys("PFTEST");
			    report.writeTorun(runlog,"Given Sales PF Number");		
			    Thread.sleep(5000);
			    
			    //Give Contact Duration Value
		  		driver.findElement(By.xpath("//input[@aria-label='Contract Duration']")).sendKeys("6");
			    report.writeTorun(runlog,"Given Contact Duration Value");		
			    Thread.sleep(5000);	 
			    
			
				//SIM
				driver.findElement(By.id("1EEMBL_SIM_Card")).click();
				driver.findElement(By.xpath("//td[@id='1EEMBL_SIM_Card']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='EEMBL_SIM_Card']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='EEMBL_SIM_Card']")).sendKeys(xSIM);
				Thread.sleep(3000);
				
				//MSISDN
				driver.findElement(By.id("1EEMBL_MSISDN")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='EEMBL_MSISDN']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//input[@name='EEMBL_MSISDN']")).sendKeys(xMSISDN);
				Thread.sleep(5000);				
				
			    //Click on Expand Button
		  		driver.findElement(By.xpath("//button[text()='Expand']")).click();
			    report.writeTorun(runlog,"Clicked on Expand Button");		
			    Thread.sleep(5000);
			    
			    //Click on Expand Button
		  		driver.findElement(By.xpath("//button[text()='Expand']")).click();
			    report.writeTorun(runlog,"Clicked on Expand Button");		
			    Thread.sleep(5000);
			    
			    //Click on Collops Image
		  		driver.findElement(By.xpath("//td[@id='2Product']/div/div")).click();
			    report.writeTorun(runlog,"Clicked on Collaps Image");		
			    Thread.sleep(10000);	    
			    
				try{
			    	Alert alert1 = driver.switchTo().alert();
			    	alert1.accept();
			    	report.writeTorun(runlog,"Alert1 Accepted");
			    }
				catch(NoAlertPresentException e6){
			    	report.writeTorun(runlog,"Alert1 not present");
			    }
				
				Thread.sleep(5000);
				
				try{
			    	Alert alert1 = driver.switchTo().alert();
			    	alert1.accept();
			    	report.writeTorun(runlog,"Alert1 Accepted");
			    }
				catch(NoAlertPresentException e8){
			    	report.writeTorun(runlog,"Alert1 not present");
			    }
				
				Thread.sleep(5000);
				
				try{
			    	Alert alert1 = driver.switchTo().alert();
			    	alert1.accept();
			    	report.writeTorun(runlog,"Alert1 Accepted");
			    }
				catch(NoAlertPresentException e8){
			    	report.writeTorun(runlog,"Alert1 not present");
			    }
				
				Thread.sleep(5000);
			    //IMEI 
				driver.findElement(By.id("13EEMBL_IMEI")).click();
				driver.findElement(By.xpath("//td[@id='13EEMBL_IMEI']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//input[@id='13_EEMBL_IMEI']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//input[@id='13_EEMBL_IMEI']")).sendKeys(xIMEI);
				Thread.sleep(5000);
				
				String orderNumber1=driver.findElement(By.xpath("//input[@aria-labelledby='OrderNumber_Label']")).getText();
				
						
				//Click on Sumbit Approval
				driver.findElement(By.xpath("//button[text()='Submit Approval']")).click();
				Thread.sleep(30000);
				
				try{
			    	Alert alert2 = driver.switchTo().alert();
			    	alert2.accept();
			    	report.writeTorun(runlog,"Alert2 Accepted");
			    }
				catch(NoAlertPresentException e5){
			    	report.writeTorun(runlog,"Alert2 not present");
			    }	
				Thread.sleep(20000);
				
			
		
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
		catch(NoAlertPresentException e4)
		{
	    	report.writeTorun(runlog,"Alert1 not present");
	    }		
		driver.findElement(By.xpath("//input[@aria-labelledby='Order_Number_Label']")).sendKeys(orderNumber);
		we.click();
		Thread.sleep(6000);
		report.writeTorun(runlog,"Clicked on Go");
		
		//cu.selectOption(driver, "s_vis_div", "All Sales Order - Supervisor");
		
		cu.selectDD(driver, "//select[@name='s_vis_div']", "All Sales Order - Supervisor");
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
		catch(NoAlertPresentException e3){
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
		catch(NoAlertPresentException e1)
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
		catch(NoAlertPresentException e2)
		{
	    	report.writeTorun(runlog,"Alert3 not present");
	    }		
		

		Thread.sleep(2000);
		report.writeToFile(reportfile, "Activation completed successfully");
		return 0;
	
	}

}
