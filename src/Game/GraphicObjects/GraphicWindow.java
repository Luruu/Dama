package Game.GraphicObjects;

import javax.swing.*;


public abstract class GraphicWindow implements objGraphics {
    protected JFrame frame;
    protected JPanel panel;
    protected String ICON_PATH = "/images/CheckersGameIcon.png"; //a frame can change his ICON_PATH

    public String geticonPath(){
        return ICON_PATH;
    }

    public JFrame getFrame(){
        return frame;
    }

    public JPanel getPanel(){
        return panel;
    }
}
