package Chess.Pieces;

import Chess.Square;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static Chess.Pieces.Piece.pieceFromCode;
import static org.junit.Assert.*;

import static Chess.Pieces.Piece.PieceColor.*;

/**
 * These tests test all the basic functionality inherited from the abstract Piece class.
 */
public class PieceTest
{

    private Piece piece;

    @Before
    public void setup()
    {
        piece = new Pawn(WHITE);
    }

    public void testGetColor()
    {
        assertEquals(WHITE, piece.getColor());
        Piece blackPiece = new Queen(BLACK);
        assertEquals(BLACK, blackPiece.getColor());
    }

    @Test
    public void testMarkMoved()
    {
        assertFalse(piece.hasMoved());
        piece.markMoved();
        assertTrue(piece.hasMoved());
    }

    @Test
    public void testSetLocation()
    {
        assertNull(piece.getLocation());
        piece.setLocation(0,0);
        Assert.assertEquals(new Square(0,0), piece.getLocation());
    }

    private void testPieceFromCode(Piece p, char pieceType, char pieceColor)
    {
        Piece.PieceColor color = (pieceColor == 'B')? BLACK : WHITE;
        assertEquals(color, p.getColor());
        switch (pieceType)
        {
            case 'K': assertTrue(p instanceof King); break;
            case 'Q': assertTrue(p instanceof Queen); break;
            case 'B': assertTrue(p instanceof Bishop); break;
            case 'N': assertTrue(p instanceof Knight); break;
            case 'R': assertTrue(p instanceof Rook); break;
            case 'P': assertTrue(p instanceof Pawn); break;
            default : assertTrue(p instanceof Pawn); break;
        }
    }

    @Test
    public void testAllPieceFromCode()
    {
        char[] pieceTypeCodes = {'K', 'Q', 'B', 'N', 'R', 'P'};
        for(char pieceCode : pieceTypeCodes)
        {
            Piece blkPiece = pieceFromCode(pieceCode, 'B');
            testPieceFromCode(blkPiece, pieceCode, 'B');
            Piece whtPiece = pieceFromCode(pieceCode, 'W');
            testPieceFromCode(whtPiece, pieceCode, 'W');
        }
    }
}