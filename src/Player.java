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
        CheckersTable TABLE = null;
        try {
            TABLE = CheckersTable.getInstance();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String nameClass = e.getSource().getClass().toString();
        if (nameClass.equals("class Pawn") || nameClass.equals("class Wizard") ||  nameClass.equals("class Checkers")){
            if (!checkTurn(TABLE.activePlayer))
                return;
            invoker.clear();
            Piece pieceClicked = (Piece)e.getSource();
            if ( pieceClicked.getColor() == PlayerColor )
                invoker.suggestions(pieceClicked); //Show suggestions for pieceClicked
            else
                return;
        }
        else if(nameClass.equals("class Box")){
            Box boxClicked = (Box) e.getSource();
            if (Boolean.logicalOr(boxClicked.getColor().equals(Color.red), boxClicked.getColor().equals(Color.green))){
                try {
                    invoker.move(boxClicked, boxClicked.getCoord().x, boxClicked.getCoord().y);
                } catch (Exception e1) {
                    e1.getMessage();
                }
                TABLE.switchTurn();
                invoker.clear();
            }
        }
    }

    private Boolean checkTurn(Color c){
        return (PlayerColor.equals(c));
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