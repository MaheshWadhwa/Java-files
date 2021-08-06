package BUSINESS_RAQI3_MNP_PORT_IN ;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.bcel.generic.Select;
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



public class Raaqi3BusinessMNPPortINActivation {
	
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=Raaqi3BusinessMNPPortINActivationMain.runlog;
	static String reportfile=Raaqi3BusinessMNPPortINActivationMain.reportfile;
	static String Acti_path=Raaqi3BusinessMNPPortINActivationMain.Actipre;
	static int row;
	static int cellId=Raaqi3BusinessMNPPortINActivationMain.cellId;
	static int conditionCounter=0;
	static String  orderNumber;
	static String device=Raaqi3BusinessMNPPortINActivationMain.device;
		
	public int RaqiMNPActivation() throws IOException, FileNotFoundException, InterruptedException 
	
	{
		
		if(device.equalsIgnoreCase("Skip"))
		{
			report.writeToFile(reportfile, System.lineSeparator()+"Consmer Activation Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else
		{
			report.writeToFile(reportfile, System.lineSeparator()+"Consmer Activation with device Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation with device Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		}

		
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
		
		//int cellId = iterations;
		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Raqi3BusinessMNP.xls");
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

		// Activation Page Data
		
		testSheet = testWorkBook.getSheet("Activation");

		String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMsisdn = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDeliveryPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPaymentType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIMEI= testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim1 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim2= testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDonor= testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		

		report.writeTorun(runlog,"Read Data from Excel Successful");
		inputStream.close();
		//Maximize
		driver.manage().window().maximize();

		driver.get(siebelUrl);
		report.writeTorun(runlog,"URL invoked");

		// Login Page
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();

		report.writeTorun(runlog,"Clicked on Login");

		Thread.sleep(15000);		

		Thread.sleep(5000);
/*		WebElement select1 = driver.findElement(By.id("j_s_sctrl_tabScreen"));
		List<WebElement> options1 = select1.findElements(By.tagName("option"));
		for (WebElement option : options1) {
			if("Business Accounts".equals(option.getText())) {
				option.click();
				report.writeTorun(runlog,"Clicked on Business Account");
				break;
			}
		} */
		
		//Clicked on Business Accounts
		report.selectOption(driver, "j_s_sctrl_tabScreen", "Business Accounts");
		report.writeTorun(runlog,"Clicked on Business Account");
		Thread.sleep(10000);

	
		//Give Commercial Id and Click on Go Button
	    Thread.sleep(5000);
	    driver.findElement(By.xpath(".//*[@id='s_S_A1_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[3]/td[2]/table/tbody/tr/td/input")).sendKeys(xIdNo);
	    driver.findElement(By.xpath(".//*[@id='s_1_1_9_0_Ctrl']")).click(); // Click on Go
	    report.writeTorun(runlog,"Clicked on Go");

		//Click on Account Number
		Thread.sleep(15000);
		driver.findElement(By.xpath(".//*[@id='1Account_Number']/a")).click();
		report.writeTorun(runlog,"Clicked on AccountNumber");

		Thread.sleep(15000);
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			report.writeTorun(runlog,"Alert1 Accepted");
		}catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert1 not present");
		}

		//Click on Master Billing Account Number
		Thread.sleep(15000);
		driver.findElement(By.xpath(".//*[@id='1Account_Number']/a")).click(); //
		report.writeTorun(runlog,"Click on Master Billing Account Number");

		Thread.sleep(10000);
		try
		{
			Alert alert2 = driver.switchTo().alert();
			alert2.accept();
			report.writeTorun(runlog,"Alert2 Accepted");
		}
		catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert2 not present");
		}

		Thread.sleep(10000);
		
		//Clicked on Business RaqiIII
		report.selectOption(driver, "j_s_vctrl_div_tabScreen", "Business Raqi 3");
		report.writeTorun(runlog,"Clicked on Business RaqiIII");
		Thread.sleep(10000);
		
	    //Click New 
	    Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='s_1_1_0_0_Ctrl']")).click();
	    report.writeTorun(runlog,"Clicked on New");		
	    Thread.sleep(10000);
			    
	    //Clicked on New Order ID for enable link
		driver.findElement(By.xpath(".//*[@id='jqgh_s_1_l_Order_Id']")).click();
	    report.writeTorun(runlog,"Clicked on New Order ID to enable Link");		
	    Thread.sleep(10000);   
	    
	    
	    //Clicked on New Order Number
		driver.findElement(By.xpath(".//*[@id='1Order_Number']/a")).click();
	    report.writeTorun(runlog,"Clicked on New Order Number");		
	    Thread.sleep(10000);    
	    
			    	
		// Check the “MNP activation” check box and Give the MNP MSISDN.and Select the MNP donor.
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_MNP_Activation_Label']")).click(); // Check MNP Activation Checkbox
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_MNP_MSISDN_Label']")).sendKeys(xMsisdn); // MSISDN
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_MNP_Donor_Label']")).sendKeys("Zain"); // Give Zain as Donor value.

		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@data-display='Expand']")).click(); // Click on Expand button
		Thread.sleep(10000);
		
