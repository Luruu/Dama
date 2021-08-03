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
        frame = new JFrame("Dama");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(N_ROWS*DIM_RETTANGOLO, N_COLS*DIM_RETTANGOLO);
        frame.setBackground(Color.white);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(N_ROWS, N_COLS, 0, 0));
        panel.setPreferredSize(new Dimension(N_ROWS*DIM_RETTANGOLO, N_COLS*DIM_RETTANGOLO));
        panel.setBackground(Color.white);

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
            k = (i%2 == 0) ?  1 : 0;
            for (int j = DIM_RETTANGOLO * k; j < N_COLS * DIM_RETTANGOLO; j += DIM_RETTANGOLO) {
                Rectangle rettangolo = new Rectangle(j, i * DIM_RETTANGOLO, DIM_RETTANGOLO, DIM_RETTANGOLO);
                rettangolo.setColor(Color.blue);
                panel.add(rettangolo);
                System.out.println("Rettangolo aggiunto alla posizione: " + j + " " + i * DIM_RETTANGOLO);
                Thread.sleep(400); //Utilizzato per vedere come disegna. Spoiler: non lo fa.
            }
        }
    }

    public int get_nRows(){return N_ROWS;}
    public int get_nCols(){return N_COLS;}
}

