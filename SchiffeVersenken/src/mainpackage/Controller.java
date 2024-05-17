package mainpackage;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener {
	
	private Game game;
	
	public Controller(Game game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Not used
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		game.updateLastClick(e.getX(), e.getY());
		System.out.println("X: " + e.getX() + "  Y: " + e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Not used
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Not used
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Not used
		
	}

}
