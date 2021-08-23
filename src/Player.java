import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;


public class Player extends MouseAdapter {
    public static int count_players = 0;
    public static final int MAX_NPLAYERS = 2;
    public static boolean RedChosen = false;
    public static boolean GreenChosen = false;
    private final Color PlayerColor;
    private final String PlayerName;
    
    private final InvokerList invoker;

    private int PlayerScore;

    public Player(Color c, String n) throws Exception {
        super();

        if (++count_players > MAX_NPLAYERS)
            throw new Exception("Maximum number of players exceeded [MAXIMUM " + MAX_NPLAYERS + "]");

      //  if (count_players > 1 && )

        PlayerColor = c;
        PlayerName = n;
        PlayerScore = 0;
        invoker = new InvokerList(new ConcreteCommand(CheckersTable.getInstance()));
    }

    //Overload del costruttore
    //Usato per i rettangoli che hanno bisogno di un mouseadapter.
    public Player() throws Exception {
        PlayerColor  = null;
        PlayerName = null;
        invoker = new InvokerList(new ConcreteCommand(CheckersTable.getInstance()));
    }

    public void mouseClicked(MouseEvent e){
        String nameClass = e.getSource().getClass().toString();
        if (nameClass.equals("class Pawn") || nameClass.equals("class Wizard") ||  nameClass.equals("class Checkers")){
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


    // Getters and Setters methods..
    
    public void addPlayerPoints(int points){
        PlayerScore += points;
        System.out.println(PlayerName + " score: " + PlayerScore); 
    }

    //

    public Color getPlayerColor(){
        return PlayerColor;
    }

    public String getPlayerName(){
        return PlayerName;
    }

    public int getPlayerScore(){
        return PlayerScore;
    }
    
}