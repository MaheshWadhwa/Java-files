package BusinessRaqiActivation;

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


public class DataCheckInSiebelDB 
{
	static Statement stmt = null;
	static String dataCheckFolderPath = "";
	static String dataCheckInSiebelExcelFile = "Data_Check_In_Siebel.xls";
	static String dataCheckInSiebelExcelPath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	public DataCheckInSiebelDB(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		DataCheckInSiebelDB.dataCheckFolderPath = dataCheckFolderPath;
		DataCheckInSiebelDB.dataCheckInSiebelExcelPath = dataCheckFolderPath.concat("\\").concat(dataCheckInSiebelExcelFile);
		DataCheckInSiebelDB.reportFile = reportFile;
		DataCheckInSiebelDB.datasetLogFile = datasetLogFile;
		DataCheckInSiebelDB.utilities = utilities;
	}

	public int execute(String Msisdn, String Sim1, String Sim2, String Sim3) throws ClassNotFoundException, FileNotFoundException, SQLException, InterruptedException, IOException
	{
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Siebel"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		connectionToDB();
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "Counter Execute - "+conditionCounter);
		msisdn1_query(Msisdn);
		msisdn2_query(Msisdn);
		msisdn3_query(Msisdn);
		msisdn4_query(Msisdn);
		sim1_query(Sim1,"SIM1");
		sim2_query(Sim1,"SIM1");
		sim3_query(Sim1,"SIM1");
		sim4_query(Sim1,"SIM1");
		sim1_query(Sim2,"SIM2");
		sim2_query(Sim2,"SIM2");
		sim3_query(Sim2,"SIM2");
		sim4_query(Sim2,"SIM2");
		sim1_query(Sim3,"SIM3");
		sim2_query(Sim3,"SIM3");
		sim3_query(Sim3,"SIM3");
		sim4_query(Sim3,"SIM3");
		return conditionCounter;
	}

	public void connectionToDB() throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con =DriverManager.getConnection("jdbc:oracle:thin:@10.14.11.209:1521:sblsit","MUZZAMIL","muzzamil");
		con.setAutoCommit(false);
		stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		utilities.writeToFile(datasetLogFile, "DB Connection established...");
	}

	public void msisdn1_query(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter = 0;
			// Setting the query
			String msisdn1=("SELECT A.OPER_STATUS_CD,B.NAME FROM SIEBEL.CX_INVTASSET A,SIEBEL.S_PROD_INT B WHERE A.PROD_ID = B.ROW_ID AND A.ASSET_NUM='").concat(MSISDN).concat("'");

			// Executing the query
			ResultSet rs=stmt.executeQuery(msisdn1);
			utilities.writeToFile(datasetLogFile, "Executed MSISDN Query 1"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn1);

			// Assigning the result set to workbook
			HSSFWorkbook wb=new HSSFWorkbook();
			HSSFSheet sht=wb.createSheet("Msisdn_Query_1");
			HSSFRow rowhead=sht.createRow(0);
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

			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
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
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Pass"+System.lineSeparator()+"---------------------");
			} // end Else checking for empty result set	

			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Fail"+System.lineSeparator()+"---------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 1 = Pass"+System.lineSeparator()+"---------------------");
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
	} // end of msisdn1_query method

	public void msisdn2_query(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter = 0;
			// Setting the query
			String msisdn2=("select a.ROW_ID,b.EECC_MSISDN_NUM,a.CUST_STAT_CD,a.CREATED from siebel.S_ORG_EXT a,siebel.S_ORG_EXT_X b where a.ROW_ID = b.PAR_ROW_ID and b.EECC_MSISDN_NUM='").concat(MSISDN).concat("' order by created desc");

			// Executing the query
			ResultSet rs=stmt.executeQuery(msisdn2);
			utilities.writeToFile(datasetLogFile, "Executed MSISDN Query 2"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn2);

			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet("Msisdn_Query_2");
			HSSFRow rowhead=sht.createRow(0);
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
			fileIn.close();
			
			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(3).equalsIgnoreCase("Closed")) || (rs.getString(3).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Pass"+System.lineSeparator()+"---------------------");
			} // end Else checking for empty result set

			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Fail"+System.lineSeparator()+"---------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 2 = Pass"+System.lineSeparator()+"---------------------");
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
	} // end of msisdn2_query method

	public void msisdn3_query(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter = 0;
			// Setting the query
			String msisdn3=("SELECT A.STATUS_CD,a.created FROM SIEBEL.S_ORDER A,SIEBEL.CX_INVTASSET B WHERE A.X_EECC_EA_MSISDN_ID= B.ROW_ID AND B.ASSET_NUM='").concat(MSISDN).concat("' order by a.created desc");

			// Executing the query
			ResultSet rs=stmt.executeQuery(msisdn3);
			utilities.writeToFile(datasetLogFile, "Executed MSISDN Query 3"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn3);

			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet("Msisdn_Query_3");
			HSSFRow rowhead=sht.createRow(0);
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
			fileIn.close();
			
			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Pass"+System.lineSeparator()+"---------------------");
			} // end Else checking for empty result set
			
			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Fail"+System.lineSeparator()+"---------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 3 = Pass"+System.lineSeparator()+"---------------------");
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
	} // end of msisdn3_query method

	public void msisdn4_query(String MSISDN) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter = 0;
			// Setting the query
			String msisdn4=("SELECT A.STATUS_CD,A.CREATED,A.ROW_ID FROM SIEBEL.S_ORDER_ITEM A,SIEBEL.CX_INVTASSET B WHERE A.ASSET_ID= B.ROW_ID AND B.ASSET_NUM ='").concat(MSISDN).concat("' order by a.created desc");

			// Executing the query
			ResultSet rs=stmt.executeQuery(msisdn4);
			utilities.writeToFile(datasetLogFile, "Executed MSISDN Query 4"+System.lineSeparator()+"-----------------------"+System.lineSeparator()+msisdn4);
			
			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet("Msisdn_Query_4");
			HSSFRow rowhead=sht.createRow(0);
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
			fileIn.close();
			
			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Pass"+System.lineSeparator()+"---------------------");
			} // end Else checking for empty result set
			
			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Fail"+System.lineSeparator()+"---------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "---------------------"+System.lineSeparator()+"MSISDN Query 4 = Pass"+System.lineSeparator()+"---------------------");
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
	} // end of msisdn4_query method

	public void sim1_query(String SIM, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter = 0;
			// Setting the query
			String sim1=("SELECT A.OPER_STATUS_CD,B.NAME FROM SIEBEL.CX_INVTASSET A,SIEBEL.S_PROD_INT B WHERE A.PROD_ID = B.ROW_ID AND A.ASSET_NUM='").concat(SIM).concat("'");

			// Executing the query
			ResultSet rs=stmt.executeQuery(sim1);
			utilities.writeToFile(datasetLogFile, "Executed SIM Query 1"+System.lineSeparator()+"--------------------"+System.lineSeparator()+sim1);

			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet(type+"_Sim_Query_1");
			HSSFRow rowhead=sht.createRow(0);
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
			fileIn.close();
			
			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
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
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 1 = Pass"+System.lineSeparator()+"------------------");
			} // end Else checking for empty result set	

			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 1 = Fail"+System.lineSeparator()+"------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 1 = Pass"+System.lineSeparator()+"------------------");
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
	} // end of sim1_query method

	public void sim2_query(String SIM, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		try 
		{
			int checkCounter = 0;
			// Setting the query
			String sim2=("select c.ASSET_NUM,a.CUST_STAT_CD,a.CREATED from siebel.S_ORG_EXT a,siebel.S_ORG_EXT_X b,siebel.CX_INVTASSET c where a.ROW_ID = b.PAR_ROW_ID and b.EECC_SIMCARD_ID= c.ROW_ID and c.ASSET_NUM = '").concat(SIM).concat("' order by a.created desc");

			// Executing the query
			ResultSet rs=stmt.executeQuery(sim2);
			utilities.writeToFile(datasetLogFile, "Executed SIM Query 2"+System.lineSeparator()+"--------------------"+System.lineSeparator()+sim2);

			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet(type+"_Sim_Query_2");
			HSSFRow rowhead=sht.createRow(0);
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
			fileIn.close();
			
			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(2).equalsIgnoreCase("Closed")) || (rs.getString(2).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 2 = Pass"+System.lineSeparator()+"------------------");
			} // end Else checking for empty result set
			
			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 2 = Fail"+System.lineSeparator()+"------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 2 = Pass"+System.lineSeparator()+"------------------");
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

	} // end of sim2_query method

	public void sim3_query(String SIM, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		int checkCounter = 0;
		try 
		{
			// Setting the query
			String sim3=("SELECT A.STATUS_CD,a.CREATED FROM SIEBEL.S_ORDER A,SIEBEL.CX_INVTASSET B WHERE A.X_EECC_EA_SIMCARD_ID= B.ROW_ID AND B.ASSET_NUM='").concat(SIM).concat("' order by a.created desc");

			// Executing the query
			ResultSet rs=stmt.executeQuery(sim3);
			utilities.writeToFile(datasetLogFile, "Executed SIM Query 3"+System.lineSeparator()+"--------------------"+System.lineSeparator()+sim3);

			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet(type+"_Sim_Query_3");
			HSSFRow rowhead=sht.createRow(0);
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
			fileIn.close();
			
			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 3 = Pass"+System.lineSeparator()+"------------------");
			} // end Else checking for empty result set
			
			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 3 = Fail"+System.lineSeparator()+"------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 3 = Pass"+System.lineSeparator()+"------------------");
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
	} // end of sim3_query method

	public void sim4_query(String SIM, String type) throws ClassNotFoundException, SQLException, InterruptedException,IOException,FileNotFoundException
	{
		int checkCounter = 0;
		try 
		{
			// Setting the query
			String sim4=("SELECT a.STATUS_CD,a.CREATED,a.ROW_ID FROM SIEBEL.S_ORDER_ITEM a,siebel.CX_INVTASSET b WHERE a.ASSET_ID= b.ROW_ID  and b.ASSET_NUM ='").concat(SIM).concat("' order by a.created desc");

			// Executing the query
			ResultSet rs=stmt.executeQuery(sim4);
			utilities.writeToFile(datasetLogFile, "Executed SIM Query 4"+System.lineSeparator()+"--------------------"+System.lineSeparator()+sim4);

			// Assigning the result set to workbook
			FileInputStream fileIn = new FileInputStream(dataCheckInSiebelExcelPath);
			HSSFWorkbook wb=new HSSFWorkbook(fileIn);
			HSSFSheet sht=wb.createSheet(type+"_Sim_Query_4");
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
			fileIn.close();

			FileOutputStream fileOut = new FileOutputStream(dataCheckInSiebelExcelPath);
			wb.write(fileOut);
			wb.close();
			fileOut.close();

			// Resetting the pointer of result set before first row
			rs.beforeFirst();

			// Checking the condition for each available row
			if(rs.next()){
				do {
					if((rs.getString(1).equalsIgnoreCase("Closed")) || (rs.getString(1).equalsIgnoreCase("Cancelled"))){
						// TBD
					} else{
						checkCounter++;
						conditionCounter++;
					} // end Else checking for any status other than Closed or Cancelled
				} while(rs.next());
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 4 = Pass"+System.lineSeparator()+"------------------");
			} // end Else checking for empty result set
			
			if(checkCounter > 0){
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 4 = Fail"+System.lineSeparator()+"------------------");
			} else {
				utilities.writeToFile(datasetLogFile, "------------------"+System.lineSeparator()+"SIM Query 4 = Pass"+System.lineSeparator()+"------------------");
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
	} // end of sim4_query method
} // End of Class