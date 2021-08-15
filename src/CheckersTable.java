//import javax.lang.model.util.ElementScanner6;
//import javax.management.MBeanAttributeInfo;
import javax.swing.*;

//import org.graalvm.compiler.nodes.calc.RightShiftNode;

//import jdk.internal.org.jline.terminal.spi.Pty;

import java.lang.Exception;
import java.awt.*;
//import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
//import java.util.Objects;

//Singleton
//Command Receiver
public class CheckersTable {
    private static CheckersTable Instance;

    private final int N_ROWS, N_COLS, DIM_RECT;
    private Player p1, p2;
    private Piece pToMove;

    private JFrame frame;
    private JPanel panel;

    private static Rectangle[][] rectangles;

    private List<Point> pointsListToClear = new ArrayList<Point>();

    private Point posAfterMove = new Point(); //free position on which to show suggestion 

    /*-------------------------------------------------------------------------------------------------
            p1 = new Player(Color.green,playerName);
            p2 = new Player(Color.red, playerName2);
            inizializeWindow();
     ---------------------------------------------------------------------------------------------------*/
    //Da modificare, invece delle stringe nome player passiamo gli oggetti player instanziati nel main (con eventuali interfacce etc.)
    private CheckersTable(final int N_ROWS, final int N_COLS, final int DIM) {
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        DIM_RECT = DIM;
    }

    //Singleton
    public static synchronized CheckersTable getInstance(final int N_ROWS, final int N_COLS, final int DIM) {
        if (Instance == null)
            Instance = new CheckersTable(N_ROWS, N_COLS, DIM);
        return Instance;
    }

    //get the existing instance without specifying unnecessary parameters
    public static synchronized CheckersTable getInstance() throws Exception {
        if (Instance == null)
            throw new Exception("ISTANCE NULL. Please use method: public static synchronized CheckersTable getInstance(final int N_ROWS, final int N_COLS, final int DIM)");
        return Instance;
    }

    //Create and set the main Frame
    private void createFrame() {
        frame = new JFrame("Dama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(N_ROWS * DIM_RECT, N_COLS * DIM_RECT);
        frame.setBackground(Color.white);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    //Create and set the Panel that  will contain all the elements of the game
    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(N_ROWS, N_COLS, 0, 0));
        panel.setPreferredSize(new Dimension(N_ROWS * DIM_RECT, N_COLS * DIM_RECT));
        panel.setBackground(Color.gray);
    }


    private void inizializeWindow() throws Exception {
        createFrame();
        createPanel();

        //Create and Set an array of game cells (Rectangle type) with and without pieces
        rectangles = Rectangle.createRectangles(N_ROWS, N_COLS, DIM_RECT, p1, p2);

        //Add rect to Table
        for (Rectangle[] row : rectangles)
            for (Rectangle rect : row)
                panel.add(rect);
        frame.add(panel);
        frame.setVisible(true);
    }

    protected void startGame(Player p1, Player p2) throws Exception {
        this.p1 = p1;
        this.p2 = p2;
        inizializeWindow();
    }

    //Shows the moves allowed to click on a piece
    protected void suggestions(Piece p) {
        pToMove = p; //pToMove is a istance variable which stores piece to be moved
        String classSelectedPiece = pToMove.getClass().toString();
        switch (classSelectedPiece){
            case "class Pawn":
                showSuggestions();
                break;
            case "class Archer":
                showSuggestions();
                //Ulteriori suggerimenti da aggiungere
                System.out.println("CASA ARCHER: Vediamo cosa fare per l'arciere");
                break;

            case "class Dama":
                showSuggestions();
                //Ulteriori suggerimenti da aggiungere
                System.out.println("CASE DAMA: Vediamo cosa fare per la DAMA");
                break;

            default:
             System.out.println("CASE DEFAULT: vediamo se lasciarlo.");
        }
    }

