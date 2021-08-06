package iucNormalActivation_FrameWork;

import java.io.IOException;
import java.sql.SQLException;

public class DataCleanlinessCheck {

	static String Sim = "";
	static String Imsi = "";
	static String Msisdn = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static int conditionCounter = 0;
	static CommonUtilitiesWriter utilities = null;

	// Constructor method initializing all the class variables
	public DataCleanlinessCheck(String Sim, String Imsi, String Msisdn, String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		DataCleanlinessCheck.Sim = Sim;
		DataCleanlinessCheck.Imsi = Imsi;
		DataCleanlinessCheck.Msisdn = Msisdn;
		DataCleanlinessCheck.reportFile = reportFile;
		DataCleanlinessCheck.datasetLogFile = datasetLogFile;
		DataCleanlinessCheck.datasetFolderPath = datasetFolderPath;
		DataCleanlinessCheck.utilities = utilities;
	}

	// This method calls the data cleanliness check in other systems
	public int dataCheck()throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		try
		{
			conditionCounter = 0;
			utilities.writeToFile(reportFile, "Data Cleanliness Check"+System.lineSeparator()+utilities.insertDashedLine("Data Cleanliness Check"));

			// Creating folder for Data Cleanliness Check
			String dataCheckFolderName = "Data_Cleanliness_Check";
			String dataCheckFolderPath = datasetFolderPath.concat("\\").concat(dataCheckFolderName);
			utilities.createFolder(datasetFolderPath, "\\"+dataCheckFolderName); 

			// Data Cleanliness Check in Siebel DB
			DataCheckInSiebelDB dataCheckInSiebelDBObj = new DataCheckInSiebelDB(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			int check = dataCheckInSiebelDBObj.execute(Msisdn, Sim);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in Siebel - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in Siebel - Pass");
			}

			// Data Cleanliness Check in Billing DB
			check = 0;
			DataCheckInBillingDB dataCheckInBillingDBObj = new DataCheckInBillingDB(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInBillingDBObj.execute(Msisdn, Sim);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in Billing - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in Billing - Pass");
			}

			// Data Cleanliness Check in Network
			check = 0;
			DataCheckInNetwork dataCheckInNetworkObj = new DataCheckInNetwork(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInNetworkObj.execute(Msisdn, Imsi);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in Network - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in Network - Pass");
			}

			// Data Cleanliness Check in CC
			DataCheckInCC dataCheckInCCObj = new DataCheckInCC(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInCCObj.execute(Msisdn);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in CC - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in CC - Pass");
			}

			// Data Cleanliness Check in MED_AUC_DATA
			check = 0;
			DataCheckInMedAuc dataCheckInMedAucObj = new DataCheckInMedAuc(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInMedAucObj.execute(Imsi);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in MED_AUC_DATA table - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in MED_AUC_DATA table - Pass");
			}

			// Data Cleanliness Check in MCR
			check = 0;
			DataCheckInMcr dataCheckInMcrObj = new DataCheckInMcr(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInMcrObj.execute(Msisdn,Sim);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in MCR - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in MCR - Pass");
			}

			// Data Cleanliness Check in MDM
			check = 0;
			DataCheckInMdm dataCheckInMdmObj = new DataCheckInMdm(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInMdmObj.execute(Msisdn,Sim);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in MDM - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in MDM - Pass");
			}
		}
		catch (IOException e)
		{
			utilities.writeToFile(datasetLogFile, "Encountered IOException. Skipping this iteration.");
			conditionCounter++;
			return conditionCounter;
		}
		return conditionCounter;


	}

}
