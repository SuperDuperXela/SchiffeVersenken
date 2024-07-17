package mainpackage;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ViewGraphics {

	private JFrame frame = new JFrame("Schiffe Versenken");
	private Zeichenfeld zeichenfeld;

	private Model model;
	private Game game;

	public ViewGraphics(Model model, Game game) {
		this.model = model;
		this.game = game;

		createWindow();
	}

	private void createWindow() {
		SwingUtilities.invokeLater(() -> {
			frame.setSize(1300, 700);
			zeichenfeld = new Zeichenfeld(model, game);
			frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			frame.add(zeichenfeld);

//			JPanel backgroundPanel = new JPanel();
//			backgroundPanel.setBounds(0, 0, 1300, 700);
//			backgroundPanel.setBackground(new Color(25, 30, 40));
//			
//			ImageIcon image = new ImageIcon("media/images/schiffMenuHintergrund.png");
//			JLabel backgroundImage = new JLabel(image);
//			backgroundImage.setBounds(0, 5, 1000, 750);
//			backgroundPanel.add(backgroundImage);
//			
//			frame.add(backgroundPanel);

			frame.setVisible(true);
			frame.requestFocus();
		});
	}

	public void addController(Controller controller) {
		SwingUtilities.invokeLater(() -> frame.addMouseListener(controller));
	}

	public void refreshGraphics() {
		SwingUtilities.invokeLater(() -> zeichenfeld.repaint());
	}

	public void startAnimation(int animation) {
		zeichenfeld.startAnimation(animation);
	}
}
