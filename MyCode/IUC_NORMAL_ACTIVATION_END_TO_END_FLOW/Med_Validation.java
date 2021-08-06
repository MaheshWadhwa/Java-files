package IUC_NORMAL_ACTIVATION_END_TO_END_FLOW;

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
	static Statement stmt;
	static String MSISDN;
	
	public static void main(String [] args) throws ClassNotFoundException, SQLException, IOException
	{
		connection();
		data_read();
		query();
		File file=new File("C:\\Automated_Execution\\Output\\Post Validation\\Mediation\\Med.xls");
		FileInputStream fileInputStream = new FileInputStream(file);
		HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
	    HSSFSheet worksheet = workbook1.getSheet("MED");
		HSSFRow row1 = worksheet.getRow(1);
		HSSFCell cellA1 = row1.getCell(0);
		String a1Val = cellA1.getStringCellValue();
		int y = Integer.parseInt(a1Val);
		if (y==2)
		{
			System.out.println("Statis is " +y);
			System.out.println("Activation success in Mediator");
			//writer.println("Activation success in Mediator");
		}
		else
		{
			System.out.println("Activation not reached in Mediator");
			//writer.println("Activation not reached in Mediator");
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
		String File ="C:\\Automated_Execution\\Output\\Post Validation\\Mediation\\Med.xls";
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    System.out.println("MED File has been created successfully ");
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
	
	public static void connection() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.186:1522:emadev","CDFDBA","cdfdba");
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		System.out.println("DB Connection established");
	}
	
	public static void data_read() throws IOException
	{
		File file_data=new File ("C:\\Automated_Execution\\Input\\PostValidation.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet worksheet = testdata.getSheet("Data");
	    HSSFRow ro1 = worksheet.getRow(1);
	    MSISDN = ro1.getCell(1).getStringCellValue();
	    System.out.println("Data read successfully");

	}

	
}
