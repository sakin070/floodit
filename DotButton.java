
import java.awt.*;
import javax.swing.*;
/**
 * In the application <b>FloodIt</b>, a <b>DotButton</b> is a specialized color of
 * <b>JButton</b> that represents a dot in the game. It can have one of six colors
 * 
 * The icon images are stored in a subdirectory ``data''. We have 3 sizes, ``normal'',
 * ``medium'' and ``small'', respectively in directory ``N'', ``M'' and ``S''.
 *
 * The images are 
 * ball-0.png => grey icon
 * ball-1.png => orange icon
 * ball-2.png => blue icon
 * ball-3.png => green icon
 * ball-4.png => purple icon
 * ball-5.png => red icon
 *
 *  <a href=
 * "http://developer.apple.com/library/safari/#samplecode/Puzzler/Introduction/Intro.html%23//apple_ref/doc/uid/DTS10004409"
 * >Based on Puzzler by Apple</a>.
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

// Author 1: Saheed Akinbile Abimbola
// Student number: 8872464
// Author 2: Wilfried Tanezick Dondji Fogang
// Student number: 7968381
// Course: ITI 1121-B
// Assignment 3
// Question 1

public class DotButton extends JButton {

    private int row;
    private int column;
    private int color;
    private int iconSize;
    private ImageIcon[] icons;
    private static final int NUMBER_OF_COLORS  = 6;

    /**
     * Constructor used for initializing a cell of a specified color.
     * 
     * @param row the row of this Cell
     * @param column the column of this Cell
     * @param color specifies the color of this cell
     * @param iconSize specifies the size to use, one of SMALL_SIZE, MEDIUM_SIZE or MEDIUM_SIZE
     */

    public DotButton(int row, int column, int color, int iconSize) {

        this.row = row;
        this.column = column;
        this.color = color;
        this.iconSize = iconSize;
        String str;
        if (iconSize>=10 && iconSize<=25) {
            str = "M";
        } else if (iconSize>25) {
            str ="S"; 
        } else {
            str = "N";
        }
        icons = new ImageIcon[NUMBER_OF_COLORS];
        for (int i=0; i<NUMBER_OF_COLORS; i++) {
            icons[i] = new ImageIcon("data/"+str+"/ball-"+Integer.toString(i)+".png");
        }
        setIcon();
    }

    /**
     * Other constructor used for initializing a cell of a specified color.
     * no row or column info available. Uses "-1, -1" for row and column instead
     * 
     * @param color specifies the color of this cell
     * @param iconSize specifies the size to use, one of SMALL_SIZE, MEDIUM_SIZE or MEDIUM_SIZE
     */   

    public DotButton(int color, int iconSize) {

        this.row = -1;
        this.column = -1;
        this.color = color;
        this.iconSize = iconSize;

        String str;
        if (iconSize>=10 && iconSize<=25) {
            str = "M";
        } else if (iconSize>25) {
            str ="S"; 
        } else {
            str = "N";
        }
        icons = new ImageIcon[NUMBER_OF_COLORS];
        for (int i=0; i<NUMBER_OF_COLORS; i++) {
            icons[i] = new ImageIcon("data/"+str+"/ball-"+Integer.toString(i)+".png");
        }
        setIcon();
    }
 
    /**
     * Changes the cell color of this cell. The image is updated accordingly.
     * 
     * @param color the color to set
     */

    public void setColor(int color) {

        this.color = color;
        setIcon();
   }

    /**
     * Getter for color
     *
     * @return color
     */

    public int getColor() {

        return this.color;
    }
 
    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */

    public int getRow() {

        return this.row ;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */

    public int getColumn() {

        return this.column;
    }

    // Private methods
    private void setIcon() {

        setIcon(icons[color]);
    }

    public String toString() {
        
        return Integer.toString(this.color);
    }
}
