package CUC_Number_Swap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import CUC_Number_Swap.CommonUtilitiesWriter;


public class CUC_NumberSwap{
	
	static String reportfile=NumSwapMain.reportfile;
	CommonUtilitiesWriter report=new CommonUtilitiesWriter();

	public int numberswap() throws InterruptedException, IOException 
	{
		// TODO Auto-generated method stub
		
		WebDriver driver = null;
		String browser = "Chrome"; 
		//int conditionCounter=0;
	
		
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome")
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC_NumSwap.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
		
		HSSFWorkbook testWorkBook = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet = testWorkBook.getSheet("Application_Information");
		
		// Login Page Data
		HSSFRow row1 = testSheet.getRow(1);
		String siebelUrl = row1.getCell(2).getStringCellValue();
		HSSFRow row2=testSheet.getRow(2);
		String xUserName = row2.getCell(2).getStringCellValue();
		HSSFRow row3=testSheet.getRow(3);
		String xPassword = row3.getCell(2).getStringCellValue();
		
		HSSFRow row4=testSheet.getRow(4);
		String xMSISDN = row4.getCell(2).getStringCellValue();
		
		
		HSSFRow row5=testSheet.getRow(5);
		String newMSISDN = row5.getCell(2).getStringCellValue();
		System.out.println("Application info read");
		
		//Launching Siebel URL
		driver.manage().window().maximize();
		driver.get(siebelUrl);
		

		//Login in
		
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();
		Thread.sleep(20000);
		//Clicking on services
		
		driver.findElement(By.xpath("//a[text()='Services']")).click();
		Thread.sleep(4000);
		
		//Submititng MSISDN
		driver.findElement(By.xpath(".//*[@id='s_S_A1_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td/input"));
		driver.findElement(By.xpath(".//*[@id='s_1_1_22_0_Ctrl']")).click();
		
		//Clicking on Active  MSISDN
		WebElement we=driver.findElement(By.xpath("//td[text()='Active']/../td[2]/a"));
		String msisdn=we.getText();
		if(xMSISDN==msisdn)
		{
			we.click();
			System.out.println("Clicked on MSISDN"+msisdn);
			Thread.sleep(5000);
		}
		
		else
		{
			//TBD
		}
		
		//Clicking on sales service
		
		driver.findElement(By.xpath("//a[text()='Sales Services']")).click();
		
		//Clicking on MISISDN Swap
		
		driver.findElement(By.xpath("//a[text()='MSISDN Swap']")).click();
		
		//Clicking on new
		
		driver.findElement(By.xpath("//button[text()='New']")).click();
		
		//Enter new MSISDN
		
		WebElement wb=driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[4]/td[6]/div/input"));
		wb.click();
		wb.sendKeys(newMSISDN);

		//Clicking on save
		
		driver.findElement(By.xpath(".//*[@id='s_1_1_6_0_Ctrl']")).click();
		
		//CLicking on Submit

		
		report.writeToFile(reportfile,"Number swap oreder submitted successfully");
//Code to complete	
		return 0;
	}
	
}
