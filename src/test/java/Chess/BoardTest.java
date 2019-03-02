package Chess;

import Chess.Pieces.Piece;
import Chess.Pieces.Pawn;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

import static Chess.Pieces.Piece.PieceColor.*;

public class BoardTest
{

    private Board board;
    private Piece piece;

    /**
     * Initializes a board with a white pawn at A1 (0,0).
     */
    @Before
    public void setup()
    {
        board = new Board();
        piece = new Pawn(WHITE);
        board.putPiece(piece, 0, 0);
    }

    /**
     * Tests that the custom dimension constructor creates a 2D of the correct dimensions.
     */
    @Test
    public void testCustomBoardConstructor() throws IllegalAccessException, NoSuchFieldException
    {
        Board b = new Board(5,6);
        assertEquals(5, b.xLength);
        assertEquals(6, b.yLength);
        Class<?> bClass = Board.class;
        Field field = bClass.getDeclaredField("board");
        field.setAccessible(true);
        Piece[][] internalBoard = (Piece[][]) field.get(b);
        assertEquals(5, internalBoard.length);
        assertEquals(6, internalBoard[0].length);
    }

    /**
     * Tests that the custom dimension constructor with invalid dimensions. Should default to 8x8 board
     */
    @Test
    public void testInvalidCustomBoard() throws IllegalAccessException, NoSuchFieldException
    {
        Board b = new Board(-1,6);
        assertEquals(8, b.xLength);
        assertEquals(8, b.yLength);
        Class<?> bClass = Board.class;
        Field field = bClass.getDeclaredField("board");
        field.setAccessible(true);
        Piece[][] internalBoard = (Piece[][]) field.get(b);
        assertEquals(8, internalBoard.length);
        assertEquals(8, internalBoard[0].length);
    }

    /**
     * Tests that all squares actually on the board are valid.
     */
    @Test
    public void testIsValidSquare()
    {
        for(int y = 0; y < 8; y++)
            for(int x = 0; x < 8; x++)
                assertTrue(board.isValidSqr(x, y));
    }

    @Test
    public void testInvalidSquare()
    {
        assertFalse(board.isValidSqr(-1, -1));
        assertFalse(board.isValidSqr(8, 8));
    }

    @Test
    public void testSetPieceValid()
    {
        assertTrue(board.hasPiece(0,0));
        board.removePiece(0, 0);
        assertFalse(board.hasPiece(0,0));
    }

    @Test
    public void testSetPieceInValid()
    {
        Piece p = new Pawn(WHITE);
        board = new Board();
        board.putPiece(p, -1, -1);
        assertFalse(board.hasPiece(-1, -1));
    }

    @Test public void testMovePiece()
    {
        board.movePiece(new Square(0,0), new Square(1,1));
        assertFalse(board.hasPiece(0,0));
        assertTrue(board.hasPiece(1,1));
        assertEquals(new Square(1,1), piece.getLocation());
    }

    @Test public void testMovePieceOffBoard()
    {
        board.movePiece(new Square(0,0), new Square(8,8));
        assertTrue(board.hasPiece(0,0));
        assertEquals(new Square(0,0), piece.getLocation());
    }
}