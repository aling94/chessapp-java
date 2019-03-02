package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.PieceIcons.BISHOP_W;
import static Chess.Pieces.PieceIcons.BISHOP_B;

import java.util.HashSet;

public class Bishop extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public Bishop(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = BISHOP_W;
        else icon = BISHOP_B;
    }

    /**
     * Bishops move diagonally up until the first obstructing piece or the board boundary.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        addLineToMoves(board, 1,  1, moves);
        addLineToMoves(board,-1,  1, moves);
        addLineToMoves(board, 1, -1, moves);
        addLineToMoves(board,-1, -1, moves);
        return moves;
    }
}
