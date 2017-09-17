

/**
 * The class <b>FloodIt</b> launches the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

// Author 1: Saheed Akinbile Abimbola
// Student number: 8872464
// Author 2: Wilfried Tanezick Dondji Fogang
// Student number: 7968381
// Course: ITI 1121-B
// Assignment 3
// Question 1

public class FloodIt {

    /**
     * <b>main</b> of the application. Creates the instance of  GameController 
     * and starts the game. If a game size > (12) is passed as parameter, it is 
     * used as the board size. Otherwise, a default value is passed
     * 
     * @param args command line parameters
     */

    public static void main(String[] args) {

        StudentInfo.display();
        if (args.length == 0) {
            new GameController(12);
        } else {
            if (Integer.parseInt(args[0]) > 12) {
                new GameController(Integer.parseInt(args[0]));
            } else {
                new GameController(12);
            }
        }
    }
}
