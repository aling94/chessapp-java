package Chess;

import Chess.Pieces.Piece;

/**
 * Representation of a Move in Chess. Records the piece moved, piece captured, and the squares involved in the move.
 * Also tracks whether the moving piece had previously moved, so we can restore that state.
 */
public class Move
{
    public final Square from;
    public final Square dest;
    public final Piece movedPiece;
    public final Piece capturedPiece;
    public final boolean hadMoved;

    /**
     * Constructors a new Move object that stores the given parameters.
     * @param from Square
     * @param dest Square
     * @param moved Piece
     * @param capped Piece
     */
    public Move(Square from, Square dest, Piece moved, Piece capped)
    {
        this.from = from;
        this.dest = dest;
        movedPiece = moved;
        capturedPiece = capped;
        hadMoved = moved.hasMoved();
    }
}
