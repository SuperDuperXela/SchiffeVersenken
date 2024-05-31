package mainpackage;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Zeichenfeld extends JPanel {
	
	private static final Color BUTTON_OUTLINE = new Color(50, 50, 50);
	private static final Color BUTTON_OUTLINE_PRESSED = new Color(100, 50, 50);
	private static final Color BUTTON_FILL= new Color(150, 150, 150);
	private static final Color BUTTON_FILL_PRESSED= new Color(120, 120, 120);

	private transient Model model;
	private Game game;
	private int squareSize = 50;
	private int startDelta = 30;
	private boolean buttonsCreated = false;

	public Zeichenfeld(Model model, Game game) {
		super();
		this.model = model;
		this.game = game;
		this.setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {

		CellType[][] map = model.getViewMap(0);
		CellType[][] map2 = model.getViewMap(1);
		int mapOffsetX = squareSize * (model.getMapSize() + 2);
		
		
		paintShipMap(g, map, 0);
		paintShootMap(g, map2, mapOffsetX);
		if (!buttonsCreated) {
			createButtons();
			buttonsCreated = true;
		}
	}
	
	private void createButtons() {
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1500, 70);
		
		JButton verticalButton = new JButton("Vertikal");
		JButton horizontalButton = new JButton("Horizontal");
		
		verticalButton.setBounds(80, 20, 120, 40);
		verticalButton.setEnabled(false);
		verticalButton.addActionListener(e -> {
			verticalButton.setEnabled(false);
			horizontalButton.setEnabled(true);
			game.setVertical(true);
		});
		
		horizontalButton.setBounds(220, 20, 120, 40);
		horizontalButton.setEnabled(true);
		horizontalButton.addActionListener(e -> {
			verticalButton.setEnabled(true);
			horizontalButton.setEnabled(false);
			game.setVertical(false);
		});
		
		panel.add(verticalButton);
		panel.add(horizontalButton);
		panel.setLayout(null);
		panel.setVisible(true);
		this.add(panel);
	}
	
	private void paintButtons(Graphics g) {
			
		/*
		g.setColor(BUTTON_FILL);
		g.fillRect(80, 20, 120, 50);
		g.setColor(BUTTON_OUTLINE);
		g.drawRect(80, 20, 120, 50);
		

		g.setColor(BUTTON_OUTLINE_PRESSED);
		g.fillRect(280 - 2, 20 - 2, 120 + 4, 50 + 4);
		g.setColor(BUTTON_FILL_PRESSED);
		g.fillRect(280, 20, 120, 50);
		*/
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
