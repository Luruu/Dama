import javax.swing.*;
import javax.swing.border.Border;
//import javax.swing.border.Border; Attualmente inutile
import java.awt.*;

//Singleton
public class Checkers{
    private static Checkers Instance;

    private final int N_ROWS, N_COLS;
    final int DIM_RETTANGOLO = 96;

    private JFrame frame;
    private JPanel panel;

    private Piece piece;

    private Checkers (final int N_ROWS,final int N_COLS){
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        inizializeWindow();
    }

    public static synchronized Checkers getInstance(int n, int c){
        if (Instance == null){
            Instance = new Checkers(n,c);
        }
        return Instance;
    }

    private void createFrame(){
        frame = new JFrame("Dama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(N_ROWS*DIM_RETTANGOLO, N_COLS*DIM_RETTANGOLO);
        frame.setBackground(Color.white);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Point centro_schermo = new Point(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
        frame.setLocation(centro_schermo.x, centro_schermo.y);
    }

    private void createPanel(){
        panel = new JPanel();
        panel.setLayout(new GridLayout(N_ROWS,N_COLS,0,0));
        panel.setPreferredSize(new Dimension(N_ROWS*DIM_RETTANGOLO, N_COLS*DIM_RETTANGOLO));
        panel.setBackground(Color.gray);
    }

    private void inizializeWindow(){
        createFrame();
        createPanel();
        addPieces();


        frame.add(panel);
        frame.setVisible(true);
    }

    private void addPieces(){
        Color colore;
        Pawn pedina = new Pawn(Color.white);
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j ++) {
                if(i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0)
                    colore = setColor(i,j);
                else
                    colore = Color.white;
                //0 0 because it is the grid that arranges the rectangles
                Rectangle rettangolo = new Rectangle(0, 0, DIM_RETTANGOLO, DIM_RETTANGOLO);
                rettangolo.setColor(colore);

                //Add Pieces

                rettangolo.add(pedina, BorderLayout.CENTER);


                panel.add(rettangolo);
            }
        }
    }


    private Color setColor(int i, int j){
        Color c;
        if(i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0)
            c = Color.darkGray;
        else
            c = Color.white;
        return c;
    }

    public int get_nRows(){return N_ROWS;}

    public int get_nCols(){return N_COLS;}
}

