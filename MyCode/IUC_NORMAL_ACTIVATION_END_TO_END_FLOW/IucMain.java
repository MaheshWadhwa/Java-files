package IUC_NORMAL_ACTIVATION_END_TO_END_FLOW;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Data_Clean.DataCleanlinessCheck;
//import PostActivation_Validation.PostActivationCheckInBilling;
import Utilities.CommonUtilitiesWriterOLD;

public class IucMain {

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException {

		// Variable Declaration
		String inputFile = "C:\\Automated_Execution\\Input\\TestData_IUC.xlsx";
		String outputFolderPath = "C:\\Automated_Execution\\Output\\IUC";
		String executionFolderName = null;
		String executionFolderPath = null;
		String datasetFolderName = null;
		String datasetFolderPath = null;
		String reportFile = null;
		String datasetLogFile = null;
		String xMsisdn = null;
		String xSim = null;
		String xImsi = null;

		int iterations = 0;
		int cellId = 0;
		int row = 0;
		int check = 0;

		File xlFile = null;
		FileInputStream inputStream = null;
		Workbook testWorkBook = null;
		Sheet testSheet = null;

		CommonUtilitiesWriterOLD utilities = new CommonUtilitiesWriterOLD(); 

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

			// Creating Dataset directory
			datasetFolderName = "Dataset_".concat(Integer.toString(i));
			datasetFolderPath = executionFolderPath.concat("\\").concat(datasetFolderName);
			utilities.createFolder(executionFolderPath.concat("\\"), datasetFolderName);

			// Assigning the Dataset Log File
			datasetLogFile = datasetFolderPath.concat("\\").concat(datasetFolderName).concat("_LogFile.txt");

			// Printing to reportFile and dataset log file
//			System.out.println(datasetFolderName);
//			System.out.println("----------");
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
			row = 7;
			cellId = 2+i-1;
			try {
				xSim = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xImsi = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
				xMsisdn = testSheet.getRow(row++).getCell(cellId).getStringCellValue();
			} catch (NullPointerException e){
				utilities.writeToFile(reportFile, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
				utilities.writeToFile(datasetLogFile, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
				continue;
			} 

			// Close Input Excel Workbook
			testWorkBook.close();

			// Printing to reportFile and dataset log file
			utilities.writeToFile(reportFile, "Data used: "+System.lineSeparator()+"----------");
			utilities.writeToFile(reportFile, "SIM	- "+xSim+System.lineSeparator()+"IMSI	- "+xImsi+System.lineSeparator()+"MSISDN	- "+xMsisdn+System.lineSeparator());
			utilities.writeToFile(datasetLogFile, "Data used: "+System.lineSeparator()+"----------");
			utilities.writeToFile(datasetLogFile, "SIM	- "+xSim+System.lineSeparator()+"IMSI	- "+xImsi+System.lineSeparator()+"MSISDN	- "+xMsisdn+System.lineSeparator());
/*			System.out.println("Data used:");
			System.out.println("----------");
			System.out.println("SIM    - "+xSim);
			System.out.println("IMSI   - "+xImsi);
			System.out.println("MSISDN -"+xMsisdn);*/
			// Creating object for DataCleanlinessCheck
			DataCleanlinessCheck dataCheckObj = new DataCleanlinessCheck(xSim,xImsi,xMsisdn,reportFile,datasetLogFile,datasetFolderPath,utilities);
			// Invoke dataCheck method
			check = dataCheckObj.dataCheck();
			
			if(check > 0){
				utilities.writeToFile(reportFile, "Data Cleanliness Check - Fail");
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
				continue;
			} else {
				utilities.writeToFile(reportFile, "Services Health Check - Pass");
			}

			// Creating object for Creating Profile
			IucProfileCreation iucProfileCreationObj = new IucProfileCreation(reportFile,datasetLogFile,datasetFolderPath,utilities);
			//Invoke createProfile method
			check = iucProfileCreationObj.createProfile(cellId);

			if(check==101){
				utilities.writeToFile(reportFile, "Profile Creation Skipped...");
			} else if(check == 0){
				utilities.writeToFile(reportFile, "Profile Validation in Siebel - Pass");
			} else {
				utilities.writeToFile(reportFile, "Profile Validation in Siebel - Fail");
			}
			Thread.sleep(2000);

			// Creating object for Initiating Activation
			IucActivation iucActivationObj = new IucActivation(reportFile,datasetLogFile,datasetFolderPath,utilities);
			// Invoke iucActivate method
			check = iucActivationObj.iucActivate(cellId);

/*			check = 6;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet1");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("ORDER_ID");
			HSSFRow row1 = sheet.createRow((short) 1);
			row1.createCell(0).setCellValue("1-18990757115");

			utilities.createFolder(datasetFolderPath, "\\Activation_From_Siebel");
			String File = datasetFolderPath.concat("\\").concat("Activation_From_Siebel\\").concat("DB_VALIDATION_IUC.xls");
			FileOutputStream fileOut = new FileOutputStream(File, false);
			workbook.write(fileOut);
			workbook.close();
*/
			if(check == 0){
				utilities.writeToFile(reportFile, "Initiate Activation from Siebel - Pass");
				// Creating object for EAI DB Check
				IucEaiDbCheck iucEaiDbCheckObj = new IucEaiDbCheck(reportFile,datasetLogFile,datasetFolderPath,datasetFolderName,utilities);
				// Invoke iucDBCheck method
				check = iucEaiDbCheckObj.iucDBCheck();
				
				if(check == 6){
					utilities.writeToFile(reportFile, "EAI DB Check - Pass");

					/*
					 * 
					 * Temporary Check
					HSSFSheet sheet = workbook.createSheet("Sheet1");
					sheet.createRow(0).createCell(0).setCellValue("SR_ID");
					sheet.createRow(1).createCell(0).setCellValue("SR_JD1127313941");
					utilities.createFolder(datasetFolderPath, "\\IUC_DB_Check");

					FileOutputStream fos = new FileOutputStream(datasetFolderPath.concat("\\IUC_DB_Check\\").concat(datasetFolderName).concat("_Log.xls"));
					workbook.write(fos);
					workbook.close();
					fos.close();
					 *
					 *
					 */					
					// Creating object to fetch XML
					IucFetchXml iucFetchXmlObj = new IucFetchXml(inputFile,reportFile,datasetLogFile,datasetFolderPath,datasetFolderName,utilities);
					// Invoke fetchSadadXML method
					iucFetchXmlObj.fetchSadadXML();
					utilities.writeToFile(reportFile, "Fetched XML sent to SADAD..."+System.lineSeparator());
				} else{
					utilities.writeToFile(reportFile, "EAI DB Check - Fail");
					continue;
				}
			} else {
				utilities.writeToFile(reportFile, "Initiate Activation from Siebel - Fail");
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