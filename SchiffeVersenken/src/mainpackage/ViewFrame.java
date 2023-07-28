package mainpackage;

import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ViewFrame {

	private static final int BUTTONSIZE = 20;
	private static final Insets INSETS = new Insets(0, 0, 0, 0);
	private int mapSize = 10;
	private List<JButton[][]> buttons = new ArrayList<>();
	private Model model;
	private Game game;
	private int[] lastButton;

	public ViewFrame(Model model, Game game) {
		this.model = model;
		this.game = game;

		JFrame frame = new JFrame("Schiffe Versenken");
		frame.setSize(1500, 700);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		mapSize = model.getMapSize();
		buttons.add(new JButton[mapSize][mapSize]);
		buttons.add(new JButton[mapSize][mapSize]);

		JPanel ownMap = new JPanel();
		ownMap.setBounds(10, 10, 500, 500);
		ownMap.setLayout(new GridLayout(10, 10, 7, 7));
		generateButtons(ownMap, 0);

		JPanel enemyMap = new JPanel();
		enemyMap.setBounds(650, 10, 500, 500);
		enemyMap.setLayout(new GridLayout(10, 10, 7, 7));
		generateButtons(enemyMap, 1);

		frame.add(ownMap);
		frame.add(enemyMap);
		frame.setVisible(true);
	}

	private void generateButtons(JPanel panel, int n) {

		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				panel.add(createButton(j, i, n));
			}
		}
	}

	private JButton createButton(int x, int y, int n) {
		JButton button = new JButton();
		button.setText(model.getViewMap(n)[x][y].character + "");
		button.setSize(BUTTONSIZE, BUTTONSIZE);
		button.setMargin(INSETS);
		button.addActionListener(e -> sendtest(x, y, n));
		buttons.get(n)[x][y] = button;
		return button;
	}

	private synchronized void sendtest(int x, int y, int n) {
		lastButton = new int[] { x, y };
		System.out.println("x: " + x + " |y: " + y + " |n: " + n);

		game.setWait(false);
		
		refreshAllButtons();
	}

	public int[] getLastButton() {
		return lastButton;
	}

	private void refreshAllButtons() {
		int n = -1;
		for (JButton[][] buttonArrays : buttons) {
			n++;
			int y = -1;
			for (JButton[] buttonRow : buttonArrays) {
				y ++;
				int x = -1;
				for (JButton button : buttonRow) {
					x ++;
					button.setText(model.getViewMap(n)[y][x].character + "");
				}
			}
		}
	}
}
