import java.awt.*;
public class myColor {
    //Return the color according to position
    public static Color getColor(int i, int j){
        return  (i%2 == 0 && j%2 != 0 || j%2 == 0 && i%2 != 0) ? Color.darkGray : Color.white;
    }
    
}
