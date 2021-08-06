package com.example.helloworld;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Gmail_personal {
	
	public static void main(String [] args) throws InterruptedException
	{
	
	System.setProperty("webdriver.chrome.driver","/Users/mahesh.wadhwa/Documents/Java_life/chromedriver");
	ChromeDriver driver = new ChromeDriver();
	
	driver.get("https://mail.google.com/");
	
	WebElement inputemail = driver.findElement(By.id("Email"));
	
	inputemail.sendKeys("islandmahesh@gmail.com");
	
    WebElement next = driver.findElement(By.id("next"));
	
	next.click();
	
	Thread.sleep(2000);
	
	//driver.navigate().forward();
	
	WebElement inputpassword = driver.findElement(By.id("Passwd"));
	
	inputpassword.sendKeys("india@best1");
	
    WebElement submit = driver.findElement(By.id("signIn"));
	
	submit.click();

}
}
