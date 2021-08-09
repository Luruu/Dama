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
        invoker.clear();
       System.out.println(e.getSource().getClass());
        if (e.getSource().getClass().toString().equals("class Pawn") || e.getSource().getClass().toString().equals("class Archer") ){
            Piece tmp = (Piece)e.getSource();
            Point p = tmp.getCoord();
            invoker.suggestion(tmp);
        }
        else if (e.getSource().getClass().toString().equals("class Rectangle")){
            Rectangle tmp = (Rectangle) e.getSource();
            if (tmp.getColor() == Color.cyan){
                //RIprendiamo da qua-------------------------------------------------------
                System.out.println("Riprendiamo da qua");
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
