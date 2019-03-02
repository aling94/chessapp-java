package ChessGUI;

import Chess.Square;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JButton extension. Class keeps track of its coordinates on the board as well as its color. Button is initialized
 * as a 60x60 square colored White or Gray.
 */
public class SquareButton extends JButton
{

    public final Square loc;
    public final Color defaultColor;

    /**
     * Constructs a new JButton that also stores a Square representing its location on a board. Also calculates the
     * color of the square using the coordinates and stores it.
     * @param x int
     * @param y int
     */
    public SquareButton(int x, int y)
    {
        super();
        loc = new Square(x,y);
        defaultColor = getSqrColor(x,y);

        setPreferredSize(new Dimension(60,60));
        setBackground(defaultColor);
        setContentAreaFilled(false);
        setOpaque(true);
        setMargin(new Insets(0,0,0,0));
        setFocusPainted(false);
    }

    /**
     * Sets the background color to green to represent a highlighted square.
     */
    public void highLight()
    {
        setBackground(Color.GREEN);
    }

    /**
     * Restores the original color of this SquareButton.
     */
    public void unHighLight()
    {
        setBackground(defaultColor);
    }

    /**
     * Helper function for identifying whether the square at the specified coordinates should be white or gray.
     * @param x int
     * @param y int
     * @return Color
     */
    public static Color getSqrColor(int x, int y)
    {
        if((x%2 == 0 && y%2 == 0) || (x%2 == 1 && y%2 == 1))
            return Color.WHITE;
        return Color.GRAY;
    }
}
