package jmandel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class MandelbrotViewer implements ComplexGridPositionListener {

    private MandelbrotPanel mandelPanel;
    private JLabel statusLabel;

    private static final double RMINSTART = -2.5;
    private static final double RMAXSTART = 1;
    private static final double IMINSTART = -1;
    private static final double IMAXSTART = 1;

    public static void main(String[] args) {
        new MandelbrotViewer().createAndShowGUI();
    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("JMandel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mandelPanel = new MandelbrotPanel();
        mandelPanel.addComplexGridPositionListener(this);

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        frame.getContentPane().add(mandelPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, statusPanel);

        statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        frame.setSize(600, 400);
        frame.setVisible(true);

        mandelPanel.init(RMINSTART, RMAXSTART, IMINSTART, IMAXSTART);
        mandelPanel.generateAndDisplayFractal();
    }

    public void ComplexGridPositionUpdate(Complex c) {
        statusLabel.setText(c.toString());
    }
}
