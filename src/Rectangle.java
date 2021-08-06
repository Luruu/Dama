import javax.swing.*;

import java.awt.*;

public  class Rectangle extends JPanel {
    private final int x,y,wid,hei;
    private Color color;

    public Rectangle(int x, int y, int wid, int hei){
        this.x = x;
        this.y = y;
        this.wid = wid;
        this.hei = hei;
    }

//This function create and Set an array of game cells (Rectangle type) and return it to the CheckersTable
    public static Rectangle[][] createRectangles(int N_ROWS, int N_COLS, int DIM_RECT, Player p1, Player p2){
        Rectangle[][] rectangles = new Rectangle[N_ROWS][N_COLS];
        Color color_rect;
            for (int i = 0; i < N_ROWS; i++)
                for (int j = 0; j < N_COLS; j++){
                    //Color the rectangles according to their position
                    if (i % 2 == 0 && j % 2 != 0 || j % 2 == 0 && i % 2 != 0)
                        color_rect = myColor.getColor(i,j);
                    else
                        color_rect = Color.white;
                    rectangles[i][j] = new Rectangle(0, 0, DIM_RECT, DIM_RECT);
                    rectangles[i][j].color = color_rect;
                }
        
        addPieces(rectangles, N_ROWS, N_COLS, p1,p2);
        return rectangles;
    }

    
    private static void addPieces(Rectangle[][] rectangles , int N_ROWS, int N_COLS, Player p1, Player p2){
        //Used to create game's pieces
        ConcreteFactoryM factory = new ConcreteFactoryM();
        Color pieceColor;
        String typePiece;
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLS; j++){
                //Add Pieces in the correct position
                if ( (i < 3 || i > 4 ) && rectangles[i][j].color == Color.darkGray){
                    pieceColor = (i<3) ? Color.green : Color.red;
                    typePiece = ((i == 0 && j == 7) || (i == 7 && j == 0 )) ? "archer": "pawn";
                    Piece piece = factory.factoryMethod(typePiece, pieceColor);

                    //Assignment of the player based on the color of the pawn
                    piece.addMouseListener((pieceColor == Color.red) ? p1 : p2);
                    rectangles[i][j].add(piece, BorderLayout.CENTER);
                }
                
            }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x,y,wid,hei);
        g.setColor(color);
        g.fillRect(x,y,wid,hei);
    }

}
