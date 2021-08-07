import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;

public abstract class Piece extends JComponent{
   protected final Color color;
   protected  int points;
   protected Image objIMG;
   protected String IMG_Path;
   protected Point coord;
   
   public void setCoord(int i, int j){
      coord.x = i;
      coord.y = j;
   }

   public Point getCoord() {
      return coord;
   }

   public Piece(Color c, int p, String imgName){
      color = c;
      points = p;
      IMG_Path = imgName;
      objIMG = new Image(imgName);
      coord = new Point();
   }

   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(objIMG.img,0,0,null);
   }


}
