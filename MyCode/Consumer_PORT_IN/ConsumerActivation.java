package Consumer_PORT_IN;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ConsumerActivation {
	
	static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String Actipre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	static String device;
	static String  orderNumber;
	public ConsumerActivation(String runlog,String reportfile,String Actipre,CommonUtilitiesWriter report)
	{
		ConsumerActivation.runlog=runlog;
		ConsumerActivation.reportfile=reportfile;
		ConsumerActivation.Actipre=Actipre;
		ConsumerActivation.report=report;
	}
	
	public int ConActivation(int cellId) throws IOException, FileNotFoundException, InterruptedException 
	
	{
		
		if(device.equalsIgnoreCase("Skip"))
		{
			report.writeToFile(reportfile, System.lineSeparator()+"Consmer Activation Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		}
		else
		{
			report.writeToFile(reportfile, System.lineSeparator()+"Consmer Activation with device Started in Siebel"+System.lineSeparator()+"--------------------------");
			report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tConsmer Activation with device Started in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		}
		WebDriver driver = null;
		String browser = "Chrome";
		
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}
		
		//int cellId = iterations;
		int row = 0;

		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Consumer_MNP.xls");
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
		testSheet = testWorkBook.getSheet("Activation");
		String xIdNo = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xSim = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xImsi = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xMsisdn = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPackage = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xDeliveryPreference = testSheet.getRow(++row).getCell(cellId).getStringCellValue();
		String xPaymentType = testSheet.getRow(++row).getCell(cellId).getStringCellValue();

		report.writeTorun(runlog,"Read Data from Excel Successful");
		inputStream.close();
		//Maximize
		driver.manage().window().maximize();

		driver.get(siebelUrl);
		report.writeTorun(runlog,"URL invoked");

		// Login Page
		driver.findElement(By.xpath(".//*[@id='s_swepi_1']")).sendKeys(xUserName);
		driver.findElement(By.xpath(".//*[@id='s_swepi_2']")).sendKeys(xPassword);
		driver.findElement(By.xpath(".//*[@id='s_swepi_22']")).click();

		report.writeTorun(runlog,"Clicked on Login");

		//Click Customer Tab
		Thread.sleep(20000);
		driver.findElement(By.linkText("Customer")).click();
		report.writeTorun(runlog,"Clicked on Customer Tab");

		Thread.sleep(15000);
		driver.findElement(By.xpath(".//*[@id='s_S_A2_div']/div/form/div/table/tbody/tr/td/table/tbody/tr[3]/td[2]/table/tbody/tr/td/input")).sendKeys(xIdNo);
		driver.findElement(By.xpath(".//*[@id='s_2_1_13_0_Ctrl']")).click(); // Click on Go
		report.writeTorun(runlog,"Clicked on Go");

		Thread.sleep(10000);

		driver.findElement(By.xpath(".//*[@id='s_2_1_94_0_Ctrl']")).click();  // Click on New
		report.writeTorun(runlog,"Clicked on New (for test purpose)");
		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='s_2_1_98_0_Ctrl']")).click();  // Click on Cancel 
		report.writeTorun(runlog,"Clicked on Cancel (for test purpose)");

		Thread.sleep(5000);
		WebElement select1 = driver.findElement(By.id("j_s_sctrl_tabView"));
		List<WebElement> options1 = select1.findElements(By.tagName("option"));
		for (WebElement option : options1) {
			if("Express Activation".equals(option.getText())) {
				option.click();
				report.writeTorun(runlog,"Clicked on Express Activation");
				break;
			}
		} 

		Thread.sleep(8000);

		WebElement select2 = driver.findElement(By.id("j_s_vctrl_div_tabScreen"));
		List<WebElement> options2 = select2.findElements(By.tagName("option"));
		for (WebElement option : options2) {
			if("Express Activation - MNP".equals(option.getText())) {
				option.click();
				report.writeTorun(runlog,"Clicked on Express Activation - MNP");
				break;
			}
		}   
		
		Thread.sleep(8000);
		try{
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			report.writeTorun(runlog,"Alert1 Accepted");
		}catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert1 not present");
		}

		Thread.sleep(10000);
		driver.findElement(By.xpath("//button[@title='Service Details:New']")).click(); 
		//click on New
		report.writeTorun(runlog,"Clicked on New");

		Thread.sleep(20000);
		try
		{
			Alert alert2 = driver.switchTo().alert();
			alert2.accept();
			report.writeTorun(runlog,"Alert2 Accepted");
		}
		catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert2 not present");
		}

		Thread.sleep(5000);
		driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[4]/td[3]/div/input")).sendKeys(xSim); // SIM
		driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[5]/td[3]/div/input")).sendKeys(xMsisdn); // MSISDN
		driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[7]/td[3]/div/input")).sendKeys("Zain");
		
		driver.findElement(By.xpath(".//*[@id='a_1']/div/table/tbody/tr[4]/td[5]/div/input")).sendKeys(xPackage); // Package
		driver.findElement(By.xpath("//button[text()='Save']")).click(); // Click on Save
		report.writeTorun(runlog,"Clicked on Save");
		
		Thread.sleep(20000);
		try
		{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			report.writeTorun(runlog,"Alert3 Accepted");
		}catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert3 not present");
		}
		Thread.sleep(20000);
		WebElement we = new WebDriverWait(driver,20).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Service Details:Submit']")));
		orderNumber = driver.findElement(By.xpath(".//*[@id='1Order_Number']/a")).getText();
		report.writeTorun(runlog,"Order Number is "+orderNumber);
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Order");
		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell(0).setCellValue("ORDER_ID");
		HSSFRow r = sheet.createRow((short) 1);
		r.createCell(0).setCellValue(orderNumber);
		
		String File =Actipre.concat("\\order.xls");
		 FileOutputStream fileOut = new FileOutputStream(File,false);
	    workbook.write(fileOut);
	    fileOut.close();
	    System.out.println("Activation File has been created successfully ");
		inputStream.close();
		we.click();
		report.writeTorun(runlog,"Clicked on Submit");
		Thread.sleep(20000);
			
		try{
			Alert alert3 = driver.switchTo().alert();
			alert3.accept();
			report.writeTorun(runlog,"Alert4 Accepted");
			Thread.sleep(3000);
			
			we.click();
			report.writeTorun(runlog,"Clicked on Submit");
		}catch(NoAlertPresentException e){
			report.writeTorun(runlog,"Alert4 not present");
		}
		
		Thread.sleep(20000);
		String status = "";
		int counter = 0;
		while(!status.equals("Pending")){
			String path = "//a[text()='".concat(orderNumber).concat("']/../../td[3]");
			status = driver.findElement(By.xpath(path)).getText();
			counter++;
			if(counter == 5)
				break;
			report.writeTorun(runlog,"Order Status is "+status);
			Thread.sleep(10000);
		}
		report.writeTorun(runlog,"Order Status in Siebel - "+status);
		driver.quit();
		report.writeTorun(runlog,"Browser Closed");
		return 0;
		}
}