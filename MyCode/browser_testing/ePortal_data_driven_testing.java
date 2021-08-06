package browser_testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ePortal_data_driven_testing {
	
	
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException, InterruptedException{

	    //Create a object of File class to open xlsx file

	    File file =    new File(filePath+"\\"+fileName);

	    //Create an object of FileInputStream class to read excel file

	    FileInputStream inputStream = new FileInputStream(file);

	    Workbook workbook = null;

	    //Find the file extension by spliting file name in substring and getting only extension name

	    String fileExtensionName = fileName.substring(fileName.indexOf("."));

	    //Check condition if the file is xlsx file

	    if(fileExtensionName.equals(".xlsx")){

	    //If it is xlsx file then create object of XSSFWorkbook class

	    workbook = new XSSFWorkbook(inputStream);

	    }

	    //Check condition if the file is xls file

	    else if(fileExtensionName.equals(".xls")){

	        //If it is xls file then create object of XSSFWorkbook class

	        workbook = new HSSFWorkbook(inputStream);

	    }

	    //Read sheet inside the workbook by its name

	    Sheet worksheet = workbook.getSheet(sheetName);

	    //Find number of rows in excel file

	    int rowCount = worksheet.getLastRowNum()-worksheet.getFirstRowNum();

	    //Create a loop over all the rows of excel file to read it

	   
	    
	    FirefoxDriver driver 	= new FirefoxDriver();
	driver.get("http://10.14.170.163/wps/myportal/personal/my-mobily/MyServices/Services Management/");
	    		driver.manage().window().maximize();
	    		WebElement userid = driver.findElement(By.id("userID"));
	    		WebElement password = driver.findElement(By.id("password"));
	    		
	    		for (int i = 0; i < rowCount+1; i++) {

	    	        Row row = worksheet.getRow(i);

	    	        //Create a loop to print cell values in a row

	    	        for (int j = 0; j < row.getLastCellNum(); j++) {

	    	            //Print excel data in console

	    	           // System.out.print(row.getCell(j).getStringCellValue()+"|| ");
	    	            //try
	    	            
	    	            if(row.getCell(j).getStringCellValue().equals("Eportal"))
	    	            {
	    	            	j= j+1;
	    	            	userid.sendKeys(row.getCell(j).getStringCellValue());
	    	            	Thread.sleep(500);
	    	            	j= j+1;
	    	            	password.sendKeys(row.getCell(j).getStringCellValue());
	    	            	
	    	            }
	    	            
	    	        
	    	            }
	    		}
	    		
	    		
	    		WebElement submit = driver.findElement(By.className("lotusFormButton"));
	    		submit.click();
	    		driver.navigate().forward();
	    		Thread.sleep(5000);
	    		//WebElement Personal = driver.findElement(By.xpath(".//*[@id='?uri=nm:oid:sa.com.mobily.home.personal']/span"));
	    		//Personal.click();
	    		driver.findElement(By.xpath(".//*[@id='?uri=nm:oid:sa.com.mobily.home.personal']/span")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(5000);
	    		WebElement my_mobily = driver.findElement(By.partialLinkText("My Mobily"));
	    		my_mobily.click();
	    		//driver.findElement(By.xpath(".//*[@id='nav']/portlet:defineobjects/li/a")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(9000);
	    		//WebElement neqaty = driver.findElement(By.xpath(".//*[@id='my_account_nav']/ul/li[5]/a"));
	    		//neqaty.click();
	    		driver.findElement(By.xpath(".//*[@id='my_account_nav']/ul/li[5]/a")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(9000);
	    		System.out.println("Current balance " + driver.findElement(By.className("current_balance")).getText());
	    		System.out.println("\nEarned Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_earned_content']/span[1]/strong")).getText());
	    		System.out.println("\nRedeemed Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_redeemed_content']/span[1]/strong")).getText());
	    		
	    		
	}
	    		public static void main(String[] args) throws InterruptedException, IOException {
	    			
	    			
	    			
	    			
		
	    		
	    			
	    			System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
	    			
	    		
	    		
	    		////Create a object of Timesheet_data_driven class
	    		
	    			ePortal_data_driven_testing objexcelfile = new ePortal_data_driven_testing();
	    		
	    		String filePath = "D://java life";
	    		
	    		//Call read file method of the class to read data

	    	    objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office");


	    		

	    			
	    			
	    			
	    		}


}