    protected void showSuggestions(){
        final int GOLEFTCOL = pToMove.getCoord().y - 1, GORIGHTCOL = pToMove.getCoord().y + 1;
        int esito_left, esito_right;

        posAfterMove.x = setRowbyColor();
        esito_left = checkMove(posAfterMove.x, GOLEFTCOL);

        if (esito_left == 0 || esito_left == 1){ // Se a sinistra non si può mangiare
            esito_right = checkMove(posAfterMove.x, GORIGHTCOL); //Vedo se a destra posso muovermi o mangiare
            if (esito_right == 2){ //DEVO mangiare a DESTRA
                posAfterMove.x = setRowonEat();
                showFreeRectangle(posAfterMove.x, posAfterMove.y);
            }
            else{ //Se nemmeno a destra si può mangiare allora..
                if (esito_left == 0) //Se posso muovermi a sinistra
                    showFreeRectangle(posAfterMove.x, GOLEFTCOL);   
                if (esito_right == 0) //Se posso muovermi a destra
                    showFreeRectangle(posAfterMove.x, GORIGHTCOL); 
            }
        }
        else if (esito_left == 2){ //DEVO mangiare a SINISTRA
            posAfterMove.x = setRowonEat();
            showFreeRectangle(posAfterMove.x, posAfterMove.y);
        }
    }
    // Indica la riga da colorare quando occorre mangiare
    protected int setRowonEat(){
        return (pToMove.getColor() == Color.red) ? pToMove.getCoord().x - 2 : pToMove.getCoord().x + 2; 
    }
    // Indica la colonna da colorare quando occorre mangiare
    protected int setColonEat(int col){
        return (col > pToMove.getCoord().y) ? pToMove.getCoord().y + 2 : pToMove.getCoord().y - 2;
    }

    protected int setRowbyColor(){
        return  (pToMove.getColor() == Color.red) ? pToMove.getCoord().x - 1 : pToMove.getCoord().x + 1;
    }
    
    protected int checkMove(int row, final int COL_DIRECTION){
        Point position = new Point(row, COL_DIRECTION); // Indica le coordinate del rettangolo da analizzare
        int result = enemyPiece_inRect(position);
        // System.out.println("Analizzo il rettangolo "+ i + " " + j); // System.out.println("Result = " + result);
        if(result == 2){ //ho trovato un pezzo avversario da mangiare (ma non so se posso mangiarlo)
            position.x = setRowonEat();
            position.y = setColonEat(position.y);
            posAfterMove.y = position.y;
            return (canIeat(position)) ? 2 : 1; //2: Devo mangiarlo. -- 1: Non posso mangiare, né posso muovermi.
        }
        else if(result == 0) //Se la casella è libera..
            return 0; //posso muovermi qui
        else
            return 1; //Non posso mangiare, né posso muovermi.
    }

    protected int enemyPiece_inRect(Point position){
        if(rectangles[position.x][position.y].getHasPiece()){
            // Se c'è un pezzo vediamo se è dello stesso colore di chi si muove
            Piece tmp = (Piece) rectangles[position.x][position.y].getComponent(0);
            boolean pezzo_avversario = !myColor.checkColors(pToMove.getColor(),tmp.getColor());
            return (pezzo_avversario) ? 2 : 1; // 2: pezzo avversario, forse è mangiabile 
        }                                      // 1: c'è un mio pezzo, non posso mangiarlo
        else
            return 0; //non c'è un pezzo, casella libera
    }

    protected boolean canIeat(Point position){
        return (enemyPiece_inRect(position) == 0) ? true : false; //true: il secondo rect è libero. SI DEVE MANGIARE.
    }                        //false: Non posso mangiare. Il secondo rect è occupato da un pezzo rosso o verde.

    protected void showFreeRectangle(int row, int col){
            rectangles[row][col].setColor(Color.cyan);
            rectangles[row][col].repaint();
            pointsListToClear.add(new Point(row, col));
    }

    //This function move pToMove into destRectangle
    public void move(Rectangle destRectangle, int i, int j) {
        Rectangle srcRectangle = rectangles[pToMove.getCoord().x][pToMove.getCoord().y]; //this rectangle contain the piece to be moved
        Component component_pToMove = srcRectangle.getComponent(0); // first component with index 0
        pToMove.setCoord(i, j);

        //Add piece to move in new rectagle
        destRectangle.add(component_pToMove);
        destRectangle.setHasPiece(true); // now rectangles has a piece.
        destRectangle.repaint();

        //remove the old piece from the previous rectangle
        srcRectangle.removeAll(); //remove all components by panel Rectangle (but it only has 1 component with index 0)
        srcRectangle.setHasPiece(false);
        srcRectangle.repaint();
    }

    public void clearSuggestions(){
        for (Point pointToClear : pointsListToClear){
            rectangles[pointToClear.x][pointToClear.y].setColor(Color.darkGray);
            rectangles[pointToClear.x][pointToClear.y].repaint();
        }
        pointsListToClear.clear();
    }

    public final int getDIM_RECT(){
        return DIM_RECT;
    }

    public final int getN_ROWS(){
        return N_ROWS;
    }

    public final int getN_COLS(){
        return N_COLS;
    }

}
