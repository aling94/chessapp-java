package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.PieceIcons.KNIGHT_W;
import static Chess.Pieces.PieceIcons.KNIGHT_B;
import static java.lang.Math.abs;

import java.util.HashSet;

public class Knight extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public Knight(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = KNIGHT_W;
        else icon = KNIGHT_B;
    }

    /**
     * A knight can hop over pieces and moves in an L shape.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        int x = location.x,
            y = location.y;
        for(int dx = -2; dx <= 2; dx++)
            for(int dy = -2; dy <= 2; dy++)
            {
                boolean isLShapedMove = (abs(dx) + abs(dy) == 3) && (dx != 0 || dy != 0);
                if(isLShapedMove && isOpenOrCapturable(board, x+dx, y+dy))
                    moves.add(new Square(x+dx, y+dy));
            }
        return moves;
    }
}
