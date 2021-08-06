package iucNormalActivation_FrameWork;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IucActivationWD {
	
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public IucActivationWD(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		IucActivationWD.reportFile = reportFile;
		IucActivationWD.datasetLogFile = datasetLogFile;
		IucActivationWD.datasetFolderPath = datasetFolderPath;
		IucActivationWD.utilities = utilities;
		
	} // end of constructor method

	public int iucActivate(int cellId) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		utilities.writeToFile(reportFile, System.lineSeparator()+"Initiate IUC Activation in Siebel"+System.lineSeparator()+"---------------------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tInitiate IUC Activation in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		WebDriver driver = null;
		String browser = "Chrome";

		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_IUC.xlsx");
		FileInputStream inputStream = new FileInputStream(xlFile);


		Workbook testWorkBook = new XSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Application_Information");

		// Login Page Data
		String url = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xUserName = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xPassword = testSheet.getRow(++row).getCell(2).getStringCellValue();

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
		String xContract = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDeviceModel = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImei = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		testWorkBook.close();

		CommonUtilitiesWriter cu = new CommonUtilitiesWriter();

		driver = cu.invokeBrowser(driver, browser, url);

		SiebelOUILoginPF login = new SiebelOUILoginPF(driver);

		driver.manage().window().maximize();

		login.loginSiebelOUI(xUserName, xPassword);

		Thread.sleep(15000);
		try{
			Alert alert0 = driver.switchTo().alert();
			alert0.accept();
			
			cu.writeToFile(datasetLogFile, "Password Expire Alert Accepted");
		}catch(NoAlertPresentException e){
			
			cu.writeToFile(datasetLogFile, "Password Expire Alert not present");
		}
		
		WebElement we = null;

		Thread.sleep(20000);
		cu.clickWE(driver, "//a[text()='Customer']");
		//writer.println("Clicked on Customer Tab");
		cu.writeToFile(datasetLogFile, "Clicked on Customer Tab");

		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Go']")));
		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_ID_Number_Label']")).sendKeys(xCDId);
		we.click();
		we = null;
		//writer.println("Clicked on Go");
		cu.writeToFile(datasetLogFile, "Clicked on Go");
		
		Thread.sleep(10000);
		cu.selectOption(driver, "j_s_sctrl_tabView", "Express Activation"); 

		Thread.sleep(5000);
		cu.selectOption(driver, "j_s_vctrl_div_tabScreen", "IUC Raaqi / SOHO Activation");

		Thread.sleep(5000);
		cu.clickWE(driver, "//button[@title='Orders:New']");

		Thread.sleep(10000);

		String orderNumber = driver.findElement(By.xpath("//a[@name='Order Number']")).getText();
		//writer.println("Order Number is "+orderNumber);
		cu.writeToFile(datasetLogFile, "Order Number is "+orderNumber);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet1");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow row1 = sheet.createRow((short) 1);
		row1.createCell(0).setCellValue(orderNumber);
		
		cu.createFolder(datasetFolderPath, "\\Activation_From_Siebel");
		String File = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_IUC.xls");
		FileOutputStream fileOut = new FileOutputStream(File, false);
		workbook.write(fileOut);
		workbook.close();

		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_EA_Product_Label']")).sendKeys(xPackage);

		cu.selectDD(driver, ".//*[@id='s_1_2_32_0_icon']", xPaymentType);

		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Commercial_ID_Label']")).sendKeys(xCommercialID);
		

		cu.clickWE(driver, "//button[@title='Orders:Save']");

		Thread.sleep(20000);
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			cu.writeToFile(datasetLogFile, "Alert1 Accepted");
		}catch(NoAlertPresentException e){
			cu.writeToFile(datasetLogFile, "Alert1 not present");
		}

		String orderNoLink1 = "//a[text()='";
		String orderNoLink2 = "']";
		String orderNoLink = orderNoLink1.concat(orderNumber).concat(orderNoLink2);

		cu.clickWE(driver, orderNoLink);
		Thread.sleep(5000);
		
		// Customizing as per the device
		cu.clickWE(driver,"//button[@title='Line Items:Customize']");
		Thread.sleep(5000);
		cu.selectOptionDD(driver, "//option[text()='12']/..", xContract);
		Thread.sleep(3000);
		cu.selectOptionDD(driver, "//option[text()='Nokia Asha 311']/..", xDeviceModel);
		Thread.sleep(3000);
		cu.clickWE(driver, "//button[text()='Done']");
		Thread.sleep(15000);
		

		cu.clickWE(driver, "//td[@id='1EEMBL_SIM_Card']");
		cu.clickWE(driver, "//td[@id='1EEMBL_SIM_Card']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_SIM_Card']")).sendKeys(xSim);
		Thread.sleep(3000);

		cu.clickWE(driver, "//td[@id='1EEMBL_MSISDN']");
		cu.clickWE(driver, "//td[@id='1EEMBL_MSISDN']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_MSISDN']")).sendKeys(xMsisdn);
		Thread.sleep(5000);
		
		cu.clickWE(driver, "//td[@id='1EEMBL_IMEI']");
		cu.clickWE(driver, "//td[@id='1EEMBL_IMEI']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@id='1_EEMBL_IMEI']")).sendKeys(xImei);
		Thread.sleep(5000);
		
		cu.clickWE(driver, "//button[@title='Line Items:Save.']");
		Thread.sleep(20000);
		try{
			Alert alert2 = driver.switchTo().alert();
			alert2.accept();
			cu.writeToFile(datasetLogFile, "Alert2 Accepted");
		}catch(NoAlertPresentException e){
			cu.writeToFile(datasetLogFile, "Alert1 not present");
		}
		Thread.sleep(5000);
		cu.clickWE(driver, "//input[@aria-labelledby='Available_Date_Label']");
		driver.findElement(By.xpath("//input[@aria-labelledby='Available_Date_Label']")).sendKeys(xPoReceivedDate);

		Thread.sleep(5000);
		cu.clickWE(driver, "//button[text()='Submit']");
		
		Thread.sleep(20000);
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			//writer.println("Alert3 Accepted");
			cu.writeToFile(datasetLogFile, "Alert3 Accepted");
		}catch(NoAlertPresentException e){
			//writer.println("Alert3 not present");
			cu.writeToFile(datasetLogFile, "Alert3 not present");
		}
		Thread.sleep(30000);
		
		String status = "";
		int counter = 0;
		while(!status.equals("Pending")){
			status = driver.findElement(By.xpath("//input[@aria-labelledby='Status_Label']")).getAttribute("value");
			counter++;
			if(counter == 5)
				break;
			cu.writeToFile(datasetLogFile, "Order Status is "+status);
			Thread.sleep(10000);
		}
		
		cu.writeToFile(datasetLogFile, "Order Status in Siebel - "+status);
		driver.quit();
		
		cu.writeToFile(datasetLogFile, "Browser Closed");
		
		cu.writeToFile(datasetLogFile, "IUC Activation initiated from Siebel!");
		cu.writeToFile(reportFile, "IUC Activation initiated from Siebel");
		cu.writeToFile(reportFile, "Order Number is "+orderNumber);
		return 0;
	}
}