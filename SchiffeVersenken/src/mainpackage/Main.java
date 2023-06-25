package mainpackage;

public class Main {

	public static void main(String[] args) {

		Model model = new Model();
		
		Ship s1 = new Ship(0, 0, 3, true);
		model.addShip(0, 0, 0, s1);
		model.addShip(0, 0, 1, s1);
		model.addShip(0, 0, 2, s1);
		
		model.addShot(0, 0, 1);
		model.addShot(0, 1, 0);
		
//		int[][] zahlen = {{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}};
//		System.out.print(zahlen[1][0]);
		
		for (int i = 0; i < model.getViewMap(0).length; i++) {
			for (int j = 0; j < model.getViewMap(0).length; j++) {
				if (model.getViewMap(0)[j][i] == null) {
					System.out.print("__ ");
				} else {
					System.out.print(model.getViewMap(0)[j][i] + " ");
				}
			}
			System.out.println();
		}
		
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//					System.out.print(zahlen[i][j] + " ");
//			}
//			System.out.println();
//		}
	}

}
