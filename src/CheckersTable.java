import javax.swing.*;
import java.lang.Exception;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
            for (Rectangle rec_rowj : row)
                panel.add(rec_rowj);

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
        Point coord = pToMove.getCoord();
        String classSelectedPiece = pToMove.getClass().toString();
        Color colorSelectedPiece = pToMove.getColor();
        Point eatCoord;
        switch (classSelectedPiece){
            case "class Pawn":
                if (colorSelectedPiece == Color.red){
                    if ((eatCoord = showSuggestion(coord.x - 1, coord.y)) != null)
                        showEatSuggestion(eatCoord.x, eatCoord.y);
                } // parenthesis required!
                else if (colorSelectedPiece == Color.green)
                    if ((eatCoord = showSuggestion(coord.x + 1, coord.y)) != null) 
                        showEatSuggestion(eatCoord.x, eatCoord.y);
                break;

            case "class Archer":
                /* DEAD CODE: NON CI VA MAI. (ERRORE SEGNALATO DA VSCODE)
                if (p == null) { } */ 
                System.out.println("CASA ARCHER: Vediamo cosa fare per l'arciere");
                break;

            case "class Dama":
                System.out.println("CASE DAMA: Vediamo cosa fare per la DAMA");
                break;

            default:
             System.out.println("CASE DEFAULT: vediamo se lasciarlo.");
        }
    }


    //Show (paint the rect) the suggestions on the game's table: Return Point when can eat the piece.
    private Point showSuggestion(int i_row, int j) {
        int j_coordPieceToEat;
        int j_left_pos = j - 1, j_right_pos = j + 1;
        System.out.println("i_row = " + i_row + " j = " + j);

        if (j_left_pos >= 0 && i_row > -1 && i_row < N_ROWS) {
            if(!rectangles[i_row][j_right_pos].getHasPiece()){
                if (showFreeRectangle(i_row, j_left_pos))
                    pointsListToClear.add(new Point(i_row, j_left_pos)); 
                else{
                    j_coordPieceToEat = rectangles[i_row][j_left_pos].getHasPiece() ? j_left_pos : j_right_pos;
                    return new Point(i_row, j_coordPieceToEat); //return the coords of the piece that you can eat
                }
            }
        }

        if (j_right_pos < N_COLS && i_row > -1 && i_row < N_ROWS)
            if (showFreeRectangle(i_row, j_right_pos)) //If rectangle is free from pieces
                pointsListToClear.add(new Point(i_row, j_right_pos)); //add new coordinates to clear
            else
                return new Point(i_row, j_right_pos); //else return: piece you can eat

        return null; //return: null when you can't eat nobody
    }
    private void showEatSuggestion(int i_pToEat, int j_pToEat) {
        int i_bottom = i_pToEat - 1, j_right = j_pToEat + 1, j_left = j_pToEat - 1;

        int pToMove_bottom  = pToMove.getCoord().x - 1;
        int pToMove_left    = pToMove.getCoord().y - 1;
        int pToMove_right   = pToMove.getCoord().y + 1;

        boolean eating_byleft = pToMove.getCoord().y < j_pToEat;
        if (eating_byleft) {                        //Move to the RIGHT DIAGONAL
            if (showFreeRectangle(i_bottom, j_right))
                pointsListToClear.add(new Point(i_bottom, j_right));
            else //Can't eat on the diagonal
                if (showFreeRectangle(pToMove_bottom, pToMove_left))
                    pointsListToClear.add(new Point(pToMove_bottom, pToMove_left));
        } 
        else {                                      //Move to the LEFT DIAGONAL
            if (showFreeRectangle(i_bottom, j_left))
                pointsListToClear.add(new Point(i_bottom, j_left));
            else
                if (showFreeRectangle(pToMove_bottom, pToMove_right)) 
                    pointsListToClear.add(new Point(pToMove_bottom, pToMove_right));
        }

    }

    protected boolean showFreeRectangle(int row, int col){
        if (!rectangles[row][col].getHasPiece()){ //if Rectangle is free, then..
            rectangles[row][col].setColor(Color.cyan);
            rectangles[row][col].repaint();
            return true; //cyan suggestion is shown
        }
        return false; // Rectangle has a piece, so cyan suggestion is not shown
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
}
