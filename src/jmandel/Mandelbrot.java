package jmandel;

import java.awt.Dimension;


public class Mandelbrot {

    private final static int DEF_NUM_ITER = 100;
    private final ComplexGrid grid;

    public Mandelbrot(ComplexGrid grid) {
        this.grid = grid;
    }

    public MandelbrotResult generate() {
        Dimension d = grid.getSize();
        int result[][] = new int[d.height][d.width];

        for (int y = 0; y < d.height; y++) {
            for (int x = 0; x < d.height; x++) {
                Complex c = grid.index(x, y);
                result[y][x] = iterations(c, DEF_NUM_ITER);
            }
        }
        return new MandelbrotResult(result);
    }

    private static int iterations(Complex c, int noIterations) {
        double zr;
        double zi;
        double tmp;
        int count = 0;

        zr = zi = 0;
        while (zr * zr + zi * zi < 4 && count < noIterations) {
            tmp = zr * zr - zi * zi + c.real;
            zi = 2 * zr * zi + c.imag;
            zr = tmp;
            count++;
        }

        if (zr * zr + zi * zi < 4)
            return 0;
        return count;
    }

    public static boolean inSet(Complex c) {
        return iterations(c, DEF_NUM_ITER) == 0;
    }
}
