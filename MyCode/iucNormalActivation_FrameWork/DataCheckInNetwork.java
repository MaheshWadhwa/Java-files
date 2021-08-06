package iucNormalActivation_FrameWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;


public class DataCheckInNetwork 
{
	static Statement stmt = null;
	static String dataCheckFolderPath = "";
	static String dataCheckInNetworkFile = "Data_Check_In_Network.txt";
	static String dataCheckInNetworkFilePath = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;
	
	static BufferedReader inFromServer = null;
	static BufferedWriter outToServer = null;

	static Socket soc = null;
	static String message = null;

	public DataCheckInNetwork(String reportFile, String datasetLogFile, String dataCheckFolderPath, CommonUtilitiesWriter utilities){
		DataCheckInNetwork.dataCheckFolderPath = dataCheckFolderPath;
		DataCheckInNetwork.dataCheckInNetworkFilePath = dataCheckFolderPath.concat("\\").concat(dataCheckInNetworkFile);
		DataCheckInNetwork.reportFile = reportFile;
		DataCheckInNetwork.datasetLogFile = datasetLogFile;
		DataCheckInNetwork.utilities = utilities;
	} // end of constructor method

	public int execute(String Msisdn, String Imsi) throws ClassNotFoundException, FileNotFoundException, SQLException, InterruptedException, IOException
	{
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Check in Network"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		connectionToNetwork();
		conditionCounter = 0;
		networkCheck("GET:HLRSUB:MSISDN,",Msisdn,";\n","RESP:14","HLRSUB","MSISDN");
		networkCheck("GET:HSSSUB:MSISDN,",Msisdn,":DATATYPE,0;\n","RESP:93001","HSSSUB","MSISDN");
		networkCheck("GET:FNSUB:MSISDN,",Msisdn,";\n","RESP:14","FNSUB","MSISDN");
		networkCheck("GET:ACCOUNTINFORMATION:SubscriberNumber,",Msisdn,";\n","Subscriber number not found","ACCOUNTINFORMATION","MSISDN");
		networkCheck("GET:ACCOUNTDETAILS:SubscriberNumber,",Msisdn,";\n","Subscriber number not found","ACCOUNTDETAILS","MSISDN");
		networkCheck("GET:HLRSUB:IMSI,",Imsi,";\n","RESP:13","HLRSUB","IMSI");
		networkCheck("GET:HSSSUB:IMSI,",Imsi,":DATATYPE,0;\n","RESP:93001","HSSSUB","IMSI");
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
		utilities.writeToFile(datasetLogFile, message);
		utilities.writeToFile(datasetLogFile, "Connection established...");
	} // end of connectionToNetwork method

	public void networkCheck(String preText, String asset, String postText, String resp, String networkElement, String type) 
	{
		try
		{
			outToServer.write(preText+asset+postText);
			outToServer.flush();
			
			message = inFromServer.readLine();
			
			if (message.contains(resp)) {
				utilities.writeToFile(dataCheckInNetworkFilePath, type+" is cleaned in "+networkElement+" and ready to use for activation");
			} else {
				conditionCounter++;
				utilities.writeToFile(dataCheckInNetworkFilePath, type+" is not cleaned in "+networkElement+" and cannot be used for activation");
			}
			utilities.writeToFile(datasetLogFile, System.lineSeparator()+preText+asset+postText+System.lineSeparator()+message+System.lineSeparator());
			utilities.writeToFile(dataCheckInNetworkFilePath, System.lineSeparator()+preText+asset+postText+System.lineSeparator()+message+System.lineSeparator());
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
} // end of class