package Chess.Game;

import Chess.Move;
import Chess.Pieces.*;
import Chess.Board;
import Chess.Square;
import Chess.Pieces.Piece.PieceColor;

import static Chess.Pieces.Piece.PieceColor.*;
import static Chess.Game.Game.GameState.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Representation of a standard chess game.
 */
public abstract class Game
{

    /**
     * Enums representation the current state of the game.
     */
    public enum GameState {PLAYING, WHITE_IN_CHECK, BLACK_IN_CHECK, STALEMATE, WHITE_WINS, BLACK_WINS}

    public final int boardX;
    public final int boardY;
    protected Board chessboard;

    protected HashSet<Piece> whitePieces = new HashSet<>();
    protected HashSet<Piece> blackPieces = new HashSet<>();
    protected Piece whiteKing;
    protected Piece blackKing;

    protected List<Move> moveList = new ArrayList<>();
    protected PieceColor turnColor = WHITE;
    protected GameState state = PLAYING;
    protected boolean gameEnded = false;


    /**
     * Super class constructor. Sets the dimension of the board the game will be played on.
     * @param boardXLen int
     * @param boardYLen int
     */
    public Game(int boardXLen, int boardYLen)
    {
        boardX = boardXLen;
        boardY = boardYLen;
        chessboard = new Board(boardXLen, boardYLen);
    }

    /**
     * Runs a single loop of the game. Will attempt to make a legal move, and then swap the turn color and validate
     * the state of game if the move was successful.
     * @param from Square
     * @param dest Square
     */
    public void runTurn(Square from, Square dest)
    {
        if(!gameEnded && makeMove(from, dest))
        {
            swapTurnColor();
            validateState();
        }
    }

    /**
     * Obtains the potential moves of the piece at the square and filters out non-legal moves. Non-legal moves are any
     * that put the piece's own King into danger.
     * @param from Square
     * @return HashSet<Square>
     */
    public HashSet<Square> getLegalMoves(Square from)
    {
        Piece piece = chessboard.getPiece(from);
        return getLegalMoves(piece);
    }

    /**
     * Obtains the potential moves of the specified piece and filters out non-legal moves. Non-legal moves are any
     * that put the piece's own King into danger.
     * @param piece Piece
     * @return HashSet<Square>
     */
    public HashSet<Square> getLegalMoves(Piece piece)
    {
        if(gameEnded || piece == null || piece.getColor() != turnColor) return new HashSet<>();
        HashSet<Square> potentialMoves = piece.getPossibleMoves(chessboard);
        if(potentialMoves == null) return new HashSet<>();

        Piece currentColorKing = getCurrentColorKing();
        HashSet<Piece> opposingPieces = getOpposingPieces();
        Square origSqr = piece.getLocation();
        HashSet<Square> legalMoves = new HashSet<>();

        for(Square dest : potentialMoves)   // Attempt each potential move, and check that king is not in danger
        {
            Piece cappedPiece = chessboard.getPiece(dest);
            opposingPieces.remove(cappedPiece);
            chessboard.removePiece(dest);
            chessboard.movePiece(origSqr, dest);
            if(isKingSafe(chessboard, currentColorKing.getLocation(), opposingPieces))
                legalMoves.add(dest);

            // Move the piece back
            chessboard.movePiece(dest, origSqr);
            if(cappedPiece != null)          // Restore the piece that was captured (if it exists)
            {
                chessboard.putPiece(cappedPiece, dest);
                opposingPieces.add(cappedPiece);
            }
        }
        return legalMoves;
    }

    /**
     * Validates the state of game by checking if the current color is in check and then checking if current color is
     * in check, and then checking if the current color has moves.
     */
    public void validateState()
    {
        if(gameEnded) return;
        state = PLAYING;
        Piece currentColorKing = getCurrentColorKing();
        HashSet<Piece> opposingPieces = getOpposingPieces();

        // Check if current color is in check
        if(!isKingSafe(chessboard, currentColorKing.getLocation(), opposingPieces))
        {
            if(isWhitesTurn()) state = WHITE_IN_CHECK;
            else state = BLACK_IN_CHECK;
        }
        // Check if current color can still move.
        if(!currentColorHasMoves())
        {
            gameEnded = true;
            if(state == WHITE_IN_CHECK) state = BLACK_WINS;
            else if(state == BLACK_IN_CHECK) state = WHITE_WINS;
            else state = STALEMATE;
        }
    }

    /**
     * Undo the last move made. Will swap the turn color and revalidate the state of the board. Returns the Move object
     * representing the last move. Returns null if no moves have been made.
     * @return Move
     */
    public Move undoMove()
    {
        if(moveList.isEmpty()) return null;
        // Restore the board to the state before the move
        Move lastMove = moveList.remove(moveList.size() - 1);
        Piece movedPiece = lastMove.movedPiece;
        Piece cappedPiece = lastMove.capturedPiece;
        Square from = lastMove.from;
        Square dest = lastMove.dest;

        chessboard.movePiece(dest, from);
        // If the moved piece was previously unmoved, restore that too.
        if(!lastMove.hadMoved) movedPiece.markUnmoved();
        // Add the captured piece back to the board and to its respectively team
        addPieceToGame(cappedPiece, dest.x, dest.y);

        swapTurnColor();
        validateState();
        return lastMove;
    }

