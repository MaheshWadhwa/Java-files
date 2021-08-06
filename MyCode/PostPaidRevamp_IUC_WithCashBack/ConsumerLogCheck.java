package PostPaidRevamp_IUC_WithCashBack;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;




import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ConsumerLogCheck 

{
	
	static String Actipre;
	static String runlog;
	static String reportfile;
	static CommonUtilitiesWriter report;
	static int conditionCounter=0;
	static String File;
	/*static String File;
	static String Actipre=ConsumerActivationMain.Actipre;
	
	static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivationMain.runlog;
	static String reportfile=ConsumerActivationMain.reportfile;
	static int conditionCounter = 0;*/
	
	public ConsumerLogCheck(String runlog,String reportfile,String Actipre,CommonUtilitiesWriter report)
	{
		ConsumerLogCheck.runlog=runlog;
		ConsumerLogCheck.reportfile=reportfile;
		ConsumerLogCheck.Actipre=Actipre;
		ConsumerLogCheck.report=report;	
	}
	
	public void execute() throws IOException
	{
		
		report.writeToFile(reportfile, System.lineSeparator()+"Fetch XML Sent to SADAD"+System.lineSeparator()+"-----------------------");
		conditionCounter = 0;
		report.writeToFile(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tFetch XML Sent to SADAD"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	
		int cellId = 2;
		conditionCounter=0;
		
		File xlFile = new File("C:\\Automated_Execution\\Input\\TestData_Revamp_IUC _WithCashBack.xls");
		FileInputStream inputStream = new FileInputStream(xlFile);
		
		
		Workbook testWorkBook = new HSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Log_Check");
		
		// Log Info
		String xHost = testSheet.getRow(1).getCell(cellId).getStringCellValue();
		String xUser = testSheet.getRow(2).getCell(cellId).getStringCellValue();
		String xPassword = testSheet.getRow(3).getCell(cellId).getStringCellValue();
		String xSRID = testSheet.getRow(4).getCell(cellId).getStringCellValue();
		
		report.writeTorun(runlog,"Read Data from Excel Successful");
		//testWorkBook.close();
		inputStream.close();
		
		String command1 = "pwd";
		String command2 = "cd AccountActivationIndividual/log";
		String command3 = "grep 'Going to send Request Message to BE \\[MOBILY\\.FUNC\\.CREATEACT\\.SADAD\\.EXTERNAL\\.REQUEST\\] for SR_ID \\[".concat(xSRID).concat("\\].*' actActivationInd.log");
		String command4 = "nawk '/Going to send Request Message to BE \\[MOBILY\\.FUNC\\.CREATEACT\\.SADAD\\.EXTERNAL\\.REQUEST\\] for SR_ID \\[".concat(xSRID).concat("\\]/{getline;print}' actActivationInd.log");
		
		String command = command1.concat("\n").concat(command2).concat("\n").concat(command3).concat("\n").concat(command4);
		
		JSch jsch = new JSch();
		Session session = null;

		java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        config.put("PreferredAuthentications", "password,gssapi-with-mic,publickey,keyboard-interactive");
		
		try {
			session=jsch.getSession(xUser, xHost, 22);
			session.setPassword(xPassword);
	        session.setConfig(config);
	        
	        session.connect();
	        report.writeTorun(runlog,"Connected");
	        System.out.println("Connected");
	        executeCommand(session,command);
            session.disconnect();
         
	        
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void executeCommand(Session session, String command){
		
		Channel channel;
		try {
			channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			
			channel.connect();
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
	        InputStream in=channel.getInputStream();
	        
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            report.writeTorun(runlog,new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            if(in.available()>0) continue; 
	            report.writeTorun(runlog,"exit-status: "+channel.getExitStatus());
	            report.writeTorun(runlog,"Log captured successfully");
	            report.writeToFile(reportfile,"Log captured successfully");
	            break;
	          }
	          try
	          {
	        	  Thread.sleep(1000);
	        	  }
	          catch(Exception ee)
	          {
	        	  
	          }
	        }
	        
	        channel.disconnect();
	   
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}