package Game.Windows.Table;
import javax.swing.*;

import Game.TimerObservable;
import Game.FactoryM.ConcreteFactoryM;
import Game.FactoryM.Factory;
import Game.FactoryM.Pieces.Piece;
import Game.FactoryM.Players.Observer;
import Game.FactoryM.Players.Player;
import Game.Windows.Start.CheckersStart;
import Game.Windows.GraphicWindow;


import java.awt.*;
import java.lang.Exception;
import java.util.ArrayList;

/**
 * <h1>CheckersTable Class</h1> 
 * it is the class that has all the features and methods that concern the <b>creation</b> and <b>management</b> of the board.
 * @author <a href="https://github.com/Luruu">Luca Rubino</a>
 * @author <a href="https://github.com/RenatoEsposito1999">Renato Esposito</a>
 * @version 1.0
 * @since 31-08-2021
 */

public class CheckersTable extends GraphicWindow {

    private static CheckersTable Instance; //Singleton

    private int N_ROWS, N_COLS;

    private boolean revisedChecker;

    private Player p1, p2;

    private JFrame frameTable;
    private JPanel panelTable;
    private PanelInfo panelInfo;
    
    private Piece pToMove; // Piece to move when a Box is clicked by a player

    private Box[][] Boxes; // Matrix of all the Boxes of the board 

    private ArrayList<Point> pointsListToClear = new ArrayList<Point>();

    private CheckersMemento memento;
    
    public  Color activePlayer = Color.red;

    private int timer_value;

    TimerObservable timer;


    private CheckersTable(int N_ROWS, int N_COLS, boolean revisedChecker) {
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        this.revisedChecker = revisedChecker;
    }

    /**
     * Singleton (Lazy Initialization because constructor parameters)
     * @param N_ROWS number of rows in the board
     * @param N_COLS number of columns in the board
     * @param revisedChecker game mode chosen by user
     * @return Singleton Instance
     * @see #getInstance()
     */
    public static synchronized CheckersTable getInstance(final int N_ROWS, final int N_COLS, boolean revisedChecker) {
        if (Instance == null)
            Instance = new CheckersTable(N_ROWS, N_COLS, revisedChecker);
        return Instance;
    }
    
    /**
     * Singleton method overload: get the existing instance without specifying unnecessary parameters
     * @return Singleton Instance
     * @throws Exception if the instance does not exist it must be created using <code>getInstance(final int, final int, boolean)</code>
     */
    public static synchronized CheckersTable getInstance() throws Exception {
        if (Instance == null)
            throw new Exception("ISTANCE NULL. Please use method: public static synchronized CheckersTable getInstance(<see args into code>)");
        return Instance;
    }

    private void initializeWindow() throws Exception {
        Dimension sizeFrame = new Dimension(N_ROWS *Box.DIM_BOX, N_COLS * Box.DIM_BOX);
        frameTable = addFrame("Checkers Table", sizeFrame.width, sizeFrame.height, Color.black, false, new BorderLayout(0,0), CheckersStart.getInstance().geticonPath(), true, CheckersStart.getInstance().centerTableY, JFrame.DO_NOTHING_ON_CLOSE);
        panelTable = addPanel(sizeFrame.width, sizeFrame.height, Color.black, new GridLayout(N_ROWS, N_COLS, 0, 0));
        panelInfo = new PanelInfo(200, sizeFrame.height, Color.getHSBColor(0, 0, 15), new FlowLayout(FlowLayout.CENTER, sizeFrame.width/10, sizeFrame.height/10 - 30), p1, p2, timer_value);
    
        Boxes = Box.createBoxes(N_ROWS, N_COLS, Box.DIM_BOX, p1, p2); //create new Boxes (all game table)
        addBoxesToPanel();

        frameTable.add(panelTable);
        frameTable.add(panelInfo.getpanelInfo(),BorderLayout.LINE_END);
        frameTable.setVisible(true);
        frameTable.pack();
        memento = new CheckersMemento();
    }

    private void addBoxesToPanel(){
        for (Box[] rowBoxes : Boxes)
            for (Box box : rowBoxes)
                panelTable.add(box);
    }

    public void startGame(Player p1, Player p2, int timer_value) throws Exception {
        this.p1 = p1;
        this.p2 = p2;
        this.timer_value = timer_value;
        initializeWindow();
        ArrayList<Observer> p = new ArrayList<Observer>();
        p.add(p1);
        p.add(p2);
        p.add(panelInfo);
        timer = new TimerObservable(p,2, this.timer_value);
    }

    //Shows the moves allowed to click on a piece
    protected void suggestions(Piece pClicked) {
        pToMove = pClicked; 
        pToMove.showSuggestions(pToMove.setRowbyColor());
    }

