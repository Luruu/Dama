package Game.GraphicObjects;

import javax.swing.*;

import Game.GraphicObjects.Start.EnumIndices;
import Game.Image.ImageFunctions;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * <h1>GrapicComponents class</h1>
 * This interface abstracts the concept of graphic component, therefore encapsulates the methods for managing the swing components.
 */
public interface GraphicComponents extends ImageFunctions {
    //Add and set a new Frame
    default JFrame addFrame(String titleFrame, int width, int height, Color backGroundColor, boolean resizable, LayoutManager layout, String icon, boolean centerX, boolean centerY, int actionOnExit) {
        JFrame newFrame = new JFrame(titleFrame);
       
        newFrame.setSize(width, height);
        newFrame.setBackground(backGroundColor);
        newFrame.setResizable(resizable);
        newFrame.setLayout(layout);

        Image iconImg = ImageFunctions.readFile(icon);
        newFrame.setIconImage(iconImg);

        newFrame.setDefaultCloseOperation(actionOnExit);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Point centerScreen = null;
        int margin = 15;
        int x = centerX ? dim.width/2 - newFrame.getSize().width/2 : margin;
        int y = centerY ? dim.height/2 - newFrame.getSize().height/2 : margin;
        centerScreen = new Point(x,y);
        newFrame.setLocation(centerScreen);
        return newFrame;
    }

    /**
     * Create and set a new Panel.
     * @param width widht of the panel.
     * @param height heiht of the panel
     * @param backGroundColor background color of the panel.
     * @param layout layout manager of the panel.
     * @return a penl.
     */
    default JPanel addPanel(int width, int height, Color backGroundColor, LayoutManager layout) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(layout);
        newPanel.setPreferredSize(new Dimension(width, height));
        newPanel.setBackground(backGroundColor);
        return newPanel;
    }

    /**
     * Return a panel with null layout manager.
     * @return a panel with null layout manager.
     */
    default JPanel addPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(null);
        return newPanel;
    }

    /**
     * Create and set a button.
     * @param text string inside the button. 
     * @param listener actionlistener of the button.
     * @param action associated action.
     * @return a button.
     */
    default JButton addButton(String text, ActionListener listener, String action){
        JButton newButton = new JButton(text);
        newButton.addActionListener(listener);
        newButton.setActionCommand(action);
        return newButton;
    }

    /**
     * Create and set a JComboBox.
     * @param str string of the first field.
     * @param SelectedIndex preselected item.
     * @param editablebyUser true if the user can edit, false otherwise.
     * @return a JComboBox.
     */
    default JComboBox<?> addComboBoxString(String [] str, EnumIndices SelectedIndex, boolean editablebyUser) {
        JComboBox<?> newCombobox = new JComboBox<String>(str);
        newCombobox.setSelectedIndex(SelectedIndex.getValue());
        newCombobox.setEditable(editablebyUser);
        return newCombobox;
    }


    /**
     * Create and set textField.
     * @param str text inside the field.
     * @param dim field size.
     * @param opaque true if you want opaque, false otherwise.
     * @return textField.
     */
    default JTextField addTextField(String str, Dimension dim, boolean opaque){
        JTextField newTextField = new JTextField(str);
        Font smallFont = new Font("Monospaced", Font.PLAIN, 14);  
        newTextField.setPreferredSize(new Dimension(dim.width, dim.height)); 
        newTextField.setOpaque(opaque);
        newTextField.setFont(smallFont);
        return newTextField;
    }
    
    /**
     * Create and set a label
     * @param str string inside the label.
     * @return the label.
     */
    default JLabel addLabel(String str){
        JLabel newLabel = new JLabel(str);
        return newLabel;
    }

    /**
     * Create and set a label with a font indicated.
     * @param str string inside the label. 
     * @param font font.
     * @return the label.
     */
    default JLabel addLabel(String str, Font font){
        JLabel newLabel = new JLabel(str);
        if (font != null)
            newLabel.setFont(font);
        return newLabel;
    }
}
