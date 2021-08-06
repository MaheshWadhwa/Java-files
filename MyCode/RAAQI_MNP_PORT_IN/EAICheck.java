package RAAQI_MNP_PORT_IN;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;

import org.openqa.selenium.support.ui.Select;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class EAICheck{
	static String ip;
	static String ser_username;
	static String ser_password;
	static String finalstr;
	private static int y;
	static String trackFile;
	static String statusFile;

	//static String ip;
	static String File;
	static String EAIpre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	static String orderNumber;
	static String Actipre;
	static int checkCounter;

	static ResultSet rs_track;
	static String b;
	static String c;
	static String d;
	static String e;

	static String s_id;

	public EAICheck(String runlog,String reportfile,String EAIpre,CommonUtilitiesWriter report)
	{
		EAICheck.runlog=runlog;
		EAICheck.reportfile=reportfile;
		EAICheck.EAIpre=EAIpre;
		EAICheck.report=report;
	}
	public int consumer(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{

		report.writeToFile(reportfile, System.lineSeparator()+"EAI DB Check"+System.lineSeparator()+"------------");
		conditionCounter = 0;
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tEAI DB Check"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String temp = "";
		statusFile =EAIpre.concat("\\STATUS.xls");
		trackFile =EAIpre.concat("\\TRACK.xls");
		int i=1;
		while (i<15)
		{

			report.writeTorun(runlog,"Iteration" +i+ "of 15");
			report.writeToFile(reportfile,"Iteration" +i+ "of 15");

			status_write(MSISDN);
			status_read();
			

		}	
		return conditionCounter;

	} // Corporate end

	public static void status_write(String MSISDN) throws IOException,FileNotFoundException
	{
		try {


			//Test data reading


			report.writeTorun(runlog,"Status query started");

			//Connection -DB
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","PGW","pgw");
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

			System.out.println("DB Connection established");
			report.writeTorun(runlog,"DB Connection established");
			String b = ("select SR_ID,STATUS,Time_recieved from SR_SERVICEREQUEST_TBL D where D.LINE_NUMBER='").concat(MSISDN).concat("' ORDER BY TIME_RECIEVED DESC");
			ResultSet rs=stmt.executeQuery(b);
			System.out.println("Status query executed");
			report.writeTorun(runlog, "Executed Status Query"+System.lineSeparator()+"---------------------"+System.lineSeparator()+b);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("DB_VALIDATION_SR_ID");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SR_ID");
			rowhead.createCell(1).setCellValue("STATUS");
			rowhead.createCell(2).setCellValue("TIME_RECIEVED");

			int i = 1;
			while (rs.next()){
				HSSFRow row = sheet.createRow((short) i);
				row.createCell( 0).setCellValue(rs.getString(1));
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				i++;
			}

			statusFile =EAIpre.concat("\\STATUS.xls");
			FileOutputStream fileOut = new FileOutputStream(statusFile,false);
			workbook.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"Status file has been created successfully");

		}
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}

	}

	public static void status_read() throws IOException,FileNotFoundException
	{
		File file=new File(statusFile);
		FileInputStream fileInputStream = new FileInputStream(file);
		HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook1.getSheet("DB_VALIDATION_SR_ID");
		HSSFRow row1 = worksheet.getRow(1);
		HSSFCell cellA1 = row1.getCell(1);
		HSSFCell cellA2 = row1.getCell(0);
		String a1Val = cellA1.getStringCellValue();
		String a1Va2 = cellA2.getStringCellValue();
		String str =a1Va2;
		int len = str.length();
		finalstr = str.substring(0, len);

	}

	public static void track() throws IOException,FileNotFoundException
	{

		try 
		{

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","PGW","pgw");
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);


			String a=("Select * from (select a.SR_ID,a.TIME_STAMP,a.TRACKING_POINT,b.NAME,a.MESSAGE from SR_MNP_RECV_AUDIT_TBL a ,SR_TRACKING_POINT_TBL b WHERE a.TRACKING_POINT=b.CODE and a.SR_ID = '").concat(finalstr).concat("union select a.SR_ID,a.TIMESTAMP,a.TRACKING_POINT,b.NAME,a.MESSAGE from SR_MNP_LINE_AUDIT_TBL a ,SR_TRACKING_POINT_TBL b WHERE a.TRACKING_POINT=b.CODE and a.SR_ID = '").concat(finalstr).concat("order by TIME_STAMP desc");

			report.writeTorun(runlog, "Executed Status Query"+System.lineSeparator()+"---------------------"+System.lineSeparator()+a);
			ResultSet rs_track=stmt.executeQuery(a);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("DB_VALIDATION_TRACK");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SR_ID");
			rowhead.createCell(1).setCellValue("TIME_STAMP");
			rowhead.createCell(2).setCellValue("TRACKING_POINT");
			rowhead.createCell(3).setCellValue("NAME");
			rowhead.createCell(4).setCellValue("MESSAGE");



			int j = 1;
			while (rs_track.next()){
				HSSFRow row = sheet.createRow((short) j);
				row.createCell(0).setCellValue(rs_track.getString(1));
				row.createCell(1).setCellValue(rs_track.getString(2));
				row.createCell(2).setCellValue(rs_track.getString(3));
				row.createCell(3).setCellValue(rs_track.getString(4));
				row.createCell(4).setCellValue(rs_track.getString(5));
				j++;
			}
			trackFile =EAIpre.concat("\\TRACK.xls");
			FileOutputStream fileOut1 = new FileOutputStream(trackFile,false);
			workbook.write(fileOut1);
			fileOut1.close();
			report.writeTorun(runlog,"Track File has been created successfully");


		} 
		catch (ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}

	public static void xml1_push() throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{

		checkCounter=0;
		//XML declarations
		b="C:\\Automated_Execution\\Input\\MNP PORT IN\\xml1.txt";
		s_id=finalstr;

		//XML reading

		BufferedReader brTest = new BufferedReader(new FileReader(b));
		String text = brTest .readLine();
		String sr_id=text.replace("AUTO_SR_ID", s_id); 



		WebDriver driver = null;
		String browser = "Chrome";

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		//Maximize
		driver.manage().window().maximize();

		driver.get("http://10.12.234.139:8084/TestDataManagement/loginform.jsp");
		Thread.sleep(7000);
		//report.writeTorun(runlog,"URL invoked");
		System.out.println("URL invoked");
		//Login

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("j.dasari.mit");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password@G7119");
		driver.findElement(By.xpath("//input[@id='search']")).click();
		Thread.sleep(3000);

		//MQUTILITY Page
		driver.findElement(By.xpath("//a[text()='MQPutUtility']")).click();

		//Pushing 1st XML
		report.selectOption(driver, "qmname", "EELX992I - sdl020-EAI");
		Thread.sleep(5000);
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='queuename']")));
		Thread.sleep(2000);
		dropdown.selectByVisibleText("MOBILY.MNP.PG.RECIPIENT.INIT.NPRACKREPLY");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(sr_id);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='submit']")).click();;
		Thread.sleep(6000);
		driver.quit();


		//Track executing

		track();

		rs_track.beforeFirst();

		if(rs_track.next())
		{
			do
			{
				if(rs_track.getString(3).equalsIgnoreCase("MNP RECIPIENT-RECIVED SYNCH DELIVERY NOTIFICATION FROM PG FOR NPR"))
				{
					//TBD
				}
				else
				{
					checkCounter++;
					conditionCounter++;
				}
			}
			while(rs_track.next());

		}

		else
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 1  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");

			////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN  - Fail"+System.lineSeparator()+"------------------------------------");
			checkCounter++;
			conditionCounter++;

		} // end Else checking for empty result set

		if(checkCounter > 0)
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 1  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
			////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
			checkCounter++;
			conditionCounter++;
		}

		else
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 1  PUSH - PASS"+System.lineSeparator()+"------------------------------------");
			//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
		}

	}


	public static void xml2_push() throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		//XML declarations
		c="C:\\Automated_Execution\\Input\\MNP PORT IN\\xml2.txt";
		s_id=finalstr;

		//XML reading

		BufferedReader brTest = new BufferedReader(new FileReader(b));
		String text = brTest .readLine();
		String sr_id=text.replace("AUTO_SR_ID", s_id); 



		WebDriver driver = null;
		String browser = "Chrome";

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		//Maximize
		driver.manage().window().maximize();

		driver.get("http://10.12.234.139:8084/TestDataManagement/loginform.jsp");
		Thread.sleep(7000);
		//report.writeTorun(runlog,"URL invoked");
		System.out.println("URL invoked");
		//Login

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("j.dasari.mit");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password@G7119");
		driver.findElement(By.xpath("//input[@id='search']")).click();
		Thread.sleep(3000);

		//MQUTILITY Page
		driver.findElement(By.xpath("//a[text()='MQPutUtility']")).click();

		//Pushing 1st XML
		report.selectOption(driver, "qmname", "EELX992I - sdl020-EAI");
		Thread.sleep(5000);
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='queuename']")));
		Thread.sleep(2000);
		dropdown.selectByVisibleText("MOBILY.MNP.PG.RECIPIENT.INIT.NPRACKREPLY");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(sr_id);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='submit']")).click();;
		Thread.sleep(6000);
		driver.quit();
		//Track executing

		track();

		rs_track.beforeFirst();

		if(rs_track.next())
		{
			do
			{
				if(rs_track.getString(3).equalsIgnoreCase("MNP RECIPIENT-RECIVED MPC ACK FOR NPR"))
				{
					//TBD
				}
				else
				{
					checkCounter++;
					conditionCounter++;
				}
			}
			while(rs_track.next());

		}

		else
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 2  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");

			////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN  - Fail"+System.lineSeparator()+"------------------------------------");
			checkCounter++;
			conditionCounter++;

		} // end Else checking for empty result set

		if(checkCounter > 0)
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 2  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
			////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
			checkCounter++;
			conditionCounter++;
		}

		else
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 2  PUSH - PASS"+System.lineSeparator()+"------------------------------------");
			//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
		}
	}

	public static void xml3_push() throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		//XML declarations
		b="C:\\Automated_Execution\\Input\\MNP PORT IN\\xml3.txt";
		s_id=finalstr;

		//XML reading

		BufferedReader brTest = new BufferedReader(new FileReader(b));
		String text = brTest .readLine();
		String sr_id=text.replace("AUTO_SR_ID", s_id); 



		WebDriver driver = null;
		String browser = "Chrome";

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		//Maximize
		driver.manage().window().maximize();

		driver.get("http://10.12.234.139:8084/TestDataManagement/loginform.jsp");
		Thread.sleep(7000);
		//report.writeTorun(runlog,"URL invoked");
		System.out.println("URL invoked");
		//Login

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("j.dasari.mit");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password@G7119");
		driver.findElement(By.xpath("//input[@id='search']")).click();
		Thread.sleep(3000);

		//MQUTILITY Page
		driver.findElement(By.xpath("//a[text()='MQPutUtility']")).click();

		//Pushing 1st XML
		report.selectOption(driver, "qmname", "EELX992I - sdl020-EAI");
		Thread.sleep(5000);
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='queuename']")));
		Thread.sleep(2000);
		dropdown.selectByVisibleText("MOBILY.MNP.PG.RECIPIENT.INIT.NPRARHREP");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(sr_id);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='submit']")).click();;
		Thread.sleep(6000);
		driver.quit();
		//Track executing

		track();

		int i=1;
		rs_track.beforeFirst();

		while (1<10)

		{
			report.writeToFile(runlog, "Iteration : " +i+" of 15"+System.lineSeparator()+"--------------------");
			System.out.println("Iteration : " +i+" of 15");


			if(rs_track.next())
			{
				do
				{
					if((rs_track.getString(3).equalsIgnoreCase("Sync Reply From Account ACtivation Service(Accepted)")))
					{
						//TBD
						checkCounter=0;
					}
					else
					{
						checkCounter++;
						conditionCounter++;
					}
				}
				while(rs_track.next());

			}

			else
			{

				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 3  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
				checkCounter++;
				conditionCounter++;

			} // end Else checking for empty result set

			if(checkCounter > 0)
			{

				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 3  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
				////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
				checkCounter++;
				conditionCounter++;
			}

			else
			{
				checkCounter=0;
				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 3  PUSH - PASS"+System.lineSeparator()+"------------------------------------");
				//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
			}

			rs_track.beforeFirst();

			if(rs_track.next())
			{
				do
				{
					if((rs_track.getString(2).equals("550")))
					{
						//TBD
						checkCounter=0;
					}
					else
					{
						checkCounter++;
						conditionCounter++;
					}
				}
				while(rs_track.next());

			}

			else
			{

				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 3  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
				checkCounter++;
				conditionCounter++;

			} // end Else checking for empty result set

			if(checkCounter > 0)
			{

				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 3  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
				////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
				checkCounter++;
				conditionCounter++;
			}

			else
			{
				checkCounter=0;
				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 3  PUSH - PASS"+System.lineSeparator()+"------------------------------------");
				//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
			}
		
			report.writeTorun(runlog, "Activation is in progress. Kindly wait for a minute to check where the request resides at that moment..."+System.lineSeparator());
			System.out.println("\nActivation is in progress. Kindly wait for a minute to check where the request resides at that moment...");
			Thread.sleep(60000);
			
			if(checkCounter==0)
			{
				
				//Call xml 4 PUSH
				
				xml4_push();
				
			}
			
			else
			{
				//TBD
			}
			}
		
	
		}

	public static void xml4_push() throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		//XML declarations
		b="C:\\Automated_Execution\\Input\\MNP PORT IN\\xml4.txt";
		s_id=finalstr;

		//XML reading

		BufferedReader brTest = new BufferedReader(new FileReader(b));
		String text = brTest .readLine();
		String sr_id=text.replace("AUTO_SR_ID", s_id); 



		WebDriver driver = null;
		String browser = "Chrome";

		if (browser=="Firefox") {
			driver=new FirefoxDriver();
		}else if (browser=="IE") {
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Drivers\\IEDriverServer_x32.exe");
			driver=new InternetExplorerDriver();			
		}else if (browser=="Chrome"){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
			driver= new ChromeDriver();
		}


		//Maximize
		driver.manage().window().maximize();

		driver.get("http://10.12.234.139:8084/TestDataManagement/loginform.jsp");
		Thread.sleep(7000);
		//report.writeTorun(runlog,"URL invoked");
		System.out.println("URL invoked");
		//Login

		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("j.dasari.mit");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Password@G7119");
		driver.findElement(By.xpath("//input[@id='search']")).click();
		Thread.sleep(3000);

		//MQUTILITY Page
		driver.findElement(By.xpath("//a[text()='MQPutUtility']")).click();

		//Pushing 1st XML
		report.selectOption(driver, "qmname", "EELX992I - sdl020-EAI");
		Thread.sleep(5000);
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='queuename']")));
		Thread.sleep(2000);
		dropdown.selectByVisibleText("MOBILY.MNP.PG.RECIPIENT.INIT.NPRACKREPLY");
		Thread.sleep(5000);

		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(sr_id);
		Thread.sleep(4000);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		Thread.sleep(6000);
		
		driver.quit();
		//Track executing

		track();

		rs_track.beforeFirst();

		if(rs_track.next())
		{
			do
			{
				if(rs_track.getString(3).equalsIgnoreCase("MOBILY.MNP.PGADAPTER.RECIPIENT.NPRFS.REPLY"))
				{
					//TBD
				}
				else
				{
					checkCounter++;
					conditionCounter++;
				}
			}
			while(rs_track.next());

		}

		else
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 4  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");

			////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN  - Fail"+System.lineSeparator()+"------------------------------------");
			checkCounter++;
			conditionCounter++;

		} // end Else checking for empty result set

		if(checkCounter > 0)
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 4  PUSH - FAIL"+System.lineSeparator()+"------------------------------------");
			////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
			checkCounter++;
			conditionCounter++;
		}

		else
		{

			report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"XML 4  PUSH - PASS"+System.lineSeparator()+"------------------------------------");
			//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
		}
		
	}


}
