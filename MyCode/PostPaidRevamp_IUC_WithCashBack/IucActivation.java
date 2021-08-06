package PostPaidRevamp_IUC_WithCashBack;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

//import pageFactory.SiebelOUILoginPF;


public class IucActivation {
	
	static String runlog = "";
	static String reportfile = "";
	static String Actipre = "";
	static CommonUtilitiesWriter report = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public IucActivation(String runlog, String reportfile, String Actipre, CommonUtilitiesWriter report){
		IucActivation.runlog = runlog;
		IucActivation.reportfile = reportfile;
		IucActivation.Actipre = Actipre;
		IucActivation.report = report;
		
	} // end of constructor method

	public int iucActivate(int cellId) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		report.writeToFile(reportfile, System.lineSeparator()+"Initiate Postpaid Revamp IUC Activation in Siebel"+System.lineSeparator()+"---------------------------------");
		conditionCounter = 0;
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tInitiate Postpaid Revamp IUC Activation in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
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
		int row = 0;

		
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Revamp_IUC _WithCashBack.xls");
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
		
		testSheet = testWorkBook.getSheet("Activation");
		row = 0;
		// Activation Information
		String xCDId = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		@SuppressWarnings("unused")
		String xPackageType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPaymentType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCommercialID = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPoReceivedDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		@SuppressWarnings("unused")
		String xImsi = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMsisdn = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMode = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBundle = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSlab = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDevice = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImei = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xInstalment = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDevicePayment= testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xQuantity = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xContract = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAttachmenttype1=testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAttachmenttype2=testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		testWorkBook.close();
		report.writeTorun(runlog,"Read Data from Excel Successful");
		inputStream.close();
		

		//CommonUtilitiesWriter cu = new CommonUtilitiesWriter(reportfile,runlog);

		driver.get(siebelUrl);
		report.writeTorun(runlog,"URL invoked");
		
		//Maximize
		driver.manage().window().maximize();
		
		Thread.sleep(10000);
		
		// Login Page
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();

		report.writeTorun(runlog,"Clicked on Login");

	
		Thread.sleep(10000);
		
		/*SiebelOUILoginPF login = new SiebelOUILoginPF(driver);

		driver.manage().window().maximize();

		login.loginSiebelOUI(xUserName, xPassword);

		Thread.sleep(15000);
		try{
			Alert alert0 = driver.switchTo().alert();
			alert0.accept();
			
			report.writeTorun(runlog,"Password Expire Alert Accepted");
		}catch(NoAlertPresentException e){
			
			report.writeTorun(runlog,"Password Expire Alert not present");
		}
		*/
		WebElement we = null;

		Thread.sleep(20000);
		report.clickWE(driver, "//a[text()='Customer']");
		//writer.println("Clicked on Customer Tab");
		report.writeTorun(runlog,"Clicked on Customer Tab");

		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Go']")));
		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_ID_Number_Label']")).sendKeys(xCDId);
		we.click();
		we = null;
		//writer.println("Clicked on Go");
		report.writeTorun(runlog,"Clicked on Go");
		
		Thread.sleep(10000);
		report.selectOption(driver, "j_s_sctrl_tabView", "Express Activation"); 

		Thread.sleep(5000);
		report.selectOption(driver, "j_s_vctrl_div_tabScreen", "IUC Raaqi / SOHO Activation");

		Thread.sleep(5000);
		report.clickWE(driver, "//button[@title='Orders:New']");

		Thread.sleep(10000);

		String orderNumber = driver.findElement(By.xpath("//a[@name='Order Number']")).getText();
		//writer.println("Order Number is "+orderNumber);
		report.writeTorun(runlog,"Order Number is "+orderNumber);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet1");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow ro = sheet.createRow((short) 1);
		ro.createCell(0).setCellValue(orderNumber);
		
		//report.createFolder(datasetFolderPath, "\\Activation_From_Siebel");
		String File =Actipre.concat("\\Order.xls");
		 FileOutputStream fileOut = new FileOutputStream(File,false);
	    workbook.write(fileOut);
	    fileOut.close();
	    System.out.println("Activation File has been created successfully ");

		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_EA_Product_Label']")).sendKeys(xPackage);

		report.selectDD(driver, ".//*[@id='s_1_2_32_0_icon']", xPaymentType);

		//QATESTSURENUDP
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Commercial_ID_Label']")).sendKeys(xCommercialID);
		
		report.clickWE(driver, "//button[@title='Orders:Save']");

