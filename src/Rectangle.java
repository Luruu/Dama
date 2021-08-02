import javax.swing.*;
import java.awt.*;

public  class Rectangle extends JPanel {
    private final int x,y,w,h;
    private Color color;

    public Rectangle(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRect(x,y,w,h);
        g.setColor(color);
        g.fillRect(x,y,w,h);
    }

    public void setColor(Color c){
        this.color  = c;
    }
}
