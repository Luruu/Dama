package Game.GraphicObjects.Start;


import Game.GameObjects.FactoryM.ConcreteFactoryM;
import Game.GameObjects.FactoryM.Factory;
import Game.GameObjects.Pieces.Checkers;
import Game.GameObjects.Pieces.Pawn;
import Game.GameObjects.Pieces.Wizard;
import Game.GameObjects.Players.Player;
import Game.GraphicObjects.GraphicWindow;
import Game.GraphicObjects.Table.CheckersTable;
import Game.Image.ComponentImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * <h1>CheckersStart class</h1>
 * This class encapsulates the mechanism for setting settings before starting the concrete game.
 */
public class CheckersStart extends GraphicWindow implements ActionListener {

    //Singleton Eager initialization
    private static final CheckersStart Instance = new CheckersStart();


    private String  firstPlayerName, secondPlayerName;
    private int     dimTable , time;
    private Boolean modeRevised;

    private ArrayList<JComponent> jComponentList = new ArrayList<>();

    private String stringAction = "0";
    private ArrayList<String> listActionCommands = new ArrayList<>();


    /**
     * Singleton Eager initialization.
     * @return CheckersStart instance.
     */
    public static CheckersStart getInstance(){
        return Instance;
    }

    /**
     * Private constructor of the class, it sets the initial window from which to modify the settings and start the game.
     */
    private CheckersStart(){
        
        frame = addFrame("Checkers Game - Luca Rubino 1934 / Renato Esposito 1881", 190, 380, Color.WHITE, false, new BorderLayout(0,0), ICON_PATH, true, true, JFrame.EXIT_ON_CLOSE);
        panel = addPanel(190, 380, frame.getBackground(), new FlowLayout());

        jComponentList.add(addLabel("CheckersGame", new Font("Verdana", Font.PLAIN, 18))); 

        jComponentList.add(addLabel("table size"));
        
        String[] dimensionStrings = { "4", "6", "8", "10", "12", "14", "16"};
        jComponentList.add(addComboBoxString(dimensionStrings, EnumIndices.THIRD, false));


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
        addtoCommandsList("Start Game");
        
        jComponentList.add(addButton("Game Rules", this, stringAction));
        addtoCommandsList("game rules");

        Dimension Imgdim = new Dimension(38,38);
        panel.add(new ComponentImage(Pawn.PAWGREEN_Path, Imgdim));
        panel.add(new ComponentImage(Checkers.CHECKGREEN_Path, Imgdim));
        panel.add(new ComponentImage(Wizard.WIZRED_Path, Imgdim));
        panel.add(new ComponentImage(Pawn.PAWRED_Path, Imgdim));
       

        for (JComponent jb : jComponentList)
            panel.add(jb);

        frame.add(panel);
        frame.setVisible(true);
    }

    /**
     * This method adds a string to an action list.
     * @param nameObject represents a specific action performed on the initial panel.
     */
    public void addtoCommandsList(String nameObject){
        listActionCommands.add(nameObject);
        int intAction = Integer.parseInt((String)stringAction);
        stringAction = String.valueOf(++intAction); //Increase action because a nameObject is added
    }

   /**
    * This method analyzes the selected item on the panel and starts the associated procedure.
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action){
            case "0":
                firstPlayerName = ((JTextField) jComponentList.get(8)).getText();
                secondPlayerName = ((JTextField) jComponentList.get(10)).getText();
                time = Integer.parseInt((String)(((JComboBox<?>)jComponentList.get(6)).getSelectedItem()));
                dimTable = Integer.parseInt((String)(((JComboBox<?>)jComponentList.get(2)).getSelectedItem()));
                modeRevised = ((JComboBox<?>)jComponentList.get(4)).getSelectedItem().equals("revised"); //true revised, false classic
                if (firstPlayerName.isBlank() || secondPlayerName.isBlank()) return;
                try {
                    frame.setVisible(false); //hide CheckersStart Window
                    openWindowTable(firstPlayerName, secondPlayerName, dimTable, modeRevised, time);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case "1":
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

    /**
     * Set the players and call the game table. The game begins.
     * @param p1Name the name of the first player
     * @param p2Name the name of the second player
     * @param DIM_TABLE the size of the table
     * @param DIM_BOX the size of every single box
     * @param revisedChecker game mode
     * @param n_sec timer value
     * @throws Exception
     */
    private void openWindowTable(String p1Name, String p2Name, int DIM_TABLE, boolean revisedChecker, int n_sec) throws Exception{
        CheckersTable table = CheckersTable.getInstance(DIM_TABLE, DIM_TABLE, revisedChecker);
        Factory factoryM = new ConcreteFactoryM();
        Player pl1 = (Player) factoryM.factoryMethod(p1Name, Color.red, null);
        Player pl2 = (Player) factoryM.factoryMethod(p2Name, Color.green, null);
        table.startGame(pl1, pl2, n_sec); 
    }
}
