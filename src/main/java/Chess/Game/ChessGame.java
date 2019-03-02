package Chess.Game;

import Chess.Pieces.*;

import java.io.IOException;

import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Pieces.Piece.pieceFromCode;

/**
 * Representation of a standard chess game.
 */
public class ChessGame extends Game
{

    /**
     * Sets up a standard chess board game.
     */
    public ChessGame()
    {
        super(8,8);
        setupPieces(WHITE);
        setupPieces(BLACK);
    }

    /**
     * Creates a Chess board arrangement given Strings specifying piece positions in the following 5 character format:<br>
     * [Piece-type][Color][x-coord][y-coord][is unmoved]<br>
     * [P|R|B|N|Q|K][B|W][0-7][0-7][T|F]
     * @param piecePositions String[]
     * @param currentTurnColor PieceColor
     */
    public ChessGame(String[] piecePositions, Piece.PieceColor currentTurnColor)
    {
        super(8,8);
        turnColor = currentTurnColor;
        for(String pieceCode : piecePositions)
            addPieceFromStringCode(pieceCode);
        validateState();
    }

    /**
     * Sets up the standard Chess piece arrangement for a color.
     * @param color PieceColor
     */
    private void setupPieces(Piece.PieceColor color)
    {
        boolean isWhite = color == WHITE;
        int pawnRow = isWhite? 1:6;
        int backRow = isWhite? 0:7;

        for(int x = 0; x < 8; x++)
            addPieceToGame(new Pawn(color), x, pawnRow);
        addPieceToGame(new Rook(color),   0, backRow);
        addPieceToGame(new Rook(color),   7, backRow);
        addPieceToGame(new Knight(color), 1, backRow);
        addPieceToGame(new Knight(color), 6, backRow);
        addPieceToGame(new Bishop(color), 2, backRow);
        addPieceToGame(new Bishop(color), 5, backRow);
        addPieceToGame(new Queen(color),  3, backRow);
        addPieceToGame(new King(color),   4, backRow);
    }

    /**
     * Takes a string representation of a piece and adds it to the game if it is valid.
     * @param pieceCode String
     */
    private void addPieceFromStringCode(String pieceCode)
    {
        if(pieceCode.matches("[P|R|B|N|Q|K][B|W][0-7][0-7][T|F]"))
        {
            char[] codes = pieceCode.toCharArray();
            Piece piece = pieceFromCode(codes[0], codes[1]);
            if(codes[4] == 'F') piece.markMoved();
            int x = Character.getNumericValue(codes[2]);
            int y = Character.getNumericValue(codes[3]);
            addPieceToGame(piece, x,y);
        }
    }

    /**
     * Returns string representation of the game.
     * @return String
     */
    @Override
    public String toString()
    {
        return "Standard (8x8) Chess Game";
    }
}
