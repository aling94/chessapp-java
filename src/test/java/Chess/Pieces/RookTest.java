package Chess.Pieces;

import Chess.Board;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

import static Chess.Pieces.Piece.PieceColor.*;

public class RookTest extends PieceTester
{

    private Piece rook;

    /**
     * Initializes a board and a rook. The rook has not been set in any location yet.
     */
    @Before
    public void setup()
    {
        rook = new Rook(WHITE);
        board = new Board();
    }

    /**
     * Helper function to check that the set of moves contains all squares in a horizontal line from minX to maxY,
     * centered at the piece but not including the square at the piece.
     */
    private void containsHorizontal(int curX, int curY, int minX, int maxX)
    {
        for(int x = minX; x <= maxX; x++)
            if(x != curX) checkHasMove(x,curY);
    }

    /**
     * Helper function to check that the set of moves contains all squares in a vertical line from minX to maxY,
     * centered at the piece but not including the square at the piece.
     */
    private void containsVertical(int curX, int curY, int minY, int maxY)
    {
        for(int y = minY; y <= maxY; y++)
            if(y != curY) checkHasMove(curX, y);
    }

    /**
     * Places the rook in a corner and verifies its moveset only contains valid moves. It should not try to move off
     * the board.
     */
    @Test
    public void testMovesFromCorner()
    {
        board.putPiece(rook, 0,0);
        moves = rook.getPossibleMoves(board);
        assertEquals(14, moves.size());
        containsHorizontal(0,0, 1, 7);
        containsVertical(0,0, 1, 7);
    }

    /**
     * Places the rook in the near center and verifies its moveset only contains valid moves.
     */
    @Test
    public void testMovesFromCenter()
    {
        board.putPiece(rook, 3,4);
        moves = rook.getPossibleMoves(board);
        assertEquals(14, moves.size());
        containsHorizontal(3,4, 0, 7);
        containsVertical(3,4, 0, 7);
    }

    /**
     * Places the rook in a corner and verifies with pieces obstructing it. Checks if its moveset contains the square
     * the black piece is on, but not the square the white piece is on.
     */
    @Test
    public void testMovesFromCornerWithObstacles()
    {
        board.putPiece(rook, 0,0);
        Piece obs1 = new Rook(WHITE),
              cap1 = new Rook(BLACK);
        board.putPiece(obs1, 0,4);
        board.putPiece(cap1, 3,0);
        moves = rook.getPossibleMoves(board);
        assertEquals(6, moves.size());
        containsHorizontal(0,0, 0, 3);
        containsVertical(0,0, 0, 3);
        assertTrue(rook.canAttack(board, 3,0));
    }

    /**
     * Places the rook in the center with pieces obstructing it. Checks if its moveset contains no white-occupied squares
     * but does contain black-occupied squares.
     */
    @Test
    public void testMovesFromCenterrWithObstacles()
    {
        Piece obs1 = new Rook(WHITE),
              obs2 = new Rook(WHITE),
              cap1 = new Rook(BLACK),
              cap2 = new Rook(BLACK);
        board.putPiece(rook, 4,3);
        board.putPiece(obs1, 3,3);
        board.putPiece(obs2, 5,3);
        board.putPiece(cap1, 4,1);
        board.putPiece(cap2, 4,4);
        moves = rook.getPossibleMoves(board);
        assertEquals(3, moves.size());
        containsHorizontal(4,3, 4, 4);
        containsVertical(4,3, 1, 4);
        assertTrue(rook.canAttack(board, 4,1));
        assertTrue(rook.canAttack(board, 4,4));
        assertFalse(rook.canAttack(board, 5,3));
    }
}