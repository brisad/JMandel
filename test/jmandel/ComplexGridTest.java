package jmandel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.awt.Dimension;

/**
 * Tests for {@link ComplexGrid}.
 *
 * @author brennan.brisad@gmail.com (Michael Brennan)
 */

@RunWith(JUnit4.class)
public class ComplexGridTest {

    @Test
    public void getSize_ReturnsSize() {
        ComplexGrid grid = new ComplexGrid(0, 1, 0, 1, 20, 40);
        Dimension expected = new Dimension(20, 40);
        Dimension actual = grid.getSize();
        assertEquals("Incorrect dimensions", expected, actual);
    }

    @Test
    public void index_PointFromDegenerateGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(1, 1, 2, 2, 1, 1);
        Complex expected = new Complex(1, 2);
        Complex actual = grid.index(0, 0);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_LowerIndexFromNormalGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(-1, 1, -1, 1, 20, 40);
        Complex expected = new Complex(-1, -1);
        Complex actual = grid.index(0, 0);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_UpperIndexFromNormalGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(0, 1, 0, 1, 2, 5);
        Complex expected = new Complex(1, 1);
        Complex actual = grid.index(1, 4);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_MiddleIndexFromNormalGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(-1, 1, -1, 1, 3, 9);
        Complex expected = new Complex(0, 0);
        Complex actual = grid.index(1, 4);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("ComplexGridTest");
    }
}
