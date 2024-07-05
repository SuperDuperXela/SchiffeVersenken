package mainmenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import mainpackage.CellType;
import mainpackage.Model;

public class HostServer {
	private ServerSocket serverSocket;
	private static boolean active = true;
	private ArrayList<HostClientHandler> clientHandlers = new ArrayList<>();

	private static CreateRoomMenu createRoomMenu;

	public HostServer(CreateRoomMenu menu) {
		HostServer.createRoomMenu = menu;
	}

	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			while (active) {
				HostClientHandler clientHandler = new HostClientHandler(null);
				clientHandler.setSocket(serverSocket.accept());
				clientHandler.start();
				clientHandlers.add(clientHandler);
			}
		} catch (SocketException e) {
			System.out.println("Room closed, likely intended");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendStartGame(Model model) {
		for (HostClientHandler handler : clientHandlers) {
			handler.sendGameStart(model);
		}
	}

	public void sendGameData(Model model) {
		for (HostClientHandler handler : clientHandlers) {
			handler.sendGameData(model);
		}
	}

	public void stop() {
		setActive(false);
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setActive(boolean b) {
		active = b;
	}

	private static class HostClientHandler extends Thread {
		private Socket clientSocket;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
//		private BufferedReader in;

		public HostClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void setSocket(Socket socket) {
			this.clientSocket = socket;
		}

		private static void clientSwitchReadyStatus() {
			createRoomMenu.switchClientReadyStatus();
		}

		@Override
		public void run() {
			try {
				oos = new ObjectOutputStream(clientSocket.getOutputStream());
				ois = new ObjectInputStream(clientSocket.getInputStream());
//				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				while (active) {

					System.out.println("Host: readingByte");
					byte b = ois.readByte();
					MessageTypes messageType = MessageTypes.get(b);
					System.out.println("Host: read" + b);

					switch (messageType) {
					case CLIENT_DISCONNECT:
						System.out.println("Host: A client disconnected");
						break;
					case PING:
						oos.writeLong(System.currentTimeMillis());
						oos.flush();
						break;
					case CHAT_MESSAGE:
						break;
					case CLIENT_INPUT:
						break;
					case GAME_DATA:
						Model modelData = (Model) ois.readObject();
						createRoomMenu.handleGameData(modelData);
						break;
					case GAME_END:
						break;
					case GAME_START_DATA:
						Model modelStart = (Model) ois.readObject();
						createRoomMenu.handleStartGameData(modelStart);
						break;
					case READY:
						System.out.println("Host: Client switched ready status.");
						clientSwitchReadyStatus();
						break;
					case SERVER_CLOSING:
						break;
					default:
						break;
					}

				}
				System.out.println("Host: Ende im Gelände");
				oos.writeByte(MessageTypes.SERVER_CLOSING.getMessageType());
				oos.flush();
				ois.close();
				oos.close();
				clientSocket.close();
			} catch (SocketException e) {
				System.out.println("Host: Client disconnected (SocketException)");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Host: Class not Found");
			}
		}

		public void sendGameStart(Model model) {
			try {
				oos.writeByte(MessageTypes.GAME_START.getMessageType());
				oos.flush();
				oos.writeObject(model);
				oos.flush();
				oos.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void sendGameData(Model model) {
			try {
				oos.writeByte(MessageTypes.GAME_DATA.getMessageType());
				oos.flush();
				oos.writeObject(model);
				oos.flush();
				oos.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
