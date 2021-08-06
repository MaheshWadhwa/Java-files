package PostPaidRevamp_IUC_WithCash;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class EAICheck{
	static String ip;
	static String ser_username;
	static String ser_password;
	static String finalstr;
	private static int y;
	static String a;
	static String trackFile;
	static String statusFile;
	
	//static String ip;
	static String File;
	static String EAIpre;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static CommonUtilitiesWriter report;
	static String orderNumber;
	static String Actipre;
	
	
	public EAICheck(String runlog,String reportfile,String EAIpre,CommonUtilitiesWriter report)
	{
		EAICheck.runlog=runlog;
		EAICheck.reportfile=reportfile;
		EAICheck.EAIpre=EAIpre;
		EAICheck.report=report;
	}
	public int consumer(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
	
		report.writeToFile(reportfile, System.lineSeparator()+"EAI DB Check"+System.lineSeparator()+"------------");
		conditionCounter = 0;
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tEAI DB Check"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String temp = "";
		statusFile =EAIpre.concat("\\STATUS.xls");
		trackFile =EAIpre.concat("\\TRACK.xls");
		int i=1;
		while (i<15)
		{
			
			report.writeTorun(runlog,"Iteration" +i+ "of 15");
			report.writeToFile(reportfile,"Iteration" +i+ "of 15");
			
			status_write(MSISDN);
			status_read();
			track_write();
			track_read();
			if(y==4)
			{
				
				report.writeToFile(reportfile,"Activation status is - Pending - " +y+ "&& Activation is in ||" +a);
				report.writeTorun(runlog,"Activation status is - Pending - " +y);
				report.writeTorun(runlog,"Activation is  in || " +a);
			}
			else if(y==3)
			{
				report.writeToFile(reportfile,"Activation status is - Rejected - " +y+ "&& Activation is in ||" +a);
				report.writeTorun(runlog,"Activation status is - Rejected - " +y);
				report.writeTorun(runlog,"Activation is in || " +a);
				break;
			}


			else if (y==5)
			{
				report.writeToFile(reportfile,"Activation status is - Failed - " +y+ "&& Activation is in ||" +a);
				report.writeTorun(runlog,"Activation status is - Failed - " +y);
				report.writeTorun(runlog,"Activation is in || " +a);
				break;
			}
			else if(y==6)
			{
				
				report.writeTorun(runlog, "Status is : " +y);
				report.writeTorun(runlog, "Activation Success || "+a);
				report.writeTorun(runlog, "SR ID is "+finalstr);
				report.writeToFile(reportfile, "Status is : " +y);
				report.writeToFile(reportfile, "Activation Success || "+a);
				report.writeToFile(reportfile, "SR ID is "+finalstr);
				
				FileInputStream fis = new FileInputStream("C:\\Automated_Execution\\Input\\TestData_Revamp_IUC.xls");
				HSSFWorkbook workbook = new HSSFWorkbook(fis);
				HSSFSheet sheet = workbook.getSheet("Log_Cheeck");
				sheet.getRow(4).getCell(2).setCellValue(finalstr);
				fis.close();
				
				
				FileOutputStream fos = new FileOutputStream("C:\\Automated_Execution\\Input\\TestData_Revamp_IUC.xls");
				workbook.write(fos);
				fos.close();
				break;
			}
			
			else 
			
			{
				
				System.out.println("Status is : " +y);
				System.out.println("Activation Request currently in " +a);
				report.writeToFile(runlog, "Status is : " +y);
				report.writeToFile(runlog, "Activation currently in " +a);
				if(i==15){
					report.writeToFile(reportfile, "Status is : " +y);
					report.writeToFile(reportfile, "Activation currently in " +a);
				}
			}
			if(i==1)
			{
				System.out.println("Check EAI");
				temp = a;
				i++;
			}
			else if(temp.equalsIgnoreCase(a))
			{
				i++;
			}
			else
			{
				temp = a;
				i=1;
			}
			
			report.writeTorun(runlog,"Next iteration is in progress...");
			report.writeToFile(runlog, "Activation is in progress. Kindly wait for a minute to check where the request resides at that moment..."+System.lineSeparator());
			System.out.println("\nActivation is in progress. Kindly wait for a minute to check where the request resides at that moment...");
			Thread.sleep(60000);
		} // while end
		return y;
	} // Corporate end
	
	public static void status_write(String MSISDN) throws IOException,FileNotFoundException
	{
		try {
			
			
			
			//Test data reading
			String File =Actipre.concat("\\Order.xls");
			File file_data=new File (File);
			FileInputStream inputstrm= new FileInputStream(file_data);

			HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
			HSSFSheet worksheet = testdata.getSheet("Sheet1");
			HSSFRow ro = worksheet.getRow(1);
			String a1 = ro.getCell(0).getStringCellValue();
			testdata.close();
			inputstrm.close();
			
			
			report.writeTorun(runlog,"Status query started");

					
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","PGW","pgw");
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			System.out.println("DB Connection established");
			report.writeTorun(runlog,"DB Connection established");
			String b = ("select sr_id,LAST_UPDATE_TIME,status from sr_acv_lineservice_tbl where CHANNEL_TRANS_ID='").concat(a1).concat("' ORDER BY LAST_UPDATE_TIME DESC");
			ResultSet rs=stmt.executeQuery(b);
			System.out.println("Status query executed");
			report.writeTorun(runlog, "Executed Status Query"+System.lineSeparator()+"---------------------"+System.lineSeparator()+b);
			HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet("DB_VALIDATION_SR_ID");
		    HSSFRow rowhead = sheet.createRow((short) 0);
		    rowhead.createCell(0).setCellValue("SR_ID");
		    rowhead.createCell(1).setCellValue("STATUS");
		    rowhead.createCell(2).setCellValue("TIME_RECIEVED");
		   
		    int i = 1;
		    while (rs.next()){
		        HSSFRow row = sheet.createRow((short) i);
		        row.createCell( 0).setCellValue(rs.getString(1));
		        row.createCell(1).setCellValue(rs.getString(2));
		        row.createCell(2).setCellValue(rs.getString(3));
		        i++;
		    }
		    
		    statusFile =EAIpre.concat("\\STATUS.xls");
		    FileOutputStream fileOut = new FileOutputStream(statusFile,false);
			workbook.write(fileOut);
			fileOut.close();
			report.writeTorun(runlog,"Status file has been created successfully");
			    
		}
		catch (ClassNotFoundException e1)
		{
	       e1.printStackTrace();
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
	
	public static void status_read() throws IOException,FileNotFoundException
	{
		File file=new File(statusFile);
		FileInputStream fileInputStream = new FileInputStream(file);
		HSSFWorkbook workbook1 = new HSSFWorkbook(fileInputStream);
	    HSSFSheet worksheet = workbook1.getSheet("DB_VALIDATION_SR_ID");
		HSSFRow row1 = worksheet.getRow(1);
		HSSFCell cellA1 = row1.getCell(1);
		HSSFCell cellA2 = row1.getCell(0);
		String a1Val = cellA1.getStringCellValue();
		String a1Va2 = cellA2.getStringCellValue();
		String str =a1Va2;
        int len = str.length();
        finalstr = str.substring(0, len);
        y = Integer.parseInt(a1Val);
        fileInputStream.close();
	}

	public static void track_write() throws IOException,FileNotFoundException
	{
		try 
	{
					
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","PGW","pgw");
		con.setAutoCommit(false);
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
		String a = ("select a.id,b.name,a.sr_id,a.time_stamp,a.message from sr_acv_account_audit_tbl a, sr_tracking_point_tbl b  WHERE A.SR_ID ='").concat(finalstr).concat("' AND A.TRACKING_POINT = B.CODE   order by TIME_STAMP desc");
		//System.out.println("Track query executed");
		report.writeTorun(runlog, "Executed Status Query"+System.lineSeparator()+"---------------------"+System.lineSeparator()+a);
		ResultSet rs=stmt.executeQuery(a);
		HSSFWorkbook workbook = new HSSFWorkbook();
	    HSSFSheet sheet = workbook.createSheet("DB_VALIDATION_TRACK");
	    HSSFRow rowhead = sheet.createRow((short) 0);
	    rowhead.createCell(0).setCellValue("ID");
	    rowhead.createCell(1).setCellValue("NAME");
	    rowhead.createCell(2).setCellValue("SR_ID");
	    rowhead.createCell(3).setCellValue("TIME_STAMP");
	    rowhead.createCell(4).setCellValue("MESSAGE");
	    
	    int j = 1;
	    while (rs.next()){
	        HSSFRow row = sheet.createRow((short) j);
	        row.createCell(0).setCellValue(rs.getString(1));
	        row.createCell(1).setCellValue(rs.getString(2));
	        row.createCell(2).setCellValue(rs.getString(3));
	        row.createCell(3).setCellValue(rs.getString(4));
	        row.createCell(4).setCellValue(rs.getString(5));
	        j++;
	    }
	    //trackFile="C:\\Automated_Execution\\Output\\EAI_CHECK\\TRACK.xls";
	    
	    trackFile =EAIpre.concat("\\TRACK.xls");
	    FileOutputStream fileOut1 = new FileOutputStream(trackFile,false);
		workbook.write(fileOut1);
		//workbook.close();
		fileOut1.close();
		//System.out.println("Track File has been created successfully ");
		report.writeTorun(runlog,"Track File has been created successfully");
		} 
		catch (ClassNotFoundException e1)
		{
	       e1.printStackTrace();
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
	
	public static void track_read() throws IOException
	{
		File file1=new File(trackFile);
		FileInputStream fileInputStream1 = new FileInputStream(file1);
		HSSFWorkbook workbook2 = new HSSFWorkbook(fileInputStream1);
	    HSSFSheet worksheet2 = workbook2.getSheet("DB_VALIDATION_TRACK");
		HSSFRow row2 = worksheet2.getRow(1);
		HSSFCell cllA1 = row2.getCell( 1);
		String a2Val = cllA1.getStringCellValue();
		a=a2Val;
		fileInputStream1.close();
	}
}
