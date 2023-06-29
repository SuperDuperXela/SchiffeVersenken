package mainpackage;

/**
 * types of cells on the board
 */
public enum CellType {
	WATER(), 		// empty or unknown
	SHIP(), 		// occupied by your ship
	SHOT_WATER(), 	// missed
	SHOT_SHIP(), 	// ship segment was hit
	SUNKEN_SHIP() 	// all segments of a ship were hit

}
