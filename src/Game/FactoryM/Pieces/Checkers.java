package Game.FactoryM.Pieces;
import java.awt.*;

import Game.FactoryM.Players.Player;
public class Checkers extends Piece{
    public final static int CHECKERS_POINTS = 5;
    
    public Checkers(Color colorPiece, Player playerowner) throws Exception{
        super(colorPiece, CHECKERS_POINTS, playerowner);
    }

    @Override
    public int showSuggestions(int direction){
        int oppositeDirection;
        boolean showMoreSuggestions = super.showSuggestions(direction) != 2; //if you have not to eat
        if(showMoreSuggestions){ //Show more suggestions if you have not to eat 
            TABLE.clearSuggestions(); 
            oppositeDirection = (getColor().equals(Color.red)) ? getCoord().x + 1 : getCoord().x - 1;
            if (super.showSuggestions(oppositeDirection) != 2)  //if you have not to eat
            super.showSuggestions(direction); //Show again base suggestions
        }
        return 1; //It's useless but necessary because of override
    }

    @Override
    public String getPathIMG(){
        return (getColor() == Color.red) ? "/images/CheckersRed.png" : "/images/CheckersGreen.png";
    }

}
