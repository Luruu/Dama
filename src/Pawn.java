import java.awt.*;
public class Pawn extends Piece{
    public Pawn(Color c){
        super(c,1, "/images/PawnRed.png");
        setPreferredSize(new Dimension(82,82));
    }
}
