package jmandel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class MandelbrotPanel extends JPanel {

    private Image fractal;

    public MandelbrotPanel(Image fractal) {
        this.fractal = fractal;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.drawImage(fractal, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
