package Game.Windows.Table;
import Game.ObjGamepkg.Pieces.*;

public interface Command {
    void suggestions(Piece pClicked);
    void clear();
    void move(Box r, int i, int j) throws Exception;
}
