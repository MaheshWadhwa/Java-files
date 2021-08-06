package IUC_MNP;
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



public class DATACLEAN_SIEBEL_ASSET 
{
	static int row=2;
	static Statement stmt;
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
	
	public DATACLEAN_SIEBEL_ASSET(String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{

		DATACLEAN_SIEBEL_ASSET.runlog=runlog;
		DATACLEAN_SIEBEL_ASSET.reportfile=reportfile;
		DATACLEAN_SIEBEL_ASSET.datapre=datapre;
		DATACLEAN_SIEBEL_ASSET.report=report;
	}
	
	public int execute(String MSISDN,String SIM) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException, InterruptedException,NullPointerException
	{
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		conditionCounter = 0;
		//data_read(MSISDN,SIM);
		connection();
		/*msisdn1_query(MSISDN,SIM);
		msisdn2_query(MSISDN,SIM);
		msisdn3_query(MSISDN,SIM);
		msisdn4_query(MSISDN,SIM);
		sim1_query(MSISDN,SIM);
		sim2_query(MSISDN,SIM);
		sim3_query(MSISDN,SIM);
		sim4_query(MSISDN,SIM);
		*/mnp(MSISDN);
		//System.out.println(conditionCounter);
		return conditionCounter;
		
		}

	
	public void connection() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_IUC_MNP.xls");
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
	
	public void msisdn1_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			
		int checkCounter=0;
		
		String msisdn1=("SELECT A.OPER_STATUS_CD,B.NAME FROM SIEBEL.CX_INVTASSET A,SIEBEL.S_PROD_INT B WHERE A.PROD_ID = B.ROW_ID AND A.ASSET_NUM='").concat(MSISDN).concat("'");
		
		ResultSet rs=stmt.executeQuery(msisdn1);
		report.writeTorun(runlog, "Executed MSISDN Query 1"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn1);
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
		File = datapre.concat("\\Siebel_MSISDN_1.xls");
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
	 			} 	

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
	
	
	public void msisdn2_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
		int checkCounter=0;
		//report.writeTorun(runlog, "***** MSISDN - Validation #2*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #2*******");
		
		
		String msisdn2=("select a.ROW_ID,b.EECC_MSISDN_NUM,a.CUST_STAT_CD,a.CREATED from siebel.S_ORG_EXT a,siebel.S_ORG_EXT_X b where a.ROW_ID = b.PAR_ROW_ID and b.EECC_MSISDN_NUM='").concat(MSISDN).concat("' order by created desc");
		ResultSet rs=stmt.executeQuery(msisdn2);
		
		report.writeTorun(runlog, "Executed MSISDN Query 2"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn2);
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
		File = datapre.concat("\\Siebel_MSISDN_2.xls");
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
		
	public void msisdn3_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
		int checkCounter=0;
		//report.writeTorun(runlog, "***** MSISDN - Validation #3*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #3*******");
		//writer.println("***********************************************************************************************");
		String msisdn3=("SELECT A.STATUS_CD,a.created FROM SIEBEL.S_ORDER A,SIEBEL.CX_INVTASSET B WHERE A.X_EECC_EA_MSISDN_ID= B.ROW_ID AND B.ASSET_NUM='").concat(MSISDN).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(msisdn3);
		report.writeTorun(runlog, "Executed MSISDN Query 3"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn3);
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
		File = datapre.concat("\\Siebel_MSISDN_3.xls");
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
	public void msisdn4_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		//writer.println("***********************************************************************************************");
		int checkCounter=0;
		//report.writeTorun(runlog, "***** MSISDN - Validation #4*******");
		//report.writeToFile(reportfile, "***** MSISDN - Validation #4*******");

		String msisdn4=("SELECT A.STATUS_CD,A.CREATED,A.ROW_ID FROM SIEBEL.S_ORDER_ITEM A,SIEBEL.CX_INVTASSET B WHERE A.ASSET_ID= B.ROW_ID AND B.ASSET_NUM ='").concat(MSISDN).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(msisdn4);
		report.writeTorun(runlog, "Executed MSISDN Query 4"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn4);
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
		File = datapre.concat("\\Siebel_MSISDN_4.xls");
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
		public void sim1_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
			//writer.println("***********************************************************************************************");
		int checkCounter=0;
		//report.writeTorun(runlog, "***** SIM - Validation #1*******");
		//report.writeToFile(reportfile, "***** SIM - Validation #1*******");
		
		//writer.println("***********************************************************************************************");
		String sim1=("SELECT A.OPER_STATUS_CD,B.NAME FROM SIEBEL.CX_INVTASSET A,SIEBEL.S_PROD_INT B WHERE A.PROD_ID = B.ROW_ID AND A.ASSET_NUM='").concat(SIM).concat("'");
		ResultSet rs=stmt.executeQuery(sim1);
		
