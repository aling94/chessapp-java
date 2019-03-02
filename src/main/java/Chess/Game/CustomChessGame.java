package Chess.Game;

import Chess.Board;
import Chess.Pieces.*;

import java.io.IOException;

import static Chess.Pieces.Piece.PieceColor.*;

/**
 * A custom game of chess played on a 10x8 board with two custom pieces, Princess and Empress, added to the game.
 * Standard rules of chess still apply.
 */
public class CustomChessGame extends Game
{

    /**
     * Sets up a 10x8 chessboard with the Princess and Empress custom pieces added.
     */
    public CustomChessGame()
    {
        super(10, 8);
        setupPieces(WHITE);
        setupPieces(BLACK);
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

        for(int x = 1; x < 9; x++)
            addPieceToGame(new Pawn(color), x, pawnRow);

        addPieceToGame(new Princess(color), 0, pawnRow);
        addPieceToGame(new Princess(color), 9, pawnRow);
        addPieceToGame(new Empress(color),  0, backRow);
        addPieceToGame(new Empress(color),  9, backRow);
        addPieceToGame(new Rook(color),     1, backRow);
        addPieceToGame(new Rook(color),     8, backRow);
        addPieceToGame(new Knight(color),   2, backRow);
        addPieceToGame(new Knight(color),   7, backRow);
        addPieceToGame(new Bishop(color),   3, backRow);
        addPieceToGame(new Bishop(color),   6, backRow);
        addPieceToGame(new Queen(color),    4, backRow);
        addPieceToGame(new King(color),     5, backRow);
    }

    /**
     * Returns string representation of the game.
     * @return String
     */
    @Override
    public String toString()
    {
        return "Custom (10x8) Chess Game";
    }
}
