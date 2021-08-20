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

    //Overload del costruttore
    //Usato per i rettangoli che hanno bisogno di un mouseadapter.
    public Player() throws Exception {
        color = null;
        nome = null;
        invoker = new InvokerList(new ConcreteCommand(CheckersTable.getInstance()));
    }

    public void mouseClicked(MouseEvent e){
        String nameClass = e.getSource().getClass().toString();
        if (nameClass.equals("class Pawn") || nameClass.equals("class Archer") ||  nameClass.equals("class Checkers")){
            invoker.clear();
            Piece pieceClicked = (Piece)e.getSource();
            invoker.suggestions(pieceClicked); //Show suggestions for pieceClicked
        }
        else if(nameClass.equals("class Rectangle")){;
            Rectangle rectClicked = (Rectangle) e.getSource();
            if (rectClicked.getColor() == Color.cyan){
                try {
                    invoker.move(rectClicked, rectClicked.getCoord().x, rectClicked.getCoord().y);
                } catch (Exception e1) {
                    e1.getMessage();
                }
                invoker.clear();
            }
        }
    }

    public void addPlayerPoints(int value){
        points += value;
        System.out.println("il nuovo punteggio è " + points);
    }
    
}