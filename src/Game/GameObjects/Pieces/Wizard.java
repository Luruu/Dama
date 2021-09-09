package Game.GameObjects.Pieces;
import java.awt.*;

import Game.GameObjects.Players.Player;

/**
 * <h1>Wizard Class</h1> 
 * This class represents the wizard object and encapsulates its movement logic.
 * @author <a href="https://github.com/Luruu">Luca Rubino</a>
 * @author <a href="https://github.com/RenatoEsposito1999">Renato Esposito</a>
 * @version 1.0
 * @since 31-08-2021
 */
public class Wizard extends Piece{
    public final static int WIZARD_POINTS = 3;
    public final static String WIZGREEN_Path    = "/images/WizardGreen.png";
    public final static String WIZRED_Path      = "/images/WizardRed.png";
    
/**
 * @param colorPiece the color of the checkers piece.
 * @param playerowner the owner player who has the ability to move the piece.
 * @throws Exception CheckersTable getInstance() Exception.
 */
    public Wizard(Color colorPiece, Player playerowner) throws Exception{
        super(colorPiece, WIZARD_POINTS, playerowner);
    }

/**
 * This method determines the possible movements of the wizard piece.
 * @param direction the direction of the boxes to be analyzed.
 * @return 1: //It's useless but necessary because of override.
 */
    @Override
    public int showSuggestions(int direction){
        boolean showMoreSuggestions = super.showSuggestions(direction) != 2; //true if you have not to eat
        if(showMoreSuggestions){ //Show more suggestions if you have not to eat 
            TABLE.clearSuggestions(); 
            int oppositeDirection = (getColor().equals(Color.red)) ? getCoord().x + 1 : getCoord().x - 1;
            if (super.showSuggestions(oppositeDirection) != 2)  //true if you have not to eat
            super.showSuggestions(direction); //Show again base suggestions
        }
        return 1; //It's useless but necessary because of override
    }

/**
*This method determines the path of the image to be drawn on the game table.
*/
    @Override
    public String getPathIMG(){
        return (getColor() == Color.red) ? WIZRED_Path : WIZGREEN_Path;
    }


/**
 * Return if a wizard must be inserted in the input position, false otherwise.
 * @param i_row row index.
 * @param j_col column index.
 * @param n_rows number of lines.
 * @param n_cols number of columns.
 * @return if a wizard must be inserted in the input position, false otherwise.
*/
    public static boolean is_WizardStartPosition(int i_row, int j_col, int n_rows, int n_cols){
        return (i_row == 0 && j_col == n_rows - 1) || (i_row == n_cols - 1 && j_col == 0);
    }
}
