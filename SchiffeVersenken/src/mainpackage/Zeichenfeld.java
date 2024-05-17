package mainpackage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Zeichenfeld extends JPanel {

	private Model model;
	private int squareSize = 50;
	private int startDelta = 30;

	public Zeichenfeld(Model model) {
		super();
		this.model = model;
	}

	@Override
	public void paintComponent(Graphics g) {

		CellType[][] map = model.getViewMap(0);

		int x = 0;
		for (CellType[] row : map) {
			x += 1;
			int y = 0;
			for (CellType cell : row) {
				y += 1;
				g.setColor(Color.BLACK);
				g.drawRect(startDelta + squareSize * x - 1, startDelta + squareSize * y - 1, squareSize, squareSize);
				switch (cell) {
				case SHIP:
					g.setColor(Color.YELLOW);
					g.fillRect(startDelta + squareSize * x, startDelta + squareSize * y, squareSize - 1,
							squareSize - 1);
					break;
				case SHOT_SHIP:
					break;
				case SHOT_WATER:
					break;
				case SUNKEN_SHIP:
					break;
				case WATER:
					g.setColor(Color.BLUE);
					g.fillRect(startDelta + squareSize * x, startDelta + squareSize * y, squareSize - 1,
							squareSize - 1);
					break;
				default:
					break;

				}
			}
		}
	}

}
