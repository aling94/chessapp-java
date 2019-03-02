package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.PieceIcons.KING_W;
import static Chess.Pieces.PieceIcons.KING_B;

import java.util.HashSet;

public class King extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public King(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = KING_W;
        else icon = KING_B;
    }

    /**
     * A King can only move 1 square away from its position in any direction.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        int x = location.x,
            y = location.y;
        for(int dx = -1; dx <= 1; dx++)
            for(int dy = -1; dy <= 1; dy++)
                if((dx != 0 || dy != 0) && isOpenOrCapturable(board, x+dx, y+dy))
                   moves.add(new Square(x+dx, y+dy));

        return moves;
    }

    /**
     * A King should return true since it is indeed a King.
     * @return boolean
     */
    @Override
    public boolean isKing()
    {
        return true;
    }
}
