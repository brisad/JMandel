package jmandel;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class MandelbrotResult {

    private final int[][] fractal;

    public MandelbrotResult(int[][] fractal) {
        this.fractal = fractal;
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

    public BufferedImage toImage() {

        BufferedImage image = new BufferedImage(fractal[0].length,
                                                fractal.length,
                                                BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < fractal.length; y++) {
            for (int x = 0; x < fractal[y].length; x++) {
                image.setRGB(x, y, fractal[y][x]);
            }
        }

        return image;
    }
}
