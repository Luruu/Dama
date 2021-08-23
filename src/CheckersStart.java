import javax.swing.*;
import java.awt.*;

public class CheckersStart {
    private JFrame frame;

    public CheckersStart(){
        createFrame();
        
        AddLabel();
        AddComboBox();
        AddButton();
       
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = CGO.createFrame("Checkers Game - Luca Rubino 1934 / Renato Esposito 1881", 640, 480, Color.GREEN, false, new FlowLayout());
        
    }

    public void AddComboBox(){
        String[] someStrings = { "4x4", "6x6", "8x8", "9x9", "10x10", "12x12", "14x14", "16x16"};
        JComboBox<?> comboBox = CGO.createComboBoxString(someStrings, 2, false);
        //comboBox.addActionListener(this);
        frame.add(comboBox);
    }

    public void AddButton(){
        JButton obj = new JButton("Gioca");
        frame.add(obj);
    }

    public void AddLabel(){
        JLabel obj = new JLabel("Scegli dimensione damiera: ");
        frame.add(obj);
    }
}
