package mainmenu;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
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

	private static final Color buttonBackgroundEnabled = new Color(44, 70, 120);
	private static final Color buttonBackgroundDisabled = new Color(70, 70, 70);

	private JLabel testLabel;

	private JButton createRoomButton;
	private JButton closeRoomButton;
	private JButton readyButton;

	private boolean hostReady = false;
	private boolean clientReady = false;

	private Model model;
	private Game game;
	private HostServer server;

	public CreateRoomMenu() {
		JFrame frame = new JFrame("Raum erstellen");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		mainPanel.setLayout(null);

		ImageIcon image = new ImageIcon("media/images/schiffMenuHintergrund.png");
		JLabel backgroundImage = new JLabel(image);
		backgroundImage.setBounds(0, 5, 1000, 750);

		testLabel = new JLabel("");
		testLabel.setBounds(30, 30, 150, 20);
		mainPanel.add(testLabel);

		createRoomButton = createButton("Raum öffnen", e -> createRoomButton());
		createRoomButton.setBounds(30, 100, 150, 45);
		mainPanel.add(createRoomButton);

		closeRoomButton = createButton("Raum schließen", e -> closeRoomButton());
		closeRoomButton.setBounds(30, 155, 150, 45);
		closeRoomButton.setBackground(buttonBackgroundDisabled);
		closeRoomButton.setEnabled(false);
		mainPanel.add(closeRoomButton);

		readyButton = createButton("Bereit", e -> ready());
		readyButton.setBounds(30, 210, 150, 45);
		mainPanel.add(readyButton);

		mainPanel.add(backgroundImage);
		frame.add(mainPanel);
		frame.setVisible(true);
	}

	private JButton createButton(String buttonText, ActionListener listener) {
		JButton button = new JButton(buttonText);
		button.addActionListener(listener);
		button.setBackground(buttonBackgroundEnabled);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		return button;
	}

	private void createRoomButton() {
		createRoom();
		createRoomButton.setBackground(buttonBackgroundDisabled);
		closeRoomButton.setBackground(buttonBackgroundEnabled);
		createRoomButton.setEnabled(false);
		closeRoomButton.setEnabled(true);
	}

	private void closeRoomButton() {
		closeRoom();
		closeRoomButton.setBackground(buttonBackgroundDisabled);
		createRoomButton.setBackground(buttonBackgroundEnabled);
		createRoomButton.setEnabled(true);
		closeRoomButton.setEnabled(false);
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
			readyButton.setText("Nicht bereit");
			readyButton.setBackground(buttonBackgroundDisabled);
		} else {
			hostReady = false;
			readyButton.setText("Bereit");
			readyButton.setBackground(buttonBackgroundEnabled);
		}

		if (hostReady && clientReady) {
			startGame();
		}
	}

	private void startGame() {
		model = new Model();
		View view = new View(model);
		game = new Game(model, view);

		server.sendStartGame(model);

		game.startOnlinePVPHost(this);
	}

	public void switchClientReadyStatus() {
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
		// update viewMaps
		ArrayList<CellType[][]> listo = new ArrayList<>();
		listo.add(model.getViewMap(0));
		listo.add(otherModel.getViewMap(0));
		model.updateViewMaps(listo);

		// update shipMaps
		ArrayList<Ship[][]> shipMaps = new ArrayList<>();
		shipMaps.add(model.getShipMap(0));
		shipMaps.add(otherModel.getShipMap(0));
		model.updateShipMaps(shipMaps);

		// update shipLists
		ArrayList<ArrayList<Ship>> shipLists = new ArrayList<>();
		shipLists.add(model.getShipLists(0));
		shipLists.add(otherModel.getShipLists(0));
		model.updateshipLists(shipLists);

		game.setWaitForOtherPlayer(false);
	}

	public void handleGameData(Model otherModel) {
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

		game.setWaitForOtherPlayer(false);
	}

	public void sendGameData() {
		server.sendGameData(model);
	}
}