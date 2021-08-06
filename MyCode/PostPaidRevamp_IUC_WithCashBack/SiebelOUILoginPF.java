/* 
 * Purpose - This Page Factory is used for login to Siebel Open UI application
 * 
 * Created By 	- Karthik Ananthapadmanaban
 * Created On 	- 20-Apr-2015
 * Modified By 	- <N/A>
 * Modified On 	- <N/A>
 * 
 * Current Version - 0.1
 * 
 * Note - See Bottom of the program for Version History
 * 
 */

package PostPaidRevamp_IUC_WithCashBack;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SiebelOUILoginPF {
	WebDriver driver;
	
	@FindBy(xpath=".//*[@id='s_swepi_1']")
	WebElement userName;
	
	@FindBy(xpath=".//*[@id='s_swepi_2']")
	WebElement password;
	
	@FindBy(xpath=".//*[@id='s_swepi_22']")
	WebElement loginBtn;
	
	// Constructor Method
	public SiebelOUILoginPF(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// Input User Name
	public void setUserName(String userName){
		this.userName.sendKeys(userName);
	}
	
	// Input Password
	public void setPassword(String password){
		this.password.sendKeys(password);
	}
	
	// Click on Login Button
	public void clickLogin(){
		loginBtn.click();
	}
	
	// Login Function - This will be used in Test for Login to Siebel Open UI
	public void loginSiebelOUI(String userName, String password){
		this.setUserName(userName);
		this.setPassword(password);
		this.clickLogin();
	}
	
}

/* Version History
 * ---------------
 * Version 		- 0.1
 * Modified On 	- 20-Apr-2015
 * Modified By 	- Karthik Ananthapadmanaban
 * Changes Made - Initial Version Drafted
 * 
 * 
 * 
 * 
 */