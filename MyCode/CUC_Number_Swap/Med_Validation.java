package CUC_Number_Swap;

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

public class Med_Validation 

{
	static int row=6;
	static int cellId=NumSwapMain.cellId;
	static Statement stmt;
	static String MSISDN;
	static String SIM;
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String post_path=NumSwapMain.Postpre;

	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=NumSwapMain.runlog;
	static String reportfile=NumSwapMain.reportfile;

	static int counditionCounter=0;

	public void post_mediation() throws ClassNotFoundException, SQLException, IOException
	{
		connection();
		data_read();
		query();

		File file=new File(File);
		FileInputStream fileInputStream = new FileInputStream(file);
		HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook1.getSheet("MED");
		HSSFRow row1 = worksheet.getRow(1);
		HSSFCell cellA1 = row1.getCell(0);
		String a1Val = cellA1.getStringCellValue();
		//System.out.println("Status is" +a1Val);
		int y = Integer.parseInt(a1Val);
		if (y==2)
		{

			report.writeTorun(runlog, "Statis is " +y);
			report.writeToFile(reportfile,"Activation reached mediation - Pass");
			report.writeTorun(runlog,"Activation success in Mediator");

		}
		else if (a1Val.isEmpty())
		{
			report.writeToFile(reportfile,"Activation reached mediation - Fail");
			report.writeTorun(runlog,"Activation reached mediation - Fail");
		}
		else 
		{
			report.writeTorun(runlog,"Activation reached mediation - Fail");

			report.writeToFile(reportfile,"Activation reached mediation - Fail");
		}
	}
	public static void query() throws SQLException

	{
		try
		{

			String query=("select status,service_trans_no,current_msisdn,current_sim,TIME_STAMP from med_cust_service_trans a WHERE CURRENT_MSISDN='").concat(MSISDN).concat("'ORDER BY TIME_STAMP DESC");
			ResultSet rs=stmt.executeQuery(query);
			System.out.println("Query executed");
			//writer.println("Query executed");
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("MED");
			HSSFRow rowhead=sht.createRow(0);
			rowhead.createCell(0).setCellValue("STATUS");
			rowhead.createCell(1).setCellValue("SERVICE_TRANS_NO");
			rowhead.createCell(2).setCellValue("CURRENT_MSISDN");
			rowhead.createCell(3).setCellValue("CURRENT_SIM");
			rowhead.createCell(4).setCellValue("TIME_STAMP");
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
			File =post_path.concat("\\Mediation.xls");
			FileOutputStream fileOut = new FileOutputStream(File,false);
			wb.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"MED File has been created successfully ");
			//writer.println("EMADEV File has been created successfully ");
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
		HSSFRow ro1 = worksheet.getRow(10);
		ip=ro1.getCell(2).getStringCellValue();
		HSSFRow ro2 = worksheet.getRow(11);
		ser_username=ro2.getCell(2).getStringCellValue();
		HSSFRow ro3 = worksheet.getRow(12);
		ser_password=ro3.getCell(2).getStringCellValue();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection(ip,ser_username,ser_password);
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		report.writeTorun(runlog,"DB Connection established");

	}

	public static void data_read() throws IOException
	{
		row=2;
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
		HSSFSheet testSheet1 = testdata.getSheet("Activation");
		SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
		MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		report.writeTorun(runlog,"Data read successfully");
	}


}
