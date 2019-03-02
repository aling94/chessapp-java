package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.PieceIcons.ROOK_W;
import static Chess.Pieces.PieceIcons.ROOK_B;

import java.util.HashSet;

public class Rook extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public Rook(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = ROOK_W;
        else icon = ROOK_B;
    }

    /**
     * Rooks move horizontally or vertically up until the first obstructing piece or the board boundary.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        addLineToMoves(board,  1,  0, moves);
        addLineToMoves(board, -1,  0, moves);
        addLineToMoves(board,  0,  1, moves);
        addLineToMoves(board,  0, -1, moves);
        return moves;
    }
}
