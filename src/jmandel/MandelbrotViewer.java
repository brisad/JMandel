package jmandel;

import javax.swing.JFrame;

public class MandelbrotViewer {
    public static void main(String[] args) {
        new MandelbrotViewer().createAndShowGUI();
    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("Mandelbrot Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ComplexGrid grid = new ComplexGrid(-2.5, 1, -1, 1, 500, 500);
        Mandelbrot m = new Mandelbrot(grid);
        MandelbrotResult result = m.generate();
        MandelbrotPanel panel = new MandelbrotPanel(result.toImage());

        frame.getContentPane().add(panel);

        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
