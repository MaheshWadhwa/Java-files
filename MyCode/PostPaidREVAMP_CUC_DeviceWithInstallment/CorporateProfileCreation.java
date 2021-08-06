package PostPaidREVAMP_CUC_DeviceWithInstallment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;






public class CorporateProfileCreation {

	/*
	static String runlog=CucMain.runlog;
	static String reportfile=CucMain.reportfile;
	static CommonUtilitiesWriter report= new CommonUtilitiesWriter();
	static int conditionCounter=0;
	//static int checkCounter=0;
	static int cellId=CucMain.cellId;*/
	
	static String runlog;
	static String reportfile;
	//static String report;
	static CommonUtilitiesWriter report;
	static int conditionCounter=0;
	static int checkCounter=0;
	static String Postpre;
	
	public CorporateProfileCreation(String runlog,String reportfile,String Postpre,CommonUtilitiesWriter report)
	{
		CorporateProfileCreation.reportfile=reportfile;
		CorporateProfileCreation.runlog=runlog;
		CorporateProfileCreation.report=report;
		CorporateProfileCreation.Postpre=Postpre;
	}
	
	public int createProfile(int cellId) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		
		//report.writeToFile(reportfile, System.lineSeparator()+"Create Profile in Siebel"+System.lineSeparator()+report.insertDashedLine("Create Profile in Siebel"));
		conditionCounter = 0;
		report.writeToFile(reportfile, System.lineSeparator()+"Create Profile in Siebel"+System.lineSeparator()+report.insertDashedLine("Create Profile in Siebel"));
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tCreate Profile in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		WebDriver driver = null;
		String browser = "Chrome";
		//int cellId = 2;
		int row = 0;
		
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevamp.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
		
		/*HSSFWorkbook testWorkBook = new HSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Application_Information");*/
		
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
		
		
		row=0;
		testSheet = testWorkBook.getSheet("Profile_Creation");
		
		
		// Company Document
		String xCDIdType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		if(xCDIdType.equalsIgnoreCase("Skip")){
			//testWorkBook.close();
			inputStream.close();
			report.writeToFile(reportfile, "Profile Creation Skipped...");
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
		
		report.writeTorun(runlog,"Read Data from Excel Successful");
		
		inputStream.close();
		
		CommonUtility cu = new CommonUtility();
		
		driver = cu.invokeBrowser(driver, browser, siebelUrl);
		
		SiebelOUILoginPF login = new SiebelOUILoginPF(driver);
		
		driver.manage().window().maximize();
		
		login.loginSiebelOUI(xUserName, xPassword);
		
		Thread.sleep(20000);
		
		report.writeTorun(runlog, "Logged in successfully");
		//System.out.println("Logged in successfully");
		
			
		cu.selectOption(driver, "j_s_sctrl_tabScreen", "Business Accounts /");
		Thread.sleep(2000);
		
		
		
		
		cu.clickWE(driver, "//button[text()='Go']");
		Thread.sleep(2000);
		
		System.out.println("Clicked on go");
		
		
		cu.clickWE(driver, "//button[text()='New']");
		Thread.sleep(3000);
		
		/*WebElement w= driver.findElement(By.xpath(".//*[@id='a_2']/div/table/tbody/tr[5]/td[7]/div/input"));
		w.click();
		w.sendKeys(xCDIdType);
		*/
		
		
		//.//*[@id='a_2']/div/table/tbody/tr[5]/td[7]/div/input
		
		cu.select(driver, ".//*[@id='a_2']/div/table/tbody/tr[5]/td[7]/div/input", xCDIdType);

		//cu.select(driver, ".//*[@id='s_2_1_37_0_icon']", xCDIdType);
		
		//cu.selectDD(driver, ".//*[@id='ui-menu-17-0']", xCDIdType);
		
		//System.out.println("Check1");
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Number_Label']")).sendKeys(xCDIdNo);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Place_Label']")).sendKeys(xCDDocIssuePlace);
	
		
		cu.select(driver, ".//*[@id='a_2']/div/table/tbody/tr[11]/td[5]/div/input", xCDDateFormat);
		
