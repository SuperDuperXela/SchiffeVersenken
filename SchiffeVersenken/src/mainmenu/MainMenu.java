package mainmenu;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu {

	private static final int WINDOW_WIDTH = 385;
	private static final int WINDOW_HEIGHT = 600;
	private static final Color buttonBackgroundEnabled = new Color(44, 70, 120);
	private static final Color buttonBackgroundDisabled = new Color(70, 70, 70);

	public static void main(String[] args) {
		JFrame frame = new JFrame("Schiffe Versenken (extreme p2w)");
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(10, 110, 350, 250);
		mainPanel.setLayout(new GridLayout(5, 1, 0, 10));
		mainPanel.setBackground(new Color(25, 30, 40));
		
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBounds(-10, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		backgroundPanel.setBackground(new Color(25, 30, 40));

		ImageIcon image = new ImageIcon("media/images/schiffMenuHintergrundKlein.png");
		JLabel backgroundImage = new JLabel(image);
		backgroundPanel.add(backgroundImage);

		JButton quickPlayButton = createButton("Schnelles Spiel", "Such ein Spiel gegen einen anderen Spieler!",
				e -> new QuickPlay());
		JButton joinRoomButton = createButton("Raum beitreten", "Trete einen Mehrspieler Raum bei!",
				e -> new JoinRoomMenu());
		JButton createRoomButton = createButton("Raum erstellen", "Erstelle einen Mehrspieler Raum!",
				e -> new CreateRoomMenu());
		JButton settingsButton = createButton("Einstellungen", "Ändere Optionen", e -> new SettingsMenu());
		JButton creditsButton = createButton("Mitwirkende", "Bestaune die Ersteller dieses Programms",
				e -> new CreditsMenu());
		JButton exitButton = createButton("Spiel verlassen", "Beendet das Programm", e -> System.exit(0));

		// Deaktivierung der Knöpfe, da die noch nicht implementiert sind.
		quickPlayButton.setEnabled(false);
		quickPlayButton.setBackground(buttonBackgroundDisabled);
		settingsButton.setEnabled(false);
		settingsButton.setBackground(buttonBackgroundDisabled);
		creditsButton.setEnabled(false);
		creditsButton.setBackground(buttonBackgroundDisabled);

		JComponent[] components = { createRow(new JButton[] { quickPlayButton }), //
				createRow(new JButton[] { joinRoomButton, createRoomButton }), //
				createRow(new JButton[] { settingsButton }), //
				createRow(new JButton[] { creditsButton, exitButton }) //
		};
		for (JComponent component : components) {
			mainPanel.add(component);
		}

		frame.add(mainPanel);
		frame.add(backgroundPanel);
		frame.setVisible(true);
	}

	private static JButton createButton(String buttonText, String toolTip, ActionListener listener) {
		JButton button = new JButton(buttonText);
		button.setToolTipText(toolTip);
		button.addActionListener(listener);
		
		button.setBackground(buttonBackgroundEnabled);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		
		return button;
	}

	private static JPanel createRow(JButton[] components) {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(25, 30, 40));

		if (components != null) {
			panel.setLayout(new GridLayout(1, components.length, 10, 0));
			for (JComponent component : components) {
				panel.add(component);
			}
		}
		return panel;
	}

}
