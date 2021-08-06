package CUC_SIM_Swap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Utilities.CommonUtilitiesWriterOLD;

public class PostActivationCheckInBilling {

	static Statement stmt;
	static String account_no = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static String datasetFolderName = "";
	static String inputFile = "";
	static CommonUtilitiesWriterOLD utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public PostActivationCheckInBilling(String inputFile, String reportFile, String datasetLogFile, String datasetFolderPath, String datasetFolderName, CommonUtilitiesWriterOLD utilities){
		PostActivationCheckInBilling.reportFile = reportFile;
		PostActivationCheckInBilling.datasetLogFile = datasetLogFile;
		PostActivationCheckInBilling.datasetFolderPath = datasetFolderPath;
		PostActivationCheckInBilling.datasetFolderName = datasetFolderName;
		PostActivationCheckInBilling.inputFile = inputFile;
		PostActivationCheckInBilling.utilities = utilities;
	} // end of constructor method

	public void billingPostCheck(String Msisdn,String Sim) throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in Billing"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		// Connect to Billing Database
		connectionToDB();

		// Execute query to get account number
		billing(Msisdn);
		
		// Execute query to get list of services
		billing_device();
	} // end of billingPostCheck method

	public static void billing_device() throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{
		try
		{
			// Setting the query
			String query=("select c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where c.poid_id0=b.Account_OBJ_ID0 and ab.POID_ID0=b.obj_id0 and c.account_no='").concat(account_no).concat("'");
			
			// Executing the query
			ResultSet rs=stmt.executeQuery(query);
			utilities.writeToFile(datasetLogFile, "Executed Billing Query 2"+System.lineSeparator()+"-------------------------"+System.lineSeparator()+query);
			
			// Assigning the values to workbook
			String File = datasetFolderPath.concat("\\Post_Activation_Validation\\Billing.xls");
			FileInputStream fis = new FileInputStream(File);
			HSSFWorkbook wb=new HSSFWorkbook(fis);
			HSSFSheet sht=wb.createSheet("BILL_DEVICE");
			HSSFRow rowhead=sht.createRow(0);
			rowhead.createCell(0).setCellValue("ACCOUNT_NO");
			rowhead.createCell(1).setCellValue("POID_ID");
			rowhead.createCell(2).setCellValue("DEVICE_ID");
			rowhead.createCell(3).setCellValue("STATE_ID");
			rowhead.createCell(4).setCellValue("ACCOUNTOBJ_ID");
			fis.close();

			int i=1;
			while(rs.next())
			{
				HSSFRow row=sht.createRow(i);
				row.createCell(0).setCellValue(rs.getString(1));
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				row.createCell(3).setCellValue(rs.getString(4));
				row.createCell(4).setCellValue(rs.getString(5));
				i++;

			}

			
			FileOutputStream fileOut = new FileOutputStream(File);
			wb.write(fileOut);
			fileOut.close();
			System.out.println("Billing device File has been created successfully ");
			//	writer.println("EMADEV File has been created successfully ");
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
	} // end of billing_device method


	public static void billing(String Msisdn) throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{
		try
		{
			// Setting the query
			String query=("select DISTINCT c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(Msisdn).concat("'");
			
			// Executing the query
			ResultSet rs=stmt.executeQuery(query);
			utilities.writeToFile(datasetLogFile, "Executed Billing Query 1"+System.lineSeparator()+"-------------------------"+System.lineSeparator()+query);
			
			// Assigning the result set to workbook
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("Billing_Query_1");
			HSSFRow rowhead=sht.createRow(0);
			rowhead.createCell(0).setCellValue("ACCOUNT_NO");
			rowhead.createCell(1).setCellValue("POID_ID");
			rowhead.createCell(2).setCellValue("DEVICE_ID");
			rowhead.createCell(3).setCellValue("STATE_ID");
			rowhead.createCell(4).setCellValue("ACCOUNTOBJ_ID");

			int i=1;
			while(rs.next())
			{
				HSSFRow row=sht.createRow(i);
				row.createCell(0).setCellValue(rs.getString(1));
				account_no = rs.getString(1);
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				row.createCell(3).setCellValue(rs.getString(4));
				row.createCell(4).setCellValue(rs.getString(5));
				i++;
			}
			
			utilities.createFolder(datasetFolderPath, "\\Post_Activation_Validation");
			String File = datasetFolderPath.concat("\\Post_Activation_Validation\\Billing.xls");
			FileOutputStream fileOut = new FileOutputStream(File);
			wb.write(fileOut);
			wb.close();
			fileOut.close();
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
	} // end of billing method

	public void connectionToDB() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.22:1521:BRMDEV","PIN","pin");
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		utilities.writeToFile(datasetLogFile, "DB Connection established");
	} // end of connectionToDB method
}
