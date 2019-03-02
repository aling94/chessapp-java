package ChessGUI;

import Chess.Pieces.Piece.PieceColor;
import static Chess.Pieces.Piece.PieceColor.*;

import javax.swing.*;
import java.awt.*;

/**
 * Extension of a JLabel that represents a player. Keeps track of its own score and name.
 */
public class PlayerLabel extends JLabel
{
    public final String name;
    private int score = 0;

    /**
     * Constructors a new player that plays the given piece color. Will prompt the user to input a name for this PlayerLabel.
     * @param color PieceColor
     */
    public PlayerLabel(PieceColor color)
    {
        super("", SwingConstants.CENTER);
        setFont( new Font(getFont().getFontName(), Font.PLAIN, 20) );
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        String playerClr = (color == WHITE)? "White" : "Black";
        name = promptName(playerClr);
        updateScoreLabel();
    }

    /**
     * Increments the score of the player and updates the JLabel associated with this PlayerLabel.
     */
    public void incrementScore()
    {
        score++;
        updateScoreLabel();
    }

    /**
     * Helper function to update the text of the internal JLabel with the new score.
     */
    private void updateScoreLabel()
    {
        setText(name + " : " + score);
    }

    /**
     *  Helper function for prompting user input for a name for the player. The color the player will be playing as
     *  is inputted as a string.
     * @param color String
     * @return String
     */
    private String promptName(String color)
    {
        String msg = "Please enter the name of the player playing the " + color + " Pieces.";
        String name = JOptionPane.showInputDialog(msg);
        if(name == null || name.isEmpty()) name = color;
        name = name.trim().replaceAll("\\s+", " ");             // Collapse spaces to one space
        name = name.substring(0, Math.min(name.length(), 12));  // Truncate to length 12
        name = name.trim();                                     // Trim it again
        return String.format("%s (%c)", name, color.charAt(0)).trim();
    }
}
