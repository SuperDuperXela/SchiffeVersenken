package mainpackage;

import java.util.ArrayList;

public class Model {
	
	private ArrayList<Ship[][]> shipMaps = new ArrayList<>();
	
	private ArrayList<ArrayList<Ship>> shipLists = new ArrayList<>();
	
	private ArrayList<CellType[][]> viewMaps = new ArrayList<>();
	
	private static final int SIZE = 10;
	
	public Model() {
		shipMaps.add(new Ship[SIZE][SIZE]);
		shipMaps.add(new Ship[SIZE][SIZE]);
		
		viewMaps.add(new CellType[SIZE][SIZE]);
		viewMaps.add(new CellType[SIZE][SIZE]);
		
		shipLists.add(new ArrayList<>());
		shipLists.add(new ArrayList<>());
	}
	
	public Ship[][] getShipMap(int n) {
		return shipMaps.get(n);
	}
	
	public void addShip(int n, int x, int y, Ship ship) {
		shipMaps.get(n)[x][y] = ship;
		shipLists.get(n).add(ship);
		viewMaps.get(n)[x][y] = CellType.SHIP;
	}
	
	public CellType[][] getViewMap(int n) {
		return viewMaps.get(n);
	}
	
	public boolean addShot(int m, int x, int y) {
		
		if (shipMaps.get(m)[x][y] != null) {
			shipMaps.get(m)[x][y].hitSegment(x, y);
			viewMaps.get(m)[x][y] = CellType.SHOT_SHIP;
			if (shipMaps.get(m)[x][y].isSunken() ) {
				sinkShip(m, shipMaps.get(m)[x][y]);
			}
		} else {
			viewMaps.get(m)[x][y] = CellType.SHOT_WATER;
		}
		
		return false;
		
	}
	
	private void sinkShip(int n, Ship ship) {
		int[] x = ship.getXSegments();
		int[] y = ship.getYSegments();
		
		for (int i = 0; i < ship.getLength(); i++) {
			viewMaps.get(n)[x[i]][y[i]] = CellType.SUNKEN_SHIP;
		}
	}

}
