package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;

import javax.swing.*;
import java.util.HashSet;

/**
 * Abstract class representing a main.java.Chess piece. At minimum, keeps track of its color, location on a board, and
 * whether it has been moved or not.
 */
public abstract class Piece
{

    /**
     * Enum representing Piece colors : white and black.
     */
    public enum PieceColor {BLACK, WHITE}

    protected PieceColor color;
    protected boolean hasMoved;
    protected Square location;
    protected ImageIcon icon;

    /**
     * Constructs a piece given two character codes pertaining to the piece type and color.<br>
     * Piece Type : K(King), Q(Queen), B(Bishop), N(Knight), R(Rook), P(Pawn)<br>
     * Color : B(Black), W(White)
     * @param pieceType char
     * @param color char
     */
    public static Piece pieceFromCode(char pieceType, char color)
    {
        PieceColor clr = (color == 'B')? BLACK : WHITE;
        switch (pieceType)
        {
            case 'K': return new King(clr);
            case 'Q': return new Queen(clr);
            case 'B': return new Bishop(clr);
            case 'N': return new Knight(clr);
            case 'R': return new Rook(clr);
            default : return new Pawn(clr);
        }
    }

    /**
     * Returns all possible open or capturable squares a piece can move to on the given board.
     * @param board Board
     * @return Hashset<Square>
     */
    protected abstract HashSet<Square> getMyPossibleMoves(Board board);

    /**
     * Default super constructor. Pieces always start out unmoved.
     */
    public Piece()
    {
        hasMoved = false;
    }

    /**
     * Returns all possible moves the piece can make on the specified board. Piece will assume it exists on the board
     * at the square saved by its internal location. Returns empty Set if board is null or piece is set to invalid
     * location.
     * @param board Board
     * @return Hashset<Square>
     */
    public HashSet<Square> getPossibleMoves(Board board)
    {
        if(board == null || !board.isValidSqr(location)) return new HashSet<>();
        return getMyPossibleMoves(board);
    }

    /**
     * Returns true if the piece is able to attack a specific square on the board.
     * @param board Board
     * @param target Square
     * @return boolean
     */
    public boolean canAttack(Board board, Square target)
    {
        if(board == null || !board.isValidSqr(location)
                         || !board.isValidSqr(target)) return false;
        return getPossibleMoves(board).contains(target);
    }

    /**
     * Returns true if the piece is able to attack a specific coordinate on the board.
     * @param board Board
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean canAttack(Board board, int x, int y)
    {
        return canAttack(board, new Square(x,y));
    }

    /**
     * Helper function used for searching all open or capturable squares on the board in a straight line.
     * Assumes piece can move onto capturable pieces, but not through them. Parameters dx and dy specify the
     * change in that direction
     * @param board Board
     * @param dx int
     * @param dy int
     * @param moves HashSet<Square>
     */
    protected void addLineToMoves(Board board, int dx, int dy, HashSet<Square> moves)
    {
        if(dx == 0 && dy == 0) return;
        int x = location.x,
            y = location.y;
        for(int curX = x+dx, curY = y+dy; board.isValidSqr(curX, curY); curX += dx, curY += dy)
        {
            if(board.isOpenSquare(curX, curY))
                moves.add(new Square(curX, curY));
            else if(hasEnemy(board, curX, curY))
            {
                moves.add(new Square(curX, curY));
                break;
            }
            else break;
        }
    }

    /**
     * Returns true if the square has a piece with opposite color of the attacker. False if empty, contains friendly,
     * or is invalid square.
     * @param board Board
     * @param x int
     * @param y int
     * @return boolean
     */
    protected boolean hasEnemy(Board board, int x, int y)
    {
        return board.hasPiece(x, y) && board.getPieceColor(x, y) != color;
    }

    /**
     * Returns true if the square is free or capturable (has a piece of the opposite color on it)
     * @param board Board
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean isOpenOrCapturable(Board board, int x, int y)
    {
        return board.isOpenSquare(x, y) || hasEnemy(board, x, y);
    }

    /**
     * True if this piece is a King. Normally false, but may be overriden.
     * @return boolean
     */
    public boolean isKing()
    {
        return false;
    }

    /**
     * Returns the color of the piece.
     * @return PieceColor
     */
    public PieceColor getColor()
    {
        return color;
    }

    /**
     * Returns the location of the piece if set, otherwise null.
     * @return Square
     */
    public Square getLocation()
    {
        return location;
    }

    /**
     * Sets the location of the piece.
     * @param x int
     * @param y int
     */
    public void setLocation(int x, int y)
    {
        location = new Square(x, y);
    }

    /**
     * Sets the location of the piece.
     * @param loc Square
     */
    public void setLocation(Square loc)
    {
        location = loc;
    }

    /**
     * Marks the piece as moved.
     */
    public void markMoved()
    {
        hasMoved = true;
    }

    /**
     * Marks the piece as moved.
     */
    public void markUnmoved()
    {
        hasMoved = false;
    }

    /**
     * Returns true if the piece has been marked as already moved.
     * @return boolean
     */
    public boolean hasMoved()
    {
        return hasMoved;
    }

    /**
     * Returns the ImageIcon representing this piece.
     * @return ImageIcon
     */
    public ImageIcon getIcon()
    {
        return icon;
    }
}
