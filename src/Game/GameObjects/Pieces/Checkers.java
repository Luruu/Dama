package Game.GameObjects.Pieces;
import java.awt.*;
import Game.GameObjects.Players.*;
import Game.GraphicObjects.Table.IstanceException;

/**
 * <h1>Checkers Class</h1> 
 * This class represents the checkers object and encapsulates its movement logic.
 * @author <a href="https://github.com/Luruu">Luca Rubino</a>
 * @author <a href="https://github.com/RenatoEsposito1999">Renato Esposito</a>
 * @version 1.0
 * @since 31-08-2021
 */
public class Checkers extends Piece{
    public final static int CHECKERS_POINTS = 5;
    public final static String CHECKGREEN_Path    = "/images/CheckersGreen.png";
    public final static String CHECKRED_Path      = "/images/CheckersRed.png";


/**
 * @param colorPiece the color of the checkers piece.
 * @param playerowner the owner player who has the ability to move the piece.
 * @throws NPlayerExceededException CheckersTable getInstance() Exception.
 * @throws IstanceException
 */
    public Checkers(Color colorPiece, Player playerowner) throws NPlayerExceededException, IstanceException{
        super(colorPiece, CHECKERS_POINTS, playerowner);
    }

/**
 * This method determines the possible movements of the checker piece.
 * @param direction the direction of the boxes to be analyzed.
 * @return 1: //It's useless but necessary because of override.
 */
    @Override
    public int showSuggestions(int direction){
        int oppositeDirection;
        boolean showMoreSuggestions = super.showSuggestions(direction) != 2; //if you have not to eat
        if(showMoreSuggestions){ //Show more suggestions if you have not to eat 
            TABLE.clearSuggestions(); 
            oppositeDirection = (getColor().equals(Color.red)) ? getCoord().x + 1 : getCoord().x - 1;
            if (super.showSuggestions(oppositeDirection) != 2)  //if you have not to eat
            super.showSuggestions(direction); //Show again base suggestions
        }
        return 1;
    }

/**
 * This method determines the path of the image to be drawn on the game table.
 * @return  the path of the image to be drawn on the game table.
*/
    @Override
    public String getPathIMG(){
        return (getColor() == Color.red) ? CHECKRED_Path : CHECKGREEN_Path;
    }

}
