package IucNormalActivation;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class ProfileCheckInMdmIuc {

	static String datasetFolderPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	public ProfileCheckInMdmIuc(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		ProfileCheckInMdmIuc.datasetFolderPath = datasetFolderPath;
		ProfileCheckInMdmIuc.reportFile = reportFile;
		ProfileCheckInMdmIuc.datasetLogFile = datasetLogFile;
		ProfileCheckInMdmIuc.utilities = utilities;
	} // end of constructor method

	public int checkInMdm(String CustomerId) throws InterruptedException, IOException {

		@SuppressWarnings("unused")
		int checkCounter = 0;
		conditionCounter = 0;
		utilities.writeToFile(reportFile, System.lineSeparator()+"Validate Profile in MDM"+System.lineSeparator()+"-----------------------");
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MDM"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		WebDriver driver = null;
		String browser = "Firefox";
		String MDMUrl = "http://10.14.170.83:9084/Inquiry-ui/GetAllParty.jsf";

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}
		else if (browser=="IE")
		{
			System.setProperty("webdriver.ie.driver","C:\\Automated_Execution\\Scripts\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}
		else if (browser=="Chrome")
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Automated_Execution\\Scripts\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		driver.get(MDMUrl);
		driver.manage().window().maximize();

		Thread.sleep(4000);	

		// Selecting GCC ID
		driver.findElement(By.xpath(".//*[@id='form2:input-identificationType']/div[2]/span")).click();
		driver.findElement(By.xpath("//li[text()='GCC ID']")).click();
		Thread.sleep(2000);

		// Give Customer ID 
		driver.findElement(By.xpath(".//*[@id='form2:input-identificationNumber']")).sendKeys(CustomerId);		
		Thread.sleep(2000);

		// Click on Search button
		driver.findElement(By.xpath(".//*[@id='form2:j_id_32']")).click();		
		Thread.sleep(2000);

		try{
			if((driver.findElement(By.xpath("//label[text()='Result Code : ']/../../td[4]/label")).getText().equalsIgnoreCase("No Identifiers found for that party")) && (driver.findElement(By.xpath("//label[text()='Result Code : ']/../../td[2]/label")).getText().equalsIgnoreCase("FATAL"))){
				utilities.writeToFile(datasetLogFile, "Profile not seen in MDM...");
				checkCounter++;
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			try{
				if(driver.findElement(By.xpath("//label[text()='Party Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Active")){
					driver.findElement(By.xpath("//label[text()='Party Status Value : ']/../../td[4]/label")).click();
					utilities.writeToFile(datasetLogFile, "Profile seen in MDM..");
				}else{
					utilities.writeToFile(datasetLogFile, "Profile not Active in MDM...");
					checkCounter++;
					conditionCounter++;
				}
			} catch(NoSuchElementException e1){
				utilities.writeToFile(datasetLogFile, "Exception occured while checking Profile in MDM.");
			}
		}
		Thread.sleep(2000);
		utilities.takeScreenShotMethod(driver, datasetFolderPath.concat("\\Profile_Creation"), "Profile Validation in MDM");
		driver.close();
		return conditionCounter;
	}
}