	/*	//driver.findElement(By.xpath("//td[@id='4Asset_Number']")).click();
		driver.findElement(By.id("4Asset_Number")).click();;
		Thread.sleep(5000);*/
		
		driver.findElement(By.xpath(".//*[@id='4Asset_Number']")).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//input[@id='4_Asset_Number']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='4_Asset_Number']")).sendKeys(xSim); // SIM
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//button[@data-display='Expand']")).click(); // Click on Expand button
		Thread.sleep(10000);
		
	
		driver.findElement(By.xpath(".//*[@id='5Asset_Number']")).click();
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//input[@id='5_Asset_Number']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@id='5_Asset_Number']")).sendKeys(xSim1); // SIM1		
		Thread.sleep(5000);
	
		driver.findElement(By.xpath("//button[@data-display='Expand']")).click(); // Click on Expand button
		Thread.sleep(10000);
		
//		driver.findElement(By.id("8Asset_Number")).click();
//		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='10Asset_Number']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='10_Asset_Number']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='10_Asset_Number']")).sendKeys(xSim2); // SIM2
		
	
	
		//DEVICE CHECKING AND SUBMISSION
		
		if(device.equalsIgnoreCase("Skip"))
		{
			//TBD
			
		}
		else
		{
			driver.findElement(By.xpath("//input[@name='s_1_2_97_0']")).sendKeys(device);
			report.writeTorun(runlog, "IMEI number submitted");
		}
		
		driver.findElement(By.xpath("//button[text()='Save Record']")).click(); // Click on Save
		report.writeTorun(runlog,"Clicked on Save");
		
		Thread.sleep(20000);
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			report.writeTorun(runlog,"Alert3 Accepted");
		}catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert3 not present");
		}
		Thread.sleep(20000);
		WebElement we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Sales Order:Submit']")));
		orderNumber = driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[3]/td[3]/div/input")).getText();
		report.writeTorun(runlog,"Order Number is "+orderNumber);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Order");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow r = sheet.createRow((short) 1);
		r.createCell(0).setCellValue(orderNumber);
		
		String File =Acti_path.concat("\\Oreder.xls");
		 FileOutputStream fileOut = new FileOutputStream(File,false);
	    workbook.write(fileOut);
	    fileOut.close();
	    System.out.println("Activation File has been created successfully ");
		inputStream.close();
		
		
//		String xDate= "09/24/2017 11:20:23 AM";
//		Thread.sleep(5000);	
		
/*	//    driver.findElement(By.xpath(".//*[@id='dp1443415834250']")).click(); 
	    Thread.sleep(2000);
	    driver.findElement(By.xpath(".//*[@id='s_2_1_122_0_icon']")).click();   //PO Received Date
	    Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Now']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()='Done']")).click(); // Click on Done    
*/        
		driver.findElement(By.xpath("//input[@aria-labelledby='Available_Date_Label']")).sendKeys("09/29/2015 10:42:13 AM"); //PO Recieve
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[50]/td[3]/div/input[1]")).click(); // Click on Advancepayment RadioButton
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@title='Sales Order:EligibilityCheck']")).click(); // Click on Eligibility Criteria Button
		     
		
		we.click();
		report.writeTorun(runlog,"Clicked on Submit");
		Thread.sleep(50000);
			
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			report.writeTorun(runlog,"Alert4 Accepted");
			Thread.sleep(3000);
			
			we.click();
			report.writeTorun(runlog,"Clicked on Submit");
		}catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert4 not present");
		}
		Thread.sleep(20000);
		
		String status = "";
		int counter = 0;
		while(!status.equals("Pending")){
			String path = "//a[text()='".concat(orderNumber).concat("']/../../td[3]");
			status = driver.findElement(By.xpath(path)).getText();
			counter++;
			if(counter == 5)
				break;
			report.writeTorun(runlog,"Order Status is "+status);
			Thread.sleep(10000);
		}
		report.writeTorun(runlog,"Order Status in Siebel - "+status);
		//System.out.println("Order Status in Siebel - "+status);
		driver.quit();
		report.writeTorun(runlog,"Browser Closed");
		//report.writeToFile(reportfile, "Activation completed successfully");
		return 0;
		}
}