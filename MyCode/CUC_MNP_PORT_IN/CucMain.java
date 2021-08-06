package CUC_MNP_PORT_IN;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;







import com.google.common.collect.Table.Cell;

public class CucMain 
{

	/*public static String pat;
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
	*/
	
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, SQLException 
	{
		
		// TODO Auto-generated method stub
		String pat;
		File Dataprecheck;
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
		String MSISDN;
		String SIM;
		String IMSI;
		int check=0;
		String device;		// TODO Auto-generated method stub

		int i=0;
		


		Date date = null;	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String output = null;
		String outputpath = null;

		File xlFile = null;
		FileInputStream inputStream = null;
		// Getting the number of iterations
		xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
		inputStream = new FileInputStream(xlFile);

		HSSFWorkbook testWorkBook1 = new HSSFWorkbook(inputStream);
		HSSFSheet testSheet1 = testWorkBook1.getSheet("Iterations");

		HSSFRow row1 = testSheet1.getRow(0);
		String it = row1.getCell(1).getStringCellValue();
		iterations = Integer.parseInt(it);
		inputStream.close();
		
		//Report file and Run log calling

		CommonUtilitiesWriter report=new CommonUtilitiesWriter();



		//Date time stamp file creation
		date = new Date();
		output = dateFormat.format(date);
		outputpath="C:\\Automated_Execution\\Output\\CUC\\".concat(output);

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


			xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC.xls");
			inputStream = new FileInputStream(xlFile);

			cellId=2+i-1;

			HSSFSheet testSheet3 = testWorkBook1.getSheet("Profile_Creation");
			//String xUsage = testSheet3.getRow(1).getCell(cellId).getStringCellValue();
			
			HSSFSheet testSheet4 = testWorkBook1.getSheet("Activation");
			//HSSFCell c=testSheet4.getRow(1).getCell(cellId);
			String atciteration=testSheet4.getRow(1).getCell(cellId).getStringCellValue();

			HSSFSheet testSheet5=testWorkBook1.getSheet("Activation");
			//HSSFRow row2 = testSheet5.getRow(10);
			device=testSheet4.getRow(10).getCell(cellId).getStringCellValue();
			
			// Close Input Excel Workbook
			inputStream.close();



			report.writeTorun(runlog, " Data Set #" + i);
			System.out.println("\t DataSet # "+i);
			report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\t\tData Set #" +i+"" +System.lineSeparator()+"------------------------------------------");

			try {
				row=6;
				SIM = testSheet4.getRow(row).getCell(cellId).getStringCellValue();
				IMSI = testSheet4.getRow(++row).getCell(cellId).getStringCellValue();
				MSISDN = testSheet4.getRow(++row).getCell(cellId).getStringCellValue();
				if(SIM.equalsIgnoreCase("") || IMSI.equalsIgnoreCase("") || MSISDN.equalsIgnoreCase("")){
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
			report.writeToFile(reportfile, "SIM	- "+SIM+ System.lineSeparator()+"IMSI	- "+SIM+System.lineSeparator()+"MSISDN	- "+MSISDN+System.lineSeparator());
			report.writeTorun(runlog, "Data used: "+System.lineSeparator()+"----------");
			report.writeTorun(runlog, "SIM	- "+SIM+System.lineSeparator()+"IMSI	- "+IMSI+System.lineSeparator()+"MSISDN	- "+MSISDN+System.lineSeparator());

			//Data pre-Check

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
				DataClean_Main dclean=new DataClean_Main( MSISDN, SIM, IMSI, runlog, reportfile, datapre, report);
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
			// Creating object for Services Health Check
			check=0;
			ConsumerHealthCheck servicesHealthCheckObj = new ConsumerHealthCheck(runlog,reportfile,report);
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
			}


			//Profile creation

			/*check=0;
			CorporateProfileCreation prof=new CorporateProfileCreation(runlog,reportfile,Postpre,report);
			check=prof.createProfile(cellId);

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
			CucActivationEx acti=new CucActivationEx( runlog, reportfile, Actipre, report);
			check=acti.CUCAct(cellId,device);
			
			if(check==0)
			{


				report.writeToFile(reportfile, report.getDateTime()+" Initiate Activation from Siebel - Pass");
				EAICheck dbvaild= new EAICheck( runlog, reportfile, EAIpre, report);
				check=dbvaild.consumer(MSISDN);

				if(i==6)
				{
					report.writeToFile(reportfile, report.getDateTime()+" EAI DB Check - Pass");
					ConsumerLogCheck log=new ConsumerLogCheck(runlog,reportfile,Actipre,report);
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

			Post_ValidationMain post=new Post_ValidationMain(MSISDN,SIM,IMSI,runlog,reportfile,Postpre,Actipre,report);
			check=post.postMain();

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











/*report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tServices Health check"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tServices Health check"+System.lineSeparator()+"###############################################");

				CUCHealthCheck hlthchk=new CUCHealthCheck();
				hlthchk.execute();

				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tServices Health check Completed"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tServices Health check Completed"+System.lineSeparator()+"###############################################");


				//Profile Creation

			report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t Corporate Profile Creation"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tCorporate Profile Creation"+System.lineSeparator()+"###############################################");
				if(xUsage.equalsIgnoreCase("Skip"))
				{

					report.writeTorun(runlog, "Profile Creation Skipped!");
					report.writeToFile(reportfile, "Profile Creation Skipped!");

				}
				else
				{
					CorporateProfileCreation prof = new CorporateProfileCreation();
					System.out.println("Calling Profile Creation Module!");
					report.writeTorun(runlog, "Calling Profile Creation Module!");
					prof.createProfile();
				}

				Thread.sleep(5000);
				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t Corporate Profile Creation Completed"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tCorporate Profile Creation completed"+System.lineSeparator()+"###############################################");


				//Calling profile validation module

				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t Corporate Profile validation"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tCorporate validation Creation"+System.lineSeparator()+"###############################################");

				if(xUsage.equalsIgnoreCase("Skip"))
				{

					report.writeToFile(reportfile, "Profile Validation skipped");
					report.writeTorun(runlog, "Profile Validation skipped");
				}
				else
				{
				PostProfileValidation_MDM profvalid= new PostProfileValidation_MDM();
				profvalid.ProfileValidation_MDM();				
				}

				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t Corporate Profile validation Completed"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tCorporate Profile validation completed"+System.lineSeparator()+"###############################################");



				//Calling Activation module

				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t Activation Module"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tActivation Module"+System.lineSeparator()+"###############################################");

				if(atciteration.isEmpty())
				{
					report.writeTorun(runlog, "Activation skipped ");
					System.out.println("Activation skipped ");
				}
				else
				{
					report.writeTorun(runlog, "Activation Started");
					System.out.println("Activation Started");
				CucActivationEx Acti= new CucActivationEx();
				Acti.CUCAct();

				Thread.sleep(5000);

				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t CUC Activation completed"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tCUC Activation completed"+System.lineSeparator()+"###############################################");
			 }



			  	//EAI Check

				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t Calling EAI check Module"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tCalling EAI check Module"+System.lineSeparator()+"###############################################");

				System.out.println("Calling EAI DB check Module!");

				CUCActivation cp=new CUCActivation();
				cp.CorporateActi();
				Thread.sleep(5000);


				report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\t EAI Check completed"+System.lineSeparator()+"###############################################");
				report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tEAI Check completed"+System.lineSeparator()+"###############################################");
 */							



/*catch(Exception e)
			{
				//writer.println(e.getStackTrace());
				report.writeTorun(runlog, "");
			}
			finally
			{
				try {

					report.writeTorun(runlog, "***********************************************************************************************");
					report.writeTorun(runlog,"\t Execution of the iteration Stopped!");
					report.writeTorun(runlog, "***********************************************************************************************");


					report.writeToFile(reportfile, "***********************************************************************************************");
					report.writeToFile(reportfile,"\t Execution of the iteration Stopped!");
					report.writeToFile(reportfile, "***********************************************************************************************");


					Thread.sleep(10000);
				}
				catch (Exception ex) {ignore}
			}
		}
	}
}*/