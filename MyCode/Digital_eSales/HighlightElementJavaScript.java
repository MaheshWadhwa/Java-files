/*package Digital_eSales;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import com.project.setup.WebDriverManager;

public class HighlightElementJavaScript {

    public static void highlightElement(WebElement element) {
        for (int i = 0; i <2; i++) {
        	JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.driver;
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: yellow; border: 2px solid yellow;");
            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
            }
        }
}*/