package Game.GraphicObjects.Start;


import Game.EnumIndices;
import Game.GameObjects.ConcreteFactoryM;
import Game.GameObjects.Factory;
import Game.GameObjects.Players.Player;
import Game.GraphicObjects.GraphicWindow;
import Game.GraphicObjects.Table.Box;
import Game.GraphicObjects.Table.CheckersTable;
import Game.ImageComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class CheckersStart extends GraphicWindow implements ActionListener {

    //Singleton Eager initialization
    private static final CheckersStart Instance = new CheckersStart();


    private String  firstPlayerName, secondPlayerName;
    private int     dimTable;
    private Boolean modeRevised;

    private ArrayList<JComponent> jComponentList = new ArrayList<>();

    private String stringAction = "0";
    private ArrayList<String> listActionCommands = new ArrayList<>();

    public boolean centerTableY = true;
    public int n_sec;


     //Singleton Eager initialization
    public static CheckersStart getInstance(){
        return Instance;
    }

    private CheckersStart(){
        
        frame = addFrame("Checkers Game - Luca Rubino 1934 / Renato Esposito 1881", 190, 380, Color.WHITE, false, new BorderLayout(0,0), ICON_PATH, true, true, JFrame.EXIT_ON_CLOSE);
        panel = addPanel(190, 380, frame.getBackground(), new FlowLayout());

        jComponentList.add(addLabel("CheckersGame", new Font("Verdana", Font.PLAIN, 18))); 

        jComponentList.add(addLabel("table size"));
        
        String[] dimensionStrings = { "4", "6", "8", "10", "12", "14", "16"};
        jComponentList.add(addComboBoxString(dimensionStrings, EnumIndices.THIRD, false, this, stringAction));
        addcommandtoList("combobox1");

        jComponentList.add(addLabel("game mode"));

        String[] modeStrings = { "classic", "revised"};
        jComponentList.add(addComboBoxString(modeStrings, EnumIndices.SECOND, false));

        jComponentList.add(addLabel("timer"));

        String[] timeStrings = {"10", "60", "120", "180", "240", "480"};
        jComponentList.add(addComboBoxString(timeStrings, EnumIndices.FOURTH, false));
        
        jComponentList.add(addLabel("Choose name Player 1"));
        
        jComponentList.add(addTextField("Player1", new Dimension(100, 20), true));
        
        jComponentList.add(addLabel("Choose name Player 2"));
        
        jComponentList.add(addTextField("Player2", new Dimension(100, 20), true));
        
        jComponentList.add(addButton("Start Game", this, stringAction));
        addcommandtoList("Start Game");
        
        jComponentList.add(addButton("Game Rules", this, stringAction));
        addcommandtoList("game rules");

        Dimension Imgdim = new Dimension(38,38);
        panel.add(new ImageComponent("/images/PawnGreen.png", Imgdim));
        panel.add(new ImageComponent("/images/CheckersGreen.png", Imgdim));
        panel.add(new ImageComponent("/images/WizardRed.png", Imgdim));
        panel.add(new ImageComponent("/images/PawnRed.png", Imgdim));
       

        for (JComponent jb : jComponentList)
            panel.add(jb);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void addcommandtoList(String nameObject){
        listActionCommands.add(nameObject);
        int intAction = Integer.parseInt((String)stringAction);
        stringAction = String.valueOf(++intAction); //Increase action because a nameObject is added
    }

   
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "0":
                break;
            case "1":
                firstPlayerName = ((JTextField) jComponentList.get(8)).getText();
                secondPlayerName = ((JTextField) jComponentList.get(10)).getText();
                n_sec = Integer.parseInt((String)(((JComboBox<?>)jComponentList.get(6)).getSelectedItem()));
                dimTable = Integer.parseInt((String)(((JComboBox<?>)jComponentList.get(2)).getSelectedItem()));
                modeRevised = ((JComboBox<?>)jComponentList.get(4)).getSelectedItem().equals("revised"); //true revised, false classic
                if (firstPlayerName.isBlank() || secondPlayerName.isBlank()) return;
                try {
                    frame.setVisible(false); //hide CheckersStart Window
                    startGame(firstPlayerName, secondPlayerName, dimTable, Box.DIM_BOX, modeRevised, n_sec);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "2":
                JOptionPane.showMessageDialog(frame, "REVISED MODE: Ogni giocatore dispone di N pedine e 2 maghi (N in base alla dimensione del campo da gioco) di colore diverso rispetto a quelle dell'avversario.\n Il giocatore verde fa sempre la prima mossa.\n" +
                "L'obiettivo del gioco è quello di mangiare tutti i pezzi dell'avversario o di fare il miglior punteggio entro il tempo limite\n" +
                "Sul campo da gioco sono presenti i seguenti pezzi:\n" + 
                "Pedina: pezzo classico che si muove solamente in diagonale di una casella alla volta e soltanto in avanti. Quando una pedina raggiunge una delle caselle dell'ultima riga viene promossa diventando dama.\n" +
                "Dama: upgrade della pedina. Può muoversi in più direzioni ad un passo alla volta.\n"+
                "Mago: Se il mago mangia un pezzo, resuscita una pedina. Invece se viene mangiato la pedina avversaria diventa dama. Un mago non può diventare dama e non può mangiare un altro mago.\n" +
                "Il punteggio è dato dalla tipologia e dalla quantità di pezzi mangiati, in particolare:\n Pedina: 1 punto.\n Mago: 3 punti.\n Dama: 5 punti\n\n" +
                "REVISED MODE: Each player has N pieces and 2 Wizard (N according to the size of the playing field) of a different color than the opponent's. \n The green player always makes the first move. \n "+
                "The goal of the game is to eat all the opponent's pieces or to get the best score within the time limit \n" +
                "The following pieces are on the playing field: \n" +
                "Pawn: classic piece that only moves diagonally one square at a time and only forward. When a pawn reaches one of the squares in the last row it is promoted to become king. \n" +
                "Checkers: checker upgrade. Can move in multiple directions one step at a time. \n" +
                "Wizard: If the Wizard eats a piece, he resurrects a piece. If it is eaten, the opposing piece becomes a checker. A Wizard cannot become a checker and cannot eat another mage. \n" +
                "The score is given by the type and quantity of pieces eaten, in particular: \n Pawn: 1 point. \n Wizard: 3 points. \n Checkers: 5 points");
            default:
            break;
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
    
    
    private void startGame(String p1Name, String p2Name, int DIM_TABLE, int DIM_BOX, boolean revisedChecker, int n_sec) throws Exception{
        Box.DIM_BOX = DIM_BOX;
        scaleDimensionTable(); //N.B: Game Table sizes are always (DIM * Box.DIM_BOX, DIM * DIM_BOX)
        CheckersTable table = CheckersTable.getInstance(DIM_TABLE, DIM_TABLE, revisedChecker);
        Factory factoryM = new ConcreteFactoryM();
        Player pl1 = (Player) factoryM.factoryMethod(p1Name, Color.red, null);
        Player pl2 = (Player) factoryM.factoryMethod(p2Name, Color.green, null);
        table.startGame(pl1, pl2, n_sec); 
    }

        
}
