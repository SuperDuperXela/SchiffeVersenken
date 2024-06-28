package mainpackage;

public class View {

	private Model model;

	private static final String COORDINATESLETTERS = "";

	public View(Model model) {
		this.model = model;
	}

	/**
	 * prints all maps for a player
	 * 
	 * @param n player ID
	 */
	public void printAll(int n, int m) {
		System.out.println("Gegnerische Karte:");
		printShootMap(m);
		System.out.println("\nEigene Karte:");
		printShipMap(n);
	}

	/**
	 * prints a players ship map to the console
	 * 
	 * @param n player ID
	 */
	public void printShipMap(int n) {
		System.out.println(COORDINATESLETTERS);
		for (int i = 0; i < model.getViewMap(n).length; i++) {
			if (i != 9) {
				System.out.print((i + 1) + " ");
			} else {
				System.out.print("X");
			}
			for (int j = 0; j < model.getViewMap(n).length; j++) {
				System.out.print(model.getViewMap(n)[j][i].character + " ");
			}
			System.out.println();
		}
	}

	/**
	 * prints a players shoot map to the console
	 * 
	 * @param n player ID
	 */
	public void printShootMap(int n) {
		System.out.println(COORDINATESLETTERS);
		for (int i = 0; i < model.getViewMap(n).length; i++) {
			if (i != 9) {
				System.out.print((i + 1) + " ");
			} else {
				System.out.print("X");
			}
			for (int j = 0; j < model.getViewMap(n).length; j++) {
				if (model.getViewMap(n)[j][i] == CellType.SHIP) {
					System.out.print('\u25A2' + " ");
				} else {
					System.out.print(model.getViewMap(n)[j][i].character + " ");
				}
			}
			System.out.println();
		}
	}
}
