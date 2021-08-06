import java.awt.*;
public class Pawn extends Piece{
    public Pawn(Color c, String path_name){
        
        super(c,1, path_name);
        setPreferredSize(new Dimension(82,82));
    }
}
