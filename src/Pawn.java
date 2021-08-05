import java.awt.*;
public class Pawn extends Piece{
    public Pawn(Color c){
        super(c,1, "/images/pedina.png");
        setPreferredSize(new Dimension(82,82));
        //setLocation(43,43);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //g.drawRect(0,0,180,180);
        g.drawImage(objIMG.img,0,0,null);
        //g.setColor(Color.green);
        //g.fillRect(0,0,180,180);
    }
}