    //Move pToMove into destBox
    public void move(Box dstBox) throws Exception {
        boolean wizardEated = false; 

        Box srcBox = Boxes[pToMove.getCoord().x][pToMove.getCoord().y]; //this Box contain the piece to be moved
        int diff = srcBox.getCoord().x - dstBox.getCoord().x;

        if (diff > 1 || diff < -1){  //If have to eat difference is > 1 or < -1
            int i = (diff > 1) ? pToMove.getCoord().x - 1 : pToMove.getCoord().x + 1;
            int j = (srcBox.getCoord().y > dstBox.getCoord().y) ? pToMove.getCoord().y - 1 : pToMove.getCoord().y + 1;
            Box pToEatBox = Boxes[i][j];
            Piece enemyPiece = Boxes[i][j].getPiece();
            enemyPiece.getOwner().decreaseNpieces();
            checkGameEnd(enemyPiece);
            String enemyPieceClass = enemyPiece.getClass().getSimpleName();
            String pToMoveClass = pToMove.getClass().getSimpleName();

            addOrRemove(pToEatBox, false, pToMove);
            
            if (enemyPieceClass.equals("Wizard") && pToMoveClass.equals("Pawn")) //if pawn eat Wizard
                wizardEated = true; //Pawn will be a new checkers
            
            if ((enemyPieceClass.equals("Pawn") || enemyPieceClass.equals("Checkers")) && pToMoveClass.equals("Wizard")) //if Wizard eat
                respawn(pToEatBox); //add a new piece
            
            Player player = pToMove.getOwner();
            player.addPlayerPoints(enemyPiece.getPoints()); // increase player's score after eating
            panelInfo.updateScore(player);
        }

        pToMove.setCoord(dstBox.getCoord().x, dstBox.getCoord().y);
        Point pieceCoord = pToMove.getCoord();
      
        addOrRemove(srcBox, false, pToMove);  //remove the old piece from the previous Box

        if (canPieceUpgrade() || wizardEated){ //If pawn can upgrade
            Factory factory = new ConcreteFactoryM();
            pToMove = (Piece) factory.factoryMethod("checkers", pToMove.getColor(), pToMove.getOwner());
            pToMove.setCoord(pieceCoord.x, pieceCoord.y);
        }
        addOrRemove(dstBox, true, pToMove);
    }
    //In Box will respawn a piece
    private void respawn(Box box) throws Exception{
        Factory factory = new ConcreteFactoryM();
        Piece piece = (Piece) factory.factoryMethod("pawn", pToMove.getColor(), pToMove.getOwner());
        pToMove.getOwner().increaseNpieces();
        addOrRemove(box, true, piece);
    }

    public void showFreeBox(int row, int col){
        Color c = (pToMove.getColor().equals(Color.red)) ? Color.red : Color.green;
            Boxes[row][col].setColor(c);
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

    //true: adds a new piece - false: remove a piece
    private void addOrRemove(Box box, boolean action, Piece piece){
        if(action == true){
            if (!piece.equals(pToMove)) {
                piece.setCoord(box.getCoord().x, box.getCoord().y);
            }
            box.add(piece, BorderLayout.CENTER);
        }
        else
            box.removeAll();
        box.HasPiece(action);
        box.revalidate();
        box.repaint();
    }

    private boolean canPieceUpgrade(){
        String StrPiece = pToMove.getClass().getSimpleName();
        boolean isRedOnEnemyFstLine = pToMove.getColor() == Color.red && pToMove.getCoord().x == 0;
        boolean isGreenOnEnemyFstLine = pToMove.getColor() == Color.green && pToMove.getCoord().x == N_ROWS - 1;

        return StrPiece.equals("Pawn") && (isGreenOnEnemyFstLine || isRedOnEnemyFstLine);
    }

    public boolean illegalMove(int k){
        return k >= N_ROWS || k < 0;
    }

    public void switchTurn(){
        activePlayer = (activePlayer.equals(Color.red)) ? Color.green : Color.red;
        Player p = (activePlayer.equals(Color.red)) ? p1 : p2;
        panelInfo.switchTurn(p);
    }

    private void checkGameEnd(Piece enemyPiece){
        if (enemyPiece.getOwner().getNpieces() == 0){
            JOptionPane.showMessageDialog(null, "Game over! " + enemyPiece.getOwner().getPlayerName() + " lost.");
        }
    }

    public Memento createMemento(){
        return new CheckersMemento();
    }

    public void timeElapsed(Player winner, Object obj){
        if (obj != null)
            JOptionPane.showMessageDialog(null, "Tempo scaduto! \n La partita termina in parità");
        else
            JOptionPane.showMessageDialog(null, "Tempo scaduto! \n Il vincitore è " + winner.getPlayerName());
        returnToStart();
    }

    public void returnToStart(){
        frameTable.dispose();
        Instance = null; // when another game is started, a new object must be instantiated to use the class constructor 
        Player.count_players = 0;
        CheckersStart CT = CheckersStart.getInstance();
        CT.getFrame().setVisible(true);
        
    }

    public void stopTimer(){
        timer.stop();
    }

    public void reStart(){
    //    memento.restoreState();
    //    panelTable.revalidate();
    //    panelTable.repaint();
        System.out.println("da implementare memento");    
    }

    //Memento class
    public class CheckersMemento implements Memento{
        private Player mem_p1, mem_p2;
        private JPanel mem_panelTable;
        private PanelInfo mem_panelInfo; 

        public CheckersMemento(){
            mem_p1 = p1;
            mem_p2 = p2;
            mem_panelInfo = panelInfo;
            mem_panelTable = panelTable;
        }
        public void restoreState(){
        p1 = mem_p1;
        p2 = mem_p2;
        panelInfo = mem_panelInfo;
        panelTable = mem_panelTable;
        }
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

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getActivePlayer(){
        return (activePlayer.equals(Color.red)) ? p1 : p2;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Piece getPToMove() {
        return this.pToMove;
    }

    public void setPToMove(Piece pToMove) {
        this.pToMove = pToMove;
    }
    public boolean getRevisedChecker(){
        return revisedChecker;
    }

    public ArrayList<Point> getPointsListToClear() {
        return this.pointsListToClear;
    }

    public void setPointsListToClear(ArrayList<Point> pointsListToClear) {
        this.pointsListToClear = pointsListToClear;
    }

}
