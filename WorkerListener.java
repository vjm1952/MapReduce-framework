
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerListener extends Thread {
	ServerSocket ss;
	Worker worker;
	
	WorkerListener(Worker worker) {
		this.worker = worker;
		try {
			ss = new ServerSocket(Constants.WORKER_LISTEN_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				Socket socket = ss.accept();
				ObjectInputStream ois = new ObjectInputStream(
						socket.getInputStream());
				Message message = (Message) ois.readObject();
				switch (message.command) {
				case Constants.MASTER_SEND_FILE:new WorkerFileReceiver(socket, (FileInfo) message.data).start();
					break;
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
