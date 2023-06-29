package mainpackage;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {

	public static void main(String[] args) {

		try {
			System.setOut(new PrintStream(System.out, true, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Model model = new Model();
		View view = new View(model);
		Game game = new Game(model, view);

//		game.menu();

		game.test();

	}

}
