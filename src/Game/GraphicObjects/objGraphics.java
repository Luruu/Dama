package Game.GraphicObjects;

import javax.swing.*;

import Game.EnumIndices;
import Game.ImageFunctions;

import java.awt.*;
import java.awt.event.ActionListener;

public interface objGraphics extends ImageFunctions {
    //Add and set a new Frame
    default JFrame addFrame(String titleFrame, int width, int height, Color backGroundColor, boolean resizable, LayoutManager layout, String icon, boolean centerX, boolean centerY, int actionOnExit) {
        JFrame newFrame = new JFrame(titleFrame);
       
        newFrame.setSize(width, height);
        newFrame.setBackground(backGroundColor);
        newFrame.setResizable(resizable);
        newFrame.setLayout(layout);

        Image iconImg = readFile(icon);
        newFrame.setIconImage(iconImg);

        newFrame.setDefaultCloseOperation(actionOnExit);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Point centerScreen = null;
        int margin = 15;
        if (centerX && centerY)
            centerScreen = new Point(dim.width/2 - newFrame.getSize().width/2, dim.height/2 - newFrame.getSize().height/2);
        else if (!centerX && centerY)
            centerScreen = new Point(margin, dim.height/2 - newFrame.getSize().height/2);
        else if (centerX && !centerY)
            centerScreen = new Point(dim.width/2 - newFrame.getSize().width/2, margin);
        else if (!centerX && !centerY)
            centerScreen = new Point(margin, margin);

        newFrame.setLocation(centerScreen);
        return newFrame;
    }

    //Add and set a new Panel
    default JPanel addPanel(int width, int height, Color backGroundColor, LayoutManager layout) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(layout);
        newPanel.setPreferredSize(new Dimension(width, height));
        newPanel.setBackground(backGroundColor);
        return newPanel;
    }

    default JPanel addPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(null);
        return newPanel;
    }

    default JButton addButton(String text, ActionListener listener, String action){
        JButton newButton = new JButton(text);
        newButton.addActionListener(listener);
        newButton.setActionCommand(action);
        return newButton;
    }

    default JComboBox<?> addComboBoxString(String [] str, EnumIndices SelectedIndex, boolean editablebyUser) {
        JComboBox<?> newCombobox = new JComboBox<String>(str);
        newCombobox.setSelectedIndex(SelectedIndex.getValue());
        newCombobox.setEditable(editablebyUser);
        return newCombobox;
    }

    default JComboBox<?> addComboBoxString(String [] str, EnumIndices SelectedIndex, boolean editablebyUser, ActionListener listener, String action) {
        JComboBox<?> newCombobox = new JComboBox<String>(str);
        newCombobox.setSelectedIndex(SelectedIndex.getValue());
        newCombobox.setEditable(editablebyUser);
        newCombobox.addActionListener(listener);
        newCombobox.setActionCommand(action);
        return newCombobox;
    }

    default JTextField addTextField(String str, Dimension dim, boolean opaque){
        JTextField newTextField = new JTextField(str);
        Font smallFont = new Font("Monospaced", Font.PLAIN, 14);  
        newTextField.setPreferredSize(new Dimension(dim.width, dim.height)); 
        newTextField.setOpaque(opaque);
        newTextField.setFont(smallFont);
        return newTextField;
    }
    
    default JLabel addLabel(String str){
        JLabel newLabel = new JLabel(str);
        return newLabel;
    }

    default JLabel addLabel(String str, Font font){
        JLabel newLabel = new JLabel(str);
        if (font != null)
            newLabel.setFont(font);
        return newLabel;
    }
}
