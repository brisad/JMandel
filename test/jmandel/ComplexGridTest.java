package jmandel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
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
    public void index_PointOnDegenerateGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(1, 1, 2, 2, 1, 1);
        Complex expected = new Complex(1, 2);
        Complex actual = grid.index(0, 0);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_PointOnDegenerateRealAxis_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(1, 1, 0, 2, 1, 2);
        Complex expected = new Complex(1, 2);
        Complex actual = grid.index(0, 0);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_PointOnDegenerateImagAxis_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(0, 1, 2, 2, 2, 1);
        Complex expected = new Complex(0, 2);
        Complex actual = grid.index(0, 0);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_LowerIndexFromNormalGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(-1, 1, -1, 1, 20, 40);
        Complex expected = new Complex(-1, 1);
        Complex actual = grid.index(0, 0);
        assertEquals("Incorrect coordinates", expected, actual);
    }

    @Test
    public void index_UpperIndexFromNormalGrid_ReturnsComplexCoordinates() {
        ComplexGrid grid = new ComplexGrid(0, 1, 0, 1, 2, 5);
        Complex expected = new Complex(1, 0);
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

    @Test
    public void equals_SameData_ReturnsTrue() {
        ComplexGrid grid1 = new ComplexGrid(-1, 1, -2, 3, 7, 11);
        ComplexGrid grid2 = new ComplexGrid(-1, 1, -2, 3, 7, 11);
        assertEquals("Should be same", grid1, grid2);
    }

    @Test
    public void equals_DifferentData_ReturnsFalse() {
        ComplexGrid grid1 = new ComplexGrid(-1, 1, -2, 3, 7, 11);
        ComplexGrid grid2 = new ComplexGrid(1, -1, -2, 3, 7, 11);
        assertFalse("Should be different", grid1.equals(grid2));
    }

    @Test
    public void hashCode_SameGridInHashSet_ContainsReturnsTrue() {
        ComplexGrid grid1 = new ComplexGrid(-1, 1, -2, 3, 7, 11);
        ComplexGrid grid2 = new ComplexGrid(-1, 1, -2, 3, 7, 11);
        HashSet<ComplexGrid> coll = new HashSet<ComplexGrid>();
        coll.add(grid1);
        assertTrue(coll.contains(grid2));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("ComplexGridTest");
    }
}
