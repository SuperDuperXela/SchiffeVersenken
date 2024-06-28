package mainpackage;

import java.io.Serializable;

public class Ship implements Serializable {

	/**
	 * generated ID
	 */
	private static final long serialVersionUID = -6691351843204603196L;
	/**
	 * length of the ship
	 */
	private int length;
	/**
	 * number of hits taken by the ship
	 */
	private int hitsTaken;
	/**
	 * state of the ships segments, true if it was hit
	 */
	private boolean[] segments;
	/**
	 * x coordinates of segments
	 */
	private int[] xSegments;
	/**
	 * y coordinates of segments
	 */
	private int[] ySegments;

	/**
	 * ship constructor
	 * 
	 * @param x        x coordinate of the ship
	 * @param y        y coordinate of the ship
	 * @param length   length of the ship
	 * @param vertical true if the ship is vertical
	 */
	public Ship(int x, int y, int length, boolean vertical) {
		this.length = length;
		this.xSegments = new int[length];
		this.ySegments = new int[length];

		this.segments = new boolean[length];
		for (int i = 1; i < length; i++) {
			segments[i - 1] = false;
		}

		if (vertical) {

			for (int i = 0; i < length; i++) {
				this.xSegments[i] = x;
				this.ySegments[i] = y + i;
			}

		} else {

			for (int i = 0; i < length; i++) {
				this.xSegments[i] = x + i;
				this.ySegments[i] = y;
			}

		}
	}

	/**
	 * adds a hit to a ship at the given coordinates and increases hits taken by one
	 * 
	 * @param x x coordinate of the hit
	 * @param y y coordinate of the hit
	 */
	public void hitSegment(int x, int y) {
		if (x != xSegments[0]) {
			segments[x - xSegments[0]] = true;
		} else {
			segments[y - ySegments[0]] = true;
		}
		hitsTaken += 1;
	}

	/**
	 * @return length of the ship
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return number of hits taken by the ship
	 */
	public int getHitsTaken() {
		return hitsTaken;
	}

	/**
	 * @return true if every segment was hit
	 */
	public boolean isSunken() {
		return length == hitsTaken;
	}

	/**
	 * @return states of all segments of the ship
	 */
	public boolean[] getSegments() {
		return segments;
	}

	/**
	 * @return x coordinates of segments
	 */
	public int[] getXSegments() {
		return xSegments;
	}

	/**
	 * @return y coordinates of segments
	 */
	public int[] getYSegments() {
		return ySegments;
	}

}
