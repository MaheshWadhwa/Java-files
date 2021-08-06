package IUC_NORMAL_ACTIVATION_END_TO_END_FLOW;
import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.CommonUtilitiesWriterOLD;


public class PostValidation_In_MDM {
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		//Launching Firefox 	
		WebDriver driver = null;
		String MDMUrl = "http://10.14.170.83:9084/Inquiry-ui/GetAllContract.jsf"; 
	//	System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
		driver= new FirefoxDriver();
		driver.manage().window().maximize();
		CommonUtilitiesWriterOLD cu = new CommonUtilitiesWriterOLD();
		Thread.sleep(1000);	
		
		//Open MDM		
		driver.get(MDMUrl);	
		Thread.sleep(4000);	
		
		//Read data from Excel 
		int cellId = 2;		
		File xlFile = new File("C:\\Automated Execution\\Input\\PostDataValidtion_MDM.xlsx");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
		@SuppressWarnings("resource")
		Workbook testWorkBook = new XSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Sheet1");
		
		//Input Information
		String Msisdn = testSheet.getRow(1).getCell(cellId).getStringCellValue();
		String SIM = testSheet.getRow(2).getCell(cellId).getStringCellValue();
		System.out.println("Read Data from Excel Successful");			
		
		//Select Filter Value as All from Drop down List		
/*		WebElement element=driver.findElement(By.id("form2:j_id_k_label"));
		element.click();
		element.getText();
		System.out.println("Initial Drop Down values is " + element.getText());
		driver.findElement(By.xpath(".//*[@id='form2:j_id_k_panel']/ul/li[8]")).click();
		Thread.sleep(2000);	
		System.out.println("Initial Drop Down values is " + element.getText());	
		Thread.sleep(2000);	*/	
	
		
		driver.findElement(By.xpath(".//*[@id='form2:input-msisdn']")).sendKeys(Msisdn);		
		Thread.sleep(4000);
		driver.findElement(By.xpath(".//*[@id='form2:j_id_2x']")).click();
		Thread.sleep(5000);		
		System.out.println("To Check the Availability of MSISDN is :"+ Msisdn);		
		Thread.sleep(5000);		
		
				
		// TO VALIDATE MSISDN STATUS
/*		try{
		if(driver.findElement(By.xpath(".//*[@id='form2:accordion:j_id8:j_id10']/table/tbody/tr[2]/td[4]/label")).getText().equalsIgnoreCase("Active"))
			System.out.println("MIISDN Status is Active in MDM ");
		} catch(NoSuchElementException e){
			 System.out.println("Exception");
			System.out.println("MIISDN Status is Active in MDM ");
		}*/
		
		//TO VALIDATE MSISDN STATUS		
		String MSISDNStatus = driver.findElement(By.xpath("//label[text()='Contract Status Value : ']/../../td[4]/label")).getText();
		System.out.println("MSISDN Nubmer Status in MDM is :" + MSISDNStatus);
		
		//TO VALIDATE SIM NUMBER 		
		String SimNo = driver.findElement(By.xpath("//label[text()='SIM : ']/../../td[4]/label")).getText();
		System.out.println("MSISDN Associated with SIM Number in MDM is :" + SimNo);
		
		//TO VALIDATE ACCOUNT NUMBER 
		String AcctNo = driver.findElement(By.xpath("//label[text()='Account Number : ']/../../td[2]/label")).getText();
		System.out.println("MSISDN Associated with Account Number in MDM is :" +AcctNo);
		
		
		
	}
}


