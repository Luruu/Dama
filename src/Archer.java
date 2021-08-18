import java.awt.*;

public class Archer extends Piece{
    public Archer(Color c, String path_name) throws Exception{
        super(c, 3, path_name);
    }

    @Override
    public int showSuggestions(int direction){
        
        //Poi vediamo per archer
        return 1;
    }
}
