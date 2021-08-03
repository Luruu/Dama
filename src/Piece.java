import java.awt.*;

public abstract class Piece {
   protected final Color color;
   protected  int points;
   protected Image img;
   public Piece(Color c, int p, String imgName){
      color = c;
      points = p;
      img = new Image(imgName);
   }

   protected Image getImg(){
      return img;
   }
}
