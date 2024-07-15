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

	public static void main(String[] args) {
		JFrame frame = new JFrame("Schiffe Versenken (extreme p2w)");
		frame.setSize(385, 700);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(10, 10, 350, 250);
		mainPanel.setLayout(new GridLayout(5, 1, 0, 10));
		mainPanel.setBackground(new Color(255, 255, 255));

		ImageIcon image = new ImageIcon("media/images/TestBildMainMenu.png");
		JLabel backgroundImage = new JLabel(image);
		backgroundImage.setBounds(0, 5, 375, 700);
		backgroundImage.setVisible(true);

		JButton quickPlayButton = createButton("Schnelles Spiel", "Such ein Spiel gegen einen anderen Spieler!",
				e -> new QuickPlay());
		JButton joinRoomButton = createButton("Raum beitreten", "Trete einen Mehrspieler Raum bei!", e -> new JoinRoomMenu());
		JButton createRoomButton = createButton("Raum erstellen", "Erstelle einen Mehrspieler Raum!", e -> new CreateRoomMenu());
		JButton settingsButton = createButton("Einstellungen", "Ändere Optionen", e -> new SettingsMenu());
		JButton creditsButton = createButton("Abspann", "Bestaune die Ersteller dieses Programms",
				e -> new CreditsMenu());
		JButton exitButton = createButton("Spiel verlassen", "Beendet das Programm", e -> System.exit(0));

		// Deaktivierung der Knöpfe, da die noch nicht implementiert sind.
		quickPlayButton.setEnabled(false);
		settingsButton.setEnabled(false);
		creditsButton.setEnabled(false);

		JComponent[] components = { createRow(new JButton[] { quickPlayButton }), //
				createRow(new JButton[] { joinRoomButton, createRoomButton }), //
				createRow(new JButton[] { settingsButton }), //
				createRow(new JButton[] { creditsButton, exitButton }) //
		};
		for (JComponent component : components) {
			mainPanel.add(component);
		}

		frame.add(mainPanel);
		frame.add(backgroundImage);
		frame.setVisible(true);
	}

	private static JButton createButton(String buttonText, String toolTip, ActionListener listener) {
		JButton button = new JButton(buttonText);
		button.setToolTipText(toolTip);
		button.addActionListener(listener);
		return button;
	}

	private static JPanel createRow(JButton[] components) {
		JPanel panel = new JPanel();

		if (components != null) {
			panel.setLayout(new GridLayout(1, components.length, 10, 0));
			for (JComponent component : components) {
				panel.add(component);
			}
		}
		return panel;
	}

}
