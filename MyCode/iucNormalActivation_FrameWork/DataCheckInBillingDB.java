package iucNormalActivation_FrameWork;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class DataCheckInBillingDB 
{
	static Statement stmt = null;
	static String dataCheckFolderPath = "";
	static String dataCheckInBillingExcelFile = "Data_Check_In_Billing.xls";
	static String dataCheckInBillingExcelPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	public DataCheckInBillingDB(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		DataCheckInBillingDB.dataCheckFolderPath = dataCheckFolderPath;
		DataCheckInBillingDB.dataCheckInBillingExcelPath = dataCheckFolderPath.concat("\\").concat(dataCheckInBillingExcelFile);
		DataCheckInBillingDB.reportFile = reportFile;
		DataCheckInBillingDB.datasetLogFile = datasetLogFile;
		DataCheckInBillingDB.utilities = utilities;
	} // end of constructor method

	public int execute(String Msisdn, String Sim) throws ClassNotFoundException, FileNotFoundException, SQLException, InterruptedException, IOException
	{
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Billing"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		connectionToDB();
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "Counter Execute - "+conditionCounter);
		billingDeviceCheck(Msisdn,"MSISDN");
		billingDeviceCheck(Sim,"SIM");
		billingAccountCheck(Msisdn,"MSISDN");
		billingAccountCheck(Sim,"SIM");
		return conditionCounter;
	} // end of execute method

	public static void connectionToDB() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.22:1521:BRMDEV","PIN","pin");
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		utilities.writeToFile(datasetLogFile, "DB Connection established...");
	} // end of connectionToDB method

	public void billingDeviceCheck(String device, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException,NullPointerException
	{
		try
		{
			FileInputStream fileIn = null;
			HSSFWorkbook wb = null;
			int temp = 0;
			int checkCounter = 0;
			// Setting the query
			String query=("select device_id, case when (poid_type = '/device/num' and state_id != 4) then 'Invalid State, it should be 4'when (poid_type = '/device/sim' and state_id != 8) then 'Invalid State, it should be 8'when account_obj_id0 is not null then 'Device already associated to some account' when login is not null then 'Uniqueness_t already has the value ' || login || ' for login' else 'Device is OK' end result from ( select distinct d.poid_id0, d.device_id, d.poid_type, d.state_id, ds.account_obj_id0, dsim.imsi,(select distinct login from uniqueness_t where decode(d.poid_type, '/device/num', d.device_id, '/device/sim', dsim.imsi) = login) login from   device_t d, device_services_t ds, device_sim_t dsim, uniqueness_t u where  d.poid_id0 = ds.obj_id0(+) and    d.device_id like ('").concat(device).concat("%') and    d.poid_id0 = dsim.obj_id0 (+) )");	

			// Executing the query
			ResultSet rs=stmt.executeQuery(query);
			utilities.writeToFile(datasetLogFile, "Executed Device Check Query for "+type+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+query);

			// Assigning the result set to workbook
			try {
				fileIn = new FileInputStream(dataCheckInBillingExcelPath);
				wb=new HSSFWorkbook(fileIn);
				temp++;
			} catch (FileNotFoundException e){
				wb=new HSSFWorkbook();
			}			
			HSSFSheet sht=wb.createSheet("Device_Check_"+type);
			HSSFRow rowhead=sht.createRow(0);
			rowhead.createCell(0).setCellValue("DEVICE_ID");
			rowhead.createCell(1).setCellValue("Billing");
			int i=1;
			while(rs.next())
			{
				HSSFRow row=sht.createRow(i);
				row.createCell(0).setCellValue(rs.getString(1));
				row.createCell(1).setCellValue(rs.getString(2));
				i++;

			}
			if(temp>0){
				fileIn.close();
			}


			FileOutputStream fileOut = new FileOutputStream(dataCheckInBillingExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if(rs.getString(2).equalsIgnoreCase("Device is OK")){
						// TBD
					} else {
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Device is OK
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "------------------------------------"+System.lineSeparator()+"Device Check Query for "+type+" - Fail"+System.lineSeparator()+"------------------------------------");
				checkCounter++;
				conditionCounter++;
			} // end Else checking for empty result set

			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "------------------------------------"+System.lineSeparator()+"Device Check Query for "+type+" - Fail"+System.lineSeparator()+"------------------------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "------------------------------------"+System.lineSeparator()+"Device Check Query for "+type+" - Pass"+System.lineSeparator()+"------------------------------------");
			}
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
	} // end of billingDeviceCheck method

	public void billingAccountCheck(String device, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException,NullPointerException
	{
		try
		{
			@SuppressWarnings("unused")
			int checkCounter = 0;
			// Setting the query
			String query=("select ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0,c.account_no from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(device).concat("'");	

			// Executing the query
			ResultSet rs=stmt.executeQuery(query);
			utilities.writeToFile(datasetLogFile, "Executed Account Check Query for "+type+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+query);
			
			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInBillingExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet("Account_Check_"+type);
			HSSFRow rowhead=sht.createRow( 0 );
			rowhead.createCell(0).setCellValue("POID_ID0");
			rowhead.createCell(1).setCellValue("DEVICE_ID");
			rowhead.createCell(2).setCellValue("STATE_ID");
			rowhead.createCell(3).setCellValue("ACCOUNT_OBJ_ID");
			rowhead.createCell(4).setCellValue("ACCOUNT_NO");
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
			fileIn.close();

			FileOutputStream fileOut = new FileOutputStream(dataCheckInBillingExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(!rs.next()){
				utilities.writeToFile(datasetLogFile, "--------------------------------------"+System.lineSeparator()+"Device Account Query for "+type+" - Pass"+System.lineSeparator()+"--------------------------------------");
			} else {
				checkCounter++;
				conditionCounter++;
				utilities.writeToFile(datasetLogFile, "--------------------------------------"+System.lineSeparator()+"Device Account Query for "+type+" - Fail"+System.lineSeparator()+"--------------------------------------");
			} // end Else checking for empty result set
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
	} // end of billingAccountCheck method
} // end of class