    /**
     * Check if the current moving color has any legal moves it can make. In the case that there are no moves to make
     * and check has occurred, a checkmate occurs. If check has not occured but there are still no legal moves, it is a
     * stalemate. Returns true if the current color has any legal moves left.
     */
    public boolean currentColorHasMoves()
    {
        if(gameEnded) return false;
        HashSet<Piece> defenders = (isWhitesTurn())? whitePieces : blackPieces;
        for(Piece defender : defenders)
        {
            HashSet<Square> potentialDefense = getLegalMoves(defender);
            if(!potentialDefense.isEmpty()) return true;
        }
        return false;
    }

    /**
     * Returns a shallow copy of the piece roster. Behavior of the game is not defined should you choose to modify the
     * state of the pieces.
     * @return HashSet<Piece>
     */
    public HashSet<Piece> getAllPieces()
    {
        HashSet<Piece> allPieces = new HashSet<>();
        allPieces.addAll(whitePieces);
        allPieces.addAll(blackPieces);
        return allPieces;
    }

    /**
     * Returns an enum representing the current state of the game.
     * @return GameState
     */
    public GameState getGameState()
    {
        return state;
    }

    /**
     * Returns the PieceColor representing the current turn.
     * @return PieceColor
     */
    public PieceColor getTurnColor()
    {
        return turnColor;
    }

    /**
     * Returns the PieceColor representing the opposing side.
     * @return PieceColor
     */
    public PieceColor getOpposingColor()
    {
        return (turnColor == WHITE)? BLACK : WHITE;
    }

    /**
     * Returns true if it is white's turn.
     * @return boolean
     */
    public boolean isWhitesTurn()
    {
        return turnColor == WHITE;
    }

    /**
     * Returns true if it is black's turn.
     * @return boolean
     */
    public boolean isBlacksTurn()
    {
        return turnColor == BLACK;
    }

    /**
     * Switches the turn color to the opposite color.
     */
    protected void swapTurnColor()
    {
        turnColor = getOpposingColor();
    }

    /**
     * Returns a reference to the King of the currently moving color.
     * @return Piece
     */
    protected Piece getCurrentColorKing()
    {
        return (isWhitesTurn())? whiteKing : blackKing;
    }

    /**
     * Returns thte set of pieces of the opposing color
     * @return HashSet<Piece>
     */
    protected HashSet<Piece> getOpposingPieces()
    {
        return (isWhitesTurn())? blackPieces : whitePieces;
    }

    /**
     * Given a piece square and a destination square, will attempt to make a move if it is a legal move.
     * Returns true to indicate the move was successful, otherwise false.
     * @param from Square
     * @param dest Square
     * @return Piece
     */
    protected boolean makeMove(Square from, Square dest)
    {
        if(gameEnded) return false;
        Piece piece = chessboard.getPiece(from);
        if(piece == null || dest == null ||
                piece.getColor() != turnColor) return false;
        HashSet<Square> legalMoves = getLegalMoves(piece);
        if(legalMoves.contains(dest))
        {
            Piece capturedPiece = chessboard.getPiece(dest);
            // Record the move
            moveList.add(new Move(from, dest, piece, capturedPiece));

            chessboard.removePiece(dest);
            getOpposingPieces().remove(capturedPiece);
            chessboard.movePiece(from, dest);
            piece.markMoved();
            return true;
        }
        return false;
    }

    /**
     * Returns true if the current color's king is in a safe position.
     * @param board Board
     * @param kingLocation Square
     * @param opposing HashSet<Piece>
     * @return boolean
     */
    protected boolean isKingSafe(Board board, Square kingLocation, HashSet<Piece> opposing)
    {
        for(Piece enemy : opposing)
            if (enemy.canAttack(board, kingLocation))
                return false;
        return true;
    }

    /**
     * Adds a piece to the board and to the appropriate piece set. Restricts the number of kings to one for each color.
     * @param piece Piece
     * @param x int
     * @param y int
     */
    protected void addPieceToGame(Piece piece, int x, int y)
    {
        if(piece == null) return;
        boolean isWhite = piece.getColor() == WHITE;
        if(piece.isKing())
        {
            if(isWhite && whiteKing == null)
                whiteKing = piece;
            else if(!isWhite && blackKing == null)
                blackKing = piece;
            else return;
        }
        HashSet<Piece> pieceSet = (isWhite)? whitePieces : blackPieces;
        pieceSet.add(piece);
        chessboard.putPiece(piece, x,y);
    }
}
