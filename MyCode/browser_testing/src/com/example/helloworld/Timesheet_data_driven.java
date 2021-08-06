package com.example.helloworld;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;

public class Timesheet_data_driven {
	
	
	public void readExcel(String filePath,String fileName,String sheetName) throws IOException{

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

	    for (int i = 0; i < rowCount+1; i++) {

	        Row row = worksheet.getRow(i);

	        //Create a loop to print cell values in a row

	        for (int j = 0; j < row.getLastCellNum(); j++) {

	            //Print excel data in console

	            System.out.print(row.getCell(j).getStringCellValue()+"|| ");

	        }

	        System.out.println();

	    }
	    
	    FirefoxDriver driver 	= new FirefoxDriver();

		driver.get("http://10.12.242.12:8080/tsrt/timeSheetAction.do");
		
		WebElement user = driver.findElement(By.xpath(".//*[@id='TSLF_userName']"));
		user.sendKeys("k.mahesh.mit");
		
		WebElement password = driver.findElement(By.xpath(".//*[@id='TSLF_password']"));
		password.sendKeys("year2017@123");
		
		WebElement login = driver.findElement(By.xpath(".//*[@id='LoginButtonID']"));
		login.click();
		
		System.out.println("The timesheet is opened for you to fill");
		driver.manage().window().maximize();
		
			
			driver.close();
	    

	    }
	
	public static void main(String [] args) throws IOException
	
	{
		
		System.setProperty("webdriver.gecko.driver","D:/java life/geckodriver.exe");
		
	
	
	////Create a object of Timesheet_data_driven class
	
	Timesheet_data_driven objexcelfile = new Timesheet_data_driven();
	
	String filePath = "D://java life";
	
	//Call read file method of the class to read data

    objexcelfile.readExcel(filePath,"credentials_office.xlsx","Credentials_office");
}}
