package jmandel;

import java.awt.Dimension;

public class ComplexGrid {

    public final double rMin;
    public final double rMax;
    public final double iMin;
    public final double iMax;
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
        } else {
            real = (rMax - rMin) / (width - 1) * rIndex + rMin;
        }

        if (height == 1) {
            imag = iMax;
        } else {
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
        return (int)Math.pow(31, 5) * Double.valueOf(rMin).hashCode() +
            (int)Math.pow(31, 4) * Double.valueOf(rMax).hashCode() +
            (int)Math.pow(31, 3) * Double.valueOf(iMin).hashCode() +
            (int)Math.pow(31, 2) * Double.valueOf(iMax).hashCode() +
            31 * width + height;
    }
}
