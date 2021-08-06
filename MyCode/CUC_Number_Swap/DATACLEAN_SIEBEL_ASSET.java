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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.*;

	import javax.swing.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

//import CUC_Number_Swap.ConsumerPrepaid;

public class DATACLEAN_SIEBEL_ASSET 
{
	static String MSISDN;
	static String SIM;
	static Statement stmt; 
	static PrintWriter writer;
	static String IMSI;
	static String Siebel_Path=NumSwapMain.datapre;
	static String File;
	static String ip;
	static String ser_username;
	static String ser_password;
	static int row=6;
	static int cellId=NumSwapMain.cellId;
	static int conditionCounter = 0;
	
	
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=NumSwapMain.runlog;
	static String reportfile=NumSwapMain.reportfile;
	//static int checkCounter;
	
	public int execute() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, InterruptedException,NullPointerException
	{
		conditionCounter = 0;
		data_read();
		connection();
		msisdn1_query();
		msisdn2_query();
		msisdn3_query();
		msisdn4_query();
		sim1_query();
		sim2_query();
		sim3_query();
		sim4_query();
		return conditionCounter;
		}
	
	public void data_read() throws IOException,FileNotFoundException
	{
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet testSheet1 = testdata.getSheet("Activation");
	    SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
	    MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		report.writeTorun(runlog,"Data read successfully");
	    
	  }
	
	
	public void connection() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet worksheet = testdata.getSheet("Server_Information");
	    HSSFRow ro1 = worksheet.getRow(1);
	    ip=ro1.getCell(2).getStringCellValue();
	    HSSFRow ro2 = worksheet.getRow(2);
	    ser_username=ro2.getCell(2).getStringCellValue();
	    HSSFRow ro3 = worksheet.getRow(3);
	    ser_password=ro3.getCell(2).getStringCellValue();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection(ip,ser_username,ser_password);
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		report.writeTorun(runlog,"DB Connection established");
		
	}
	
	public void msisdn1_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
			
		int checkCounter=0;
		
		report.writeTorun(runlog, "***** MSISDN - Validation #1*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #1*******");
		
		String msisdn1=("SELECT A.OPER_STATUS_CD,B.NAME FROM SIEBEL.CX_INVTASSET A,SIEBEL.S_PROD_INT B WHERE A.PROD_ID = B.ROW_ID AND A.ASSET_NUM='").concat(MSISDN).concat("'");
		
		ResultSet rs=stmt.executeQuery(msisdn1);
		report.writeTorun(runlog,"Qeury executed successfully ");
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("msisdn1");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("OPER_STATUS_CD");
		rowhead.createCell(1).setCellValue("NAME");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_MSISDN_1.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	 // Resetting the pointer of result set before first row
	 			rs.beforeFirst();

	 			// Checking the condition for each available row
	 			if(rs.next()){
	 				do {
	 					if(rs.getString(1).equalsIgnoreCase("Available")){
	 						// TBD
	 					} else if(rs.getString(1).equalsIgnoreCase("Selected")){
	 						checkCounter++;
	 						conditionCounter++;
	 					} // end Else If checking for Selected
	 				} 
	 				while(rs.next());
	 			} 
	 			else 
	 			{
	 				
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Pass" +System.lineSeparator()+"---------------------");
	 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Pass"+System.lineSeparator()+"---------------------");
	 			} // end Else checking for empty result set	

	 			if(checkCounter > 0){
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Fail"+System.lineSeparator()+"---------------------");
	 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 1 =Fail"+System.lineSeparator()+"---------------------");
	 				checkCounter++;
						conditionCounter++;
	 			} 
	 			else
	 			{
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Pass"+System.lineSeparator()+"---------------------");
	 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Pass"+System.lineSeparator()+"---------------------");
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
	
	
	public void msisdn2_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
		int checkCounter=0;
		report.writeTorun(runlog, "***** MSISDN - Validation #2*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #2*******");
		
		
		String msisdn2=("select a.ROW_ID,b.EECC_MSISDN_NUM,a.CUST_STAT_CD,a.CREATED from siebel.S_ORG_EXT a,siebel.S_ORG_EXT_X b where a.ROW_ID = b.PAR_ROW_ID and b.EECC_MSISDN_NUM='").concat(MSISDN).concat("' order by created desc");
		ResultSet rs=stmt.executeQuery(msisdn2);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("msisdn2");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("ROW_ID");
		rowhead.createCell(1).setCellValue("EECC_MSISDN_NUM");
		rowhead.createCell(2).setCellValue("CUST_STAT_CD");
		rowhead.createCell(3).setCellValue("CREATED");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			row.createCell(2).setCellValue(rs.getString(3));
			row.createCell(3).setCellValue(rs.getString(4));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_MSISDN_2.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	     
	 // Resetting the pointer of result set before first row
	 			rs.beforeFirst();

	 			// Checking the condition for each available row
	 			if(rs.next()){
	 				do {
	 					if((rs.getString(3).equalsIgnoreCase("Closed")) || (rs.getString(3).equalsIgnoreCase("Cancelled"))){
	 						// TBD
	 					} else
	 					{
	 						checkCounter++;
	 						conditionCounter++;
	 					} // end Else checking for any status other than Closed or Cancelled
	 				} 
	 				while(rs.next());
	 			} 
	 			else
	 			{
	 				
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Pass"+System.lineSeparator()+"---------------------");
	 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Pass"+System.lineSeparator()+"---------------------");
	 			} // end Else checking for empty result set

	 			if(checkCounter > 0)
	 			{
	 				
	 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Fail"+System.lineSeparator()+"---------------------");
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Fail"+System.lineSeparator()+"---------------------");
	 				checkCounter++;
						conditionCounter++;
	 			}
	 			else 
	 			{
	 				
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Pass"+System.lineSeparator()+"---------------------");
	 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Pass"+System.lineSeparator()+"---------------------");
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
		
	public void msisdn3_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
		int checkCounter=0;
		report.writeTorun(runlog, "***** MSISDN - Validation #3*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #3*******");
		//writer.println("***********************************************************************************************");
		String msisdn3=("SELECT A.STATUS_CD,a.created FROM SIEBEL.S_ORDER A,SIEBEL.CX_INVTASSET B WHERE A.X_EECC_EA_MSISDN_ID= B.ROW_ID AND B.ASSET_NUM='").concat(MSISDN).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(msisdn3);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("msisdn3");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("STATUS_CD");
		rowhead.createCell(1).setCellValue("CREATED");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_MSISDN_3.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    //System.out.println("Siebel File has been createdsuccessfully ");
	    
	 // Resetting the pointer of result set before first row
	 			rs.beforeFirst();

	 			// Checking the condition for each available row
	 			if(rs.next()){
	 				do {
	 					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
	 						// TBD
	 					} else
	 					{
	 						checkCounter++;
	 						conditionCounter++;
	 					} // end Else checking for any status other than Closed or Cancelled
	 				} 
	 			
	 				while(rs.next());
	 			} 
	 			else
	 			{
	 				
	 			report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Pass"+System.lineSeparator()+"---------------------");
	 			//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Pass"+System.lineSeparator()+"---------------------");
	 			} // end Else checking for empty result set
	 			
	 			if(checkCounter > 0)
	 			{
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Fail"+System.lineSeparator()+"---------------------");
		 			//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Fail"+System.lineSeparator()+"---------------------");
	 				checkCounter++;
						conditionCounter++;	
	 			}
	 			else 
	 			{
	 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Pass"+System.lineSeparator()+"---------------------");
		 			//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Pass"+System.lineSeparator()+"---------------------");
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
	public void msisdn4_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
		int checkCounter=0;
		report.writeTorun(runlog, "***** MSISDN - Validation #4*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #4*******");

		String msisdn4=("SELECT A.STATUS_CD,A.CREATED,A.ROW_ID FROM SIEBEL.S_ORDER_ITEM A,SIEBEL.CX_INVTASSET B WHERE A.ASSET_ID= B.ROW_ID AND B.ASSET_NUM ='").concat(MSISDN).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(msisdn4);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("msisdn4");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("TATUS_CD");
		rowhead.createCell(1).setCellValue("CREATED");
		rowhead.createCell(2).setCellValue("ROW_ID");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			row.createCell(2).setCellValue(rs.getString(3));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_MSISDN_4.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    report.writeTorun(runlog,"Siebel File has been created successfully ");
	    
	    rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else
					{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} 
			
				while(rs.next());
			} 
			else
			{
				
				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Pass"+System.lineSeparator()+"---------------------");
				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Pass"+System.lineSeparator()+"---------------------");
			} // end Else checking for empty result set
			
			if(checkCounter > 0)
			{
				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Fail"+System.lineSeparator()+"---------------------");
				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Fail"+System.lineSeparator()+"---------------------");
				checkCounter++;
					conditionCounter++;
			} 
			else 
			{
				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Pass"+System.lineSeparator()+"---------------------");
				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Pass"+System.lineSeparator()+"---------------------");
				}
   

	    //writer.println("Siebel File has been created successfully ");
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
		public void sim1_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
			//writer.println("***********************************************************************************************");
		int checkCounter=0;
		report.writeTorun(runlog, "***** SIM - Validation #1*******");
		//report.writeToFile(reportfile, "***** SIM - Validation #1*******");
		
		//writer.println("***********************************************************************************************");
		String sim1=("SELECT A.OPER_STATUS_CD,B.NAME FROM SIEBEL.CX_INVTASSET A,SIEBEL.S_PROD_INT B WHERE A.PROD_ID = B.ROW_ID AND A.ASSET_NUM='").concat(SIM).concat("'");
		ResultSet rs=stmt.executeQuery(sim1);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("sim1");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("OPER_STATUS_CD");
		rowhead.createCell(1).setCellValue("NAME");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			i++;
			
		}
		File = Siebel_Path.concat("\\Siebel_SIM_1.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    //System.out.println("Siebel File has been created successfully ");  
	    
	 // Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
 				do {
 					if(rs.getString(1).equalsIgnoreCase("Available")){
 						// TBD
 					} else if(rs.getString(1).equalsIgnoreCase("Selected")){
 						checkCounter++;
 						conditionCounter++;
 					} // end Else If checking for Selected
 				} 
 				while(rs.next());
 			} 
 			else 
 			{
 				
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 1 = Pass"+System.lineSeparator()+"---------------------");
 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 1 = Pass"+System.lineSeparator()+"---------------------");
 			} // end Else checking for empty result set	

 			if(checkCounter > 0){
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 1 = Fail"+System.lineSeparator()+"---------------------");
 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"Fail"+System.lineSeparator()+"---------------------");
 				checkCounter++;
					conditionCounter++;
 			} 
 			else
 			{
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 1 = Pass"+System.lineSeparator()+"---------------------");
 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 1 = Pass"+System.lineSeparator()+"---------------------");
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
		
		
		public void sim2_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		
			int checkCounter=0;
			
			report.writeTorun(runlog, "***** SIM - Validation #2*******");
			//report.writeToFile(reportfile, "***** SIM - Validation #2*******");
		String sim2=("select c.ASSET_NUM,a.CUST_STAT_CD,a.CREATED from siebel.S_ORG_EXT a,siebel.S_ORG_EXT_X b,siebel.CX_INVTASSET c where a.ROW_ID = b.PAR_ROW_ID and b.EECC_SIMCARD_ID= c.ROW_ID and c.ASSET_NUM = '").concat(SIM).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(sim2);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("sim2");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("ASSET_NUM");
		rowhead.createCell(1).setCellValue("CUST_STAT_CD");
		rowhead.createCell(2).setCellValue("CREATED");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			row.createCell(2).setCellValue(rs.getString(3));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_SIM_2.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    //System.out.println("Siebel File has been created successfully ");
	    
	 // Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
 				do {
 					if((rs.getString(3).equalsIgnoreCase("Closed")) || (rs.getString(3).equalsIgnoreCase("Cancelled"))){
 						// TBD
 					}
 					else
 					{
 						checkCounter++;
 						conditionCounter++;
 					} // end Else checking for any status other than Closed or Cancelled
 				} 
 				while(rs.next());
 			} 
 			else
 			{
 				
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 2 = Pass"+System.lineSeparator()+"---------------------");
 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 2 = Pass"+System.lineSeparator()+"---------------------");
 			} // end Else checking for empty result set

 			if(checkCounter > 0)
 			{
 				
 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 2 = Fail"+System.lineSeparator()+"---------------------");
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 2 = Fail"+System.lineSeparator()+"---------------------");
 				checkCounter++;
					conditionCounter++;
 			}
 			else 
 			{
 				
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 2 = Pass"+System.lineSeparator()+"---------------------");
 				//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 2 = Pass"+System.lineSeparator()+"---------------------");
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

	public void sim3_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		
			int checkCounter=0;
			
			report.writeTorun(runlog,"***** SIM - Validation #3*******");
			//report.writeToFile(reportfile,"***** SIM - Validation #3*******");
			String sim3=("SELECT A.STATUS_CD,a.CREATED FROM SIEBEL.S_ORDER A,SIEBEL.CX_INVTASSET B WHERE A.X_EECC_EA_SIMCARD_ID= B.ROW_ID AND B.ASSET_NUM='").concat(SIM).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(sim3);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("sim3");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("STATUS_CD");
		rowhead.createCell(1).setCellValue("CREATED");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_SIM_3.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    //System.out.println("Siebel File has been created successfully ");
	    
	 // Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
 				do {
 					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
 						// TBD
 					} 
 					else
 					{
 						checkCounter++;
 						conditionCounter++;
 					} // end Else checking for any status other than Closed or Cancelled
 				} 
 			
 				while(rs.next());
 			} 
 			else
 			{
 				
 			report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 3 = Pass"+System.lineSeparator()+"---------------------");
 			//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 3 = Pass"+System.lineSeparator()+"---------------------");
 			} // end Else checking for empty result set
 			
 			if(checkCounter > 0)
 			{
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 3 = Fail"+System.lineSeparator()+"---------------------");
	 			//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 3 = Fail"+System.lineSeparator()+"---------------------");
 				checkCounter++;
					conditionCounter++;	
 			}
 			else 
 			{
 				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 3 = Pass"+System.lineSeparator()+"---------------------");
	 			//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 3 = Pass"+System.lineSeparator()+"---------------------");
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
	
	public void sim4_query() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			
		int checkCounter=0;	

		report.writeTorun(runlog, " SIM - Validation #4");
		//report.writeToFile(reportfile, " SIM - Validation #4");
	
		
			//writer.println("***********************************************************************************************");
		String sim4=("SELECT a.STATUS_CD,a.CREATED,a.ROW_ID FROM SIEBEL.S_ORDER_ITEM a,siebel.CX_INVTASSET b WHERE a.ASSET_ID= b.ROW_ID  and b.ASSET_NUM ='").concat(SIM).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(sim4);
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sht=wb.createSheet("sim4");
		HSSFRow rowhead=sht.createRow( 0 );
		rowhead.createCell(0).setCellValue("STATUS_CD");
		rowhead.createCell(1).setCellValue("CREATED");
		rowhead.createCell(2).setCellValue("ROW_ID");
		int i=1;
		while(rs.next())
		{
			HSSFRow row=sht.createRow(i);
			row.createCell(0).setCellValue(rs.getString(1));
			row.createCell(1).setCellValue(rs.getString(2));
			row.createCell(2).setCellValue(rs.getString(3));
			i++;
		
		}
		File = Siebel_Path.concat("\\Siebel_SIM_4.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    //System.out.println("Siebel File has been created successfully ");  
	 // Resetting the pointer of result set before first row
	 			rs.beforeFirst();

	 			// Checking the condition for each available row
	 			if(rs.next()){
					do {
						if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
							// TBD
						} else
						{
							checkCounter++;
							conditionCounter++;
						} // end Else checking for any status other than Closed or Cancelled
					} 
				
					while(rs.next());
				} 
				else
				{
					
					//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 4 = Pass"+System.lineSeparator()+"---------------------");
					report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 4 = Pass"+System.lineSeparator()+"---------------------");
				} // end Else checking for empty result set
				
				if(checkCounter > 0)
				{
					//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 4 = Fail"+System.lineSeparator()+"---------------------");
					report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 4 = Fail"+System.lineSeparator()+"---------------------");
					checkCounter++;
						conditionCounter++;
				} else 
				{
					//report.writeToFile(reportfile, "---------------------"+System.lineSeparator()+"SIM Query 4 = Pass"+System.lineSeparator()+"---------------------");
					report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"SIM Query 4 = Pass"+System.lineSeparator()+"---------------------");
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
