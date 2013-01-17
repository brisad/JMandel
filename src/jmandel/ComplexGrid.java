package jmandel;

import java.awt.Dimension;


public class ComplexGrid {

    private final double rMin;
    private final double rMax;
    private final double iMin;
    private final double iMax;
    private final int width;
    private final int height;

    public ComplexGrid(double rMin, double rMax, double iMin, double iMax,
                       int width, int height) {
        this.width = width;
        this.height = height;
        this.rMin = rMin;
        this.rMax = rMax;
        this.iMin = iMin;
        this.iMax = iMax;
    }

    public Dimension getSize() {
        return new Dimension(width, height);
    }

    public Complex index(int rIndex, int iIndex) {
        double real;
        double imag;

        if (width == 1) {
            real = rMin;
            imag = iMin;
        } else {
            real = (rMax - rMin) / (width - 1) * rIndex + rMin;
            imag = (iMax - iMin) / (height - 1) * iIndex + iMin;
        }
        return new Complex(real, imag);
    }

}
