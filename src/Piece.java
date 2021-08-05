import javax.swing.*;
import java.awt.*;

public abstract class Piece extends JComponent {
   protected final Color color;
   protected  int points;
   protected Image objIMG;

   public Piece(Color c, int p, String imgName){
      color = c;
      points = p;
      objIMG = new Image(imgName);
   }

   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(objIMG.img,0,0,null);
   }
}
