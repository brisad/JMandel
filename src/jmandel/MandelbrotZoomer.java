package jmandel;

public class MandelbrotZoomer {

    private ComplexGrid grid;
    private MandelbrotResult result;
    private int noIterations;

    public MandelbrotZoomer(ComplexGrid grid) {
        this.grid = grid;
    }

    public void zoom(double factor) {
        Complex center = new Complex((grid.rMin + grid.rMax) / 2,
                                     (grid.iMin + grid.iMax) / 2);
        zoom(factor, center);
    }

    public void zoom(double factor, Complex center) {
        double realSize = grid.rMax - grid.rMin;
        double imagSize = grid.iMax - grid.iMin;
        grid = new ComplexGrid(center.real - realSize / (2 * factor),
                               center.real + realSize / (2 * factor),
                               center.imag - imagSize / (2 * factor),
                               center.imag + imagSize / (2 * factor),
                               grid.getSize().width, grid.getSize().height);
    }

    public void setBounds(double rMin, double rMax, double iMin, double iMax) {
        grid = new ComplexGrid(rMin, rMax, iMin, iMax,
                               grid.getSize().width, grid.getSize().height);
    }

    public void setResolution(int width, int height) {
        grid = new ComplexGrid(grid.rMin, grid.rMax, grid.iMin, grid.iMax,
                               width, height);
    }

    public ComplexGrid getComplexGrid() {
        return grid;
    }

    public void setIterations(int noIterations) {
        this.noIterations = noIterations;
    }

    public int getIterations() {
        if (noIterations > 0)
            return noIterations;
        else
            return Mandelbrot.DEFAULT_NUM_ITERATIONS;
    }

    public void generate() {
        if (noIterations > 0)
            result = new Mandelbrot(grid).generate(noIterations);
        else
            result = new Mandelbrot(grid).generate();
    }

    public MandelbrotResult getMandelbrotResult() {
        return result;
    }
}
