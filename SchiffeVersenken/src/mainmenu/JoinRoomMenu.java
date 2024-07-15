package mainmenu;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinRoomMenu {

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;
	private JTextField ipAdressField;

	Client client;

	public JoinRoomMenu() {
		JFrame frame = new JFrame("Join room");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		mainPanel.setLayout(null);

		JButton joinRoomButton = new JButton("Join!");
		joinRoomButton.setBounds(50, 50, 100, 50);
		joinRoomButton.addActionListener(e -> connect());
		mainPanel.add(joinRoomButton);
		
		JButton readyButton = new JButton("Ready!");
		readyButton.setBounds(50, 150, 100, 50);
		readyButton.addActionListener(e -> ready());
		mainPanel.add(readyButton);
		
		ipAdressField = new JTextField("localhost");
		ipAdressField.setBounds(180, 50, 120, 40);
		ipAdressField.setToolTipText("IP Adresse vom Raum Ersteller, leer lassen für lokalen Host");
		mainPanel.add(ipAdressField);

		frame.add(mainPanel);
		frame.setVisible(true);
	}

	private void connect() {
		System.out.println("DEBUG JoinRoomMenu: connect()");
		
		Runnable runnable = () -> {
			client = new Client();
			
			String targetIP = ipAdressField.getText();
			if (targetIP.equals("")) {
				targetIP = "localhost";
			}
			client.startConnection(targetIP, 5555); // "127.0.0.1"
			client.start();
		};
		new Thread(runnable).start();
	}
	
	private void ready() {
		if (client != null) {
			System.out.println("JoinRoom: ready");
			client.sendMessage(MessageTypes.READY);
		}
	}
}
