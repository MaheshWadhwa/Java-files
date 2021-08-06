package IucNormalActivation;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class PostActivationCheckInMdm {

	static String dataCheckFolderPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;


	public PostActivationCheckInMdm(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		PostActivationCheckInMdm.dataCheckFolderPath = dataCheckFolderPath;
		PostActivationCheckInMdm.reportFile = reportFile;
		PostActivationCheckInMdm.datasetLogFile = datasetLogFile;
		PostActivationCheckInMdm.utilities = utilities;
	} // end of constructor method

	public int execute(String Msisdn, String Sim) throws InterruptedException, IOException {

		@SuppressWarnings("unused")
		int checkCounter = 0;
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in MDM"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		WebDriver driver = null;
		String browser = "Firefox";
		String MDMUrl = "http://10.14.170.83:9084/Inquiry-ui/GetAllContract.jsf";

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

		driver.findElement(By.xpath(".//*[@id='form2:input-msisdn']")).sendKeys(Msisdn);		
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='form2:j_id_2x']")).click();
		Thread.sleep(5000);		


		try{
			if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Active")) { 
				utilities.writeToFile(datasetLogFile, "MSISDN is Active in MDM");
			}
			else{
				utilities.writeToFile(datasetLogFile, "MSISDN Not Active in MDM");
				checkCounter++;
				conditionCounter++;
			}
			if(driver.findElement(By.xpath("//label[text()='SIM : ']/../../td[4]/label")).getText().equals(Sim)){
				utilities.writeToFile(datasetLogFile, "Associated SIM in MDM - "+Sim);
			}else{
				utilities.writeToFile(datasetLogFile, "Another SIM seen in MDM");
				checkCounter++;
				conditionCounter++;
			}
			if(driver.findElement(By.xpath("//label[text()='Account Number : ']/../../td[2]/label")).getText().equals(PostActivationCheckInBilling.account_no)){
				utilities.writeToFile(datasetLogFile, "Account Number in MDM - "+PostActivationCheckInBilling.account_no);
			}else{
				utilities.writeToFile(datasetLogFile, "Another Account Number seen in MDM");
				checkCounter++;
				conditionCounter++;
			}

		} catch(NoSuchElementException e1){
			utilities.writeToFile(datasetLogFile, "Exception occured while checking MSISDN in MDM.");
		}
		driver.findElement(By.xpath("//label[text()='Executed Date : ']")).click();
		utilities.takeScreenShotMethod(driver, dataCheckFolderPath.concat("\\Post_Activation_Validation"), "Post_Activation_Check_In_MDM_MSISDN");

		driver.close();
		return conditionCounter;
	}
}