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
    
    private Piece pToMove; // Piece to move when a Box is clicked by a player

    private static Box[][] Boxes; // Matrix of all the Boxes of the board 

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
        frameTable = CGO.addFrame("Checkers Table", N_ROWS * Box.DIM_BOX, N_COLS * Box.DIM_BOX, Color.WHITE, false, new BorderLayout(0,0), CheckersStart.getIstance().geticonPath());
        panelTable = CGO.addPanel(N_ROWS * Box.DIM_BOX, N_COLS * Box.DIM_BOX, Color.blue, new GridLayout(N_ROWS, N_COLS, 0, 0));
 
        //create new Boxes (all game table) and add them to the new panel
        Boxes = Box.createBoxes(N_ROWS, N_COLS, Box.DIM_BOX, p1, p2);
        addBoxesToPanel();

        frameTable.add(panelTable);
        frameTable.setVisible(true);
    }

    private void addBoxesToPanel(){
        for (Box[] row : Boxes)
            for (Box box : row)
                panelTable.add(box);
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

    //Move pToMove into destBox
    public void move(Box dstBox) throws Exception {
        boolean upgrade = false; 

        Box srcBox = Boxes[pToMove.getCoord().x][pToMove.getCoord().y]; //this Box contain the piece to be moved
        int diff = srcBox.getCoord().x - dstBox.getCoord().x;

        if (diff > 1 || diff < -1){  //If have to eat difference is > 1 or < -1
            int i = (diff > 1) ? pToMove.getCoord().x - 1 : pToMove.getCoord().x + 1;
            int j = (srcBox.getCoord().y > dstBox.getCoord().y) ? pToMove.getCoord().y - 1 : pToMove.getCoord().y + 1;
            Box pToEatBox = Boxes[i][j];
            Piece enemyPiece = Boxes[i][j].getPiece();
            String enemyPieceClass = enemyPiece.getClass().toString();
            String pToMoveClass = pToMove.getClass().toString();

            addOrRemove(pToEatBox, false);
            
            if (enemyPieceClass.equals("class Wizard") && pToMoveClass.equals("class Pawn")) //if pawn eat Wizard
                upgrade = true; //Pawn will be a new checkers
            
            if ((enemyPieceClass.equals("class Pawn") || enemyPieceClass.equals("class Checkers")) && pToMoveClass.equals("class Wizard")) //if Wizard eat
                respawn(pToEatBox); //add a new piece
            
            Player player = pToMove.getOwner();
            player.addPlayerPoints(enemyPiece.getPoints()); // increase player's score after eating
        }


        pToMove.setCoord(dstBox.getCoord().x, dstBox.getCoord().y);
        Point pieceCoord = pToMove.getCoord();
      
        addOrRemove(srcBox, false);  //remove the old piece from the previous Box

        if (canPieceUpgrade() || upgrade){ //If pawn can upgrade
            Creator factory = new ConcreteFactoryM();
            pToMove = (Piece) factory.factoryMethod("checkers", pToMove.getColor(), pToMove.getOwner());
            pToMove.setCoord(pieceCoord.x, pieceCoord.y);
            addOrRemove(dstBox, true);
        }
        else //Add a piece to move in new Box
            addOrRemove(dstBox, true);
    }
    
    //In Box will respawn a piece
    private void respawn(Box box) throws Exception{
        Creator factory = new ConcreteFactoryM();
        Piece piece = (Piece) factory.factoryMethod("pawn", pToMove.getColor(), pToMove.getOwner());
        addOrRemove(box, true, piece);
    }

    protected void showFreeBox(int row, int col){
            Boxes[row][col].setColor(Color.cyan);
            Boxes[row][col].repaint();
            pointsListToClear.add(new Point(row, col));
    }

    public void clearSuggestions(){
        for (Point pointToClear : pointsListToClear){
            Boxes[pointToClear.x][pointToClear.y].setColor(Color.darkGray);
            Boxes[pointToClear.x][pointToClear.y].repaint();
        }
        pointsListToClear.clear();
    }

    

    //true: adds existing piece - false: remove a piece
    private void addOrRemove(Box box, boolean action){
        if(action == true)
            box.add(pToMove, BorderLayout.CENTER);
        else
            box.removeAll();
        box.HasPiece(action);
        box.revalidate();
        box.repaint();
    }

    //true: adds a new piece - false: remove a piece
    private void addOrRemove(Box box, boolean action, Piece piece){
        if(action == true){
            piece.setCoord(box.getCoord().x, box.getCoord().y);
            box.add(piece, BorderLayout.CENTER);
        }
        else
            box.removeAll();
        box.HasPiece(action);
        box.revalidate();
        box.repaint();
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

    public Box getBoxfromList(int row, int col){
        return Boxes[row][col];
    }

    public final int getN_ROWS(){
        return N_ROWS;
    }

    public final int getN_COLS(){
        return N_COLS;
    }
}
