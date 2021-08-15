import java.awt.*;

public class Archer extends Piece{
    public Archer(Color c, String path_name) throws Exception{
        super(c, 3, path_name);
        points = 3;
    }

    @Override
    public void showSuggestions(){
        super.showSuggestions(); //show basics movement for a piece
        // DA IMPLEMENTARE!!!!
        System.out.println("Archer ha richiamato showSuggestion di Piece!");
    }
}
