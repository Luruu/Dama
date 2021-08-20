import javax.swing.*;

import java.lang.Exception;
import java.awt.*;
import java.util.ArrayList;

//Singleton
//Command Receiver
public class CheckersTable {

    private static CheckersTable Instance; //Singleton

    private final int N_ROWS, N_COLS, DIM_RECT;
    
    private Player p1, p2;

    private JFrame frame;
    private JPanel panel;
    
    private Piece pToMove; 

    private static Rectangle[][] rectangles;

    private ArrayList<Point> pointsListToClear = new ArrayList<Point>();

    private CheckersTable(final int N_ROWS, final int N_COLS, final int DIM_RECT) {
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        this.DIM_RECT = DIM_RECT;
    }

    //Singleton
    public static synchronized CheckersTable getInstance(final int N_ROWS, final int N_COLS, final int DIM) {
        if (Instance == null)
            Instance = new CheckersTable(N_ROWS, N_COLS, DIM);
        return Instance;
    }

    //Singleton, but get the existing instance without specifying unnecessary parameters
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


    private void initializeWindow() throws Exception {
        createFrame();
        createPanel();

        //Create and Set an array of game cells (Rectangle type)
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
        initializeWindow();
    }

    //Shows the moves allowed to click on a piece
    protected void suggestions(Piece pClicked) {
        pToMove = pClicked; 
        pToMove.showSuggestions(pToMove.setRowbyColor());
    }

    //Move pToMove into destRectangle
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
            Piece enemyPiece = rectangles[i][j].getPiece();
            String enemyPieceClass = enemyPiece.getClass().toString();
            String pToMoveClass = pToMove.getClass().toString();
            addOrRemove(pToEatRect, false);
            
            if (enemyPieceClass.equals("class Archer") && pToMoveClass.equals("class Pawn")) //if pawn eat archer
                upgrade = true; //Pawn will be a new checkers
            
            if ((enemyPieceClass.equals("class Pawn") || enemyPieceClass.equals("class Checkers")) && pToMoveClass.equals("class Archer")) //if archer eat
                respawn(pToEatRect); //add a new piece
            
            Player player = pToMove.getOwner();
            player.addPlayerPoints(enemyPiece.getPoints()); // increase player's score after eating
        }


        pToMove.setCoord(destRectangle.getCoord().x, destRectangle.getCoord().y);
        Point pieceCoord = pToMove.getCoord();
      
        addOrRemove(srcRectangle, false);  //remove the old piece from the previous rectangle

        if (canPieceUpgrade() || upgrade){ //If pawn can upgrade
            Creator factory = new ConcreteFactoryM();
            pToMove = (Piece) factory.factoryMethod("checkers", pToMove.getColor(), pToMove.getOwner());
            pToMove.setCoord(pieceCoord.x, pieceCoord.y);
            addOrRemove(destRectangle, true);
        }
        else //Add a piece to move in new rectagle
            addOrRemove(destRectangle, true);
        
    }
    
    //In rect will respawn a piece
    private void respawn(Rectangle rect) throws Exception{
        Creator factory = new ConcreteFactoryM();
        Piece piece = (Piece) factory.factoryMethod("pawn", pToMove.getColor(), pToMove.getOwner());
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

    

    //When true add pieces, false remove.
    private void addOrRemove(Rectangle rect, boolean action){
        if(action == true)
            rect.add(pToMove, BorderLayout.CENTER);
        else
            rect.removeAll();
        rect.HasPiece(action);
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
        rect.HasPiece(action);
        rect.revalidate();
        rect.repaint();
    }

    private boolean canPieceUpgrade(){
        String StrPiece = pToMove.getClass().toString();
        boolean isRedOnEnemyFstLine = pToMove.getColor() == Color.red && pToMove.getCoord().x == 0;
        boolean isGreenOnEnemyFstLine = pToMove.getColor() == Color.green && pToMove.getCoord().x == N_ROWS - 1;

        return (StrPiece.equals("class Pawn") && (isGreenOnEnemyFstLine || isRedOnEnemyFstLine)) ? true : false;
    }

    public boolean illegalMove(int k){
        return (k >= N_ROWS || k < 0) ? true : false;
    }

    public Rectangle getRectanglefromList(int row, int col){
        return rectangles[row][col];
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
