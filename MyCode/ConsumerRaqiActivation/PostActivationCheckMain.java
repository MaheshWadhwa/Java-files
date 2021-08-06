package ConsumerRaqiActivation;


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
	public PostActivationCheckMain(String Sim1, String Imsi1, String Sim2, String Imsi2, String Sim3, String Imsi3, String Msisdn, String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		PostActivationCheckMain.Sim1 = Sim1;
		PostActivationCheckMain.Imsi1 = Imsi1;
		PostActivationCheckMain.Sim2 = Sim2;
		PostActivationCheckMain.Imsi2 = Imsi2;
		PostActivationCheckMain.Sim3 = Sim3;
		PostActivationCheckMain.Imsi3 = Imsi3;
		PostActivationCheckMain.Msisdn = Msisdn;
		PostActivationCheckMain.reportFile = reportFile;
		PostActivationCheckMain.datasetLogFile = datasetLogFile;
		PostActivationCheckMain.datasetFolderPath = datasetFolderPath;
		PostActivationCheckMain.utilities = utilities;
	} // End of constructor method
	
	public int postActivationCheck(){
		
//		PostActivationCheckInBilling billingObj = new PostActivationCheckInBilling()
		
		
		return 0;
	}


}
