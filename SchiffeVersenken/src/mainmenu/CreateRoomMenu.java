package mainmenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mainpackage.CellType;
import mainpackage.Game;
import mainpackage.Model;
import mainpackage.Ship;
import mainpackage.View;

public class CreateRoomMenu {

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;

	private HostServer server;

	private JLabel testLabel;

	private JButton closeRoomButton;

	private JButton readyButton;

	private boolean hostReady = false;
	private boolean clientReady = false;

	private Model model;

	public CreateRoomMenu() {

		JFrame frame = new JFrame("Create room");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		mainPanel.setLayout(null);

		testLabel = new JLabel("cool");
		testLabel.setBounds(30, 30, 100, 20);
		mainPanel.add(testLabel);

		JButton createRoomButton = new JButton("Create!");
		createRoomButton.setBounds(50, 50, 100, 50);
		createRoomButton.addActionListener(e -> {
			createRoom();
			createRoomButton.setEnabled(false);
			closeRoomButton.setEnabled(true);
		});
		mainPanel.add(createRoomButton);

		closeRoomButton = new JButton("Close!");
		closeRoomButton.setBounds(50, 100, 100, 50);
		closeRoomButton.setEnabled(false);
		closeRoomButton.addActionListener(e -> {
			closeRoom();
			createRoomButton.setEnabled(true);
			closeRoomButton.setEnabled(false);
		});
		mainPanel.add(closeRoomButton);

		readyButton = new JButton("Ready!");
		readyButton.setBounds(50, 150, 100, 50);
		readyButton.addActionListener(e -> ready());
		mainPanel.add(readyButton);

		frame.add(mainPanel);
		frame.setVisible(true);

	}

	private void createRoom() {
		Runnable runnable = () -> {
			server = new HostServer(this);
			server.start(5555);
		};
		new Thread(runnable).start();
		testLabel.setText("Raum erstellt");
	}

	private void closeRoom() {
		server.stop();
	}

	private void ready() {
		if (!hostReady) {
			hostReady = true;
			readyButton.setText("Unready!");
		} else {
			hostReady = false;
			readyButton.setText("Ready!");
		}

		if (hostReady && clientReady) {
			startGame();
		}
	}

	private void startGame() {
		model = new Model();
		View view = new View(model);
		Game game = new Game(model, view);

		server.sendStartGame(model);

		game.startOnlinePVPHost();
	}

	public void switchClienReadyStatus() {
		if (!clientReady) {
			clientReady = true;
		} else {
			clientReady = false;
		}

		if (hostReady && clientReady) {
			startGame();
		}
	}

	public void handleStartGameData(Model otherModel) {
		//update viewMaps
		ArrayList<CellType[][]> listo = new ArrayList<>();
		listo.add(model.getViewMap(0));
		listo.add(otherModel.getViewMap(0));
		model.updateViewMaps(listo);
		
		for (CellType[] row : model.getViewMap(0)) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println("1:");
		for (CellType[] row : model.getViewMap(1)) {
			System.out.println(Arrays.toString(row));
		}
		
		//updae shipMaps
		ArrayList<Ship[][]> shipMaps = new ArrayList<>();
		shipMaps.add(model.getShipMap(0));
		shipMaps.add(otherModel.getShipMap(0));
		model.updateShipMaps(shipMaps);
		
		//update shipLists
		ArrayList<ArrayList<Ship>> shipLists = new ArrayList<>();
		shipLists.add(model.getShipLists(0));
		shipLists.add(otherModel.getShipLists(0));
		model.updateshipLists(shipLists);
		
		sendGameData();
	}
	
	public void sendGameData() {
		server.sendGameData(model);

		System.out.println("0 nach send:");
		for (CellType[] row : model.getViewMap(0)) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println("1:");
		for (CellType[] row : model.getViewMap(1)) {
			System.out.println(Arrays.toString(row));
		}
	}
}
