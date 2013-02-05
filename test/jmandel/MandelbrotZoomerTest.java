package jmandel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link MandelbrotZoomer}.
 *
 * @author brennan.brisad@gmail.com (Michael Brennan)
 */

@RunWith(JUnit4.class)
public class MandelbrotZoomerTest {

    private ComplexGrid defaultGrid;
    private MandelbrotZoomer zoomer;

    @Before
    public void setUp() {
        defaultGrid = new ComplexGrid(-2.5, 1, -1, 1, 10, 10);
        zoomer = new MandelbrotZoomer(defaultGrid);
    }

    @Test
    public void zoom_WithFactorGreaterThanOne_ZoomsInComplexGridInCenter() {
        zoomer.zoom(2);
        ComplexGrid expected = new ComplexGrid(-1.625, 0.125, -0.5, 0.5,
                                               10, 10);
        ComplexGrid actual = zoomer.getComplexGrid();
        assertEquals(expected, actual);
    }

    @Test
    public void zoom_WithFactorLessThanOne_ZoomsOutComplexGridInCenter() {
        zoomer.zoom(0.5);
        ComplexGrid expected = new ComplexGrid(-4.25, 2.75, -2, 2, 10, 10);
        ComplexGrid actual = zoomer.getComplexGrid();
        assertEquals(expected, actual);
    }

    @Test
    public void zoom_WithFactorAndCoordinates_ZoomsComplexGridAtCoordinates() {
        zoomer.zoom(2, new Complex(1, 0.5));
        ComplexGrid expected = new ComplexGrid(0.125, 1.875, 0, 1, 10, 10);
        ComplexGrid actual = zoomer.getComplexGrid();
        assertEquals(expected, actual);
    }

    @Test
    public void setBounds_WithBoundaries_SetsComplexGrid() {
        zoomer.setBounds(-1, 1, 0, 10);
        ComplexGrid expected = new ComplexGrid(-1, 1, 0, 10, 10, 10);
        ComplexGrid actual = zoomer.getComplexGrid();
        assertEquals(expected, actual);
    }

    @Test
    public void generate_DefaultGrid_GeneratesMandelbrotResultFractal() {
        zoomer.generate();
        MandelbrotResult expected = new Mandelbrot(defaultGrid).generate();
        MandelbrotResult actual = zoomer.getMandelbrotResult();
        assertEquals(expected, actual);
    }

    @Test
    public void setResolution_DefaultGrid_ChangesComplexGridWidthAndHeight() {
        zoomer.setResolution(100, 200);
        ComplexGrid expected = new ComplexGrid(-2.5, 1, -1, 1, 100, 200);
        ComplexGrid actual = zoomer.getComplexGrid();
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("MandelbrotZoomerTest");
    }
}
