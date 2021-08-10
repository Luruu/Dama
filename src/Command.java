import java.awt.*;
public interface Command {
    void suggestion(Piece p);
    void clear();
    void move(Rectangle r,int i, int j);
}
