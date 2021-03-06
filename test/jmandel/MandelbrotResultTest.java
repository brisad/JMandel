package jmandel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

/**
 * Tests for {@link MandelbrotResult}.
 *
 * @author brennan.brisad@gmail.com (Michael Brennan)
 */

@RunWith(JUnit4.class)
public class MandelbrotResultTest {

    private int[][] fractal2x2;
    private int[][] fractal3x2;

    @Before
    public void setUp() {
        fractal2x2 = new int[][] { {0, 1}, {0, 2} };
        fractal3x2 = new int[][] { {0, 1, 3}, {7, 2, 5} };
    }

    @Test
    public void getSize_ReturnsSize() {
        MandelbrotResult mandelbrotResult = new MandelbrotResult(fractal3x2);
        Dimension expected = new Dimension(3, 2);
        Dimension actual = mandelbrotResult.getSize();
        assertEquals("Incorrect dimensions", expected, actual);
    }

    @Test
    public void index_ReturnsFractalData() {
        MandelbrotResult mandelbrotResult = new MandelbrotResult(fractal2x2);
        assertEquals(0, mandelbrotResult.index(0, 0));
        assertEquals(1, mandelbrotResult.index(1, 0));
        assertEquals(0, mandelbrotResult.index(0, 1));
        assertEquals(2, mandelbrotResult.index(1, 1));
    }

    @Test
    public void getArray_ReturnsFractalArray() {
        MandelbrotResult mandelbrotResult = new MandelbrotResult(fractal2x2);
        int[][] expected = fractal2x2;
        int[][] actual = mandelbrotResult.getArray();
        assertArrayEquals("Not same as fractal array", expected, actual);
    }

    @Test
    public void toImage_ReturnsBufferedImageWithCorrectSize() {
        MandelbrotResult mandelbrotResult = new MandelbrotResult(fractal3x2);
        BufferedImage image = mandelbrotResult.toImage();
        assertEquals("Widths not same", 3, image.getWidth());
        assertEquals("Heights not same", 2, image.getHeight());
    }

    @Test
    public void toImage_Default_ReturnsBufferedImageWithScaledBluePixels() {
        MandelbrotResult mandelbrotResult = new MandelbrotResult(fractal3x2);
        BufferedImage image = mandelbrotResult.toImage();
        int[] expected = {(0xff<<24), (0xff<<24) + 36, (0xff<<24) + 109,
                          (0xff<<24) + 255, (0xff<<24) + 73, (0xff<<24) + 182};
        int[] actual = image.getRGB(0, 0, 3, 2, null, 0, 3);
        assertArrayEquals("RGB data differs", expected, actual);
    }

    @Test
    public void equals_SameContents_ReturnsTrue() {
        MandelbrotResult result1 = new MandelbrotResult(fractal2x2);
        MandelbrotResult result2 = new MandelbrotResult(fractal2x2);
        assertEquals("Should be same", result1, result2);
    }

    @Test
    public void equals_DifferentContents_ReturnsFalse() {
        MandelbrotResult result1 = new MandelbrotResult(fractal2x2);
        MandelbrotResult result2 = new MandelbrotResult(fractal3x2);
        assertFalse(result1.equals(result2));
    }

    @Test
    public void hashCode_SameResultInHashSet_ContainsReturnsTrue() {
        MandelbrotResult result1 = new MandelbrotResult(fractal2x2);
        MandelbrotResult result2 = new MandelbrotResult(fractal2x2);
        HashSet<MandelbrotResult> coll = new HashSet<MandelbrotResult>();
        coll.add(result1);
        assertTrue(coll.contains(result2));
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("MandelbrotResultTest");
    }
}
