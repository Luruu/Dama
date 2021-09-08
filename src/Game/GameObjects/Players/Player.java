package Game.GameObjects.Players;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Game.GameObjects.FactoryM.ElementGame;
import Game.GameObjects.Pieces.*;
import Game.GraphicObjects.Table.*;
import Game.Observer.Observer;

import java.awt.*;

/**
 * <h1>Player Class</h1> 
 * This class represents the abstraction of the player,so it encapsulates the click logic.
 * @author <a href="https://github.com/Luruu">Luca Rubino</a>
 * @author <a href="https://github.com/RenatoEsposito1999">Renato Esposito</a>
 * @version 1.0
 * @since 31-08-2021
 */
public class Player extends MouseAdapter implements Observer, ElementGame, Prototype{
    public static int count_players = 0;
    public static final int MAX_NPLAYERS = 2;
    private final Color PlayerColor;
    private final String PlayerName;
    private int npieces = 0;
    private final InvokerList invoker;
    private CheckersTable TABLE;
    private int PlayerScore;

/**
 * 
 * @param c colors of the pieces that can move.
 * @param n player's name.
 * @throws Exception Player Exception: Maximum number of players exceeded
 */
    public Player(Color c, String n) throws Exception {
        super();

        if (++count_players > MAX_NPLAYERS)
            throw new Exception("Maximum number of players exceeded [MAXIMUM " + MAX_NPLAYERS + "]");

        PlayerColor = c;
        PlayerName = n;
        PlayerScore = 0;
        TABLE = CheckersTable.getInstance();
        invoker = new InvokerList(new ConcreteCommand(TABLE));
    }


    /**
     * Overload of the constructor, used for rectangles that need a mouseadapter.
     * @throws Exception Exception checkersTable getInstance() 
     */
    public Player() throws Exception {
        PlayerColor  = null;
        PlayerName = null;
        TABLE = CheckersTable.getInstance();
        invoker = new InvokerList(new ConcreteCommand(TABLE));
    }

    /**
     * Override of the MouseAdapter method, it defines the commands to be given to the game table when the player clicks on the components.
     */
    @Override
    public void mouseClicked(MouseEvent e){
        String nameClass = e.getSource().getClass().getSimpleName();
        if (nameClass.equals("Pawn") || nameClass.equals("Wizard") ||  nameClass.equals("Checkers")){
            if (!checkTurn(TABLE.activePlayer))
                return;
            invoker.clear();
            Piece pieceClicked = (Piece)e.getSource();
            if ( pieceClicked.getColor() == PlayerColor )
                invoker.suggestions(pieceClicked); //Show suggestions for pieceClicked
        }
        else if(nameClass.equals("Box")){
            Box boxClicked = (Box) e.getSource();
            if (Boolean.logicalOr(boxClicked.getColor().equals(Color.red), boxClicked.getColor().equals(Color.green))){
                try {
                    invoker.move(boxClicked);
                } catch (Exception e1) {
                    e1.getMessage();
                }
                TABLE.switchTurn();
                invoker.clear();
            }
        }
    }

    /**
     * Return true if it is the player's turn, false otherwise.
     * @param c color of the player.
     * @return true if it is the player's turn, false otherwise.
     */
    private Boolean checkTurn(Color c){
        return (PlayerColor.equals(c));
    }

    /** 
     * This method updates the winning player.
     * @param obj if it is null, the game ends in a draw.
    */
    @Override
    public void update(Object obj) {
        TABLE.timeElapsed(this, obj);        
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    // Getters and Setters methods..
    
    /**
     * @param points This method adds points to the PlayerScore.
     */
    public void addPlayerPoints(int points){
        PlayerScore += points;
    }


    /**
     * Return player color.
     * @return player color.
     */
    public Color getPlayerColor(){
        return PlayerColor;
    }

    /**
     * Return player name.
     * @return player name.
     */
    public String getPlayerName(){
        return PlayerName;
    }
    
    /**
     * Return player score.
     * @return player score.
     */
    public int getPlayerScore(){
        return PlayerScore;
    }

    /**
     * Return the number of "live" pieces it owns.
     * @return the number of "live" pieces it owns.
     */
    public int getNpieces() {
        return npieces;
    }

    /**
     * Increases the number of "live" pieces it owns by one.
     */
    public void increaseNpieces(){
        npieces += 1;
    }

    /**
     * Decreases the number of "live" pieces it owns by one.
     */
    public void decreaseNpieces() {
        npieces -= 1;
    }

    /**
     * Return the invoker of the pattern command.
     * @return the invoker of the pattern command.
     */
    public InvokerList getInvoker() {
        return this.invoker;
    }

    /**
     * Set the player's score.
     * @param PlayerScore the player score.
     */
    public void setPlayerScore(int PlayerScore) {
        this.PlayerScore = PlayerScore;
    }

    
    
}