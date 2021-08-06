package CUC_SIM_Swap;

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

import CUC_SIM_Swap.CommonUtilitiesWriter;
import CUC_SIM_Swap.DataClean_Main;


import com.google.common.collect.Table.Cell;
public class SIMSwapMain 

{
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
	static int check = 0;


	public static void main() throws IOException, InterruptedException, ClassNotFoundException, SQLException 

	{

		// TODO Auto-generated method stub

		int i=0;



		Date date = null;	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String output = null;
		String outputpath = null;

		File xlFile = null;
		FileInputStream inputStream = null;
		// Getting the number of iterations
		xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC_SIMSwap.xls");
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
		outputpath="C:\\Automated_Execution\\Output\\SIMSwap\\".concat(output);

		File outputfile=new File (outputpath);
		outputfile.mkdir();

		//Report file creation

		reportfile = outputpath.concat("\\Execution Report.txt");
		//outwriter = new PrintWriter(reportFile);


		for(i=1;i<iterations;i++)
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

			File Activation=new File (data,"SIMSwap");
			Activation.mkdir();
			Actipre=pat.concat("\\NumberSwap");
			//Activation folder to keep order id excel

			File EAI=new File (data,"EAI");
			EAI.mkdir();
			EAIpre=pat.concat("\\EAI");

			//Activation folder to keep order id excel

			File PostValidation=new File (data,"PostValidation");
			PostValidation.mkdirs();
			Postpre=pat.concat("\\PostValidation");		


			xlFile = new File("C:\\Automated_Execution\\Input\\TestData_CUC_NumberSwap.xls");
			inputStream = new FileInputStream(xlFile);

			cellId=2+i-1;

			HSSFSheet testSheet3 = testWorkBook1.getSheet("Profile_Creation");
			String xUsage = testSheet3.getRow(1).getCell(cellId).getStringCellValue();

			HSSFSheet testSheet4 = testWorkBook1.getSheet("Activation");
			//HSSFCell c=testSheet4.getRow(1).getCell(cellId);
			String atciteration=testSheet4.getRow(1).getCell(cellId).getStringCellValue();

			inputStream.close();

			try
			{
				report.writeTorun(runlog, " Data Set #" + i);
				System.out.println("\t DataSet # "+i);
				report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\t\tData Set #" +i+"" +System.lineSeparator()+"------------------------------------------");


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


					DataClean_Main dclean=new DataClean_Main();
					int cleancheck=dclean.dataCleanmain();
					Thread.sleep(5000);
					if(cleancheck>0)
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
				
				
				ServiceHealthCheck  servicesHealthCheckObj= new ServiceHealthCheck();
				//ServicesHealthCheck servicesHealthCheckObj = new ServicesHealthCheck(reportFile,datasetLogFile,datasetFolderPath,utilities);
				// Invoke servicesCheck method
				check = servicesHealthCheckObj.execute();

				if(check > 0){
					report.writeToFile(reportfile,"Services Health Check - Fail");
					report.writeToFile(reportfile, "Skipping this Activation..."+System.lineSeparator());
					continue;
				} else {
					report.writeToFile(reportfile,"Services Health Check - Pass");
				}

				//Number swap module
				
				
				SIMSwap cuc=new SIMSwap();
				int numswapcheck=cuc.simswap();
				if (numswapcheck==0)
				{
					report.writeToFile(reportfile,"SIM swap done successfully");
					report.writeTorun(runlog,"SIM swap done successfully");
				
					//Post validations
					
					report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\tPost Validation\t"  +System.lineSeparator()+"------------------------------------------");
					report.writeTorun(runlog, "\tPost Validation\t");
					
					Post_ValidationMain post=new Post_ValidationMain();
					int postvalid=post.postMain();
					
					if(postvalid>0)
					{
						report.writeToFile(reportfile,"Post validation - Pass");
						report.writeTorun(runlog,"Post validation - Pass");
					}
					
					else
					{
						report.writeToFile(reportfile,"Post validation - Fail");
						report.writeTorun(runlog,"Post validation - Fail");
					
					}
					
					
					report.writeToFile(reportfile, "-----------------------------------------"+System.lineSeparator()+"\tPost validation completed\t"  +System.lineSeparator()+"------------------------------------------");
					report.writeTorun(runlog, "\t\tPost validation Completed\t\t");

					
					
				}
				
				else
				{
					report.writeToFile(reportfile,"SIM swap failed and order is cancelled");
				}
				
				
				}

			catch(Exception e)
			{
				//writer.println(e.getStackTrace());
				report.writeToFile(runlog, "");
			}
			finally
			{
				try {

					report.writeToFile(runlog, "***********************************************************************************************");
					report.writeToFile(runlog,"\t Execution of the iteration Stopped!");
					report.writeToFile(runlog, "***********************************************************************************************");


					report.writeToFile(reportfile, "***********************************************************************************************");
					report.writeToFile(reportfile,"\t Execution of the iteration Stopped!");
					report.writeToFile(reportfile, "***********************************************************************************************");


					Thread.sleep(10000);
				}
				catch (Exception ex) {/*ignore*/}


			}

		}
	}
}