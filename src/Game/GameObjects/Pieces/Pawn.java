package Game.GameObjects.Pieces;
import java.awt.*;

import Game.GameObjects.Players.Player;

public class Pawn extends Piece{
    public final static int PAWN_POINTS = 1;
    public Pawn(Color c, Player p) throws Exception{
        super(c, PAWN_POINTS, p);
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
