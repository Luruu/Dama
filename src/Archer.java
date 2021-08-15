import java.awt.*;

public class Archer extends Piece{
    public Archer(Color c, String path_name){
        super(c, 3, path_name);
        setPreferredSize(new Dimension(DIM_IMG, DIM_IMG));
    }
}
