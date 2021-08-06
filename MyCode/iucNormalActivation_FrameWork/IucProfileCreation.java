package iucNormalActivation_FrameWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class IucProfileCreation {

	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public IucProfileCreation(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		IucProfileCreation.reportFile = reportFile;
		IucProfileCreation.datasetLogFile = datasetLogFile;
		IucProfileCreation.datasetFolderPath = datasetFolderPath;
		IucProfileCreation.utilities = utilities;
	} // end of constructor method


	public int createProfile(int cellId) throws IOException, InterruptedException{

		utilities.writeToFile(reportFile, System.lineSeparator()+"Create Profile in Siebel"+System.lineSeparator()+utilities.insertDashedLine("Create Profile in Siebel"));
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tCreate Profile in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		WebDriver driver = null;
		String browser = "Chrome";

		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_IUC.xlsx");
		FileInputStream inputStream = new FileInputStream(xlFile);


		Workbook testWorkBook = new XSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Application_Information");

		// Login Page Data
		String siebelUrl = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xUserName = testSheet.getRow(++row).getCell(2).getStringCellValue();
		String xPassword = testSheet.getRow(++row).getCell(2).getStringCellValue();

		// Customer Profile Creation Page
		row = 0;
		testSheet = testWorkBook.getSheet("Profile_Creation");
		String xIdType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		if(xIdType.equalsIgnoreCase("Skip")){
			testWorkBook.close();
			inputStream.close();
			utilities.writeToFile(datasetLogFile, "Profile Creation Skipped...");
			return 101;
		}
		String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xCategory = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xOccupation = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDateFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIdExpiry = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIssueDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xIssuePlace = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xTitle = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xFirstName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xLastName = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBirthDateFormat = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xBirthDate = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xLanguagePreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xContactPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMobile = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xEmail = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xNationality = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xGender = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xAddress = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xZipCode = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPoBox = testSheet.getRow(++row).getCell(cellId).getStringCellValue();


		testWorkBook.close();
		inputStream.close();


		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Automated_Execution\\Scripts\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Automated_Execution\\Scripts\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		
		//Property file declaration
		 FileInputStream objfile = new FileInputStream("C:\\Users\\j.dasari.mit\\j.dasari.mit_view\\vobs\\QualityControl\\Mobily\\FrameWork\\src\\IUCNormalActivation_OR\\IUCProfileCreation.properties");
		 Properties prop = new Properties();
		 prop.load(objfile);
		

		//Maximize
		driver.manage().window().maximize();


		driver.get(siebelUrl);
		utilities.writeToFile(datasetLogFile, "URL invoked");


		// Login Page
		driver.findElement(By.xpath(prop.getProperty("Siebel_User_name"))).sendKeys(xUserName);
		driver.findElement(By.xpath(prop.getProperty("SiebelLoginPSWD"))).sendKeys(xPassword);
		driver.findElement(By.xpath("SiebelLoginButton")).click();

		utilities.writeToFile(datasetLogFile, "Clicked on Login");

		//Click Customer Tab
		Thread.sleep(20000);
		driver.findElement(By.linkText("Customer")).click();
		utilities.writeToFile(datasetLogFile, "Clicked on Customer Tab");

		//Click Express Activation
		Thread.sleep(5000);
		WebElement select = driver.findElement(By.id(prop.getProperty("ExpressActivation")));
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if("Express Activation".equals(option.getText())) {
				option.click();
				utilities.writeToFile(datasetLogFile, "Clicked on Express Activation");
				break;
			}
		} 

		//Click New
		Thread.sleep(10000);
		driver.findElement(By.xpath(prop.getProperty("NewButton"))).click();
		utilities.writeToFile(datasetLogFile, "Clicked on New");

		//Fill Details
		Thread.sleep(5000);
		driver.findElement(By.xpath(prop.getProperty("ProfileText"))).sendKeys("Mobily ");
	    driver.findElement(By.xpath(prop.getProperty("IDNo"))).sendKeys(xIdNo);
	    driver.findElement(By.xpath(prop.getProperty("Category"))).sendKeys(xCategory);
	    driver.findElement(By.xpath(prop.getProperty("Occupation"))).sendKeys(xOccupation);
	    driver.findElement(By.xpath(prop.getProperty("DateFormat"))).sendKeys(xDateFormat);
	    driver.findElement(By.xpath(prop.getProperty("ExpiryID"))).sendKeys(xIdExpiry);
	    driver.findElement(By.xpath(prop.getProperty("IssueDate"))).sendKeys(xIssueDate);
	    driver.findElement(By.xpath(prop.getProperty("Issueplace"))).sendKeys(xIssuePlace);
	    Thread.sleep(2000);
	    
	    driver.findElement(By.xpath(prop.getProperty("Title"))).sendKeys(xTitle);
	    driver.findElement(By.xpath(prop.getProperty("FirstName"))).sendKeys(xFirstName);
	    driver.findElement(By.xpath(prop.getProperty("LastName"))).sendKeys(xLastName);
	    driver.findElement(By.xpath(prop.getProperty("BirthDateFormat"))).sendKeys(xBirthDateFormat);
	    driver.findElement(By.xpath(prop.getProperty("BirthDate"))).sendKeys(xBirthDate);
	    driver.findElement(By.xpath(prop.getProperty("ClearText"))).clear();
	    driver.findElement(By.xpath(prop.getProperty("LanguagePreference"))).sendKeys(xLanguagePreference);
	    driver.findElement(By.xpath(prop.getProperty("Contactpreference"))).sendKeys(xContactPreference);
	    driver.findElement(By.xpath(prop.getProperty("MobileNumber"))).sendKeys(xMobile);
	    driver.findElement(By.xpath(prop.getProperty("Email"))).sendKeys(xEmail);
	    driver.findElement(By.xpath(prop.getProperty("Nationality"))).sendKeys(xNationality);
	    driver.findElement(By.xpath(prop.getProperty("Gender"))).sendKeys(xGender);
	    driver.findElement(By.xpath(prop.getProperty("Address"))).sendKeys(xAddress);
	    driver.findElement(By.xpath(prop.getProperty("Clear"))).clear();
	    driver.findElement(By.xpath(prop.getProperty("ZipCode"))).sendKeys(xZipCode);
	    driver.findElement(By.xpath(prop.getProperty("PoBox"))).sendKeys(xPoBox);
		driver.findElement(By.xpath(prop.getProperty("ClearT"))).clear();
		driver.findElement(By.xpath(prop.getProperty("IdType"))).sendKeys(xIdType);
	    driver.findElement(By.xpath(prop.getProperty("ClearTest"))).clear();
	    driver.findElement(By.xpath(prop.getProperty("Title"))).sendKeys(xTitle);
	    driver.findElement(By.xpath(prop.getProperty("SaveButton"))).click();

		utilities.writeToFile(datasetLogFile, "Clicked on Save");

		Thread.sleep(20000);
		driver.findElement(By.linkText("Express Activation")).click();

		utilities.writeToFile(datasetLogFile, "Clicked on Express Activation");

		Thread.sleep(15000); 
		driver.findElement(By.linkText("Customer")).click();
		utilities.writeToFile(datasetLogFile, "Clicked on Customer");

	    Thread.sleep(6000);
	    driver.findElement(By.xpath(prop.getProperty("CustomerID"))).sendKeys(xIdNo);
	    driver.findElement(By.xpath(prop.getProperty("GoButton"))).click(); // Click on Go
		utilities.writeToFile(datasetLogFile, "Clicked on Go");

		Thread.sleep(10000);	
		utilities.createFolder(datasetFolderPath, "\\Profile_Creation");
		utilities.takeScreenShotMethod(driver, datasetFolderPath.concat("\\Profile_Creation"), "Profile Creation in Siebel");
		driver.quit();
		utilities.writeToFile(datasetLogFile, "Browser Closed");
		utilities.writeToFile(datasetLogFile, "Customer Successfully Created from Siebel!");
		utilities.writeToFile(reportFile, utilities.getDateTime()+" Profile Created in Siebel");

		ProfileCheckInMdmIuc profileCheckInMdmObj = new ProfileCheckInMdmIuc(reportFile,datasetLogFile,datasetFolderPath,utilities);
		int check = profileCheckInMdmObj.checkInMdm(xIdNo);

		return check;
	} // method
}