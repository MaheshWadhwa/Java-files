package IucNormalActivation;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DataCheckInMdm {

	static String dataCheckFolderPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	public DataCheckInMdm(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		DataCheckInMdm.dataCheckFolderPath = dataCheckFolderPath;
		DataCheckInMdm.reportFile = reportFile;
		DataCheckInMdm.datasetLogFile = datasetLogFile;
		DataCheckInMdm.utilities = utilities;
	} // end of constructor method

	public int execute(String Msisdn, String Sim) throws InterruptedException, IOException {

		@SuppressWarnings("unused")
		int checkCounter = 0;
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MDM"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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
			if((driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).getText().equalsIgnoreCase("No contract found matching the search criterion")) && (driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[2]/label")).getText().equalsIgnoreCase("FATAL"))){
				utilities.writeToFile(datasetLogFile, "MSISDN Available for use");
			}
			else{
				utilities.writeToFile(datasetLogFile, "MSISDN Not Available. It should be cleaned for use.");
				checkCounter++;
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			try{
				if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Terminated")) { 
					utilities.writeToFile(datasetLogFile, "MSISDN Available for use");
				}
				else{
					utilities.writeToFile(datasetLogFile, "MSISDN Not Available. It should be cleaned for use.");
					checkCounter++;
					conditionCounter++;
				}

			} catch(NoSuchElementException e1){
				utilities.writeToFile(datasetLogFile, "Exception occured while checking MSISDN in MDM.");
			}
		}
		utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Data_Check_In_MDM_MSISDN");

		//**************************************************************************			
		//IF IT IS SIM
		//CLICK ON GET ALL CONTACT WITH PRODUCT LINK
		driver.findElement(By.xpath(".//*[@id='form1:j_id_c']/span[2]")).click();
		Thread.sleep(10000);

		//SELECT SIM OPTION FROM OTHER SEARCH CIRTERIA
		driver.findElement(By.xpath(".//*[@id='form2:j_id_5o_label']")).click();
		driver.findElement(By.xpath(".//*[@id='form2:j_id_5o_panel']/ul/li[2]")).click();

		//GIVE SIM 
		driver.findElement(By.xpath(".//*[@id='form2:input-paramValue']")).sendKeys(Sim);		
		Thread.sleep(4000);
		//CLICK ON SEARCH BUTTON
		driver.findElement(By.xpath(".//*[@id='form2:j_id_6c']")).click();		
		Thread.sleep(4000);

		try{
			if((driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).getText().equalsIgnoreCase("No contract found matching the search criterion")) && (driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[2]/label")).getText().equalsIgnoreCase("FATAL"))){
				driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).click();
				utilities.writeToFile(datasetLogFile, "SIM Available for use");
			}else{
				driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id7']/table/tbody/tr[1]/td[4]/label")).click();
				utilities.writeToFile(datasetLogFile, "SIM Not Available. It should be cleaned for use.");
				checkCounter++;
				conditionCounter++;
			}

		} catch(NoSuchElementException e){
			try{
				if(driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText().equalsIgnoreCase("Terminated")){
					driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).click();
					utilities.writeToFile(datasetLogFile, "SIM Available for use");
				}else{
					driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).click();
					utilities.writeToFile(datasetLogFile, "SIM Not Available. It should be cleaned for use.");
					checkCounter++;
					conditionCounter++;
				}
			} catch(NoSuchElementException e1){
				utilities.writeToFile(datasetLogFile, "Exception occured while checking SIM in MDM.");
			}
		}
		Thread.sleep(2000);
		utilities.takeScreenShotMethod(driver, dataCheckFolderPath, "Data_Check_In_MDM_SIM");
		driver.close();
		return conditionCounter;
	}
}