package jmandel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MandelbrotPanel extends JPanel implements MouseMotionListener {

    private Image fractalImage;
    private MandelbrotZoomer zoomer;
    private ArrayList<ComplexGridPositionListener> listeners;

    public MandelbrotPanel() {
        listeners = new ArrayList<ComplexGridPositionListener>();
        addMouseMotionListener(this);
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

    private Complex pointToComplexCoordinate(Point p) {
        ComplexGrid grid = zoomer.getComplexGrid();
        return grid.index(p.x * grid.getSize().width / getWidth(),
                          p.y * grid.getSize().height / getHeight());
    }

    public void mouseMoved(MouseEvent e) {
        for (ComplexGridPositionListener l : listeners) {
            l.ComplexGridPositionUpdate(pointToComplexCoordinate(e.getPoint()));
        }
    }

    public void mouseDragged(MouseEvent e) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(fractalImage, 0, 0, getWidth(), getHeight(), null);
    }
}
