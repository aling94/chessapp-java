package Chess.Pieces;

import Chess.Board;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static Chess.Pieces.Piece.PieceColor.*;

public class KingTest extends PieceTester
{
    private Piece king;

    /**
     * Initializes a board and a king. The king has not been set in any location yet.
     */
    @Before
    public void setup()
    {
        king = new King(WHITE);
        board = new Board();
    }

    /**
     * Verifies the King's moveset from a corner
     */
    @Test
    public void testMovesFromCorner()
    {
        board.putPiece(king, 7,7);
        moves = king.getPossibleMoves(board);
        assertEquals(3, moves.size());
        checkHasMove(6,7);
        checkHasMove(7,6);
        checkHasMove(6,6);
    }

    /**
     * Verifies the King's moveset from the center of the board
     */
    @Test
    public void testMovesFromCenter()
    {
        board.putPiece(king, 4,4);
        moves = king.getPossibleMoves(board);
        assertEquals(8, moves.size());
        checkHasMove(3,3);
        checkHasMove(3,4);
        checkHasMove(3,5);
        checkHasMove(5,3);
        checkHasMove(5,4);
        checkHasMove(5,5);
        checkHasMove(4,5);
        checkHasMove(4,3);
    }

    /**
     * Surrounds the king with white and black pieces. It should only have two move options, which are capturable
     * black pieces.
     */
    @Test
    public void testMovesFromCenterSurrounded()
    {
        Piece obs = new Knight(WHITE),
              cap = new Knight(BLACK);
        board.putPiece(king, 4,4);
        board.putPiece(obs, 3,3);
        board.putPiece(obs, 3,4);
        board.putPiece(obs, 3,5);
        board.putPiece(obs, 5,3);
        board.putPiece(obs, 5,4);
        board.putPiece(obs, 5,5);
        board.putPiece(cap, 4,5);
        board.putPiece(cap, 4,3);
        moves = king.getPossibleMoves(board);
        assertEquals(2, moves.size());
        checkHasMove(4,5);
        checkHasMove(4,3);
    }
}