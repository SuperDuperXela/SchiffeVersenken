package mainmenu;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreateRoomMenu {

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;

	private HostServer server;

	private JLabel testLabel;

	private JButton closeRoomButton;

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

		frame.add(mainPanel);
		frame.setVisible(true);

	}

	private void createRoom() {
		Runnable runnable = () -> {
			server = new HostServer();
			server.start(5555);
		};
		new Thread(runnable).start();
		testLabel.setText("Raum erstellt");
	}

	private void closeRoom() {
		server.stop();
	}
}
