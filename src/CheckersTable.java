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
    

    private JFrame frame;
    private JPanel panel;
    
    private Piece pToMove; 

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
        pToMove = p; 
        pToMove.showSuggestions(pToMove.setRowbyColor());
    }

    //This function move pToMove into destRectangle
    public void move(Rectangle destRectangle, int i_src, int j_src) throws Exception {
        int i,j;
        boolean upgrade = false; 
        Rectangle pToEatRect;
        Rectangle srcRectangle = rectangles[pToMove.getCoord().x][pToMove.getCoord().y]; //this rectangle contain the piece to be moved
        int diff = srcRectangle.getCoord().x - destRectangle.getCoord().x;

        if (diff > 1 || diff < -1){  //If have to eat difference is > 1 or < -1
            i = (diff > 1) ? pToMove.getCoord().x - 1 : pToMove.getCoord().x + 1;
            j = (srcRectangle.getCoord().y > destRectangle.getCoord().y) ? pToMove.getCoord().y - 1 : pToMove.getCoord().y + 1;
            pToEatRect = rectangles[i][j];
            Piece enemy = (Piece) rectangles[i][j].getComponent(0);
            addOrRemove(pToEatRect, false);
            //se un arciere viene mangiato
            if (enemy.getClass().toString().equals("class Archer") && pToMove.getClass().toString().equals("class Pawn"))
                    upgrade = true;
            //Se un arciere mangia
            if ( (enemy.getClass().toString().equals("class Pawn") || enemy.getClass().toString().equals("class Checkers") )&& pToMove.getClass().toString().equals("class Archer"))
                    respwan(pToEatRect);
        }


        pToMove.setCoord(destRectangle.getCoord().x, destRectangle.getCoord().y);
        Point pieceCoord = pToMove.getCoord();
      
        addOrRemove(srcRectangle, false);  //remove the old piece from the previous rectangle

        if (canPieceUpgrade() || upgrade){
            Creator factory = new ConcreteFactoryM();
            pToMove = (Piece) factory.factoryMethod("checkers", pToMove.getColor());
            pToMove.setCoord(pieceCoord.x, pieceCoord.y);
            addOrRemove(destRectangle, true);
            pToMove.addMouseListener((pToMove.getColor() == Color.red) ? p1 : p2);
        }
        else //Add piece to move in new rectagle
            addOrRemove(destRectangle, true);
        
    }
    
    //In rect will respawn a piece
    private void respwan(Rectangle rect) throws Exception{
        Creator factory = new ConcreteFactoryM();
        Piece piece = (Piece) factory.factoryMethod("pawn", pToMove.getColor());
        addOrRemove(rect, true, piece);
    }

    protected void showFreeRectangle(int row, int col){
            rectangles[row][col].setColor(Color.cyan);
            rectangles[row][col].repaint();
            pointsListToClear.add(new Point(row, col));
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

    public Rectangle getRectanglefromList(int row, int col){
        return rectangles[row][col];
    }

    public boolean illegalMove(int k){
        return (k >= N_ROWS || k < 0) ? true : false;
    }

    //When true add pieces, false remove.
    private void addOrRemove(Rectangle rect, boolean action){
        if(action == true)
            rect.add(pToMove, BorderLayout.CENTER);
        else
            rect.removeAll();
        rect.setHasPiece(action);
        rect.revalidate();
        rect.repaint();
    }

    private void addOrRemove(Rectangle rect, boolean action, Piece piece){
            if(action == true){
                piece.setCoord(rect.getCoord().x, rect.getCoord().y);
                rect.add(piece, BorderLayout.CENTER);
            }
            else
                rect.removeAll();
            rect.setHasPiece(action);
            rect.revalidate();
            rect.repaint();
            System.out.println("in rect " + rect + " ho inserito " + piece);
    }

    private boolean canPieceUpgrade(){
        String StrPiece = pToMove.getClass().toString();
        boolean isRedOnEnemyFstLine = pToMove.getColor() == Color.red && pToMove.getCoord().x == 0;
        boolean isGreenOnEnemyFstLine = pToMove.getColor() == Color.green && pToMove.getCoord().x == N_ROWS - 1;

        return (StrPiece.equals("class Pawn") && (isGreenOnEnemyFstLine || isRedOnEnemyFstLine)) ? true : false;
    }
    
}
