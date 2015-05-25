import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class MasterWorkerConnection extends Thread {
	Master master;
	String workerip;
	int workerId;
	public HashMap<String, String> jobAssigned;
	public InputStream in;
	public OutputStream out;
	Socket worker;

	public MasterWorkerConnection(String ip, Master master, int wid)
			throws UnknownHostException, IOException {
		this.workerip = ip;
		this.master = master;
		this.workerId = wid;
		worker = new Socket(workerip, Constants.WORKER_LISTEN_PORT);
		this.in = worker.getInputStream();
		this.out = worker.getOutputStream();
		jobAssigned = new HashMap<String, String>();
	}

	public void run() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			//while (true) {
				Message messageReceived = (Message) ois.readObject();
				switch (messageReceived.command) {
				case Constants.receiveResult://System.out.println("Result received");
					FileInfo fileInfo = (FileInfo) messageReceived.data;
					
					master.addResult(fileInfo.result,fileInfo.name+fileInfo.chunkNo);
					//Master.resultsReceived= Master.resultsReceived+1;
					//System.out.println(Master.resultsReceived);				
					worker.close();
				//}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
