package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.PieceIcons.QUEEN_W;
import static Chess.Pieces.PieceIcons.QUEEN_B;

import java.util.HashSet;

public class Queen extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public Queen(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = QUEEN_W;
        else icon = QUEEN_B;
    }

    /**
     * Queens have the combined movement of a rook and bishop.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        for(int dx = -1; dx <= 1; dx++)
            for(int dy = -1; dy <= 1; dy++)
                addLineToMoves(board, dx, dy, moves);
        return moves;
    }
}
