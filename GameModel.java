
import java.util.Random;
import java.io.*;
import java.io.ObjectOutputStream;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the followiung information:
 * - the state of all the ``dots'' on the board (color, captured or not)
 * - the size of the board
 * - the number of steps since the last reset
 * - the current color of selection
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
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

public class GameModel implements Cloneable, Serializable {

    /**
     * predefined values to capture the color of a DotInfo
     */
    public static final int COLOR_0           = 0;
    public static final int COLOR_1           = 1;
    public static final int COLOR_2           = 2;
    public static final int COLOR_3           = 3;
    public static final int COLOR_4           = 4;
    public static final int COLOR_5           = 5;
    public static final int NUMBER_OF_COLORS  = 6;

    private int size;
    private DotInfo[][] board;
    private Random generator = new Random();
    private int steps;
    private int currentColor;
    private boolean plane;
    private boolean torus;
    private boolean orthogonal;
    private boolean diagonal;

    private File data = new File("savedGame.ser");

    private GenericLinkedStack<GameModel> undoStack;
    private GenericLinkedStack<GameModel> redoStack;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param size the size of the board
     */
    
    public GameModel(int size) {

        this.steps = -1;
        this.size = size;
        board = new DotInfo[size][size];
        for (int i = 0; i<size; i++) {
            for (int j = 0; j<size; j++) {
                board[i][j] = new DotInfo(i,j,generator.nextInt(NUMBER_OF_COLORS));
            }
        }
        this.currentColor = 7;
        undoStack = new GenericLinkedStack<GameModel>();
        redoStack = new GenericLinkedStack<GameModel>();
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */

    public void reset() {

        for (int i = 0; i<size; i++) {
            for (int j = 0; j<size; j++) {
                board[i][j].setColor(generator.nextInt(NUMBER_OF_COLORS));
                board[i][j].setCaptured(false);
            }
        }
        this.steps = -1;
        undoStack = new GenericLinkedStack<GameModel>() ;
        redoStack = new GenericLinkedStack<GameModel>();
    }

    /**
     * Getter method for the size of the game
     * 
     * @return the value of the attribute sizeOfGame
     */  

    public int getSize() {

        return size;
    }

    /**
     * Sets the plane option to true or false
     * Automically sets torus to its opposite
     *
     * @param the state of plane
     */

    public void setPlane(boolean state) {
        plane = state;
        if (plane) {
            torus = false;
        } else {
            torus = true;
        }
    }

    public void setTorus(boolean state) {
        torus = state;
        if (torus) {
            plane = false;
        } else {
            plane = true;
        }
    }

    /**
     * Sets the orthogonal option to true or false
     * Automatically sets diagonal to its oppopsite
     *
     * @param the state of orthogonal
     */

    public void setOrthogonal(boolean state) {
        orthogonal = state;
        if (orthogonal) {
            diagonal = false;
        } else {
            diagonal = true;
        }
    }

    public void setDiagonal(boolean state) {
        diagonal = state;
        if (diagonal) {
            orthogonal = true;
        } else {
            orthogonal = false;
        }
    }

    /**
     * Returns the state of plane
     *
     * @return the state of plane
     */

    public boolean getPlane() {
        return plane;
    }

    /**
     * Returns the state of torus
     *
     * @return the state of torus
     */
    
    public boolean getTorus() {
        return torus;
    }

    /**
     * Returns the state of orthogonal
     *
     * @return the state of orthogonal
     */
    
    public boolean getOrthogonal() {
        return orthogonal;
    }

    /**
     * Returns the state of diagonal
     *
     * @return the state of diagonal
     */
    
    public boolean getDiagonal() {
        return diagonal;
    }

    /**
     * returns the current color  of a given dot in the game
     * 
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   

    public int getColor(int i, int j) {

        return board[i][j].getColor();
    }

    /**
     * returns true is the dot is captured, false otherwise
     * 
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */ 

    public boolean isCaptured(int i, int j) {

        return board[i][j].isCaptured();
    }

    /**
     * Sets the status of the dot at coordinate (i,j) to captured
     * 
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     */

    public void capture(int i, int j) {

        board[i][j].setCaptured(true);
   }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */ 

    public int getNumberOfSteps() {

        return steps;
    }

    /**
     * Setter method for currentSelectedColor
     * 
     * @param val the new value for currentSelectedColor
     */

    public void setCurrentSelectedColor(int val) {

        this.currentColor = val;
    }

    /**
     * Getter method for currentSelectedColor
     * 
     * @return currentSelectedColor
     */

    public int getCurrentSelectedColor() {

        return currentColor;
    }

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
     * @param i the x coordinate of the dot
     * @param j the y coordinate of the dot
     *
     * @return model[i][j]
     */

    public DotInfo get(int i, int j) {

        return board[i][j];
    }


    /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new color.
     */

     public void step() {

        steps++;
    }
 
    /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the dats are captured.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished() {

        boolean ans  = true;
         for (int i = 0; i<size; i++) {
            for (int j = 0; j<size; j++) {
                if (board[i][j].isCaptured() == false) {
                    ans =  false;
                }    
            }
        }
        return ans;
    }

     public GenericLinkedStack<GameModel> getRedoStack() {
        return redoStack;
    
    }

     public GenericLinkedStack<GameModel> getUndoStack() {
        return undoStack;
    
    }


    public GameModel popUndo() {

        return undoStack.pop();
    }

    public GameModel popRedo(){
        return redoStack.pop();
    }


    public void pushUndo(GameModel object){
        undoStack.push(object); 
    }


    public void pushRedo(GameModel object){
        redoStack.push(object); 
    }




    /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */

    @Override
    public String toString() {

        return "You have a board of size: " + size + " the currently selected color is "
        + currentColor + " "+this.getNumberOfSteps() +" steps"+" and the game is finished :"+ this.isFinished();
    }

     @Override
    public GameModel clone() throws CloneNotSupportedException{
        GameModel clone = new GameModel(size);
        clone.board = this.cloneBoard(); 
        clone.steps = steps;
        clone.currentColor = currentColor;
        clone.redoStack = (GenericLinkedStack<GameModel>) this.redoStack.clone();
        clone.undoStack = (GenericLinkedStack<GameModel>) this.undoStack.clone();
        return clone;
    }

    public void saveGame() throws IOException{
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(data));
    oos.writeObject(board);
    oos.writeObject(size);
    oos.writeObject(steps);
    oos.writeObject(currentColor);
    oos.writeObject(undoStack);
    oos.writeObject(redoStack);

    oos.close();
    }

    @SuppressWarnings("unchecked")
    public void loadGame() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(data));
        board = (DotInfo[][])ois.readObject();
        size = (int)ois.readObject();
        steps = (int)ois.readObject();
        currentColor =  (int)ois.readObject();
        undoStack = (GenericLinkedStack<GameModel>)ois.readObject();
        redoStack = (GenericLinkedStack<GameModel>)ois.readObject();
        
        ois.close();
    }

    public DotInfo[][] cloneBoard(){
        DotInfo[][] newBoard = new DotInfo[size][size];
        for (int i = 0; i<size; i++) {
            for (int j = 0; j<size; j++) {
                newBoard[i][j] = new DotInfo(i,j,this.getColor(i,j));
                if(this.isCaptured(i,j)){
                    newBoard[i][j].setCaptured(true);
                }
            }
        }
        return newBoard;
    }

}   


