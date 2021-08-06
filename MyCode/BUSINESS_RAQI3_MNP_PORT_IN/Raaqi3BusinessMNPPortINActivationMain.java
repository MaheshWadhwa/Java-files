package BUSINESS_RAQI3_MNP_PORT_IN;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import RAAQI_MNP_PORT_IN.RaaqiMNPPortINProfileCreation;



public class Raaqi3BusinessMNPPortINActivationMain {
		
	public static String pat;
	public static File Dataprecheck=null;
	public static String datapre;
	public static String Actipre;
	public static String EAIpre;
	public static String Postpre;
	public static int cellId;
	public static int row = 0;
	public static int iterations;
	static int ReturnCode;
	static String runlog=null;
	static String reportfile=null;
	static String xSim;
	static String xMsisdn;
	static String xImsi;
	static int check=0;
	static String device;
	static String MSISDN;
	
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException 
	{
		//Variable declaration
		
		/*String pat;
		File Dataprecheck=null;
		String datapre;
		String Actipre;
		String EAIpre;
		String Postpre;
		int cellId;
		int row = 0;
		int iterations;
		int ReturnCode;
		String runlog=null;
		String reportfile=null;
		String xSim=null;
		String xMsisdn=null;
		String xImsi=null;
		int check=0;
*/		
		// TODO Auto-generated method stub

		int i=0;
		Date date = null;	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String output = null;
		String outputpath = null;

		File xlFile = null;
		FileInputStream inputStream = null;
		
		// Getting the number of iterations
		xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Raqi3BusinessMNP.xls");
		inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWorkBook1 = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet1 = testWorkBook1.getSheet("Iterations");

		HSSFRow row1 = testSheet1.getRow(0);
		String it = row1.getCell(1).getStringCellValue();
		iterations = Integer.parseInt(it);
		
		testSheet1=testWorkBook1.getSheet("Activation");
		HSSFRow ro2=testSheet1.getRow(9);
		device= ro2.getCell(2).getStringCellValue();
		inputStream.close();

		//Report file and Run log calling

		CommonUtilitiesWriter report=new CommonUtilitiesWriter();



		//Date time stamp file creation
		date = new Date();
		output = dateFormat.format(date);
		outputpath="C:\\Automated_Execution\\Output\\Raqi3BusinessMNP\\".concat(output);

		File outputfile=new File (outputpath);
		outputfile.mkdir();

		//Report file creation

		reportfile = outputpath.concat("\\Execution Report.txt");
		//outwriter = new PrintWriter(reportFile);


		for(i=1;i<=iterations;i++)
		{

			String str = Integer.toString(i);

			pat=outputpath.concat("\\DataSet_").concat(str);
			File data=new File (pat);
			data.mkdirs();

			runlog=pat.concat("\\Run Log.txt");
			//writer = new PrintWriter(runlog);

			//Data PreCheck folder creation

			Dataprecheck=new File (data,"Dataprecheck");
			Dataprecheck.mkdir();
			datapre=pat.concat("\\Dataprecheck");


			//Activation folder to keep order id excel

			File Activation=new File (data,"Activation");
			Activation.mkdir();
			Actipre=pat.concat("\\Activation");
			//Activation folder to keep order id excel

			File EAI=new File (data,"EAI");
			EAI.mkdir();
			EAIpre=pat.concat("\\EAI");

			//Activation folder to keep order id excel

			File PostValidation=new File (data,"PostValidation");
			PostValidation.mkdirs();
			Postpre=pat.concat("\\PostValidation");		


			xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Raqi3BusinessMNP.xls");
			inputStream = new FileInputStream(xlFile);

			cellId=2+i-1;

			HSSFSheet testSheet3 = testWorkBook1.getSheet("Profile_Creation");
			//String xUsage = testSheet3.getRow(1).getCell(cellId).getStringCellValue();
			
			HSSFSheet testSheet4 = testWorkBook1.getSheet("Activation");
			//HSSFCell c=testSheet4.getRow(1).getCell(cellId);
			String atciteration=testSheet4.getRow(1).getCell(cellId).getStringCellValue();
			

			// Close Input Excel Workbook
			inputStream.close();



			report.writeTorun(runlog, " Data Set #" + i);
			System.out.println("\t DataSet # "+i);
			report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\t\tData Set #" +i+"" +System.lineSeparator()+"------------------------------------------");

			try {
				row=2;
				xSim = testSheet4.getRow(row).getCell(cellId).getStringCellValue();
				xImsi = testSheet4.getRow(++row).getCell(cellId).getStringCellValue();
				xMsisdn = testSheet4.getRow(++row).getCell(cellId).getStringCellValue();
				if(xSim.equalsIgnoreCase("") || xImsi.equalsIgnoreCase("") || xMsisdn.equalsIgnoreCase("")){
					report.writeToFile(reportfile, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
					report.writeTorun(runlog, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
					continue;
				}
			} catch (NullPointerException e){
				report.writeToFile(reportfile, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
				report.writeTorun(runlog, "Test data not found. Hence skipping this dataset iteration."+System.lineSeparator());
				continue;
			}

			// Printing to reportFile and dataset log file
			report.writeToFile(reportfile, "Data used: "+ System.lineSeparator()+"----------");
			report.writeToFile(reportfile, "SIM	- "+xSim+ System.lineSeparator()+"IMSI	- "+xImsi+System.lineSeparator()+"MSISDN	- "+xMsisdn+System.lineSeparator());
			report.writeTorun(runlog, "Data used: "+System.lineSeparator()+"----------");
			report.writeTorun(runlog, "SIM	- "+xSim+System.lineSeparator()+"IMSI	- "+xImsi+System.lineSeparator()+"MSISDN	- "+xMsisdn+System.lineSeparator());

			//Data pre-Check
/*
			report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\tData Pre check\t"  +System.lineSeparator()+"------------------------------------------");
			report.writeTorun(runlog, "\tData Pre check\t");

			if(atciteration.equalsIgnoreCase("Skip"))
			{
				report.writeTorun(runlog, "Data pre check skipped");
				report.writeToFile(reportfile, "Data pre check skipped");
			}
			else
			{
				

				check=0;
				DataClean_Main dclean=new DataClean_Main();   // Commented by JP
				check=dclean.dataCleanmain();
				Thread.sleep(5000);
				if(check>0)
				{
					report.writeToFile(reportfile, report.getDateTime()+"Data Cleanliness Check - Fail");
					report.writeToFile(reportfile, "Skipping this Activation..."+System.lineSeparator());
					continue;
				}

				else
				{
					report.writeToFile(reportfile, report.getDateTime()+"Data Cleanliness Check - Pass");
				}

				report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\tData Pre check completed\t"  +System.lineSeparator()+"------------------------------------------");
				report.writeTorun(runlog, "\t\tData Pre check Completed\t\t");

			}
			
			// Creating object for Services Health Check   -----> Commented by JP
			check=0;    
			RaaqiMNPHealthCheck servicesHealthCheckObj = new RaaqiMNPHealthCheck();
			// Invoke servicesCheck method
			check = servicesHealthCheckObj.execute();

			if(check > 0){
				report.writeToFile(reportfile, report.getDateTime()+"Services Health Check - Fail");
				report.writeToFile(reportfile, "Skipping this Activation..."+System.lineSeparator());
				continue;
			} 
			else
			{
				report.writeToFile(reportfile, report.getDateTime()+"Services Health Check - Pass");
			}*/


/*			//Profile creation

			check=0;
			Raaqi3BusinessMNPPortINProfileCreation prof=new Raaqi3BusinessMNPPortINProfileCreation();
			check=prof.acti();

			if(check==101){
				report.writeToFile(reportfile, "Profile Creation Skipped...");
			} else if(check == 0){
				report.writeToFile(reportfile, report.getDateTime()+" Profile Validation in MDM - Pass");
			} else {
				report.writeToFile(reportfile, report.getDateTime()+" Profile Validation in MDM - Fail");
				report.writeToFile(reportfile, "Skipping this Activation..."+System.lineSeparator());
				continue;
			}
*/

			//Activation

			check=0;
			Raaqi3BusinessMNPPortINActivation acti=new Raaqi3BusinessMNPPortINActivation();
			check=acti.RaqiMNPActivation();
			
			if(check==0)
			{

				report.writeToFile(reportfile, report.getDateTime()+" Initiate Activation from Siebel - Pass");
				EAICheck dbvaild= new EAICheck(runlog,reportfile,EAIpre,report);
				check=dbvaild.consumer(MSISDN);

				if(i==6)
				{
					report.writeToFile(reportfile, report.getDateTime()+" EAI DB Check - Pass");
					RaaqiMNPLogCheck log=new RaaqiMNPLogCheck();
					log.execute();
					report.writeToFile(reportfile, report.getDateTime()+" Fetched XML sent to SADAD..."+System.lineSeparator());
				}
				else
				{
					report.writeToFile(reportfile, report.getDateTime()+" EAI DB Check - Fail");
					continue;
				}
			}
			else 
				
			{
				report.writeToFile(reportfile, report.getDateTime()+" Initiate Activation from Siebel - Fail");
				continue;
			}

			Post_ValidationMain post=new Post_ValidationMain();
			check=post.dataCleanmain();

			if(check > 0)
			{
				report.writeToFile(reportfile, report.getDateTime()+" Post Activation Check - Fail");
			} 
			else 
			{
				report.writeToFile(reportfile, report.getDateTime()+" Post Activation Check - Pass");
			}
			report.writeToFile(reportfile, System.lineSeparator());
		}
		Thread.sleep(5000);
		System.out.println("Program End");
	}
}
