package app.taxi.util;

import javax.swing.*;

public interface ControlPanel {
    JComponent getControlPanel();

    void configureControlPanel();

    int getPreferredHeight();
}

