package jmandel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MandelbrotPanel extends JPanel
    implements MouseListener, MouseMotionListener {

    private Image fractalImage;
    private MandelbrotZoomer zoomer;
    private ArrayList<ComplexGridPositionListener> listeners;
    private Complex selectedCoordinates;

    public MandelbrotPanel() {
        listeners = new ArrayList<ComplexGridPositionListener>();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void init(double rMin, double rMax, double iMin, double iMax) {
        zoomer = new MandelbrotZoomer(new ComplexGrid(rMin, rMax, iMin, iMax,
                                                      getWidth(),
                                                      getHeight()));
    }

    public void generateAndDisplayFractal() {
        zoomer.generate();
        fractalImage = zoomer.getMandelbrotResult().toImage();
        repaint();
    }

    public void addComplexGridPositionListener(ComplexGridPositionListener l) {
        listeners.add(l);
    }

    private Complex pointToComplexCoordinates(Point p) {
        ComplexGrid grid = zoomer.getComplexGrid();
        return grid.index(p.x * grid.getSize().width / getWidth(),
                          p.y * grid.getSize().height / getHeight());
    }

    private Point complexCoordinatesToPoint(Complex c) {
        ComplexGrid grid = zoomer.getComplexGrid();
        return new Point((int)(getWidth() *
                               (c.real - grid.rMin) / (grid.rMax - grid.rMin)),
                         (int)(getHeight() *
                               (c.imag - grid.iMax) / (grid.iMin - grid.iMax)));
    }

    public void zoom(double factor) {
        if (selectedCoordinates != null) {
            zoomer.zoom(factor, selectedCoordinates);
            generateAndDisplayFractal();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (ComplexGridPositionListener l : listeners) {
            Complex coordinates = pointToComplexCoordinates(e.getPoint());
            l.ComplexGridPositionUpdate(coordinates);
        }
    }

    public void mouseDragged(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
        selectedCoordinates = pointToComplexCoordinates(e.getPoint());
        repaint();
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(fractalImage, 0, 0, getWidth(), getHeight(), null);
        drawCrosshair(g2);
    }

    private void drawCrosshair(Graphics2D g2) {
        if (selectedCoordinates != null) {
            Point p = complexCoordinatesToPoint(selectedCoordinates);
            g2.setPaint(Color.red);
            g2.draw(new Line2D.Double(p.x - 5, p.y, p.x + 5, p.y));
            g2.draw(new Line2D.Double(p.x, p.y - 5, p.x, p.y + 5));
        }
    }
}
