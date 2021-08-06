package iucNormalActivation_FrameWork;

import java.io.IOException;
import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ServicesHealthCheck {

	static String result = "";
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public ServicesHealthCheck(String reportFile, String datasetLogFile, String datasetFolderPath, CommonUtilitiesWriter utilities){
		ServicesHealthCheck.reportFile = reportFile;
		ServicesHealthCheck.datasetLogFile = datasetLogFile;
		ServicesHealthCheck.datasetFolderPath = datasetFolderPath;
		ServicesHealthCheck.utilities = utilities;
	}
	
	
	public int servicesCheck() throws IOException, InterruptedException {

		utilities.writeToFile(reportFile, System.lineSeparator()+"Services Health Check"+System.lineSeparator()+"---------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tServices Health Check"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		String chkCP001 = "ps -ef|grep CP001";
		String chkMDP1 = "ps -ef|grep MDP1";
		String chk = "sh /apps/med/prov/bin/chk";

		JSch jsch = new JSch();
		Session session = null;

		java.util.Properties config = new java.util.Properties(); 
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications", "password,gssapi-with-mic,publickey,keyboard-interactive");

		try {
			session=jsch.getSession("meduser", "10.14.11.186", 22);
			session.setPassword("Mobily@678");
			session.setConfig(config);

			session.connect();

			System.out.println("Connected");

			executeCommand(session,chkCP001);
			int checkCounter = 0;
			for(int i=0;i<5;i++){
				if(result.contains("/apps/med/prov/bin/caiProv CP001")){
					//System.out.println("CP001 up and running..");
					checkCounter++;
					utilities.writeToFile(reportFile, "CP001 up and running..");
					utilities.writeToFile(datasetLogFile, "CP001 up and running..");
					break;
				}else{
					utilities.writeToFile(datasetLogFile, "CP001 was not up..");
					utilities.writeToFile(datasetLogFile, "Executing chk command....");
					executeCommand(session,chk);
					utilities.writeToFile(datasetLogFile, "Executed chk command..");
				}
				Thread.sleep(5000);
				System.out.println(i);
				executeCommand(session,chkCP001);
			}
			if(checkCounter==0){
				conditionCounter++;
				utilities.writeToFile(reportFile, "CP001 is down..");
				utilities.writeToFile(datasetLogFile, "CP001 is down..");
			}

			checkCounter = 0;
			executeCommand(session,chkMDP1);
			for(int i=0;i<5;i++){
				if(result.contains("/apps/med/prov/bin/med_poller MDP1")){
					//System.out.println("MDP1 up and running..");
					checkCounter++;
					utilities.writeToFile(reportFile, "MDP1 up and running..");
					utilities.writeToFile(datasetLogFile, "MDP1 up and running..");
					break;
				}else{
					utilities.writeToFile(datasetLogFile, "MDP1 was not up..");
					utilities.writeToFile(datasetLogFile, "Executing chk command....");
					executeCommand(session,chk);
					utilities.writeToFile(datasetLogFile, "Executed chk command..");
				}
				Thread.sleep(5000);
				System.out.println(i);
				executeCommand(session,chkMDP1);
			}
			if(checkCounter==0){
				conditionCounter++;
				utilities.writeToFile(reportFile, "MDP1 is down..");
				utilities.writeToFile(datasetLogFile, "MDP1 is down..");
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