import java.awt.*;

public class Checkers extends Piece{
    
    public Checkers(Color c, String path_name) throws Exception{
        super(c, 5, path_name);
    }

    @Override
    public int showSuggestions(int direction){
        int oppositeDirection;
        boolean showMoreSuggestions = super.showSuggestions(direction) != 2;
        if(showMoreSuggestions){
            oppositeDirection = (getColor().equals(Color.red)) ? getCoord().x + 1 : getCoord().x - 1;
            super.showSuggestions(oppositeDirection);
        }
        return 1;
    }

}
