import java.awt.*;
public class Pawn extends Piece{
    public Pawn(Color c, String path_name) throws Exception{
        super(c, 1, path_name);
    }
   
    @Override
    public int showSuggestions(int direction){
        super.showSuggestions(direction); //show basics movement for a piece
        // DA IMPLEMENTARE!!!!
        return 0;
        
    }
}
