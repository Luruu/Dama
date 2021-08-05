import javax.swing.*;
import java.awt.*;

public  class Rectangle extends JPanel {
    private final int x,y,wid,hei;
    private Color color;

    public Rectangle(int x, int y, int wid, int hei){
        this.x = x;
        this.y = y;
        this.wid = wid;
        this.hei = hei;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x,y,wid,hei);
        g.setColor(color);
        g.fillRect(x,y,wid,hei);
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
}
