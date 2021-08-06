package ConsumerRaqiActivation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

//import ConsumerRaqiActivation.CommonUtilitiesWriter;

public class ConsumerRaqiEaiDbCheck
{
	static String finalstr;
	static int y;
	static String a;
 
	static String trackFile,statusFile;

	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static String datasetFolderName = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public ConsumerRaqiEaiDbCheck(String reportFile, String datasetLogFile, String datasetFolderPath, String datasetFolderName, CommonUtilitiesWriter utilities){
		ConsumerRaqiEaiDbCheck.reportFile = reportFile;
		ConsumerRaqiEaiDbCheck.datasetLogFile = datasetLogFile;
		ConsumerRaqiEaiDbCheck.datasetFolderPath = datasetFolderPath;
		ConsumerRaqiEaiDbCheck.datasetFolderName = datasetFolderName;
		ConsumerRaqiEaiDbCheck.utilities = utilities;
		
	} // end of constructor method

	public int eaiDBCheck()throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		String temp = "";
//		System.out.println(reportFile);
		utilities.writeToFile(reportFile, System.lineSeparator()+"EAI DB Check"+System.lineSeparator()+"------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tEAI DB Check"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		utilities.createFolder(datasetFolderPath, "\\CONSUMER_RAQI_DB_Check");
		
		trackFile = datasetFolderPath.concat("\\CONSUMER_RAQI_DB_Check\\").concat(datasetFolderName).concat("_Track.xls");
		statusFile = datasetFolderPath.concat("\\CONSUMER_RAQI_DB_Check\\").concat(datasetFolderName).concat("_Status.xls");

		int i=1;
		while (i<=15)
		{
			utilities.writeToFile(datasetLogFile, "Iteration : " +i+" of 15"+System.lineSeparator()+"--------------------");
			System.out.println("Iteration : " +i+" of 15");
			System.out.println("--------------------");
			status_write();
			status_read();
			track_write();
			track_read();
			if(y==4)
			{
				System.out.println("Status is : " +y);
				System.out.println("Activation pending || Request currently in " +a);
				utilities.writeToFile(datasetLogFile, "Status is : " +y);
				utilities.writeToFile(datasetLogFile, "Activation pending || Request currently in " +a);
			}
			else if(y==3)
			{
				System.out.println("Status is : " +y);
				System.out.println("Activation rejected || "+a);
				utilities.writeToFile(datasetLogFile, "Status is : " +y);
				utilities.writeToFile(datasetLogFile, "Activation rejected || "+a);
				utilities.writeToFile(reportFile, "Activation rejected || "+a);
				break;
			}
			else if (y==5)
			{
				System.out.println("Status is : " +y);
				System.out.println("Activation failed || "+a);
				utilities.writeToFile(datasetLogFile, "Status is : " +y);
				utilities.writeToFile(reportFile, "Activation failed || "+a);
				break;
			}
			else if(y==6)
			{
				System.out.println("Status is : " +y);
				System.out.println("Activation Success || "+a);
				System.out.println("SR ID is "+finalstr);
				utilities.writeToFile(datasetLogFile, "Status is : " +y);
				utilities.writeToFile(datasetLogFile, "Activation Success || "+a);
				utilities.writeToFile(datasetLogFile, "SR ID is "+finalstr);
				utilities.writeToFile(reportFile, "Activation Success || "+a);
				utilities.writeToFile(reportFile, "SR ID is "+finalstr);
				
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Sheet1");
				sheet.createRow(0).createCell(0).setCellValue("SR_ID");
				sheet.createRow(1).createCell(0).setCellValue(finalstr);

				FileOutputStream fos = new FileOutputStream(datasetFolderPath.concat("\\CONSUMER_RAQI_DB_Check\\").concat(datasetFolderName).concat("_Log.xls"));
				workbook.write(fos);
				workbook.close();
				fos.close();
				break;
			} else {
				System.out.println("Status is : " +y);
				System.out.println("Activation Request currently in " +a);
				utilities.writeToFile(datasetLogFile, "Status is : " +y);
				utilities.writeToFile(datasetLogFile, "Activation currently in " +a);
				if(i==15){
					utilities.writeToFile(reportFile, "Status is : " +y);
					utilities.writeToFile(reportFile, "Activation currently in " +a);
				}
			}
			if(i==1){
				temp = a;
				i++;
			}else if(temp.equalsIgnoreCase(a)){
				i++;
			}else{
				temp = a;
				i=1;
			}
			Thread.sleep(60000);
		}
		utilities.writeToFile(datasetLogFile, "EAI DB validation done!");
		return y;
	}
	public static void status_write() throws IOException,FileNotFoundException
	{
		try {
			
			// Fetch Order ID from file
			String file = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_CONSUMER_RAQI.xls");
			File file_data=new File (file);
			FileInputStream inputstrm= new FileInputStream(file_data);

			HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
			HSSFSheet worksheet = testdata.getSheet("Sheet1");
			HSSFRow ro = worksheet.getRow(1);
			String a1 = ro.getCell(0).getStringCellValue();
			testdata.close();
			inputstrm.close();
			
			// Connect to DB
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","PGW","pgw");
			con.setAutoCommit(false);
			Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			// Set the query to fetch SR_ID
			String b = ("SELECT * FROM SR_SALES_ORDER_TBL WHERE ORDER_NUMBER = '").concat(a1).concat("'");
			
			// Execute the query
			ResultSet rs=st.executeQuery(b);
			utilities.writeToFile(datasetLogFile, "Executed Query to fetch SR_ID"+System.lineSeparator()+"---------------------"+System.lineSeparator()+b);
			
			// Fetch the SR_ID
			rs.next();
			String SR_ID = rs.getString(1);
			utilities.writeToFile(datasetLogFile, "SR_ID is "+SR_ID);
			
			// Set the query
			b = "SELECT SR_ID, LAST_UPDATE_TIME, STATUS FROM SR_SERVICEREQUEST_TBL where SR_ID = '".concat(SR_ID).concat("'");
			
			// Execute the query
			rs=st.executeQuery(b);
			utilities.writeToFile(datasetLogFile, "Executed Status Query"+System.lineSeparator()+"---------------------"+System.lineSeparator()+b);

			// Assigning the result set to workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("DB_VALIDATION_SR_ID");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("SR_ID");
			rowhead.createCell(1).setCellValue("LAST_UPDATE_TIME");
			rowhead.createCell(2).setCellValue("STATUS");

			int i = 1;
			while (rs.next()){
				HSSFRow row = sheet.createRow((short) i);
				row.createCell( 0).setCellValue(rs.getString(1));
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				i++;
			}
			
			FileOutputStream fileOut = new FileOutputStream(statusFile);
			workbook.write(fileOut);
			workbook.close();   
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
		utilities.writeToFile(datasetLogFile, "Status File created successfully!");
	}

	public static void status_read() throws IOException,FileNotFoundException
	{
		File file=new File(statusFile);
		FileInputStream fileInputStream = new FileInputStream(file);
		HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook1.getSheet("DB_VALIDATION_SR_ID");
		HSSFRow row1 = worksheet.getRow(1);
		HSSFCell cellA1 = row1.getCell(2);
		HSSFCell cellA2 = row1.getCell(0);
		String a1Val = cellA1.getStringCellValue();
		String a1Va2 = cellA2.getStringCellValue();
		String str =a1Va2;
		int len = str.length();
		finalstr = str.substring(0, len);
		y = Integer.parseInt(a1Val);
		workbook1.close();
	}

	public static void track_write() throws IOException,FileNotFoundException
	{
		try 
		{
			// Connect to DB
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","PGW","pgw");
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			// Set the query
			String a = ("Select ID,  name, SR_ID, time_stamp, message From SR_sales_order_AUDIT_TBL a,   SR_TRACKING_POINT_TBL  b  WHERE code=tracking_code and sr_id = '").concat(finalstr).concat("' Order By sr_id DESC, ID desc");
			
			// Execute the query
			ResultSet rs=stmt.executeQuery(a);
			utilities.writeToFile(datasetLogFile, "Executed Track Query"+System.lineSeparator()+"--------------------"+System.lineSeparator()+a);

			// Assigning the result set to workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("DB_VALIDATION_TRACK");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("ID");
			rowhead.createCell(1).setCellValue("NAME");
			rowhead.createCell(2).setCellValue("SR_ID");
			rowhead.createCell(3).setCellValue("TIME_STAMP");
//			rowhead.createCell(4).setCellValue("MESSAGE");

			int j = 1;
			while (rs.next()){
				HSSFRow row = sheet.createRow((short) j);
				row.createCell(0).setCellValue(rs.getString(1));
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				row.createCell(3).setCellValue(rs.getString(4));
//				row.createCell(4).setCellValue(rs.getString(5));
				j++;
			}
			
			FileOutputStream fileOut1 = new FileOutputStream(trackFile);
			workbook.write(fileOut1);
			workbook.close();
			fileOut1.close();
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
		utilities.writeToFile(datasetLogFile, "Track File created successfully!");
	}

	public static void track_read() throws IOException
	{
		File file1=new File(trackFile);
		FileInputStream fileInputStream1 = new FileInputStream(file1);
		HSSFWorkbook workbook2 = new HSSFWorkbook(fileInputStream1);
		HSSFSheet worksheet2 = workbook2.getSheet("DB_VALIDATION_TRACK");
		HSSFRow row2 = worksheet2.getRow(1);
		HSSFCell cllA1 = row2.getCell(1);
		a = cllA1.getStringCellValue();
		workbook2.close();
	}
}