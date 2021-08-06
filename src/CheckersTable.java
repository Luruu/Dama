import javax.swing.*;

//import javax.swing.border.Border; Attualmente inutile
import java.awt.*;

//Singleton
//Command Receiver
public class CheckersTable {
    private static CheckersTable Instance;

    private final int N_ROWS, N_COLS, DIM_RECT;
    private Player p1,p2;

    //Used to create game's pieces
    private final ConcreteFactoryM factory = new ConcreteFactoryM();
    private JFrame frame;
    private JPanel panel;

    private final Rectangle[][] rectangles;

    private CheckersTable(final int N_ROWS, final int N_COLS, final int dim, final String playerName, final String playerName2){
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        DIM_RECT = dim;
        rectangles = new Rectangle[N_ROWS][N_COLS];
        p1 = new Player(Color.green,playerName);
        p2 = new Player(Color.red, playerName2);
        inizializeWindow();
    }

    //Singleton
    public static synchronized CheckersTable getInstance(final int n, final int c, final int dim, final String playerName1, final String playerName2){
        if (Instance == null){
            Instance = new CheckersTable(n,c,dim, playerName1, playerName2);
        }
        return Instance;
    }

    //Create and set the main Frame
    private void createFrame(){
        frame = new JFrame("Dama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(N_ROWS*DIM_RECT, N_COLS*DIM_RECT);
        frame.setBackground(Color.white);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Point centro_schermo = new Point(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
        frame.setLocation(centro_schermo.x, centro_schermo.y);
    }

    //Create and set the Panel that  will contain all the elements of the game
    private void createPanel(){
        panel = new JPanel();
        panel.setLayout(new GridLayout(N_ROWS,N_COLS,0,0));
        panel.setPreferredSize(new Dimension(N_ROWS*DIM_RECT, N_COLS*DIM_RECT));
        panel.setBackground(Color.gray);
    }

    
    private void inizializeWindow(){
        createFrame();
        createPanel();
        createRectangles();

        addComponents(p1,p2);

        frame.add(panel);
        frame.setVisible(true);
    }


    private void createRectangles() {
        Color color_rect;
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLS; j++){
                //Color the rectangles according to their position
                if (i % 2 == 0 && j % 2 != 0 || j % 2 == 0 && i % 2 != 0)
                    color_rect = getColor(i,j);
                else
                    color_rect = Color.white;
                rectangles[i][j] = new Rectangle(0, 0, DIM_RECT, DIM_RECT);
                rectangles[i][j].setColor(color_rect);
            }
    }

    //Add pieces to rect and rect to Panel
    private void addComponents(Player p1, Player p2){
        Color pieceColor;
        String typePiece;
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLS; j++){
                //Add Pieces in the correct position
                if ( (i < 3 || i > 4 ) && rectangles[i][j].getColor() == Color.darkGray){
                    pieceColor = (i<3) ? Color.green : Color.red;
                    typePiece = ((i == 0 && j == 7) || (i == 7 && j == 0 )) ? "archer": "pawn";
                    Piece piece = factory.factoryMethod(typePiece, pieceColor);

                    //Assignment of the player based on the color of the pawn
                    piece.addMouseListener((pieceColor == Color.red) ? p1 : p2);
                    rectangles[i][j].add(piece, BorderLayout.CENTER);
                }
                panel.add(rectangles[i][j]);

            }
    }


    //Return the color according to position
    private Color getColor(int i, int j){
        return  (i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0) ? Color.darkGray : Color.white;
    }
}

