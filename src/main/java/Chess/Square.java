package Chess;

/**
 * Representation of a square on a game board
 */
public final class Square
{
    public final int x;
    public final int y;

    /**
     * Constructs a square from the given x and y coordinates
     * @param x int
     * @param y int
     */
    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Overrides the java.lang.Object equals() function. Squares are equal if they refer to the same coordinates.
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Square)
        {
            Square sqr = (Square) obj;
            return (this.x == sqr.x) && (this.y == sqr.y);
        }
        return false;
    }

    /**
     * Overrides the java.lang.Object hashCode() function. Always 0 to force .equals() to be used.
     * @return int
     */
    @Override
    public int hashCode()
    {
        return 0;
    }
}
