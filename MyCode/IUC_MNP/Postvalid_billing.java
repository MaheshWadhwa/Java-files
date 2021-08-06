package IUC_MNP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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



public class Postvalid_billing {
	
	static int row=2;
	//static int cellId=CucMain.cellId;
	static Statement stmt;
	static String MSISDN;
	static String SIM;
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String Fil;
	//static String post_path=CucMain.Postpre;
	static String account_no;
	
	/*static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;*/
	static int conditionCounter = 0;
	static int checkCounter = 0;

	static String runlog;
	static String reportfile;
	static CommonUtilitiesWriter report;
	static String Postpre;
	
	public Postvalid_billing(String runlog,String reportfile,String Postpre,CommonUtilitiesWriter report)
	{
		Postvalid_billing.runlog=runlog;
		Postvalid_billing.reportfile=reportfile;
		Postvalid_billing.Postpre=Postpre;
		Postvalid_billing.report=report;
		//Postvalid_billing.MSISDN=MSISDN;
		
	}
	
	public int post_billing(String MSISDN) throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{
		
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Check in Billing"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		connection();
		//data_read();
		billing(MSISDN);
		billing_device();
		return conditionCounter;
		}
	
	public static void billing_device() throws ClassNotFoundException, SQLException, IOException , NullPointerException
		{
		
		try
		{
			String query=("select c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where c.poid_id0=b.Account_OBJ_ID0 and ab.POID_ID0=b.obj_id0 and c.account_no='").concat(account_no).concat("'");
			ResultSet rs=stmt.executeQuery(query);
			//System.out.println("Query executed");
			report.writeTorun(runlog, "Executed Billing Query 1"+System.lineSeparator()+"-------------------------"+System.lineSeparator()+query);
			//writer.println("Query executed");
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("BILL_DEVICE");
			HSSFRow rowhead=sht.createRow( 0 );
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
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				row.createCell(3).setCellValue(rs.getString(4));
				row.createCell(4).setCellValue(rs.getString(5));
				i++;
				
			}

			Fil = Postpre.concat("\\Billing_device.xls");
			FileOutputStream fileOut = new FileOutputStream(Fil,false);
			wb.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"Billing device File has been created successfully ");
			
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
            
	
	public static void billing(String MSISDN) throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{
		
		try
		{
			String query=("select DISTINCT c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(MSISDN).concat("'");
			ResultSet rs=stmt.executeQuery(query);
			report.writeToFile(runlog, "Executed Billing Query 2"+System.lineSeparator()+"-------------------------"+System.lineSeparator()+query);
			//System.out.println("Query executed");
			//writer.println("Query executed");
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("BILL");
			HSSFRow rowhead=sht.createRow( 0 );
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
				row.createCell(1).setCellValue(rs.getString(2));
				row.createCell(2).setCellValue(rs.getString(3));
				row.createCell(3).setCellValue(rs.getString(4));
				row.createCell(4).setCellValue(rs.getString(5));
				i++;
				
			}

			File = Postpre.concat("\\Billing.xls");
			FileOutputStream fileOut = new FileOutputStream(File,false);
			wb.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"Billing File has been created successfully ");
			//	writer.println("EMADEV File has been created successfully ");
			
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				account_no = rs.getString(1);
				report.writeTorun(runlog, "Fetched the account number "+account_no);
			} else {
				checkCounter++;
				conditionCounter++;
				report.writeToFile(runlog, "No account number seen");
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
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_IUC_MNP.xls");
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
		report.writeTorun(runlog,"DB Connection established");
		
}
}
