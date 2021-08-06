package BUSINESS_RAQI3_MNP_PORT_IN;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;

	import javax.swing.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;


public class Post_ValidationMain {
	static String runlog=Raaqi3BusinessMNPPortINActivationMain.runlog;
	static String reportfile=Raaqi3BusinessMNPPortINActivationMain.reportfile;
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
			
			////report.writeTorun(runlog, "***********Data pre check in SIEBEL started *************");
			
			//Billing
			resultdata=0;
			////report.writeTorun(runlog, "***********Data pre check in BIlling started *************");
			DATACLEAN_BILLING_MNP billing=new DATACLEAN_BILLING_MNP();
			resultdata=billing.billing_execute();
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
			DATACLEAN_SIEBEL_ASSET siebel_clean=new DATACLEAN_SIEBEL_ASSET();
			resultdata=siebel_clean.execute();
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
			DATACLEAN_EMADEV emadev=new DATACLEAN_EMADEV();
			resultdata=emadev.emadev_clean();
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
			DataCheckInCC cc=new DataCheckInCC();
			resultdata=cc.execute();
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
			DataCheckInNetwork net=new DataCheckInNetwork();
			resultdata=net.execute();
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
			//report.writeTorun(runlog, "***********Data pre check in MDM completed ***********");
			//report.writeToFile(reportfile, "***********Data pre check in MDM completed ***********");
			Thread.sleep(3000);

			//MCR
			resultdata=0;
			report.writeToFile(runlog, "***********Data pre check in MCR started *************");
			DATACLEAN_MCR mcr=new DATACLEAN_MCR();
			resultdata=mcr.mcrClean();
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
