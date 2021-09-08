package Game.GraphicObjects.Table;
import Game.GameObjects.Pieces.*;

/**
 * <h1>Command pattern interface</h1> 
 */
public interface Command {
    /**
     * Show board suggestions based on the clicked piece.
     * @param pClicked clicked piece to move.
     */
    void suggestions(Piece pClicked);
    /**
     * Cleans the suggestions of the movements.
     */
    void clear();
    /**
     * Move a piece from one position to another.
     * @param r Destination box.
     * @throws Exception exception.
     */
    void move(Box r) throws Exception;
}