		Thread.sleep(5000);
	
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			//writer.println("Alert1 Accepted");
			report.writeTorun(runlog,"Alert1 Accepted");
		}catch(NoAlertPresentException e){
			//writer.println("Alert1 not present");
			report.writeTorun(runlog,"Alert1 not present");
		}

		String orderNoLink1 = "//a[text()='";
		String orderNoLink2 = "']";
		String orderNoLink = orderNoLink1.concat(orderNumber).concat(orderNoLink2);

		report.clickWE(driver, orderNoLink);
		Thread.sleep(5000);
				
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			//writer.println("Alert1 Accepted");
			report.writeTorun(runlog,"Alert1 Accepted");
		}catch(NoAlertPresentException e){
			//writer.println("Alert1 not present");
			report.writeTorun(runlog,"Alert1 not present");
		}
		Thread.sleep(10000);
		// Customizing package
		
		//Clicking on customize 
		driver.findElement(By.xpath("//button[@id='s_4_1_32_0_Ctrl']")).click();
		Thread.sleep(15000);	
		
		 //CLICK ON MODE LIST
	    String a="//div[text()='";
	    String b="']/../../div/input";
	    String c= xMode;
	    String d=a.concat(c).concat(b);			    
	    driver.findElement(By.xpath(d)).click();			    
	  // driver.findElement(By.xpath("//div[text()='Mode Advanced']/../../div/input")).click();
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
	    String C= xBundle;
	    String D=A.concat(C).concat(B);			    
	    driver.findElement(By.xpath(D)).click();  
  	//	driver.findElement(By.xpath("//option[text()='Bundle Custom - Supreme']")).click();
	    report.writeTorun(runlog,"Select Bundle Custom - Supreme from Bundle List");		
	    Thread.sleep(10000);	    
	    
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
		    Thread.sleep(10000);  
					    
		    // Click Device with Cashback Check box
	  		driver.findElement(By.xpath("//div[text()='Device with Cashback']/../../div/input")).click();
	  		Thread.sleep(5000);
	  		
		    //Click on Customize Image
		    driver.findElement(By.xpath("//a[text()='Device with Cashback']/../../../div/span/a/img[@title='Customize']")).click();
		    report.writeTorun(runlog,"Clicked on Customize Image");		
		    Thread.sleep(10000);		    
		
	    //Again Select Slab Value
		// driver.findElement(By.xpath("//option[text()='Default']/..")).click();
	  		Thread.sleep(2000);
		    String e1="//option[text()='";
		    String f1="']/..";
		    String g1= xSlab;
		    String h1=e1.concat(g1).concat(f1);			    
		    driver.findElement(By.xpath(h1)).click();			    
	  	//	driver.findElement(By.xpath("//option[text()='Default']")).click();
		    String ee1="//option[text()='";
		    String ff1="']";
		    String gg1= xSlab;
		    String hh1=e.concat(gg1).concat(ff1);			    
		    driver.findElement(By.xpath(hh1)).click();
		    report.writeTorun(runlog,"Select Slab Value - Slab List");		
		    Thread.sleep(10000);		

		    //Select Device Model Value from Device Model List 
	  	//	driver.findElement(By.xpath("//option[text()='HTC one M8']/..")).click();
	  		Thread.sleep(2000);
	  		String aa1="//option[text()='";
			String bb1="']/..";
			String cc1= xDevice;
			String dd1=aa1.concat(cc1).concat(bb1);			    
			driver.findElement(By.xpath(dd1)).click();
	  	//	driver.findElement(By.xpath("//option[text()='HTC one M8']")).click();
	  		String aaa1="//option[text()='";
			String bbb1="']";
			String ccc1= xDevice;
			String ddd1=aa1.concat(ccc1).concat(bbb1);			    
			driver.findElement(By.xpath(ddd1)).click();
		    report.writeTorun(runlog,"Select Device Model from DeviceModel List ");		
		    Thread.sleep(10000);
		    
		    //Select Contract Value from Contract List 
		  //driver.findElement(By.xpath("//option[text()='12']/..")).click();
			Thread.sleep(2000);
			String AA1="//option[text()='";
			String BB1="']/..";
			String CC1= xContract;
			String DD1=AA1.concat(CC1).concat(BB1);			    
			driver.findElement(By.xpath(DD1)).click(); 
			//	driver.findElement(By.xpath("//option[text()='12']")).click();
			String AAA1="//option[text()='";
			String BBB1="']";
			String CCC1= xContract;
			String DDD1=AAA1.concat(CCC1).concat(BBB1);			    
			driver.findElement(By.xpath(DD1)).click();
			report.writeTorun(runlog,"Select Contract Value from Contract List ");		
			Thread.sleep(10000);

			//Clicking on done		
			driver.findElement(By.xpath("//button[text()='Done']")).click();
			Thread.sleep(30000);
		
		
/*		//ExpandindgPackage
		
		driver.findElement(By.xpath("//div[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']")).click();
		Thread.sleep(10000);
		
		driver.findElement(By.xpath("//a[text()='Mode Supreme']/../..//div[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']")).click();
		Thread.sleep(10000);*/
		
	    //Click on Expand Button
	  		driver.findElement(By.xpath("//button[text()='Expand']")).click();
		    report.writeTorun(runlog,"Clicked on Expand Button");		
		    Thread.sleep(10000);
		    
		    //Click on Collops Image
	  		driver.findElement(By.xpath("//td[@id='2Product']/div/div")).click();
		    report.writeTorun(runlog,"Clicked on Collaps Image");		
		    Thread.sleep(10000);
		
		//IMEI
		driver.findElement(By.id("13EEMBL_IMEI")).click();
		driver.findElement(By.xpath("//td[@id='13EEMBL_IMEI']")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@id='13_EEMBL_IMEI']")).click();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//input[@id='13_EEMBL_IMEI']")).sendKeys(xImei);
		Thread.sleep(10000);
