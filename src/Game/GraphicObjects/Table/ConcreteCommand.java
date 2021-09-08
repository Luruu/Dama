package Game.GraphicObjects.Table;

import Game.GameObjects.Pieces.Piece;
/**
 * Implementation of the Command pattern concrete Command.
 */
public class ConcreteCommand implements Command{
    private CheckersTable checkTable;
    public ConcreteCommand(CheckersTable ct) {
        checkTable = ct;
    }
    /**
     * Show board suggestions based on the clicked piece.
     */
    @Override
    public void suggestions(Piece pClicked) {
        checkTable.suggestions(pClicked);
    }
    /**
     * Cleans the suggestions of the movements.
     */
    public void clear(){
        checkTable.clearSuggestions();
    }
    
    /**
     * Move a piece from one position to another.
     * @param r Destination box.
     * @throws Exception exception.
     */
    public void move(Box b) throws Exception{ 
        checkTable.move(b);
    }
}
