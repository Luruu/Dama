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

    private List<Point> clearList = new ArrayList();

    /*-------------------------------------------------------------------------------------------------
            p1 = new Player(Color.green,playerName);
            p2 = new Player(Color.red, playerName2);
            inizializeWindow();
     ---------------------------------------------------------------------------------------------------*/
    //Da modificare, invece delle stringe nome player passiamo gli oggetti player instanziati nel main (con eventuali interfacce etc.)
    private CheckersTable(final int N_ROWS, final int N_COLS, final int dim) {
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        DIM_RECT = dim;
    }

    //Singleton
    public static synchronized CheckersTable getInstance(final int n, final int c, final int dim) {
        if (Instance == null)
            Instance = new CheckersTable(n, c, dim);
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
        for (Rectangle[] listRowRect : rectangles)
            for (Rectangle recCol : listRowRect)
                panel.add(recCol);

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
        Point coord = p.getCoord();
        pToMove = p;
        Point eatCoord;
        switch (p.getClass().toString()) {
            case "class Pawn":
                if (p.getColor() == Color.red) {
                    //Pedine rosse
                    if ((eatCoord = showSuggestion(coord.x - 1, coord.y)) != null)
                        showEatSuggestion(eatCoord.x, eatCoord.y);
                } else { //Pedine verdi
                    if ((eatCoord = showSuggestion(coord.x + 1, coord.y)) != null)
                        showEatSuggestion(eatCoord.x, eatCoord.y);
                }
            case "class Archer":
                if (p == null) {

                }
                break;

            default:
                System.out.println("Vediamo cosa fare per la DAMA");
        }
    }


    //Show (paint the rect) the suggestions on the game's table
    //Return Point when can eat the piece.
    private Point showSuggestion(int i, int j) {
        int x;
        System.out.println("i = " + i + " j = " + j);
        if (j - 1 >= 0 && i > -1 && i < N_ROWS) {
            if (!rectangles[i][j - 1].getHasPiece() && !rectangles[i][j + 1].getHasPiece()) {
                rectangles[i][j - 1].setColor(Color.cyan);
                rectangles[i][j - 1].repaint();
                clearList.add(new Point(i, j - 1));
            } else {
                //return the coords of the piece that you can eat
                if (rectangles[i][j - 1].getHasPiece())
                    x = j - 1;
                else
                    x = j + 1;
                return new Point(i, x);
            }

        }

        if (j + 1 < N_COLS && i > -1 && i < N_ROWS) {
            if (!rectangles[i][j + 1].getHasPiece()) {
                rectangles[i][j + 1].setColor(Color.cyan);
                rectangles[i][j + 1].repaint();
                clearList.add(new Point(i, j + 1));
            } else
                return new Point(i, j + 1);
        }
        //return null when you can't eat nobody
        return null;
    }

    public void clear() {
        for (Point p : clearList) {
            rectangles[p.x][p.y].setColor(Color.darkGray);
            rectangles[p.x][p.y].repaint();
        }
        clearList.clear();
    }

    //This function move pToMove into r
    public void move(Rectangle r, int i, int j) {
        //This variable contains the rectangle containing the piece to be moved
        Rectangle tmpRect = rectangles[pToMove.getCoord().x][pToMove.getCoord().y];
        pToMove.setCoord(i, j);
        //Add the piece to move in new rectagle
        r.add(tmpRect.getComponent(0));
        r.setHasPiece(true);
        r.repaint();
        //remove the old piece from the previous rectangle
        tmpRect.removeAll();
        tmpRect.setHasPiece(false);
        tmpRect.repaint();
    }

    private void showEatSuggestion(int i, int j) {
        //Move to the right diagonal
        if (pToMove.getCoord().y < j) {
            if (!rectangles[i - 1][j + 1].getHasPiece()) {
                rectangles[i - 1][j + 1].setColor(Color.cyan);
                rectangles[i - 1][j + 1].repaint();
                clearList.add(new Point(i - 1, j + 1));
                return;
            } else {
                //Can't eat on the diagonal
                if (!rectangles[pToMove.getCoord().x - 1][pToMove.getCoord().y - 1].getHasPiece()) {
                    rectangles[pToMove.getCoord().x - 1][pToMove.getCoord().y - 1].setColor(Color.cyan);
                    rectangles[pToMove.getCoord().x - 1][pToMove.getCoord().y - 1].repaint();
                    clearList.add(new Point(pToMove.getCoord().x - 1, pToMove.getCoord().y - 1));
                    return;
                }
            }
        } else {
            //Move to the left diagonal
            if (!rectangles[i - 1][j - 1].getHasPiece()) {
                rectangles[i - 1][j - 1].setColor(Color.cyan);
                rectangles[i - 1][j - 1].repaint();
                clearList.add(new Point(i - 1, j - 1));
                return;
            } else {
                if (!rectangles[pToMove.getCoord().x - 1][pToMove.getCoord().y + 1].getHasPiece()) {
                    rectangles[pToMove.getCoord().x - 1][pToMove.getCoord().y + 1].setColor(Color.cyan);
                    rectangles[pToMove.getCoord().x - 1][pToMove.getCoord().y + 1].repaint();
                    clearList.add(new Point(pToMove.getCoord().x - 1, pToMove.getCoord().y + 1));
                    return;
                }
            }
        }
    }

}


