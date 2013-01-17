package jmandel;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class MandelbrotResult {

    private final int[][] fractal;
    private final int maxValue;

    public MandelbrotResult(int[][] fractal) {
        this.fractal = fractal;
        maxValue = findMax();
    }

    public Dimension getSize() {
        return new Dimension(fractal[0].length, fractal.length);
    }

    public int index(int x, int y) {
        return fractal[y][x];
    }

    public int[][] getArray() {
        return fractal;
    }

    private int findMax() {
        int max = fractal[0][0];
        for (int y = 0; y < fractal.length; y++) {
            for (int x = 0; x < fractal[y].length; x++) {
                if (fractal[y][x] > max)
                    max = fractal[y][x];
            }
        }
        return max;
    }

    public BufferedImage toImage() {

        BufferedImage image = new BufferedImage(fractal[0].length,
                                                fractal.length,
                                                BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < fractal.length; y++) {
            for (int x = 0; x < fractal[y].length; x++) {
                image.setRGB(x, y, (int)Math.round(255 * fractal[y][x] /
                                                   (double)maxValue));
            }
        }

        return image;
    }
}
