package mainpackage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Zeichenfeld extends JPanel {

	private transient Model model;
	private int squareSize = 50;
	private int startDelta = 30;

	public Zeichenfeld(Model model) {
		super();
		this.model = model;
	}

	@Override
	public void paintComponent(Graphics g) {

		CellType[][] map = model.getViewMap(0);
		CellType[][] map2 = model.getViewMap(1);
		int mapOffsetX = squareSize * (model.getMapSize() + 2);
		paintShipMap(g, map, 0);
		paintShootMap(g, map2, mapOffsetX);
	}

	private void paintShipMap(Graphics g, CellType[][] map, int mapOffsetX) {
		int cellOffsetX = startDelta + mapOffsetX;
		int x = 0;
		for (CellType[] row : map) {
			x += 1;
			int y = 0;
			for (CellType cell : row) {
				y += 1;
				g.setColor(Color.BLACK);
				g.drawRect(cellOffsetX + squareSize * x - 1, startDelta + squareSize * y - 1, squareSize, squareSize);
				switch (cell) {
				case SHIP:
					paintCell(g, Color.YELLOW, x, y, cellOffsetX);
					break;
				case SHOT_SHIP:
					paintCell(g, Color.DARK_GRAY, x, y, cellOffsetX);
					break;
				case SHOT_WATER:
					paintCell(g, Color.CYAN, x, y, cellOffsetX);
					break;
				case SUNKEN_SHIP:
					paintCell(g, Color.RED, x, y, cellOffsetX);
					break;
				case WATER:
					paintCell(g, Color.BLUE, x, y, cellOffsetX);
					break;
				default:
					break;
				}
			}
		}
	}

	private void paintShootMap(Graphics g, CellType[][] map, int mapOffsetX) {
		int cellOffsetX = startDelta + mapOffsetX;
		int x = 0;
		for (CellType[] row : map) {
			x += 1;
			int y = 0;
			for (CellType cell : row) {
				y += 1;
				g.setColor(Color.BLACK);
				g.drawRect(cellOffsetX + squareSize * x - 1, startDelta + squareSize * y - 1, squareSize, squareSize);
				switch (cell) {
				case SHIP:
					paintCell(g, Color.BLUE, x, y, cellOffsetX);
					break;
				case SHOT_SHIP:
					paintCell(g, Color.DARK_GRAY, x, y, cellOffsetX);
					break;
				case SHOT_WATER:
					paintCell(g, Color.CYAN, x, y, cellOffsetX);
					break;
				case SUNKEN_SHIP:
					paintCell(g, Color.RED, x, y, cellOffsetX);
					break;
				case WATER:
					paintCell(g, Color.BLUE, x, y, cellOffsetX);
					break;
				default:
					break;
				}
			}
		}
	}

	private void paintCell(Graphics g, Color color, int x, int y, int cellOffsetX) {
		g.setColor(color);
		g.fillRect(cellOffsetX + squareSize * x, startDelta + squareSize * y, squareSize - 1, squareSize - 1);
	}

}
