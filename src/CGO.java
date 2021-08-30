import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Create Graphic Objects 
public abstract class CGO {
    //Add and set a new Frame
    public static JFrame addFrame(String titleFrame, int width, int height, Color backGroundColor, boolean resizable, LayoutManager layout, String icon, boolean centerX, boolean centerY) {
        JFrame newFrame = new JFrame(titleFrame);
       
        newFrame.setSize(width, height);
        newFrame.setBackground(backGroundColor);
        newFrame.setResizable(resizable);
        newFrame.setLayout(layout);

        Image iconImg = ImageFunctions.readFile(icon);
        newFrame.setIconImage(iconImg);

        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    public static JPanel addPanel(int width, int height, Color backGroundColor, LayoutManager layout) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(layout);
        newPanel.setPreferredSize(new Dimension(width, height));
        newPanel.setBackground(backGroundColor);
        return newPanel;
    }


    public static JButton addButton(String text, ActionListener listener, String action){
        JButton newButton = new JButton(text);
        newButton.addActionListener(listener);
        newButton.setActionCommand(action);
        return newButton;
    }
    public static JComboBox<?> addComboBoxString(String [] str, int SelectedIndex, boolean editablebyUser) {
        JComboBox<?> newCombobox = new JComboBox<String>(str);
        newCombobox.setSelectedIndex(SelectedIndex);
        newCombobox.setEditable(editablebyUser);
        return newCombobox;
    }

    public static JComboBox<?> addComboBoxString(String [] str, int SelectedIndex, boolean editablebyUser, ActionListener listener, String action) {
        JComboBox<?> newCombobox = new JComboBox<String>(str);
        newCombobox.setSelectedIndex(SelectedIndex);
        newCombobox.setEditable(editablebyUser);
        newCombobox.addActionListener(listener);
        newCombobox.setActionCommand(action);
        return newCombobox;
    }

    public static JTextField addTextField(String str, Dimension dim, boolean opaque){
        JTextField newTextField = new JTextField(str);
        Font smallFont = new Font("Monospaced", Font.PLAIN, 14);  
        newTextField.setPreferredSize(new Dimension(dim.width, dim.height)); 
        newTextField.setOpaque(opaque);
        newTextField.setFont(smallFont);
        return newTextField;
    }
    
    public static JLabel addLabel(String str){
        JLabel newLabel = new JLabel(str);
        return newLabel;
    }

    public static JLabel addLabel(String str, Font font){
        JLabel newLabel = new JLabel(str);
        if (font != null)
            newLabel.setFont(font);
        return newLabel;
    }

    //METODO FOLLE ---------------------------- LASCIATO SOLO PER RIVEDERLO!!
   /* public void addButton(){
        JButton obj = new JButton("Play");
        //obj.setActionCommand("disable");
        frameStart.add(obj);

        obj.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                System.out.println("CLICK");
                try {
                    frameStart.setVisible(false);
                    startGame("Renato", "Luca", dimTable, Box.DIM_BOX);
                } 
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });  
    } */

}
