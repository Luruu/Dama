import javax.swing.*;
import java.awt.*;

import java.lang.Exception;
import java.util.ArrayList;


public class CheckersTable {

    private static CheckersTable Instance; //Singleton

    private final int N_ROWS, N_COLS;
    
    private Player p1, p2;

    private JFrame frameTable;
    private JPanel panelTable;
    
    private Piece pToMove; // Piece to move when a rectangle is clicked by a player

    private static Rectangle[][] rectangles; // Matrix of all the rectangles of the board 

    private ArrayList<Point> pointsListToClear = new ArrayList<Point>();

    private CheckersTable(final int N_ROWS, final int N_COLS) {
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
    }

    //Singleton (Lazy Initialization because constructor parameters)
    public static synchronized CheckersTable getInstance(final int N_ROWS, final int N_COLS) {
        if (Instance == null)
            Instance = new CheckersTable(N_ROWS, N_COLS);
        return Instance;
    }

    //Singleton, but get the existing instance without specifying unnecessary parameters
    public static synchronized CheckersTable getInstance() throws Exception {
        if (Instance == null)
            throw new Exception("ISTANCE NULL. Please use method: public static synchronized CheckersTable getInstance(final int N_ROWS, final int N_COLS)");
        return Instance;
    }


    private void initializeWindow() throws Exception {
        frameTable = CGO.createFrame("Checkers Table", N_ROWS * Rectangle.DIM_RECT, N_COLS * Rectangle.DIM_RECT, Color.WHITE, false, new BorderLayout(0,0));
        panelTable = CGO.createPanel(N_ROWS * Rectangle.DIM_RECT, N_COLS * Rectangle.DIM_RECT, Color.blue, new GridLayout(N_ROWS, N_COLS, 0, 0));
 
        //create new rectangles (all game table) and add them to the new panel
        rectangles = Rectangle.createRectangles(N_ROWS, N_COLS, Rectangle.DIM_RECT, p1, p2);
        addRectsToPanel();

        frameTable.add(panelTable);
        frameTable.setVisible(true);
    }

    private void addRectsToPanel(){
        for (Rectangle[] row : rectangles)
            for (Rectangle rect : row)
                panelTable.add(rect);
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
    public void move(Rectangle dstRectangle) throws Exception {
        boolean upgrade = false; 

        Rectangle srcRectangle = rectangles[pToMove.getCoord().x][pToMove.getCoord().y]; //this rectangle contain the piece to be moved
        int diff = srcRectangle.getCoord().x - dstRectangle.getCoord().x;

        if (diff > 1 || diff < -1){  //If have to eat difference is > 1 or < -1
            int i = (diff > 1) ? pToMove.getCoord().x - 1 : pToMove.getCoord().x + 1;
            int j = (srcRectangle.getCoord().y > dstRectangle.getCoord().y) ? pToMove.getCoord().y - 1 : pToMove.getCoord().y + 1;
            Rectangle pToEatRect = rectangles[i][j];
            Piece enemyPiece = rectangles[i][j].getPiece();
            String enemyPieceClass = enemyPiece.getClass().toString();
            String pToMoveClass = pToMove.getClass().toString();

            addOrRemove(pToEatRect, false);
            
            if (enemyPieceClass.equals("class Wizard") && pToMoveClass.equals("class Pawn")) //if pawn eat Wizard
                upgrade = true; //Pawn will be a new checkers
            
            if ((enemyPieceClass.equals("class Pawn") || enemyPieceClass.equals("class Checkers")) && pToMoveClass.equals("class Wizard")) //if Wizard eat
                respawn(pToEatRect); //add a new piece
            
            Player player = pToMove.getOwner();
            player.addPlayerPoints(enemyPiece.getPoints()); // increase player's score after eating
        }


        pToMove.setCoord(dstRectangle.getCoord().x, dstRectangle.getCoord().y);
        Point pieceCoord = pToMove.getCoord();
      
        addOrRemove(srcRectangle, false);  //remove the old piece from the previous rectangle

        if (canPieceUpgrade() || upgrade){ //If pawn can upgrade
            Creator factory = new ConcreteFactoryM();
            pToMove = (Piece) factory.factoryMethod("checkers", pToMove.getColor(), pToMove.getOwner());
            pToMove.setCoord(pieceCoord.x, pieceCoord.y);
            addOrRemove(dstRectangle, true);
        }
        else //Add a piece to move in new rectagle
            addOrRemove(dstRectangle, true);
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

    

    //true: adds existing piece - false: remove a piece
    private void addOrRemove(Rectangle rect, boolean action){
        if(action == true)
            rect.add(pToMove, BorderLayout.CENTER);
        else
            rect.removeAll();
        rect.HasPiece(action);
        rect.revalidate();
        rect.repaint();
    }

    //true: adds a new piece - false: remove a piece
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

        return StrPiece.equals("class Pawn") && (isGreenOnEnemyFstLine || isRedOnEnemyFstLine);
    }

    public boolean illegalMove(int k){
        return k >= N_ROWS || k < 0;
    }


    // Getters and Setters methods..

    public Rectangle getRectanglefromList(int row, int col){
        return rectangles[row][col];
    }

    public final int getN_ROWS(){
        return N_ROWS;
    }

    public final int getN_COLS(){
        return N_COLS;
    }
}
