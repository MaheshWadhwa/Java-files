package Digital_eSales;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;


import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.CORBA.portable.OutputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Augmenter;
import org.apache.commons.io.FileUtils;
//import com.sun.jna.platform.FileUtils;

public class Utilities_eSales {

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
	

	public void takeScreenShotMethod(WebDriver driver, String path, String fileName) throws InterruptedException{

		Thread.sleep(2000);

		String imgPath;		

		try {

			WebDriver augmentedDriver = new Augmenter().augment(driver);

			File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);

			imgPath = path.concat("\\").concat(fileName).concat(".jpg");

			FileUtils.copyFile(source, new File(imgPath));	
			
/*			String imageName;
			document.createParagraph().createRun().addPicture(new FileInputStream(imgPath),document.PICTURE_TYPE_JPEG,imageName, 328, 247);
			
			try{
				document.write(outStream);
				outStream.close();
			}
			catch (FileNotFoundException e2)
			{
				e2.printStackTrace();
			} 
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			*/
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


/*		
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
//				System.out.println("creating directory: " + folderName);
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
	
	
    @SuppressWarnings("all")
     public void takeScreenShotMethod(WebDriver driver, String path, String fileName) throws InterruptedException, FileNotFoundException{

            System.setErr(new PrintStream(new OutputStream() {
                   @Override
                   public void write(int b) {
                   }
            }));

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
	
	*/

  
}
