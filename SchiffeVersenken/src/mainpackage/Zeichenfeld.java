package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Zeichenfeld extends JPanel {

	private transient Model model;
	private Game game;
	private int squareSize = 50;
	private int startDelta = 30;
	private boolean buttonsCreated = false;
	private boolean animationIsRunning = false;
	private static BufferedImage backgroundImage;
	
	private JPanel buttonPanel;

	public Zeichenfeld(Model model, Game game) {
		super();
		this.model = model;
		this.game = game;
		this.setLayout(null);
		try {
			backgroundImage = ImageIO.read(new File("media/images/schiffMenuHintergrundErweitert.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		if (animationIsRunning) {
			return;
		}

		g.clearRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(backgroundImage, 0, 0, null);

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
		buttonPanel = new JPanel();
		buttonPanel.setBounds(100, 0, 340, 61);
		buttonPanel.setBackground(new Color(25, 30, 40));

		JButton verticalButton = new JButton("Vertikal");
		JButton horizontalButton = new JButton("Horizontal");

		verticalButton.setBounds(0, 20, 120, 40);
		verticalButton.setEnabled(false);
		verticalButton.addActionListener(e -> {
			verticalButton.setEnabled(false);
			horizontalButton.setEnabled(true);
			game.setVertical(true);
		});

		horizontalButton.setBounds(140, 20, 120, 40);
		horizontalButton.setEnabled(true);
		horizontalButton.addActionListener(e -> {
			verticalButton.setEnabled(true);
			horizontalButton.setEnabled(false);
			game.setVertical(false);
		});

		buttonPanel.add(verticalButton);
		buttonPanel.add(horizontalButton);
		buttonPanel.setLayout(null);
		buttonPanel.setVisible(true);
		this.add(buttonPanel);
	}

	public void startAnimation(int animation) {
		animationIsRunning = true;
		buttonPanel.setVisible(false);
		
		JPanel animationPanel = new JPanel();
		animationPanel.setBounds(0, 0, 1300, 700);
		this.add(animationPanel);
		
		ImageIcon image = new ImageIcon();
		int animationTime = 2600;
		switch (animation) {
		case 1:
			 image = new ImageIcon("media/images/schiffVersenkt.gif");
			 animationTime = 2600;
			break;
		case 2:
			 image = new ImageIcon("media/images/gewonnen3.gif");
			 animationTime = 6000;
			 break;
		default:
			break;
		}
		
		JLabel animationLabel = new JLabel(image);
		animationLabel.setBounds(0, 20, 1300, 650);
		animationLabel.setVisible(true);
		animationPanel.add(animationLabel);
		
		try {
			Thread.sleep(animationTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		animationPanel.remove(animationLabel);
		animationPanel.setVisible(false);
		buttonPanel.setVisible(true);
		animationIsRunning = false;
		repaint();
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

	/**
	 * @param g
	 * @param map
	 * @param mapOffsetX
	 * 
	 * Displays oppponents ships on the ship map, used for debugging.
	 */
	@SuppressWarnings("unused")
	private void paintShootMapDEBUG(Graphics g, CellType[][] map, int mapOffsetX) {
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

	private void paintCell(Graphics g, Color color, int x, int y, int cellOffsetX) {
		g.setColor(color);
		g.fillRect(cellOffsetX + squareSize * x, startDelta + squareSize * y, squareSize - 1, squareSize - 1);
	}

}
