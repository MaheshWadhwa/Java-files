package iucNormalActivation_FrameWork;

import java.io.IOException;
import java.sql.SQLException;

public class PostActivationCheckMain {
	
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
	static String serviceAccountNumber = "";
	static int conditionCounter = 0;
	static CommonUtilitiesWriter utilities = null;

	// Constructor method initializing all the class variables
	public PostActivationCheckMain(String Sim1, String Imsi1, String Msisdn, String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		PostActivationCheckMain.Sim1 = Sim1;
		PostActivationCheckMain.Imsi1 = Imsi1;
		PostActivationCheckMain.Msisdn = Msisdn;
		PostActivationCheckMain.reportFile = reportFile;
		PostActivationCheckMain.datasetLogFile = datasetLogFile;
		PostActivationCheckMain.datasetFolderPath = datasetFolderPath;
		PostActivationCheckMain.utilities = new CommonUtilitiesWriter(reportFile, datasetLogFile);
	} // End of constructor method
	
	public int postActivationCheck() throws ClassNotFoundException, NullPointerException, SQLException, IOException, InterruptedException{
		
		int check = 0;
		utilities.writeToFile(reportFile, System.lineSeparator()+"Post Activation Validation"+System.lineSeparator()+"--------------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Validation"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		PostActivationCheckInBilling postBillingCheckObj = new PostActivationCheckInBilling(reportFile,datasetLogFile,datasetFolderPath,utilities);
		check = postBillingCheckObj.billingPostCheck(Msisdn);
		
		if(check == 0){
			utilities.writeToFile(reportFile, "Post Activation Check in Billing - Pass");
		}else{
			utilities.writeToFile(reportFile, "Post Activation Check in Billing - Fail");
			conditionCounter++;
		}
		
		PostActivationCheckInSiebel postSiebelCheckObj = new PostActivationCheckInSiebel(reportFile,datasetLogFile,datasetFolderPath,utilities);
		check = postSiebelCheckObj.checkSiebel(Msisdn);
		
		if(check == 0){
			utilities.writeToFile(reportFile, "Post Activation Check in Siebel - Pass");
		}else{
			utilities.writeToFile(reportFile, "Post Activation Check in Siebel - Fail");
			conditionCounter++;
		}
		
		PostActivationCheckInMdm postMdmCheckObj = new PostActivationCheckInMdm(reportFile,datasetLogFile,datasetFolderPath,utilities);
		check = postMdmCheckObj.execute(Msisdn, Sim1);
		
		if(check == 0){
			utilities.writeToFile(reportFile, "Post Activation Check in MDM - Pass");
		}else{
			utilities.writeToFile(reportFile, "Post Activation Check in MDM - Fail");
			conditionCounter++;
		}
		
		PostActivationCheckInMCR postMcrCheckObj = new PostActivationCheckInMCR(reportFile,datasetLogFile,datasetFolderPath,utilities);
		check = postMcrCheckObj.execute(Msisdn, Sim1);
		
		if(check == 0){
			utilities.writeToFile(reportFile, "Post Activation Check in MCR - Pass");
		}else{
			utilities.writeToFile(reportFile, "Post Activation Check in MCR - Fail");
			conditionCounter++;
		}	
		return conditionCounter;
	}
}
