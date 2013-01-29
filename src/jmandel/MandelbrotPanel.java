package jmandel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MandelbrotPanel extends JPanel implements  MouseMotionListener {

    private Image fractal;
    private ComplexGrid grid;
    private ArrayList<ComplexGridPositionListener> listeners;

    public MandelbrotPanel() {
        listeners = new ArrayList<ComplexGridPositionListener>();
        addMouseMotionListener(this);
    }

    public void generateFractal(double rMin, double rMax,
                                double iMin, double iMax) {
        grid = new ComplexGrid(rMin, rMax, iMin, iMax,
                               getWidth(), getHeight());
        fractal = new Mandelbrot(grid).generate().toImage();
        repaint();
    }

    public void addComplexGridPositionListener(ComplexGridPositionListener l) {
        listeners.add(l);
    }

    public void mouseMoved(MouseEvent e) {
        for (ComplexGridPositionListener l : listeners) {
            l.ComplexGridPositionUpdate(grid.index(e.getX(), e.getY()));
        }
    }

    public void mouseDragged(MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(fractal, 0, 0, getWidth(), getHeight(), null);
    }
}
