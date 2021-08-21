import javax.swing.*;

import java.awt.*;
public  class Rectangle extends JPanel{
    public static int DIM_RECT = 96; //Default value. It can be changed by Main Class.
    private final int x,y;
    private Color color;
    private boolean hasPiece = false;
    private Point Coord = new Point();
    
    public Rectangle(int x, int y, int dim){
        this.x = x;
        this.y = y;
        DIM_RECT = dim;
    }

//This function create and Set an array of game cells (Rectangle type) and return it to the CheckersTable
    public static Rectangle[][] createRectangles(int N_ROWS, int N_COLS, int DIM_RECT, Player p1, Player p2) throws Exception {
        Rectangle[][] rectangles = new Rectangle[N_ROWS][N_COLS];
        Color color_rect;
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLS; j++){
                color_rect = ColorFunctions.getColor_PlayableOrNot(i, j);
                rectangles[i][j] = new Rectangle(0, 0, DIM_RECT);
                rectangles[i][j].setCoord(i,j);
                rectangles[i][j].color = color_rect;
                rectangles[i][j].addMouseListener(new Player()); //ADD Player that is also handler mouse event
            }
        addPieces(rectangles, N_ROWS, N_COLS, p1,p2);
        return rectangles;
    }

    private static void addPieces(Rectangle[][] rectangles , int N_ROWS, int N_COLS, Player p1, Player p2) throws Exception {
        //Used to create game's pieces
        Creator factory = new ConcreteFactoryM();
        Color pieceColor;
        String typePiece;
        Piece piece;
        int mid_table = N_COLS/2;
        for (int i = 0; i < N_ROWS; i++){
            for (int j = 0; j < N_COLS; j++){
                //Add Pieces in the correct position
                if ( (i < mid_table - 1 || i > mid_table) && rectangles[i][j].color == Color.darkGray){
                    typePiece = ((i == 0 && j == N_ROWS - 1) || (i == N_COLS - 1 && j == 0)) ? "archer": "pawn";
                    pieceColor = (i < mid_table - 1) ? Color.green : Color.red;
                    Player owner = (pieceColor == Color.red) ? p1 : p2;
                    piece = (Piece) factory.factoryMethod(typePiece, pieceColor, owner);
                    //Assignment of the player based on the color of the pawn
                    //Forse non serve. Basterebbe passargli un new Player a caso.
                    //Il controllo va fatto in base ai colori dei pezzi e del player
                    //Probabilmente sarà una features o un bug o non serve a niente.
                    //Questo if serve perché un player potrebbe cliccaere su un pezzo di colore diverso dal suo
                    //Quindi otterremo il mouselistener del pezzo selezionato e lo confrontiamo con il colore del pezzo
                    // Tutto questo nella funzione player che invoca il click.
                    rectangles[i][j].add(piece, BorderLayout.CENTER);
                    rectangles[i][j].hasPiece = true;
                    piece.setCoord(i,j);
                }
            }
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x, y, DIM_RECT, DIM_RECT);
        g.setColor(color);
        g.fillRect(x, y, DIM_RECT, DIM_RECT);
    }
    

    // Getters and Setters methods..

    //gets a value indicating whether the rectangle contains a piece 
    public boolean HasPiece(){
        return hasPiece;
    }

    //sets a value indicating whether the rectangle contains a piece 
    public void HasPiece(boolean x){
        hasPiece = x;
    }

    public void setColor(Color c){
        color = c;
    }

    public Color getColor(){
        return color;
    }

    private void setCoord(int i, int j){
        Coord.x = i;
        Coord.y = j;
    }

    public Point getCoord(){
        return Coord;
    }

    //get the only component present in a rectangle, one piece
    public Piece getPiece(){
        return (Piece)getComponent(0);
    }

}
