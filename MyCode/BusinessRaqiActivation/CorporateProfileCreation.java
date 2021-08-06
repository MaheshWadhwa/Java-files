package  BusinessRaqiActivation ;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class CorporateProfileCreation {
	
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public CorporateProfileCreation(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		CorporateProfileCreation.reportFile = reportFile;
		CorporateProfileCreation.datasetLogFile = datasetLogFile;
		CorporateProfileCreation.datasetFolderPath = datasetFolderPath;
		CorporateProfileCreation.utilities = utilities;
	} // end of constructor method

	public int createProfile(int cellId) throws IOException, InterruptedException{

		utilities.writeToFile(reportFile, System.lineSeparator()+"Create Profile in Siebel"+System.lineSeparator()+"------------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tCreate Profile in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		WebDriver driver = null;
		String browser = "Chrome";

		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Business_Raqi.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);

		Workbook testWorkBook = new HSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Application_Information");

		// Login Page Data
		String siebelUrl = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xUserName = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xPassword = testSheet.getRow(++row).getCell(2).getStringCellValue();
		
		// Company Document
		row = 0;
		testSheet = testWorkBook.getSheet("Profile_Creation");
		String xCDIdType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		if(xCDIdType.equalsIgnoreCase("Skip")){
			testWorkBook.close();
			inputStream.close();
			utilities.writeToFile(datasetLogFile, "Profile Creation Skipped...");
			return 101;
		}
		
		String xCDIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCDDocIssuePlace = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCDDateFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCDIssueDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCDIdExpiry = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		// Comapny / Corporate Information
		String xCCCompanyName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCCCompanyCategory = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCCDiscount = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCCDeliveryPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		// Authorized Person
		String xAPTitle = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPFirstName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPLastName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPDobFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPDob = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPIdType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPIdNumber = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPIdDocIssuePlace = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPDateFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPNationality = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPDeliveryPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPMobileNumber = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPEmail = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPGender = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPDocIssueDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAPDocExpDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		// Company Demographics
		String xDCountryOfOrigin = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDCompanyType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDNumberOfOffices = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDNumberOfEmployees = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		// Company Address
		String xCAAddress = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCAPostalCode = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCAPoBox = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCAAddrType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		
		testWorkBook.close();
		inputStream.close();
		
		utilities.writeToFile(datasetLogFile, "Data Read from excel successful");
		
		
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}

		//Maximize
		driver.manage().window().maximize();


		driver.get(siebelUrl);
		utilities.writeToFile(datasetLogFile, "URL invoked");


		// Login Page
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();

		utilities.writeToFile(datasetLogFile, "Clicked on Login");

		Thread.sleep(20000);
		utilities.selectOption(driver, "j_s_sctrl_tabScreen", "Business Accounts /");
		
		utilities.clickWE(driver, "//button[text()='Go']");
		Thread.sleep(2000);
		utilities.clickWE(driver, "//button[text()='New']");
		
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_ID_Doc_Type_Label']/../img", xCDIdType);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Number_Label']")).sendKeys(xCDIdNo);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Place_Label']")).sendKeys(xCDDocIssuePlace);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Date_Format_Label']/../img", xCDDateFormat);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Date_Label']")).sendKeys(xCDIssueDate);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Exp_Date_Label']")).sendKeys(xCDIdExpiry);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Company_Name_Label']")).sendKeys(xCCCompanyName);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Customer_Category_Label']/../img", xCCCompanyCategory);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Discount_Package_Label']/../img")).click();
		Thread.sleep(4000);
		String path = "//td[@title='".concat(xCCDiscount).concat("']");
		driver.findElement(By.xpath(path)).click();
		utilities.clickWE(driver, "//button[text()='OK']");
		Thread.sleep(4000);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Preferred_Shipping_Method_Label']/../img", xCCDeliveryPreference);
		
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Title_Label']/../img", xAPTitle);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_First_Name_Label']")).sendKeys(xAPFirstName);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Last_Name_Label']")).sendKeys(xAPLastName);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_DOB_Format_Label']/../img", xAPDobFormat);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Birth_Date_Label']")).sendKeys(xAPDob);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_ID_Doc_Type_Contact_Label']/../img", xAPIdType);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Number_Contact_Label']")).sendKeys(xAPIdNumber);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Place_Contact_Label']")).sendKeys(xAPIdDocIssuePlace);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Date_Format_Contact_Label']/../img", xAPDateFormat);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Nationality_Label']/../img", xAPNationality);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Contact_Preference_Label']/../img", xAPDeliveryPreference);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Mobile_Label']")).sendKeys(xAPMobileNumber);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Email_Address_Label']")).sendKeys(xAPEmail);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_M/F_Label']/../img", xAPGender);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Date_Contact_Label']")).sendKeys(xAPDocIssueDate);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Exp_Date_Contact_Label']")).sendKeys(xAPDocExpDate);
		
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Country_of_Incorporation_Label']/../img", xDCountryOfOrigin);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Company_Type_Label']/../img", xDCompanyType);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Number_of_Offices_Label']")).sendKeys(xDNumberOfOffices);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Number_of_Employees_Label']")).sendKeys(xDNumberOfEmployees);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Street_Address_Label']")).sendKeys(xCAAddress);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Postal_Code_Label']")).clear();
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Postal_Code_Label']")).sendKeys(xCAPostalCode);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_PO_Box_Label']")).sendKeys(xCAPoBox);
		utilities.selectDD(driver, "//input[@aria-labelledby='EEMBL_Addr_Type_Label']/../img", xCAAddrType);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Company_Name_Eng_Label']")).sendKeys(xCCCompanyName);
		
		utilities.clickWE(driver, "//button[text()='Save']");
		
		Thread.sleep(30000);
		driver.findElement(By.partialLinkText("Business Accounts /")).click();
		
		Thread.sleep(10000);
		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_ID_Number_Label']")).sendKeys(xCDIdNo);
		utilities.clickWE(driver, "//button[text()='Go']");
		
		Thread.sleep(15000);
		String accountNumber1 = driver.findElement(By.xpath("//td[text()='"+xCDIdNo+"']/../td[2]/a")).getText();
		utilities.writeToFile(datasetLogFile, "Account Number 1 is "+accountNumber1);
		utilities.writeToFile(reportFile, "Account Number 1 is "+accountNumber1);
		
		utilities.clickWE(driver, "//td[text()='"+xCDIdNo+"']/../td[2]/a");
		Thread.sleep(5000);
		
		utilities.clickWE(driver, "//button[text()='New']");
		Thread.sleep(5000);
		
		utilities.clickWE(driver, "//button[text()='Save']");
		Thread.sleep(20000);
		
		String accountNumber2 = driver.findElement(By.xpath("//td[text()='"+xCAPoBox+"']/../td[2]/a")).getText();
		utilities.writeToFile(datasetLogFile, "Account Number 2 is "+accountNumber2);
		utilities.writeToFile(reportFile, "Account Number 2 is "+accountNumber2);
		
		Thread.sleep(10000);	
		utilities.createFolder(datasetFolderPath, "\\Profile_Creation");
		utilities.takeScreenShotMethod(driver, datasetFolderPath.concat("\\Profile_Creation"), "Profile Creation in Siebel");
		driver.quit();
		utilities.writeToFile(datasetLogFile, "Browser Closed");
		utilities.writeToFile(datasetLogFile, "Customer Successfully Created from Siebel!");
		utilities.writeToFile(reportFile, "Profile Created in Siebel");

		ProfileCheckInMdmCorporate profileCheckInMdmObj = new ProfileCheckInMdmCorporate(reportFile,datasetLogFile,datasetFolderPath,utilities);
		int check = profileCheckInMdmObj.checkInMdm(xCDIdNo);

		return check;
	}
}