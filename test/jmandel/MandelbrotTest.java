package jmandel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.Dimension;

/**
 * Tests for {@link Mandelbrot}.
 *
 * @author brennan.brisad@gmail.com (Michael Brennan)
 */

@RunWith(JUnit4.class)
public class MandelbrotTest {
    @Test
    public void inSet_ReturnsTrue() {
        boolean inSet = Mandelbrot.inSet(new Complex(0, 1));
        assertTrue("Point should be in set", inSet);
    }

    @Test
    public void inSet_ReturnsFalse() {
        boolean inSet = Mandelbrot.inSet(new Complex(1, 0));
        assertFalse("Point should not be in set", inSet);
    }

    @Test
    public void generate_ReturnsCorrectSize() {
        Mandelbrot m = new Mandelbrot(new ComplexGrid(-1, 1, -1, 1, 3, 3));
        MandelbrotResult result = m.generate();
        assertEquals(new Dimension(3, 3), result.getSize());
    }

    @Test
    public void generate_ReturnsCorrectPoints() {
        Mandelbrot m = new Mandelbrot(new ComplexGrid(-1, 1, -1, 1, 3, 3));
        MandelbrotResult result = m.generate();
        assertTrue("Failed at (0, 0)", result.index(0, 0) > 0);
        assertEquals("Failed at (0, 1)", 0, result.index(0, 1));
        assertTrue("Failed at (0, 2)", result.index(0, 2) > 0);
        assertEquals("Failed at (1, 0)", 0, result.index(1, 0));
        assertEquals("Failed at (1, 1)", 0, result.index(1, 1));
        assertEquals("Failed at (1, 2)", 0, result.index(1, 2));
        assertTrue("Failed at (2, 0)", result.index(2, 0) > 0);
        assertTrue("Failed at (2, 1)", result.index(2, 1) > 0);
        assertTrue("Failed at (2, 2)", result.index(2, 2) > 0);
    }

    @Test
    public void generate_OnePoint_ReturnsCorrectNumberOfIterations() {
        Mandelbrot m = new Mandelbrot(new ComplexGrid(.5, .5, .5, .5, 1, 1));
        MandelbrotResult result = m.generate();
        assertEquals(5, result.index(0, 0));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("org.jmandel.MandelbrotTest");
    }
}
