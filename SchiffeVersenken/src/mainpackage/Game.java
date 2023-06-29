package mainpackage;

import java.util.Scanner;

public class Game {

    /**
     * test for basic functions like adding ships and shots
     */
    public static void test() {
        
		Model model = new Model();

        Ship s1 = new Ship(0, 0, 3, true);
                model.addShip(0, 0, 0, s1);
                model.addShip(0, 0, 1, s1);
                model.addShip(0, 0, 2, s1);
                
                model.addShot(0, 0, 1);
                model.addShot(0, 1, 0);
                
        //		int[][] zahlen = {{1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}, {1, 0, 0, 0}};
        //		System.out.print(zahlen[1][0]);
                
                for (int i = 0; i < model.getViewMap(0).length; i++) {
                    for (int j = 0; j < model.getViewMap(0).length; j++) {
                        if (model.getViewMap(0)[j][i] == null) {
                            System.out.print("__ ");
                        } else {
                            System.out.print(model.getViewMap(0)[j][i] + " ");
                        }
                    }
                    System.out.println();
                }
                
        //		for (int i = 0; i < 4; i++) {
        //			for (int j = 0; j < 4; j++) {
        //					System.out.print(zahlen[i][j] + " ");
        //			}
        //			System.out.println();
        //		}

    }

    /**
     * displays the main menu
     */
    public static void menu() {
        while (true) {
            //menü ausgeben und eingabe anfordern,
            //while (true) damit das menü nach spielende wieder geöffnet wird

            //commandline prototyp
            System.out.println("1) PVP starten\n"+
                            "2) PVE starten\n"+
                            "3) Spielstand laden\n"+
                            "4) Beenden");
            
            Scanner menuInput = new Scanner(System.in);
            int menuChoice = menuInput.nextInt();

            switch (menuChoice){
                case 1: startPVP();
                case 2: startPVE();
                case 3: loadGame();
                case 4: SaveAndExit();
            }

            menuInput.close();
        }
    }


    private static void startPVP() {
        //mit spieler verbinden und spiel starten
    }

    private static void startPVE() {
        //spiel mit bot starten
    }

    private static void loadGame() {
        //vorherigen spielstand laden
        //nur PVE oder PVP verbindung widerherstellen?
    }

    public static void SaveAndExit() {
        //Spielstand speichern (in json datei?) und programm schließen
        //wenn json schon für spielstände genutzt wird könte man auch eine config.json einbauen
        //dort zbsp spielfeldgröße, schiffe (anzahl/längen) o.ä. hinterlegen
    }

}
