package CUC_MNP_PORT_IN;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



import CUC_MNP_PORT_IN.DataClean_Main;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ConsumerHealthCheck

{


	/*static CommonUtilitiesWriter report=new CommonUtilitiesWriter();
	static String runlog=ConsumerActivation.runlog;
	static String reportfile=ConsumerActivation.reportfile;
*/
	static String result = "";
	static int conditionCounter=0;
	static int checkCounter=0;
	
	static CommonUtilitiesWriter report;
	//static String result;
	static String runlog;
	static String reportfile;
	
	public ConsumerHealthCheck(String runlog,String reportfile,CommonUtilitiesWriter report)
	{
		
		ConsumerHealthCheck.report=report;
		ConsumerHealthCheck.reportfile=reportfile;
		ConsumerHealthCheck.runlog=runlog;
	
	}
	
	public int execute() throws IOException, InterruptedException {

		report.writeToFile(reportfile, System.lineSeparator()+"Services Health Check"+System.lineSeparator()+"---------------------");
		conditionCounter = 0;
		report.writeTorun(runlog, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tServices Health Check"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String ip;
		String username;
		String password;
		int port;
		String chkCP001 = "ps -ef|grep CP001";
		String chkMDP1 = "ps -ef|grep MDP1";
		String chk = "sh /apps/med/prov/bin/chk";

		JSch jsch = new JSch();
		Session session = null;

		java.util.Properties config = new java.util.Properties(); 
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications", "password,gssapi-with-mic,publickey,keyboard-interactive");
		
		//Data Read
		
		File file_data=new File ("C:\\Automated_Execution\\Input\\TestData_Consumer_MNP.xls");
		FileInputStream inputstrm= new FileInputStream(file_data);
		HSSFWorkbook testdata=new HSSFWorkbook(inputstrm); 
	    HSSFSheet testSheet1 = testdata.getSheet("Server_Information");
	    ip = testSheet1.getRow(13).getCell(2).getStringCellValue();
		username = testSheet1.getRow(14).getCell(2).getStringCellValue();
		password = testSheet1.getRow(15).getCell(2).getStringCellValue();
		String por = testSheet1.getRow(16).getCell(2).getStringCellValue();
		port = Integer.parseInt(por);
		
		report.writeTorun(runlog,"Data raed successfully");
		
		try {
			
			session=jsch.getSession(username, ip,port);
			session.setPassword(password);
			session.setConfig(config);

			session.connect();

			report.writeTorun(runlog,"Connected to servers");
			executeCommand(session,chkCP001);
			for(int i=0;i<5;i++){
				if(result.contains("/apps/med/prov/bin/caiProv CP001")){
					//System.out.println("CP001 up and running..");
					checkCounter++;
					report.writeTorun(runlog, "CP001 up and running..");
					//report.writeToFile(datasetLogFile, "CP001 up and running..");
					break;
				}else{
					report.writeTorun(runlog, "CP001 was not up..");
					report.writeTorun(runlog, "Executing chk command....");
					executeCommand(session,chk);
					//report.writeToFile(datasetLogFile, "Executed chk command..");
				}
				Thread.sleep(5000);
				System.out.println(i);
				executeCommand(session,chkCP001);
			}
			if(checkCounter==0){
				conditionCounter++;
				report.writeTorun(runlog, "CP001 is down..");
				//report.writeToFile(datasetLogFile, "CP001 is down..");
			}

			checkCounter = 0;
			executeCommand(session,chkMDP1);
			for(int i=0;i<5;i++){
				if(result.contains("/apps/med/prov/bin/med_poller MDP1")){
					//System.out.println("MDP1 up and running..");
					checkCounter++;
					report.writeTorun(runlog, "MDP1 up and running..");
					//report.writeToFile(datasetLogFile, "MDP1 up and running..");
					break;
				}else{
					report.writeTorun(runlog, "MDP1 was not up..");
					report.writeTorun(runlog, "Executing chk command....");
					executeCommand(session,chk);
					//report.writeToFile(datasetLogFile, "Executed chk command..");
				}
				Thread.sleep(5000);
				System.out.println(i);
				executeCommand(session,chkMDP1);
			}
			if(checkCounter==0){
				conditionCounter++;
				report.writeTorun(runlog, "MDP1 is down..");
				//report.writeToFile(datasetLogFile, "MDP1 is down..");
			}
			session.disconnect();
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conditionCounter;
	}

	public static void executeCommand(Session session, String command){

		result = "";
		Channel channel;
		try {
			channel = session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);

			channel.connect();
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);
			System.err.close();
			InputStream in=channel.getInputStream();

			byte[] tmp=new byte[1024];
			while(true){

				while(in.available()>0){
					int i=in.read(tmp, 0, 1024);
					if(i<0)break;
					if(!command.contains("chk"))
						result = result.concat(new String(tmp,0,i));
//					else
//						System.out.println("chk command executed");
				}

				//System.out.println("---"+result);
				if(channel.isClosed()){
					if(in.available()>0) continue; 
					//System.out.println("exit-status: "+channel.getExitStatus());
					break;
				}
				try{Thread.sleep(1000);}catch(Exception ee){}
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
