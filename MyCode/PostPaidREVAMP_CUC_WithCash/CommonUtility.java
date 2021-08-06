package PostPaidREVAMP_CUC_WithCash;

import java.io.PrintWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtility {

	static PrintWriter writer = null;

	/*public void CommonUtilitiesWriter(PrintWriter writer){
		CommonUtilitiesWriter.writer = writer;
	}*/



	public WebDriver invokeBrowser(WebDriver driver,String browser,String URL){

				
		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}

		driver.get(URL);

		return driver;
	}

	public void clickWE(WebDriver driver,String strPath){
		WebElement we = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath(strPath)));
		//writer.println("Clicked on "+we.getText());
		we.click();

	}
	public void select(WebDriver driver, String strPath, String strOpt)
	{
		WebElement we = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath(strPath)));
		we.click();
		we.sendKeys(strOpt);
	}

	public void selectDD(WebDriver driver, String strPath, String strOpt){
		WebElement we = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath(strPath)));
		we.click();
		driver.findElement(By.linkText(strOpt)).click();
		//writer.println("Clicked on "+strOpt);

	}

	public void selectOption(WebDriver driver,String selectTagID,String opt){
		WebElement select1 = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.id(selectTagID)));
		List<WebElement> options1 = select1.findElements(By.tagName("option"));
		for (WebElement option : options1) {
			if(option.getText().contains(opt)) {
				option.click();
				
				System.out.println("Clicked on "+opt);
				break;


			}
		}
	}

	public void StaleElementHandleByLinkText (WebDriver driver, String elementID){
		int count = 0; 
		while (count < 4){
			try {
				WebElement yourSlipperyElement = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.linkText(elementID)));
				yourSlipperyElement.click(); 
				writer.println("Clicked Stale");
				count = count+4;
			} catch (StaleElementReferenceException e){
				e.toString();
				writer.println("Trying to recover from a stale element :" + e.getMessage());
				count = count+1;
			} catch (WebDriverException e){
				e.toString();
				writer.println("Trying to recover from a Element not clickable :" + e.getMessage());
				count = count+1;
			}
			//count = count+4;
		}
	}

	public void StaleElementHandleByXPath (WebDriver driver, String elementID){
		int count = 0; 
		while (count < 4){
			try {
				WebElement yourSlipperyElement = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath(elementID)));
				yourSlipperyElement.click(); 
				writer.println("Clicked Stale");
				count = count+4;
			} catch (StaleElementReferenceException e){
				e.toString();
				writer.println("Trying to recover from a stale element :" + e.getMessage());
				count = count+1;
			} catch (WebDriverException e){
				e.toString();
				writer.println("Trying to recover from a Element not clickable :" + e.getMessage());
				count = count+1;
			}
			//count = count+4;
		}


	}
}