import java.awt.*;

public class Archer extends Piece{
    public final static int ARCHER_POINTS = 3;
    
    public Archer(Color colorPiece, Player playerowner) throws Exception{
        super(colorPiece, ARCHER_POINTS, playerowner);
    }

    @Override
    public int showSuggestions(int direction){
        int oppositeDirection;
        boolean showMoreSuggestions = super.showSuggestions(direction) != 2; //true if you have not to eat
        if(showMoreSuggestions){ //Show more suggestions if you have not to eat 
            TABLE.clearSuggestions(); 
            oppositeDirection = (getColor().equals(Color.red)) ? getCoord().x + 1 : getCoord().x - 1;
            if (super.showSuggestions(oppositeDirection) != 2)  //true if you have not to eat
            super.showSuggestions(direction); //Show again base suggestions
        }
        return 1; //It's useless but necessary because of override
    }

    @Override
    public String getPathIMG(){
        return (getColor() == Color.red) ? "/images/ArcherRed.png" : "/images/ArcherGreen.png";
    }
}
