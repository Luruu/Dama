package Game.GraphicObjects.Table;


import Game.GameObjects.Players.Player;
import Game.GraphicObjects.GraphicWindow;
import Game.GraphicObjects.Start.CheckersStart;

import java.awt.*;
import javax.swing.*;
import java.lang.Exception;

public abstract class WindowTable extends GraphicWindow {
    public void reStart(){
        //    memento.restoreState();
        //    panelTable.revalidate();
        //    panelTable.repaint();
            System.out.println("da implementare memento");    
        }
    
// Memento class--------------------------------------------------------------------------
        public class CheckersMemento implements Memento{
            private Player mem_p1, mem_p2;
            private JPanel mem_panelTable;
            private PanelInfo mem_panelInfo; 
    
            public CheckersMemento(){
                mem_p1 = p1;
                mem_p2 = p2;
                mem_panelInfo = panelInfo;
                mem_panelTable = panel;
            }
            public void restoreState(){
            p1 = mem_p1;
            p2 = mem_p2;
            panelInfo = mem_panelInfo;
            panel = mem_panelTable;
            }
        }
//--------------------------------------------------------------------------
    protected int N_ROWS, N_COLS;
    protected PanelInfo panelInfo;
    protected Box[][] Boxes; // Matrix of all the Boxes of the board
    protected Player p1, p2;
    protected CheckersMemento memento;
    protected int timer_value;

    public WindowTable(int N_ROWS, int N_COLS){
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
    }


    protected void initializeWindow() throws Exception {
        Dimension sizeFrame = new Dimension(N_ROWS *Box.DIM_BOX, N_COLS * Box.DIM_BOX);
        frame = addFrame("Checkers Table", sizeFrame.width, sizeFrame.height, Color.black, false, new BorderLayout(0,0), ICON_PATH, true, CheckersStart.getInstance().centerTableY, JFrame.DO_NOTHING_ON_CLOSE);
        panel = addPanel(sizeFrame.width, sizeFrame.height, Color.black, new GridLayout(N_ROWS, N_COLS, 0, 0));
        panelInfo = new PanelInfo(200, sizeFrame.height, Color.getHSBColor(0, 0, 15), new FlowLayout(FlowLayout.CENTER, sizeFrame.width/10, sizeFrame.height/10 - 30), p1, p2, timer_value);
    
        Boxes = Box.createBoxes(N_ROWS, N_COLS, Box.DIM_BOX, p1, p2); //create new Boxes (all game table)
        addBoxesToPanel();

        frame.add(panel);
        frame.add(panelInfo.getpanelInfo(),BorderLayout.LINE_END);
        frame.setVisible(true);
        frame.pack();
        memento = new CheckersMemento();
    }

    private void addBoxesToPanel(){
        for (Box[] rowBoxes : Boxes)
            for (Box box : rowBoxes)
                panel.add(box);
    }

    public Memento createMemento(){
        return new CheckersMemento();
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
