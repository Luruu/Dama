import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;


public class Player extends MouseAdapter {
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

    public Player() throws Exception {
        color = null;
        nome = null;
        invoker = new InvokerList(new ConcreteCommand(CheckersTable.getInstance()));
    }

    public void mouseClicked(MouseEvent e){
        if (e.getSource().getClass().toString().equals("class Pawn") || e.getSource().getClass().toString().equals("class Archer") ){
            invoker.clear();
            Piece tmp = (Piece)e.getSource();
            invoker.suggestion(tmp);
        }
        else if(e.getSource().getClass().toString().equals("class Rectangle")){;
            Rectangle tmp = (Rectangle) e.getSource();
            int i = tmp.getCoord().x;
            int j = tmp.getCoord().y;
            if (tmp.getColor() == Color.cyan){
                invoker.move(tmp, i,j);
                invoker.clear();
            }
        }
    }

}

/*
if (tmp.getClass().toString().equals("class Pawn") || tmp.getClass().toString().equals("class Archer") ){
            Point p = (Piece)tmp.getCoord();
            invoker.suggestion(tmp);
        }
        else if (tmp.getClass().toString().equals("class Rectangle") && )
 */
