package mainpackage;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import mainmenu.MainMenu;

public class Main {

	public static void oldMainFunction() {

		try {
			System.setOut(new PrintStream(System.out, true, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

//		Model model = new Model();
//		View view = new View(model);
//		Game game = new Game(model, view);
		
//		MainMenu.createMenuWindow();
		
//		game.menu();
//		commit test
//		game.test();

	}

}
