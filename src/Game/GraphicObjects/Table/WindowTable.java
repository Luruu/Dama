package Game.GraphicObjects.Table;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import Game.GameObjects.Players.Player;
import Game.GraphicObjects.GraphicWindow;
import Game.GraphicObjects.Start.CheckersStart;
import java.awt.*;
import javax.swing.*;
import java.lang.Exception;
import java.awt.event.WindowAdapter;

public abstract class WindowTable extends GraphicWindow {
    
// Memento class--------------------------------------------------------------------------
        public class CheckersMemento implements Memento{
            private Player mem_p1, mem_p2;
            private Box[][] mem_boxes;
            //private JComponent mem_components;
    
            public CheckersMemento() throws CloneNotSupportedException{
                mem_p1 = (Player)p1.clone();
                mem_p2 = (Player)p2.clone();
                mem_boxes = Boxes.clone();
                    /*
                for (JComponent component : panelInfo.getListComponent()) {
                    mem_components = component.clon;
                } */
            }
            public void restoreState() throws Exception{
                p1 =(Player) mem_p1.clone();
                p2 = (Player)mem_p2.clone();
                Boxes = mem_boxes.clone();
                Box.addPieces(Boxes, N_ROWS, N_COLS, p1, p2);
                reStartPanelInfo();
            }
        }
//--------------------------------------------------------------------------
    protected int N_ROWS, N_COLS;
    protected PanelInfo panelInfo;
    protected Box[][] Boxes; // Matrix of all the Boxes of the board
    protected Player p1, p2;
    protected Memento memento;
    protected int timer_value;

    public WindowTable(int N_ROWS, int N_COLS){
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
    }

    public abstract void reStartPanelInfo();

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

    public Memento createMemento() throws CloneNotSupportedException{
        return new CheckersMemento();
    }

    private void addBoxesToPanel(){
        for (Box[] rowBoxes : Boxes)
            for (Box box : rowBoxes)
                panel.add(box);
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

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

}
