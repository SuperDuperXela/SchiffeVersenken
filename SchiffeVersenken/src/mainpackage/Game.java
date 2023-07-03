package mainpackage;

import java.util.HashMap;
import java.util.Scanner;

public class Game {

	private Model model;
	private View view;

	private Scanner scanner = new Scanner(System.in);

	private static final int[] shipsLength = { 5, 4, 4, 3, 3, 3, 2, 2, 2, 2 };

	private static HashMap<Character, Integer> coordinatesMap = new HashMap<>();

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

		// int[][] zahlen = {{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}};
		// System.out.print(zahlen[1][0]);

		// for (int i = 0; i < 4; i++) {
		// for (int j = 0; j < 4; j++) {
		// System.out.print(zahlen[i][j] + " ");
		// }
		// System.out.println();
		// }

	}

	/**
	 * displays the main menu
	 */
	public void menu() {

		// commandline prototyp
		System.out.println("1) PVP starten\n" + "2) PVE starten\n" + "3) Spielstand laden\n" + "4) Beenden");

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
		default:
			scanner.close();
			break;

		}
	}

	private void startPVP() {
		placeShipsPhase(0);
		System.out.print("Spieler 1, alle Schiffe platziert!\n \n \n \n \n \n \n \n \n \n \n");

		// TODO: Konsole leeren?

		placeShipsPhase(1);
		// Spieler 1 / 2 abwechselnd am Zug
	}

	private void startPVE() {
		// spiel mit bot starten
	}

	private void loadGame() {
		// vorherigen spielstand laden
		// nur PVE oder PVP verbindung widerherstellen?
	}

	public void SaveAndExit() {
		// Spielstand speichern (in json datei?) und programm schließen
		// wenn json schon für spielstände genutzt wird könnte man auch eine
		// config.json einbauen
		// dort zbsp spielfeldgröße, schiffe (anzahl/längen) o.ä. hinterlegen
	}

	private void placeShipsPhase(int n) {
		for (int length : shipsLength) {
			boolean loop = true;
			while (loop) {
				int[] coordinates = readInput();
				System.out.println("Ausrichtung eingeben (0 Waagerecht, 1 Senkrecht): ");
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
	}

	/**
	 * requests and validates input
	 * 
	 * @return formatted coordintates
	 */
	private int[] readInput() {
		System.out.println("Koordinaten eingeben: ");
		String input = scanner.next();

		int[] coordinates = new int[2];

		if (!coordinatesMap.containsKey(input.charAt(0))) {
			// meckern
		}
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
	 * @param n player ID
	 * @param x x coordinate of the ships first segment
	 * @param y x coordinate of the ships first segment
	 * @param length length of the ship in segments
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

}
