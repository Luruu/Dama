package Game.GraphicObjects.Table;
import Game.GameObjects.Pieces.*;

public interface Command {
    void suggestions(Piece pClicked);
    void clear();
    void move(Box r, int i, int j) throws Exception;
}
