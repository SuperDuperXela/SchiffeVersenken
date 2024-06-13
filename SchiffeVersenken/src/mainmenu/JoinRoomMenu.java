package mainmenu;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JoinRoomMenu {

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;

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

		JButton pingButton = new JButton("Ping!");
		pingButton.setBounds(50, 100, 100, 50);
		pingButton.addActionListener(e -> ping());
		mainPanel.add(pingButton);

		frame.add(mainPanel);
		frame.setVisible(true);
	}

	private void connect() {
		Runnable runnable = () -> {
			client = new Client();
			client.startConnection("localhost", 5555); // "127.0.0.1"
		};
		new Thread(runnable).start();
//		System.out.println("sendMSG");
//		client.sendMessage("");
	}

	private void ping() {
		System.out.println("JoinRoom: sendMSG");
		client.sendMessage("");
	}
}
