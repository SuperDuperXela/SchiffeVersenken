package mainpackage;

/**
 * types of cells on the board
 */
public enum CellType {
	WATER('\u25A2'), 		// empty or unknown
	SHIP('\u25A7'), 		// occupied by your ship
	SHOT_WATER('\u25A3'), 	// missed
	SHOT_SHIP('\u25A4'), 	// ship segment was hit
	SUNKEN_SHIP('\u25A9'); 	// all segments of a ship were hit
	
	public final char character;
	
	private CellType(char character) {
		this.character = character;
	}

}
