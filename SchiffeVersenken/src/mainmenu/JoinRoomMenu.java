package mainmenu;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JoinRoomMenu {

	private static final int WINDOW_WIDTH = 900;
	private static final int WINDOW_HEIGHT = 600;
	private static final Color buttonBackgroundEnabled = new Color(44, 70, 120);
	private static final Color buttonBackgroundDisabled = new Color(70, 70, 70);
	
	private JTextField ipAdressField;

	Client client;

	public JoinRoomMenu() {
		JFrame frame = new JFrame("Raum beitreten");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		mainPanel.setLayout(null);
		
		ImageIcon image = new ImageIcon("media/images/schiffMenuHintergrund.png");
		JLabel backgroundImage = new JLabel(image);
		backgroundImage.setBounds(0, 5, 1000, 750);

		JButton joinRoomButton = createButton("Raum beitreten", e -> connect());
		joinRoomButton.setBounds(30, 100, 150, 45);
		mainPanel.add(joinRoomButton);
		
		JButton readyButton = createButton("Bereit", e -> ready());
		readyButton.setBounds(30, 155, 150, 45);
		mainPanel.add(readyButton);
		
		ipAdressField = new JTextField("");
		ipAdressField.setBounds(190, 100, 150, 45);
		ipAdressField.setBackground(buttonBackgroundDisabled);
		ipAdressField.setForeground(Color.WHITE);
		ipAdressField.setToolTipText("IP Adresse vom Raum Ersteller, leer lassen für lokalen Host");
		mainPanel.add(ipAdressField);

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