		//cu.selectDD(driver, ".//*[@id='s_2_1_40_0_icon']", xCDDateFormat);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Date_Label']")).sendKeys(xCDIssueDate);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Exp_Date_Label']")).sendKeys(xCDIdExpiry);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Company_Name_Label']")).sendKeys(xCCCompanyName);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[9]/td[3]/div/input",xCCCompanyCategory);
		//cu.selectDD(driver, ".//*[@id='s_2_1_6_0_icon']", xCCCompanyCategory);
		
		Thread.sleep(5000);
		
		//cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[13]/td[3]/div/input", xCCDiscount);
		driver.findElement(By.xpath(".//*[@id='s_2_1_8_0_icon']")).click();
		Thread.sleep(5000);
		String path = "//td[@title='".concat(xCCDiscount).concat("']");
		driver.findElement(By.xpath(path)).click();
		cu.clickWE(driver, "//button[text()='OK']");
		
		cu.select(driver, ".//*[@id='a_2']/div/table/tbody/tr[17]/td[3]/div/input", xCCDeliveryPreference);
		//cu.selectDD(driver,".//*[@id='s_2_1_10_0_icon']",xCCDeliveryPreference);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[16]/td[7]/div/input",xAPTitle);
		
		//cu.selectDD(driver,".//*[@id='s_2_1_44_0_icon']",xAPTitle);
		
		
		//cu.select(driver, ".//*[@id='s_2_1_45_0_icon']", xAPTitle);
		
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_First_Name_Label']")).sendKeys(xAPFirstName);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Last_Name_Label']")).sendKeys(xAPLastName);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[22]/td[8]/div/input", xAPDobFormat);
		//cu.selectDD(driver, ".//*[@id='s_2_1_49_0_icon']", xAPDobFormat);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Birth_Date_Label']")).sendKeys(xAPDob);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[27]/td[5]/div/input",xAPIdType);
		//cu.selectDD(driver, ".//*[@id='s_2_1_53_0_icon']", xAPIdType);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Number_Contact_Label']")).sendKeys(xAPIdNumber);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Place_Contact_Label']")).sendKeys(xAPIdDocIssuePlace);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[33]/td[3]/div/input",xAPDateFormat);
		//cu.selectDD(driver, ".//*[@id='s_2_1_56_0_icon']", xAPDateFormat);
		
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[17]/td[8]/div/input",xAPNationality);
		//cu.selectDD(driver, ".//*[@id='s_2_1_58_0_icon']", xAPNationality);
		
		//cu.selectDD(driver, ".//*[@id='s_2_1_59_0_icon']", xAPDeliveryPreference);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[19]/td[7]/div/input",xAPDeliveryPreference);
				
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Mobile_Label']")).sendKeys(xAPMobileNumber);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Email_Address_Label']")).sendKeys(xAPEmail);
		
		//cu.selectDD(driver, ".//*[@id='s_2_1_65_0_icon']", xAPGender);
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[27]/td[7]/div/input",xAPGender);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Issue_Date_Contact_Label']")).sendKeys(xAPDocIssueDate);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_ID_Doc_Exp_Date_Contact_Label']")).sendKeys(xAPDocExpDate);
		
		//cu.selectDD(driver, ".//*[@id='s_2_1_25_0_icon']", xDCountryOfOrigin);
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[28]/td[3]/div/input",xDCountryOfOrigin);
		
		//cu.selectDD(driver, ".//*[@id='s_2_1_26_0_icon']", xDCompanyType);
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[30]/td[3]/div/input",xDCompanyType);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Number_of_Offices_Label']")).sendKeys(xDNumberOfOffices);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Number_of_Employees_Label']")).sendKeys(xDNumberOfEmployees);
		
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Street_Address_Label']")).sendKeys(xCAAddress);
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Postal_Code_Label']")).clear();
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Postal_Code_Label']")).sendKeys(xCAPostalCode);
		Thread.sleep(2000);
		/*try{
	    	Alert alert2 = driver.switchTo().alert();
	    	alert2.accept();
	    	System.out.println("Alert2 Accepted");
	    }
		catch(NoAlertPresentException e){
	    	System.out.println("Alert2 not present");
	    }	*/
		driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_PO_Box_Label']")).sendKeys(xCAPoBox);
		
		//cu.selectDD(driver, ".//*[@id='s_2_1_23_0_icon']", xCAAddrType);
		cu.select(driver, ".//*[@id='a_2']/div/table/tbody/tr[23]/td[5]/div/input", xCAAddrType);
		
		cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[39]/td[11]/div/input", xCCCompanyName);
		//driver.findElement(By.xpath("//input[@aria-labelledby='EEMBL_Company_Name_Eng_Label']")).sendKeys(xCCCompanyName);
		Thread.sleep(2000);
		
	//	cu.select(driver,".//*[@id='a_2']/div/table/tbody/tr[13]/td[3]/div/input", xCCDiscount);
		
		/*driver.findElement(By.xpath(".//*[@id='s_2_1_8_0_icon']")).click();
		Thread.sleep(5000);
		String path = "//td[@title='".concat(xCCDiscount).concat("']");
		driver.findElement(By.xpath(path)).click();
		cu.clickWE(driver, "//button[text()='OK']");
		*/
		String busAccNo = driver.findElement(By.xpath("//a[@name='EEMBL Account Number']")).getText();
		
		report.writeTorun(runlog, "Business Account Number is "+busAccNo);
		
		cu.clickWE(driver, "//button[text()='Save']");
		
		Thread.sleep(20000);
		driver.findElement(By.partialLinkText("Business Accounts /")).click();
		
		Thread.sleep(10000);
		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_ID_Number_Label']")).sendKeys(xCDIdNo);
		cu.clickWE(driver, "//button[text()='Go']");
		
		Thread.sleep(20000);
		cu.clickWE(driver, "//a[text()='"+busAccNo+"']");
		
		Thread.sleep(10000);
		cu.clickWE(driver, "//button[text()='New']");
		
		Thread.sleep(5000);
		cu.clickWE(driver, "//button[text()='Save']");
		
		Thread.sleep(180000);
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			
			report.writeTorun(runlog,"Alert1 Accepted");
		}catch(NoAlertPresentException e) 
		
		{
			report.writeTorun(runlog,"Alert1 not present");
		}
		
		Thread.sleep(10000);
		//WebElement we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='New']")));
		String billingAccNo = driver.findElement(By.xpath(".//*[@id='1Account_Number']/a")).getText();
		report.writeTorun(runlog,"Master Billing Account Number is "+billingAccNo);
		
		FileInputStream fis = new FileInputStream("C:\\Automated_Execution\\Input\\TestData_CUCPostPaidRevamp.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheet("Activation");
		sheet.getRow(2).getCell(2).setCellValue(busAccNo);
		sheet.getRow(3).getCell(2).setCellValue(billingAccNo);
		fis.close();
		
		FileOutputStream fos = new FileOutputStream("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
		workbook.write(fos);
		fos.close();
		driver.quit();
		report.writeTorun(runlog,"Browser Closed");
	    report.writeTorun(runlog,"Customer Successfully Created from Siebel!\n\n");
	    //report.writeToFile(reportfile,"Customer Successfully Created from Siebel!\n\n");
	    

		PostProfileValidation_MDM profileCheckInMdmObj = new PostProfileValidation_MDM(runlog,reportfile,Postpre,report);
		int check = profileCheckInMdmObj.ProfileValidation_MDM(cellId);

		return check;
	
	}
}