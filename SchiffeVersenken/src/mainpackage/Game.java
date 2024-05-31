package mainpackage;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {

	private Model model;
	private View view;

	private Scanner scanner = new Scanner(System.in);

	private static final int[] SHIPSLENGTH = { 5, 4 };
	private static final int[] SHIPSLENGTHFULL = { 5, 4, 4, 3, 3, 3, 2, 2, 2, 2 };

	private static HashMap<Character, Integer> coordinatesMap = new HashMap<>();

	private boolean wait = true;
	private AtomicBoolean stop = new AtomicBoolean(false);
	private AtomicBoolean waitForCellClick = new AtomicBoolean(true);

	private int xCell; // x coordinate of last clicked cell
	private int yCell;

	private boolean vertical = true; // vertical / horizontal placement

	public Game(Model model, View view) {
		this.model = model;
		this.view = view;

		coordinatesMap.put('a', 1);
		coordinatesMap.put('b', 2);
		coordinatesMap.put('c', 3);
		coordinatesMap.put('d', 4);
		coordinatesMap.put('e', 5);
		coordinatesMap.put('f', 6);
		coordinatesMap.put('g', 7);
		coordinatesMap.put('h', 8);
		coordinatesMap.put('i', 9);
		coordinatesMap.put('j', 10);
	}

	/**
	 * test for basic functions like adding ships and shots
	 */
	public void test() {

		Ship s1 = new Ship(0, 0, 3, true);
		model.addShip(0, 0, 0, s1);
		model.addShip(0, 0, 1, s1);
		model.addShip(0, 0, 2, s1);

		model.addShot(0, 0, 0);
		model.addShot(0, 0, 1);
		model.addShot(0, 5, 2);

		view.printShipMap(0);
	}

	/**
	 * displays the main menu
	 */
	public void menu() {

		// commandline prototyp
		System.out.println("1) PVP starten\n" + "2) PVE starten\n" + "3) Spielstand laden\n" + "4) viewFrame Test\n"
				+ "5) viewGraphics Test\n");

		String menuChoice = scanner.next();

		switch (menuChoice) {
		case "1":
			startPVP();
			break;
		case "2":
			startPVE();
			break;
		case "3":
			loadGame();
			break;
		case "4":
			viewFrame();
			break;
		case "5":
			Runnable runnable = () -> viewGraphics();
			new Thread(runnable).start();
			startPVP();
			break;
		default:
			scanner.close();
			break;

		}
	}

	private void startPVP() {
		view.printShipMap(0);
		placeShipsPhase(0);
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		placeShipsPhase(1);
		view.printShipMap(1);
		// Spieler 1 / 2 abwechselnd am Zug
		int active = 0;
		int opponent = 1;
		boolean gameEnd = false;
		String fail = "";

		while (!gameEnd) {
			int[] coord;
			do {
				view.printAll(active, opponent);
				System.out.println("Koordinaten eingeben (a - j, 1 - 10): " + fail);
				fail = " Ung�ltige Eingabe!";
				coord = readInput();
			} while (model.getViewMap(opponent)[coord[0]][coord[1]] == CellType.SHOT_WATER
					|| model.getViewMap(opponent)[coord[0]][coord[1]] == CellType.SHOT_SHIP
					|| model.getViewMap(opponent)[coord[0]][coord[1]] == CellType.SUNKEN_SHIP);

			printHitMessage(model.getViewMap(opponent)[coord[0]][coord[1]]);
			if (model.addShot(opponent, coord)) {
				// �berpr�fe ob Spieler gewonnen hat
				int counter = 0;

				for (Ship ship : model.getShipLists(opponent)) {
					if (ship.isSunken()) {
						counter += 1;
					}
				}
				if (counter == model.getShipLists(opponent).size()) {
					// Spieler hat gewonnen
					gameEnd = true;
					System.out.print("Spieler " + (active + 1) + " hat gewonnen!");
				}
			}
			switchPlayerPhase(opponent);
			int h = active;
			active = opponent;
			opponent = h;
			fail = "";
		}

	}

	private void startPVE() {
		// spiel mit bot starten
	}

	private void loadGame() {
		// vorherigen spielstand laden
		// nur PVE oder PVP verbindung widerherstellen?
	}

	public void saveAndExit() {
		// Spielstand speichern (in json datei?) und programm schließen
		// wenn json schon für spielstände genutzt wird könnte man auch eine
		// config.json einbauen
		// dort zbsp spielfeldgröße, schiffe (anzahl/längen) o.ä. hinterlegen
	}

	private void placeShipsPhase(int n) {
		System.out.println("Spieler " + (n + 1) + ", platziere deine Schiffe!");
		for (int length : SHIPSLENGTH) {
			boolean loop = true;
			while (loop) {
				printShipNameMessage(length);
				System.out.println("Koordinaten eingeben (a - j, 1 - 10): ");
				int[] coordinates = readInput();
				System.out.println("Ausrichtung eingeben (1 Senkrecht, 2 Waagerecht): ");
				int input = scanner.nextInt();
				Boolean vertical = (input == 1);

				if (placeShip(n, coordinates[0], coordinates[1], length, vertical)) {
					System.out.println("Ung�ltige Position!");
				} else {
					loop = false;
				}
			}
			view.printShipMap(n);
		}
		System.out.print("Spieler " + (n + 1) + ", alle Schiffe platziert!\n \n \n \n \n \n \n \n \n \n \n");
	}

	/**
	 * requests and validates input
	 * 
	 * @return formatted coordintates
	 */
	private int[] readInput() {
		int[] coordinates = new int[2];
		String input;
		do {

			input = scanner.next();
		} while (!coordinatesMap.containsKey(input.charAt(0)));

		coordinates[0] = coordinatesMap.get(input.charAt(0)) - 1;
		if (input.length() > 3) {
			// meckern
		} else if (input.length() == 3) {
			coordinates[1] = 9;
		} else {
			coordinates[1] = Character.getNumericValue(input.charAt(1)) - 1;
		}

		return coordinates;
	}

	/**
	 * places a ship at the given coordinates
	 * 
	 * @param n        player ID
	 * @param x        x coordinate of the ships first segment
	 * @param y        x coordinate of the ships first segment
	 * @param length   length of the ship in segments
	 * @param vertical true if the ship is vertical
	 * 
	 * @return false
	 */
	private boolean placeShip(int n, int x, int y, int length, boolean vertical) {
		Ship ship = new Ship(x, y, length, vertical);

		int size = model.getMapSize() - 1;

		if (vertical) {
			if (y + length > size + 1) {
				return true;
			}
		} else {
			if (x + length > size + 1) {
				return true;
			}
		}

		for (int i = 0; i < length; i++) {
			if (model.getShipMap(n)[ship.getXSegments()[i]][ship.getYSegments()[i]] != null) {
				return true;
			}
			if (model.getShipMap(n)[Math.max(0, ship.getXSegments()[i] - 1)][ship.getYSegments()[i]] != null) {
				return true;
			}
			if (model.getShipMap(n)[Math.min(size, ship.getXSegments()[i] + 1)][ship.getYSegments()[i]] != null) {
				return true;
			}
			if (model.getShipMap(n)[ship.getXSegments()[i]][Math.max(0, ship.getYSegments()[i] - 1)] != null) {
				return true;
			}
			if (model.getShipMap(n)[ship.getXSegments()[i]][Math.min(size, ship.getYSegments()[i] + 1)] != null) {
				return true;
			}
		}

		for (int i = 0; i < length; i++) {
			model.addShip(n, ship.getXSegments()[i], ship.getYSegments()[i], ship);
		}

		return false;
	}

	private void printShipNameMessage(int length) {
		switch (length) {
		case 5:
			System.out.println("Schlachtschiff (5 K�stchen)");
			break;
		case 4:
			System.out.println("Kreuzer (4 K�stchen)");
			break;
		case 3:
			System.out.println("Zerst�rer (3 K�stchen)");
			break;
		case 2:
			System.out.println("U-Boot (2 K�stchen)");
			break;
		default:
			break;
		}
	}

	private void printHitMessage(CellType cell) {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		String text = "";
		switch (cell) {
		case SHIP:
			text = "Schiff";
			break;
		case WATER:
			text = "Wasser";
			break;
		default:
			break;
		}
		System.out.println("Du hast " + text + " getroffen!");
	}

	private void switchPlayerPhase(int opponent) {
		System.out.println("Spieler " + (opponent + 1) + " ist am Zug!");
		scanner.next();
	}

	private void viewFrame() {
		ViewFrame viewFrame = new ViewFrame(model, this);

		placeShipsPhaseFrame(0, viewFrame);
		placeShipsPhaseFrame(1, viewFrame);
		// Spieler 1 / 2 abwechselnd am Zug
		int active = 0;
		int opponent = 1;
		boolean gameEnd = false;

		while (!gameEnd) {
			int[] coord;
			do {
				coord = readInputFrame(viewFrame);
			} while (model.getViewMap(opponent)[coord[0]][coord[1]] == CellType.SHOT_WATER
					|| model.getViewMap(opponent)[coord[0]][coord[1]] == CellType.SHOT_SHIP
					|| model.getViewMap(opponent)[coord[0]][coord[1]] == CellType.SUNKEN_SHIP);

			if (model.addShot(opponent, coord)) {
				// �berpr�fe ob Spieler gewonnen hat
				int counter = 0;

				for (Ship ship : model.getShipLists(opponent)) {
					if (ship.isSunken()) {
						counter += 1;
					}
				}
				if (counter == model.getShipLists(opponent).size()) {
					// Spieler hat gewonnen
					gameEnd = true;
					System.out.print("Spieler " + (active + 1) + " hat gewonnen!");
				}
			}
			switchPlayerPhaseFrame(opponent);
			int h = active;
			active = opponent;
			opponent = h;
		}
	}

	private void placeShipsPhaseFrame(int n, ViewFrame viewFrame) {
		System.out.println("Spieler " + (n + 1) + ", platziere deine Schiffe!");
		for (int length : SHIPSLENGTH) {
			boolean loop = true;
			while (loop) {
				printShipNameMessage(length);
				System.out.println("Feld anklicken!");
				int[] coordinates = readInputFrame(viewFrame);

				System.out.println("Ausrichtung eingeben (1 Senkrecht, 2 Waagerecht): ");
//				int input = scanner.nextInt();
				int input = 1;
				Boolean vertical = (input == 1);

				if (placeShip(n, coordinates[0], coordinates[1], length, vertical)) {
					System.out.println("Ung�ltige Position!");
				} else {
					loop = false;
				}
			}
		}
	}

	private synchronized int[] readInputFrame(ViewFrame viewFrame) {
		try {
			while (wait) {
				this.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		wait = true;
		return viewFrame.getLastButton();
	}

	private void switchPlayerPhaseFrame(int opponent) {
		System.out.println("Spieler " + (opponent + 1) + " ist am Zug!");
	}

	public synchronized void setWait(boolean b) {
		wait = b;
		notifyAll();
	}

	private void viewGraphics() {
		ViewGraphics viewGraphics = new ViewGraphics(model, this);
		viewGraphics.addController(new Controller(this));

		Runnable runnable = () -> {
			while (!stop.get()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}

				viewGraphics.refreshGraphics();
			}
		};
		new Thread(runnable).start();

		placeShipsPhaseGraphics(0, viewGraphics);

		placeShipsPhaseGraphics(1, viewGraphics);

		while (!stop.get()) {

			boolean loop = true;

			while (loop) {
				System.out.println("Feld anklicken!");
				while (waitForCellClick.get()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
				waitForCellClick.set(true);

				loop = (model.getViewMap(1)[xCell][yCell] == CellType.SHOT_WATER
						|| model.getViewMap(1)[xCell][yCell] == CellType.SHOT_SHIP
						|| model.getViewMap(1)[xCell][yCell] == CellType.SUNKEN_SHIP);

			}

			if (model.addShot(1, xCell, yCell)) {
				// �berpr�fe ob Spieler gewonnen hat
				int counter = 0;

				for (Ship ship : model.getShipLists(1)) {
					if (ship.isSunken()) {
						counter += 1;
					}
				}
				if (counter == model.getShipLists(1).size()) {
					// Spieler hat gewonnen
					stop.set(true);
				}
			}
		}

	}

	private void placeShipsPhaseGraphics(int n, ViewGraphics viewGraphics) {
		System.out.println("Spieler " + (n + 1) + ", platziere deine Schiffe!");
		for (int length : SHIPSLENGTH) {
			boolean loop = true;
			while (loop) {
				printShipNameMessage(length);
				System.out.println("Feld anklicken!");
				while (waitForCellClick.get()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
				waitForCellClick.set(true);
				int xc = xCell;
				int xy = yCell;
				System.out.println("Ausrichtung eingeben (1 Senkrecht, 2 Waagerecht): ");

				if (placeShip(n, xc, xy, length, vertical)) {
					System.out.println("Ung�ltige Position!");
				} else {
					loop = false;
				}
			}
		}
	}

	/**
	 * @param x
	 * @param y Rechnet die X und Y Koordinaten vom Fenster in Zellen-Koordinaten
	 *          um, und speichert diese ab. Verwendet im graphics Modus
	 */
	public void updateLastClick(int x, int y) {
		x = (x - 88) / 50;
		y = (y - 117) / 50;
		xCell = x;
		yCell = y;
		if (xCell < model.getMapSize() && yCell < model.getMapSize() && xCell >= 0 && yCell >= 0)
			waitForCellClick.set(false);
		System.out.println("Xc: " + x + "  Yc: " + y);
	}

	/**
	 * @param bool �ndert den Wert von vertical, welches bestimmt, wie die Schiffe
	 *             platziert werden im graphics Modus
	 */
	public void setVertical(boolean bool) {
		vertical = bool;
	}
}
