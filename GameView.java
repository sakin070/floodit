import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
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

public class GameView extends JFrame {

    private JPanel bottom, center, south, north;
    private GameModel model;
    private GameController controller;
    private JButton reset;
    private JButton quit;
    private JLabel steps;
    private JButton grey,orange,blue,green,purple,red,undo, redo, settings;
    private DotButton[] dotList;
    private int size;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param model the model of the game (already initialized)
     * @param gameController the controller
     */

    public GameView(GameModel model, GameController gameController) {

        super("Flood it");
        this.model = model;
        this.controller = gameController;

        String string;
        if(model.getNumberOfSteps() == -1){
            string = "Select initial dot";
        }
        else{
            string = Integer.toString(model.getNumberOfSteps());
        }
        this.steps = new JLabel("Number of steps: "+string);



        size = model.getSize();
        dotList = new DotButton[size*size];

        bottom = new JPanel();
        center = new JPanel();
        south = new JPanel();
        north = new JPanel();

        north.setBackground(Color.WHITE);
        undo = new JButton("Undo");
        undo.addActionListener(controller);
        redo = new JButton("Redo");
        redo.addActionListener(controller);
        settings = new JButton("Settings");
        settings.addActionListener(controller);
        north.add(undo);
        north.add(redo);
        north.add(settings);
        add(north, BorderLayout.NORTH);


        center.setLayout(new GridLayout(size,size));
        center.setBackground(Color.WHITE);

        south.setLayout(new BorderLayout());
        south.setBackground(Color.WHITE);


        reset = new JButton("Reset");
        reset.addActionListener(controller);
        quit = new JButton("Quit");
        quit.addActionListener(controller);
    
        south.add(bottom,BorderLayout.SOUTH);

        bottom.add(steps);
        bottom.add(reset);
        bottom.add(quit);
        bottom.setBackground(Color.WHITE);

        add(south,BorderLayout.SOUTH);
        add(center,BorderLayout.CENTER);

        int k =0;
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                dotList[k] = new DotButton(i,j,model.getColor(i,j),size);
                dotList[k].addActionListener(controller);
                k++;
            }
        }

        for (DotButton item : dotList) {
            center.add(item);
        }

        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
    }

    /**
     * update the status of the board's DotButton instances based on the current game model
     */

    public void update() {
        String string;
        if(model.getNumberOfSteps() == -1){
            string = "Select initial dot";
        }
        else{
            string = Integer.toString(model.getNumberOfSteps());
        }

        steps.setText("Number of steps: "+string);
        for (int i=0; i<dotList.length; i++) {
            dotList[i].setColor(model.getColor(dotList[i].getRow(),dotList[i].getColumn()))  ;
        }  


        // to find out wtf is wrong
        System.out.println("Start---------------------------------");
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                System.out.println(model.isCaptured(i,j));
            }
        }
         System.out.println("Stop---------------------------------");
    }
}
