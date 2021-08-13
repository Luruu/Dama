import javax.lang.model.util.ElementScanner6;
import javax.management.MBeanAttributeInfo;
import javax.swing.*;

//import org.graalvm.compiler.nodes.calc.RightShiftNode;

//import jdk.internal.org.jline.terminal.spi.Pty;

import java.lang.Exception;
import java.awt.*;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private int j_eat; //è la coordianta j del rettangolo su cui posizionarsi dopo aver mangiato

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
    public static synchronized CheckersTable getInstance(final int N, final int C, final int DIM) {
        if (Instance == null)
            Instance = new CheckersTable(N, C, DIM);
        return Instance;
    }

    //get the existing instance without specifying unnecessary parameters
    public static synchronized CheckersTable getInstance() throws Exception {
        if (Instance == null)
            throw new Exception("ISTANCE NULL. Please use method: public static synchronized CheckersTable getInstance(final int n, final int c, final int dim)");
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
    protected void suggestion(Piece p) {
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
        final int LEFT = pToMove.getCoord().y - 1, RIGHT = pToMove.getCoord().y + 1;
        int esito_left, esito_right = 0;
        int row = setRowonEat();
        esito_left = checkMove(setRowbyColor(), LEFT);
        //Se ritorna 2 devo mangiare quindi non coloro right
        //Se ritorna 0 o 1 controllo a dx, se a dx ritorna 0 coloro sia dx che sx
        
        if (esito_left == 0 || esito_left == 1){ // Se a sx non pouoi mangiare
            esito_right = checkMove(setRowbyColor(), RIGHT); //Se non devo mangiare posso vedere se anche a destra posso muovermi o mangiare
            if (esito_right == 2) //Sei obbligato a mangiare verso dx
                showFreeRectangle(row, j_eat);
            else{ // Coloro i rect liberi
                if (esito_right == 0)
                    showFreeRectangle(setRowbyColor(), RIGHT);
                if (esito_left == 0)
                    showFreeRectangle(setRowbyColor(), LEFT);    
            }
        }
        else if (esito_left == 2){ //Puoi mangiare a sx 
            showFreeRectangle(row, j_eat);
        }
    }
    protected int setRowonEat(){
        return (pToMove.getColor() == Color.red) ? setRowbyColor() - 1 : setRowbyColor() + 1 ; // Indica la riga da colorare quando occorre mangiare
    }
    protected int setColonEat(int j){
        System.out.println(pToMove.getCoord().y + " j = " + j
        );
        return (j > pToMove.getCoord().y) ? j + 1 : j - 1;
    }

    protected int setRowbyColor(){
        if(pToMove.getColor() == Color.red)
            return pToMove.getCoord().x - 1;
        else
            return pToMove.getCoord().x + 1;
    }
    // Comment
    protected int checkMove(int i, int j){
        Point position = new Point(i,j); // Indica le coordinate del rettangolo da analizzare
        System.out.println("Analizzo il rettangolo "+ i + " " + j);
        boolean mustEat;
        int result = enemyPiece_inRect(position);
        System.out.println("Result = " + result);
        //AGGIORNARE POSITION CON LA POSIZIONE SUCCESSIVA AL PEZZO DA MANGIARE
        
        if(result == 2){ //hai trovato un pezzo avversario DA MANGIARE per cui c'è un pezzo ed è un pezzo avversario in rect_xy
            position.x = setRowonEat();
            j_eat = setColonEat(position.y);
            System.out.println(position.y);
            mustEat = canIeat(new Point(position.x, j_eat));// Forse si può maniare
            if(mustEat) // Devo mangiarlo
                return 2;
        }
        else if(result == 0)
            return 0;//può muoversi qui
        else if(result == 1)
            return 1; // Non posso né mangiare, né muovermi.
        return 1; 
    }

    protected int enemyPiece_inRect(Point position){
        if(rectangles[position.x][position.y].getHasPiece()){
            // Se c'è un pezzo vediamo se è dello stesso colore di chi si muove
            Piece tmp = (Piece) rectangles[position.x][position.y].getComponent(0);
            boolean pezzo_avversario = !checkColors(pToMove.getColor(),tmp.getColor());
            if (pezzo_avversario)
                return 2; //pezzo avversario, forse posso mangiarlo
            else
                return 1; //c'è un mio pezzo, non posso mangiarlo
        }
        else
            return 0; //non c'è un pezzo, la casella libera
    }
    protected boolean canIeat(Point position){
        int result = enemyPiece_inRect(position);
        if(result == 0) //il secondo rettangolo è libero. SI DEVE MANGIARE.
            return true;
        else            //Non posso mangiare. Il secondo rettangolo è occupato da un pezzo rosso o verde.
            return false; 
    }

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

    //Dobbiamo passare solo la riga corretta
    private boolean checkLeft(int i, int j){
        return (rectangles[i][j - 1].getHasPiece()) ? true : false;
    }

    //Dobbiamo passare solo la riga corretta
    private boolean checkRight(int i, int j){
        return (rectangles[i][j + 1].getHasPiece()) ? true : false;
    }

    private boolean checkColors(Color a, Color b){
        return (a == b) ? true : false;
    }
}
