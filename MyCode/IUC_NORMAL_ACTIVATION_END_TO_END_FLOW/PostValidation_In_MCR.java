package IUC_NORMAL_ACTIVATION_END_TO_END_FLOW;

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

@SuppressWarnings("unused")
public class PostValidation_In_MCR 
{
	public static void main(String[] args) throws InterruptedException, IOException,ClassNotFoundException {
		
		
		//Data File
		File file_data=new File ("C:\\Automated_Execution\\Input\\PostValidation.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet worksheet = testdata.getSheet("MCR");
	    HSSFRow ro = worksheet.getRow(0);
	    String username = ro.getCell(1).getStringCellValue();
	    HSSFRow ro1 = worksheet.getRow(1);
	    String password = ro1.getCell(1).getStringCellValue();
	    HSSFRow ro2 = worksheet.getRow(2);
	    String msisdn = ro2.getCell(1).getStringCellValue();
	    HSSFRow ro3 = worksheet.getRow(3);
	    //String SIM = ro3.getCell(1).getStringCellValue();


		// TODO Auto-generated method stub
		WebDriver driver = null;
		String browser = "Chrome";
		String MCRurl = "http://10.6.37.119/MCR/Portal/members/Default.aspx";
//		driver.manage().window().maximize();
				
		//driver=new FirefoxDriver();
		
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}
		else if (browser=="IE")
		{
			System.setProperty("webdriver.ie.driver","C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}
		else if (browser=="Chrome")
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		
    	//Launching URL
		driver.get(MCRurl);
		System.out.println("URL invoked");

		//Login Page
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_UserName']")).sendKeys(username);
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_Password']")).sendKeys(password);
		driver.findElement(By.xpath(".//*[@id='uiLoginControl_LoginImageButton']")).click();
		System.out.println("Logged in successfully");
		Thread.sleep(4000);
		
		//MSISDN Search		
		driver.findElement(By.xpath(".//*[@id='aspnetForm']/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr[2]/td/table/tbody/tr[2]/td/a")).click();
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiMSISDN']")).sendKeys(msisdn);
		driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiQueryIntegByMSISDN']")).click();
		System.out.println("MSISDN submitted");
		Thread.sleep(3000);
		
		
		//TO VALIDATE ACCOUNT NUMBER	
		String AccountNo = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiActiveAccount']/tbody/tr[2]/td[1]/a")).getText();
		System.out.println("MSISDN Associated with Account Number in MCR is :" + AccountNo);
		
		//TO VALIDATE MSISDN NUMBER 		
		String MSISDN = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiActiveAccount']/tbody/tr[2]/td[3]/a")).getText();
		System.out.println("MSISDN Number in MCR is :" + MSISDN);
		
		//TO VALIDATE SIM NUMBER 
		String SIM = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_uiActiveAccount']/tbody/tr[2]/td[4]")).getText();
		System.out.println("MSISDN Associated with SIM Number in MCR is :" +SIM);
	}
}
		
		

