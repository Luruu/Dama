import java.awt.*;

public class Checkers extends Piece{
    
    public Checkers(Color c, String path_name) throws Exception{
        super(c, 5, path_name);
    }

    @Override
    public void showSuggestions(){
        super.showSuggestions(); //show basics movement for a piece
        // DA IMPLEMENTARE!!!!
        System.out.println("Archer ha richiamato showSuggestion di Piece!");
    }


}
