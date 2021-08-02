import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

//Singleton
public class Damiera{
    private final int n_rows, n_cols;
    private static Damiera Instance;
    final int  PIXEL = 96;
    private JFrame frame;
    private JPanel panel;

    private Damiera(int n, int c){
        this.n_cols = c;
        this.n_rows = n;
        inizializeWindow();
    }

    public static synchronized Damiera getInstance(int n, int c){
        if (Instance == null){
            Instance = new Damiera(n,c);
        }
        return Instance;
    }

    public void inizializeWindow(){
        this.frame = new JFrame("Dama");
        this.panel  = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(n_rows*PIXEL, n_cols*PIXEL);
        frame.setBackground(Color.white);
        frame.setResizable(false);

        panel.setLayout(new GridLayout(8,8,0,0) );
        panel.setPreferredSize( new Dimension(n_rows*PIXEL,n_cols*PIXEL) );
        panel.setBackground(Color.white);

        frame.add(panel, BorderLayout.CENTER);


        //Centered JFrame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        frame.setVisible(true);


        addRectangles();

    }

    public void addRectangles(){
        int k;
        for (int i = 0; i < n_rows; i++) {
            if (i%2 == 0) {
                k=1;
                //System.out.println("A");
            }
            else {
                k = 0;
              //  System.out.println("B");
            }
            for (int j=k; j < n_cols; j+=2) {
                Rectangle tmp = new Rectangle(i*PIXEL,j*PIXEL,PIXEL,PIXEL);
                System.out.print(k);
                tmp.setColor(Color.blue);
                panel.add(tmp);
            }
            System.out.println("c");
        }
    }

    public int get_nRows(){return this.n_rows;}

    public int get_nCols(){return this.n_cols;}



}

