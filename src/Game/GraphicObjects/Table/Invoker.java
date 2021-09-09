package Game.GraphicObjects.Table;
import Game.GameObjects.Pieces.*;
/**
 * <h1>InvokerList class</h1> 
 * This class is the implementation of the Command pattern invoker.
 */
public class Invoker {
    private Command command;
    /**
     * Constructor.
     * @param command Element that implements the commands.
     */
    public Invoker(Command command){
        this.command = command;
    }

    /**
     * /**
     * Show board suggestions based on the clicked piece.
     * @param p clicked piece to move.
     */
    public void suggestions(Piece p){
        command.suggestions(p);
    }

    /**
     * Cleans the suggestions of the movements.
     */
    public void clear() {
        command.clear();
    }
    
    /**
     * Move a piece from one position to another.
     * @param box Destination box.
     * @throws Exception exception.
     */
    public void move(Box box) throws Exception{
        command.move(box);
    }
}
