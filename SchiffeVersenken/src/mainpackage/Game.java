package mainpackage;

import java.util.Scanner;

public class Game {

	private Model model;
	private View view;

	public Game(Model model, View view) {
		this.model = model;
		this.view = view;
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
		boolean loop = true;
		while (loop) {
			// menü ausgeben und eingabe anfordern,
			// while (true) damit das menü nach spielende wieder geöffnet wird

			// commandline prototyp
			System.out.println("1) PVP starten\n" + "2) PVE starten\n" + "3) Spielstand laden\n" + "4) Beenden");

			Scanner menuInput = new Scanner(System.in);
			String menuChoice = menuInput.next();

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
				menuInput.close();
				loop = false;
				break;
			}
		}
	}

	private void startPVP() {
		// mit spieler verbinden und spiel starten
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
		// wenn json schon für spielstände genutzt wird könte man auch eine
		// config.json einbauen
		// dort zbsp spielfeldgröße, schiffe (anzahl/längen) o.ä. hinterlegen
	}

}
