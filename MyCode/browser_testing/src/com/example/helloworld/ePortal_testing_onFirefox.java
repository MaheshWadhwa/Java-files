package com.example.helloworld;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class ePortal_testing_onFirefox {

	public static void main(String[] args) throws InterruptedException {
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
	WebDriver driver = new FirefoxDriver();
		
		/*	 ePortal objExcelFile = new ePortal();
		int row = 0;
        
		File file = new File("C://Users//k.mahesh.mit//Desktop//credentials_office.xlsx");
		//String filename = objExcelFile.credentials_office.xlsx;
		FileInputStream inputStream = new FileInputStream(file);
		Workbook credentials = null;

	    //Find the file extension by splitting file name in substring and getting only extension name

	    String fileExtensionName = file.substring(file.indexOf("."));

	    //Check condition if the file is xlsx file

	    if(fileExtensionName.equals(".xlsx")){

	    //If it is xlsx file then create object of XSSFWorkbook class

	    credentials = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file

	    else if(fileExtensionName.equals(".xls")){

	        //If it is xls file then create object of XSSFWorkbook class

	        guru99Workbook = new HSSFWorkbook(inputStream);

	    }

	    //Read sheet inside the workbook by its name

	    Sheet guru99Sheet = guru99Workbook.getSheet(sheetName);

	    //Find number of rows in excel file

	    int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();

	    //Create a loop over all the rows of excel file to read it

	    for (int i = 0; i < rowCount+1; i++) {

	        Row row = guru99Sheet.getRow(i);

	        //Create a loop to print cell values in a row

	        for (int j = 0; j < row.getLastCellNum(); j++) {

	            //Print excel data in console

	            System.out.print(row.getCell(j).getStringCellValue()+"|| ");*/
	            
		     /*  System.setProperty("webdriver.chrome.driver","D://java life/chromedriver.exe");
		       ChromeDriver driver = new ChromeDriver(); */
	        	
	    		driver.get("http://10.14.170.163/wps/portal/!ut/p/a0/04_Sj9CPykssy0xPLMnMz0vMAfIj83Kt8jNTrMoLivV88tMz8_QLsh0VAVTAxWw!/");
	    		driver.manage().window().maximize();
	    		WebElement userid = driver.findElement(By.id("userID"));
	    		userid.sendKeys("neqaty_success");
	    		Thread.sleep(500);
	    		WebElement Password = driver.findElement(By.id("password"));
	    		Password.sendKeys("Nandu_123");
	    		WebElement submit = driver.findElement(By.className("lotusFormButton"));
	    		submit.click();
	    		driver.navigate().forward();
	    		Thread.sleep(5000);
	    		//WebElement Personal = driver.findElement(By.xpath(".//*[@id='?uri=nm:oid:sa.com.mobily.home.personal']/span"));
	    		//Personal.click();
	    		driver.findElement(By.partialLinkText("Personal")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(5000);
	    		//WebElement my_mobily = driver.findElement(By.linkText("My Mobily"));
	    		//my_mobily.click();
	    		driver.findElement(By.partialLinkText("My Mobily")).click();
	    		Thread.sleep(5000);
	    		//WebElement neqaty = driver.findElement(By.xpath(".//*[@id='my_account_nav']/ul/li[5]/a"));
	    		//neqaty.click();
	    		driver.findElement(By.xpath(".//*[@id='my_account_nav']/ul/li[5]/a")).click();
	    		Thread.sleep(5000);
	    		System.out.println("Current balance " + driver.findElement(By.className("current_balance")).getText());
	    		System.out.println("\nEarned Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_earned_content']/span[1]/strong")).getText());
	    		System.out.println("\nRedeemed Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_redeemed_content']/span[1]/strong")).getText());
	    		
	    		
	    		
		
		// TODO Auto-generated method stub

	}

}