/*		//Collapse
		driver.findElement(By.xpath("//a[text()='Mode Supreme']/../..//div[@class='ui-icon ui-icon-triangle-1-e tree-plus treeclick']")).click();
		Thread.sleep(3000);*/
		
		//Attachments		
		report.selectOption(driver, "j_s_vctrl_div_tabScreen", "Attachments");
		Thread.sleep(10000);
		
		//New URL
		//URL1		
		driver.findElement(By.xpath("//button[@id='s_2_1_3_0_Ctrl']")).click();
		Thread.sleep(3000);
		
		WebElement u=driver.findElement(By.xpath("//input[@name='s_SweUrl_0']"));
		u.click();
		u.sendKeys("Test");//Name
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@id='s_3_1_22_0_Ctrl']")).click();//Add
		Thread.sleep(3000);
		
		//Attachment type		
		driver.findElement(By.xpath("//td[@id='1EEMBL_Attachment_Type']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_Attachment_Type']")).click();
		driver.findElement(By.xpath("//input[@id='1_EEMBL_Attachment_Type']")).sendKeys(xAttachmenttype1);
		Thread.sleep(3000);
		
		//URL2
		driver.findElement(By.xpath("//button[@id='s_2_1_3_0_Ctrl']")).click();
		Thread.sleep(3000);
		WebElement u1=driver.findElement(By.xpath("//input[@name='s_SweUrl_0']"));
		u1.click();
		u1.sendKeys("Test1");//Name
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@id='s_3_1_22_0_Ctrl']")).click();//Add
		Thread.sleep(5000);
		
		//Attachment type		
		driver.findElement(By.xpath("1EEMBL_Attachment_Type")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_Attachment_Type']")).click();
		driver.findElement(By.xpath("//input[@id='1_EEMBL_Attachment_Type']")).sendKeys(xAttachmenttype2);
		Thread.sleep(5000);
		
		
		//Clicking on Line items		
		driver.findElement(By.xpath("//a[text()='Line Items']")).click();
		Thread.sleep(10000);
		report.clickWE(driver, "//td[@id='1EEMBL_SIM_Card']");
		report.clickWE(driver, "//td[@id='1EEMBL_SIM_Card']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_SIM_Card']")).sendKeys(xSim);

		report.clickWE(driver, "//td[@id='1EEMBL_MSISDN']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_MSISDN']")).sendKeys(xMsisdn);
		Thread.sleep(5000);
		report.clickWE(driver, "//button[@title='Line Items:Save.']");
		Thread.sleep(20000);
		try{
			Alert alert2 = driver.switchTo().alert();
			alert2.accept();
			report.writeTorun(runlog,"Alert2 Accepted");
		}catch(NoAlertPresentException e11){
			report.writeTorun(runlog,"Alert1 not present");
		}
		Thread.sleep(5000);
		//PO received date		
		report.clickWE(driver, "//input[@aria-labelledby='Available_Date_Label']");
		driver.findElement(By.xpath("//input[@aria-labelledby='Available_Date_Label']")).sendKeys(xPoReceivedDate);
		Thread.sleep(5000);		
		
		//Contract duration
		driver.findElement(By.xpath("//input[@name='s_2_1_54_0']")).click();
		driver.findElement(By.xpath("//input[@name='s_2_1_54_0']")).sendKeys("5");
		
		//Sales PF number
		driver.findElement(By.xpath("//input[@name='s_2_1_45_0']")).click();
		driver.findElement(By.xpath("//input[@name='s_2_1_45_0']")).sendKeys("234");
		Thread.sleep(3000);
		
		report.clickWE(driver, "//button[text()='Submit']");
		
		Thread.sleep(30000);
		
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			//writer.println("Alert3 Accepted");
			report.writeTorun(runlog,"Alert3 Accepted");
		}catch(NoAlertPresentException e12){
			//writer.println("Alert3 not present");
			report.writeTorun(runlog,"Alert3 not present");
		}
		Thread.sleep(30000);
		String status = "";
		int counter = 0;
		while(!status.equals("Pending")){
			status = driver.findElement(By.xpath("//input[@aria-labelledby='Status_Label']")).getAttribute("value");
			counter++;
			if(counter == 5)
				break;
			//writer.println("Order Status is "+status);
			report.writeTorun(runlog,"Order Status is "+status);
			Thread.sleep(10000);
		}
		report.writeTorun(runlog,"Order Status in Siebel - "+status);
		driver.quit();
		report.writeTorun(runlog,"Browser Closed");
		report.writeTorun(runlog,"IUC Activation initiated from Siebel!");
		report.writeTorun(runlog,"IUC Activation initiated from Siebel");
		report.writeTorun(runlog, "Order Number is "+orderNumber);
		return 0;
	}
}