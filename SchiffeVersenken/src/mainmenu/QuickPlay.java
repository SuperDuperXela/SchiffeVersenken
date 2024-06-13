package mainmenu;

import mainpackage.Game;
import mainpackage.Model;
import mainpackage.View;

public class QuickPlay {

	Model model = new Model();
	View view = new View(model);
	Game game = new Game(model, view);
	
	public QuickPlay() {
		game.testStartGraphics();
	}
}
