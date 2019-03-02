package Chess;

import org.junit.Test;
import java.util.HashSet;

import static org.junit.Assert.*;

public class SquareTest
{

    @Test
    public void testConstructor()
    {
        Square sqr = new Square(1, -1);
        assertEquals(1, sqr.x);
        assertEquals(-1, sqr.y);
    }

    @Test
    public void testEquality()
    {
        Square s0 = new Square(1, -1),
               s1 = new Square(1, -1);
        assertEquals(s0, s1);
    }

    @Test
    public void testNonEquality()
    {
        Square s0 = new Square(1, -1),
               s1 = new Square(2, -3);
        assertNotEquals(s0, s1);
    }

    /**
     * Tests that the HashSet will not added duplicate squares with the same coordinates, even if they have
     * different memory addresses.
     */
    @Test
    public void testHashSetInsert()
    {
        Square s0 = new Square(1, -1),
               s1 = new Square(1, -1);
        HashSet<Square> hs = new HashSet<>();
        hs.add(s0);
        assertEquals(1, hs.size());
        hs.add(s1);
        assertEquals(1, hs.size());
        assertTrue(hs.contains(s0));
        assertTrue(hs.contains(s1));
    }

}