package jmandel;

public final class Complex {
    public final double real;
    public final double imag;

    private final static double TOLERANCE = 1e-9;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Complex) {
            Complex that = (Complex) other;
            result = Math.abs(this.real - that.real) < TOLERANCE
                && Math.abs(this.imag - that.imag) < TOLERANCE;
        }
        return result;
    }

    @Override
    public int hashCode() {
        return (int)((real * 7) + imag) * 31;
    }

    @Override
    public String toString() {
        return String.format("%f + %fi", real, imag);
    }
}
