package mainmenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private Socket clientSocket;
//	private PrintWriter out;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
//	private BufferedReader in;

	public void startConnection(String ip, int port) {
		try {
			clientSocket = new Socket(ip, port);
//			out = new PrintWriter(clientSocket.getOutputStream(), true);
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String sendMessage(String msg) {
		String resp = null;
		try {
			long time = System.currentTimeMillis();
			System.out.println("Client: sendingByte");
			oos.writeByte(MessageTypes.PING.getMessageType());
			oos.flush();
			System.out.println("Client: sent");
			long time2 = ois.readLong();
			long time3 = System.currentTimeMillis();
			System.out.println("Client: Ping: " + (time2 - time) + "ms, Ping Pong: " + (time3 - time) + "ms.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resp;
	}

	public void stopConnection() {
		try {
			ois.close();
			oos.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
