package Game.GraphicObjects.Table;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import Game.GameObjects.Players.Player;
import Game.GraphicObjects.GraphicWindow;
import Game.GraphicObjects.Start.CheckersStart;
import Game.Observer;
import Game.TimerObservable;
import java.awt.*;
import javax.swing.*;
import java.lang.Exception;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

/**
 * <h1>WindowTable class</h1> 
 * This class abstracts the concept of the game window.
 */
public abstract class WindowTable extends GraphicWindow {
    
        /**
         * This nested class implements the memento pattern.
         */
        public class CheckersMemento implements Memento{
            private Player mem_p1, mem_p2;
            private Box[][] mem_boxes;

            /**
             * Constructor.
             * @throws CloneNotSupportedException CloneNotSupportedException.
             */
            public CheckersMemento() throws CloneNotSupportedException{
                mem_p1 = (Player)p1.clone();
                mem_p2 = (Player)p2.clone();
                mem_boxes = Boxes.clone();
            }
            /**
             * This method allows you to return the game to its initial state.
             */
            public void restoreState() throws Exception{
                p1 =(Player) mem_p1.clone();
                p2 = (Player)mem_p2.clone();
                Boxes = mem_boxes.clone();
                Box.addPieces(Boxes, N_ROWS, N_COLS, p1, p2);
                clearSuggestions();
                reStartPanelInfo();
            }
        }


    protected int N_ROWS, N_COLS;
    protected PanelInfo panelInfo;
    protected Box[][] Boxes; // Matrix of all the Boxes of the board
    protected Player p1, p2;
    protected Memento memento;
    protected int timer_value;

    protected ArrayList<Point> pointsListToClear = new ArrayList<>();
    
    protected ArrayList<Observer> observerList = new ArrayList<>();

    protected TimerObservable timer;

    /**
     * Constructor.
     * @param N_ROWS number of rows of the playing field.
     * @param N_COLS number of columns of the playing field.
     */
    public WindowTable(int N_ROWS, int N_COLS){
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
    }

    /**
     * This abstract method allows to define how to clean the suggestions of the movements.
     */
    public abstract void clearSuggestions();

    /**
     * This abstract method allows to define the settings to be made for a new game.
     */
    public abstract void reStartPanelInfo();

    /**
     * This function allows you to initialize the game table.
     * @throws Exception exception.
     */
    protected void initializeWindow() throws Exception {
        Dimension sizeFrame = new Dimension(N_ROWS *Box.DIM_BOX, N_COLS * Box.DIM_BOX);
        frame = addFrame("Checkers Table", sizeFrame.width, sizeFrame.height, Color.black, false, new BorderLayout(0,0), ICON_PATH, true, CheckersStart.getInstance().centerTableY, JFrame.DO_NOTHING_ON_CLOSE);
        panel = addPanel(sizeFrame.width, sizeFrame.height, Color.black, new GridLayout(N_ROWS, N_COLS, 0, 0));
        panelInfo = new PanelInfo(200, sizeFrame.height, Color.getHSBColor(0, 0, 15), new FlowLayout(FlowLayout.CENTER, sizeFrame.width/10, sizeFrame.height/10 - 30), p1, p2, timer_value);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, 
                    "Are you sure you want to close Checkersgame?", "CheckersGame", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });


        Boxes = Box.createBoxes(N_ROWS, N_COLS, Box.DIM_BOX, p1, p2); //create new Boxes (all game table)
        addBoxesToPanel();

        frame.add(panel);
        frame.add(panelInfo.getpanelInfo(), BorderLayout.LINE_END);
        frame.setVisible(true);
        frame.pack();
        memento = createMemento();
    }

    /**
     * Return the game to an initial state.
     * @throws Exception exception.
     */
    public void reStartGame() throws Exception{

        for (int i = 0; i < Boxes.length; i++)
            for (int j = 0; j < Boxes.length; j++)
                Box.removePiece(Boxes, i, j);

        
        memento.restoreState();

        panel.revalidate();
        panel.repaint();

        JPanel p = panelInfo.getpanelInfo();
        p.revalidate();
        p.repaint();
    }

    /**
     * Return memento element.
     * @return memento element.
     * @throws CloneNotSupportedException cloneNotSupportedException.
     */
    public Memento createMemento() throws CloneNotSupportedException{
        return new CheckersMemento();
    }

    /**
     * Adds all boxes to the panel.
     */
    private void addBoxesToPanel(){
        for (Box[] rowBoxes : Boxes)
            for (Box box : rowBoxes)
                panel.add(box);
    }

    // Getters and Setters methods..

    /**
     * Return a box from the list.
     * @param row line index of the box to be returned.
     * @param col Column index of the box to be returned.
     * @return a box from the list.
     */
    public Box getBoxfromList(int row, int col){
        return Boxes[row][col];
    }

    /**
     * Return number of lines in the field.
     * @return number of lines in the field.
     */
    public final int getN_ROWS(){
        return N_ROWS;
    }

    /**
     * Return number of columns in the field.
     * @return number of columns in the field.
     */
    public final int getN_COLS(){
        return N_COLS;
    }

    /**
     * Return the first player.
     * @return the first player.
     */
    public Player getP1() {
        return p1;
    }

    /**
     * Set the first player.
     * @param p1 first player.
     */
    public void setP1(Player p1) {
        this.p1 = p1;
    }

    /**
     * Return the second player.
     * @return the second player.
     */
    public Player getP2() {
        return p2;
    }

    /**
     * Set the second player.
     * @param p1 second player.
     */
    public void setP2(Player p2) {
        this.p2 = p2;
    }

}
