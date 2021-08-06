package IUC_MNP;
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
	
	static int row=2;
	//static int cellId
	static Statement stmt;
	/*static String MSISDN;
	static String SIM;*/
	static String ip;
	static String ser_username;
	static String ser_password;
	static String File;
	static String Postpre;
	static String Actipre;
	static String bill_path;
	static int conditionCounter = 0;
	static String runlog;
	static String reportfile;
	static String SIM;
	static String MSISDN;
	static String IMSI;
	static CommonUtilitiesWriter report;
	//static int resultdata;
	

	public Post_ValidationMain(String MSISDN,String SIM,String IMSI,String runlog,String reportfile,String Postpre,String Actipre,CommonUtilitiesWriter report)
	{
		Post_ValidationMain.runlog=runlog;
		Post_ValidationMain.reportfile=reportfile;
		Post_ValidationMain.Postpre=Postpre;
		Post_ValidationMain.report=report;
		Post_ValidationMain.Actipre=Actipre;
		Post_ValidationMain.MSISDN=MSISDN;
		Post_ValidationMain.SIM=SIM;
		Post_ValidationMain.IMSI=IMSI;
	}
	
	public int postMain() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		//Billing
		
		
		int check = 0;
		report.writeToFile(reportfile, System.lineSeparator()+"Post Activation Validation"+System.lineSeparator()+"--------------------------");
		conditionCounter = 0;
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tPost Activation Validation"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		Postvalid_billing postBillingCheckObj = new Postvalid_billing(runlog,reportfile,Postpre,report);
		check = postBillingCheckObj.post_billing(MSISDN);
		
		if(check == 0){
			report.writeToFile(reportfile, "Post Activation Check in Billing - Pass");
		}else{
			report.writeToFile(reportfile, "Post Activation Check in Billing - Fail");
			conditionCounter++;
		}
		
		Postvalid_Siebel_UI postSiebelCheckObj = new Postvalid_Siebel_UI(runlog,reportfile,Postpre,Actipre,report);
		check = postSiebelCheckObj.post_siebel(MSISDN);
		
		if(check == 0){
			report.writeToFile(reportfile, "Post Activation Check in Siebel - Pass");
		}else{
			report.writeToFile(reportfile, "Post Activation Check in Siebel - Fail");
			conditionCounter++;
		}
		
		PostValidation_In_MDM postMdmCheckObj = new PostValidation_In_MDM(runlog,reportfile,Postpre,Actipre,report);
		check = postMdmCheckObj.PostValidMDM(MSISDN,SIM);
		
		if(check == 0){
			report.writeToFile(reportfile, "Post Activation Check in MDM - Pass");
		}else{
			report.writeToFile(reportfile, "Post Activation Check in MDM - Fail");
			conditionCounter++;
		}
		
		PostValidation_In_MCR postMcrCheckObj = new PostValidation_In_MCR(runlog,reportfile,Postpre,Actipre,report);
		check = postMcrCheckObj.postValidMCR(MSISDN,SIM);
		
		if(check == 0){
			report.writeToFile(reportfile, "Post Activation Check in MCR - Pass");
		}else{
			report.writeToFile(reportfile, "Post Activation Check in MCR - Fail");
			conditionCounter++;
		}	
		return conditionCounter;
	}
}
