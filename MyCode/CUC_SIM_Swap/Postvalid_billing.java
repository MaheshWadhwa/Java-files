package CUC_SIM_Swap;

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

	static int row=6;
	static int cellId=SIMSwapMain.cellId;
	static Statement stmt;
	static String oldMSISDN;
	static String newMSISDN;
	static String SIM;
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String File_new;
	static String Fil;
	static String New_Fil;
	static String post_path=SIMSwapMain.Postpre;
	static String account_no="";
	static String new_account_no="";
	static int checkCounter;
	static int conditionCounter;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=SIMSwapMain.runlog;
	static String reportfile=SIMSwapMain.reportfile;

	public int post_billing() throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{
		
		conditionCounter = 0;
		checkCounter = 0;
		connection();
		data_read();
		Old_billing();
		New_billing();

		
		return conditionCounter;
		
		/*File file=new File(File);
		FileInputStream fileInputStream = new FileInputStream(file);
		HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook1.getSheet("BILL");
		HSSFRow row1 = worksheet.getRow(1);
		HSSFCell cellA1 = row1.getCell(0);
		account_no = cellA1.getStringCellValue();
		//int y = Integer.parseInt(account_no);

		if (account_no.equals(null))

		{
			report.writeTorun(runlog,"Activation not reached to Billing and " +account_no);
			report.writeToFile(reportfile,"Activation reach Billing - Fail ");
		}

		billing_device();
		new_billing_device();
		File file1=new File(Fil);
		FileInputStream fileInputStream1 = new FileInputStream(file1);
		HSSFWorkbook workbook2 = new HSSFWorkbook(fileInputStream1);
		HSSFSheet worksheet2 = workbook2.getSheet("BILL_DEVICE");
		HSSFRow ro = worksheet2.getRow(1);
		HSSFCell cell = ro.getCell(0);
		String entry = cell.getStringCellValue();

		File file2=new File(New_Fil);
		FileInputStream fileInputStream2 = new FileInputStream(file2);
		HSSFWorkbook workbook3 = new HSSFWorkbook(fileInputStream2);
		HSSFSheet worksheet3 = workbook3.getSheet("New_BILL_DEVICE");
		HSSFRow ro1 = worksheet3.getRow(1);
		HSSFCell cell1 = ro1.getCell(0);
		String entry_new = cell1.getStringCellValue();


		if (entry_new.equals(null))
		{

			report.writeTorun(runlog,"Account # has no entry || Hence activation has not reached Builling");
			report.writeToFile(reportfile,"Activation reach billing - Fail");
		}
		else
		{
			report.writeTorun(runlog,"Account # has entry || Hence activation has reached Builling successfully");
			report.writeToFile(reportfile,"Activation reach billing - Pass");
		}
*/	
		}

	public static void billing_device() throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{

		try
		{
			String query=("select c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where c.poid_id0=b.Account_OBJ_ID0 and ab.POID_ID0=b.obj_id0 and c.account_no='").concat(account_no).concat("'");
			ResultSet rs=stmt.executeQuery(query);
			System.out.println("Query executed");
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

			Fil = post_path.concat("\\Billing_device.xls");
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

	public static void new_billing_device() throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{

		try
		{
			String query=("select c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where c.poid_id0=b.Account_OBJ_ID0 and ab.POID_ID0=b.obj_id0 and c.account_no='").concat(new_account_no).concat("'");
			ResultSet rs=stmt.executeQuery(query);
			//System.out.println("Query executed");
			//writer.println("Query executed");
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("New_BILL_DEVICE");
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

			New_Fil = post_path.concat("\\New_Billing_device.xls");
			FileOutputStream fileOut = new FileOutputStream(New_Fil,false);
			wb.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"New Billing device File has been created successfully ");

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

	public static void Old_billing() throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{

		try
		{
			String query=("select DISTINCT c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(oldMSISDN).concat("'");
			ResultSet rs=stmt.executeQuery(query);
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

			File = post_path.concat("\\OldBilling.xls");
			FileOutputStream fileOut = new FileOutputStream(File,false);
			wb.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"Old Billing File has been created successfully ");
			//	writer.println("EMADEV File has been created successfully ");

			rs.beforeFirst();
			if(rs.next()){
				account_no = rs.getString(1);
				report.writeTorun(runlog, "Fetched the account number - Fail"+account_no);
				checkCounter++;
				conditionCounter++;
				
			}
			else 
			{
				
				report.writeTorun(runlog, "No account number seen");
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

	public static void New_billing() throws ClassNotFoundException, SQLException, IOException , NullPointerException
	{

		try
		{
			String query=("select DISTINCT c.account_no,ab.POID_ID0,ab.device_id,ab.STATE_ID,b.Account_OBJ_ID0 from device_t ab,device_services_T b ,account_t c where ab.POID_ID0=b.obj_id0 and b.Account_OBJ_ID0 =c.POID_ID0 and device_id='").concat(newMSISDN).concat("'");
			ResultSet rs=stmt.executeQuery(query);
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

			File_new = post_path.concat("\\NewBilling.xls");
			FileOutputStream fileOut = new FileOutputStream(File_new,false);
			wb.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"New Billing File has been created successfully ");
			//	writer.println("EMADEV File has been created successfully ");
			rs.beforeFirst();
			if(rs.next()){
				new_account_no = rs.getString(1);
				report.writeTorun(runlog, "Fetched the account number "+new_account_no);
			} else {
				checkCounter++;
				conditionCounter++;
				report.writeTorun(runlog, "No account number seen");
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
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
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

	public static void data_read() throws IOException
	{
		//row=6;
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC_NumSwap.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
		HSSFSheet testSheet1 = testdata.getSheet("Activation");
		oldMSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		newMSISDN=testSheet1.getRow(4+row).getCell(cellId).getStringCellValue();
		report.writeTorun(runlog,"Data read successfully");


	}

}
