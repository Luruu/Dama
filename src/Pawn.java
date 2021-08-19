import java.awt.*;
public class Pawn extends Piece{
    public Pawn(Color c) throws Exception{
        super(c, 1);
    }
   
    @Override
    public int showSuggestions(int direction){
        super.showSuggestions(direction);
        return 0;
    }

    @Override
    public String getPathIMG(){
        return (getColor() == Color.red) ? "/images/PawnRed.png" : "/images/PawnGreen.png";
    }
}
