package Game.GraphicObjects;

import javax.swing.*;

/**
 * <h1>GrapicWindow class</h1>
 * This class abstracts the concept of a graphics window.
 */
public abstract class GraphicWindow implements GraphicComponents {
    protected JFrame frame;
    protected JPanel panel;
    protected String ICON_PATH = "/images/CheckersGameIcon.png"; //a frame can change his ICON_PATH

    /**
     * Return the path of the game's icon.
     * @return the path of the game's icon.
     */
    public String geticonPath(){
        return ICON_PATH;
    }

    /**
     * Return the frame
     * @return frame.
     */
    public JFrame getFrame(){
        return frame;
    }

    /**
     * Return the panel.
     * @return the panel.
     */
    public JPanel getPanel(){
        return panel;
    }
}
