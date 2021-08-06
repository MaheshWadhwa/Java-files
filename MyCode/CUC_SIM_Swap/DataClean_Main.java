package CUC_SIM_Swap;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DataClean_Main {
	
	static String runlog=SIMSwapMain.runlog;
	static String reportfile=SIMSwapMain.reportfile;
	static int conditionCounter = 0;
	static int resultdata;
	
	public int dataCleanmain()throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		
		CommonUtilitiesWriter report=new CommonUtilitiesWriter();
		
		try
		{
			//Siebel
			
			conditionCounter=0;
			resultdata=0;
			report.writeTorun(runlog, "***********Data pre check in SIEBEL started *************");
			DATACLEAN_SIEBEL_ASSET siebel_clean=new DATACLEAN_SIEBEL_ASSET();
			siebel_clean.execute();
			if(resultdata>0)
			{
				report.writeToFile(reportfile,"Data check in Siebel - Fail");
				conditionCounter++;
			}
			else
			{
				report.writeTorun(reportfile,"Data check in Siebel - Pass");
			}
			
			report.writeTorun(runlog, "***********Data pre check in SIEBEL completed ***********");
			Thread.sleep(3000);

			//Billing
			resultdata=0;
			report.writeTorun(runlog, "***********Data pre check in BIlling started *************");
			DATACLEAN_BILLING billing=new DATACLEAN_BILLING();
			billing.billing_execute();
			if(resultdata>0)
			{
				report.writeToFile(reportfile,"Data check in Billing - Fail");
				conditionCounter++;
			
			}
			else
			{
				report.writeToFile(reportfile,"Data check in Billing - Pass");
			}
			report.writeTorun(runlog, "***********Data pre check in Billing completed ***********");
			Thread.sleep(3000);

			
			//EMADEV
			resultdata=0;
			report.writeTorun(runlog, "***********Data pre check in MEDAUC table started *************");
			DATACLEAN_EMADEV emadev=new DATACLEAN_EMADEV();
			emadev.emadev_clean();
			if(resultdata>0)
			{
				report.writeToFile(reportfile,"Data check in MEDAUC table - Fail");
				conditionCounter++;
			}
			else
			{
				report.writeToFile(reportfile,"Data check in MEDAUC table - Pass");
			}
			report.writeTorun(runlog, "***********Data pre check in MEDAUC table completed ***********");
			Thread.sleep(3000);
			
			//MDM
			/*resultdata=0;
			//report.writeToFile(reportfile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Precheck in MDM started"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			report.writeTorun(runlog, "***********Data pre check in MDM started *************");
			//report.writeToFile(reportfile, "***********Data pre check in MDM started *************");
			Data_CleanIn_MDM mdm= new Data_CleanIn_MDM();
			resultdata=mdm.data_CelanMDM();
			if(resultdata>0)
			{
				report.writeToFile(reportfile,"Data check in MDM - Fail");
				conditionCounter++;
			}
			else
			{
				report.writeToFile(reportfile,"Data check in MDM - Pass");
			}
			//report.writeToFile(reportfile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Precheck in MDM completed"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			report.writeTorun(runlog, "***********Data pre check in MDM completed ***********");
			//report.writeToFile(reportfile, "***********Data pre check in MDM completed ***********");
			Thread.sleep(3000);
*/
			//MCR
			/*resultdata=0;
			report.writeToFile(runlog, "***********Data pre check in MCR started *************");
			DATACLEAN_MCR mcr=new DATACLEAN_MCR();
			mcr.mcrClean();
			if(resultdata>0)
			{
				report.writeToFile(reportfile,"Data check in MCR - Fail");
				conditionCounter++;
			}
			else
			{
				report.writeToFile(reportfile,"Data check in MCR - Pass");
			}
			report.writeTorun(runlog, "***********Data pre check in MCR completed ***********");
			Thread.sleep(3000);
*/
			}
		catch (IOException e)
		{
			report.writeTorun(runlog, "Encountered IOException. Skipping this iteration.");
			conditionCounter++;
			return conditionCounter;
		}
		return conditionCounter;

	}
	
}
