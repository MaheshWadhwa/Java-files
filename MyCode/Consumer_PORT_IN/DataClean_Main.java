package Consumer_PORT_IN;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;







public class DataClean_Main {
	static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String datapre;
	static String bill_path;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static String IMSI;
	static CommonUtilitiesWriter report;
	static int resultdata;
	
	/*static String runlog=ConsumerActivation.runlog;
	static String reportfile=ConsumerActivation.reportfile;
	static int conditionCounter = 0;
	static int resultdata;
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static int cellId=ConsumerActivationMain.cellId;*/
	
	public DataClean_Main(String MSISDN,String SIM,String IMSI,String runlog,String reportfile,String datapre,CommonUtilitiesWriter report)
	{
		DataClean_Main.SIM= SIM;
		DataClean_Main.IMSI = IMSI;
		DataClean_Main.MSISDN = MSISDN;
		DataClean_Main.report=report;
		DataClean_Main.reportfile=reportfile;
		DataClean_Main.runlog=runlog;
		DataClean_Main.datapre=datapre;
	}
	public int dataCleanmain()throws IOException, InterruptedException, ClassNotFoundException, SQLException
	{
		
		//CommonUtilitiesWriter report=new CommonUtilitiesWriter();
		
		conditionCounter=0;
		resultdata=0;
		
//		report.writeToFile(reportfile, "Data Cleanliness Check"+System.lineSeparator()+report.insertDashedLine("Data Cleanliness Check"));
//
//		// Creating folder for Data Cleanliness Check
//		String  dataprecheck= "Data_Cleanliness_Check";
//		String datapr = datapre.concat("\\").concat(dataprecheck);
//		report.createFolder(datapr, "\\"+dataprecheck); 

		
		////report.writeTorun(runlog, "***********Data pre check in SIEBEL started *************");
		
		//Billing
		
		////report.writeTorun(runlog, "***********Data pre check in BIlling started *************");
		DATACLEAN_BILLING billing=new DATACLEAN_BILLING(runlog,reportfile,datapre,report);
		resultdata=billing.billing_execute(MSISDN, SIM);
		System.out.print("Check ");
		if(resultdata>0)
		{
			report.writeToFile(reportfile,"Data check in Billing - Fail");
			conditionCounter++;
		
		}
		else
		{
			report.writeToFile(reportfile,"Data check in Billing - Pass");
		}
		////report.writeTorun(runlog, "***********Data pre check in Billing completed ***********");
		Thread.sleep(3000);
		
		//Siebel
		
		resultdata=0;
		DATACLEAN_SIEBEL_ASSET siebel_clean=new DATACLEAN_SIEBEL_ASSET(runlog,reportfile,datapre,report);
		resultdata=siebel_clean.execute(MSISDN,SIM);
		if(resultdata==0)
		{
			
			//System.out.println("Check 1");
			report.writeToFile(reportfile,"Data check in Siebel - Pass");
			}
		else
		{
			
			report.writeToFile(reportfile,"Data check in Siebel - Fail");
			conditionCounter++;
		
			
		}
		
		////report.writeTorun(runlog, "***********Data pre check in SIEBEL completed ***********");
		Thread.sleep(3000);

		
		//EMADEV
		resultdata=0;
		//report.writeTorun(runlog, "***********Data pre check in MEDAUC table started *************");
		DATACLEAN_EMADEV emadev=new DATACLEAN_EMADEV(runlog,reportfile,datapre,report);
		resultdata=emadev.emadev_clean(IMSI);
		if(resultdata>0)
		{
			report.writeToFile(reportfile,"Data check in MEDAUC table - Fail");
			conditionCounter++;
		}
		else
		{
			report.writeToFile(reportfile,"Data check in MEDAUC table - Pass");
		}
		//report.writeTorun(runlog, "***********Data pre check in MEDAUC table completed ***********");
		Thread.sleep(3000);
		
		//Credit Control
		resultdata=0;
		DataCheckInCC cc=new DataCheckInCC(runlog,reportfile,datapre,report);
		resultdata=cc.execute(MSISDN,SIM);
		if(resultdata>0)
		{
			report.writeToFile(reportfile,"Data check in Credit Control - Fail");
			conditionCounter++;
		}
		else
		{
			report.writeToFile(reportfile,"Data check in Credit Control - Pass");
		}
		//report.writeTorun(runlog, "***********Data pre check in MEDAUC table completed ***********");
		Thread.sleep(3000);
		
		//Network
		
		resultdata=0;
		DataCheckInNetwork net=new DataCheckInNetwork(runlog,reportfile,datapre,report);
		resultdata=net.execute(MSISDN,SIM,IMSI);
		if(resultdata>0)
		{
			report.writeToFile(reportfile,"Data check in Network - Fail");
			conditionCounter++;
		}
		else
		{
			report.writeToFile(reportfile,"Data check in Network - Pass");
		}
		//report.writeTorun(runlog, "***********Data pre check in MEDAUC table completed ***********");
		Thread.sleep(3000);
			
		
		
		
		
		//MDM
		resultdata=0;
		//report.writeToFile(reportfile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tData Precheck in MDM started"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//report.writeTorun(runlog, "***********Data pre check in MDM started *************");
		//report.writeToFile(reportfile, "***********Data pre check in MDM started *************");
		Data_CleanIn_MDM mdm= new Data_CleanIn_MDM(runlog,reportfile,datapre,report);
		resultdata=mdm.data_CelanMDM(MSISDN,SIM);
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
		//report.writeTorun(runlog, "***********Data pre check in MDM completed ***********");
		//report.writeToFile(reportfile, "***********Data pre check in MDM completed ***********");
		Thread.sleep(3000);
		
		
		//MCR
		resultdata=0;
		report.writeToFile(runlog, "***********Data pre check in MCR started *************");
		DATACLEAN_MCR mcr=new DATACLEAN_MCR(runlog,reportfile,datapre,report);
		resultdata=mcr.mcrClean(MSISDN,SIM);
		if(resultdata>0)
		{
			report.writeToFile(reportfile,"Data check in MCR - Fail");
			conditionCounter++;
		}
		else
		{
			report.writeToFile(reportfile,"Data check in MCR - Pass");
		}
		//report.writeTorun(runlog, "***********Data pre check in MCR completed ***********");
		Thread.sleep(3000);

		
		
		return conditionCounter;

	}
	
}
