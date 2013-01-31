package jmandel;

import java.awt.Dimension;

public class ComplexGrid {

    private final double rMin;
    private final double rMax;
    private final double iMin;
    private final double iMax;
    private final int width;
    private final int height;

    private final static double TOLERANCE = 1e-9;

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
            imag = -(iMax - iMin) / (height - 1) * iIndex + iMax;
        }
        return new Complex(real, imag);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ComplexGrid))
            return false;
        ComplexGrid otherGrid = (ComplexGrid) other;
        if (Math.abs(rMin - otherGrid.rMin) < TOLERANCE &&
            Math.abs(rMax - otherGrid.rMax) < TOLERANCE &&
            Math.abs(iMin - otherGrid.iMin) < TOLERANCE &&
            Math.abs(iMax - otherGrid.iMax) < TOLERANCE &&
            width == otherGrid.width &&
            height == otherGrid.height)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return (int)(Math.pow(31, 5) * rMin +
                     Math.pow(31, 4) * rMax +
                     Math.pow(31, 3) * iMin +
                     Math.pow(31, 2) * iMax +
                     31 * width + height);
    }
}
