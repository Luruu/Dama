import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;



public class Player extends MouseAdapter{
    private final Color color;
    private final String nome;
    private int points;
    private final InvokerList invoker;

    public Player(Color c, String n) throws Exception {
        super();
        color = c;
        nome = n;
        points = 0;
        invoker = new InvokerList(new ConcreteCommand(CheckersTable.getInstance()));
    }

    public void mouseClicked(MouseEvent e){
        Piece tmp = (Piece)e.getSource();
        Point p = tmp.getCoord();
        invoker.suggestion(p.x, p.y);
     }
}
