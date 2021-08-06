package ConsumerRaqiActivation;

import java.io.IOException;
import java.sql.SQLException;


public class DataCleanlinessCheckConRaqi {

	static String Sim1 = "";
	static String Imsi1 = "";
	static String Sim2 = "";
	static String Imsi2 = "";
	static String Sim3 = "";
	static String Imsi3 = "";
	static String Msisdn = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static int conditionCounter = 0;
	static CommonUtilitiesWriter utilities = null;

	// Constructor method initializing all the class variables
	public DataCleanlinessCheckConRaqi(String Sim1, String Imsi1, String Sim2, String Imsi2, String Sim3, String Imsi3, String Msisdn, String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		DataCleanlinessCheckConRaqi.Sim1 = Sim1;
		DataCleanlinessCheckConRaqi.Imsi1 = Imsi1;
		DataCleanlinessCheckConRaqi.Sim2 = Sim2;
		DataCleanlinessCheckConRaqi.Imsi2 = Imsi2;
		DataCleanlinessCheckConRaqi.Sim3 = Sim3;
		DataCleanlinessCheckConRaqi.Imsi3 = Imsi3;
		DataCleanlinessCheckConRaqi.Msisdn = Msisdn;
		DataCleanlinessCheckConRaqi.reportFile = reportFile;
		DataCleanlinessCheckConRaqi.datasetLogFile = datasetLogFile;
		DataCleanlinessCheckConRaqi.datasetFolderPath = datasetFolderPath;
		DataCleanlinessCheckConRaqi.utilities = utilities;
	}

	// This method calls the data cleanliness check in other systems
	public int dataCheck()throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		try
		{
			conditionCounter = 0;
			utilities.writeToFile(reportFile, System.lineSeparator()+"Data Cleanliness Check"+System.lineSeparator()+"----------------------");

			// Creating folder for Data Cleanliness Check
			String dataCheckFolderName = "Data_Cleanliness_Check";
			String dataCheckFolderPath = datasetFolderPath.concat("\\").concat(dataCheckFolderName);
			utilities.createFolder(datasetFolderPath, "\\"+dataCheckFolderName); 

			// Data Cleanliness Check in Siebel DB
			DataCheckInSiebelDB dataCheckInSiebelDBObj = new DataCheckInSiebelDB(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			int check = dataCheckInSiebelDBObj.execute(Msisdn, Sim1, Sim2, Sim3);
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
			check = dataCheckInBillingDBObj.execute(Msisdn, Sim1, Sim2, Sim3);
			Thread.sleep(3000);

			if(check > 0){
				utilities.writeToFile(reportFile, "Cleanliness Check in Billing - Fail");
				conditionCounter++;
			} else {
				utilities.writeToFile(reportFile, "Cleanliness Check in Billing - Pass");
			}

			// Data Cleanliness Check in MED_AUC_DATA
			check = 0;
			DataCheckInMedAuc dataCheckInMedAucObj = new DataCheckInMedAuc(reportFile, datasetLogFile, dataCheckFolderPath, utilities);
			check = dataCheckInMedAucObj.execute(Imsi1, Imsi2, Imsi3);
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
			check = dataCheckInMcrObj.execute(Msisdn,Sim1, Sim2, Sim3);
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
			check = dataCheckInMdmObj.execute(Msisdn,Sim1, Sim2, Sim3);
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

		}
		return conditionCounter;
	}
}