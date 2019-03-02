package Chess.Pieces;

import static Chess.Pieces.Piece.PieceColor.*;

import Chess.Board;
import Chess.Square;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PawnTest extends PieceTester
{

    private Piece pawn;

    /**
     * Initalizes a pawn on a board at (4,1)
     */
    @Before
    public void setup()
    {
        pawn = new Pawn(WHITE);
        board = new Board();
        board.putPiece(pawn, 4,1);
    }

    /**
     * Tests that an unmoved pawn can move 2 spaces if not blocked
     */
    @Test
    public void testPawnCanMove2Spaces()
    {
        moves = pawn.getPossibleMoves(board);
        assertEquals(2, moves.size());
        checkHasMove(4,2);
        checkHasMove(4,3);
    }

    /**
     * Tests that a pawn that has already moved can only move 1 space
     */
    @Test
    public void testPawnCannotMove2Spaces()
    {
        pawn.markMoved();
        moves = pawn.getPossibleMoves(board);
        assertEquals(1, moves.size());
        checkHasMove(4,2);
    }

    /**
     * Tests that a pawn cannot move forward through pieces, even if allowed a 2 square move.
     */
    @Test
    public void testGetPawnMoves1()
    {
        Piece obstacle1 = new Pawn(WHITE);
        board.putPiece(obstacle1, 4,3);
        moves = pawn.getPossibleMoves(board);
        assertEquals(1, moves.size());
        checkHasMove(4,2);
    }

    /**
     * Blocks the pawn with a piece directly in front of it. The pawn should not be able to move.
     */
    @Test
    public void testGetPawnMoves2()
    {
        Piece obstacle1 = new Pawn(WHITE);
        board.putPiece(obstacle1, 4,2);
        moves = pawn.getPossibleMoves(board);
        assertTrue(moves.isEmpty());
    }

    /**
     * Tests that the pawn does not move off the board when placed at a boundary
     */
    @Test
    public void testPawnStaysOnBoard()
    {
        board.movePiece(new Square(4,1), new Square(4,7));
        moves = pawn.getPossibleMoves(board);
        assertTrue(moves.isEmpty());
        board.movePiece(new Square(4,7), new Square(4,6));
        moves = pawn.getPossibleMoves(board);
        assertEquals(1, moves.size());
        checkHasMove(4,7);
    }

    /**
     * Tests that the pawn correctly identifies possible capture moves diagonally from it.
     * A white and black pawn have been placed in these positions. It should only be able to attack one of them as
     * well as move forward two spaces.
     */
    @Test
    public void testPawnAttacks()
    {
        Piece obstacle1 = new Pawn(BLACK);
        Piece obstacle2 = new Pawn(WHITE);
        board.putPiece(obstacle1, 3,2);
        board.putPiece(obstacle2, 5,2);
        assertTrue(pawn.canAttack(board, new Square(3,2)));
        moves = pawn.getPossibleMoves(board);
        assertEquals(3, moves.size());
        checkHasMove(3,2);
        checkHasMove(4,2);
        checkHasMove(4,3);
    }

    /**
     * Tests that black pawns moves in the negative y direction.
     */
    @Test
    public void testBlackPawnMoveDirection()
    {
        Piece blackPawn = new Pawn(BLACK);
        board.putPiece(blackPawn, 5,6);
        moves = blackPawn.getPossibleMoves(board);
        assertEquals(2, moves.size());
        checkHasMove(5,5);
        checkHasMove(5,4);
    }
}
