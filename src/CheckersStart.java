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

    private String  firstPlayerName, secondPlayerName;
    private int     dimTable;
    private Boolean modeRevised;

    private ArrayList<JComponent> jComponentList = new ArrayList<>();

    private String stringAction = "0";
    private ArrayList<String> listActionCommands = new ArrayList<>();

    public boolean centerTableY = true;

   



    private CheckersStart(){
        frameStart = CGO.addFrame("Checkers Game - Luca Rubino 1934 / Renato Esposito 1881", 190, 300, Color.GREEN, false, new FlowLayout(), ICON_PATH, true, true);
    
        jComponentList.add(CGO.addLabel("CheckersGame",new Font("Verdana", Font.PLAIN, 18))); 

        jComponentList.add(CGO.addLabel("table size"));
        
        String[] dimensionStrings = { "4", "6", "8", "10", "12", "14", "16"};
        jComponentList.add(CGO.addComboBoxString(dimensionStrings, 2, false, this, stringAction));
        addcommandtoList("combobox1");

        jComponentList.add(CGO.addLabel("game mode"));

        String[] modeStrings = { "classic", "revised"};
        jComponentList.add(CGO.addComboBoxString(modeStrings, 1, false));
        
        jComponentList.add(CGO.addLabel("Choose name Player 1"));
        
        jComponentList.add(CGO.addTextField("Player1", new Dimension(100, 20), true));
        
        jComponentList.add(CGO.addLabel("Choose name Player 2"));
        
        jComponentList.add(CGO.addTextField("Player2", new Dimension(100, 20), true));
        
        jComponentList.add(CGO.addButton("Start Game", this, stringAction));
        addcommandtoList("bStart Game");
        
        jComponentList.add(CGO.addButton("Game Rules", this, stringAction));
        addcommandtoList("game rules");

        for (JComponent jb : jComponentList)
            frameStart.add(jb);

        printComponentsList();

        frameStart.setVisible(true);
    }

    public void printComponentsList(){
        int i=0;
        System.out.println("CheckersStart Components List:");
        for (JComponent jb : jComponentList)
            System.out.println(i++ + ": " + jb.getClass().toString());
        System.out.println("----------------------------------");
    }

    //Singleton Eager initialization
    public static CheckersStart getIstance(){
        return Instance;
    }

    public void addcommandtoList(String nameObject){
        listActionCommands.add(nameObject);//EVENTUALMENTE DA ELIMINAREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
        int intAction = Integer.parseInt((String)stringAction);
        stringAction = String.valueOf(++intAction); //Increase action because a nameObject is added
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
                firstPlayerName = ((JTextField) jComponentList.get(6)).getText();
                secondPlayerName = ((JTextField) jComponentList.get(8)).getText();
                
                dimTable = Integer.parseInt((String)(((JComboBox<?>)jComponentList.get(2)).getSelectedItem()));
                modeRevised = ((JComboBox<?>)jComponentList.get(4)).getSelectedItem().equals("revised"); //true revised, false classic
                if (firstPlayerName.isBlank() || secondPlayerName.isBlank()) return;
                try {
                    frameStart.setVisible(false); //hide CheckersStart Window
                    startGame(firstPlayerName, secondPlayerName, dimTable, Box.DIM_BOX, modeRevised);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "2":
                System.out.println("label info pressed!");
                JOptionPane.showMessageDialog(frameStart, "REVISED MODE: Ogni giocatore dispone di N pedine e 2 Arcieri (N in base alla dimensione del campo da gioco) di colore diverso rispetto a quelle dell'avversario.\n Il giocatore verde fa sempre la prima mossa.\n" +
                "L'obiettivo del gioco è quello di mangiare tutti i pezzi dell'avversario o di fare il miglior punteggio entro il tempo limite\n" +
                "Sul campo da gioco sono presenti i seguenti pezzi:\n" + 
                "Pedina: pezzo classico che si muove solamente in diagonale di una casella alla volta e soltanto in avanti. Quando una pedina raggiunge una delle caselle dell'ultima riga viene promossa diventando dama.\n" +
                "Dama: upgrade della pedina. Può muoversi in più direzioni ad un passo alla volta.\n"+
                "Mago: Se il mago mangia un pezzo, resuscita una pedina. Invece se viene mangiato la pedina avversaria diventa dama. Un mago non può diventare dama e non può mangiare un altro mago.\n" +
                "Il punteggio è dato dalla tipologia e dalla quantità di pezzi mangiati, in particolare:\n Pedina: 1 punto.\n Mago: 3 punti.\n Dama: 5 punti");
            default:
                System.out.println("case default: " + action + listActionCommands.get(Integer.parseInt((String)action)) + ": pressed!");
        }
    }
    private void scaleDimensionTable(){
        Dimension dimensionTableFrame = new Dimension(dimTable * Box.DIM_BOX, dimTable * Box.DIM_BOX);
        Dimension dimensionScreenPC = Toolkit.getDefaultToolkit().getScreenSize();
        Boolean dimensions_Too_large = dimensionTableFrame.height > dimensionScreenPC.height || dimensionTableFrame.width > dimensionScreenPC.width;
        if (dimensions_Too_large){
            int diff = dimensionTableFrame.height - dimensionScreenPC.height;
            centerTableY = false; //Table will start at the position (x,15)
            if (diff > 400)
                Box.DIM_BOX -= (Box.DIM_BOX/2 - 14);
            else if (diff > 300)
                Box.DIM_BOX -= (Box.DIM_BOX/2 - 18);
            else if (diff > 200)
                Box.DIM_BOX -= (Box.DIM_BOX/2 - 22);
            else if (diff > 50)
                Box.DIM_BOX -= (Box.DIM_BOX/2 - 33);
        }
    }
    
    
    private void startGame(String p1Name, String p2Name, int DIM_TABLE, int DIM_BOX, boolean revisedChecker) throws Exception{
        Box.DIM_BOX = DIM_BOX;
        scaleDimensionTable(); //N.B: Game Table sizes are always (DIM * Box.DIM_BOX, DIM * DIM_BOX)
        CheckersTable table = CheckersTable.getInstance(DIM_TABLE, DIM_TABLE, revisedChecker);
        Creator factoryM = new ConcreteFactoryM();
        Player pl1 = (Player) factoryM.factoryMethod(p1Name, Color.red, null);
        Player pl2 = (Player) factoryM.factoryMethod(p2Name, Color.green, null);
        table.startGame(pl1, pl2); 
    }

    public String geticonPath(){
        return ICON_PATH;
    }
        
}
