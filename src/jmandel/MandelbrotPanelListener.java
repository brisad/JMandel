package jmandel;

public interface MandelbrotPanelListener {
    void mousePositionUpdate(Complex c);
    void selectionUpdate(MandelbrotPanel.SelectionState state);
}
