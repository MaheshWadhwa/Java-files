package com.example.helloworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ePortal {
	
	public static void main(String [] args) throws InterruptedException, FileNotFoundException
	
	{
		System.setProperty("webdriver.chrome.driver","D://java life/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://10.14.170.163/wps/myportal/personal/my-mobily/MyServices/Services Management/");
		driver.manage().window().maximize();
		WebElement userid = driver.findElement(By.id("userID"));
		userid.sendKeys("neqaty_success");
		Thread.sleep(500);
		WebElement Password = driver.findElement(By.id("password"));
		Password.sendKeys("Nandu_123");
		WebElement submit = driver.findElement(By.className("lotusFormButton"));
		submit.click();
		driver.navigate().forward();
		WebElement Personal = driver.findElement(By.linkText("Personal"));
		Personal.click();
		driver.navigate().forward();
		Thread.sleep(500);
		WebElement my_mobily = driver.findElement(By.linkText("My Mobily"));
		my_mobily.click();
		Thread.sleep(500);
		WebElement neqaty = driver.findElement(By.linkText("Neqaty"));
		neqaty.click();
		System.out.println("Current balance " + driver.findElement(By.className("current_balance")).getText());
		System.out.println("\nEarned Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_earned_content']/span[1]/strong")).getText());
		System.out.println("\nRedeemed Details " + driver.findElement(By.xpath(".//*[@id='dijit_layout_TabContainer_0_tablist_redeemed_content']/span[1]/strong")).getText());
		
		
		
		
		
		
		
		
		
		//driver.close();
		
		
	        }}
		
	


