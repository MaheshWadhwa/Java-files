package CUC_Number_Swap;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
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

import CUC_Number_Swap.CommonUtilitiesWriter;


public class Postvalid_Siebel_UI 
{

	static int row=6;
	static int cellId=NumSwapMain.cellId;
	static Statement stmt;
	static String MSISDN;
	static String newMSISDN;
	static String SIM;
	static String username;
	static String password;
	static String File;
	static String Fil;
	static String post_path=NumSwapMain.Postpre;
	static String Billing_account;
	static String account=Postvalid_billing.account_no;
	//static String orderid=CUC_NumberSwap.oreder_id;
	static String url;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=NumSwapMain.runlog;
	static String reportfile=NumSwapMain.reportfile;
	static int conditionCounter=0;
	
	
	public int post_siebel()throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		conditionCounter=0;
		data_read();
		siebel();
		return conditionCounter;
		
	}
    
	public static void siebel() throws ClassNotFoundException, SQLException, IOException , NullPointerException, InterruptedException
	{
		WebDriver driver = null;
		String browser = "Chrome";
		
		if (browser=="Firefox") 
		{
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
		
		driver.get(url);
		report.writeTorun(runlog,"URL invoked");
		driver.manage().window().maximize();

		//Login

		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(username);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(password);
		//Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();
		Thread.sleep(20000);
		System.out.println("Logged in succefully to Siebel");
				
		
		try{
			Alert alert0 = driver.switchTo().alert();
			alert0.accept();
			
			report.writeToFile(runlog, "Password Expire Alert Accepted");
		}catch(NoAlertPresentException e){
			
			report.writeToFile(runlog, "Password Expire Alert not present");
		}
		
		Thread.sleep(15000);
	
		
		//New MSISDN
		// Go to Services tab
		
		report.clickWE(driver, "//a[text()='Services']");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@aria-labelledby='EECC_MSISDN_Label']")).sendKeys(newMSISDN);
		report.clickWE(driver, ".//*[@id='s_1_1_22_0_Ctrl']");
		Thread.sleep(5000);

		// Check for service
		
		String postPath = "C:\\Automated_Execution\\Output";
		String postcheckFolderPath = "C:\\Automated_Execution\\Output";
		
		
		
		
		try{
			if(driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]"))).getText().equals("Active")){
				WebElement element = driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]")));
				report.takeScreenShotMethodWE(driver, postPath, "Siebel_Services_Tab.jpg",element);
				report.writeToFile(runlog, "Service is Active in Siebel");
				report.writeToFile(reportfile, "Service is Active in Siebel");
			} else {
				WebElement element = driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]")));
				report.takeScreenShotMethodWE(driver, postcheckFolderPath, "Siebel_Services_Tab.jpg",element);
				report.writeToFile(runlog, "Service is Not Active in Siebel");
				report.writeToFile(reportfile, "Service is Not Active in Siebel");
				conditionCounter++;
			}
		} catch(NoSuchElementException e){
			report.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Services_Tab.jpg");
			report.writeToFile(runlog, "Service Not Found in Siebel for Account "+account);
			report.writeToFile(reportfile, "Service Not Found in Siebel for Account "+account);
			conditionCounter++;
		}



		//Old MISISDN Check
		// Go to Services tab
		
				report.clickWE(driver, "//a[text()='Services']");
				Thread.sleep(5000);
				
				driver.findElement(By.xpath("//input[@aria-labelledby='EECC_MSISDN_Label']")).sendKeys(MSISDN);
				report.clickWE(driver, ".//*[@id='s_1_1_22_0_Ctrl']");
				Thread.sleep(5000);

				// Check for service
				
				try{
					if(driver.findElement(By.xpath("//td[text()='".concat(account).concat("']/../td[5]"))).getText().equals("Active")){
						//report.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Services_Tab.jpg");
						report.writeToFile(runlog, "Service is Active in Siebel for old MISISDN - Fail");
						report.writeToFile(reportfile, "Service is Active in Siebel  for old MSISDN - Fail");
						conditionCounter++;
					}
				
				}
				catch(NoSuchElementException e){
					//report.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Services_Tab.jpg");
					report.writeToFile(runlog, "Service is Not Active in Siebel old MISISDN - Pass");
					report.writeToFile(reportfile, "Service is Not Active in Siebel for new MSISDN old MISISDN  - Pass");
					
				}
		
		
		
		
		
		
		/*// Go to Orders tab
		report.clickWE(driver, "//a[text()='Orders']");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//input[@aria-labelledby='Order_Number_Label']")).sendKeys(orderid);
		report.clickWE(driver, "//button[text()='Go']");
		Thread.sleep(5000);

		// Check for Order
		try{
			if(driver.findElement(By.xpath("//a[text()='".concat(account).concat("']/../../td[4]"))).getText().equals("Applied")){
				//report.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Orders_Tab.jpg");
				report.writeToFile(runlog, "Order is Applied in Siebel");
				report.writeToFile(reportfile, "Order is Applied in Siebel");
			}
		} catch(NoSuchElementException e){
			//report.takeScreenShotMethod(driver, postcheckFolderPath, "Siebel_Orders_Tab.jpg");
			report.writeToFile(runlog, "Service is Not Applied in Siebel");
			report.writeToFile(reportfile, "Service is Not Applied in Siebel");
			conditionCounter++;
		}

*/
		//Validation
		//Clicking on Services
		/*driver.findElement(By.xpath("//a[text()='Services']")).click();
		Thread.sleep(7000);
		report.writeTorun(runlog,"Clicked on Services");

		report.writeTorun(runlog,"Service check");
		driver.findElement(By.xpath(".//*[@id='s_S_A1_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td/input")).sendKeys(MSISDN);
		driver.findElement(By.xpath(".//*[@id='s_1_1_22_0_Ctrl']")).click();
		report.writeTorun(runlog,"MSISDN search");
		Thread.sleep(4000);

		
		

		
		driver.findElement(By.xpath(".//*[@id='s_1_1_11_0_Ctrl']")).click();
		Thread.sleep(4000);
		WebElement ms=driver.findElement(By.xpath(".//*[@id='1_EECC_MSISDN']"));
		ms.click();
		Thread.sleep(4000);
		ms.sendKeys(MSISDN);

		WebElement st=driver.findElement(By.xpath(".//*[@id='1Account_Status']"));
		st.click();
		Thread.sleep(4000);
		st.sendKeys("Active");

		driver.findElement(By.xpath(".//*[@id='s_1_1_8_0_Ctrl']")).click();
		report.writeTorun(runlog,"Clicked on go");
		Thread.sleep(6000);

		String status=driver.findElement(By.xpath("//td[text()='Active']")).getText();

		if(status.equals("Active"))
		{
			report.writeTorun(runlog,"Success || MSISDN status is" +status);
			//report.writeToFile(reportfile,"Post validation in Siebel(MSISDN) - Pass");
		}
		else
		{
			report.writeTorun(runlog,"Failed || MSISDN status is" +status);

			//report.writeToFile(reportfile,"Post validation in Siebel(MSISDN) - Failed");}

			String acct=driver.findElement(By.xpath(".//*[@id='1Account_Number']")).getText();

			if (acct.equals(account))
			{
				report.writeTorun(runlog,"Account # mathced and activation is success");
				//report.writeToFile(reportfile,"Post validation in Siebel(Account) - Pass");
			}
			else
			{
				report.writeTorun(runlog,"Account # not mathced and activation is unsuccess");
				//report.writeToFile(reportfile,"Post validation in Siebel(Account) - Fail");
			}


			//order check

			report.writeTorun(runlog,"Order check");
			System.out.println("Order check");
			driver.findElement(By.xpath("//a[text()='Orders']")).click();;
			Thread.sleep(6000);
			report.writeTorun(runlog,"Clicked Order ");

			WebElement we=driver.findElement(By.xpath(".//*[@id='s_S_A2_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td/input"));
			we.click();
			we.sendKeys(orderid);

			driver.findElement(By.xpath(".//*[@id='s_2_1_4_0_Ctrl']")).click();
			Thread.sleep(5000);

			report.writeTorun(runlog,"Cllicked on go");

			String stat=driver.findElement(By.xpath(".//*[@id='1Status']")).getText();

			if(stat.equals("Applied"))
			{
				report.writeTorun(runlog,"Oreder created successfull and status is " +stat);
				//report.writeToFile(reportfile,"Order creation in Siebel - Pass");
			}

			else
			{
				report.writeTorun(runlog,"Oreder created unsuccessfull");

				//report.writeToFile(reportfile,"Ordere creation in Siebel - Fail");
			}
		}
*/
			/*String billac=driver.findElement(By.xpath(".//*[@id='1EECC_Billing_Account_Number']/a")).getText();

		if(billac.equals(Billing_account))
		{
			System.out.println("Billing account number matched and oreder successfull ");
		}

		else
		{
			System.out.println("Billing account number no matched and oreder unsuccessfull");
		}
			 */

		}


		public static void data_read() throws IOException
		{
			//row=2;
			File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC_NumSwap.xls");
			FileInputStream inputstrm= new FileInputStream(file_data);
			HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
			HSSFSheet testSheet1 = testdata.getSheet("Activation");
			MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
			newMSISDN=testSheet1.getRow(4+row).getCell(cellId).getStringCellValue();
			
			HSSFSheet testSheet = testdata.getSheet("Application_Information");
			HSSFRow ro2 = testSheet.getRow(2);
			username=ro2.getCell(2).getStringCellValue();
			HSSFRow ro3 = testSheet.getRow(3);
			password=ro3.getCell(2).getStringCellValue();
			HSSFRow ro4 = testSheet.getRow(1);
			url=ro4.getCell(2).getStringCellValue();

			//Billing_account=ro4.getCell(1).getStringCellValue();
			report.writeTorun(runlog,"Data read successfully");


		}
	}
