package Chess;

import Chess.Pieces.Piece;

/**
 * Representation of a rectangular game board as a piece array.
 */
public class Board
{
    public final int xLength;
    public final int yLength;

    private Piece[][] board;

    /**
     * Default constructor. Initializes an empty 8x8 board.
     */
    public Board()
    {
        board = new Piece[8][8];
        xLength = yLength = 8;
    }

    /**
     * Constructors a rectangular board of dimensions xLen by yLen. Both dimensions must be positive. Defaults to
     * 8x8 board if not.
     * @param xLen int
     * @param yLen int
     */
    public Board(int xLen, int yLen)
    {
        if(xLen <= 0 || yLen <= 0)
            xLen = yLen = 8;
        board = new Piece[xLen][yLen];
        xLength = xLen;
        yLength = yLen;
    }

    /**
     * Moves a piece from Square from to Square dest. Will also update the piece's internal Square location.
     * Does nothing if from and dest refer to the same square, are invalid squares, or if from is empty.
     * @param from Square
     * @param dest Square
     */
    public void movePiece(Square from, Square dest)
    {
        if(from.equals(dest)) return;
        if(isValidSqr(from) && isValidSqr(dest) && hasPiece(from))
        {
            Piece movedPiece = board[from.x][from.y];
            board[from.x][from.y] = null;
            board[dest.x][dest.y] = null;
            movedPiece.setLocation(dest);
            board[dest.x][dest.y] = movedPiece;
        }
    }

    /**
     * Returns true if coordinates refer to valid square on the board.
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean isValidSqr(int x, int y)
    {
        return (x >= 0) && (x < xLength) && (y >= 0) && (y < yLength);
    }

    /**
     * Returns true if the square refer to valid square on the board.
     * @param sqr Square
     * @return boolean
     */
    public boolean isValidSqr(Square sqr)
    {
        return (sqr != null) && isValidSqr(sqr.x, sqr.y);
    }

    /**
     * Returns the piece at the coordinates. Null if no piece exists or invalid coordinates.
     * @param x int
     * @param y int
     * @return Piece
     */
    public Piece getPiece(int x, int y)
    {
        if(isValidSqr(x,y)) return board[x][y];
        return null;
    }

    /**
     * Returns the piece at the square. Null if no piece exists or invalid square.
     * @param sqr Square
     * @return Piece
     */
    public Piece getPiece(Square sqr)
    {
        if(sqr == null) return null;
        return getPiece(sqr.x, sqr.y);
    }

    /**
     * Places a piece onto the board. Does nothing if invalid coordinates.
     * @param x int
     * @param y int
     */
    public void putPiece(Piece piece, int x, int y)
    {
        if(isValidSqr(x, y))
        {
            board[x][y] = piece;
            if(piece != null) piece.setLocation(x, y);
        }
    }

    /**
     * Places a piece onto the board. Does nothing if invalid square.
     * @param sqr Square
     */
    public void putPiece(Piece piece, Square sqr)
    {
        if(sqr != null) putPiece(piece, sqr.x, sqr.y);
    }

    /**
     * Removes a piece onto the board. Does nothing if invalid coordinates.
     * @param x int
     * @param y int
     */
    public void removePiece(int x, int y)
    {
        if(isValidSqr(x, y)) board[x][y] = null;
    }

    /**
     * Removes a piece onto the board. Does nothing if invalid square.
     * @param sqr Square
     */
    public void removePiece(Square sqr)
    {
        if(sqr != null) removePiece(sqr.x, sqr.y);
    }

    /**
     * Returns true if the board has a piece on the specified coordinates. False if empty or invalid coordinates.
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean hasPiece(int x, int y)
    {
        return isValidSqr(x, y) && board[x][y] != null;
    }

    /**
     * Returns true if the board has a piece on the specified coordinates. False if empty or invalid square.
     * @param sqr Square
     * @return boolean
     */
    public boolean hasPiece(Square sqr)
    {
        return (sqr != null) && hasPiece(sqr.x, sqr.y);
    }

    /**
     * Returns true if square is empty. False if occupied or invalid coordinates.
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean isOpenSquare(int x, int y)
    {
        return isValidSqr(x, y) && board[x][y] == null;
    }

    /**
     * Returns the color of the piece at the specified coordinates. Null if square is empty or invalid square.
     * @param x int
     * @param y int
     * @return PieceColor
     */
    public Piece.PieceColor getPieceColor(int x, int y)
    {
        if(hasPiece(x, y)) return board[x][y].getColor();
        return null;
    }
}
