package CUC_SIM_Swap;
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

public class DATACLEAN_EMADEV 
{
	static Statement stmt;
	static String IMSI;
	static String EMA_Path=SIMSwapMain.datapre;
	static String File;
	static String SIM;
	static String MSISDN;
	static String ip;
	static String ser_username;
	static String ser_password;
	static int row=6;
	static int cellId=SIMSwapMain.cellId;
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=SIMSwapMain.runlog;
	static String reportfile=SIMSwapMain.reportfile;
	static int conditionCounter = 0;
	static int checkCounter=0;
	public int emadev_clean() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		conditionCounter=0;
		data_read();
		connection();		
		status();
		return conditionCounter;
		
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
		report.writeTorun(runlog, "DB Connection established");
		
	}
	
	public static void data_read() throws IOException
	{
		row=6;
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet testSheet1 = testdata.getSheet("Activation");
	    SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
	    IMSI=testSheet1.getRow(++row).getCell(cellId).getStringCellValue();
		MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		
		report.writeTorun(runlog, "data read successfully");
	}
	
	public static void status() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
						
		try
		{
			
		checkCounter=0;
		report.writeTorun(runlog, "*****  IMSI Status - Validation*******");
		
		String query=("select * from MED_AUC_DATA WHERE IMSI='IMSI,").concat(IMSI).concat("'");
		ResultSet rs=stmt.executeQuery(query);
		System.out.println("Query executed");
		//writer.println("Query executed");
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("EMADEV");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("IMSI");
		rowhead.createCell(1).setCellValue("KI");
		rowhead.createCell(2).setCellValue("PARAMETER1");
		rowhead.createCell(3).setCellValue("PARAMETER2");
		rowhead.createCell(4).setCellValue("PARAMETER3");
		rowhead.createCell(5).setCellValue("STATUS");
		rowhead.createCell(6).setCellValue("TIME_STAMP_D");
		rowhead.createCell(7).setCellValue("FILE_NAME");
		rowhead.createCell(8).setCellValue("PARAMETER4");
		rowhead.createCell(9).setCellValue("PARAMETER5");
		
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			row.createCell(2).setCellValue(rs.getString(3));
			row.createCell(3).setCellValue(rs.getString(4));
			row.createCell(4).setCellValue(rs.getString(5));
			row.createCell(5).setCellValue(rs.getString(6));
			row.createCell(6).setCellValue(rs.getString(7));
			row.createCell(7).setCellValue(rs.getString(8));
			row.createCell(8).setCellValue(rs.getString(9));
			row.createCell(9).setCellValue(rs.getString(10));
			i++;
		
		}
		File = EMA_Path.concat("\\EMADEV.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    report.writeTorun(runlog, "EMADEV File has been created successfully ");
	    
		
	   rs.beforeFirst();
	  
	   if(rs.next())
	   {
		   String s=rs.getString(5);		
		   if(rs.getString(6).equalsIgnoreCase("0"))
				{
					report.writeTorun(runlog, "Status is : " +s);
					report.writeTorun(runlog," Device check - Pass");
				}
				else
				{
					report.writeTorun(runlog, "Status is : " +s);
					report.writeToFile(reportfile, "Device check - Fail");
					checkCounter++;
						conditionCounter++;
				}
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
	}
		}
