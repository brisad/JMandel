package jmandel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
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

    private enum SelectionState { NONE, POINT, RECTANGLE };
    private Selection selection;
    private int lastButtonDown;

    public MandelbrotPanel() {
        listeners = new ArrayList<ComplexGridPositionListener>();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void init(double rMin, double rMax, double iMin, double iMax) {
        zoomer = new MandelbrotZoomer(new ComplexGrid(rMin, rMax, iMin, iMax,
                                                      getWidth(),
                                                      getHeight()));
        selection = new Selection();
    }

    public void updateResolution() {
        zoomer.setResolution(getWidth(), getHeight());
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
        if (selection.state != SelectionState.NONE) {
            updateResolution();
            zoomer.zoom(factor, selection.getCenter());
            generateAndDisplayFractal();
        }
    }

    public void zoomRegion() {
        if (selection.state == SelectionState.RECTANGLE) {
            updateResolution();
            zoomer.setBounds(selection.getrMin(),
                             selection.getrMax(),
                             selection.getiMin(),
                             selection.getiMax());
            generateAndDisplayFractal();
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (ComplexGridPositionListener l : listeners) {
            Complex coordinates = pointToComplexCoordinates(e.getPoint());
            l.ComplexGridPositionUpdate(coordinates);
        }
    }

    private void extendSelection(Point p) {
        if (lastButtonDown == MouseEvent.BUTTON1) {
            selection.extend(p);
            repaint();
        } else if (lastButtonDown == MouseEvent.BUTTON2) {
            selection.extendWithFixedCenter(p);
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
        extendSelection(e.getPoint());
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            selection.deselect();
        } else {
            selection.start(e.getPoint());
        }
        lastButtonDown = e.getButton();
        repaint();
    }

    public void mouseReleased(MouseEvent e) {
        extendSelection(e.getPoint());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(fractalImage, 0, 0, getWidth(), getHeight(), null);
        selection.draw(g2);
    }

    private class Selection {
        public SelectionState state;
        private Complex center;
        private double width;
        private double height;

        private Point startPoint;
        private Point endPoint;

        public Selection() {
            state = SelectionState.NONE;
        }

        public Complex getCenter() {
            return center;
        }

        public void start(Point p) {
            startPoint = p;
            center = pointToComplexCoordinates(p);
            width = 0;
            height = 0;
            state = SelectionState.POINT;
        }

        private boolean distantEnough(Point p1, Point p2) {
            return Math.abs(p1.x - p2.x) > 1 &&
                Math.abs(p1.y - p2.y) > 1;
        }

        public void extend(Point p) {
            Complex endCoord = pointToComplexCoordinates(p);
            double newCenterReal;
            double newCenterImag;

            endPoint = p;

            if (!distantEnough(startPoint, endPoint))
                state = SelectionState.POINT;
            else if (state != SelectionState.NONE) {
                if (endCoord.real < center.real) {
                    width = Math.abs(center.real + width / 2 - endCoord.real);
                    newCenterReal = endCoord.real + width / 2;
                } else {
                    width = Math.abs(center.real - width / 2 - endCoord.real);
                    newCenterReal = endCoord.real - width / 2;
                }
                if (endCoord.imag < center.imag) {
                    height = Math.abs(center.imag + height / 2 - endCoord.imag);
                    newCenterImag = endCoord.imag + height / 2;
                } else {
                    height = Math.abs(center.imag - height / 2 - endCoord.imag);
                    newCenterImag = endCoord.imag - height / 2;
                }
                center = new Complex(newCenterReal, newCenterImag);
                state = SelectionState.RECTANGLE;
            }

        }

        public void extendWithFixedCenter(Point p) {
            Complex endCoord = pointToComplexCoordinates(p);

            endPoint = p;

            if (!distantEnough(startPoint, endPoint))
                state = SelectionState.POINT;
            else if (state != SelectionState.NONE) {
                width = Math.abs(endCoord.real - center.real) * 2;
                height = Math.abs(endCoord.imag - center.imag) * 2;
                state = SelectionState.RECTANGLE;
            }
        }

        public void deselect() {
            state = SelectionState.NONE;
        }

        public double getrMin() {
            return center.real - width / 2;
        }

        public double getrMax() {
            return center.real + width / 2;
        }

        public double getiMin() {
            return center.imag - height / 2;
        }

        public double getiMax() {
            return center.imag + height / 2;
        }

        private void draw(Graphics2D g2) {
            switch (state) {
            case POINT:
                drawCrosshair(g2, complexCoordinatesToPoint(center),
                              Color.red);
                break;
            case RECTANGLE:
                drawRectangle(g2,
                              complexCoordinatesToPoint(new Complex(getrMin(),
                                                                    getiMax())),
                              complexCoordinatesToPoint(new Complex(getrMax(),
                                                                    getiMin())),
                              Color.yellow);
                break;
            }
        }

        private void drawCrosshair(Graphics2D g2, Point p, Color c) {
            g2.setPaint(c);
            g2.draw(new Line2D.Double(p.x - 5, p.y, p.x + 5, p.y));
            g2.draw(new Line2D.Double(p.x, p.y - 5, p.x, p.y + 5));
        }

        private void drawRectangle(Graphics2D g2, Point p1, Point p2, Color c) {
            g2.setPaint(c);
            g2.draw(new Rectangle2D.Double(p1.x, p1.y,
                                           p2.x - p1.x, p2.y - p1.y));
        }
    }
}
