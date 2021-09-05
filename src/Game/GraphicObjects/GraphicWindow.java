package Game.GraphicObjects;

import javax.swing.*;

// Create Graphic Objects 
public abstract class GraphicWindow implements objGraphics {
    protected JFrame frame;
    protected JPanel panel;
    protected String ICON_PATH = "/images/WizardRed.png"; //a frame can change his ICON_PATH
}
