package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.PieceIcons.PRNC_W;
import static Chess.Pieces.PieceIcons.PRNC_B;

import java.util.HashSet;

import static java.lang.Math.abs;

/**
 * A custom piece. Combines the movement of a knight and a bishop.
 */
public class Princess extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public Princess(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = PRNC_W;
        else icon = PRNC_B;
    }

    /**
     * A princess can hop over pieces and moves in an L shape. It can also move diagonally up to the first obstructing
     * piece or up to the board boundary.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        int x = location.x,
            y = location.y;

        // Calculate knight movements
        for(int dx = -2; dx <= 2; dx++)
            for(int dy = -2; dy <= 2; dy++)
            {
                boolean isLShapedMove = (abs(dx) + abs(dy) == 3) && (dx != 0 || dy != 0);
                if(isLShapedMove && isOpenOrCapturable(board, x+dx, y+dy))
                    moves.add(new Square(x+dx, y+dy));
            }

        // Calculate bishop movements
        addLineToMoves(board, 1,  1, moves);
        addLineToMoves(board,-1,  1, moves);
        addLineToMoves(board, 1, -1, moves);
        addLineToMoves(board,-1, -1, moves);
        return moves;
    }
}
