package Chess.Pieces;

import Chess.Board;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static Chess.Pieces.Piece.PieceColor.*;

public class KnightTest extends PieceTester
{
    private Piece knight;

    /**
     * Intializes a board and a knight. The knight has not be set a location yet.
     */
    @Before
    public void setup()
    {
        knight = new Knight(WHITE);
        board = new Board();
    }

    /**
     * Places the knight in a corner and verifies the moveset matches a knight moveset. It should not try to move off
     * the board.
     */
    @Test
    public void testMovesFromCorner()
    {
        board.putPiece(knight, 7,7);
        moves = knight.getPossibleMoves(board);
        assertEquals(2, moves.size());
        checkHasMove(6,5);
        checkHasMove(5,6);
    }

    /**
     * Verifies the moveset again but from the center of the board.
     */
    @Test
    public void testMovesFromCenter()
    {
        board.putPiece(knight, 4,4);
        moves = knight.getPossibleMoves(board);
        assertEquals(8, moves.size());
        checkHasMove(5,2);
        checkHasMove(5,6);
        checkHasMove(3,2);
        checkHasMove(3,6);
        checkHasMove(6,3);
        checkHasMove(6,5);
        checkHasMove(2,3);
        checkHasMove(2,5);
    }

    /**
     * Places obstacles in the squares a knight should be able to reach. It should only include the black-occupied
     * squares in its moveset.
     */
    @Test
    public void testMovesFromCenterWithObstacles()
    {
        Piece obs = new Knight(WHITE),
              cap = new Knight(BLACK);
        board.putPiece(knight, 4,4);
        board.putPiece(obs, 5,2);
        board.putPiece(obs, 5,6);
        board.putPiece(obs, 3,2);
        board.putPiece(obs, 3,6);
        board.putPiece(cap, 6,3);
        board.putPiece(cap, 2,3);
        moves = knight.getPossibleMoves(board);
        assertEquals(4, moves.size());
        checkHasMove(6,3);
        checkHasMove(6,5);
        checkHasMove(2,3);
        checkHasMove(2,5);
    }

    /**
     * Surrounds the knight with pieces 1 square in all directions. The knight should still be able to hop over them.
     */
    @Test
    public void testMovesFromCenterSurrounded()
    {
        Piece obs = new Knight(WHITE),
              cap = new Knight(BLACK);
        board.putPiece(knight, 4,4);
        board.putPiece(obs, 3,3);
        board.putPiece(obs, 3,4);
        board.putPiece(obs, 3,5);
        board.putPiece(obs, 5,3);
        board.putPiece(obs, 5,4);
        board.putPiece(obs, 5,5);
        board.putPiece(cap, 4,5);
        board.putPiece(cap, 4,3);
        moves = knight.getPossibleMoves(board);
        assertEquals(8, moves.size());
    }
}