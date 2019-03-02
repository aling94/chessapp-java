package ChessGUI;

import Chess.Game.ChessGame;
import Chess.Game.Game;
import Chess.Pieces.Piece;
import Chess.Pieces.Piece.PieceColor;
import Chess.Square;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * View representation for a Game. Displays the board for a Game object and also keeps track of player information.
 * Board is represented by an array of JButtons and interaction is done via clicking. This View allows users to start
 * new games with one of two game modes, forfeit games, and undo moves. Scores are kept for each player. A player's score
 * is incremented only when he/she achieves a Checkmate. Draws/Stalemates/Restarts do not increase anyone's score.
 */
public class ChessGUI
{

    private JFrame window = new JFrame("Chess App");
    private JPanel parentPanel = new JPanel(new BorderLayout(2,2));

    private JPanel board = new JPanel(new BorderLayout(2,2));
    private SquareButton[][] boardSqrs;

    private JButton undoButton;
    private JButton newgameButton;
    private JButton forfeitButton;

    private PlayerLabel whitePlayer;
    private PlayerLabel blackPlayer;

    /**
     * Initializes all the components of the GUI and puts them into a JFrame to display.
     */
    public ChessGUI()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e)
        {
            //silently ignore
        }

        parentPanel = new JPanel(new BorderLayout(3,3));
        parentPanel.setBorder(new EmptyBorder(0,0,0,0));
        parentPanel.setBackground(Color.BLACK);

        // Add all the components to the parent panel
        parentPanel.add(initToolbar(), BorderLayout.PAGE_START);
        parentPanel.add(board, BorderLayout.CENTER);
        parentPanel.add(initScoreBar(), BorderLayout.PAGE_END);

        // Setup an empty board at the start
        setupBoard(new ChessGame());

        // Pack and fix the window size. Set sizing and location options.
        window.add(parentPanel);
        window.pack();
        window.setMinimumSize(window.getSize());
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Given a Game object, the GUI will clear the current board and render a new board matching the one contained in
     * the Game object.
     * @param chessgame Game
     */
    public void setupGame(Game chessgame)
    {
        if(chessgame == null) return;
        setupBoard(chessgame);
        setupPieces(chessgame);
        window.pack();
    }

    /**
     * Returns the name of the PlayerLabel playing the given color.
     * @param color PieceColor
     * @return String
     */
    public String getPlayerName(PieceColor color)
    {
        if(color == PieceColor.WHITE)      return whitePlayer.name;
        else if(color == PieceColor.BLACK) return blackPlayer.name;
        else return "";
    }

    /**
     * Sets the font color of the display names.
     * @param whtPlayerClr Color
     * @param blkPlayerClr Color
     */
    public void setNameColors(Color whtPlayerClr, Color blkPlayerClr)
    {
        whitePlayer.setForeground(whtPlayerClr);
        blackPlayer.setForeground(blkPlayerClr);
    }

    /**
     * Increments the score of the player representing the Pieces of the given color.
     * @param color PieceColor
     */
    public void incrementScore(PieceColor color)
    {
        if(color == PieceColor.WHITE)      whitePlayer.incrementScore();
        else if(color == PieceColor.BLACK) blackPlayer.incrementScore();
    }

    /**
     * Adds an ActionListener for the New Game button.
     * @param actl ActionListener
     */
    public void addNewGameListener(ActionListener actl)
    {
        newgameButton.addActionListener(actl);
    }

    /**
     * Adds an ActionListener for the Forfeit Game button.
     * @param actl ActionListener
     */
    public void addForfeitListener(ActionListener actl)
    {
        forfeitButton.addActionListener(actl);
    }

    /**
     * Adds an ActionListener for the Undo Move button.
     * @param actl ActionListener
     */
    public void addUndoListener(ActionListener actl)
    {
        undoButton.addActionListener(actl);
    }

    /**
     * Adds an ActionListener for all the JButtons representing the board squares.
     * @param actl ActionListener
     */
    public void addSquareListerner(ActionListener actl)
    {
        for(int x = 0; x < boardSqrs.length; x++)
            for(int y = 0; y < boardSqrs[0].length; y++)
                boardSqrs[x][y].addActionListener(actl);
    }

    /**
     * Helper function for determining if the coordinates refer to a valid square on the board.
     * @param x int
     * @param y int
     * @return boolean
     */
    private boolean isValidSquare(int x, int y)
    {
        if(board == null || boardSqrs == null) return false;
        return (x >= 0 && y >= 0) && (x < boardSqrs.length && y < boardSqrs[0].length);
    }

    /**
     * Highlights the given square if given valid coordinates.
     * @param x int
     * @param y int
     */
    public void highlightSquare(int x, int y)
    {
        if(isValidSquare(x,y))
            boardSqrs[x][y].highLight();
    }

    /**
     * Restores the square at the given coordinates to its default color.
     * @param x int
     * @param y int
     */
    public void unHighlightSquare(int x, int y)
    {
        if(isValidSquare(x,y))
            boardSqrs[x][y].unHighLight();
    }

    /**
     * Sets the IconImage of the square at the given coordinates
     * @param sqr Square
     * @param icon ImageIcon
     */
    public void setSquareIcon(Square sqr, ImageIcon icon)
    {
        if(sqr != null &&isValidSquare(sqr.x, sqr.y))
            boardSqrs[sqr.x][sqr.y].setIcon(icon);
    }

    /**
     * Moves the IconImage from one square to another square.
     * @param from Square
     * @param dest Square
     */
    public void moveSquareIcon(Square from, Square dest)
    {
        if(isValidSquare(from.x, from.y) && isValidSquare(dest.x, dest.y))
        {
            boardSqrs[dest.x][dest.y].setIcon(boardSqrs[from.x][from.y].getIcon());
            boardSqrs[from.x][from.y].setIcon(null);
        }
    }

    /**
     * Helper function for initializing the toolbar component. Contains the undo, forfeit, and new game buttons.
     * @return JToolBar
     */
    private JToolBar initToolbar()
    {
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        undoButton = new JButton("Undo Last Move");
        newgameButton = new JButton("New Game");
        forfeitButton = new JButton("Forfeit Game");
        tools.add(newgameButton);
        tools.addSeparator();
        tools.add(forfeitButton);
        tools.addSeparator();
        tools.add(undoButton);
        return tools;
    }

    /**
     * Initializes the score bar component. Prompts the user for names for each player.
     * @return JPanel
     */
    private JPanel initScoreBar()
    {
        whitePlayer = new PlayerLabel(PieceColor.WHITE);
        blackPlayer = new PlayerLabel(PieceColor.BLACK);
        JPanel scores = new JPanel(new GridLayout(0,2));
        scores.add(whitePlayer);
        scores.add(blackPlayer);
        return scores;
    }

    /**
     * Creates a new JLabel with the given String. The labels are the same dimensions as the board squares. To be used
     * to label the rows and columns of the board.
     * @param label String
     * @return JLabel
     */
    private JLabel initLabel(String label)
    {
        JLabel marker = new JLabel(label, SwingConstants.CENTER);
        marker.setFont( new Font(marker.getFont().getFontName(), Font.PLAIN, 26) );
        marker.setPreferredSize(new Dimension(60,60));
        marker.setBackground(Color.BLACK);
        marker.setForeground(Color.WHITE);
        marker.setOpaque(true);
        return marker;
    }

    /**
     * Helper function for adding the column labels to the JPanel grid.
     * @param boardGrid JPanel
     * @param xLen int
     */
    private void addColLabels(JPanel boardGrid, int xLen)
    {
        boardGrid.add(initLabel(""));
        for(int x = 0; x < xLen; x++)
            boardGrid.add(initLabel((char)('A'+x) + ""));
        boardGrid.add(initLabel(""));
    }

    /**
     * Clears the board and sets up new JButtons to represent the board.
     * @param chessgame Game
     */
    private void setupBoard(Game chessgame)
    {
        // Clear the board
        board.removeAll();
        board.revalidate();

        int boardX = chessgame.boardX,
            boardY = chessgame.boardY;
        boardSqrs = new SquareButton[boardX][boardY];

        // Init a wrapper for all the squares to put on the board
        JPanel boardGrid = new JPanel(new GridLayout(0, boardX+2, 1, 1));
        boardGrid.setBackground(Color.BLACK);
        board.add(boardGrid, BorderLayout.CENTER);

        addColLabels(boardGrid, boardX);                                // Top col label
        for(int y = boardY-1; y >= 0; y--)
            for(int x = 0; x < boardX; x++)
            {
                if(x == 0) boardGrid.add(initLabel((y+1) + ""));        // Left row label
                boardSqrs[x][y] = new SquareButton(x,y);
                boardGrid.add(boardSqrs[x][y]);
                if(x == boardX-1) boardGrid.add(initLabel((y+1) + "")); // Right row label
            }
        addColLabels(boardGrid, boardX);                                // Bottom col label
    }

    /**
     * Initializes all the pieces of a specific color in positions specified by the internal board of the given
     * Game object.
     * @param chessgame Game
     */
    private void setupPieces(Game chessgame)
    {
        HashSet<Piece> pieces = chessgame.getAllPieces();
        for(Piece piece : pieces)
        {
            Square loc = piece.getLocation();
            int x = loc.x,
                y = loc.y;
            boardSqrs[x][y].setIcon(piece.getIcon());
        }
    }
}