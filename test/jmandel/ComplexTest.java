package jmandel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;

/**
 * Tests for {@link Complex}.
 *
 * @author brennan.brisad@gmail.com (Michael Brennan)
 */

@RunWith(JUnit4.class)
public class ComplexTest {

    @Test
    public void equals_SameComplex_ReturnsTrue() {
        Complex z1 = new Complex(0.3, 2);
        Complex z2 = new Complex(3*0.1, 2);
        assertEquals("Should be same", z1, z2);
    }

    @Test
    public void equals_DifferentComplex_ReturnsFalse() {
        Complex z1 = new Complex(1, 2);
        Complex z2 = new Complex(2, 2);
        assertFalse("Should be different", z1.equals(z2));
    }

    @Test
    public void hashCode_SameComplexInHashSet_ContainsReturnsTrue() {
        Complex z1 = new Complex(1, 2);
        Complex z2 = new Complex(1, 2);
        HashSet<Complex> coll = new HashSet<Complex>();
        coll.add(z1);
        assertTrue(coll.contains(z2));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("ComplexTest");
    }
}
