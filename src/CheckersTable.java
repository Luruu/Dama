import javax.swing.*;

//import javax.swing.border.Border; Attualmente inutile
import java.awt.*;

//Singleton
//Command Receiver
public class CheckersTable {
    private static CheckersTable Instance;

    private final int N_ROWS, N_COLS, DIM_RECT;
    private Player p1,p2;

    private JFrame frame;
    private JPanel panel;

    private static Rectangle[][] rectangles;

    private CheckersTable(final int N_ROWS, final int N_COLS, final int dim, final String playerName, final String playerName2){
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        DIM_RECT = dim;
        //rectangles = new Rectangle[N_ROWS][N_COLS];
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
        
        //Create and Set an array of game cells (Rectangle type) with and without pieces
        rectangles =  Rectangle.createRectangles(N_ROWS,N_COLS, DIM_RECT, p1,p2);
        //Add rect to Table
        for (int i = 0; i< N_ROWS; i++)
            for (int j = 0; j < N_COLS; j++){
                panel.add(rectangles[i][j]);
            }

        frame.add(panel);
        frame.setVisible(true);
    }


}