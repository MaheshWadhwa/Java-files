package com.example.helloworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Data_driven_testing_read_write_to_Excel {
	
	
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

	

	   
	    
	    FirefoxDriver driver 	= new FirefoxDriver();
	
	

	
		
	        	
	    		driver.get("http://10.14.170.163/wps/portal/!ut/p/a0/04_Sj9CPykssy0xPLMnMz0vMAfIj83Kt8jNTrMoLivV88tMz8_QLsh0VAVTAxWw!/");
	    		driver.manage().window().maximize();
	    		WebElement userid = driver.findElement(By.id("userID"));
	    		WebElement password = driver.findElement(By.id("password"));
	    	    //Create a loop over all the rows of excel file to read it
	    		for (int i = 0; i < rowCount+1; i++) {

	    	        Row row = worksheet.getRow(i);

	    	        //Create a loop to print cell values in a row

	    	        for (int j = 0; j < row.getLastCellNum(); j++) {

	    	            //Print excel data in console

	    	           // System.out.print(row.getCell(j).getStringCellValue()+"|| ");
	    	            
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
	    		Thread.sleep(7000);
	    		//WebElement Personal = driver.findElement(By.xpath(".//*[@id='?uri=nm:oid:sa.com.mobily.home.personal']/span"));
	    		//Personal.click();
	    		driver.findElement(By.xpath(".//*[@id='?uri=nm:oid:sa.com.mobily.home.personal']/span")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(7000);
	    		WebElement my_mobily = driver.findElement(By.partialLinkText("My Mobily"));
	    		my_mobily.click();
	    		//driver.findElement(By.xpath(".//*[@id='nav']/portlet:defineobjects/li/a")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(7000);
	    		//WebElement neqaty = driver.findElement(By.xpath(".//*[@id='my_account_nav']/ul/li[5]/a"));
	    		//neqaty.click();
	    		driver.findElement(By.xpath(".//*[@id='my_account_nav']/ul/li[5]/a")).click();
	    		driver.navigate().forward();
	    		Thread.sleep(7000);
	    		System.out.println("Current balance " + driver.findElement(By.className("current_balance")).getText());
	    		System.out.println("\nEarned Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_earned_content']/span[1]/strong")).getText());
	    		System.out.println("\nRedeemed Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_redeemed_content']/span[1]/strong")).getText());
	    		
	    		return;
	    		
	    		}
	
	public void writeExcel(String filePath,String fileName,String sheetName, String[][] dataToWrite) throws IOException{
		
          
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

	        workbook = new HSSFWorkbook(inputStream);}
	        
	      //Read sheet inside the workbook by its name

		    Sheet worksheet = workbook.getSheet(sheetName);

		    

		    
		    
		  //Get the first row from the sheet

		    Row row = worksheet.getRow(0);

		    

		    

		    //Create a loop over the cell of newly created Row
		    
		     int size = dataToWrite.length;
		     
		     System.out.println(" \n The length of array  is " + size);
		     
		     int i = 0;
		    int z = 0;
			do
		    {
				//Find number of rows in excel file
				int rowCount = worksheet.getLastRowNum()-worksheet.getFirstRowNum();
				//Create a new row and append it at last of sheet
				Row newRow = worksheet.createRow(rowCount+1);
				
		    for(int j = 0; j < row.getLastCellNum(); j++){

		        //Fill data in row

		        Cell cell = newRow.createCell(j);
		        
		        cell.setCellValue(dataToWrite[i][j]);
		        
		    }
		     z++;i++;
		    }while(z<size);

		    //Close input stream

		   inputStream.close();

		    //Create an object of FileOutputStream class to create write data in excel file

		    FileOutputStream outputStream = new FileOutputStream(file);

		    //write data in the excel file

		    workbook.write(outputStream);

		    //close output stream

		  outputStream.close();
		  
		 

		    return;

	        

	    }
		
		
		
	
	            
	    		public static void main(String[] args) throws InterruptedException, IOException {
	    			
	    			
	    			
	    			
		
	    		
	    			
	    			System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
	    			
	    			//Create an array with the data in the same order in which you expect to be filled in excel file
	    			
	    			
	    		
	    		////Create a object of Timesheet_data_driven class
	    		
	    			Data_driven_testing_read_write_to_Excel objexcelfile = new Data_driven_testing_read_write_to_Excel();
	    		
	    			
	    			
	    		String filePath = "D://java life";
	    		
	    		//Write on  the excel sheet

	    	    System.out.println("\n The data is being written on the output excel file");
	    	    
	    	    String[][] valueToWrite = {{"Mr.","bangalore","ujala"},{"mahesh ji","rajan","wadhwa"}};
	    		
	    	   // String filePath1 = "D://java life";
	    	    
	    	    objexcelfile.writeExcel(filePath,"output.xlsx","output2",valueToWrite);
	    			
	    			System.out.println("\n The Output is written to the Output excel file");
	    		
	    		//Call read file method of the class to read data

	    	  //  objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office");
	    	    
	    	    
	    			
	    		}


}

