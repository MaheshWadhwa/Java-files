package CUC_Number_Swap;
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
	
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=NumSwapMain.runlog;
	static String reportfile=NumSwapMain.reportfile;
	static int check=0;
	static int conditionCounter;
	
	public int postMain() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		
		
		report.writeToFile(reportfile,"");
		
		//Billing
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Billing"+System.lineSeparator()+"###############################################");
		
		Postvalid_billing bill = new Postvalid_billing();
		check=bill.post_billing();
		
		if(check==0)
	
		{
			report.writeToFile(reportfile, "Post Activation Check in Billing - Pass");
		}
		else
		{
			report.writeToFile(reportfile, "Post Activation Check in Billing - Fail");
			conditionCounter++;
		}
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Billing completed"+System.lineSeparator()+"###############################################");
		
		
		//Siebel Validation
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Siebel"+System.lineSeparator()+"###############################################");
		
 		Postvalid_Siebel_UI siebel = new Postvalid_Siebel_UI();
		check=siebel.post_siebel();
		
		if(check==0)
	
		{
			report.writeToFile(reportfile, "Post Activation Check in Siebel - Pass");
		}
		else
		{
			report.writeToFile(reportfile, "Post Activation Check in Siebel - Fail");
			conditionCounter++;
		}
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Siebel completed"+System.lineSeparator()+"###############################################");
		
		
		
		
		//MCR
		
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR"+System.lineSeparator()+"###############################################");
		
		PostValidation_In_MCR mcr = new PostValidation_In_MCR();
		check=mcr.postValidMCR();
		
		if(check==0)
	
		{
			report.writeToFile(reportfile, "Post Activation Check in MCR - Pass");
		}
		else
		{
			report.writeToFile(reportfile, "Post Activation Check in MCR - Fail");
			conditionCounter++;
		}
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR completed"+System.lineSeparator()+"###############################################");
		
		
		//MDM
		
		
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR"+System.lineSeparator()+"###############################################");
		
		PostValidation_In_MDM mdm = new PostValidation_In_MDM();
		check=mdm.PostValidMDM();
		
		if(check==0)
	
		{
			report.writeToFile(reportfile, "Post Activation Check in MCR - Pass");
		}
		else
		{
			report.writeToFile(reportfile, "Post Activation Check in MCR - Fail");
			conditionCounter++;
		}
		
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR completed"+System.lineSeparator()+"###############################################");
		
		return conditionCounter;
		
		
		
		
		
		
		
		
		
		
		/*//Mediation
		
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MEDIATION"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MEDITAION"+System.lineSeparator()+"###############################################");
		
		Med_Validation med = new Med_Validation();
		med.post_mediation();
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MEDIATION completed"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MEDITAION completed"+System.lineSeparator()+"###############################################");
		Thread.sleep(3000);

		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Billing"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Billing"+System.lineSeparator()+"###############################################");
		
		Postvalid_billing bill = new Postvalid_billing();
		bill.post_billing();
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Billing completed"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Billing completed"+System.lineSeparator()+"###############################################");
		Thread.sleep(3000);
	
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Siebel"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Siebel"+System.lineSeparator()+"###############################################");
		
		Postvalid_Siebel_UI siebel = new Postvalid_Siebel_UI();
		siebel.post_siebel();
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Siebel completed"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in Siebel completed"+System.lineSeparator()+"###############################################");
			Thread.sleep(3000);
	
		
			//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR"+System.lineSeparator()+"###############################################");
			report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR"+System.lineSeparator()+"###############################################");
			
		PostValidation_In_MCR MCR = new PostValidation_In_MCR();
		MCR.postValidMCR();
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR Completed"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MCR Completed"+System.lineSeparator()+"###############################################");
		Thread.sleep(3000);
	
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MDM "+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MDM"+System.lineSeparator()+"###############################################");
		
		PostValidation_In_MDM MDM = new PostValidation_In_MDM();
		MDM.PostValidMDM();
		
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MDM Completed"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Validation in MDM Completed"+System.lineSeparator()+"###############################################");
		Thread.sleep(3000);
	
		//report.writeToFile(reportfile, "#######################################"+System.lineSeparator()+"\t\tPost Activation Validation Completed"+System.lineSeparator()+"###############################################");
		report.writeTorun(runlog, "#######################################"+System.lineSeparator()+"\t\tPost Activation Validation  Completed"+System.lineSeparator()+"###############################################");
*/		
	}

}
