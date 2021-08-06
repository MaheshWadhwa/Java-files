package CUC_MNP_PORT_IN;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



public class DataCheckInNetwork

{
	static Statement stmt = null;
	static String dataCheckFolderPath = "";
	static String dataCheckInNetworkFile = "Data_Check_In_Network.txt";
	static String dataCheckInNetworkFilePath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	//static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;
	
	static BufferedReader inFromServer = null;
	static BufferedWriter outToServer = null;

	static Socket soc = null;
	static String message = null;
	
	//static int conditionCounter = 0;
	//static String Net_path=ConsumerActivationMain.datapre;
	
	
	static int row=2;
	//static int cellId
	//static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String datapre;
	//static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static String IMSI;
	static CommonUtilitiesWriter report;
	
/*	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;
	
	static int row;
	static String MSISDN;
	static String SIM;
	static String IMSI;
	static int cellId=ConsumerActivationMain.cellId;

*/	
	
	public DataCheckInNetwork (String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{
		DataCheckInNetwork.runlog=runlog;
		DataCheckInNetwork.reportfile=reportfile;
		DataCheckInNetwork.datapre=datapre;
		DataCheckInNetwork.report=report;
	}
	public int execute(String MSISDN,String SIM,String IMSI) throws ClassNotFoundException, FileNotFoundException, SQLException, InterruptedException, IOException
	{
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Network"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//data_read();
		connectionToNetwork();
		conditionCounter = 0;
		networkCheck("GET:HLRSUB:MSISDN,",MSISDN,";\n","RESP:14","HLRSUB","MSISDN");
		networkCheck("GET:HSSSUB:MSISDN,",MSISDN,":DATATYPE,0;\n","RESP:93001","HSSSUB","MSISDN");
		networkCheck("GET:FNSUB:MSISDN,",MSISDN,";\n","RESP:14","FNSUB","MSISDN");
		networkCheck("GET:ACCOUNTINFORMATION:SubscriberNumber,",MSISDN,";\n","Subscriber number not found","ACCOUNTINFORMATION","MSISDN");
		networkCheck("GET:ACCOUNTDETAILS:SubscriberNumber,",MSISDN,";\n","Subscriber number not found","ACCOUNTDETAILS","MSISDN");
		networkCheck("GET:HLRSUB:IMSI,",IMSI,";\n","RESP:13","HLRSUB","IMSI");
		networkCheck("GET:HSSSUB:IMSI,",IMSI,":DATATYPE,0;\n","RESP:93001","HSSSUB","IMSI");
		outToServer.close();
		inFromServer.close();
		soc.close();
		return conditionCounter;
	} // end of execute method

	public static void connectionToNetwork() throws ClassNotFoundException, SQLException, IOException
	{
		soc = new Socket("10.14.11.186", 4404);
		inFromServer = new BufferedReader(new InputStreamReader(
				soc.getInputStream()));
		outToServer = new BufferedWriter(new OutputStreamWriter(
				soc.getOutputStream()));
		inFromServer.readLine();
		inFromServer.readLine();
		inFromServer.readLine();
		inFromServer.readLine();

		outToServer.write("LOGIN:mds_cai:mds321;");
		outToServer.newLine();
		outToServer.flush();
		message = inFromServer.readLine();
		report.writeTorun(runlog, message);
		report.writeTorun(runlog, "Connection established...");
	} // end of connectionToNetwork method

	public void networkCheck(String preText, String asset, String postText, String resp, String networkElement, String type) 
	{
		try
		{
			outToServer.write(preText+asset+postText);
			outToServer.flush();
			
			message = inFromServer.readLine();
			
			if (message.contains(resp)) {
				report.writeTorun(runlog, type+" is cleaned in "+networkElement+" and ready to use for activation");
			} else {
				conditionCounter++;
				report.writeToFile(runlog, type+" is not cleaned in "+networkElement+" and cannot be used for activation");
			}
			report.writeToFile(runlog, System.lineSeparator()+preText+asset+postText+System.lineSeparator()+message+System.lineSeparator());
			report.writeToFile(runlog, System.lineSeparator()+preText+asset+postText+System.lineSeparator()+message+System.lineSeparator());
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	} // end of networkCheck method
	
	
	/*public static void data_read() throws IOException
	{
		row=2;
		
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_Consumer_MNP.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet testSheet1 = testdata.getSheet("Activation");
	    SIM = testSheet1.getRow(row).getCell(cellId).getStringCellValue();
	    IMSI = testSheet1.getRow(1+row).getCell(cellId).getStringCellValue();
	    MSISDN = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
	   // IMSI = testSheet1.getRow(2+row).getCell(cellId).getStringCellValue();
		report.writeTorun(runlog, "Data read successfully");
	    
	}*/
} // end of class