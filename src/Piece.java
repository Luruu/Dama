import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;

public abstract class Piece extends JComponent{
   protected final Color color;
   protected  int points;
   protected Image objIMG;
   protected String IMG_Path;
   int i,j;
   
   public void setIJ(int i, int j){
      this.i=i;
      this.j=j;
   }

   public Piece(Color c, int p, String imgName){
      color = c;
      points = p;
      IMG_Path = imgName;
      objIMG = new Image(imgName);
   }

   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(objIMG.img,0,0,null);
   }


}
