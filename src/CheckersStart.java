import javax.swing.*;

import java.awt.*;
import java.awt.event.*;  
import java.awt.event.ActionListener;


public class CheckersStart implements ActionListener{
    private static JFrame frameStart;
    private static final String ICON_PATH = "/images/WizardGreen.png";

    private static String firstPlayerName, secondPlayerName;

    private static int dimTable;
  
    public CheckersStart(){
        frameStart = CGO.createFrame("Checkers Game - Luca Rubino 1934 / Renato Esposito 1881", 640, 480, Color.GREEN, false, new FlowLayout(), ICON_PATH);
    
        JLabel l1 = CGO.addLabel("Choose table size");
        frameStart.add(l1);
        addComboBox();

        JLabel l2 = CGO.addLabel("Choose name Player 1");
        frameStart.add(l2);
        JTextField t1 = CGO.addTextField("Prova", new Dimension(100, 20), true);
        frameStart.add(t1);
        JLabel l3 = CGO.addLabel("Choose name Player 2");
        frameStart.add(l3);
        JTextField t2 =CGO.addTextField("Prova2", new Dimension(100, 20), true);
        frameStart.add(t2);
        JButton b1 = addButton();
        frameStart.add(b1);
        frameStart.setVisible(true);
        
    }

    public void addComboBox(){
        String[] someStrings = { "4x4", "6x6", "8x8", "9x9", "10x10", "12x12", "14x14", "16x16"};
        JComboBox<?> comboBox = CGO.addComboBoxString(someStrings, 2, false);
        comboBox.addActionListener(this);
        comboBox.setActionCommand("2");;
        frameStart.add(comboBox);
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
                    startGame("Renato", "Luca", dimTable, Rectangle.DIM_RECT);
                } 
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });  
    } */

    public JButton addButton(){
        JButton button = new JButton("test");
        button.addActionListener(this);
        button.setActionCommand("1");
        return button;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "1":
                System.out.println("Button pressed!");
                try {
                    startGame("Renato", "Luca", dimTable, Rectangle.DIM_RECT);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "2":
                System.out.println("Checkbox pressed!");
                break;
            default:
                System.out.println("Checkbox pressed!");
        }
    }
    
    
    private void startGame(String p1Name, String p2Name, int DIM_TABLE, int DIM_RECT) throws Exception{
        final int DIM = 8;
        Rectangle.DIM_RECT = 96;

        //N.B: Game Window sizes are (DIM * Rectangle.DIM_RECT, Rectangle.DIM * DIM_RECT)
        CheckersTable table = CheckersTable.getInstance(DIM, DIM);
        Creator factoryM = new ConcreteFactoryM();
        Player pl1 = (Player) factoryM.factoryMethod(p1Name, Color.red, null);
        Player pl2 = (Player) factoryM.factoryMethod(p2Name, Color.green, null);
        table.startGame(pl1, pl2); 
    }

    public static String geticonPath(){
        return ICON_PATH;
    }
        
}
