package Chess.Pieces;

import Chess.Board;
import Chess.Square;

import java.util.HashSet;

import static junit.framework.Assert.assertTrue;

public class PieceTester
{
    protected Board board;
    protected HashSet<Square> moves;

    /**
     * Helper function to check that the Set of moves contain the specified square.
     */
    protected void checkHasMove(int x, int y)
    {
        assertTrue(moves.contains(new Square(x, y)));
    }
}
