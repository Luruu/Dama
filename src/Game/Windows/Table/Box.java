package Game.Windows.Table;
import javax.swing.*;

import Game.FactoryM.ConcreteFactoryM;
import Game.FactoryM.Factory;
import Game.FactoryM.Pieces.*;
import Game.FactoryM.Players.Player;

import java.awt.*;

public class Box extends JPanel{
    public static int DIM_BOX = 96; //Default value. It can be changed by Main Class.
    private final int x,y;
    private Color color;
    private boolean hasPiece = false;
    private Point Coord = new Point();
    
    public Box(int x, int y, int dim){
        this.x = x;
        this.y = y;
        DIM_BOX = dim;
    }

//This function create and Set an array of game cells (Box type) and return it to the CheckersTable
    public static Box[][] createBoxes(int N_ROWS, int N_COLS, int DIM_BOX, Player p1, Player p2) throws Exception {
        Box[][] Boxes = new Box[N_ROWS][N_COLS];
        Boolean BoxPlayable;
        Color color_BOX;
        for (int i = 0; i < N_ROWS; i++){
            for (int j = 0; j < N_COLS; j++){
                BoxPlayable = (i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0);
                color_BOX = (BoxPlayable) ? Color.darkGray : Color.white;
                Boxes[i][j] = new Box(0, 0, DIM_BOX);
                Boxes[i][j].setCoord(i,j);
                Boxes[i][j].color = color_BOX;
                Boxes[i][j].addMouseListener(new Player()); //ADD Player that is also handler mouse event
            }
        }
        addPieces(Boxes, N_ROWS, N_COLS, p1,p2);
        return Boxes;
    }

    private static void addPieces(Box[][] Boxes , int N_ROWS, int N_COLS, Player p1, Player p2) throws Exception {
        Factory factory = new ConcreteFactoryM();
        Color pieceColor;
        String typePiece;
        Boolean revisedChecker = CheckersTable.getInstance().getRevisedChecker();
        Piece piece;
        Player owner;
        final int MID_TABLE = N_COLS/2;
        for (int i = 0; i < N_ROWS; i++){
            for (int j = 0; j < N_COLS; j++){
                //Add Pieces in the correct position
                if ((i < MID_TABLE - 1 || i > MID_TABLE) && Boxes[i][j].color == Color.darkGray){
                    typePiece = (revisedChecker && Wizard.is_WizardStartPosition(i, j, N_ROWS, N_COLS)) ? "wizard": "pawn";
                    pieceColor = (i < N_ROWS/2 - 1) ? Color.green : Color.red;
                    owner = (pieceColor == Color.red) ? p1 : p2;
                    owner.increaseNpieces();
                    piece = (Piece) factory.factoryMethod(typePiece, pieceColor, owner);
                    Boxes[i][j].add(piece, BorderLayout.CENTER);
                    Boxes[i][j].hasPiece = true;
                    piece.setCoord(i,j);
                }
            }
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x, y, DIM_BOX, DIM_BOX);
        g.setColor(color);
        g.fillRect(x, y, DIM_BOX, DIM_BOX);
    }
    

    // Getters and Setters methods..

    //gets a value indicating whether the Box contains a piece 
    public boolean HasPiece(){
        return hasPiece;
    }

    //sets a value indicating whether the Box contains a piece 
    public void HasPiece(boolean x){
        hasPiece = x;
    }

    public void setColor(Color c){
        color = c;
    }

    public Color getColor(){
        return color;
    }

    public void setCoord(int i, int j){
        Coord.x = i;
        Coord.y = j;
    }

    public Point getCoord(){
        return Coord;
    }

    //get the only component present in a Box, one piece
    public Piece getPiece(){
        return (Piece)getComponent(0);
    }

}
