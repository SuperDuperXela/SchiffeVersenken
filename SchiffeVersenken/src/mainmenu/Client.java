package mainmenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mainpackage.CellType;
import mainpackage.Game;
import mainpackage.Model;
import mainpackage.Ship;
import mainpackage.View;

public class Client extends Thread {

	private Socket clientSocket;
//	private PrintWriter out;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
//	private BufferedReader in;

	private boolean active = true;

	public void startConnection(String ip, int port) {
		System.out.println("1 DEBUG Client: trying to connect to " + ip + " port:" + port);
		try {
			System.out.println("2 DEBUG Client: trying to connect to " + ip + " port:" + port);
			clientSocket = new Socket(ip, port);
			System.out.println("3 DEBUG Client: trying to connect to " + ip + " port:" + port);
//			out = new PrintWriter(clientSocket.getOutputStream(), true);
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			ois = new ObjectInputStream(clientSocket.getInputStream());
			
			if (oos == null) {
				System.out.println("DEBUG Client: oos is null!");
			}
			if (ois == null) {
				System.out.println("DEBUG Client: ois is null!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void run() {
		Model model = new Model();
		View view;
		Game game = null;
		try {
//			ObjectInputStream oisRun = new ObjectInputStream(clientSocket.getInputStream());
			while (active) {
				System.out.println("Client: readingByte");
				byte b;
				b = ois.readByte();
				MessageTypes messageType = MessageTypes.get(b);
				System.out.println("Client: read" + b);

				switch (messageType) {
				case GAME_START:
					model = (Model) ois.readObject();
					view = new View(model);
					game = new Game(model, view);

					game.startOnlinePVPClient(this);
					break;
				case GAME_DATA:
					Model otherModel = (Model) ois.readObject();

					// update viewMaps
					ArrayList<CellType[][]> listo = new ArrayList<>();
					listo.add(otherModel.getViewMap(1));
					listo.add(otherModel.getViewMap(0));

					model.updateViewMaps(listo);

					// update shipMaps
					ArrayList<Ship[][]> shipMaps = new ArrayList<>();
					shipMaps.add(otherModel.getShipMap(1));
					shipMaps.add(otherModel.getShipMap(0));
					model.updateShipMaps(shipMaps);

					// update shipLists
					ArrayList<ArrayList<Ship>> shipLists = new ArrayList<>();
					shipLists.add(otherModel.getShipLists(1));
					shipLists.add(otherModel.getShipLists(0));
					model.updateshipLists(shipLists);

					if (game != null) {
						game.setWaitForOtherPlayer(false);
					}

					break;
				case GAME_END:
					// TODO
					active = false;
					break;
				default:
					break;
				}

			}
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Client: Expected Class not found");
		}
	}

	public String sendMessage(MessageTypes type) {
		switch (type) {
		case CHAT_MESSAGE:
			break;
		case CLIENT_DISCONNECT:
			break;
		case CLIENT_INPUT:
			break;
		case GAME_DATA:
			break;
		case GAME_END:
			break;
		case GAME_START_DATA:
			break;
		case PING:
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
			break;
		case READY:
			try {
				oos.writeByte(MessageTypes.READY.getMessageType());
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SERVER_CLOSING:
			break;
		default:
			return "";

		}
		return null;
	}

	public void sendGameStartData(Model model) {
		try {
			oos.writeByte(MessageTypes.GAME_START_DATA.getMessageType());
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

	public void stopConnection() {
		try {
			oos.writeByte(MessageTypes.CLIENT_DISCONNECT.getMessageType());
			oos.flush();
			ois.close();
			oos.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
