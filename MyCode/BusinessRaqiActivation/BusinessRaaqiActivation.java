package  BusinessRaqiActivation ;

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



public class BusinessRaaqiActivation {

	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public BusinessRaaqiActivation(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		BusinessRaaqiActivation.reportFile = reportFile;
		BusinessRaaqiActivation.datasetLogFile = datasetLogFile;
		BusinessRaaqiActivation.datasetFolderPath = datasetFolderPath;
		BusinessRaaqiActivation.utilities = utilities;

	} // end of constructor method
	
	@SuppressWarnings("unused")
	public int activation(int cellId) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		utilities.writeToFile(reportFile, System.lineSeparator()+"Initiate Business Raqi Activation in Siebel"+System.lineSeparator()+"-------------------------------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tInitiate Business Raqi Activation in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		WebDriver driver = null;
		String browser = "Chrome";

		
		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Business_Raqi.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		Workbook testWorkBook = new XSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Application_Information");

		// Login Page Data
		String url = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xUserName = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xPassword = testSheet.getRow(++row).getCell(2).getStringCellValue();

		// Activation Information;
		row = 0;
		testSheet = testWorkBook.getSheet("Activation");
		String xCDId = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBusinessAccountNumber = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBranchAccountNumber = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMsisdn = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim1 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi1 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim2 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi2 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim3 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi3 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();

		testWorkBook.close();
		inputStream.close();
		
		utilities.writeToFile(datasetLogFile, "Read Data from Excel Successful");

		driver = utilities.invokeBrowser(driver, browser, url);

		SiebelOUILoginPF login = new SiebelOUILoginPF(driver);

		driver.manage().window().maximize();

		login.loginSiebelOUI(xUserName, xPassword);
		
		Thread.sleep(15000);
		try{
			Alert alert0 = driver.switchTo().alert();
			alert0.accept();
			
			utilities.writeToFile(datasetLogFile, "Password Expire Alert Accepted");
		}catch(NoAlertPresentException e){
			
			utilities.writeToFile(datasetLogFile, "Password Expire Alert not present");
		}
		
		Thread.sleep(20000);
		utilities.selectOption(driver, "j_s_sctrl_tabScreen", "Business Accounts /");

		WebElement we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Go']")));
		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_ID_Number_Label']")).sendKeys(xCDId);
		we.click();
		we = null;
		utilities.writeToFile(datasetLogFile, "Clicked on Go");

		utilities.clickWE(driver, "//a[text()='"+xBusinessAccountNumber+"']");
		Thread.sleep(5000);
		utilities.clickWE(driver, "//a[text()='"+xBranchAccountNumber+"']");

		Thread.sleep(5000);
		utilities.selectOption(driver, "j_s_vctrl_div_tabScreen", "Business Raqi 3");
		Thread.sleep(10000);
		utilities.clickWE(driver, "//button[text()='New']");

		Thread.sleep(4000);
		String orderNumber = driver.findElement(By.xpath("//td[@id='1Order_Number']")).getAttribute("title");

		utilities.writeToFile(datasetLogFile,"Order Number is "+orderNumber);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet1");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow row1 = sheet.createRow((short) 1);
		row1.createCell(0).setCellValue(orderNumber);

		utilities.createFolder(datasetFolderPath, "\\Activation_From_Siebel");
		String File = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_BUSINESS_RAQI.xls");
		FileOutputStream fileOut = new FileOutputStream(File, false);
		workbook.write(fileOut);
		workbook.close();

		utilities.clickWE(driver, "//td[@id='1Order_Id']");
		Thread.sleep(4000);
		utilities.clickWE(driver, "//td[@id='1Order_Id']/../td[3]/a");

		Thread.sleep(10000);
		utilities.clickWE(driver, "//button[@title='Line Items:Expand']");

		utilities.clickWE(driver, "//td[@id='4Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='4_Asset_Number']")).sendKeys(xSim1);
		Thread.sleep(2000);
		utilities.clickWE(driver, "//td[@id='5Asset_Number']");
		Thread.sleep(2000);
		utilities.clickWE(driver, "//td[@id='5Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='5_Asset_Number']")).sendKeys(xSim2);
		Thread.sleep(2000);

		utilities.clickWE(driver, "//td[@id='9Asset_Number']");
		Thread.sleep(2000);
		utilities.clickWE(driver, "//td[@id='9Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='9_Asset_Number']")).sendKeys(xMsisdn);
		Thread.sleep(2000);
		utilities.clickWE(driver, "//td[@id='10Asset_Number']");
		Thread.sleep(2000);
		utilities.clickWE(driver, "//td[@id='10Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='10_Asset_Number']")).sendKeys(xSim3);
		Thread.sleep(2000);

		utilities.clickWE(driver, "//button[@title='Line Items:Save Record']");

		Thread.sleep(10000);
		if(driver.findElement(By.xpath("//a[text()='Attachments']")).isDisplayed()){
			driver.findElement(By.xpath("//a[text()='Attachments']")).click();
			utilities.writeToFile(datasetLogFile, "Clicked on Attachments");
		} else {
			utilities.selectOption(driver, "j_s_vctrl_div_tabScreen", "Attachments");
		}
		utilities.clickWE(driver, "//button[@title='Attachments:New URL']");
		utilities.clickWE(driver, "//input[@aria-labelledby='s_SweUrl_Label']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@aria-labelledby='s_SweUrl_Label']")).sendKeys("TestURL");
		Thread.sleep(2000);
		utilities.clickWE(driver, "//button[@title='Add URL:Add']");
		Thread.sleep(5000);
		utilities.clickWE(driver, "//a[text()='Line Items']");
		Thread.sleep(5000);
		utilities.clickWE(driver, "//input[@aria-labelledby='Available_Date_Label']/../img");
		utilities.clickWE(driver, "//button[text()='Now']");
		utilities.clickWE(driver, "//button[text()='Done']");

		utilities.clickWE(driver, "//button[@title='Sales Order:Submit']");

		Thread.sleep(20000);
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			utilities.writeToFile(datasetLogFile, "Alert3 Accepted");
		}catch(NoAlertPresentException e){
			utilities.writeToFile(datasetLogFile, "Alert3 Not Present");
		}
		Thread.sleep(30000);

		String status = "";
		int counter = 0;
		while(!status.equals("Pending")){
			status = driver.findElement(By.xpath("//input[@aria-labelledby='Status_Label']")).getAttribute("value");
			counter++;
			if(counter == 5)
				break;
			utilities.writeToFile(datasetLogFile, "Order Status is "+status);
			Thread.sleep(10000);
		}
		utilities.writeToFile(datasetLogFile, "Order Status in Siebel - "+status);
		driver.quit();
		
		utilities.writeToFile(datasetLogFile, "Browser Closed");
		
		utilities.writeToFile(datasetLogFile, "IUC Activation initiated from Siebel!");
		utilities.writeToFile(reportFile, "IUC Activation initiated from Siebel");
		utilities.writeToFile(reportFile, "Order Number is "+orderNumber);
		return 0;
	}
}