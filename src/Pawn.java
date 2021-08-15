import java.awt.*;
public class Pawn extends Piece{
    public Pawn(Color c, String path_name) throws Exception{
        super(c, 1, path_name);
        points = 1;
    }
   
    @Override
    public void showSuggestions(){
        super.showSuggestions(); //show basics movement for a piece
        // DA IMPLEMENTARE!!!!
        System.out.println("Pawn ha richiamato showSuggestion di Piece!");
    }
}
