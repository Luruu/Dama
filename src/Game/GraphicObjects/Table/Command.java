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
     * Cleans movement suggestions.
     */
    void clear();
    /**
     * Move a piece from one position to another.
     * @param b Destination box.
     * @throws Exception exception.
     */
    void move(Box b) throws Exception;
}
