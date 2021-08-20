import java.awt.*;

public class Archer extends Piece{
    public Archer(Color c) throws Exception{
        super(c, 3);
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
        return 1;
    }

    @Override
    public String getPathIMG(){
        return (getColor() == Color.red) ? "/images/ArcherRed.png" : "/images/ArcherGreen.png";
    }
}
