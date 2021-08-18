import java.awt.*;

public class Archer extends Piece{
    public Archer(Color c) throws Exception{
        super(c, 3);
    }

    @Override
    public int showSuggestions(int direction){
        
        //Poi vediamo per archer
        return 1;
    }

    @Override
    public String setPath(){
        return (getColor() == Color.red) ? "/images/ArcherRed.png" : "/images/ArcherGreen.png";
    }
}
