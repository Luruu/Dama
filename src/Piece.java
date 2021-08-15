import javax.swing.*;
//import javax.swing.event.MouseInputListener; INUTILIZZATO!!!

import java.awt.*;
//import java.util.Objects;

public abstract class Piece extends JComponent {
   private final Color color;
   private int points;
   private Image objIMG;
   final int DIM_IMG;
   private String IMG_Path;
   private Point coord;
   
   public void setCoord(int i, int j){
      coord.x = i;
      coord.y = j;
   }

   public Point getCoord() {
      return coord;
   }

   public Piece(Color c, int p, String imgName) throws Exception{
      color = c;
      points = p;
      IMG_Path = imgName;
      objIMG = new Image(imgName);
      DIM_IMG = CheckersTable.getInstance().getDIM_RECT() - 10; //Dimension of IMG - margin 
      coord = new Point();
   }

   

   protected void paintComponent(Graphics g){
      super.paintComponent(g);
      g.drawImage(objIMG.img,0,0,null);
   }

   protected Color getColor(){
      return color;
   }


}
