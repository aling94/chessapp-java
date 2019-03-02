package Chess.Game;

import java.lang.reflect.Field;

import Chess.Board;
import Chess.Move;
import Chess.Pieces.*;
import Chess.Square;
import Chess.Game.Game.GameState;
import static Chess.Game.Game.GameState.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static Chess.Pieces.Piece.PieceColor.*;

public class ChessGameTest
{

    private ChessGame cg;
    private Board board;

    /**
     * Helper function for using reflection to get access to a private field.
     */
    private Object getField(String fieldName, Object instance) throws NoSuchFieldException, IllegalAccessException
    {
        Class<?> gameClass = Game.class;
        Field field = gameClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }

    /**
     * Makes and verifies a valid move. Assumes that the move specified is legal. Checks that a piece has successfully
     * changed position on the board. Assumes a standard chessboard initialized (don't use with custom arrangement)
     */
    private void makeValidMove(int fromX, int fromY, int destX, int destY)
    {
        Square from = new Square(fromX, fromY);
        Square dest = new Square(destX, destY);
        Piece pieceBefore = board.getPiece(from);
        cg.runTurn(from, dest);
        Piece pieceAfter = board.getPiece(dest);
        assertTrue(board.hasPiece(dest));
        assertFalse(board.hasPiece(from));
        assertEquals(pieceBefore, pieceAfter); // The memory addresses of the pieces should be the same if it moved
    }

    /**
     * Makes and verifies an invalid move. Assumes that the move specified is illegal.
     */
    private void makeInValidMove(int fromX, int fromY, int destX, int destY)
    {
        Square from = new Square(fromX, fromY);
        Square dest = new Square(destX, destY);
        cg.runTurn(from, dest);
        assertTrue(board.hasPiece(from));
        assertFalse(board.hasPiece(dest));
    }

    /**
     * Helper function for checking an undo. Given the original location of a piece and it's current location, and the
     * piece that was captured in the process.
     */
    private void attemptUndo(int fromX, int fromY, int destX, int destY, Piece capped)
    {
        Piece p = board.getPiece(destX, destY);
        Move mv = cg.undoMove();

        assertTrue(board.hasPiece(fromX, fromY));
        if(capped == null) assertFalse(board.hasPiece(destX, destY));
        assertEquals(p, board.getPiece(fromX, fromY));

        assertEquals(new Square(fromX, fromY), mv.from);
        assertEquals(new Square(destX, destY), mv.dest);
        assertEquals(p, mv.movedPiece);
        assertEquals(capped, mv.capturedPiece);

    }

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException
    {
        cg = new ChessGame();
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
        for(int x = 0; x < 8; x++)
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
        for(int x = 0; x < 8; x++)
        {
            assertTrue(board.getPiece(x,1) instanceof Pawn);
            assertTrue(board.getPiece(x,6) instanceof Pawn);
        }

        assertTrue(board.getPiece(0,0) instanceof Rook);
        assertTrue(board.getPiece(7,0) instanceof Rook);
        assertTrue(board.getPiece(0,7) instanceof Rook);
        assertTrue(board.getPiece(7,7) instanceof Rook);

        assertTrue(board.getPiece(1,0) instanceof Knight);
        assertTrue(board.getPiece(6,0) instanceof Knight);
        assertTrue(board.getPiece(1,7) instanceof Knight);
        assertTrue(board.getPiece(6,7) instanceof Knight);

        assertTrue(board.getPiece(2,0) instanceof Bishop);
        assertTrue(board.getPiece(5,0) instanceof Bishop);
        assertTrue(board.getPiece(2,7) instanceof Bishop);
        assertTrue(board.getPiece(5,7) instanceof Bishop);

        assertTrue(board.getPiece(4,0) instanceof King);
        assertTrue(board.getPiece(3,0) instanceof Queen);
        assertTrue(board.getPiece(4,7) instanceof King);
        assertTrue(board.getPiece(3,7) instanceof Queen);
    }

    /**
     * Tests valid moves from each color.
     */
    @Test
    public void testValidMoves()
    {
        assertTrue(cg.isWhitesTurn());
        makeValidMove(1,1,  1,3);
        assertTrue(cg.isBlacksTurn());
        makeValidMove(1,7,  0,5);
        assertTrue(cg.isWhitesTurn());
    }

    /**
     * Tests undo with an empty move list. Runs the initialization tests to check that nothing on the board has changed.
     */
    @Test
    public void testUndoMoves0()
    {
        Move mv = cg.undoMove();
        assertNull(mv);
        testBoardInitialization1();
        testBoardInitialization2();
    }

    /**
     * Moves a pawn, then try to undo. Check that the piece has moved back to it's original location and the move returned
     * matches the piece and squares involved.
     */
    @Test
    public void testUndoValidMove1()
    {
        Piece p = board.getPiece(1,1);
        makeValidMove(1,1,  1,3);
        assertTrue(cg.isBlacksTurn());
        attemptUndo(1,1,  1,3, null);
        assertTrue(cg.isWhitesTurn());
    }

    /**
     * Moves a pawn, then a black knight and tries to undo. Checks that the piece has has returned.
     */
    @Test
    public void testUndoValidMove2()
    {
        Piece p = board.getPiece(1,7);
        makeValidMove(1,1,  1,3);
        makeValidMove(1,7,  0,5);
        assertTrue(cg.isWhitesTurn());
        attemptUndo(1,7,  0,5, null);
        assertTrue(cg.isBlacksTurn());
    }

