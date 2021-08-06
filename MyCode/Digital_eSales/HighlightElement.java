package Digital_eSales;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HighlightElement {

	public static void main(String args[]) throws InterruptedException{
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		Thread.sleep(5000);
		WebElement we = driver.findElement(By.xpath("//input[@value='Google Search']"));
		flash(we, driver);
		Thread.sleep(5000);
	}
	public static void flash(WebElement element, WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		String bgcolor  = element.getCssValue("backgroundColor");
		String color  = element.getCssValue("Color");
		for (int i = 0; i <  1; i++) {
			changeColor("rgb(200,0,0)", element, js);
			Thread.sleep(5000);
			changeColor(bgcolor, element, js);
			Thread.sleep(5000);
			changeColor(color, element, js);
			Thread.sleep(5000);
		}
	}
	
	public void elementHighlight(WebElement element, WebDriver driver) {
		for (int i = 0; i < 2; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: red; border: 3px solid red;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "");
		}
	}
	
	
	
	public static void changeColor(String color, WebElement element,  JavascriptExecutor js) {
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",  element);
		js.executeScript("arguments[0].Color = '"+color+"'",  element);

		try {
			Thread.sleep(20);
		}  catch (InterruptedException e) {
		}
	}
}