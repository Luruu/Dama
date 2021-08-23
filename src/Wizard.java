import java.awt.*;

public class Wizard extends Piece{
    public final static int WIZARD_POINTS = 3;
    
    public Wizard(Color colorPiece, Player playerowner) throws Exception{
        super(colorPiece, WIZARD_POINTS, playerowner);
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
        return (getColor() == Color.red) ? "/images/WizardRed.png" : "/images/WizardGreen.png";
    }

    public static boolean is_WizardStartPosition(int i_row, int j_col, int n_rows, int n_cols){
        return (i_row == 0 && j_col == n_rows - 1) || (i_row == n_cols - 1 && j_col == 0);
    }
}
