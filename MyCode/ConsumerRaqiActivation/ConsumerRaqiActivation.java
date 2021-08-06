package ConsumerRaqiActivation;

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

import ConsumerRaqiActivation.SiebelOUILoginPF;

public class ConsumerRaqiActivation {

	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public ConsumerRaqiActivation(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		ConsumerRaqiActivation.reportFile = reportFile;
		ConsumerRaqiActivation.datasetLogFile = datasetLogFile;
		ConsumerRaqiActivation.datasetFolderPath = datasetFolderPath;
		ConsumerRaqiActivation.utilities = utilities;

	} // end of constructor method

	@SuppressWarnings("unused")
	public int activation(int cellId) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		utilities.writeToFile(reportFile, System.lineSeparator()+"Initiate Consumer Raqi Activation in Siebel"+System.lineSeparator()+"-------------------------------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tInitiate Consumer Raqi Activation in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		WebDriver driver = null;
		String browser = "Chrome";


		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Consumer_Raqi.xlsx");
		FileInputStream inputStream = new FileInputStream(xlFile);
		Workbook testWorkBook = new XSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Application_Information");

		// Login Page Data
		String url = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xUserName = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xPassword = testSheet.getRow(++row).getCell(2).getStringCellValue();

		// Activation Information
		row = 0;
		testSheet = testWorkBook.getSheet("Activation");
		String xCDId = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMsisdn = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim1 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi1 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim2 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi2 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim3 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi3 = testSheet.getRow(++row).getCell(cellId).getStringCellValue();

		testWorkBook.close();

		utilities.writeToFile(datasetLogFile, "Read Data from Excel Successful");

		WebElement we = null;

		CommonUtilitiesWriter cu = new CommonUtilitiesWriter(reportFile, datasetLogFile);

		driver = cu.invokeBrowser(driver, browser, url);

		SiebelOUILoginPF login = new SiebelOUILoginPF(driver);

		driver.manage().window().maximize();

		login.loginSiebelOUI(xUserName, xPassword);

		Thread.sleep(20000);
		cu.clickWE(driver, "//a[text()='Customer']");
		Thread.sleep(10000);

		we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Go']")));
		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_ID_Number_Label']")).sendKeys(xCDId);
		we.click();
		we = null;
		utilities.writeToFile(datasetLogFile, "Clicked on Go");
		Thread.sleep(5000);
		cu.selectOption(driver, "j_s_sctrl_tabView", "Express Activation"); 

		cu.clickWE(driver, "//a[text()='Raaqi Activation']");

		cu.clickWE(driver, "//button[@title='Orders:New']");

		cu.clickWE(driver, "//button[@title='Line Items:Expand']");

		cu.clickWE(driver, "//td[@id='4Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='4_Asset_Number']")).sendKeys(xSim1);
		Thread.sleep(2000);
		cu.clickWE(driver, "//td[@id='5Asset_Number']");
		Thread.sleep(2000);
		cu.clickWE(driver, "//td[@id='5Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='5_Asset_Number']")).sendKeys(xSim2);
		Thread.sleep(2000);

		cu.clickWE(driver, "//td[@id='7Asset_Number']");
		Thread.sleep(2000);
		cu.clickWE(driver, "//td[@id='7Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='7_Asset_Number']")).sendKeys(xMsisdn);
		Thread.sleep(2000);
		cu.clickWE(driver, "//td[@id='8Asset_Number']");
		Thread.sleep(2000);
		cu.clickWE(driver, "//td[@id='8Asset_Number']");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='8_Asset_Number']")).sendKeys(xSim3);
		Thread.sleep(2000);

		cu.clickWE(driver, "//button[@title='Line Items:Save Record']");

		Thread.sleep(10000);
		if(driver.findElement(By.xpath("//a[text()='Attachments']")).isDisplayed()){
			driver.findElement(By.xpath("//a[text()='Attachments']")).click();
			utilities.writeToFile(datasetLogFile, "Clicked on Attachments");
		} else {
			cu.selectOption(driver, "j_s_vctrl_div_tabScreen", "Attachments");
		}
		cu.clickWE(driver, "//button[@title='Attachments:New URL']");
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@aria-labelledby='s_SweUrl_Label']")).sendKeys("Test_URL");
		cu.clickWE(driver, "//button[@title='Add URL:Add']");
		Thread.sleep(10000);
		cu.clickWE(driver, "//a[text()='Line Items']");

		Thread.sleep(10000);
		cu.clickWE(driver, "//input[@aria-labelledby='OrderNumber_Label']");
		String orderNumber = driver.findElement(By.xpath("//input[@aria-labelledby='OrderNumber_Label']")).getAttribute("value");
		utilities.writeToFile(datasetLogFile, "Order Number is "+orderNumber);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet1");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow row1 = sheet.createRow((short) 1);
		row1.createCell(0).setCellValue(orderNumber);

		cu.createFolder(datasetFolderPath, "\\Activation_From_Siebel");
		String File = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_CONSUMER_RAQI.xls");
		FileOutputStream fileOut = new FileOutputStream(File, false);
		workbook.write(fileOut);
		workbook.close();
		cu.writeToFile(datasetLogFile, "Order ID stored in Excel Sheet");
		Thread.sleep(5000);

		cu.clickWE(driver, "//input[@aria-labelledby='Available_Date_Label']/../img");
		Thread.sleep(3000);
		cu.clickWE(driver, "//button[text()='Now']");
		Thread.sleep(2000);
		cu.clickWE(driver, "//button[text()='Done']");
		Thread.sleep(5000);
		cu.clickWE(driver, "//input[@aria-labelledby='EEMBL_Professional_Document_Type_Label']");
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Professional_Document_Type_Label']")).clear();
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Professional_Document_Type_Label']")).sendKeys("Credit Card");
		cu.clickWE(driver, "//button[@title='Sales Order:EligibilityCheck']");
		Thread.sleep(10000);
		cu.clickWE(driver, "//button[@title='Sales Order:Submit']");

		Thread.sleep(20000);
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			cu.writeToFile(datasetLogFile, "Alert3 Accepted");
		}catch(NoAlertPresentException e){
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
			//writer.println("Order Status is "+status);
			cu.writeToFile(datasetLogFile, "Order Status is "+status);
			Thread.sleep(10000);
		}

		cu.writeToFile(datasetLogFile, "Order Status in Siebel - "+status);
		driver.quit();

		cu.writeToFile(datasetLogFile, "Browser Closed");

		cu.writeToFile(datasetLogFile, "Consumer Raqi Activation initiated from Siebel!");
		cu.writeToFile(reportFile, "Consumer Raqi Activation initiated from Siebel");
		cu.writeToFile(reportFile, "Order Number is "+orderNumber);
		return 0;
	}
}