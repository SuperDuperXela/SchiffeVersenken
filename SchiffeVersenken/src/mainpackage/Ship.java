package mainpackage;

public class Ship {
	
	private int length;
	private int hits; // wie oft es getroffen wurde
	private boolean[] segments; // abspeichern wo es getroffen wurde
	private int[] xSegments;
	private int[] ySegments;
	
	public Ship(int x, int y, int length, boolean vertically) {
		this.length = length;
		this.xSegments = new int[length];
		this.ySegments = new int[length];
		
		this.segments = new boolean[length];
		for (int i = 1; i < length; i++) {
			segments[i - 1] = false;
		}
		
		if (vertically) { // wenn vertikal von oben nach unten
			
			for (int i = 0; i < length; i++) {
				this.xSegments[i] = x;
				this.ySegments[i] = y + i;
			}
			
		} else { // wenn horizontal links von rechts
			
			for (int i = 0; i < length; i++) {
				this.xSegments[i] = x + i;
				this.ySegments[i] = y;
			}
			
		}
	}

	public void hitSegment(int x, int y) {
		if (x != xSegments[0]) {
			segments[x - xSegments[0]] = true;
		} else {
			segments[y - ySegments[0]] = true;
		}
		hits += 1;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getHits() {
		return hits;
	}
	
	public boolean isSunken() {
		return length == hits;
	}
	
	public boolean[] getSegments() {
		return segments;
	}
	
	public int[] getXSegments() {
		return xSegments;
	}
	
	public int[] getYSegments() {
		return ySegments;
	}
	
}
