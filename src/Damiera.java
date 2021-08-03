import javax.swing.*;
//import javax.swing.border.Border; Attualmente inutile
import java.awt.*;

//Singleton
public class Damiera{
    private static Damiera Instance;

    private final int N_ROWS, N_COLS;
    final int DIM_RETTANGOLO = 96;

    private JFrame frame;
    private JPanel panel;

    private Damiera (final int N_ROWS,final int N_COLS) throws InterruptedException{
        this.N_ROWS = N_ROWS;
        this.N_COLS = N_COLS;
        inizializeWindow();
    }

    public static synchronized Damiera getInstance(int n, int c) throws InterruptedException{
        if (Instance == null){
            Instance = new Damiera(n,c);
        }
        return Instance;
    }

    public void inizializeWindow() throws InterruptedException{
        createFrame("Dama");this.
        createPanel();
        this.
        frame.add(panel, BorderLayout.CENTER);


        //Centered JFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Point centro_schermo = new Point(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
        frame.setLocation(centro_schermo.x, centro_schermo.y);

        frame.setVisible(true);
        
        addRectangles();
    }

    public void addRectangles() throws InterruptedException{
        int k;
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

