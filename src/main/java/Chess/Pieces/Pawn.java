package Chess.Pieces;

import Chess.Board;
import Chess.Square;
import static Chess.Pieces.PieceIcons.PAWN_W;
import static Chess.Pieces.PieceIcons.PAWN_B;

import java.util.HashSet;
import static Chess.Pieces.Piece.PieceColor.*;

public class Pawn extends Piece
{

    /**
     * Default constructor. Constructs this piece with the specified color.
     * @param color PieceColor
     */
    public Pawn(PieceColor color)
    {
        this.color = color;
        if(color == WHITE)
            icon = PAWN_W;
        else icon = PAWN_B;
    }

    /**
     * The pawn has a unique capture movement that does not overlap with its normal movement.
     * Returns true if there is an enemy piece one square diagonally in front of the Pawn.
     * @param board Board
     * @param target Square
     */
    @Override
    public boolean canAttack(Board board, Square target)
    {
        if(board == null || !board.isValidSqr(location) ||
                !board.isValidSqr(target)) return false;
        int x = location.x,
            y = location.y,
          dir = (color == WHITE)? 1 : -1;
        return target.equals(new Square(x+1, y+dir)) ||
                    target.equals(new Square(x-1, y+dir));
    }

    /**
     * A pawn can only move foward towards the opposing side. It is allowed to move 2 spaces on its first move.
     * @param board Board
     */
    protected HashSet<Square> getMyPossibleMoves(Board board)
    {
        HashSet<Square> moves = new HashSet<>();
        int x = location.x,
            y = location.y;
        int dir = (color == WHITE)? 1 : -1;
        if(board.isOpenSquare(x, y+dir))
        {
            moves.add(new Square(x, y+dir));
            if(!hasMoved && board.isOpenSquare(x, y + 2*dir))
                moves.add(new Square(x, y + 2*dir));
        }
        if(hasEnemy(board, x+1, y+dir))
            moves.add(new Square(x+1, y+dir));
        if(hasEnemy(board, x-1, y+dir))
            moves.add(new Square(x-1, y+dir));
        return moves;
    }
}
