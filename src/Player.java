import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;



public class Player extends MouseAdapter{
    private final Color color;
    private final String nome;
    private int points;


    public Player(Color c, String n){
        super();
        color = c;
        nome = n;
        points = 0;
    }

    public void mouseClicked(MouseEvent e){
        System.out.println(e.getX() + " " + e.getY());
        //Come otteniamo I E J della componente cliccata?
        //Dobbiamo passare a invoker i e j
        System.out.println(e.getComponent().getX());
        
     }
}
