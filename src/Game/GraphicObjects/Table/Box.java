package Game.GraphicObjects.Table;
import javax.swing.*;

import Game.GameObjects.FactoryM.ConcreteFactoryM;
import Game.GameObjects.FactoryM.Factory;
import Game.GameObjects.Pieces.*;
import Game.GameObjects.Players.Player;
import Game.GameObjects.Players.NPlayerExceededException;

import java.awt.*;
/**
 * <h1>Box class</h1>
 * Represents a single cell on the playing field.
 */
public class Box extends JPanel{
    public static int DIM_BOX = 96; //Default value. It can be changed by Main Class.
    private final int x,y;
    private Color color;
    private boolean hasPiece = false;
    private Point Coord = new Point();
    
    /**
     * Constructor
     * @param x x coordinate of the position on the playing field.
     * @param y y coordinate of the position on the playing field.
     * @param dim size of box.
     */
    public Box(int x, int y, int dim){
        this.x = x;
        this.y = y;
        DIM_BOX = dim;
    }


    /**
     * This function create and Set an array of game cells (Box type) and return it to the CheckersTable.
     * @param N_ROWS number of rows in the field.
     * @param N_COLS number of columns in the field.
     * @param DIM_BOX box size.
     * @param p1 first player.
     * @param p2 second player.
     * @return an array of boxes initialized.
     * @throws IstanceException Exception
    * @throws NPlayerExceededException Exception
     */
    public static Box[][] createBoxes(int N_ROWS, int N_COLS, int DIM_BOX, Player p1, Player p2) throws IstanceException, NPlayerExceededException {
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
    
    /**
     * Analyze each box and add a piece if necessary.
     * @param Boxes array of boxes initialized.
     * @param N_ROWS number of rows in the field.
     * @param N_COLS number of columns in the field.
     * @param p1 first player.
     * @param p2 second player.
     * @throws IstanceException Exception
     * @throws NPlayerExceededException Exception
     */
    public static void addPieces(Box[][] Boxes , int N_ROWS, int N_COLS, Player p1, Player p2) throws NPlayerExceededException, IstanceException  {
        final int MID_TABLE = N_ROWS/2;
        for (int i = 0; i < MID_TABLE - 1; i++) //Add Pieces Green (Pl2)
            for (int j = (i % 2 == 0) ? 1 : 0; j < N_COLS; j += 2)
                CreatePiece(Boxes, p2, Color.green, i, j,N_ROWS, N_COLS);

        for (int i = MID_TABLE + 1; i < N_ROWS; i++) //Add Pieces Red (Pl1)
            for (int j = (i % 2 == 0) ? 1 : 0; j < N_COLS; j += 2)
                CreatePiece(Boxes, p1, Color.red, i, j,N_ROWS, N_COLS); 
    }
    
    /**
     * Adds a piece to the box passed in input.
     * @param Boxes box in which to insert the piece.
     * @param pl owner of the piece.
     * @param col color of the piece.
     * @param i x coordinate of the input box.
     * @param j y coordinate of the input box.
     * @param N_ROWS number of rows in the field.
     * @param N_COLS number of columns in the field.
     * @throws IstanceException Exception
     * @throws NPlayerExceededException Exception
     */
    public static void CreatePiece(Box[][] Boxes, Player pl, Color col, int i, int j, int N_ROWS, int N_COLS) throws IstanceException, NPlayerExceededException{
        Boolean revisedChecker = CheckersTable.getInstance().getRevisedChecker();
        Factory factory = new ConcreteFactoryM();
        String typePiece = (revisedChecker && Wizard.is_WizardStartPosition(i, j, N_ROWS, N_COLS)) ? "wizard": "pawn";
        Piece piece = (Piece) factory.factoryMethod(typePiece, col, pl);
        addPiece(Boxes, i, j, piece);
        pl.increaseNpieces();
    }
    
    /**
     * Adds a piece to the boox.
     * @param Boxes box that houses the piece.
     * @param i x coordinate of the input box.
     * @param j y coordinate of the input box.
     * @param piece piece to add.
     */
    public static void addPiece(Box[][] Boxes, int i, int j, Piece piece){
        Boxes[i][j].add(piece, BorderLayout.CENTER);
        Boxes[i][j].hasPiece = true;
        piece.setCoord(i,j);
    }

    /**
     * Remove the piece from a box.
     * @param Boxes box from which to remove the piece.
     * @param i i-th position of the box within the structure that stores them.
     * @param j j-th position of the box within the structure that stores them.
     */
    public static void removePiece(Box[][] Boxes, int i, int j){
        if (Boxes[i][j].getComponentCount() > 0){
            Boxes[i][j].remove(0);
            Boxes[i][j].HasPiece(false);
        }
    }

    /**
     * Draw the figure
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x, y, DIM_BOX, DIM_BOX);
        g.setColor(color);
        g.fillRect(x, y, DIM_BOX, DIM_BOX);
    }
    

    // Getters and Setters methods..

    /**
     * Return true if the box has a piece, false otherwise
     * @return true if the box has a piece, false otherwise.
     */
    public boolean HasPiece(){
        return hasPiece;
    }

    //sets a value indicating whether the Box contains a piece 
    /**
     * Set true if the box has a piece, false otherwise.
     * @param hasPiece true if the box has a piece, false otherwise.
     */
    public void HasPiece(boolean hasPiece){
        this.hasPiece = hasPiece;
    }

    /**
     * Set the color of the box
     * @param color of the box
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Return the color of the box.
     * @return the color of the box.
     */
    public Color getColor(){
        return color;
    }

    /**
     * Sets the coordinates that represent the indices i, j of the structure containing the boxes.
     * @param i i-th position of the box within the structure that stores them.
     * @param j j-th position of the box within the structure that stores them.
     */
    public void setCoord(int i, int j){
        Coord.x = i;
        Coord.y = j;
    }

    /**
     * Return the coordinates of the box.
     * @return the coordinates of the box.
     */
    public Point getCoord(){
        return Coord;
    }

    //get the only component present in a Box, one piece
    /**
     * Return the component inside it.
     * To be checked if present.
     * @return the component inside it.
     */
    public Piece getPiece(){
        return (Piece)getComponent(0);
    }

}