    /**
     * Tests that invalid moves do not change the board state.
     */
    @Test
    public void testInvalidMoves()
    {
        assertTrue(cg.isWhitesTurn());
        makeInValidMove(1,1,  1,4);
        assertFalse(cg.isBlacksTurn());
        makeInValidMove(1,7,  0,5);
        assertFalse(cg.isBlacksTurn());
    }

    /**
     * Tests a valid capture. Here a black bishop captures a pawn.
     */
    @Test
    public void testSafeCapture()
    {
        assertTrue(cg.isWhitesTurn());
        makeValidMove(7,1, 7,2);
        assertTrue(cg.isBlacksTurn());
        makeValidMove(3,6, 3,4);
        assertTrue(cg.isWhitesTurn());
        makeValidMove(1,0, 0,2);
        assertTrue(cg.isBlacksTurn());
        assertTrue(board.getPieceColor(7,2) == WHITE);
        // Do the capture
        makeValidMove(2,7, 7,2);
        assertTrue(board.getPieceColor(7,2) == BLACK);
        assertTrue(cg.isWhitesTurn());
    }

    /**
     * Tries to undo the capture done in testSafeCapture()
     */
    @Test
    public void testUndoCapture()
    {
        makeValidMove(7,1, 7,2);
        makeValidMove(3,6, 3,4);
        makeValidMove(1,0, 0,2);
        assertTrue(cg.isBlacksTurn());
        assertTrue(board.getPieceColor(7,2) == WHITE);
        // Do the capture
        Piece capped = board.getPiece(7,2);
        makeValidMove(2,7, 7,2);
        assertTrue(board.getPieceColor(7,2) == BLACK);
        assertTrue(cg.isWhitesTurn());
        attemptUndo(2,7, 7,2, capped);
        assertTrue(cg.isBlacksTurn());
    }

    /**
     * Tests that an attempt at an unsafe capture (puts King into check) does not go through.
     */
    @Test
    public void testUnsafeCapture()
    {
        String[] pieceArragement =
        {
            "KB47F", "PW35F", "PW46F", "KW45F"
        };
        cg = new ChessGame(pieceArragement, BLACK);
        assertTrue(cg.isBlacksTurn());
        cg.runTurn(new Square(4,7), new Square(4,6));
        assertTrue(cg.isBlacksTurn());
    }

    /**
     * Helper function for loading a board arrangement and verifying that it is a checkmate.
     */
    private void testCheckMateSetup(String[] piecePositions, Piece.PieceColor currentTurnColor)
    {
        GameState winner = (currentTurnColor == WHITE)? BLACK_WINS : WHITE_WINS;
        cg = new ChessGame(piecePositions, currentTurnColor);
        assertFalse(cg.currentColorHasMoves());         // has no moves -> checkmate!
        assertEquals(winner, cg.getGameState());
    }

    /**
     * Helper function for loading a board arrangement and verifying that it is a stalemate.
     */
    private void testStalemateSetup(String[] piecePositions, Piece.PieceColor currentTurnColor)
    {
        cg = new ChessGame(piecePositions, currentTurnColor);
        assertFalse(cg.currentColorHasMoves());         // still has no moves -> stalemate!
        assertEquals(STALEMATE, cg.getGameState());
    }

    /**
     * Tests a number of checkmate setups on both colors.
     */
    @Test
    public void testCheckMates()
    {
        String[] setup1 = {"KB77F", "KW04F", "RW07F", "RW06F"};
        testCheckMateSetup(setup1, BLACK);
        String[] setup2 = {"KW40F", "RW07F", "KB67F", "PB56F", "PB66F", "PB76F"};
        testCheckMateSetup(setup2, BLACK);
        String[] setup3 = {"KW55F", "QW66F", "KB67F"};
        testCheckMateSetup(setup3, BLACK);
        String[] setup4 = {"KW70F", "KB62F", "QB52F", "NB72F"};
        testCheckMateSetup(setup4, WHITE);
        String[] setup5 = {"KW00F", "KB12F", "QB20F"};
        testCheckMateSetup(setup5, WHITE);
    }

    /**
     * Tests a stalemate setup
     */
    @Test
    public void testStaleMates()
    {
        String[] setup1 = {"KB47F", "PW35F", "PW46F", "KW45F"};
        testStalemateSetup(setup1, BLACK);
        String[] setup2 = {"KW12F", "QW32F", "KB20F"};
        testStalemateSetup(setup2, BLACK);
        String[] setup3 = {"KW75F", "RW65F", "KB77F"};
        testStalemateSetup(setup3, BLACK);
        String[] setup4 = {"KW52F", "QW32F", "KB40F"};
        testStalemateSetup(setup4, BLACK);
        String[] setup5 = {"KW30F", "KB32F", "QB22F"};
        testStalemateSetup(setup5, WHITE);
        String[] setup6 = {"KB12F", "QB32F", "KW20F"};
        testStalemateSetup(setup6, WHITE);
        String[] setup7 = {"KB75F", "RB65F", "KW77F"};
        testStalemateSetup(setup7, WHITE);
    }
}