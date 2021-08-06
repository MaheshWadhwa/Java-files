package ConsumerRaqiActivation;

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


public class DataCheckInMedAuc 
{
	static Statement stmt = null;
	static String dataCheckFolderPath = "";
	static String dataCheckInMedAucExcelFile = "Data_Check_In_MED_AUC_DATA.xls";
	static String dataCheckInMedAucExcelPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	public DataCheckInMedAuc(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		DataCheckInMedAuc.dataCheckFolderPath = dataCheckFolderPath;
		DataCheckInMedAuc.dataCheckInMedAucExcelPath = dataCheckFolderPath.concat("\\").concat(dataCheckInMedAucExcelFile);
		DataCheckInMedAuc.reportFile = reportFile;
		DataCheckInMedAuc.datasetLogFile = datasetLogFile;
		DataCheckInMedAuc.utilities = utilities;
	} // end of constructor method

	public int execute(String Imsi1, String Imsi2, String Imsi3) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in MED_AUC_DATA table"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		connectionToDB();
		conditionCounter = 0;
		status(Imsi1,"IMSI1");
		status(Imsi2,"IMSI2");
		status(Imsi3,"IMSI3");
		return conditionCounter;
	} // end of execute method

	public static void connectionToDB() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.186:1522:emadev","CDFDBA","cdfdba");
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		utilities.writeToFile(datasetLogFile, "DB Connection established...");
	} // end of connectionToDB method

	public static void status(String Imsi, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try
		{
			@SuppressWarnings("unused")
			int checkCounter = 0;
			// Setting the query
			String query=("select * from MED_AUC_DATA WHERE IMSI = 'IMSI,").concat(Imsi).concat("'");

			// Executing the query
			ResultSet rs=stmt.executeQuery(query);
			utilities.writeToFile(datasetLogFile, "Executed MED_AUC_DATA check Query for "+type+System.lineSeparator()+"-------------------------------------------"+System.lineSeparator()+query);

			// Assigning result set to workbook
			FileInputStream fileIn = null;
			HSSFWorkbook wb = null;
			int tempCtr = 0;
			try{
				fileIn = new FileInputStream(dataCheckInMedAucExcelPath);
				wb=new HSSFWorkbook(fileIn);
				tempCtr++;
			} catch(FileNotFoundException e){
				wb=new HSSFWorkbook();
			}
			
			HSSFSheet sht=wb.createSheet("MED_AUC_DATA_"+type);
			HSSFRow rowhead=sht.createRow(0);
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
			if(tempCtr>0){
				fileIn.close();
			}

			FileOutputStream fileOut = new FileOutputStream(dataCheckInMedAucExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){				
				if(rs.getString(6).equalsIgnoreCase("0")){
					utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MED_AUC_DATA check = Pass"+System.lineSeparator()+"---------------------");
					utilities.writeToFile(datasetLogFile, "Counter - "+conditionCounter);
				} else if(rs.getString(6).equalsIgnoreCase("1")){
					checkCounter++;
					conditionCounter++;
					utilities.writeToFile(datasetLogFile, "Counter Else - "+conditionCounter);
					utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MED_AUC_DATA check = Fail"+System.lineSeparator()+"---------------------");
				} // end Else If checking for Selected
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MED_AUC_DATA check = Fail"+System.lineSeparator()+"---------------------");
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
}
