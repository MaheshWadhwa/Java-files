package IUC_NORMAL_ACTIVATION_END_TO_END_FLOW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtilitiesWriter {

	static PrintWriter writer = null;
	static String reportFile = "";
	static String datasetLogFile = "";

	public CommonUtilitiesWriter(PrintWriter writer){
		CommonUtilitiesWriter.writer = writer;
	}
	
	public CommonUtilitiesWriter() {
		// TODO Auto-generated constructor stub
	}
	
	public CommonUtilitiesWriter(String reportFile, String datasetLogFile){
		CommonUtilitiesWriter.reportFile = reportFile;
		CommonUtilitiesWriter.datasetLogFile = datasetLogFile;
	}
	
	@SuppressWarnings("all")
	public void takeScreenShotMethod(WebDriver driver, String path, String fileName) throws InterruptedException{
	    Thread.sleep(2000);
	    String imgPath;
	    try {
	        WebDriver augmentedDriver = new Augmenter().augment(driver);
	        File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
	        imgPath = path.concat("\\").concat(fileName).concat(".jpg");
	        FileUtils.copyFile(source, new File(imgPath)); 
	       
	    }
	    catch(IOException e) {
	        imgPath = "Failed to capture screenshot: " + e.getMessage();
	    }
	    
	}

	public void writeToFile(String file,String message){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
			out.println(message);
			out.close();
			if(file.contains("Execution Report")){
				System.out.println(message);
			}
		}catch (IOException e) {
		    System.out.println("Message couldn't write to file"+"\n"+"File - "+file+"\n"+"Message - "+message);
		}catch (NullPointerException e){
			System.out.println("Null Pointer Exception");
		}
	}
	
	public String getDateTime(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String dateTime = dateFormat.format(date);
		return dateTime;
	}
	
	public void createFolder(String folderPath,String folderName){

		String folder = folderPath.concat(folderName);
		File theDir = new File(folder);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
//			System.out.println("creating directory: " + folderName);
			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			} 
			catch(SecurityException se){
				//handle it
			}        
			if(result) {    
				//System.out.println(folderName+" DIR created");  
			} else {
				//System.out.println(folderName+" DIR Not created");
			}
		}
	}

	public WebDriver invokeBrowser(WebDriver driver,String browser,String URL){

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Automated_Execution\\Scripts\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Automated_Execution\\Scripts\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}

		driver.get(URL);

		return driver;
	}

	public void clickWE(WebDriver driver,String strPath){
		WebElement we = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath(strPath)));
//		writer.println("Clicked on "+we.getText());
		writeToFile(datasetLogFile, "Clicked on "+we.getText());
		we.click();

	}

	public void selectDD(WebDriver driver, String strPath, String strOpt){
		WebElement we = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.xpath(strPath)));
		we.click();
		driver.findElement(By.linkText(strOpt)).click();
//		writer.println("Clicked on "+strOpt);
		writeToFile(datasetLogFile, "Clicked on "+strOpt);

	}

	public void selectOption(WebDriver driver,String selectTagID,String opt){
		WebElement select1 = new WebDriverWait(driver,30).until(ExpectedConditions.elementToBeClickable(By.id(selectTagID)));
		List<WebElement> options1 = select1.findElements(By.tagName("option"));
		for (WebElement option : options1) {
			if(option.getText().contains(opt)) {
				option.click();
				//writer.println("Clicked on "+opt);
				writeToFile(datasetLogFile, "Clicked on "+opt);
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