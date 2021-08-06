package PostPaidRevamp_IUC_WithCashBack;

import java.io.File;
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



public class DataCheckInCC 
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
	
	/*static Statement stmt = null;
	//static String dataCheckFolderPath = "";
	//static String dataCheckInCCExcelFile = "Data_Check_In_CC.xls";
	//static String dataCheckInCCExcelPath = "";
	//static String reportFile = "";
	//static String datasetLogFile = "";
	//static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;
	static String CC_path=ConsumerActivationMain.datapre;
	
	
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;
	
	static int row;
	static String MSISDN;
	static String SIM;
	static int cellId=ConsumerActivationMain.cellId;
	*/
	
	public DataCheckInCC(String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{
		DataCheckInCC.runlog=runlog;
		DataCheckInCC.reportfile=reportfile;
		DataCheckInCC.datapre=datapre;
		DataCheckInCC.report=report;
	
	}
	
	public int execute(String MSISDN,String SIM) throws ClassNotFoundException, FileNotFoundException, SQLException, InterruptedException, IOException
	{
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Credit Control"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//data_read();
		connectionToDB();
		conditionCounter = 0;
		report.writeTorun(runlog, "Counter Execute - "+conditionCounter);
		checkCC(MSISDN,SIM);		
		return conditionCounter;
	}

	public void connectionToDB() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.160:1521:eaitest","SITCROP","QaSit$52");
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		report.writeTorun(runlog, "DB Connection established...");
	}

	public void checkCC(String MSISDN,String SIM) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			// Setting the query
			String msisdn1=("select a.CREATED_DATE,a.status_id,a.reason_id,a.MSISDN,a.FIRSTNAME,a.IDNUMBER,a.PACKAGE_NAME,a.PLAN_TYPE from CC_customer_profile_tbl a where msisdn in '").concat(MSISDN).concat("'");

			// Executing the query
			ResultSet rs=stmt.executeQuery(msisdn1);
			report.writeTorun(runlog, "Executed CC Query"+System.lineSeparator()+"----------------"+System.lineSeparator()+msisdn1);

			// Assigning the result set to workbook
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("Credit_Control_Query");
			HSSFRow rowhead=sht.createRow(0);
			rowhead.createCell(0).setCellValue("CREATED_DATE");
			rowhead.createCell(1).setCellValue("STATUS_ID");
			rowhead.createCell(2).setCellValue("REASON_ID");
			rowhead.createCell(3).setCellValue("MSISDN");
			rowhead.createCell(4).setCellValue("FIRSTNAME");
			rowhead.createCell(5).setCellValue("IDNUMBER");
			rowhead.createCell(6).setCellValue("PACKAGE_NAME");
			rowhead.createCell(7).setCellValue("PLAN_TYPE");
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
				i++;
			}

			String File = datapre.concat("\\CC_Data_Precheck.xls");
		    FileOutputStream fileOut = new FileOutputStream(File,false);
		    
			wb.write(fileOut);
			//wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				conditionCounter++;
				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"Credit Control Check = Fail"+System.lineSeparator()+"---------------------");
			} else {
				report.writeTorun(runlog, "---------------------"+System.lineSeparator()+"Credit Control Check = Pass"+System.lineSeparator()+"---------------------");
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
	} // end of checkCC method
	
	/*public static void data_read() throws IOException
	{
		row=2;
		
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_Consumer.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet testSheet1 = testdata.getSheet("Activation");
	    SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
	    MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		report.writeTorun(runlog, "Data read successfully");
	    
	}
*/

} // End of Class