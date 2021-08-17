import java.awt.*;
public interface ColorFunctions {
     //Return the color according to position
     public static Color getColor_PlayableOrNot(int i, int j){
        Boolean quadrato_giocabile = (i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0);
        return  (quadrato_giocabile) ? Color.darkGray : Color.white;
    }
}