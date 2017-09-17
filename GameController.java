
/**
 * The class <b>GameController</b> is the controller of the game. It has a method
 * <b>selectColor</b> which is called by the view when the player selects the next
 * color. It then computesthe next step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

// Author 1: Saheed Akinbile Abimbola
// Student number: 8872464
// Author 2: Wilfried Tanezick Dondji Fogang
// Student number: 7968381
// Course: ITI 1121-B
// Assignment 4
// Question 1


import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;

public class GameController implements ActionListener{

    private int size;
    private GameModel model;
    private GameView game;
// decide if you want the stack of redo and undo to be static
    
    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size the size of the board on which the game will be played
     */
    public GameController(int size) {
        model = new GameModel(size); 
        if(!new File("savedGame.ser").exists()){
            this.size = size;
            this.game = new GameView(model,this);
        }
        
        try{ 
            if(new File("savedGame.ser").exists()){
            model.loadGame();
            this.size = model.getSize();
            this.game = new GameView(model,this);

            }
            else{
                
            }
        }

        catch(IOException q){
            System.out.println("Cant load game error");
        }
        catch(ClassNotFoundException p){
            System.out.println("Cant load game error missing class ");
        }
        game.update();
        
    }


    /**
     * resets the game
     */
    public void reset() {
        model.reset();
    }

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {

        if(model.getNumberOfSteps()==-1 && e.getSource() instanceof DotButton){

            DotButton v = (DotButton) e.getSource();
            model.capture(v.getRow(),v.getColumn());
            selectColor(v.getColor());
            
        }

        else {
            if (e.getSource().toString().equals("0")) {
                selectColor(0);
            }

            if (e.getSource().toString().equals("1")) {
                selectColor(1);
            }

            if (e.getSource().toString().equals("2")) {
                selectColor(2);
            }

            if (e.getSource().toString().equals("3")) {
                selectColor(3);
            }

            if (e.getSource().toString().equals("4")) {
                selectColor(4);
            }

            if (e.getSource().toString().equals("5")) {
                selectColor(5);
            }

            if (e.getActionCommand().equals("Reset")) {
                reset();
            }

            if (e.getActionCommand().equals("Quit")) {
                try {
                	model.saveGame();
                	System.exit(0);
                }
                catch(IOException a) {
                	System.out.println("Can't save game error");
                }
            }
            
            if (e.getActionCommand().equals("Settings")) {
                JRadioButton[] types = new JRadioButton[2];
                JRadioButton[] orientations = new JRadioButton[2];
                final String plane = "plane";
                final String torus = "torus";
                final String orthogonal = "orthogonal";
                final String diagonal = "diagonal";
                ButtonGroup group1 = new ButtonGroup();
                ButtonGroup group2 = new ButtonGroup();
                JOptionPane message = new JOptionPane();
                JPanel settingDialog = new JPanel();
                settingDialog.setLayout(new GridLayout(7,1));
                JLabel type = new JLabel("Play on plane or torus?");
                JLabel orientation = new JLabel("Diagonal moves?");
                types[0] = new JRadioButton("Plane");
                types[0].setActionCommand(plane);
                types[1] = new JRadioButton("Torus");
                types[1].setActionCommand(torus);
                orientations[0] = new JRadioButton("Orthogonal");
                orientations[0].setActionCommand(orthogonal);
                orientations[1] = new JRadioButton("Diagonal");
                orientations[1].setActionCommand(diagonal);

                for (int i = 0; i < 2; i++) {
                    group1.add(types[i]);
                    group2.add(orientations[i]);
                }

                settingDialog.add(type);
                settingDialog.add(types[0]);
                settingDialog.add(types[1]);
                settingDialog.add(new JLabel());
                settingDialog.add(orientation);
                settingDialog.add(orientations[0]);
                settingDialog.add(orientations[1]);

                if (model.getPlane()) {
                	types[0].setSelected(true);
                } else {
                	types[1].setSelected(true);
                }

                if (model.getOrthogonal()) {
                	orientations[0].setSelected(true);
                } else {
                	orientations[1].setSelected(true);
                }

                int closed = JOptionPane.showOptionDialog(game, settingDialog, "Settings Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

                if (closed == JOptionPane.OK_OPTION || closed == JOptionPane.CLOSED_OPTION) {
                    String chosenType = group1.getSelection().getActionCommand();
                    String chosenOrientation = group2.getSelection().getActionCommand();

                    if (chosenType.equals(plane)) {
                        model.setPlane(true);
                    } else {
                        model.setPlane(false);
                    }

                    if (chosenOrientation.equals(orthogonal)) {
                        model.setOrthogonal(true);
                    } else {
                        model.setOrthogonal(false);
                    }
                }
            }
        }

        try {
        	model.pushUndo(model.clone());
        }
        catch (CloneNotSupportedException z) {
        	System.out.println("problem with undo redo button");
        }

        try {
            if (e.getActionCommand().equals("Undo")) {
            	GameModel temp, temp2;
            	try {
            		temp2 =  model.popUndo();
            		temp = model.popUndo();
            	}
            	catch (EmptyStackException exception) {
            		JOptionPane.showMessageDialog(game, "You can't undo anymore.");
            		temp = temp2 = null;
            	}
            	if (temp != null && temp2 != null) {
            		game.setVisible(false);
            		game.dispose();
            		this.model = temp;
            		this.game = new GameView(temp, this);
            		this.model.pushRedo(temp2.clone());
            	}
            }

            if(e.getActionCommand().equals("Redo")) {
            	boolean control = true;
            	try {
            		this.model = model.popRedo();
            	}
            	catch (EmptyStackException exception2) {
            		JOptionPane.showMessageDialog(game, "You can't redo yet.");
            		control = false;
            	}
            	if (control) {
            		game.dispose();
            		this.game = new GameView(model,this);
            		this.model.pushUndo(model);
            	}
            }
        }
        catch (CloneNotSupportedException z) {
            System.out.println("problem with undo redo button");
        }
        game.update();
    }

    /**
     * <b>selectColor</b> is the method called when the user selects a new color.
     * If that color is not the currently selected one, then it applies the logic
     * of the game to capture possible locations. It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives two options: start a new game, or exit
     * @param color the newly selected color
     */

    public void selectColor(int color) {

        if (model.getCurrentSelectedColor() != color) {
            model.step();
            GenericLinkedStack<DotInfo> dots = new GenericLinkedStack<DotInfo>();
            for (int i=0; i<size; i++) {
                for (int j=0; j<size; j++) {
                    DotInfo item = model.get(i,j);
                    if (item.isCaptured()) {
                        dots.push(item);
                    }
                }
            }
            model.setCurrentSelectedColor(color);

            if (model.getPlane()) {
                if (model.getOrthogonal()) { // Plane/ Orthogonal
                    while (!(dots.isEmpty())) {
                        DotInfo current = dots.pop();
                        int row = current.getX();
                        int column = current.getY();
                        for (int i = row-1; i <= row+1; i++) {
                            if (!( (i < 0) || (i > size-1) )) {
                                if ((!(model.isCaptured(i,column))) && 
                                    (model.getColor(i, column) == color)) {
                                    model.capture(i,column);
                                    dots.push(model.get(i,column));
                                }
                            }
                        }
                        for (int i = column-1; i<=column+1; i++) {
                            if (!( (i < 0) || (i > size-1) )) {
                                if ((!(model.isCaptured(row,i))) && 
                                    (model.getColor(row,i) == color)) {
                                    model.capture(row,i);
                                    dots.push(model.get(row,i));
                                }
                            }
                        }
                    }
                } else { // Plane/Diagonal
                    while (!(dots.isEmpty())) {
                        DotInfo current = dots.pop();
                        int row = current.getX();
                        int column = current.getY();
                        for (int i = row-1; i <= row+1; i++) {
                            for (int j = column-1; j <= column+1; j++) {
                                if (!(i<0 || i>size-1 || j<0 || j>size-1)) {
                                    if ((!(model.isCaptured(i,j))) && 
                                        (model.getColor(i,j) == color)) {
                                        model.capture(i,j);
                                        dots.push(model.get(i,j));
                                    }
                                }
                            }
                        }
                    }
                }                
            } else {
                if (model.getOrthogonal()) { // Torus/ Orthogonal
                    while (!(dots.isEmpty())) {
                        DotInfo current = dots.pop();
                        int row = current.getX();
                        int column = current.getY();
                        for (int i = row-1; i <= row+1; i++) {
                            int j = (i + size) % size;
                            if ((!(model.isCaptured(j,column))) && 
                                (model.getColor(j, column) == color)) {
                                model.capture(j,column);
                                dots.push(model.get(j,column));
                            }
                        }
                        for (int i = column-1; i<=column+1; i++) {
                            int j = (i + size) % size;
                            if ((!(model.isCaptured(row,j))) && 
                                (model.getColor(row,j) == color)) {
                                model.capture(row,j);
                                dots.push(model.get(row,j));
                            }
                        }
                    }
                } else {
                    while (!(dots.isEmpty())) {
                        DotInfo current = dots.pop();
                        int row = current.getX();
                        int column = current.getY();
                        for (int i = row-1; i <= row+1; i++) {
                            for (int j = column-1; j <= column+1; j++) {
                                int goodI = (i + size) % size;
                                int goodJ = (j + size) % size;
                                if ((!(model.isCaptured(goodI,goodJ))) && 
                                    (model.getColor(goodI,goodJ) == color)) {
                                    model.capture(goodI,goodJ);
                                    dots.push(model.get(goodI,goodJ));
                                }
                            }
                        }
                    }
                }
            }
        } 

        for (int i=0; i<size; i++) {
            for (int j=0; j<size;j++) {
                if (model.isCaptured(i,j)) {
                    model.get(i,j).setColor(color);
                }
            }
        }
    
        if (model.isFinished()) {
             game.update();
            Object[] options = {"Quit", "Play Again"};
            int response = JOptionPane.showOptionDialog(game, 
                "Congratulations! The game is finished it took "
                + model.getNumberOfSteps()+" to complete", "Won", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            System.out.println("Congratulations the game is finished it took "
                + model.getNumberOfSteps()+" to complete");
            if (response == 0) {
                game.setVisible(false);
                game.dispose();
            } else {
                reset();
            }
        }   
    }
}
