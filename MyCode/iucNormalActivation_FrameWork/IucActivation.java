package iucNormalActivation_FrameWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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


public class IucActivation {
	
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public IucActivation(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		IucActivation.reportFile = reportFile;
		IucActivation.datasetLogFile = datasetLogFile;
		IucActivation.datasetFolderPath = datasetFolderPath;
		IucActivation.utilities = utilities;
		
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
		
		testWorkBook.close();

		CommonUtilitiesWriter cu = new CommonUtilitiesWriter(reportFile,datasetLogFile);

		driver = cu.invokeBrowser(driver, browser, url);

		SiebelOUILoginPF login = new SiebelOUILoginPF(driver);
		
		
		//Property file declaration
		 FileInputStream objfile = new FileInputStream("C:\\Users\\j.dasari.mit\\j.dasari.mit_view\\vobs\\QualityControl\\Mobily\\FrameWork\\src\\IUCNormalActivation_OR\\IUCActivation.properties");
		 Properties prop = new Properties();
		 prop.load(objfile);
		 

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

		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("GoButton"))));
		driver.findElement(By.xpath(prop.getProperty("CommercialID"))).sendKeys(xCDId);
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

		String orderNumber = driver.findElement(By.xpath(prop.getProperty("OrderNumber"))).getText();
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

		driver.findElement(By.xpath(prop.getProperty("PackageName"))).sendKeys(xPackage);

		cu.selectDD(driver, ".//*[@id='s_1_2_32_0_icon']", xPaymentType);

		driver.findElement(By.xpath(prop.getProperty("CommercialID1"))).sendKeys(xCommercialID);
		

		cu.clickWE(driver, "//button[@title='Orders:Save']");

		Thread.sleep(20000);
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			//writer.println("Alert1 Accepted");
			cu.writeToFile(datasetLogFile, "Alert1 Accepted");
		}catch(NoAlertPresentException e){
			//writer.println("Alert1 not present");
			cu.writeToFile(datasetLogFile, "Alert1 not present");
		}

		String orderNoLink1 = "//a[text()='";
		String orderNoLink2 = "']";
		String orderNoLink = orderNoLink1.concat(orderNumber).concat(orderNoLink2);

		cu.clickWE(driver, orderNoLink);
		Thread.sleep(5000);

		cu.clickWE(driver, "//td[@id='1EEMBL_SIM_Card']");
		cu.clickWE(driver, "//td[@id='1EEMBL_SIM_Card']");
		Thread.sleep(5000);
		driver.findElement(By.xpath(prop.getProperty("Sim"))).sendKeys(xSim);

		cu.clickWE(driver, "//td[@id='1EEMBL_MSISDN']");
//		cu.clickWE(driver, "//td[@id='1EEMBL_MSISDN']");
		Thread.sleep(5000);
		driver.findElement(By.xpath(prop.getProperty("MSISDN"))).sendKeys(xMsisdn);
		Thread.sleep(5000);
		cu.clickWE(driver, "//button[@title='Line Items:Save.']");
		Thread.sleep(20000);
		try{
			Alert alert2 = driver.switchTo().alert();
			alert2.accept();
			//writer.println("Alert2 Accepted");
			cu.writeToFile(datasetLogFile, "Alert2 Accepted");
		}catch(NoAlertPresentException e){
			//writer.println("Alert2 not present");
			cu.writeToFile(datasetLogFile, "Alert1 not present");
		}
		Thread.sleep(5000);
		cu.clickWE(driver, "//input[@aria-labelledby='Available_Date_Label']");
		driver.findElement(By.xpath(prop.getProperty("PORecievedDate"))).sendKeys(xPoReceivedDate);

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
			status = driver.findElement(By.xpath(prop.getProperty("Status"))).getAttribute("value");
			counter++;
			if(counter == 5)
				break;
			//writer.println("Order Status is "+status);
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