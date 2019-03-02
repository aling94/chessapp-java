package Chess.Game;

import Chess.Board;
import Chess.Pieces.*;
import static Chess.Pieces.Piece.PieceColor.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Test class for the CustomChessGame. Game logic inherited from Game class is tested in ChessGameTest.
 */
public class CustomChessGameTest
{

    private CustomChessGame cg;
    private Board board;


    private Object getField(String fieldName, Object instance) throws NoSuchFieldException, IllegalAccessException
    {
        Class<?> gameClass = Game.class;
        Field field = gameClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException
    {
        cg = new CustomChessGame();
        board = (Board)getField("chessboard", cg);
    }

    /**
     * Checks the initialization of the boards. Verifies that the correct spaces are filled and the correct spaces are
     * empty.
     */
    @Test
    public void testBoardInitialization1()
    {
        assertTrue(cg.isWhitesTurn());
        for(int x = 0; x < 10; x++)
        {
            assertTrue(board.hasPiece(x,0) && board.getPieceColor(x,0) == WHITE);
            assertTrue(board.hasPiece(x,1) && board.getPieceColor(x,1) == WHITE);
            assertTrue(board.hasPiece(x,6) && board.getPieceColor(x,6) == BLACK);
            assertTrue(board.hasPiece(x,7) && board.getPieceColor(x,7) == BLACK);

            assertFalse(board.hasPiece(x,2));
            assertFalse(board.hasPiece(x,3));
            assertFalse(board.hasPiece(x,4));
            assertFalse(board.hasPiece(x,5));
        }
    }

    /**
     * Checks the initialization of the boards. Verifies that the correct spaces are filled with the correct piece types.
     */
    @Test
    public void testBoardInitialization2()
    {
        for(int x = 1; x < 9; x++)
        {
            assertTrue(board.getPiece(x,1) instanceof Pawn);
            assertTrue(board.getPiece(x,6) instanceof Pawn);
        }

        assertTrue(board.getPiece(0,0) instanceof Empress);
        assertTrue(board.getPiece(9,0) instanceof Empress);
        assertTrue(board.getPiece(0,7) instanceof Empress);
        assertTrue(board.getPiece(9,7) instanceof Empress);

        assertTrue(board.getPiece(0,1) instanceof Princess);
        assertTrue(board.getPiece(9,1) instanceof Princess);
        assertTrue(board.getPiece(0,6) instanceof Princess);
        assertTrue(board.getPiece(9,6) instanceof Princess);

        assertTrue(board.getPiece(1,0) instanceof Rook);
        assertTrue(board.getPiece(8,0) instanceof Rook);
        assertTrue(board.getPiece(1,7) instanceof Rook);
        assertTrue(board.getPiece(8,7) instanceof Rook);

        assertTrue(board.getPiece(2,0) instanceof Knight);
        assertTrue(board.getPiece(7,0) instanceof Knight);
        assertTrue(board.getPiece(2,7) instanceof Knight);
        assertTrue(board.getPiece(7,7) instanceof Knight);

        assertTrue(board.getPiece(3,0) instanceof Bishop);
        assertTrue(board.getPiece(6,0) instanceof Bishop);
        assertTrue(board.getPiece(3,7) instanceof Bishop);
        assertTrue(board.getPiece(6,7) instanceof Bishop);

        assertTrue(board.getPiece(5,0) instanceof King);
        assertTrue(board.getPiece(4,0) instanceof Queen);
        assertTrue(board.getPiece(5,7) instanceof King);
        assertTrue(board.getPiece(4,7) instanceof Queen);
    }
}