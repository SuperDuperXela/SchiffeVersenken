package mainpackage;

import javax.swing.JFrame;
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
			frame.setSize(1500, 700);
			zeichenfeld = new Zeichenfeld(model);
			frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
			frame.add(zeichenfeld);
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
}
