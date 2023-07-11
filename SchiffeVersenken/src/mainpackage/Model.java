package mainpackage;

import java.util.ArrayList;
import java.util.List;

public class Model {

	/**
	 * map containing all ship segments of a player
	 */
	private ArrayList<Ship[][]> shipMaps = new ArrayList<>();
	/**
	 * list containing all ship objects of a player
	 */
	private ArrayList<ArrayList<Ship>> shipLists = new ArrayList<>();
	

	/**
	 * map to be displayed to a player
	 */
	private ArrayList<CellType[][]> viewMaps = new ArrayList<>();

	/**
	 * size of the board
	 */
	private static final int SIZE = 10;

	/**
	 * Model constructor
	 */
	public Model() {
		shipMaps.add(new Ship[SIZE][SIZE]);
		shipMaps.add(new Ship[SIZE][SIZE]);

		viewMaps.add(new CellType[SIZE][SIZE]);
		viewMaps.add(new CellType[SIZE][SIZE]);

		shipLists.add(new ArrayList<>());
		shipLists.add(new ArrayList<>());

		fillViewMapWithWater(0);
		fillViewMapWithWater(1);
	}

	/**
	 * gets a players shipmap
	 * 
	 * @param n player ID
	 * 
	 * @return shipMap of player n
	 */
	public Ship[][] getShipMap(int n) {
		return shipMaps.get(n);
	}

	/**
	 * adds a ship to ship map ship list and view map of player n
	 * 
	 * @see Ship
	 * 
	 * @param n    player ID
	 * @param x    x coordinate of the ship
	 * @param y    y coordinate of the ship
	 * @param ship ship object to be added
	 */
	public void addShip(int n, int x, int y, Ship ship) {
		shipMaps.get(n)[x][y] = ship;
		shipLists.get(n).add(ship);
		viewMaps.get(n)[x][y] = CellType.SHIP;
	}

	/**
	 * gets the view map for player n
	 * 
	 * @param int n player ID
	 * 
	 * @return view map of player n
	 */
	public CellType[][] getViewMap(int n) {
		return viewMaps.get(n);
	}

	/**
	 * adds a shot to the given x and y coordinates in the ship and view maps and
	 * checks if a ship was sunken
	 * 
	 * @param m ID of player being shot
	 * @param x x coordinate of the shot
	 * @param y y coordinate of the shot
	 * 
	 * @return false
	 */
	public boolean addShot(int m, int x, int y) {

		if (shipMaps.get(m)[x][y] != null) {
			shipMaps.get(m)[x][y].hitSegment(x, y);
			viewMaps.get(m)[x][y] = CellType.SHOT_SHIP;
			if (shipMaps.get(m)[x][y].isSunken()) {
				sinkShip(m, shipMaps.get(m)[x][y]);
				return true;
			}
		} else {
			viewMaps.get(m)[x][y] = CellType.SHOT_WATER;
		}

		return false;

	}

	public boolean addShot(int m, int[] coord) {
		return addShot(m, coord[0], coord[1]);
	}

	/**
	 * changes a ships segments to the SUNKEN_SHIP cell type
	 * 
	 * @param n    player ID
	 * @param ship ship to be sunken
	 */
	private void sinkShip(int n, Ship ship) {
		int[] x = ship.getXSegments();
		int[] y = ship.getYSegments();

		for (int i = 0; i < ship.getLength(); i++) {
			viewMaps.get(n)[x[i]][y[i]] = CellType.SUNKEN_SHIP;
		}
	}

	/**
	 * changes all cells of a map to water
	 * 
	 * @param n player ID
	 */
	private void fillViewMapWithWater(int n) {
		for (int i = 0; i < getViewMap(n).length; i++) {
			for (int j = 0; j < getViewMap(n).length; j++) {
				getViewMap(n)[i][j] = CellType.WATER;
			}
		}
	}

	/**
	 * @return map size
	 */
	public int getMapSize() {
		return SIZE;
	}

	public ArrayList<Ship> getShipLists(int m) {
		return shipLists.get(m);
	}
	
}
