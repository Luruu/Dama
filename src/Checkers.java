import javax.swing.*;
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

    private void inizializeWindow(){
        createFrame();
        createPanel();
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        //addRectangles();
        addPieces();
    }

    private void addRectangles(){
        Color colore;
        for (int i = 0; i < N_ROWS; i++) {
            for (int j = 0; j < N_COLS; j ++) {
                if(i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0)
                    colore = setColor(i,j);
                else
                    colore = Color.white;

                Rectangle rettangolo = new Rectangle(0, 0, DIM_RETTANGOLO, DIM_RETTANGOLO);
                rettangolo.setColor(colore);
                panel.add(rettangolo);
            }
        }
    }

    private void addPieces(){
        piece = new Pawn(Color.blue);
        panel.add(piece.getImg());
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

