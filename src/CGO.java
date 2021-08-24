import javax.swing.*;
import java.awt.*;

// Create Graphic Objects interface
public interface CGO {
    //Create and set a new Frame
    public static JFrame createFrame(String titleFrame, int width, int height, Color backGroundColor, boolean resizable, LayoutManager layout, String icon) {
        JFrame newFrame = new JFrame(titleFrame);
       
        newFrame.setSize(width, height);
        newFrame.setBackground(backGroundColor);
        newFrame.setResizable(resizable);
        newFrame.setLayout(layout);

        Image iconImg = ImageFunctions.readFile(icon);
        newFrame.setIconImage(iconImg);

        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Point centerScreen = new Point(dim.width/2 - newFrame.getSize().width/2 , dim.height/2 - newFrame.getSize().height/2);
        newFrame.setLocation(centerScreen);
        return newFrame;
    }

    //Create and set a new Panel
    public static JPanel createPanel(int width, int height, Color backGroundColor, LayoutManager layout) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(layout);
        newPanel.setPreferredSize(new Dimension(width, height));
        newPanel.setBackground(backGroundColor);
        return newPanel;
    }

    public static JComboBox<?> addComboBoxString(String [] str, int SelectedIndex, boolean editablebyUser) {
        JComboBox<?> newCombobox = new JComboBox<String>(str);
        newCombobox.setSelectedIndex(SelectedIndex);
        newCombobox.setEditable(editablebyUser);
        return newCombobox;
    }

    public static JTextField addTextField(String str, Dimension dim, boolean opaque){
        JTextField textField = new JTextField(str);
        Font smallFont = new Font("Monospaced", Font.PLAIN, 14);  
        textField.setPreferredSize(new Dimension(dim.width, dim.height)); 
        textField.setOpaque(opaque);
        textField.setFont(smallFont);
        return textField;
    }

    public static JLabel addLabel(String str){
        JLabel label = new JLabel(str);
        return label;
    }

}
