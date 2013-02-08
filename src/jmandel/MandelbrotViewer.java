package jmandel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MandelbrotViewer implements MandelbrotPanelListener {

    private MandelbrotPanel mandelPanel;
    private JLabel statusLabel;

    private static final double RMINSTART = -2.5;
    private static final double RMAXSTART = 1;
    private static final double IMINSTART = -1;
    private static final double IMAXSTART = 1;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private static final double ZINFACTOR = 2;
    private static final double ZOUTFACTOR = .5;

    public static void main(String[] args) {
        new MandelbrotViewer().createAndShowGUI();
    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("JMandel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mandelPanel = new MandelbrotPanel();
        mandelPanel.addMandelbrotPanelListener(this);

        JPanel buttonPanel = createButtonPanel();
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setPreferredSize(new Dimension(WIDTH, 16));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel("ABC");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusPanel.setMaximumSize(new Dimension(Short.MAX_VALUE,
                                                 Short.MAX_VALUE));
        southPanel.add(buttonPanel);
        southPanel.add(statusPanel);

        frame.getContentPane().add(mandelPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, southPanel);

        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);

        mandelPanel.init(RMINSTART, RMAXSTART, IMINSTART, IMAXSTART);
        mandelPanel.generateAndDisplayFractal();
    }

    private JPanel createButtonPanel() {

        JButton zoomInButton = new JButton("Zoom in");
        JButton zoomOutButton = new JButton("Zoom out");
        JButton zoomRegionButton = new JButton("Zoom region");
        JButton generateButton = new JButton("Generate");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        buttonPanel.add(zoomRegionButton);
        buttonPanel.add(generateButton);

        zoomInButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mandelPanel.zoom(ZINFACTOR);
                }
            });
        zoomOutButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mandelPanel.zoom(ZOUTFACTOR);
                }
            });
        zoomRegionButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mandelPanel.zoomRegion();
                }
            });
        generateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mandelPanel.updateResolution();
                    mandelPanel.generateAndDisplayFractal();
                }
            });
        return buttonPanel;
    }

    public void mousePositionUpdate(Complex c) {
        statusLabel.setText(c.toString());
    }
}
