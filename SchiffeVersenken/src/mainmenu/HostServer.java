package mainmenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class HostServer {
	private ServerSocket serverSocket;
	private boolean active = true;

	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			while (active) {
				new HostClientHandler(serverSocket.accept()).start();
			}
		} catch (SocketException e) {
			System.out.println("Room closed, likely intended");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class HostClientHandler extends Thread {
		private Socket clientSocket;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
//		private BufferedReader in;

		public HostClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		@Override
		public void run() {
			try {
				oos = new ObjectOutputStream(clientSocket.getOutputStream());
				ois = new ObjectInputStream(clientSocket.getInputStream());
//				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				System.out.println("Host: readingByte");
				byte b = ois.readByte();
				MessageTypes messageType = MessageTypes.get(b);
				System.out.println("Host: read" + b);

				switch (messageType) {
				case CLIENT_DISCONNECT:
					break;
				case PING:
					oos.writeLong(System.currentTimeMillis());
					oos.flush();
					break;
				default:
					break;
				}

				System.out.println("Server: Ende im Gelände");
				ois.close();
				oos.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
