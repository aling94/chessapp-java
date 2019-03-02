package Chess.Pieces;

import Chess.Board;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

import static Chess.Pieces.Piece.PieceColor.*;

public class BishopTest extends PieceTester
{

    private Piece bishop;

    /**
     * Initializes a board and a bishop. The bishop has not been set in any location yet.
     */
    @Before
    public void setup() throws IOException
    {
        bishop = new Bishop(WHITE);
        board = new Board();
    }

    /**
     * Checks that the moveset contains all open or attackable squares along a y=x line, from minX to maxX, centered
     * on the piece.
     */
    private void containsYEqualsX(int curX, int curY, int minX, int maxX)
    {
        for(int x = curX-1, y = curY-1; x >= minX; x--, y--)
            checkHasMove(x,y);
        for(int x = curX+1, y = curY+1; x <= maxX; x++, y++)
            checkHasMove(x,y);
    }

    /**
     * Checks that the moveset contains all open or attackable squares along a y=-x line, from minX to maxX, centered
     * on the piece.
     */
    private void containsYEqualsNegX(int curX, int curY, int minX, int maxX)
    {
        for(int x = curX-1, y = curY+1; x >= minX; x--, y++)
            checkHasMove(x,y);
        for(int x = curX+1, y = curY-1; x <= maxX; x++, y--)
            checkHasMove(x,y);
    }

    /**
     * Places the bishop in the corner and verifies it only has valid moves. It should not try to move off the board.
     */
    @Test
    public void testMovesFromCorner()
    {
        board.putPiece(bishop, 0,0);
        moves = bishop.getPossibleMoves(board);
        assertEquals(7, moves.size());
        containsYEqualsX(0,0, 1, 7);
    }

    /**
     * Places the bishop in the near center and verifies it only has valid moves.
     */
    @Test
    public void testMovesFromCenter()
    {
        board.putPiece(bishop, 3,4);
        moves = bishop.getPossibleMoves(board);
        assertEquals(13, moves.size());
        containsYEqualsX(3,4, 0, 6);
        containsYEqualsNegX(3,4, 0, 7);
    }

    /**
     * Places the bishop in the corner with pieces in the way. The bishop should include the black-occupied square
     * in its moveset.
     */
    @Test
    public void testMovesFromCornerWithObstacle() throws IOException
    {
        Piece obs1 = new Bishop(BLACK);
        board.putPiece(bishop, 0,0);
        board.putPiece(obs1, 4,4);
        moves = bishop.getPossibleMoves(board);
        assertEquals(4, moves.size());
        containsYEqualsX(0,0, 1, 4);
    }

    /**
     * Places the bishop in the center with pieces in the way. Black-occupied squares should be included in its moveset,
     * not whites.
     */
    @Test
    public void testMovesFromCenterWithObstacle() throws IOException
    {
        Piece obs1 = new Rook(WHITE),
              obs2 = new Rook(WHITE),
              cap1 = new Rook(BLACK),
              cap2 = new Rook(BLACK);
        board.putPiece(bishop, 4,4);
        board.putPiece(obs1, 2,2);
        board.putPiece(obs2, 7,7);
        board.putPiece(cap1, 3,5);
        board.putPiece(cap2, 5,3);
        moves = bishop.getPossibleMoves(board);
        assertEquals(5, moves.size());
        containsYEqualsX(4,4, 3, 6);
        containsYEqualsNegX(4,4, 3, 5);
        assertTrue(bishop.canAttack(board, 3,5));
        assertTrue(bishop.canAttack(board, 5,3));
    }
}