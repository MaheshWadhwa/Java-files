package IucNormalActivation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class IucFetchXml {
	static String reportFile = "";
	static String datasetLogFile = "";
	static String datasetFolderPath = "";
	static String datasetFolderName = "";
	static String inputFile = "";
	static CommonUtilitiesWriter utilities = null;
	static int conditionCounter = 0;

	// Constructor method initializing all the class variables
	public IucFetchXml(String inputFile, String reportFile, String datasetLogFile, String datasetFolderPath, String datasetFolderName, CommonUtilitiesWriter utilities){
		IucFetchXml.reportFile = reportFile;
		IucFetchXml.datasetLogFile = datasetLogFile;
		IucFetchXml.datasetFolderPath = datasetFolderPath;
		IucFetchXml.datasetFolderName = datasetFolderName;
		IucFetchXml.inputFile = inputFile;
		IucFetchXml.utilities = utilities;
	} // end of constructor method


	public void fetchSadadXML() throws IOException {

		utilities.writeToFile(reportFile, System.lineSeparator()+"Fetch XML Sent to SADAD"+System.lineSeparator()+"-----------------------");
		conditionCounter = 0;
		utilities.writeToFile(datasetLogFile, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+System.lineSeparator()+"\t\tFetch XML Sent to SADAD"+System.lineSeparator()+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		int cellId = 2;

		File xlFile = new File(inputFile);
		FileInputStream inputStream = new FileInputStream(xlFile);
		Workbook testWorkBook = new XSSFWorkbook(inputStream);
		Sheet testSheet = testWorkBook.getSheet("Server_Information");

		String xHost = testSheet.getRow(1).getCell(cellId).getStringCellValue();
		String xUser = testSheet.getRow(2).getCell(cellId).getStringCellValue();
		String xPassword = testSheet.getRow(3).getCell(cellId).getStringCellValue();
		

		utilities.writeToFile(datasetLogFile, "Read Server information from Excel Successful");
		testWorkBook.close();
		inputStream.close();
		
		xlFile = new File(datasetFolderPath.concat("\\IUC_DB_Check\\").concat(datasetFolderName).concat("_Log.xls"));
		inputStream = new FileInputStream(xlFile);
		testWorkBook = new HSSFWorkbook(inputStream);
		testSheet = testWorkBook.getSheet("Sheet1");
		String xSRID = testSheet.getRow(1).getCell(0).getStringCellValue();
		utilities.writeToFile(datasetLogFile, "Read SR_ID information from Excel Successful");
		testWorkBook.close();
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
			utilities.writeToFile(datasetLogFile, "Connected...");
			utilities.writeToFile(datasetLogFile, "Command is "+System.lineSeparator()+command);

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
					utilities.writeToFile(datasetLogFile, new String(tmp, 0, i));
					System.out.println(new String(tmp,0,i));
				}
				if(channel.isClosed()){
					if(in.available()>0) continue; 
					utilities.writeToFile(datasetLogFile, "exit-status: "+channel.getExitStatus());
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