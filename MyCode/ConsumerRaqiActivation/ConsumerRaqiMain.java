package ConsumerRaqiActivation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ConsumerRaqiMain {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {

		// Variable Declaration
		String inputFile = "C:\\Automated_Execution\\Input\\TestData_Consumer_Raqi.xlsx";
		String outputFolderPath = "C:\\Automated_Execution\\Output\\Consumer_Raqi";
		String executionFolderName = null;
		String executionFolderPath = null;
		String datasetFolderName = null;
		String datasetFolderPath = null;
		String reportFile = null;
		String datasetLogFile = null;
		String xMsisdn = null;
		String xSim1 = null;
		String xImsi1 = null;
		String xSim2 = null;
		String xImsi2 = null;
		String xSim3 = null;
		String xImsi3 = null;

		int iterations = 0;
		int cellId = 0;
		int row = 0;
		int check = 0;

		File xlFile = null;
		FileInputStream inputStream = null;
		Workbook testWorkBook = null;
		Sheet testSheet = null;

		CommonUtilitiesWriter utilities = new CommonUtilitiesWriter(); 

		// Open Input Excel Workbook
		xlFile = new File(inputFile);
		inputStream = new FileInputStream(xlFile);
		testWorkBook = new XSSFWorkbook(inputStream);

		// Get Number of Iterations
		testSheet = testWorkBook.getSheet("Iterations");
		iterations = (int)testSheet.getRow(0).getCell(1).getNumericCellValue();

		// Close Input Excel Workbook
		testWorkBook.close();

		// Creating output directory
		executionFolderName = utilities.getDateTime();
		executionFolderPath = outputFolderPath.concat("\\").concat(executionFolderName);
		utilities.createFolder(outputFolderPath.concat("\\"), executionFolderName);

		// Assigning the Report File
		reportFile = executionFolderPath.concat("\\").concat("Execution Report.txt");

		// Looping for given number of Datasets
		for(int i=1; i<=iterations; i++){
			Thread.sleep(3000);
			// Creating Dataset directory
			datasetFolderName = "Dataset_".concat(Integer.toString(i));
			datasetFolderPath = executionFolderPath.concat("\\").concat(datasetFolderName);
			utilities.createFolder(executionFolderPath.concat("\\"), datasetFolderName);

			// Assigning the Dataset Log File
			datasetLogFile = datasetFolderPath.concat("\\").concat(datasetFolderName).concat("_LogFile.txt");

			// Printing to reportFile and dataset log file
			utilities.writeToFile(reportFile, "***********************************************************************************************");
			utilities.writeToFile(reportFile, "\t\t\t\t\t"+datasetFolderName);
			utilities.writeToFile(reportFile, "***********************************************************************************************");
			utilities.writeToFile(datasetLogFile, "***********************************************************************************************");
			utilities.writeToFile(datasetLogFile, utilities.getDateTime()+"\t"+datasetFolderName);
			utilities.writeToFile(datasetLogFile, "***********************************************************************************************");

			// Open Input Excel Workbook
			xlFile = new File(inputFile);
			inputStream = new FileInputStream(xlFile);
			testWorkBook = new XSSFWorkbook(inputStream);

			// Getting MSISDN, SIM, IMSI of the current dataset
			testSheet = testWorkBook.getSheet("Activation");
			row = 2;
			cellId = 2+i-1;
			try {
				xMsisdn = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xSim1 = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xImsi1 = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xSim2 = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xImsi2 = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xSim3 = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xImsi3 = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
			} catch (NullPointerException e){
				utilities.writeToFile(reportFile, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
				utilities.writeToFile(datasetLogFile, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
				continue;
			} 

			// Close Input Excel Workbook
			testWorkBook.close();

			// Printing to reportFile and dataset log file
			utilities.writeToFile(reportFile, "Data used: "+System.lineSeparator()+"----------");
			utilities.writeToFile(reportFile, "MSISDN	- "+xMsisdn);
			utilities.writeToFile(reportFile, "SIM  1	- "+xSim1);
			utilities.writeToFile(reportFile, "IMSI 1	- "+xImsi1);
			utilities.writeToFile(reportFile, "SIM  2	- "+xSim2);
			utilities.writeToFile(reportFile, "IMSI 2	- "+xImsi2);
			utilities.writeToFile(reportFile, "SIM  3	- "+xSim3);
			utilities.writeToFile(reportFile, "IMSI 3	- "+xImsi3);

			utilities.writeToFile(datasetLogFile, "Data used: "+System.lineSeparator()+"----------");
			utilities.writeToFile(datasetLogFile, "MSISDN	- "+xMsisdn);
			utilities.writeToFile(datasetLogFile, "SIM  1	- "+xSim1);
			utilities.writeToFile(datasetLogFile, "IMSI 1	- "+xImsi1);
			utilities.writeToFile(datasetLogFile, "SIM  2	- "+xSim2);
			utilities.writeToFile(datasetLogFile, "IMSI 2	- "+xImsi2);
			utilities.writeToFile(datasetLogFile, "SIM  3	- "+xSim3);
			utilities.writeToFile(datasetLogFile, "IMSI 3	- "+xImsi3);

			// Creating object for DataCleanlinessCheck
			DataCleanlinessCheckConRaqi dataCheckObj = new DataCleanlinessCheckConRaqi(xSim1,xImsi1,xSim2,xImsi2,xSim3,xImsi3,xMsisdn,reportFile,datasetLogFile,datasetFolderPath,utilities);
			// Invoke dataCheck method
			check = dataCheckObj.dataCheck();

			if(check > 0){
				utilities.writeToFile(reportFile, "Data Cleanliness Check - Fail");
				utilities.writeToFile(reportFile, "Skipping this Activation..."+System.lineSeparator());
				continue;
			} else {
				utilities.writeToFile(reportFile, "Data Cleanliness Check - Pass");
			}

			// Creating object for Services Health Check
			ServicesHealthCheck servicesHealthCheckObj = new ServicesHealthCheck(reportFile,datasetLogFile,datasetFolderPath,utilities);
			// Invoke servicesCheck method
			check = servicesHealthCheckObj.servicesCheck();

			if(check > 0){
				utilities.writeToFile(reportFile, "Services Health Check - Fail");
				utilities.writeToFile(reportFile, "Skipping this Activation..."+System.lineSeparator());
				continue;
			} else {
				utilities.writeToFile(reportFile, "Services Health Check - Pass");
			}

			// Creating object for Creating Profile
			ConsumerRaqiProfileCreation profileCreationObj = new ConsumerRaqiProfileCreation(reportFile,datasetLogFile,datasetFolderPath,utilities);
			//Invoke createProfile method
			check = profileCreationObj.createProfile(cellId);

			if(check==101){
				utilities.writeToFile(reportFile, "Profile Creation Skipped...");
			} else if(check == 0){
				utilities.writeToFile(reportFile, "Profile Validation in MDM - Pass");
			} else {
				utilities.writeToFile(reportFile, "Profile Validation in MDM - Fail");
				utilities.writeToFile(reportFile, "Skipping this Activation..."+System.lineSeparator());
				continue;
			}
			Thread.sleep(2000);

			// Creating object for Initiating Activation
			ConsumerRaqiActivation activationObj = new ConsumerRaqiActivation(reportFile,datasetLogFile,datasetFolderPath,utilities);
			// Invoke iucActivate method
			check = activationObj.activation(cellId);

/*						check = 0;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("ORDER_ID");
			HSSFRow row1 = sheet.createRow((short) 1);
			row1.createCell(0).setCellValue("1-19017670202");

			utilities.createFolder(datasetFolderPath, "\\Activation_From_Siebel");
			String File = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_CONSUMER_RAQI.xls");
			FileOutputStream fileOut = new FileOutputStream(File, false);
			workbook.write(fileOut);
			workbook.close();
*/			 
			if(check == 0){
				utilities.writeToFile(reportFile, "Initiate Activation from Siebel - Pass");
				// Creating object for EAI DB Check
				ConsumerRaqiEaiDbCheck eaiDbCheckObj = new ConsumerRaqiEaiDbCheck(reportFile,datasetLogFile,datasetFolderPath,datasetFolderName,utilities);
				// Invoke iucDBCheck method
				check = eaiDbCheckObj.eaiDBCheck();

				if(check == 6){
					utilities.writeToFile(reportFile, "EAI DB Check - Pass");

					/* Temporary Check
					HSSFSheet sheet = workbook.createSheet("Sheet1");
					sheet.createRow(0).createCell(0).setCellValue("SR_ID");
					sheet.createRow(1).createCell(0).setCellValue("SR_JD1127313941");
					utilities.createFolder(datasetFolderPath, "\\IUC_DB_Check");

					FileOutputStream fos = new FileOutputStream(datasetFolderPath.concat("\\IUC_DB_Check\\").concat(datasetFolderName).concat("_Log.xls"));
					workbook.write(fos);
					workbook.close();
					fos.close();
					 */					
					// Creating object to fetch XML
					ConsumerRaqiFetchXml fetchXmlObj = new ConsumerRaqiFetchXml(inputFile,reportFile,datasetLogFile,datasetFolderPath,datasetFolderName,utilities);
					// Invoke fetchSadadXML method
					fetchXmlObj.fetchSadadXML();
					utilities.writeToFile(reportFile, "Fetched XML sent to SADAD..."+System.lineSeparator());
				} else{
					utilities.writeToFile(reportFile, "EAI DB Check - Fail");
					utilities.writeToFile(reportFile, "Skipping the remaining steps in this Data Set..."+System.lineSeparator());
					continue;
				}
			} else {
				utilities.writeToFile(reportFile, "Initiate Activation from Siebel - Fail");
				utilities.writeToFile(reportFile, "Skipping the remaining steps in this Data Set..."+System.lineSeparator());
				continue;
			} 
			/*
			PostActivationCheckInBilling postCheckBillingObj = new PostActivationCheckInBilling(inputFile,reportFile,datasetLogFile,datasetFolderPath,datasetFolderName,utilities);
			postCheckBillingObj.billingPostCheck(xMsisdn, xSim);
			 */
		}
		Thread.sleep(5000);
	}
}