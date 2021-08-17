import java.awt.*;

public class Checkers extends Piece{
    
    public Checkers(Color c, String path_name) throws Exception{
        super(c, 5, path_name);
    }

    @Override
    public int showSuggestions(){

        boolean showMoreSuggestions = super.showSuggestions() != 2;
        System.out.println("Archer ha richiamato showSuggestion di Piece!");
        if(showMoreSuggestions)
            showCheckersSuggestions();
        
        final int QUALCOSA = 400; //poi si vede cosa ritornare
        return QUALCOSA;
    }


    public int showCheckersSuggestions(){
        
        return 0;
    }

}
