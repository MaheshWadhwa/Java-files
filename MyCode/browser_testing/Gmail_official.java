package browser_testing;

import org.openqa.selenium.By;
import java.net.URI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Gmail_official {
	
	public static void main(String [] args) throws InterruptedException
	
	{
		
		System.setProperty("webdriver.gecko.driver","D://java life//geckodriver.exe");
		
		WebDriver driver = new FirefoxDriver();
		
		driver.get("https://mail.google.com/");
		
		//String currentUrl = driver.getCurrentUrl();
		
		
		WebElement inputemail = driver.findElement(By.id("identifierId"));
		
		inputemail.sendKeys("mahesh.wadhwa371");
		
        WebElement next = driver.findElement(By.id("identifierNext"));
		
		next.click();
		
		Thread.sleep(2000);
		
		//driver.navigate().forward();
		
		WebElement inputpassword = driver.findElement(By.id("password"));
		
		inputpassword.sendKeys("Jaishreeram@99");	
		
        WebElement submit = driver.findElement(By.id("passwordNext"));
		
		submit.click();
		
		Thread.sleep(5000);
		
		driver.navigate().to("https://mail.google.com/mail/u/0/#inbox");
		
		String newUrl = driver.getCurrentUrl();
		
		System.out.println(newUrl);
		
		if(!newUrl.equals("https://mail.google.com/mail/u/0/#inbox"))
			
		{
			System.out.println("\nThe password you entered is wrong");
			
		
		
		}
		
		else
			
			System.out.println("\n The Official Gmail is opened for you");
	}

}