		report.writeTorun(runlog, "Executed SIM Query 1"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+sim1);
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
		File = datapre.concat("\\Siebel_SIM_1.xls");
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
		
		
		public void sim2_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		
			int checkCounter=0;
			
		//report.writeTorun(runlog, "***** SIM - Validation #2*******");
		//report.writeToFile(reportfile, "***** SIM - Validation #2*******");
		String sim2=("select c.ASSET_NUM,a.CUST_STAT_CD,a.CREATED from siebel.S_ORG_EXT a,siebel.S_ORG_EXT_X b,siebel.CX_INVTASSET c where a.ROW_ID = b.PAR_ROW_ID and b.EECC_SIMCARD_ID= c.ROW_ID and c.ASSET_NUM = '").concat(SIM).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(sim2);
		report.writeTorun(runlog, "Executed SIM Query 2"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+sim2);
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
		File = datapre.concat("\\Siebel_SIM_2.xls");
	    FileOutputStream fileOut = new FileOutputStream(File,false);
	    wb.write(fileOut);
	    fileOut.close();
	    //System.out.println("Siebel File has been created successfully ");
	    
	 // Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
 				do {
 					if((rs.getString(2).equalsIgnoreCase("Closed")) || (rs.getString(2).equalsIgnoreCase("Cancelled"))){
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

	public void sim3_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
		//data_read();
		//connection();
		
			int checkCounter=0;
			
			//report.writeTorun(runlog,"***** SIM - Validation #3*******");
			//report.writeToFile(reportfile,"***** SIM - Validation #3*******");
			String sim3=("SELECT A.STATUS_CD,a.CREATED FROM SIEBEL.S_ORDER A,SIEBEL.CX_INVTASSET B WHERE A.X_EECC_EA_SIMCARD_ID= B.ROW_ID AND B.ASSET_NUM='").concat(SIM).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(sim3);
		report.writeTorun(runlog, "Executed SIM Query 3"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+sim3);
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
		File = datapre.concat("\\Siebel_SIM_3.xls");
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
	
	public void sim4_query(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			
		int checkCounter=0;	

		report.writeTorun(runlog, " SIM - Validation #4");
		//report.writeToFile(reportfile, " SIM - Validation #4");
	
		
			//writer.println("***********************************************************************************************");
		String sim4=("SELECT a.STATUS_CD,a.CREATED,a.ROW_ID FROM SIEBEL.S_ORDER_ITEM a,siebel.CX_INVTASSET b WHERE a.ASSET_ID= b.ROW_ID  and b.ASSET_NUM ='").concat(SIM).concat("' order by a.created desc");
		ResultSet rs=stmt.executeQuery(sim4);
		report.writeTorun(runlog, "Executed SIM Query 4"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+sim4);
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
		File = datapre.concat("\\Siebel_SIM_4.xls");
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
		
		public void mnp(String MSISDN)throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
		{
			try
			{
				int checkCounter=0;
				//report.writeTorun(runlog, "***********************"+System.lineSeparator() +"Billing Account check for SIM"+System.lineSeparator()+"***********************" );
				
				String mnp_siebel=(("select asset_num,oper_status_cd from cx_invtasset where asset_num='").concat(MSISDN).concat("'"));
				ResultSet rs=stmt.executeQuery(mnp_siebel);
				report.writeTorun(runlog, "Executed MNP Query for "+System.lineSeparator()+"---------------------------------------"+System.lineSeparator()+mnp_siebel);
				report.writeTorun(runlog, "Query Executed");
				HSSFWorkbook wb=new HSSFWorkbook();
				HSSFSheet sht=wb.createSheet("MNP_Siebel");
				HSSFRow rowhead=sht.createRow( 0 );
				rowhead.createCell(0).setCellValue("DEVICE_ID");
				rowhead.createCell(1).setCellValue("OPER_STATUS_CD");
				int i=1;
				while(rs.next())
				{
					HSSFRow row=sht.createRow(i);
					row.createCell(0).setCellValue(rs.getString(1));
					row.createCell(1).setCellValue(rs.getString(2));
					i++;
				}
				
				File = datapre.concat("\\MNP_SIEBEL.xls");
			    FileOutputStream fileOut = new FileOutputStream(File,false);
			    wb.write(fileOut);
			    fileOut.close();
	
			    rs.beforeFirst();
			    if(rs.next())
			    {
			    	do
			    	{
			    		if(rs.getString(2).equalsIgnoreCase("MobilyNumPortOut"))
			    		{
			    			//TBD
			    		}
			    		else
			    		{
			    			checkCounter++;
			    			conditionCounter++;
			    		}
			    	}
			    
			    	while(rs.next());
			    
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
