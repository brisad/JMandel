package jmandel;

import java.awt.Dimension;


public class Mandelbrot {

    public final static int DEFAULT_NUM_ITERATIONS = 100;
    private final ComplexGrid grid;

    public Mandelbrot(ComplexGrid grid) {
        this.grid = grid;
    }

    public MandelbrotResult generate() {
        return generate(DEFAULT_NUM_ITERATIONS);
    }

    public MandelbrotResult generate(int noIterations) {
        Dimension d = grid.getSize();
        int result[][] = new int[d.height][d.width];

        for (int y = 0; y < d.height; y++) {
            for (int x = 0; x < d.width; x++) {
                Complex c = grid.index(x, y);
                result[y][x] = iterations(c, noIterations);
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
        return iterations(c, DEFAULT_NUM_ITERATIONS) == 0;
    }
}
