package BCreator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;

	import javax.swing.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.sun.jna.platform.FileUtils;

public class DATACLEAN_BILLING 
{
	
	static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String datapre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	//static String dataCheckInBillingExcelFile = "BillingSIM_Device.xls";
	//static String dataCheckInBillingExcelPath = "";
	
	/*static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;
	static String SIM=ConsumerActivationMain.xSim;
	static String MSISDN=ConsumerActivationMain.xMsisdn;
	static String IMSI=ConsumerActivationMain.xSim;*/
	
	public DATACLEAN_BILLING(String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{
		DATACLEAN_BILLING.runlog=runlog;
		DATACLEAN_BILLING.reportfile=reportfile;
		DATACLEAN_BILLING.datapre=datapre;
		DATACLEAN_BILLING.report=report;
		//DATACLEAN_BILLING.dataCheckInBillingExcelPathl=datapre.concat("\\").concat(dataCheckInBillingExcelFile);
	}
	public int billing_execute(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException,NullPointerException
	{
		conditionCounter = 0;
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Billing"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//data_read();
		//System.out.print(SIM +MSISDN );
		connection();
		billingMSISDN_Device(MSISDN);
		billingSIM_Device(SIM);
		billingMSISDN_AcccountCheck(MSISDN);
		billingSIM_AcccountCheck(SIM);
		return conditionCounter;
		}

	public void billingMSISDN_Device(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException,NullPointerException
	{
		try 
		{
			int checkCounter=0;
			//report.writeTorun(runlog, "***********************"+System.lineSeparator() +"Billing Device check for MSISDN"+System.lineSeparator()+"***********************" );
			
			String ms=("select device_id, case when (poid_type = '/device/num' and state_id != 4) then 'Invalid State, it should be 4'when (poid_type = '/device/sim' and state_id != 8) then 'Invalid State, it should be 8'when account_obj_id0 is not null then 'Device already associated to some account' when login is not null then 'Uniqueness_t already has the value ' || login || ' for login' else 'Device is OK' end result from ( select distinct d.poid_id0, d.device_id, d.poid_type, d.state_id, ds.account_obj_id0, dsim.imsi,(select distinct login from uniqueness_t where decode(d.poid_type, '/device/num', d.device_id, '/device/sim', dsim.imsi) = login) login from   device_t d, device_services_t ds, device_sim_t dsim, uniqueness_t u where  d.poid_id0 = ds.obj_id0(+) and    d.device_id like ('").concat(MSISDN).concat("%') and    d.poid_id0 = dsim.obj_id0 (+) )");
			ResultSet rs=stmt.executeQuery(ms);
			
			report.writeTorun(runlog, "Executed Device Check Query for "+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+ms);
			
			report.writeTorun(runlog, "Qeury executed successfully");
			
			
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("BILLING_DEVICE");
			HSSFRow rowhead=sht.createRow( 0 );
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
			File = datapre.concat("\\BillingMSISDN_Device.xls");
		    FileOutputStream fileOut = new FileOutputStream(File,false);
			//FileOutputStream fileOut = new FileOutputStream(dataCheckInBillingExcelPath);
			wb.write(fileOut);
		    fileOut.close();
		    
		 // Resetting the pointer of result set before first row
		 			rs.beforeFirst();

		 			// Checking the condition for each available row
		 			if(rs.next()){
		 				do {
		 					if(rs.getString(2).equalsIgnoreCase("Device is OK")){
		 						// TBD for
		 					} 
		 					else
		 					{
		 						checkCounter++;
		 						conditionCounter++;
		 					} // end Else checking for any status other than Device is OK
		 				} 
		 				while(rs.next());
		 			} 
		 			else
		 			{
		 				
		 				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN  - Fail"+System.lineSeparator()+"------------------------------------");
		 				
		 				////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN  - Fail"+System.lineSeparator()+"------------------------------------");
		 				checkCounter++;
 						conditionCounter++;
		 				
		 			} // end Else checking for empty result set

		 			if(checkCounter > 0)
		 			{
		 				
		 				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
		 				////report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN- Fail"+System.lineSeparator()+"------------------------------------");
		 				checkCounter++;
 						conditionCounter++;
		 			}
		 			
		 			else
		 			{
		 				
		 				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
		 				//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for MSISDN - Pass"+System.lineSeparator()+"------------------------------------");
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
	
	public void billingSIM_Device(String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter=0;
			
			//report.writeTorun(runlog, "***********************"+System.lineSeparator() +"Billing Device check for SIM"+System.lineSeparator()+"***********************" );
			String sim1=("select device_id, case when (poid_type = '/device/num' and state_id != 4) then 'Invalid State, it should be 4'when (poid_type = '/device/sim' and state_id != 8) then 'Invalid State, it should be 8'when account_obj_id0 is not null then 'Device already associated to some account' when login is not null then 'Uniqueness_t already has the value ' || login || ' for login' else 'Device is OK' end result from ( select distinct d.poid_id0, d.device_id, d.poid_type, d.state_id, ds.account_obj_id0, dsim.imsi,(select distinct login from uniqueness_t where decode(d.poid_type, '/device/num', d.device_id, '/device/sim', dsim.imsi) = login) login from   device_t d, device_services_t ds, device_sim_t dsim, uniqueness_t u where  d.poid_id0 = ds.obj_id0(+) and    d.device_id like ('").concat(SIM).concat("%') and    d.poid_id0 = dsim.obj_id0 (+) )");
			ResultSet rs=stmt.executeQuery(sim1);
			report.writeTorun(runlog, "Query executed");
			report.writeTorun(runlog, "Executed Account Check Query for "+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+sim1);
			
			
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("BILLING_DEVICE");
			HSSFRow rowhead=sht.createRow( 0 );
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
			File = datapre.concat("\\BillingSIM_Device.xls");
		    FileOutputStream fileOut = new FileOutputStream(File,false);
		    wb.write(fileOut);
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
		 				} 
		 				while(rs.next());
		 			} 
		 			else
		 			{
		 				
		 				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"Device Check Query for SIM - Fail"+System.lineSeparator()+"------------------------------------");
		 				checkCounter++;
 						conditionCounter++;
		 				//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for SIM - Fail"+System.lineSeparator()+"------------------------------------");
		 			} // end Else checking for empty result set

		 			if(checkCounter > 0)
		 			{
		 				checkCounter++;
 						conditionCounter++;
		 				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"Device Check Query for SIM - Fail"+System.lineSeparator()+"------------------------------------");
		 				//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for SIM - Fail"+System.lineSeparator()+"------------------------------------");
		 			} 
		 			else
		 			{
		 				
		 				report.writeTorun(runlog, "------------------------------------"+System.lineSeparator()+"Device Check Query for SIM- Pass"+System.lineSeparator()+"------------------------------------");
		 				
		 				//report.writeToFile(reportfile, "------------------------------------"+System.lineSeparator()+"Device Check Query for SIM- Pass"+System.lineSeparator()+"------------------------------------");
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

	
	public  void billingMSISDN_AcccountCheck(String MSISDN)throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException,NullPointerException
	{
		
	try
	{
		int checkCounter=0;
		//report.writeTorun(runlog, "***********************"+System.lineSeparator() +"Billing Check check for MSISDN"+System.lineSeparator()+"***********************" );
		
		String msisdn2=("select ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0,c.account_no from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(MSISDN).concat("'");	;
		ResultSet rs=stmt.executeQuery(msisdn2);
		report.writeTorun(runlog,"Query executed");
		report.writeTorun(runlog, "Executed Device Check Query for "+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+msisdn2);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("BILLING_ACCOUNT");
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
		
		File = datapre.concat("\\BillingMSISDN_Account.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	 // Resetting the pointer of result set before first row
	 			rs.beforeFirst();

	 			// Checking the condition for each available row
	 			if(!rs.next())
	 			{
	 				
	 				report.writeTorun(runlog, "--------------------------------------"+System.lineSeparator()+"Device Account Query for MSISDN - Pass"+System.lineSeparator()+"--------------------------------------");
	 				
	 				//report.writeToFile(reportfile, "--------------------------------------"+System.lineSeparator()+"Device Account Query for MSISDN - Pass"+System.lineSeparator()+"--------------------------------------");
	 			}
	 			else
	 			{
	 				
	 				report.writeTorun(runlog, "--------------------------------------"+System.lineSeparator()+"Device Account Query for MSISDN - Fail"+System.lineSeparator()+"--------------------------------------");
	 				//report.writeToFile(reportfile, "--------------------------------------"+System.lineSeparator()+"Device Account Query for MSISDN - Fail"+System.lineSeparator()+"--------------------------------------");

	 				checkCounter++;
	 				conditionCounter++;
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
	}   
	
	

	public  void billingSIM_AcccountCheck(String SIM)throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException,NullPointerException
	{
		
	try
	{
		int checkCounter=0;
		//report.writeTorun(runlog, "***********************"+System.lineSeparator() +"Billing Account check for SIM"+System.lineSeparator()+"***********************" );
		
		String sim2=("select ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0,c.account_no from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(SIM).concat("'");	;
		ResultSet rs=stmt.executeQuery(sim2);
		report.writeTorun(runlog, "Executed Device Check Query for "+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+sim2);
		report.writeTorun(runlog, "Query Executed");
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("BILLING_ACCOUNT");
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
		
		File = datapre.concat("\\BillingSIM_Account.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	 // Resetting the pointer of result set before first row
	 			rs.beforeFirst();

	 			// Checking the condition for each available row
	 			if(!rs.next())
	 			{
	 				
	 				report.writeTorun(runlog, "--------------------------------------"+System.lineSeparator()+"Device Account Query for SIM - Pass"+System.lineSeparator()+"--------------------------------------");
	 				//report.writeToFile(reportfile, "--------------------------------------"+System.lineSeparator()+"Device Account Query for SIM - Pass"+System.lineSeparator()+"--------------------------------------");
	 				
	 			}
	 			else
	 			{	
	 				report.writeTorun(runlog, "--------------------------------------"+System.lineSeparator()+"Device Account Query for SIM - Fail"+System.lineSeparator()+"--------------------------------------");

	 				checkCounter++;
	 				conditionCounter++;
	 				
	 				//report.writeToFile(reportfile, "--------------------------------------"+System.lineSeparator()+"Device Account Query for SIM - Fail"+System.lineSeparator()+"--------------------------------------");
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
	}   
	

	public static void connection() throws ClassNotFoundException, SQLException, IOException
	{
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_Consumer.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet worksheet = testdata.getSheet("Server_Information");
	    HSSFRow ro1 = worksheet.getRow(4);
	    ip=ro1.getCell(2).getStringCellValue();
	    HSSFRow ro2 = worksheet.getRow(5);
	    ser_username=ro2.getCell(2).getStringCellValue();
	    HSSFRow ro3 = worksheet.getRow(6);
	    ser_password=ro3.getCell(2).getStringCellValue();
	    Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection(ip,ser_username,ser_password);
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		report.writeTorun(runlog, "DB Connection estabilished");
		//writer.println("DB Connection established");

	}
/*	
	public static void data_read() throws IOException
	{
		row=2;
		
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_Consumer.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet testSheet1 = testdata.getSheet("Activation");
	    SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
	    MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		report.writeTorun(runlog, "Data read successfully");
	    
	}*/

	}
