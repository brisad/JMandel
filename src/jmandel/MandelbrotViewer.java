package jmandel;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;

public class MandelbrotViewer implements MandelbrotPanelListener {

    private JFrame frame;
    private MandelbrotPanel mandelPanel;
    private JLabel statusLabel;

    JButton zoomInButton;
    JButton zoomOutButton;
    JButton zoomRegionButton;

    private static final double RMINSTART = -2.5;
    private static final double RMAXSTART = 1;
    private static final double IMINSTART = -1;
    private static final double IMAXSTART = 1;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private static final double ZINFACTOR = 2;
    private static final double ZOUTFACTOR = .5;

    private static final int START_NUM_ITERATIONS = 100;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new MandelbrotViewer().createAndShowGUI();
                }
            });
    }

    private void createAndShowGUI() {

        frame = new JFrame("JMandel");
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
        mandelPanel.setIterations(START_NUM_ITERATIONS);
        mandelPanel.generateAndDisplayFractal();
    }

    private JPanel createButtonPanel() {

        zoomInButton = new JButton("Zoom in");
        zoomOutButton = new JButton("Zoom out");
        zoomRegionButton = new JButton("Zoom region");
        JButton generateButton = new JButton("Generate");
        JButton saveImageButton = new JButton("Save image");

        zoomInButton.setEnabled(false);
        zoomOutButton.setEnabled(false);
        zoomRegionButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);
        buttonPanel.add(zoomRegionButton);
        final JSpinner iterationSpinner = addIterationSpinner(buttonPanel,
                                                              "Iterations");
        buttonPanel.add(generateButton);
        buttonPanel.add(saveImageButton);

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
        iterationSpinner.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    SpinnerNumberModel m =
                        (SpinnerNumberModel)iterationSpinner.getModel();
                    int iterations = m.getNumber().intValue();
                    mandelPanel.setIterations(iterations);
                    mandelPanel.generateAndDisplayFractal();
                }
            });
        generateButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mandelPanel.updateResolution();
                    mandelPanel.generateAndDisplayFractal();
                }
            });
        saveImageButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    BufferedImage image =
                        (BufferedImage)mandelPanel.getFractalImage();
                    JFileChooser chooser = new JFileChooser();
                    FileFilter filter =
                        new FileNameExtensionFilter("PNG file", "png");
                    chooser.setFileFilter(filter);
                    chooser.setAcceptAllFileFilterUsed(false);

                    int retval = chooser.showSaveDialog(frame);
                    if (retval == JFileChooser.APPROVE_OPTION) {
                        File saveFile = chooser.getSelectedFile();
                        boolean created;
                        try {
                            created = saveFile.createNewFile();
                        } catch (IOException ex) {
                            error("Could not create file: " + saveFile,
                                  "Write Error");
                            return;
                        }

                        if (!created) {
                            String message = "File already exists: " +
                                saveFile + ". Overwrite?";
                            if (!confirm(message, "Overwrite file"))
                                return;
                        }

                        if (!saveFile.canWrite()) {
                            error("Can't write to file: " + saveFile,
                                  "Write Error");
                            return;
                        }

                        try {
                            ImageIO.write(image, "png", saveFile);
                        } catch (IOException exception) {
                            error("Could not write to file: "
                                  + saveFile,
                                  "Write Error");
                        }
                    }
                }

                private void error(String message, String title) {
                    JOptionPane.showMessageDialog(frame, message, title,
                                                  JOptionPane.ERROR_MESSAGE);
                }

                private boolean confirm(String message, String title) {
                    int ret = JOptionPane.showConfirmDialog(frame,
                                  message, title, JOptionPane.YES_NO_OPTION);
                    return ret == JOptionPane.YES_OPTION;
                }
            });

        return buttonPanel;
    }

    private JSpinner addIterationSpinner(Container c, String label) {
        JPanel panel = new JPanel();
        JLabel spinLabel = new JLabel(label);
        panel.add(spinLabel);

        JSpinner spinner = new JSpinner(
            new SpinnerNumberModel(START_NUM_ITERATIONS, 1, null, 10));
        // Don't show a thousands separator
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
        editor.getTextField().setColumns(4);

        spinLabel.setLabelFor(spinner);
        panel.add(spinner);
        // Restrict width of panel
        panel.setMaximumSize(panel.getPreferredSize());
        c.add(panel);

        return spinner;
    }

    public void mousePositionUpdate(Complex c) {
        statusLabel.setText(c.toString());
    }

    public void selectionUpdate(MandelbrotPanel.SelectionState state) {
        switch (state) {
        case NONE:
            zoomInButton.setEnabled(false);
            zoomOutButton.setEnabled(false);
            zoomRegionButton.setEnabled(false);
            break;
        case POINT:
            zoomInButton.setEnabled(true);
            zoomOutButton.setEnabled(true);
            zoomRegionButton.setEnabled(false);
            break;
        case RECTANGLE:
            zoomInButton.setEnabled(true);
            zoomOutButton.setEnabled(true);
            zoomRegionButton.setEnabled(true);
            break;
        }
    }
}
