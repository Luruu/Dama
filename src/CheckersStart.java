import javax.swing.*;

import java.awt.*;
import java.awt.event.*;  

import java.awt.event.ActionListener;
import java.util.ArrayList;


public class CheckersStart implements ActionListener{

    //Singleton Eager initialization
    private static final CheckersStart Instance = new CheckersStart();

    private JFrame frameStart;
    private final String ICON_PATH = "/images/WizardRed.png";

    private String firstPlayerName, secondPlayerName;

    private int dimTable;

    private JTextField      t1,t2;
    private JLabel          l1,l2,l3;
    private JButton         b1;
    private JComboBox<?>    c1;

    private String stringAction = "0";
    private ArrayList<String> listActionCommands = new ArrayList<String>();

  
    private CheckersStart(){
        frameStart = CGO.addFrame("Checkers Game - Luca Rubino 1934 / Renato Esposito 1881", 320, 200, Color.GREEN, false, new FlowLayout(), ICON_PATH);
    
        l1 = CGO.addLabel("Choose table size");
        frameStart.add(l1);
        
        String[] someStrings = { "4", "6", "8", "10", "12", "14", "16"};

        c1 = CGO.addComboBoxString(someStrings, 2, false, this, stringAction);
        addcommandtoList("combobox1");
        frameStart.add(c1);

        l2 = CGO.addLabel("Choose name Player 1");
        frameStart.add(l2);
        t1 = CGO.addTextField("Player1", new Dimension(100, 20), true);
        frameStart.add(t1);
        l3 = CGO.addLabel("Choose name Player 2");
        frameStart.add(l3);
        t2 = CGO.addTextField("Player2", new Dimension(100, 20), true);
        frameStart.add(t2);

        
        b1 = CGO.addButton("Play", this, stringAction);
        addcommandtoList("button1");
        frameStart.add(b1);

        frameStart.setVisible(true);
    }

    //Singleton Eager initialization
    public static CheckersStart getIstance(){
        return Instance;
    }

    public void addcommandtoList(String nameObject){
        listActionCommands.add(nameObject);
        int intAction = Integer.parseInt((String)stringAction);
        stringAction = String.valueOf(++intAction); //Increase action because a nameObject is added
        System.out.println(nameObject + ": action " + stringAction);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "0":
                System.out.println("Checkbox pressed!");
                break;
            case "1":
                System.out.println("Button pressed!");
                firstPlayerName = t1.getText();
                secondPlayerName = t2.getText();
                dimTable = Integer.parseInt((String)c1.getSelectedItem());
                if (firstPlayerName.isBlank() || secondPlayerName.isBlank()) return;
                try {
                    frameStart.setVisible(false); //hide CheckersStart Window
                    startGame(firstPlayerName, secondPlayerName, dimTable, Box.DIM_BOX);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;

            default:
                System.out.println("case default: " + action + listActionCommands.get(Integer.parseInt((String)action)) + ": pressed!");
        }
    }
    
    
    private void startGame(String p1Name, String p2Name, int DIM_TABLE, int DIM_BOX) throws Exception{
        Box.DIM_BOX = DIM_BOX;
        //N.B: Game Window sizes are (DIM * Box.DIM_BOX, Box.DIM * DIM_BOX)
        CheckersTable table = CheckersTable.getInstance(DIM_TABLE, DIM_TABLE);
        Creator factoryM = new ConcreteFactoryM();
        Player pl1 = (Player) factoryM.factoryMethod(p1Name, Color.red, null);
        Player pl2 = (Player) factoryM.factoryMethod(p2Name, Color.green, null);
        table.startGame(pl1, pl2); 
    }

    public String geticonPath(){
        return ICON_PATH;
    }
        
}
