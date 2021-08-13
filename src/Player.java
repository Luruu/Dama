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
        String nameClass = e.getSource().getClass().toString();
        if (nameClass.equals("class Pawn") || nameClass.equals("class Archer") ){
            invoker.clear();
            Piece pieceClicked = (Piece)e.getSource();
            invoker.suggestions(pieceClicked);
        }
        else if(nameClass.equals("class Rectangle")){;
            Rectangle rectClicked = (Rectangle) e.getSource();
            if (rectClicked.getColor() == Color.cyan){
                invoker.move(rectClicked, rectClicked.getCoord().x, rectClicked.getCoord().y);
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
